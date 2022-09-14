/* 
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.functions.root_finding_methods;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.iteration_statistics.*;
import fractalzoomer.functions.Fractal;
import fractalzoomer.in_coloring_algorithms.*;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.main.app_settings.TrueColorSettings;
import fractalzoomer.out_coloring_algorithms.*;
import fractalzoomer.true_coloring_algorithms.UserTrueColorAlgorithm;
import fractalzoomer.utils.ColorAlgorithm;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public abstract class RootFindingMethods extends Fractal {

    protected double convergent_bailout;
    protected Object[] iterationData;

    public RootFindingMethods(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, 0, 0, "", "", 0, 0, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        convergent_bailout = 1E-10;
        isJulia = false;
        defaultInitVal = new DefaultInitialValue();

    }

    //orbit
    public RootFindingMethods(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, false);
        defaultInitVal = new DefaultInitialValue();

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[1];
        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
            if(!isOrbit && !isDomain) {
                complex[0] = pixel.plus(refPointSmall);
            }
            else {
                complex[0] = new Complex(pixel);
            }
        }
        else {
            complex[0] = new Complex(pixel);//z
        }

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[0]);

        return complex;

    }

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {
        iterations = 0;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, pixel, start, c0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, pixel, start, c0, pixel};
                iterationData = object;
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
                }

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, pixel, start, c0, pixel};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
        }

        return in;

    }

    @Override
    protected void iterateFractalOrbit(Complex[] complex, Complex pixel) {
        iterations = 0;

        Complex temp = null;

        for (; iterations < max_iterations; iterations++) {
            updateValues(complex);

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);

            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    protected Complex iterateFractalDomain(Complex[] complex, Complex pixel) {

        iterations = 0;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);

        }

        return complex[0];

    }

    @Override
    public double calculateJulia(GenericComplex pixel) {
        return 0;
    }

    @Override
    public void calculateJuliaOrbit() {
    }

    @Override
    public Complex calculateJuliaDomain(Complex pixel) {

        return null;

    }

    @Override
    public double getConvergentBailout() {

        return Math.sqrt(convergent_bailout);

    }

    @Override
    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int converging_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTime();
                } else {
                    out_color_algorithm = new SmoothEscapeTimeRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if (!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                } else {
                    out_color_algorithm = new SmoothBinaryDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if (!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                } else {
                    out_color_algorithm = new SmoothBinaryDecomposition2RootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                if (!smoothing) {
                    out_color_algorithm = new ColorDecompositionRootFindingMethod();
                } else {
                    out_color_algorithm = new SmoothColorDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeColorDecompositionRootFindingMethod();
                } else {
                    out_color_algorithm = new SmoothEscapeTimeColorDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(3);
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if (user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithmRootFindingMethod(outcoloring_formula, convergent_bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                } else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithmRootFindingMethod(user_outcoloring_conditions, user_outcoloring_condition_formula, convergent_bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                }
                break;

        }
    }

    @Override
    protected void InColoringAlgorithmFactory(int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, double[] plane_transform_center) {

        switch (in_coloring_algorithm) {

            case MainWindow.MAX_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(max_iterations);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike(max_iterations);
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm(max_iterations);
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag(max_iterations);
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared(max_iterations);
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared(max_iterations);
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm(max_iterations);
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares(max_iterations);
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2(max_iterations);
                break;
            case MainWindow.USER_INCOLORING_ALGORITHM:
                if (user_in_coloring_algorithm == 0) {
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations, xCenter, yCenter, size, plane_transform_center, convergent_bailout, globalVars);
                } else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations, xCenter, yCenter, size, plane_transform_center, convergent_bailout, globalVars);
                }
                break;

        }

    }

    @Override
    public double getFractal3DHeight(double value) {

        if (escaped) {
            double res = out_color_algorithm.getResult3D(iterationData);

            res = getFinalValueOut(res);

            return ColorAlgorithm.transformResultToHeight(res, max_iterations);
        }

        return ColorAlgorithm.transformResultToHeight(value, max_iterations);

    }

    @Override
    public double getJulia3DHeight(double value) {

        return 0;

    }

    @Override
    public int type() {

        return MainWindow.CONVERGING;

    }

    @Override
    protected void StatisticFactory(StatisticsSettings sts, double[] plane_transform_center) {

        statisticIncludeEscaped = sts.statisticIncludeEscaped;
        statisticIncludeNotEscaped = sts.statisticIncludeNotEscaped;

        if (sts.statisticGroup == 1) {
            statistic = new UserStatisticColoringRootFindingMethod(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, convergent_bailout, plane_transform_center, globalVars, sts.useAverage, sts.user_statistic_init_value, sts.reductionFunction, sts.useIterations, sts.useSmoothing);
            return;
        }
        else if(sts.statisticGroup == 2) {
            statistic = new Equicontinuity(sts.statistic_intensity, sts.useSmoothing, sts.useAverage, 0, true, Math.log(convergent_bailout), sts.equicontinuityDenominatorFactor, sts.equicontinuityInvertFactor, sts.equicontinuityDelta);
            return;
        }
        else if(sts.statisticGroup == 4) {
            statistic = new RootColoring(Math.log(convergent_bailout), sts.rootIterationsScaling, max_iterations, sts.rootColors, sts.rootSmooting, this, sts.unmmapedRootColor.getRGB());
            return;
        }

        switch (sts.statistic_type) {

            case MainWindow.COS_ARG_DIVIDE_INVERSE_NORM:
                statistic = new CosArgDivideInverseNorm(sts.statistic_intensity, sts.cosArgInvStripeDensity, sts.StripeDenominatorFactor);
                break;
            case MainWindow.ATOM_DOMAIN_BOF60_BOF61:
                statistic = new AtomDomain(sts.showAtomDomains, sts.statistic_intensity, sts.atomNormType, sts.atomNNorm);
                break;
            case MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS:
                statistic = new DiscreteLagrangianDescriptors(sts.statistic_intensity, sts.lagrangianPower, 0, sts.useSmoothing, sts.useAverage, true, Math.log(convergent_bailout), sts.langNormType, sts.langNNorm);
                break;
        }
    }

    @Override
    public void setTrueColorAlgorithm(TrueColorSettings tcs) {

        if (tcs.trueColorOut) {

            if (tcs.trueColorOutMode == 0) {
                super.setTrueColorAlgorithm(tcs);
            } else {
                outTrueColorAlgorithm = new UserTrueColorAlgorithm(tcs.outTcComponent1, tcs.outTcComponent2, tcs.outTcComponent3, tcs.outTcColorSpace, convergent_bailout, max_iterations, xCenter, yCenter, size, point, globalVars);
            }
        }

        if (tcs.trueColorIn) {
            if (tcs.trueColorInMode == 0) {
                super.setTrueColorAlgorithm(tcs);
            } else {
                inTrueColorAlgorithm = new UserTrueColorAlgorithm(tcs.inTcComponent1, tcs.inTcComponent2, tcs.inTcComponent3, tcs.inTcColorSpace, convergent_bailout, max_iterations, xCenter, yCenter, size, point, globalVars);
            }
        }

    }

    @Override
    public Complex[] initializePerturbation(Complex dpixel) {

        Complex[] complex = new Complex[1];

        complex[0] = new Complex(dpixel);

        return complex;

    }

    @Override
    public MantExpComplex[] initializePerturbation(MantExpComplex dpixel) {

        MantExpComplex[] complex = new MantExpComplex[1];

        complex[0] = new MantExpComplex(dpixel);

        return complex;

    }

    @Override
    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, Complex dpixel) {

        iterations = 0;

        int RefIteration = iterations;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z

        Complex zWithoutInitVal = new Complex();

        Complex pixel = dpixel.plus(refPointSmall);

        if(iterations != 0 && RefIteration < MaxRefIteration) {
            complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
        }

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, pixel, start, c0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, pixel, start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
                }

                return res;
            }

            DeltaSubN = perturbationFunction(DeltaSubN, RefIteration);

            RefIteration++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                zWithoutInitVal = getArrayValue(ReferenceSubPixel, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;
            }

        }

        Object[] object = {complex[0], zold, zold2, pixel, start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
        }

        return in;

    }

    @Override
    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, MantExpComplex dpixel) {


        iterations = 0;

        int RefIteration = iterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z

        Complex zWithoutInitVal = new Complex();

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Reduce();
        long exp = DeltaSubN.getExp();

        boolean useFullFloatExp = ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM;

        boolean usedDeepCode = false;
        if(useFullFloatExp || (skippedIterations == 0 && exp <= minExp) || (skippedIterations != 0 && exp <= reducedExp)) {
            usedDeepCode = true;

            MantExpComplex z = new MantExpComplex();
            if(iterations != 0 && RefIteration < MaxRefIteration) {
                z = getArrayDeepValue(ReferenceSubPixelDeep, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();;
            }

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (useFullFloatExp && iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, pixel, start, c0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, pixel, start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
                    }

                    return res;
                }

                DeltaSubN = perturbationFunction(DeltaSubN, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                if (max_iterations > 1) {
                    z = getArrayDeepValue(ReferenceSubPixelDeep, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
                }

                if (z.norm_squared().compareTo(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;
                }

                DeltaSubN.Reduce();

                if(!useFullFloatExp) {
                    if (DeltaSubN.getExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!useFullFloatExp) {
            Complex CDeltaSubN = DeltaSubN.toComplex();

            if(!usedDeepCode && iterations != 0 && RefIteration < MaxRefIteration) {
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
            }

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, pixel, start, c0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, pixel, start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
                    }

                    return res;
                }

                CDeltaSubN = perturbationFunction(CDeltaSubN, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    zWithoutInitVal = getArrayValue(ReferenceSubPixel, RefIteration).plus_mutable(CDeltaSubN);
                    complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
                }

            }
        }

        Object[] object = {complex[0], zold, zold2, pixel, start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
        }

        return in;

    }

    public static Complex applyStep(Complex z, Complex step, Complex relaxation) {

        return z.sub(step.times(relaxation));

    }

    public abstract Complex evaluateFunction(Complex z, Complex c);

}
