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
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.functions.ExtendedConvergentType;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaIterationBasedConverging extends ExtendedConvergentType {

    private ExpressionNode[] expr;
    private Parser[] parser;
    private Complex point;

    public UserFormulaIterationBasedConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String[] user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        if (perturbation) {
            if (variable_perturbation) {
                if (user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            } else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        } else {
            pertur_val = new DefaultPerturbation();
        }

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

        parser = new Parser[user_formula.length];
        expr = new ExpressionNode[user_formula.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_formula[i]);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

    }

    public UserFormulaIterationBasedConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String[] user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        switch (out_coloring_algorithm) {

            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-7;
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-7;
                break;
            case MainWindow.BANDED:
                convergent_bailout = 1E-7;
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                convergent_bailout = 1E-7;
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        parser = new Parser[user_formula.length];
        expr = new ExpressionNode[user_formula.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_formula[i]);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    //orbit
    public UserFormulaIterationBasedConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String[] user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        if (perturbation) {
            if (variable_perturbation) {
                if (user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            } else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        } else {
            pertur_val = new DefaultPerturbation();
        }

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

        parser = new Parser[user_formula.length];
        expr = new ExpressionNode[user_formula.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_formula[i]);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public UserFormulaIterationBasedConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String[] user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        parser = new Parser[user_formula.length];
        expr = new ExpressionNode[user_formula.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_formula[i]);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    @Override
    public void function(Complex[] complex) {

        if (iterations % 4 == 0) {
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

            complex[0] = expr[0].getValue();
        } else if (iterations % 4 == 1) {
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

            complex[0] = expr[1].getValue();
        } else if (iterations % 4 == 2) {
            if (parser[2].foundN()) {
                parser[2].setNvalue(new Complex(iterations, 0));
            }

            if (parser[2].foundZ()) {
                parser[2].setZvalue(complex[0]);
            }

            if (parser[2].foundC()) {
                parser[2].setCvalue(complex[1]);
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser[2].foundVar(i)) {
                    parser[2].setVarsvalue(i, globalVars[i]);
                }
            }

            complex[0] = expr[2].getValue();
        } else {
            if (parser[3].foundN()) {
                parser[3].setNvalue(new Complex(iterations, 0));
            }

            if (parser[3].foundZ()) {
                parser[3].setZvalue(complex[0]);
            }

            if (parser[3].foundC()) {
                parser[3].setCvalue(complex[1]);
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser[3].foundVar(i)) {
                    parser[3].setVarsvalue(i, globalVars[i]);
                }
            }

            complex[0] = expr[3].getValue();
        }

        setVariables(zold, zold2);

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = super.initialize(pixel);

        setInitVariables(start, zold, zold2, c0);

        return complex;

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = super.initializeSeed(pixel);

        setInitVariables(start, zold, zold2, c0);

        return complex;

    }

    private void setVariables(Complex zold, Complex zold2) {

        for (int i = 0; i < parser.length; i++) {
            if (parser[i].foundP()) {
                parser[i].setPvalue(zold);
            }

            if (parser[i].foundPP()) {
                parser[i].setPPvalue(zold2);
            }
        }

    }

    private void setInitVariables(Complex start, Complex zold, Complex zold2, Complex c0) {

        Complex c_center = new Complex(xCenter, yCenter);
        Complex c_size = new Complex(size, 0);
        Complex c_isize = new Complex(ThreadDraw.IMAGE_SIZE, 0);

        for (int i = 0; i < parser.length; i++) {
            if (parser[i].foundS()) {
                parser[i].setSvalue(start);
            }

            if (parser[i].foundC0()) {
                parser[i].setC0value(c0);
            }

            if (parser[i].foundMaxn()) {
                parser[i].setMaxnvalue(new Complex(max_iterations, 0));
            }

            if (parser[i].foundP()) {
                parser[i].setPvalue(zold);
            }

            if (parser[i].foundPP()) {
                parser[i].setPPvalue(zold2);
            }

            if (parser[i].foundCenter()) {
                parser[i].setCentervalue(c_center);
            }

            if (parser[i].foundSize()) {
                parser[i].setSizevalue(c_size);
            }

            if (parser[i].foundISize()) {
                parser[i].setISizevalue(c_isize);
            }

            if (parser[i].foundPoint()) {
                parser[i].setPointvalue(point);
            }
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
}
