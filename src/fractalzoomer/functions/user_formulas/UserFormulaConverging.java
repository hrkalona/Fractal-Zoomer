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
package fractalzoomer.functions.user_formulas;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskDraw;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.ExtendedConvergentType;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaConverging extends ExtendedConvergentType {

    private ExpressionNode expr;
    private Parser parser;
    private ExpressionNode expr2;
    private Parser parser2;
    private Complex point;

    public UserFormulaConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_formula, String user_formula2, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        parser = new Parser();
        expr = parser.parse(user_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_formula2);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

    }

    public UserFormulaConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_formula, String user_formula2, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        switch (out_coloring_algorithm) {

            case MainWindow.BINARY_DECOMPOSITION:
            case MainWindow.BINARY_DECOMPOSITION2:
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
            case MainWindow.BANDED:
                setConvergentBailout(1E-7);
                break;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        parser = new Parser();
        expr = parser.parse(user_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_formula2);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    //orbit
    public UserFormulaConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_formula, String user_formula2, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

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

        parser = new Parser();
        expr = parser.parse(user_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_formula2);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public UserFormulaConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_formula, String user_formula2, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);

        parser = new Parser();
        expr = parser.parse(user_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_formula2);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    @Override
    public void function(Complex[] complex) {

        /**
         * Z= *
         */
        if (parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }

        //expr.accept(new SetVariable("z", complex[0]));
        if (parser.foundZ()) {
            parser.setZvalue(complex[0]);
        }

        if (parser.foundC()) {
            //expr.accept(new SetVariable("c", complex[1]));
            parser.setCvalue(complex[1]);
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
            }
        }

        complex[0] = expr.getValue();
        /**
         * *
         */
        /**
         * C= *
         */
        if (parser2.foundN()) {
            parser2.setNvalue(new Complex(iterations, 0));
        }

        if (parser2.foundZ()) {
            parser2.setZvalue(complex[0]);
        }

        if (parser2.foundC()) {
            parser2.setCvalue(complex[1]);
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser2.foundVar(i)) {
                parser2.setVarsvalue(i, globalVars[i]);
            }
        }

        complex[1] = expr2.getValue();
        /**
         * *
         */

        setVariables(zold, zold2);
    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = super.initialize(pixel);

        setInitVariables(start, zold, zold2, c0, pixel);

        return complex;

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = super.initializeSeed(pixel);

        setInitVariables(start, zold, zold2, c0, pixel);

        return complex;

    }

    private void setVariables(Complex zold, Complex zold2) {

        if (parser.foundP()) {
            parser.setPvalue(zold);
        }

        if (parser2.foundP()) {
            parser2.setPvalue(zold);
        }

        if (parser.foundPP()) {
            parser.setPPvalue(zold2);
        }

        if (parser2.foundPP()) {
            parser2.setPPvalue(zold2);
        }

    }

    private void setInitVariables(Complex start, Complex zold, Complex zold2, Complex c0, Complex pixel) {

        if (parser.foundPixel()) {
            parser.setPixelvalue(pixel);
        }

        if (parser2.foundPixel()) {
            parser2.setPixelvalue(pixel);
        }

        if (parser.foundS()) {
            parser.setSvalue(start);
        }

        if (parser2.foundS()) {
            parser2.setSvalue(start);
        }

        if (parser.foundC0()) {
            parser.setC0value(c0);
        }

        if (parser2.foundC0()) {
            parser2.setC0value(c0);
        }

        if (parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser2.foundMaxn()) {
            parser2.setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser.foundP()) {
            parser.setPvalue(zold);
        }

        if (parser2.foundP()) {
            parser2.setPvalue(zold);
        }

        if (parser.foundPP()) {
            parser.setPPvalue(zold2);
        }

        if (parser2.foundPP()) {
            parser2.setPPvalue(zold2);
        }

        Complex c_center = new Complex(xCenter, yCenter);

        if (parser.foundCenter()) {
            parser.setCentervalue(c_center);
        }

        if (parser2.foundCenter()) {
            parser2.setCentervalue(c_center);
        }

        Complex c_size = new Complex(size, 0);

        if (parser.foundSize()) {
            parser.setSizevalue(c_size);
        }

        if (parser2.foundSize()) {
            parser2.setSizevalue(c_size);
        }

        Complex c_isize = new Complex(TaskDraw.IMAGE_SIZE, 0);
        if (parser.foundISize()) {
            parser.setISizevalue(c_isize);
        }

        if (parser2.foundISize()) {
            parser2.setISizevalue(c_isize);
        }

        if (parser.foundPoint()) {
            parser.setPointvalue(point);
        }

        if (parser2.foundPoint()) {
            parser2.setPointvalue(point);
        }
    }

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {
        return null;
    }

}
