/*
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
package fractalzoomer.functions.magnet;

import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.iteration_statistics.CosArgDivideInverseNorm;
import fractalzoomer.fractal_options.iteration_statistics.CosArgDivideNormAverage;
import fractalzoomer.fractal_options.iteration_statistics.CurvatureAverage;
import fractalzoomer.fractal_options.iteration_statistics.StripeAverage;
import fractalzoomer.fractal_options.iteration_statistics.UserStatisticColoring;
import fractalzoomer.fractal_options.iteration_statistics.UserStatisticColoringMagnetConverging;
import fractalzoomer.functions.Julia;
import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.in_coloring_algorithms.CosMag;
import fractalzoomer.in_coloring_algorithms.DecompositionLike;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.Squares2;
import fractalzoomer.in_coloring_algorithms.UserConditionalInColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.UserInColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.Banded;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2Magnet;
import fractalzoomer.out_coloring_algorithms.BinaryDecompositionMagnet;
import fractalzoomer.out_coloring_algorithms.BiomorphsMagnet;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadiusMagnet;
import fractalzoomer.out_coloring_algorithms.EscapeTimeFieldLines2Magnet;
import fractalzoomer.out_coloring_algorithms.EscapeTimeFieldLinesMagnet;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGridMagnet;
import fractalzoomer.out_coloring_algorithms.EscapeTimeMagnet;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecompositionMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphsMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeAlgorithm2Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeColorDecompositionMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeEscapeRadiusMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeFieldLines2Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeFieldLinesMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGaussianInteger3Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGaussianInteger4Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGaussianIntegerMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGridMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimePlusImMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimePlusReMagnet;
import fractalzoomer.out_coloring_algorithms.UserConditionalOutColorAlgorithmMagnet;
import fractalzoomer.out_coloring_algorithms.UserOutColorAlgorithmMagnet;
import fractalzoomer.utils.ColorAlgorithm;
import java.util.ArrayList;

/**
 *
 * @author kaloch
 */
public abstract class MagnetType extends Julia {

    protected double convergent_bailout;
    protected boolean converged;

    public MagnetType(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

    }

    public MagnetType(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

    }

