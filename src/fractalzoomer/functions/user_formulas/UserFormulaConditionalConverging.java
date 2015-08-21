/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.functions.user_formulas;

import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.DefaultInitialValue;
import fractalzoomer.fractal_options.DefaultPerturbation;
import fractalzoomer.fractal_options.InitialValue;
import fractalzoomer.fractal_options.Perturbation;
import fractalzoomer.fractal_options.VariableInitialValue;
import fractalzoomer.fractal_options.VariablePerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.in_coloring_algorithms.DecompositionLike;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.Biomorphs;
import fractalzoomer.in_coloring_algorithms.CosMag;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.Squares2;
import fractalzoomer.in_coloring_algorithms.UserConditionalInColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.UserInColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.out_coloring_algorithms.Banded;
import fractalzoomer.out_coloring_algorithms.ColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadiusNova;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGridNova;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2RootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphsNova;
import fractalzoomer.out_coloring_algorithms.SmoothColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGridNova;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.UserConditionalOutColorAlgorithmRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.UserOutColorAlgorithmRootFindingMethod;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaConditionalConverging extends Julia {

    protected double convergent_bailout;
    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode[] expr2;
    private Parser[] parser2;
    int iterations;

    public UserFormulaConditionalConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, String initial_value_user_formula, String[] user_formula_conditions, String[] user_formula_condition_formula, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        convergent_bailout = 1E-11;

        if(perturbation) {
            if(variable_perturbation) {
                pertur_val = new VariablePerturbation(perturbation_vals[0], perturbation_vals[1], perturbation_user_formula);
            }
            else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        }
        else {
            pertur_val = new DefaultPerturbation(perturbation_vals[0], perturbation_vals[1]);
        }

        if(init_value) {
            if(variable_init_value) {
                init_val = new VariableInitialValue(initial_vals[0], initial_vals[1], initial_value_user_formula);
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new DefaultInitialValue(initial_vals[0], initial_vals[1]);
        }

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
                    out_color_algorithm = new Biomorphs(2);
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
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if(user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithmRootFindingMethod(outcoloring_formula, convergent_bailout);
                }
                else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithmRootFindingMethod(user_outcoloring_conditions, user_outcoloring_condition_formula, convergent_bailout);
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
                in_color_algorithm = new Squares2(max_iterations);
                break;
            case MainWindow.USER_INCOLORING_ALGORITHM:
                if(user_in_coloring_algorithm == 0) {
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations);
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

    }

    public UserFormulaConditionalConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, double[] rotation_vals, double[] rotation_center, String[] user_formula_conditions, String[] user_formula_condition_formula, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-11;

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
                convergent_bailout = 1E-7;
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-7;
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
                    out_color_algorithm = new Biomorphs(2);
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
                convergent_bailout = 1E-7;
                out_color_algorithm = new Banded();
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                convergent_bailout = 1E-7;
                if(user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithmRootFindingMethod(outcoloring_formula, convergent_bailout);
                }
                else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithmRootFindingMethod(user_outcoloring_conditions, user_outcoloring_condition_formula, convergent_bailout);
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
                in_color_algorithm = new Squares2(max_iterations);
                break;
            case MainWindow.USER_INCOLORING_ALGORITHM:
                if(user_in_coloring_algorithm == 0) {
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations);
                }
                else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations);
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

    }

    //orbit
    public UserFormulaConditionalConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, String initial_value_user_formula, String[] user_formula_conditions, String[] user_formula_condition_formula, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        if(perturbation) {
            if(variable_perturbation) {
                pertur_val = new VariablePerturbation(perturbation_vals[0], perturbation_vals[1], perturbation_user_formula);
            }
            else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        }
        else {
            pertur_val = new DefaultPerturbation(perturbation_vals[0], perturbation_vals[1]);
        }

        if(init_value) {
            if(variable_init_value) {
                init_val = new VariableInitialValue(initial_vals[0], initial_vals[1], initial_value_user_formula);
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new DefaultInitialValue(initial_vals[0], initial_vals[1]);
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

    }

    public UserFormulaConditionalConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, double[] rotation_vals, double[] rotation_center, String[] user_formula_conditions, String[] user_formula_condition_formula, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

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

    }

    @Override
    protected void function(Complex[] complex) {

        /* LEFT */
        if(parser[0].foundN()) {
            parser[0].setNvalue(new Complex(iterations, 0));
        }
        parser[0].setZvalue(complex[0]);

        if(parser[0].foundC()) {
            parser[0].setCvalue(complex[1]);
        }

        /* RIGHT */
        if(parser[1].foundN()) {
            parser[1].setNvalue(new Complex(iterations, 0));
        }
        parser[1].setZvalue(complex[0]);

        if(parser[1].foundC()) {
            parser[1].setCvalue(complex[1]);
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundN()) {
                parser2[0].setNvalue(new Complex(iterations, 0));
            }
            parser2[0].setZvalue(complex[0]);

            if(parser2[0].foundC()) {
                parser2[0].setCvalue(complex[1]);
            }

            complex[0] = expr2[0].getValue();
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundN()) {
                parser2[1].setNvalue(new Complex(iterations, 0));
            }
            parser2[1].setZvalue(complex[0]);

            if(parser2[1].foundC()) {
                parser2[1].setCvalue(complex[1]);
            }

            complex[0] = expr2[1].getValue();
        }
        else { //left == right
            if(parser2[2].foundN()) {
                parser2[2].setNvalue(new Complex(iterations, 0));
            }
            parser2[2].setZvalue(complex[0]);

            if(parser2[2].foundC()) {
                parser2[2].setCvalue(complex[1]);
            }

            complex[0] = expr2[2].getValue();
        }

    }

    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
        iterations = 0;
        double temp = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        if(parser[0].foundS()) {
            parser[0].setSvalue(new Complex(complex[0]));
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[0].foundS()) {
            parser2[0].setSvalue(new Complex(complex[0]));
        }

        if(parser2[1].foundS()) {
            parser2[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[2].foundS()) {
            parser2[2].setSvalue(new Complex(complex[0]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(new Complex());
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(new Complex());
        }

        if(parser2[0].foundP()) {
            parser2[0].setPvalue(new Complex());
        }

        if(parser2[1].foundP()) {
            parser2[1].setPvalue(new Complex());
        }

        if(parser2[2].foundP()) {
            parser2[2].setPvalue(new Complex());
        }

        for(; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(parser[0].foundP()) {
                parser[0].setPvalue(new Complex(zold));
            }

            if(parser[1].foundP()) {
                parser[1].setPvalue(new Complex(zold));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(new Complex(zold));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(new Complex(zold));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(new Complex(zold));
            }

        }

        Object[] object = {complex[0]};
        return in_color_algorithm.getResult(object);

    }

    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
        iterations = 0;
        double temp = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        if(parser[0].foundS()) {
            parser[0].setSvalue(new Complex(complex[0]));
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[0].foundS()) {
            parser2[0].setSvalue(new Complex(complex[0]));
        }

        if(parser2[1].foundS()) {
            parser2[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[2].foundS()) {
            parser2[2].setSvalue(new Complex(complex[0]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(new Complex());
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(new Complex());
        }

        if(parser2[0].foundP()) {
            parser2[0].setPvalue(new Complex());
        }

        if(parser2[1].foundP()) {
            parser2[1].setPvalue(new Complex());
        }

        if(parser2[2].foundP()) {
            parser2[2].setPvalue(new Complex());
        }

        for(; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(parser[0].foundP()) {
                parser[0].setPvalue(new Complex(zold));
            }

            if(parser[1].foundP()) {
                parser[1].setPvalue(new Complex(zold));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(new Complex(zold));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(new Complex(zold));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(new Complex(zold));
            }

        }

        Object[] object = {complex[0]};
        return in_color_algorithm.getResult(object);

    }

    @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {
        iterations = 0;
        double temp = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        if(parser[0].foundS()) {
            parser[0].setSvalue(new Complex(complex[0]));
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[0].foundS()) {
            parser2[0].setSvalue(new Complex(complex[0]));
        }

        if(parser2[1].foundS()) {
            parser2[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[2].foundS()) {
            parser2[2].setSvalue(new Complex(complex[0]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(new Complex());
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(new Complex());
        }

        if(parser2[0].foundP()) {
            parser2[0].setPvalue(new Complex());
        }

        if(parser2[1].foundP()) {
            parser2[1].setPvalue(new Complex());
        }

        if(parser2[2].foundP()) {
            parser2[2].setPvalue(new Complex());
        }

        double temp2;

        for(; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                double[] array = {40 * Math.log(out_color_algorithm.getResult3D(object) - 100799) - 100, out_color_algorithm.getResult(object)};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(parser[0].foundP()) {
                parser[0].setPvalue(new Complex(zold));
            }

            if(parser[1].foundP()) {
                parser[1].setPvalue(new Complex(zold));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(new Complex(zold));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(new Complex(zold));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(new Complex(zold));
            }

        }

        Object[] object = {complex[0]};
        temp2 = in_color_algorithm.getResult(object);
        double result = temp2 == max_iterations ? max_iterations : max_iterations + temp2 - 100820;
        double[] array = {40 * Math.log(result + 1) - 100, temp2};
        return array;

    }

    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
        iterations = 0;
        double temp = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        if(parser[0].foundS()) {
            parser[0].setSvalue(new Complex(complex[0]));
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[0].foundS()) {
            parser2[0].setSvalue(new Complex(complex[0]));
        }

        if(parser2[1].foundS()) {
            parser2[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[2].foundS()) {
            parser2[2].setSvalue(new Complex(complex[0]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(new Complex());
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(new Complex());
        }

        if(parser2[0].foundP()) {
            parser2[0].setPvalue(new Complex());
        }

        if(parser2[1].foundP()) {
            parser2[1].setPvalue(new Complex());
        }

        if(parser2[2].foundP()) {
            parser2[2].setPvalue(new Complex());
        }

        for(; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                double[] array = {40 * Math.log(out_color_algorithm.getResult3D(object) - 100799) - 100, out_color_algorithm.getResult(object)};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(parser[0].foundP()) {
                parser[0].setPvalue(new Complex(zold));
            }

            if(parser[1].foundP()) {
                parser[1].setPvalue(new Complex(zold));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(new Complex(zold));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(new Complex(zold));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(new Complex(zold));
            }

        }

        Object[] object = {complex[0]};
        double temp2 = in_color_algorithm.getResult(object);
        double result = temp2 == max_iterations ? max_iterations : max_iterations + temp2 - 100820;
        double[] array = {40 * Math.log(result + 1) - 100, temp2};
        return array;

    }

    @Override
    public void calculateFractalOrbit() {
        iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pertur_val.getPixel(init_val.getPixel(pixel_orbit)));
        complex[1] = new Complex(pixel_orbit);//c

        Complex temp = null;

        Complex zold = new Complex();

        if(parser[0].foundS()) {
            parser[0].setSvalue(new Complex(complex[0]));
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[0].foundS()) {
            parser2[0].setSvalue(new Complex(complex[0]));
        }

        if(parser2[1].foundS()) {
            parser2[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[2].foundS()) {
            parser2[2].setSvalue(new Complex(complex[0]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(new Complex());
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(new Complex());
        }

        if(parser2[0].foundP()) {
            parser2[0].setPvalue(new Complex());
        }

        if(parser2[1].foundP()) {
            parser2[1].setPvalue(new Complex());
        }

        if(parser2[2].foundP()) {
            parser2[2].setPvalue(new Complex());
        }

        for(; iterations < max_iterations; iterations++) {
            zold.assign(complex[0]);
            function(complex);

            if(parser[0].foundP()) {
                parser[0].setPvalue(new Complex(zold));
            }

            if(parser[1].foundP()) {
                parser[1].setPvalue(new Complex(zold));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(new Complex(zold));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(new Complex(zold));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(new Complex(zold));
            }

            temp = rotation.getPixel(complex[0], true);

            if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    @Override
    public void calculateJuliaOrbit() {
        iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(seed);//c

        Complex temp = null;

        Complex zold = new Complex();

        if(parser[0].foundS()) {
            parser[0].setSvalue(new Complex(complex[0]));
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[0].foundS()) {
            parser2[0].setSvalue(new Complex(complex[0]));
        }

        if(parser2[1].foundS()) {
            parser2[1].setSvalue(new Complex(complex[0]));
        }

        if(parser2[2].foundS()) {
            parser2[2].setSvalue(new Complex(complex[0]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(new Complex());
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(new Complex());
        }

        if(parser2[0].foundP()) {
            parser2[0].setPvalue(new Complex());
        }

        if(parser2[1].foundP()) {
            parser2[1].setPvalue(new Complex());
        }

        if(parser2[2].foundP()) {
            parser2[2].setPvalue(new Complex());
        }

        for(; iterations < max_iterations; iterations++) {
            zold.assign(complex[0]);
            function(complex);

            if(parser[0].foundP()) {
                parser[0].setPvalue(new Complex(zold));
            }

            if(parser[1].foundP()) {
                parser[1].setPvalue(new Complex(zold));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(new Complex(zold));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(new Complex(zold));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(new Complex(zold));
            }

            temp = rotation.getPixel(complex[0], true);

            if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }
}
