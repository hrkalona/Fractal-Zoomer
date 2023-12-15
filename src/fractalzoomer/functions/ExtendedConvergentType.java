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
package fractalzoomer.functions;

import fractalzoomer.core.*;
import fractalzoomer.fractal_options.iteration_statistics.*;
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
 * @author kaloch
 */
public abstract class ExtendedConvergentType extends Julia {
    protected double convergent_bailout;
    protected Object[] iterationData;

    public ExtendedConvergentType() {
        super();
    }
    
    public ExtendedConvergentType(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {
        
        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

        convergent_bailout = 1E-10;

    }
    
    public ExtendedConvergentType(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {
        
        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-10;

    }
    
    //orbit
    public ExtendedConvergentType(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {
       
        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);
        
    }
    
    public ExtendedConvergentType(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {
        
        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        
    }
    
    @Override
    public double getConvergentBailout() {

        return Math.sqrt(convergent_bailout);

    }
    
    @Override
    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int converging_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {
        
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTime();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2RootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if(!smoothing) {
                    out_color_algorithm = new Biomorphs(4);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphsNova(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
                case MainWindow.COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new ColorDecompositionRootFindingMethod();
                }
                else {
                    out_color_algorithm = new SmoothColorDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeColorDecompositionRootFindingMethod();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeColorDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                out_color_algorithm = new EscapeTimeGaussianInteger3();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                out_color_algorithm = new EscapeTimeGaussianInteger4();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(3);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2();
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadiusNova();
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGridNova();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGridNova(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.BANDED:
                out_color_algorithm = new Banded();
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLinesNova();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeFieldLinesNova(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES2:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLines2Nova();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeFieldLines2Nova(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if(user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithmRootFindingMethod(outcoloring_formula, convergent_bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                }
                else {
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
                if(user_in_coloring_algorithm == 0) {
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations, xCenter, yCenter, size, plane_transform_center, convergent_bailout, globalVars);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations, xCenter, yCenter, size, plane_transform_center, convergent_bailout, globalVars);
                }
                break;

        }
        
    }
    
    @Override
    protected void StatisticFactory(StatisticsSettings sts, double[] plane_transform_center) {
        
        statisticIncludeEscaped = sts.statisticIncludeEscaped;
        statisticIncludeNotEscaped = sts.statisticIncludeNotEscaped;
        
        if(sts.statisticGroup == 1) {
            statistic = new UserStatisticColoringRootFindingMethod(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, convergent_bailout, plane_transform_center, globalVars, sts.useAverage, sts.user_statistic_init_value, sts.reductionFunction, sts.useIterations, sts.useSmoothing, sts.lastXItems);
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
                statistic = new CosArgDivideInverseNorm(sts.statistic_intensity, sts.cosArgInvStripeDensity, sts.StripeDenominatorFactor, sts.useSmoothing, Math.log(convergent_bailout), 0, sts.lastXItems);
                break;
            case MainWindow.ATOM_DOMAIN_BOF60_BOF61:
                statistic = new AtomDomain(sts.showAtomDomains, sts.statistic_intensity, sts.atomNormType, sts.atomNNorm, sts.lastXItems);
                break;
            case MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS:
                statistic = new DiscreteLagrangianDescriptors(sts.statistic_intensity, sts.lagrangianPower, 0, sts.useSmoothing, sts.useAverage, true, Math.log(convergent_bailout), sts.langNormType, sts.langNNorm, sts.lastXItems);
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
    public double iterateFractalArbitraryPrecision(GenericComplex[] complex, GenericComplex pixel) {

        iterations = 0;

        complex = precalculateArbitraryData(complex);

        Complex start = gstart.toComplex();
        Complex c0 = gc0.toComplex();
        Complex pixelC = pixel.toComplex();

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0].toComplex(), iterations);
            }

            if (iterations > 0 && convergent_bailout_algorithm.Converged(complex[0], gzold, gzold2, iterations, complex[1], gstart, gc0, pixel)) {
                escaped = true;

                Complex z = complex[0].toComplex();
                Complex zold = gzold.toComplex();
                Complex zold2 = gzold2.toComplex();
                Complex c = complex[1].toComplex();

                Object[] object = {iterations, z, convergent_bailout_algorithm.getDistance(), zold, zold2, c, start, c0, pixelC};
                iterationData = object;
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out, z);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixelC);
                }

                return out;
            }

            gzold2.set(gzold);
            gzold.set(complex[0]);

            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0].toComplex(), gzold.toComplex(), gzold2.toComplex(), iterations, complex[1].toComplex(), start, c0);
            }
        }