    //orbit
    public MagnetType(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public MagnetType(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

    }

    @Override
    protected double getPeriodSize() {

        return 1e-26;

    }

    @Override
    public double calculateFractalWithPeriodicity(Complex pixel) {
        int iterations = 0;
        Boolean temp1, temp2;
        double temp4;
        
        converged = false;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
            temp2 = bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start);
            if (temp1 || temp2) {
                escaped = true;
                converged = temp1;
                Object[] object = {iterations, complex[0], temp2, temp4, zold, zold2, complex[1], start};
                double out = out_color_algorithm.getResult(object);
                
                out = getFinalValueOut(out);
                
                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
            
            if(statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
        int iterations = 0;
        Boolean temp1, temp2;
        double temp4;

        if (trap != null) {
            trap.initialize();
        }
        
        converged = false;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
            temp2 = bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start);
            if (temp1 || temp2) {
                escaped = true;
                converged = temp1;
                Object[] object = {iterations, complex[0], temp2, temp4, zold, zold2, complex[1], start};
                double out = out_color_algorithm.getResult(object);
                
                out = getFinalValueOut(out);
                
                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
            
            if(statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);
        
        in = getFinalValueIn(in);
        
        return in;

    }

    @Override
    public double calculateJuliaWithPeriodicity(Complex pixel) {
        int iterations = 0;
        Boolean temp1, temp2;
        double temp4;
        
        converged = false;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
            temp2 = bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start);
            if (temp1 || temp2) {
                escaped = true;
                converged = temp1;
                Object[] object = {iterations, complex[0], temp2, temp4, zold, zold2, complex[1], start};
                double out = out_color_algorithm.getResult(object);
                
                out = getFinalValueOut(out);
                
                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
            
            if(statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
        int iterations = 0;
        Boolean temp1, temp2;
        double temp4;
        
        converged = false;

        if (trap != null) {
            trap.initialize();
        }

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
            temp2 = bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start);
            if (temp1 || temp2) {
                escaped = true;
                converged = temp1;
                Object[] object = {iterations, complex[0], temp2, temp4, zold, zold2, complex[1], start};
                double out = out_color_algorithm.getResult(object);
                
                out = getFinalValueOut(out);
                
                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
            
            if(statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);
        
        in = getFinalValueIn(in);
        
        return in;

    }

    @Override
    public double getConvergentBailout() {

        return Math.sqrt(convergent_bailout);

    }

    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int escaping_smooth_algorithm, int converging_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeMagnet();
                } else {
                    out_color_algorithm = new SmoothEscapeTimeMagnet(log_bailout_squared, Math.log(convergent_bailout), escaping_smooth_algorithm, converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if (!smoothing) {
                    out_color_algorithm = new BinaryDecompositionMagnet();
                } else {
                    out_color_algorithm = new SmoothBinaryDecompositionMagnet(log_bailout_squared, Math.log(convergent_bailout), escaping_smooth_algorithm, converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if (!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2Magnet();
                } else {
                    out_color_algorithm = new SmoothBinaryDecomposition2Magnet(log_bailout_squared, Math.log(convergent_bailout), escaping_smooth_algorithm, converging_smooth_algorithm);
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimePlusRe();
                } else {
                    out_color_algorithm = new SmoothEscapeTimePlusReMagnet(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimePlusIm();
                } else {
                    out_color_algorithm = new SmoothEscapeTimePlusImMagnet(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if (!smoothing) {
                    out_color_algorithm = new BiomorphsMagnet(bailout);
                } else {
                    out_color_algorithm = new SmoothBiomorphsMagnet(log_bailout_squared, Math.log(convergent_bailout), bailout, escaping_smooth_algorithm, converging_smooth_algorithm);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeColorDecomposition();
                } else {
                    out_color_algorithm = new SmoothEscapeTimeColorDecompositionMagnet(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger();
                } else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianIntegerMagnet(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger3();
                } else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianInteger3Magnet(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger4();
                } else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianInteger4Magnet(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(4);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeAlgorithm2();
                } else {
                    out_color_algorithm = new SmoothEscapeTimeAlgorithm2Magnet(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeEscapeRadiusMagnet(log_bailout_squared);
                } else {
                    out_color_algorithm = new SmoothEscapeTimeEscapeRadiusMagnet(log_bailout_squared, Math.log(convergent_bailout), escaping_smooth_algorithm, converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeGridMagnet(log_bailout_squared);
                } else {
                    out_color_algorithm = new SmoothEscapeTimeGridMagnet(log_bailout_squared, Math.log(convergent_bailout), escaping_smooth_algorithm, converging_smooth_algorithm);
                }
                break;
            case MainWindow.BANDED:
                out_color_algorithm = new Banded();
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLinesMagnet(log_bailout_squared);
                } else {
                    out_color_algorithm = new SmoothEscapeTimeFieldLinesMagnet(log_bailout_squared, Math.log(convergent_bailout), escaping_smooth_algorithm, converging_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES2:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLines2Magnet(log_bailout_squared);
                } else {
                    out_color_algorithm = new SmoothEscapeTimeFieldLines2Magnet(log_bailout_squared, Math.log(convergent_bailout), escaping_smooth_algorithm, converging_smooth_algorithm);
                }
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if (user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithmMagnet(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                } else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithmMagnet(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
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
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations, xCenter, yCenter, size, plane_transform_center, bailout, globalVars);
                } else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations, xCenter, yCenter, size, plane_transform_center, bailout, globalVars);
                }
                break;

        }

    }
    
    @Override
    public int type() {
        
        return MainWindow.ESCAPING_AND_CONVERGING;
        
    }
    
    @Override
    protected void StatisticFactory(StatisticsSettings sts, double[] plane_transform_center) {
        
        statisticIncludeEscaped = sts.statisticIncludeEscaped;
        statisticIncludeNotEscaped = sts.statisticIncludeNotEscaped;
        
        if(sts.statisticGroup == 1) {
            if(sts.statistic_escape_type == MainWindow.ESCAPING) {
                statistic = new UserStatisticColoring(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, bailout, plane_transform_center, globalVars, sts.useAverage);
            }
            else {
                statistic = new UserStatisticColoringMagnetConverging(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, convergent_bailout, bailout, plane_transform_center, globalVars, sts.useAverage);
            }         
            return;
        }
        
        switch (sts.statistic_type) {
            case MainWindow.STRIPE_AVERAGE:
                statistic = new StripeAverage(sts.statistic_intensity, sts.stripeAvgStripeDensity, log_bailout_squared);
                break;
            case MainWindow.CURVATURE_AVERAGE:
                statistic = new CurvatureAverage(sts.statistic_intensity, log_bailout_squared);
                break;
            case MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE:
                statistic = new CosArgDivideNormAverage(sts.statistic_intensity, sts.cosArgStripeDensity, log_bailout_squared);
                break;
            case MainWindow.COS_ARG_DIVIDE_INVERSE_NORM:
                statistic = new CosArgDivideInverseNorm(sts.statistic_intensity, sts.cosArgStripeDensity, sts.StripeDenominatorFactor);
                break;
               
            
        }
    }
    
    @Override
    protected double getStatistic(double result) {
        
        if((converged && statistic.getType() == MainWindow.ESCAPING) || (!converged && statistic.getType() == MainWindow.CONVERGING)) {
            return result;
        }
        
        if(Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return result;
        }
        
        return result < 0 ? result - statistic.getValue() : result + statistic.getValue();
    }

}
