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
package fractalzoomer.functions.general;

import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.iteration_statistics.*;
import fractalzoomer.functions.FractalWithoutConstant;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.MagneticPendulumSettings;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.main.app_settings.TrueColorSettings;
import fractalzoomer.out_coloring_algorithms.*;
import fractalzoomer.true_coloring_algorithms.UserTrueColorAlgorithm;
import fractalzoomer.utils.ColorAlgorithm;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 *
 * @author hrkalona2
 */
public class MagneticPendulum extends FractalWithoutConstant {

    private double height_squared;
    private Complex[] magnets;
    private Complex[] strengths;
    private Complex gravity;
    private Complex friction;
    private Complex pendulum;
    private Complex stepsize;
    private Complex stepsize_squared;
    private Object[] iterationData;
    private int magnetPendVariableId;

    public MagneticPendulum(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, MagneticPendulumSettings mps) {

        super(xCenter, yCenter, size, max_iterations, 0, 0, "", "", 0, 0, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        magnetPendVariableId = mps.magnetPendVariableId;

        pendulum = new Complex(mps.pendulum[0], mps.pendulum[1]);

        height_squared = mps.height * mps.height;
        stepsize = new Complex(mps.stepsize, mps.stepsize_im);
        stepsize_squared = stepsize.square();

        Predicate<double[]> isEnabled = (double[] strength) -> strength[0] != 0 || strength[1] != 0;

        int count = 0;
        for (int i = 0; i < mps.magnetStrength.length; i++) {
            if (isEnabled.test(mps.magnetStrength[i])) {
                count++;
            }
        }

        magnets = new Complex[count];
        strengths = new Complex[count];

        count = 0;
        for (int i = 0; i < mps.magnetStrength.length; i++) {
            if (isEnabled.test(mps.magnetStrength[i])) {
                magnets[count] = new Complex(mps.magnetLocation[i][0], mps.magnetLocation[i][1]);
                strengths[count] = new Complex(mps.magnetStrength[i][0], mps.magnetStrength[i][1]);
                count++;
            }
        }

        gravity = new Complex(mps.gravity[0], mps.gravity[1]);
        friction = new Complex(mps.friction[0], mps.friction[1]);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    //orbit
    public MagneticPendulum(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, MagneticPendulumSettings mps) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        pendulum = new Complex(mps.pendulum[0], mps.pendulum[1]);

        height_squared = mps.height * mps.height;
        stepsize = new Complex(mps.stepsize, mps.stepsize_im);
        stepsize_squared = stepsize.square();

        Predicate<double[]> isEnabled = (double[] strength) -> strength[0] != 0 || strength[1] != 0;

        int count = 0;
        for (int i = 0; i < mps.magnetStrength.length; i++) {
            if (isEnabled.test(mps.magnetStrength[i])) {
                count++;
            }
        }

        magnets = new Complex[count];
        strengths = new Complex[count];

        count = 0;
        for (int i = 0; i < mps.magnetStrength.length; i++) {
            if (isEnabled.test(mps.magnetStrength[i])) {
                magnets[count] = new Complex(mps.magnetLocation[i][0], mps.magnetLocation[i][1]);
                strengths[count] = new Complex(mps.magnetStrength[i][0], mps.magnetStrength[i][1]);
                count++;
            }
        }

        gravity = new Complex(mps.gravity[0], mps.gravity[1]);
        friction = new Complex(mps.friction[0], mps.friction[1]);
    }

    @Override
    public void function(Complex[] complex) {

        Complex acc_next = new Complex();
        for (int i = 0; i < magnets.length; i++) {
            Complex d = magnets[i].sub(complex[0]);
            double dist = Math.sqrt(d.norm_squared() + height_squared);
            acc_next.plus_mutable(d.times_mutable(strengths[i].divide(dist * dist * dist)));
        }

        acc_next.sub_mutable((complex[0].sub(pendulum)).times_mutable(gravity));
        acc_next.sub_mutable(complex[1].times(friction));

        complex[1].plus_mutable((acc_next.times(2).plus_mutable(complex[2].times(5).sub_mutable(complex[3]))).divide_mutable(6).times_mutable(stepsize));

        Complex dir = complex[1].times(stepsize).plus_mutable((acc_next.times(4).sub_mutable(complex[2])).divide_mutable(6).times_mutable(stepsize_squared));

        complex[4].plus_mutable(dir.norm());

        complex[0].plus_mutable(dir);
        complex[3].assign(complex[2]);

        complex[2].assign(acc_next);

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[5];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex();//vel
        complex[2] = new Complex(); //acc
        complex[3] = new Complex(); //acc_prev
        complex[4] = new Complex(); // len

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

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
            }

        }