        Complex z = complex[0].toComplex();
        Complex zold = gzold.toComplex();
        Complex zold2 = gzold2.toComplex();
        Complex c = complex[1].toComplex();

        Object[] object = {z, zold, zold2, c, start, c0, pixelC};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, z);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixelC);
        }

        return in;

    }

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {
        iterations = 0;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, complex[1], start, c0, pixel};
                iterationData = object;
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out, complex[0]);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, complex[0]);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }
    
    @Override
    public double getJulia3DHeight(double value) {

        if (escaped) {
            double res = out_color_algorithm.getResult3D(iterationData, value);

            res = getFinalValueOut(res, (Complex) iterationData[1]);

            return ColorAlgorithm.transformResultToHeight(res, max_iterations);
        }

        return ColorAlgorithm.transformResultToHeight(value, max_iterations);
        
    }

    @Override
    public double getFractal3DHeight(double value) {

        if (escaped) {
            double res = out_color_algorithm.getResult3D(iterationData, value);

            res = getFinalValueOut(res, (Complex) iterationData[1]);

            return ColorAlgorithm.transformResultToHeight(res, max_iterations);
        }

        return ColorAlgorithm.transformResultToHeight(value, max_iterations);

    }
    
    @Override
    public int type() {
        
        return MainWindow.CONVERGING;
        
    }

    @Override
    public double iterateFractalWithPerturbation(Complex[] complex, MantExpComplex dpixel) {

        float_exp_iterations = 0;
        double_iterations = 0;
        rebases = 0;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        int totalSkippedIterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : SAskippedIterations;
        iterations = totalSkippedIterations;

        int RefIteration = iterations;

        int ReferencePeriod = getPeriod();

        int MaxRefIteration = getReferenceFinalIterationNumber(true, referenceData);

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        boolean usedDeepCode = false;
        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            usedDeepCode = true;
            MantExpComplex z = MantExpComplex.create();
            if(iterations != 0 && RefIteration < MaxRefIteration) {
                z = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
            }
            else if(iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                z = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
            }

            MantExpComplex zoldDeep;

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (doBailCheck && iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, complex[1], start, c0, pixel};
                    iterationData = object;
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res, complex[0]);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(complex[0]);
                zoldDeep = z;

                if (max_iterations > 1) {
                    z = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = getArrayDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0, z, zoldDeep, null);
                }

                if (z.norm_squared().compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;
                    rebases++;
                }

                DeltaSubN.Normalize();

                if(!useFullFloatExp) {
                    if (DeltaSubN.getMinExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!useFullFloatExp) {

            Complex zWithoutInitVal = new Complex();

            Complex CDeltaSubN = DeltaSubN.toComplex();
            Complex CDeltaSub0 = DeltaSub0.toComplex();

            if(!usedDeepCode && iterations != 0 && RefIteration < MaxRefIteration) {
                zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration).plus_mutable(CDeltaSubN);
                complex[0] = getArrayValue(reference, RefIteration).plus_mutable(CDeltaSubN);
            }
            else if(!usedDeepCode && iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration).plus_mutable(CDeltaSubN);
                complex[0] = getArrayValue(reference, RefIteration).plus_mutable(CDeltaSubN);
            }

            boolean isZero = CDeltaSub0.isZero();

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, complex[1], start, c0, pixel};
                    iterationData = object;
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res, complex[0]);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                if (isZero) {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, RefIteration);
                } else {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, CDeltaSub0, RefIteration);
                }

                RefIteration++;
                double_iterations++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration).plus_mutable(CDeltaSubN);
                    complex[0] = getArrayValue(reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
                    rebases++;
                }

            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, complex[0]);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return getAndAccumulateStatsNotScaled(in);

    }


    @Override
    public double iterateFractalWithPerturbation(Complex[] complex, Complex dpixel) {

        double_iterations = 0;
        rebases = 0;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : SAskippedIterations;

        int RefIteration = iterations;

        int ReferencePeriod = getPeriod();

        int MaxRefIteration = getReferenceFinalIterationNumber(true, referenceData);

        Complex zWithoutInitVal = new Complex();

        Complex pixel = dpixel.plus(refPointSmall);

        if(iterations != 0 && RefIteration < MaxRefIteration) {
            complex[0] = getArrayValue(reference, RefIteration).plus_mutable(DeltaSubN);
            zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
        }
        else if(iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            complex[0] = getArrayValue(reference, RefIteration).plus_mutable(DeltaSubN);
            zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
        }

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, complex[1], start, c0, pixel};
                iterationData = object;
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res, complex[0]);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return getAndAccumulateStatsNotDeep(res);
            }

            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayValue(reference, RefIteration).plus_mutable(DeltaSubN);
            }

            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            if(zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;
                rebases++;
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, complex[0]);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return getAndAccumulateStatsNotDeep(in);

    }

    @Override
    public double iterateJuliaWithPerturbation(Complex[] complex, Complex dpixel) {

        double_iterations = 0;
        rebases = 0;

        iterations = 0;

        int RefIteration = iterations;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z

        Complex zWithoutInitVal = new Complex();

        Complex pixel = dpixel.plus(refPointSmall);

        ReferenceData data = referenceData;
        int MaxRefIteration = data.MaxRefIteration;

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, complex[1], start, c0, pixel};
                iterationData = object;
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res, complex[0]);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return getAndAccumulateStatsNotDeep(res);
            }

            DeltaSubN = perturbationFunction(DeltaSubN, data, RefIteration);

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                zWithoutInitVal = getArrayValue(data.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayValue(data.Reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;

                data = secondReferenceData;
                MaxRefIteration = data.MaxRefIteration;

                rebases++;
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, complex[0]);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return getAndAccumulateStatsNotDeep(in);

    }

    //Todo the heuristic about stop using mantexp might not work correctly in root finding methods
    @Override
    public double iterateJuliaWithPerturbation(Complex[] complex, MantExpComplex dpixel) {


        float_exp_iterations = 0;
        double_iterations = 0;
        rebases = 0;

        int totalSkippedIterations = 0;

        iterations = 0;

        int RefIteration = iterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z

        Complex zWithoutInitVal = new Complex();

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        ReferenceDeepData deepData = referenceDeepData;
        ReferenceData data = referenceData;
        int MaxRefIteration = data.MaxRefIteration;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex z = getArrayDeepValue(deepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (doBailCheck && iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, complex[1], start, c0, pixel};
                    iterationData = object;
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res, complex[0]);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                DeltaSubN = perturbationFunction(DeltaSubN, deepData, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                if (max_iterations > 1) {
                    z = getArrayDeepValue(deepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = getArrayDeepValue(deepData.Reference, RefIteration).plus_mutable(DeltaSubN).toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (z.norm_squared().compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;

                    deepData = secondReferenceDeepData;
                    data = secondReferenceData;
                    MaxRefIteration = data.MaxRefIteration;

                    rebases++;
                }

                DeltaSubN.Normalize();

                if(!useFullFloatExp) {
                    if (DeltaSubN.getMinExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!useFullFloatExp) {
            Complex CDeltaSubN = DeltaSubN.toComplex();

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (iterations > 0 && convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, complex[1], start, c0, pixel};
                    iterationData = object;
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res, complex[0]);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                CDeltaSubN = perturbationFunction(CDeltaSubN, data, RefIteration);

                RefIteration++;
                double_iterations++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    zWithoutInitVal = getArrayValue(data.ReferenceSubCp, RefIteration).plus_mutable(CDeltaSubN);
                    complex[0] = getArrayValue(data.Reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;

                    data = secondReferenceData;
                    MaxRefIteration = data.MaxRefIteration;
                    rebases++;
                }

            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, complex[0]);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return getAndAccumulateStatsNotScaled(in);

    }

    public abstract Complex evaluateFunction(Complex z, Complex c);



}
