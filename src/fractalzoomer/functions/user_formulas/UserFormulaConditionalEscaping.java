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
package fractalzoomer.functions.user_formulas;

import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.DefaultInitialValue;
import fractalzoomer.fractal_options.DefaultPerturbation;
import fractalzoomer.fractal_options.InitialValue;
import fractalzoomer.fractal_options.Perturbation;
import fractalzoomer.fractal_options.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.VariableInitialValue;
import fractalzoomer.fractal_options.VariablePerturbation;
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
import fractalzoomer.out_coloring_algorithms.Banded;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.Biomorphs;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadius;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGrid;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;

import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphs;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTime;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGrid;
import fractalzoomer.out_coloring_algorithms.UserConditionalOutColorAlgorithm;
import fractalzoomer.out_coloring_algorithms.UserOutColorAlgorithm;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaConditionalEscaping extends Julia {

    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode[] expr2;
    private Parser[] parser2;
    private int iterations;
    private Complex[] vars;
    private Complex point;

    public UserFormulaConditionalEscaping(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String[] user_formula_conditions, String[] user_formula_condition_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

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
                    out_color_algorithm = new UserOutColorAlgorithm(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithm(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center);
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
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                break;

        }

        parser = new Parser[user_formula_conditions.length];
        expr = new ExpressionNode[user_formula_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_formula_conditions[i]);
        }

        parser2 = new Parser[user_formula_condition_formula.length];
        expr2 = new ExpressionNode[user_formula_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_formula_condition_formula[i]);
        }
        
        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public UserFormulaConditionalEscaping(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String[] user_formula_conditions, String[] user_formula_condition_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

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
                    out_color_algorithm = new UserOutColorAlgorithm(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithm(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center);
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
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations, xCenter, yCenter, size, plane_transform_center);
                }
                break;

        }

        parser = new Parser[user_formula_conditions.length];
        expr = new ExpressionNode[user_formula_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_formula_conditions[i]);
        }

        parser2 = new Parser[user_formula_condition_formula.length];
        expr2 = new ExpressionNode[user_formula_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_formula_condition_formula[i]);
        }
        
        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    //orbit
    public UserFormulaConditionalEscaping(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String[] user_formula_conditions, String[] user_formula_condition_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

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

        parser = new Parser[user_formula_conditions.length];
        expr = new ExpressionNode[user_formula_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_formula_conditions[i]);
        }

        parser2 = new Parser[user_formula_condition_formula.length];
        expr2 = new ExpressionNode[user_formula_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_formula_condition_formula[i]);
        }
        
        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public UserFormulaConditionalEscaping(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String[] user_formula_conditions, String[] user_formula_condition_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        parser = new Parser[user_formula_conditions.length];
        expr = new ExpressionNode[user_formula_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_formula_conditions[i]);
        }

        parser2 = new Parser[user_formula_condition_formula.length];
        expr2 = new ExpressionNode[user_formula_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_formula_condition_formula[i]);
        }
        
        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    @Override
    protected void function(Complex[] complex) {

        /* LEFT */
        if(parser[0].foundN()) {
            parser[0].setNvalue(new Complex(iterations, 0));
        }

        if(parser[0].foundZ()) {
            parser[0].setZvalue(complex[0]);
        }

        if(parser[0].foundC()) {
            parser[0].setCvalue(complex[1]);
        }

        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser[0].foundVar(i)) {
                parser[0].setVarsvalue(i, vars[i]);
            }
        }

        /* RIGHT */
        if(parser[1].foundN()) {
            parser[1].setNvalue(new Complex(iterations, 0));
        }

        if(parser[1].foundZ()) {
            parser[1].setZvalue(complex[0]);
        }

        if(parser[1].foundC()) {
            parser[1].setCvalue(complex[1]);
        }

        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser[1].foundVar(i)) {
                parser[1].setVarsvalue(i, vars[i]);
            }
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundN()) {
                parser2[0].setNvalue(new Complex(iterations, 0));
            }

            if(parser2[0].foundZ()) {
                parser2[0].setZvalue(complex[0]);
            }

            if(parser2[0].foundC()) {
                parser2[0].setCvalue(complex[1]);
            }

            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[0].foundVar(i)) {
                    parser2[0].setVarsvalue(i, vars[i]);
                }
            }

            complex[0] = expr2[0].getValue();
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundN()) {
                parser2[1].setNvalue(new Complex(iterations, 0));
            }

            if(parser2[1].foundZ()) {
                parser2[1].setZvalue(complex[0]);
            }

            if(parser2[1].foundC()) {
                parser2[1].setCvalue(complex[1]);
            }
            
            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[1].foundVar(i)) {
                    parser2[1].setVarsvalue(i, vars[i]);
                }
            }

            complex[0] = expr2[1].getValue();
        }
        else if(result == 0) { //left == right
            if(parser2[2].foundN()) {
                parser2[2].setNvalue(new Complex(iterations, 0));
            }

            if(parser2[2].foundZ()) {
                parser2[2].setZvalue(complex[0]);
            }

            if(parser2[2].foundC()) {
                parser2[2].setCvalue(complex[1]);
            }
            
            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[2].foundVar(i)) {
                    parser2[2].setVarsvalue(i, vars[i]);
                }
            }

            complex[0] = expr2[2].getValue();
        }

    }

    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {

        iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);

    }

    @Override
    public double calculateFractalWithPeriodicity(Complex pixel) {

        iterations = 0;

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

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }

        }

        return max_iterations;

    }

    @Override
    public double[] calculateFractal3DWithPeriodicity(Complex pixel) {

        iterations = 0;

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

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        double temp;

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

            setVariables(zold, zold2);

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

        iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        double temp;

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

            setVariables(zold, zold2);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;

    }

    @Override
    public void calculateFractalOrbit() {
        iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel_orbit)));//z
        complex[1] = new Complex(pixel_orbit);//c

        Complex temp = null;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            temp = rotation.rotateInverse(complex[0]);

            if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    @Override
    public double calculateJuliaWithPeriodicity(Complex pixel) {
        iterations = 0;

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

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }

        return max_iterations;
    }

    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
        iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);

    }

    @Override
    public double[] calculateJulia3DWithPeriodicity(Complex pixel) {
        iterations = 0;

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

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        double temp;

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

            setVariables(zold, zold2);

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
        iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        double temp;

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

            setVariables(zold, zold2);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;

    }

    @Override
    public void calculateJuliaOrbit() {
        iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(seed);//c

        Complex temp = null;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            temp = rotation.rotateInverse(complex[0]);

            if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    @Override
    public Complex iterateFractalDomain(Complex pixel) {

        iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

        }

        return complex[0];

    }

    @Override
    public Complex iterateJuliaDomain(Complex pixel) {
        iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);
        
        vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {

            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

        }

        return complex[0];

    }

    private void setVariables(Complex zold, Complex zold2) {

        if(parser[0].foundP()) {
            parser[0].setPvalue(zold);
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(zold);
        }

        if(parser2[0].foundP()) {
            parser2[0].setPvalue(zold);
        }

        if(parser2[1].foundP()) {
            parser2[1].setPvalue(zold);
        }

        if(parser2[2].foundP()) {
            parser2[2].setPvalue(zold);
        }

        if(parser[0].foundPP()) {
            parser[0].setPPvalue(zold2);
        }

        if(parser[1].foundPP()) {
            parser[1].setPPvalue(zold2);
        }

        if(parser2[0].foundPP()) {
            parser2[0].setPPvalue(zold2);
        }

        if(parser2[1].foundPP()) {
            parser2[1].setPPvalue(zold2);
        }

        if(parser2[2].foundPP()) {
            parser2[2].setPPvalue(zold2);
        }
    }

    private void setInitVariables(Complex start, Complex zold, Complex zold2) {

        if(parser[0].foundS()) {
            parser[0].setSvalue(start);
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(start);
        }

        if(parser2[0].foundS()) {
            parser2[0].setSvalue(start);
        }

        if(parser2[1].foundS()) {
            parser2[1].setSvalue(start);
        }

        if(parser2[2].foundS()) {
            parser2[2].setSvalue(start);
        }

        if(parser[0].foundMaxn()) {
            parser[0].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if(parser[1].foundMaxn()) {
            parser[1].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if(parser2[0].foundMaxn()) {
            parser2[0].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if(parser2[1].foundMaxn()) {
            parser2[1].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if(parser2[2].foundMaxn()) {
            parser2[2].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(zold);
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(zold);
        }

        if(parser2[0].foundP()) {
            parser2[0].setPvalue(zold);
        }

        if(parser2[1].foundP()) {
            parser2[1].setPvalue(zold);
        }

        if(parser2[2].foundP()) {
            parser2[2].setPvalue(zold);
        }

        if(parser[0].foundPP()) {
            parser[0].setPPvalue(zold2);
        }

        if(parser[1].foundPP()) {
            parser[1].setPPvalue(zold2);
        }

        if(parser2[0].foundPP()) {
            parser2[0].setPPvalue(zold2);
        }

        if(parser2[1].foundPP()) {
            parser2[1].setPPvalue(zold2);
        }

        if(parser2[2].foundPP()) {
            parser2[2].setPPvalue(zold2);
        }

        Complex c_center = new Complex(xCenter, yCenter);
        
        if(parser[0].foundCenter()) {
            parser[0].setCentervalue(c_center);
        }

        if(parser[1].foundCenter()) {
            parser[1].setCentervalue(c_center);
        }

        if(parser2[0].foundCenter()) {
            parser2[0].setCentervalue(c_center);
        }

        if(parser2[1].foundCenter()) {
            parser2[1].setCentervalue(c_center);
        }

        if(parser2[2].foundCenter()) {
            parser2[2].setCentervalue(c_center);
        }

        Complex c_size = new Complex(size, 0);
        
        if(parser[0].foundSize()) {
            parser[0].setSizevalue(c_size);
        }

        if(parser[1].foundSize()) {
            parser[1].setSizevalue(c_size);
        }

        if(parser2[0].foundSize()) {
            parser2[0].setSizevalue(c_size);
        }

        if(parser2[1].foundSize()) {
            parser2[1].setSizevalue(c_size);
        }

        if(parser2[2].foundSize()) {
            parser2[2].setSizevalue(c_size);
        }
        
        if(parser[0].foundPoint()) {
            parser[0].setPointvalue(point);
        }
        
        if(parser[1].foundPoint()) {
            parser[1].setPointvalue(point);
        }
        
        if(parser2[0].foundPoint()) {
            parser2[0].setPointvalue(point);
        }
        
        if(parser2[1].foundPoint()) {
            parser2[1].setPointvalue(point);
        }
        
        if(parser2[2].foundPoint()) {
            parser2[2].setPointvalue(point);
        }
    }
}
