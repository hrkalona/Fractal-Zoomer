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
package fractalzoomer.functions.formulas.coupled;

import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.Biomorphs;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.Coupling;
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
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.main.MainWindow;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.fractal_options.Perturbation;
import fractalzoomer.fractal_options.SimpleCoupling;
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
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadius;
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
public class CoupledMandelbrotBurningShip extends Julia {
    private Coupling coupler;

    public CoupledMandelbrotBurningShip(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, boolean[] user_outcoloring_special_color, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean[] user_incoloring_special_color, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        coupler = new SimpleCoupling(0.1);
        
        if(perturbation) {
            if(variable_perturbation) {
                if(user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula);
                }
                else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula);
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
                    init_val = new VariableInitialValue(initial_value_user_formula);
                }
                else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula);
                }
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new InitialValue(0, 0);
        }

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
                    out_color_algorithm = new UserOutColorAlgorithm(outcoloring_formula, bailout, max_iterations);
                }
                else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithm(user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, bailout, max_iterations);
                }
                break;

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
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, max_iterations);
                }
                break;

        }

    }

    public CoupledMandelbrotBurningShip(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, boolean[] user_outcoloring_special_color, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean[] user_incoloring_special_color, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        coupler = new SimpleCoupling(0.1);
        
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
                    out_color_algorithm = new UserOutColorAlgorithm(outcoloring_formula, bailout, max_iterations);
                }
                else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithm(user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, bailout, max_iterations);
                }
                break;

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
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, max_iterations);
                }
                break;

        }

    }

    //orbit
    public CoupledMandelbrotBurningShip(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        coupler = new SimpleCoupling(0.1);
        
        if(perturbation) {
            if(variable_perturbation) {
                if(user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula);
                }
                else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula);
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
                    init_val = new VariableInitialValue(initial_value_user_formula);
                }
                else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula);
                }
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new InitialValue(0, 0);
        }

    }

    public CoupledMandelbrotBurningShip(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter); 

        coupler = new SimpleCoupling(0.1);
        
    }

    @Override
    protected void function(Complex[] complex) {

        Complex a1 = complex[0].square().plus_mutable(complex[1]);
        Complex a2 = complex[2].abs().square().plus_mutable(complex[1]);
        
        Complex[] res = coupler.couple(a1, a2, 0);
        complex[0] = res[0];
        complex[2] = res[1];;
        
    }
    
    @Override
    public double calculateFractalWithPeriodicity(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
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
    
    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        return in_color_algorithm.getResult(object);

       
    }
    
    @Override
    public double[] calculateFractal3DWithPeriodicity(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        double temp;

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
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

    @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        double temp;

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;

            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;

    }
    
    @Override
    public void calculateFractalOrbit() {
        int iterations = 0;

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pertur_val.getPixel(init_val.getPixel(pixel_orbit)));//z
        complex[1] = new Complex(pixel_orbit);//c
        complex[2] = new Complex();//z2

        Complex temp = null;

        for(; iterations < max_iterations; iterations++) {
            function(complex);
            temp = rotation.getPixel(complex[0], true);

            if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }
    
    @Override
    public double calculateJuliaWithPeriodicity(Complex pixel) {
        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
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

    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
        int iterations = 0;

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        return in_color_algorithm.getResult(object);

    }
    
    @Override
    public double[] calculateJulia3DWithPeriodicity(Complex pixel) {
        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        double temp;

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
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

    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
        int iterations = 0;

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        double temp;

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;

    }

    @Override
    public void calculateJuliaOrbit() {
        int iterations = 0;

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();//z2
    

        Complex temp = null;

        for(; iterations < max_iterations; iterations++) {
            function(complex);
            temp = rotation.getPixel(complex[0], true);

            if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }
    
    @Override
    public Complex iterateFractalDomain(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();//z2

        for(; iterations < max_iterations; iterations++) {

            function(complex);

        }

        return complex[0];
       
    }
    
    @Override
    public Complex iterateJuliaDomain(Complex pixel) {
        int iterations = 0;

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();//z2

        for(; iterations < max_iterations; iterations++) {
  
            function(complex);

        }

        return complex[0];

    }
}