        globalVars[magnetPendVariableId].assign(complex[4]);

        escaped = true;

        Object[] object = {iterations, complex[0], 0, zold, zold2, pixel, start, c0, pixel, complex[4]};
        iterationData = object;
        double out = out_color_algorithm.getResult(object);

        out = getFinalValueOut(out, complex[0]);

        if (outTrueColorAlgorithm != null) {
            setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
        }

        return out;

    }

    @Override
    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int converging_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                out_color_algorithm = new EscapeTimeMagneticPendulum();
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                out_color_algorithm = new BinaryDecompositionMagneticPendulum();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                out_color_algorithm = new BinaryDecomposition2MagneticPendulum();
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecompositionMagneticPendulum(magnets);
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecompositionMagneticPendulum(magnets);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(3);
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if (user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithmRootFindingMethod(outcoloring_formula, 0, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                } else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithmRootFindingMethod(user_outcoloring_conditions, user_outcoloring_condition_formula, 0, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                }
                break;

        }
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
    protected void StatisticFactory(StatisticsSettings sts, double[] plane_transform_center) {

        statisticIncludeEscaped = sts.statisticIncludeEscaped;
        statisticIncludeNotEscaped = sts.statisticIncludeNotEscaped;

        if (sts.statisticGroup == 1) {
            statistic = new UserStatisticColoringRootFindingMethod(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, 1e-8, plane_transform_center, globalVars, sts.useAverage, sts.user_statistic_init_value, sts.reductionFunction, sts.useIterations, sts.useSmoothing);
            return;
        }
        else if(sts.statisticGroup == 2) {
            statistic = new Equicontinuity(sts.statistic_intensity, sts.useSmoothing, sts.useAverage, 0, true, Math.log(1e-8), sts.equicontinuityDenominatorFactor, sts.equicontinuityInvertFactor, sts.equicontinuityDelta);
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
                statistic = new DiscreteLagrangianDescriptors(sts.statistic_intensity, sts.lagrangianPower, 0, sts.useSmoothing, sts.useAverage, true, Math.log(1e-8), sts.langNormType, sts.langNNorm);
                break;

        }
    }

    @Override
    public void setTrueColorAlgorithm(TrueColorSettings tcs) {

        if (tcs.trueColorOut) {

            if (tcs.trueColorOutMode == 0) {
                super.setTrueColorAlgorithm(tcs);
            } else {
                outTrueColorAlgorithm = new UserTrueColorAlgorithm(tcs.outTcComponent1, tcs.outTcComponent2, tcs.outTcComponent3, tcs.outTcColorSpace, 0, max_iterations, xCenter, yCenter, size, point, globalVars);
            }
        }

        if (tcs.trueColorIn) {
            if (tcs.trueColorInMode == 0) {
                super.setTrueColorAlgorithm(tcs);
            } else {
                inTrueColorAlgorithm = new UserTrueColorAlgorithm(tcs.inTcComponent1, tcs.inTcComponent2, tcs.inTcComponent3, tcs.inTcColorSpace, 0, max_iterations, xCenter, yCenter, size, point, globalVars);
            }
        }

    }
}
