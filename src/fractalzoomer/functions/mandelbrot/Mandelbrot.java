/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.Biomorphs;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.DefaultInitialValue;
import fractalzoomer.in_coloring_algorithms.CosMag;
import fractalzoomer.in_coloring_algorithms.DecompositionLike;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.fractal_options.DefaultPerturbation;
import fractalzoomer.fractal_options.InitialValue;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.main.MainWindow;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.Perturbation;
import fractalzoomer.fractal_options.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.VariableInitialValue;
import fractalzoomer.fractal_options.VariablePerturbation;
import fractalzoomer.functions.Julia;

import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.Squares2;
import fractalzoomer.in_coloring_algorithms.UserConditionalInColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.UserInColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.out_coloring_algorithms.Banded;
import fractalzoomer.out_coloring_algorithms.DistanceEstimator;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadius;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGrid;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;

import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphs;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTime;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGrid;
import fractalzoomer.out_coloring_algorithms.UserConditionalOutColorAlgorithm;
import fractalzoomer.out_coloring_algorithms.UserOutColorAlgorithm;
import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class Mandelbrot extends Julia {

    private MandelVariation type;
    private MandelVariation type2;
    private int special_alg;
    private boolean exterior_de;
    private double limit;
    private boolean inverse_dem;

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        if(perturbation) {
            if(variable_perturbation) {
                if(user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
                else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
            }
            else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        }
        else {
            pertur_val = new DefaultPerturbation();
        }

        if(init_value) {
            if(variable_init_value) {
                if(user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
                else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new DefaultInitialValue();
        }

        special_alg = 0;

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTime();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTime(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2(Math.log(bailout_squared), escaping_smooth_algorithm);
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
                    out_color_algorithm = new Biomorphs(bailout);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphs(Math.log(bailout_squared), bailout, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
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
                out_color_algorithm = new EscapeTimeAlgorithm1(2);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2();
                break;
            case MainWindow.DISTANCE_ESTIMATOR:
                out_color_algorithm = new DistanceEstimator();
                special_alg = 1;
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadius(Math.log(bailout_squared));
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGrid(Math.log(bailout_squared));
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGrid(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BANDED:
                out_color_algorithm = new Banded();
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if(user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithm(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithm(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                break;

        }

        this.exterior_de = exterior_de;
        this.inverse_dem = inverse_dem;

        if(exterior_de) {
            limit = (size / 33000) * exterior_de_factor;
        }

        switch (in_coloring_algorithm) {

            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations(max_iterations);
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(max_iterations);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike();
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm();
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag();
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared();
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared();
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm();
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares();
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2();
                break;
            case MainWindow.USER_INCOLORING_ALGORITHM:
                if(user_in_coloring_algorithm == 0) {
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                break;

        }

    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        special_alg = 0;

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTime();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTime(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2(Math.log(bailout_squared), escaping_smooth_algorithm);
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
                    out_color_algorithm = new Biomorphs(bailout);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphs(Math.log(bailout_squared), bailout, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
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
                out_color_algorithm = new EscapeTimeAlgorithm1(2);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2();
                break;
            case MainWindow.DISTANCE_ESTIMATOR:
                out_color_algorithm = new DistanceEstimator();
                special_alg = 1;
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadius(Math.log(bailout_squared));
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGrid(Math.log(bailout_squared));
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGrid(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BANDED:
                out_color_algorithm = new Banded();
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if(user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithm(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithm(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                break;

        }

        this.exterior_de = exterior_de;
        this.inverse_dem = inverse_dem;

        if(exterior_de) {
            limit = (size / 33000) * exterior_de_factor;
        }

        switch (in_coloring_algorithm) {

            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations(max_iterations);
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(max_iterations);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike();
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm();
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag();
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared();
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared();
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm();
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares();
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2();
                break;
            case MainWindow.USER_INCOLORING_ALGORITHM:
                if(user_in_coloring_algorithm == 0) {
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                break;

        }

    }

    //orbit
    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        if(perturbation) {
            if(variable_perturbation) {
                if(user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
                else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
            }
            else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        }
        else {
            pertur_val = new DefaultPerturbation();
        }

        if(init_value) {
            if(variable_init_value) {
                if(user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
                else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new DefaultInitialValue();
        }

    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

    }

    @Override
    protected void function(Complex[] complex) {

        type.getValue(complex[0]);
        complex[0].square_mutable().plus_mutable(complex[1]);
        type2.getValue(complex[0]);

    }

    private double mandel_2d_np_de_normal(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                double res = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                if(inverse_dem) {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : res;
                }
                else {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? res : -max_iterations;
                }
                
            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);
    }

    private double mandel_2d_np_de_dem(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex de = new Complex(0.7, 0.7);
        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                double res = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                if(inverse_dem) {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : res;
                }
                else {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? res : -max_iterations;
                }
            }

            de.times_mutable(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);
    } 

    private double mandel_2d_np_nde_normal(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);
    }

    private double mandel_2d_np_nde_dem(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex de = new Complex(0.7, 0.7);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                return out_color_algorithm.getResult(object);
            }

            de.times_mutable(complex[0]);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);
    }

    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {

        if(exterior_de) {
            if(special_alg == 0) {
                return mandel_2d_np_de_normal(pixel);
            }
            else {
                return mandel_2d_np_de_dem(pixel);
            }
        }
        else {
            if(special_alg == 0) {
                return mandel_2d_np_nde_normal(pixel);
            }
            else {
                return mandel_2d_np_nde_dem(pixel);
            }
        }

    }

    private double mandel_2d_p_de_normal(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);
        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                double res = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                if(inverse_dem) {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : res;
                }
                else {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? res : -max_iterations;
                }
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }

        }

        return max_iterations;
    }

    private double mandel_2d_p_de_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);
        Complex de = new Complex(0.7, 0.7);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                double res = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                if(inverse_dem) {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : res;
                }
                else {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? res : -max_iterations;
                }
            }
            de.times_mutable(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }

        }

        return max_iterations;
    }

    private double mandel_2d_p_nde_normal(Complex pixel) {

        int iterations = 0;

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
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }

        }

        return max_iterations;
    }

    private double mandel_2d_p_nde_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex de = new Complex(0.7, 0.7);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                return out_color_algorithm.getResult(object);
            }
            de.times_mutable(complex[0]);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }

        }

        return max_iterations;
    }

    @Override
    public double calculateFractalWithPeriodicity(Complex pixel) {

        if(exterior_de) {
            if(special_alg == 0) {
                return mandel_2d_p_de_normal(pixel);
            }
            else {
                return mandel_2d_p_de_dem(pixel);
            }
        }
        else {
            if(special_alg == 0) {
                return mandel_2d_p_nde_normal(pixel);
            }
            else {
                return mandel_2d_p_nde_dem(pixel);
            }
        }
    }

    private double julia_2d_p_de_normal(Complex pixel) {

        int iterations = 0;

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
        Complex dc = new Complex(1, 0);
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                double res = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                if(inverse_dem) {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : res;
                }
                else {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? res : -max_iterations;
                }
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }

        return max_iterations;
    }

    private double julia_2d_p_de_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex de = new Complex(0.7, 0.7);
        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                double res = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                if(inverse_dem) {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : res;
                }
                else {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? res : -max_iterations;
                }
            }
            de.times_mutable(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }

        return max_iterations;
    }

    private double julia_2d_p_nde_normal(Complex pixel) {

        int iterations = 0;

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
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }

        return max_iterations;
    }

    private double julia_2d_p_nde_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex de = new Complex(0.7, 0.7);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                return out_color_algorithm.getResult(object);
            }
            de.times_mutable(complex[0]);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }

        return max_iterations;
    }

    @Override
    public double calculateJuliaWithPeriodicity(Complex pixel) {

        if(exterior_de) {
            if(special_alg == 0) {
                return julia_2d_p_de_normal(pixel);
            }
            else {
                return julia_2d_p_de_dem(pixel);
            }
        }
        else {
            if(special_alg == 0) {
                return julia_2d_p_nde_normal(pixel);
            }
            else {
                return julia_2d_p_nde_dem(pixel);
            }
        }
    }

    private double julia_2d_np_de_normal(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex dc = new Complex(1, 0);
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                double res = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                if(inverse_dem) {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : res;
                }
                else {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? res : -max_iterations;
                }
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);
    }

    private double julia_2d_np_de_dem(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex de = new Complex(0.7, 0.7);
        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                double res = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                if(inverse_dem) {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : res;
                }
                else {
                    return (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? res : -max_iterations;
                }
            }
            de.times_mutable(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);
    }

    private double julia_2d_np_nde_normal(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);
    }

    private double julia_2d_np_nde_dem(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex de = new Complex(0.7, 0.7);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                return out_color_algorithm.getResult(object);
            }
            de.times_mutable(complex[0]);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);
    }

    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {

        if(exterior_de) {
            if(special_alg == 0) {
                return julia_2d_np_de_normal(pixel);
            }
            else {
                return julia_2d_np_de_dem(pixel);
            }
        }
        else {
            if(special_alg == 0) {
                return julia_2d_np_nde_normal(pixel);
            }
            else {
                return julia_2d_np_nde_dem(pixel);
            }
        }
    }

    private double[] mandel_3d_np_de_normal(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex dc = new Complex(1, 0);
        Complex start = new Complex(complex[0]);
        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                double result;
                if(inverse_dem) {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : temp;
                }
                else {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? temp : -max_iterations;
                }

                temp = result == -max_iterations ? -result : out_color_algorithm.transformResultToHeight(result);
                double[] array = {temp, result};
                return array;

            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;
    }

    private double[] mandel_3d_np_de_dem(Complex pixel) {
        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex de = new Complex(0.7, 0.7);
        Complex dc = new Complex(1, 0);
        double temp;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                temp = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                double result;
                if(inverse_dem) {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : temp;
                }
                else {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? temp : -max_iterations;
                }
                
                temp = result == -max_iterations ? -result : out_color_algorithm.transformResultToHeight(result);
                double[] array = {temp, result};
                return array;

            }
            de.times_mutable(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;
    }

    private double[] mandel_3d_np_nde_normal(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;

            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;
    }

    private double[] mandel_3d_np_nde_dem(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex de = new Complex(0.7, 0.7);
        double temp;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;

            }
            de.times_mutable(complex[0]);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;
    }

    @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {

        if(exterior_de) {
            if(special_alg == 0) {
                return mandel_3d_np_de_normal(pixel);
            }
            else {
                return mandel_3d_np_de_dem(pixel);
            }
        }
        else {
            if(special_alg == 0) {
                return mandel_3d_np_nde_normal(pixel);
            }
            else {
                return mandel_3d_np_nde_dem(pixel);
            }
        }

    }

    private double[] mandel_3d_p_de_normal(Complex pixel) {

        int iterations = 0;

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
        Complex dc = new Complex(1, 0);
        Complex start = new Complex(complex[0]);

        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                double result;
                if(inverse_dem) {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : temp;
                }
                else {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? temp : -max_iterations;
                }

                temp = result == -max_iterations ? -result : out_color_algorithm.transformResultToHeight(result);
                double[] array = {temp, result};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }

        }

        double[] array = {max_iterations, max_iterations};
        return array;
    }

    private double[] mandel_3d_p_de_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex de = new Complex(0.7, 0.7);
        Complex dc = new Complex(1, 0);

        double temp;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                temp = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                double result;
                if(inverse_dem) {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : temp;
                }
                else {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? temp : -max_iterations;
                }

                temp = result == -max_iterations ? -result : out_color_algorithm.transformResultToHeight(result);
                double[] array = {temp, result};
                return array;
            }
            de.times_mutable(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }

        }

        double[] array = {max_iterations, max_iterations};
        return array;
    }

    private double[] mandel_3d_p_nde_normal(Complex pixel) {

        int iterations = 0;

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

        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }

        }

        double[] array = {max_iterations, max_iterations};
        return array;
    }

    private double[] mandel_3d_p_nde_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex de = new Complex(0.7, 0.7);

        double temp;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            de.times_mutable(complex[0]);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }

        }

        double[] array = {max_iterations, max_iterations};
        return array;
    }

    @Override
    public double[] calculateFractal3DWithPeriodicity(Complex pixel) {

        if(exterior_de) {
            if(special_alg == 0) {
                return mandel_3d_p_de_normal(pixel);
            }
            else {
                return mandel_3d_p_de_dem(pixel);
            }
        }
        else {
            if(special_alg == 0) {
                return mandel_3d_p_nde_normal(pixel);
            }
            else {
                return mandel_3d_p_nde_dem(pixel);
            }
        }

    }

    private double[] julia_3d_p_de_normal(Complex pixel) {

        int iterations = 0;

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
        Complex dc = new Complex(1, 0);
        Complex start = new Complex(complex[0]);

        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                double result;
                if(inverse_dem) {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : temp;
                }
                else {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? temp : -max_iterations;
                }

                temp = result == -max_iterations ? -result : out_color_algorithm.transformResultToHeight(result);
                double[] array = {temp, result};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }
        }

        double[] array = {max_iterations, max_iterations};
        return array;
    }

    private double[] julia_3d_p_de_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex de = new Complex(0.7, 0.7);
        Complex dc = new Complex(1, 0);

        double temp;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                temp = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                double result;
                if(inverse_dem) {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : temp;
                }
                else {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? temp : -max_iterations;
                }

                temp = result == -max_iterations ? -result : out_color_algorithm.transformResultToHeight(result);
                double[] array = {temp, result};
                return array;
            }
            de.times_mutable(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }
        }

        double[] array = {max_iterations, max_iterations};
        return array;
    }

    private double[] julia_3d_p_nde_normal(Complex pixel) {

        int iterations = 0;

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

        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }
        }

        double[] array = {max_iterations, max_iterations};
        return array;
    }

    private double[] julia_3d_p_nde_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex de = new Complex(0.7, 0.7);

        double temp;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            de.times_mutable(complex[0]);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }
        }

        double[] array = {max_iterations, max_iterations};
        return array;
    }

    @Override
    public double[] calculateJulia3DWithPeriodicity(Complex pixel) {

        if(exterior_de) {
            if(special_alg == 0) {
                return julia_3d_p_de_normal(pixel);
            }
            else {
                return julia_3d_p_de_dem(pixel);
            }
        }
        else {
            if(special_alg == 0) {
                return julia_3d_p_nde_normal(pixel);
            }
            else {
                return julia_3d_p_nde_dem(pixel);
            }
        }
    }

    private double[] julia_3d_np_de_normal(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex dc = new Complex(1, 0);
        Complex start = new Complex(complex[0]);

        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                double result;
                if(inverse_dem) {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : temp;
                }
                else {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? temp : -max_iterations;
                }

                temp = result == -max_iterations ? -result : out_color_algorithm.transformResultToHeight(result);
                double[] array = {temp, result};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;
    }

    private double[] julia_3d_np_de_dem(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex de = new Complex(0.7, 0.7);
        Complex dc = new Complex(1, 0);

        double temp;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                temp = out_color_algorithm.getResult(object);

                double temp2 = complex[0].norm_squared();
                double temp3 = 0.5 * Math.log(temp2);

                double result;
                if(inverse_dem) {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? -max_iterations : temp;
                }
                else {
                    result = (temp2 * temp3 * temp3) / dc.norm_squared() > (limit * limit) ? temp : -max_iterations;
                }

                temp = result == -max_iterations ? -result : out_color_algorithm.transformResultToHeight(result);
                double[] array = {temp, result};
                return array;
            }
            de.times_mutable(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;
    }

    private double[] julia_3d_np_nde_normal(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;
    }

    private double[] julia_3d_np_nde_dem(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex de = new Complex(0.7, 0.7);

        double temp;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], de};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            de.times_mutable(complex[0]);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;
    }

    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {

        if(exterior_de) {
            if(special_alg == 0) {
                return julia_3d_np_de_normal(pixel);
            }
            else {
                return julia_3d_np_de_dem(pixel);
            }
        }
        else {
            if(special_alg == 0) {
                return julia_3d_np_nde_normal(pixel);
            }
            else {
                return julia_3d_np_nde_dem(pixel);
            }
        }

    }
    /*protected boolean mandelbrotOptimization(Complex pixel) {
    
     //if(!burning_ship) {
     double temp = pixel.getRe();
     double temp2 = pixel.getIm();
    
     double temp3 = temp2 * temp2;
     double temp6 = temp + 1.309;
    
     double temp4 = temp - 0.25;
     double q = temp4 * temp4 + temp3;
    
     if(q * (q + temp4) < 0.25 * temp3) { //Cardioid
     return true;
     }
    
     double temp5 = temp + 1;
    
     if(temp5 * temp5 + temp3 < 0.0625) { //bulb 2
     return true;
     }
    
     if(temp6 * temp6 + temp3 < 0.00345) { //bulb 4
     return true;
     }
    
     double temp7 = temp + 0.125;
     double temp8 = temp2 - 0.744;
     double temp10 = temp7 * temp7;
    
     if(temp10 + temp8 * temp8 < 0.0088) { //bulb 3 lower
     return true;
     }
    
    
     double temp9 = temp2 + 0.744;
    
     if(temp10 + temp9 * temp9 < 0.0088) { //bulb 3 upper
     return true;
     }
    
    
    
     return false;
    
     }
    
    
     public Object[] attractor(Complex z_in, Complex c, int period) {
    
    
     double epsilon = 1e-10;
     Complex zz = z_in;
    
     for (int j = 0; j < 64; ++j) {
     Complex z = new Complex(zz);
     Complex dz = new Complex(1, 0);
    
     for (int i = 0; i < period; ++i) {
     dz = z.times(dz).times(2);//2 * z * dz;
     z = z.square().plus(c);//z * z + c;
     }
    
     Complex zz1 = zz.sub((z.sub(zz)).divide(dz.sub(1)));//zz - (z  - zz) / (dz - 1);
    
     if (zz1.distance(zz) < epsilon) {
     Object[] object = {true, z, dz};
     return object;
    
     }
     zz = new Complex(zz1);
     }
    
     Object[] object = {false, null, null};
     return object;
    
     }
    
     public double interior_distance(Complex z0, Complex c, int per) {
     Complex z = new Complex(z0);
     Complex dz = new Complex(1, 0);
     Complex dzdz = new Complex();
     Complex dc = new Complex();
     Complex dcdz = new Complex();
    
     for (int p = 0; p < per; ++p) {
     dcdz = (z.times(dcdz).plus(dz.times(dc))).times(2);//2 * (z * dcdz + dz * dc);
     dc = z.times(dc).times(2).plus(1);//2 * z * dc + 1;
     dzdz = (dz.times(dz).plus(z.times(dzdz))).times(2);//2 * (dz * dz + z * dzdz);
     dz = z.times(dz).times(2);//2 * z * dz;
     z = z.square().plus(c);//z * z + c;
     }
    
     double norm_dz = dz.norm();
     double norm2 = (dcdz.plus(dzdz.times(dc))).divide(dz.r_sub(1)).norm();//cabs(dcdz + dzdz * dc / (1 - dz)
     return (1 - norm_dz * norm_dz) / norm2;//(1 - cabs(dz) * cabs(dz)) / cabs(dcdz + dzdz * dc / (1 - dz));
     }*/
}
