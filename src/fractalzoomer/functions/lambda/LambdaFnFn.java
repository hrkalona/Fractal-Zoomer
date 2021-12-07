/*
 * Copyright (C) 2020 hrkalona2
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
package fractalzoomer.functions.lambda;

import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.app_settings.LambdaFnFnSettings;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class LambdaFnFn extends Julia {

    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode[] expr2;
    private Parser[] parser2;
    private Complex point;

    public LambdaFnFn(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, LambdaFnFnSettings lfns) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        if (init_value) {
            if (variable_init_value) {
                if (user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            } else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        } else {
            init_val = new DefaultInitialValue();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        parser = new Parser[lfns.lambda_formula_conditions.length];
        expr = new ExpressionNode[lfns.lambda_formula_conditions.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(lfns.lambda_formula_conditions[i]);
        }

        parser2 = new Parser[lfns.lambda_formula_condition_formula.length];
        expr2 = new ExpressionNode[lfns.lambda_formula_condition_formula.length];

        for (int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(lfns.lambda_formula_condition_formula[i]);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);
    }

    public LambdaFnFn(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, LambdaFnFnSettings lfns, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        parser = new Parser[lfns.lambda_formula_conditions.length];
        expr = new ExpressionNode[lfns.lambda_formula_conditions.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(lfns.lambda_formula_conditions[i]);
        }

        parser2 = new Parser[lfns.lambda_formula_condition_formula.length];
        expr2 = new ExpressionNode[lfns.lambda_formula_condition_formula.length];

        for (int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(lfns.lambda_formula_condition_formula[i]);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();
    }

    //orbit
    public LambdaFnFn(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, LambdaFnFnSettings lfns) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        if (init_value) {
            if (variable_init_value) {
                if (user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            } else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        } else {
            init_val = new DefaultInitialValue();
        }

        parser = new Parser[lfns.lambda_formula_conditions.length];
        expr = new ExpressionNode[lfns.lambda_formula_conditions.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(lfns.lambda_formula_conditions[i]);
        }

        parser2 = new Parser[lfns.lambda_formula_condition_formula.length];
        expr2 = new ExpressionNode[lfns.lambda_formula_condition_formula.length];

        for (int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(lfns.lambda_formula_condition_formula[i]);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public LambdaFnFn(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, LambdaFnFnSettings lfns, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        parser = new Parser[lfns.lambda_formula_conditions.length];
        expr = new ExpressionNode[lfns.lambda_formula_conditions.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(lfns.lambda_formula_conditions[i]);
        }

        parser2 = new Parser[lfns.lambda_formula_condition_formula.length];
        expr2 = new ExpressionNode[lfns.lambda_formula_condition_formula.length];

        for (int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(lfns.lambda_formula_condition_formula[i]);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    @Override
    public void function(Complex[] complex) {

        /* LEFT */
        if (parser[0].foundN()) {
            parser[0].setNvalue(new Complex(iterations, 0));
        }

        if (parser[0].foundZ()) {
            parser[0].setZvalue(complex[0]);
        }

        if (parser[0].foundC()) {
            parser[0].setCvalue(complex[1]);
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser[0].foundVar(i)) {
                parser[0].setVarsvalue(i, globalVars[i]);
            }
        }

        /* RIGHT */
        if (parser[1].foundN()) {
            parser[1].setNvalue(new Complex(iterations, 0));
        }

        if (parser[1].foundZ()) {
            parser[1].setZvalue(complex[0]);
        }

        if (parser[1].foundC()) {
            parser[1].setCvalue(complex[1]);
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser[1].foundVar(i)) {
                parser[1].setVarsvalue(i, globalVars[i]);
            }
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if (result == -1) { // left > right
            if (parser2[0].foundN()) {
                parser2[0].setNvalue(new Complex(iterations, 0));
            }

            if (parser2[0].foundZ()) {
                parser2[0].setZvalue(complex[0]);
            }

            if (parser2[0].foundC()) {
                parser2[0].setCvalue(complex[1]);
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser2[0].foundVar(i)) {
                    parser2[0].setVarsvalue(i, globalVars[i]);
                }
            }

            complex[0] = expr2[0].getValue().times_mutable(complex[1]);
        } else if (result == 1) { // right > left
            if (parser2[1].foundN()) {
                parser2[1].setNvalue(new Complex(iterations, 0));
            }

            if (parser2[1].foundZ()) {
                parser2[1].setZvalue(complex[0]);
            }

            if (parser2[1].foundC()) {
                parser2[1].setCvalue(complex[1]);
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser2[1].foundVar(i)) {
                    parser2[1].setVarsvalue(i, globalVars[i]);
                }
            }

            complex[0] = expr2[1].getValue().times_mutable(complex[1]);
        } else if (result == 0) { //left == right
            if (parser2[2].foundN()) {
                parser2[2].setNvalue(new Complex(iterations, 0));
            }

            if (parser2[2].foundZ()) {
                parser2[2].setZvalue(complex[0]);
            }

            if (parser2[2].foundC()) {
                parser2[2].setCvalue(complex[1]);
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser2[2].foundVar(i)) {
                    parser2[2].setVarsvalue(i, globalVars[i]);
                }
            }

            complex[0] = expr2[2].getValue().times_mutable(complex[1]);
        }

        setVariables(zold, zold2);

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));;//z
        complex[1] = new Complex(pixel);//c

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        setInitVariables(start, zold, zold2, c0, pixel);

        return complex;

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));//z
        complex[1] = new Complex(seed);//c

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        setInitVariables(start, zold, zold2, c0, pixel);

        return complex;

    }

    private void setVariables(Complex zold, Complex zold2) {

        if (parser[0].foundP()) {
            parser[0].setPvalue(zold);
        }

        if (parser[1].foundP()) {
            parser[1].setPvalue(zold);
        }

        if (parser2[0].foundP()) {
            parser2[0].setPvalue(zold);
        }

        if (parser2[1].foundP()) {
            parser2[1].setPvalue(zold);
        }

        if (parser2[2].foundP()) {
            parser2[2].setPvalue(zold);
        }

        if (parser[0].foundPP()) {
            parser[0].setPPvalue(zold2);
        }

        if (parser[1].foundPP()) {
            parser[1].setPPvalue(zold2);
        }

        if (parser2[0].foundPP()) {
            parser2[0].setPPvalue(zold2);
        }

        if (parser2[1].foundPP()) {
            parser2[1].setPPvalue(zold2);
        }

        if (parser2[2].foundPP()) {
            parser2[2].setPPvalue(zold2);
        }
    }

    private void setInitVariables(Complex start, Complex zold, Complex zold2, Complex c0, Complex pixel) {

        if (parser[0].foundPixel()) {
            parser[0].setPixelvalue(pixel);
        }

        if (parser[1].foundPixel()) {
            parser[1].setPixelvalue(pixel);
        }

        if (parser2[0].foundPixel()) {
            parser2[0].setPixelvalue(pixel);
        }

        if (parser2[1].foundPixel()) {
            parser2[1].setPixelvalue(pixel);
        }

        if (parser2[2].foundPixel()) {
            parser2[2].setPixelvalue(pixel);
        }

        if (parser[0].foundC0()) {
            parser[0].setC0value(c0);
        }

        if (parser[1].foundC0()) {
            parser[1].setC0value(c0);
        }

        if (parser2[0].foundC0()) {
            parser2[0].setC0value(c0);
        }

        if (parser2[1].foundC0()) {
            parser2[1].setC0value(c0);
        }

        if (parser2[2].foundC0()) {
            parser2[2].setC0value(c0);
        }


        if (parser[0].foundS()) {
            parser[0].setSvalue(start);
        }

        if (parser[1].foundS()) {
            parser[1].setSvalue(start);
        }

        if (parser2[0].foundS()) {
            parser2[0].setSvalue(start);
        }

        if (parser2[1].foundS()) {
            parser2[1].setSvalue(start);
        }

        if (parser2[2].foundS()) {
            parser2[2].setSvalue(start);
        }

        if (parser[0].foundMaxn()) {
            parser[0].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser[1].foundMaxn()) {
            parser[1].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser2[0].foundMaxn()) {
            parser2[0].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser2[1].foundMaxn()) {
            parser2[1].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser2[2].foundMaxn()) {
            parser2[2].setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser[0].foundP()) {
            parser[0].setPvalue(zold);
        }

        if (parser[1].foundP()) {
            parser[1].setPvalue(zold);
        }

        if (parser2[0].foundP()) {
            parser2[0].setPvalue(zold);
        }

        if (parser2[1].foundP()) {
            parser2[1].setPvalue(zold);
        }

        if (parser2[2].foundP()) {
            parser2[2].setPvalue(zold);
        }

        if (parser[0].foundPP()) {
            parser[0].setPPvalue(zold2);
        }

        if (parser[1].foundPP()) {
            parser[1].setPPvalue(zold2);
        }

        if (parser2[0].foundPP()) {
            parser2[0].setPPvalue(zold2);
        }

        if (parser2[1].foundPP()) {
            parser2[1].setPPvalue(zold2);
        }

        if (parser2[2].foundPP()) {
            parser2[2].setPPvalue(zold2);
        }

        Complex c_center = new Complex(xCenter, yCenter);

        if (parser[0].foundCenter()) {
            parser[0].setCentervalue(c_center);
        }

        if (parser[1].foundCenter()) {
            parser[1].setCentervalue(c_center);
        }

        if (parser2[0].foundCenter()) {
            parser2[0].setCentervalue(c_center);
        }

        if (parser2[1].foundCenter()) {
            parser2[1].setCentervalue(c_center);
        }

        if (parser2[2].foundCenter()) {
            parser2[2].setCentervalue(c_center);
        }

        Complex c_size = new Complex(size, 0);

        if (parser[0].foundSize()) {
            parser[0].setSizevalue(c_size);
        }

        if (parser[1].foundSize()) {
            parser[1].setSizevalue(c_size);
        }

        if (parser2[0].foundSize()) {
            parser2[0].setSizevalue(c_size);
        }

        if (parser2[1].foundSize()) {
            parser2[1].setSizevalue(c_size);
        }

        if (parser2[2].foundSize()) {
            parser2[2].setSizevalue(c_size);
        }

        Complex c_isize = new Complex(ThreadDraw.IMAGE_SIZE, 0);
        if (parser[0].foundISize()) {
            parser[0].setISizevalue(c_isize);
        }

        if (parser[1].foundISize()) {
            parser[1].setISizevalue(c_isize);
        }

        if (parser2[0].foundISize()) {
            parser2[0].setISizevalue(c_isize);
        }

        if (parser2[1].foundISize()) {
            parser2[1].setISizevalue(c_isize);
        }

        if (parser2[2].foundISize()) {
            parser2[2].setISizevalue(c_isize);
        }

        if (parser[0].foundPoint()) {
            parser[0].setPointvalue(point);
        }

        if (parser[1].foundPoint()) {
            parser[1].setPointvalue(point);
        }

        if (parser2[0].foundPoint()) {
            parser2[0].setPointvalue(point);
        }

        if (parser2[1].foundPoint()) {
            parser2[1].setPointvalue(point);
        }

        if (parser2[2].foundPoint()) {
            parser2[2].setPointvalue(point);
        }
    }

}
