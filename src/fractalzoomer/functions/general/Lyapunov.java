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
package fractalzoomer.functions.general;

import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.app_settings.LyapunovSettings;
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
public class Lyapunov extends Julia {

    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode function_expr;
    private Parser function_parser;
    private ExpressionNode exponent_expr;
    private Parser exponent_parser;
    private Complex point;
    private boolean useLyapunovExponent;
    private double sum;
    private int samples;
    private int lyapunovVariableId;
    private boolean usingDefaultFunctions;
    private int initializationIterations;
    private boolean calculateExponent;
    private boolean skipBailoutCheck;

    public Lyapunov(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, String[] lyapunov_expression, boolean useLyapunovExponent, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String lyapunovInitialValue, int initializationIterations, boolean skipBailoutCheck) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

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
            init_val = new VariableInitialValue(lyapunovInitialValue, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        this.useLyapunovExponent = useLyapunovExponent;
        this.lyapunovVariableId = lyapunovVariableId;

        this.initializationIterations = initializationIterations;
        this.skipBailoutCheck = skipBailoutCheck;

        parser = new Parser[lyapunov_expression.length];
        expr = new ExpressionNode[lyapunov_expression.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(lyapunov_expression[i]);
        }

        usingDefaultFunctions = lyapunovFunction.replaceAll("\\s+", "").equals(LyapunovSettings.DEFAULT_LYAPUNOV_FUNCTION_TRIMMED) && lyapunovExponentFunction.replaceAll("\\s+", "").equals(LyapunovSettings.DEFAULT_LYAPUNOV_EXPONENT_FUNCTION_TRIMMED);

        if (!usingDefaultFunctions) {
            function_parser = new Parser();
            function_expr = function_parser.parse(lyapunovFunction);

            exponent_parser = new Parser();
            exponent_expr = exponent_parser.parse(lyapunovExponentFunction);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public Lyapunov(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, String[] lyapunov_expression, boolean useLyapunovExponent, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, int initializationIterations, boolean skipBailoutCheck, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        this.useLyapunovExponent = useLyapunovExponent;
        this.lyapunovVariableId = lyapunovVariableId;

        this.initializationIterations = initializationIterations;
        this.skipBailoutCheck = skipBailoutCheck;

        parser = new Parser[lyapunov_expression.length];
        expr = new ExpressionNode[lyapunov_expression.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(lyapunov_expression[i]);
        }

        usingDefaultFunctions = lyapunovFunction.replaceAll("\\s+", "").equals(LyapunovSettings.DEFAULT_LYAPUNOV_FUNCTION_TRIMMED) && lyapunovExponentFunction.replaceAll("\\s+", "").equals(LyapunovSettings.DEFAULT_LYAPUNOV_EXPONENT_FUNCTION_TRIMMED);

        if (!usingDefaultFunctions) {
            function_parser = new Parser();
            function_expr = function_parser.parse(lyapunovFunction);

            exponent_parser = new Parser();
            exponent_expr = exponent_parser.parse(lyapunovExponentFunction);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    //orbit
    public Lyapunov(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, String[] lyapunov_expression, String lyapunovFunction, String lyapunovExponentFunction, String lyapunovInitialValue, int initializationIterations) {

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
            init_val = new VariableInitialValue(lyapunovInitialValue, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
        }

        parser = new Parser[lyapunov_expression.length];
        expr = new ExpressionNode[lyapunov_expression.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(lyapunov_expression[i]);
        }

        usingDefaultFunctions = lyapunovFunction.replaceAll("\\s+", "").equals(LyapunovSettings.DEFAULT_LYAPUNOV_FUNCTION_TRIMMED) && lyapunovExponentFunction.replaceAll("\\s+", "").equals(LyapunovSettings.DEFAULT_LYAPUNOV_EXPONENT_FUNCTION_TRIMMED);

        if (!usingDefaultFunctions) {
            function_parser = new Parser();
            function_expr = function_parser.parse(lyapunovFunction);

            exponent_parser = new Parser();
            exponent_expr = exponent_parser.parse(lyapunovExponentFunction);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        this.initializationIterations = initializationIterations;

    }

    public Lyapunov(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, String[] lyapunov_expression, String lyapunovFunction, String lyapunovExponentFunction, int initializationIterations, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        parser = new Parser[lyapunov_expression.length];
        expr = new ExpressionNode[lyapunov_expression.length];

        for (int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(lyapunov_expression[i]);
        }

        usingDefaultFunctions = lyapunovFunction.replaceAll("\\s+", "").equals(LyapunovSettings.DEFAULT_LYAPUNOV_FUNCTION_TRIMMED) && lyapunovExponentFunction.replaceAll("\\s+", "").equals(LyapunovSettings.DEFAULT_LYAPUNOV_EXPONENT_FUNCTION_TRIMMED);

        if (!usingDefaultFunctions) {
            function_parser = new Parser();
            function_expr = function_parser.parse(lyapunovFunction);

            exponent_parser = new Parser();
            exponent_expr = exponent_parser.parse(lyapunovExponentFunction);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        this.initializationIterations = initializationIterations;

    }

    @Override
    public void function(Complex[] complex) {

        Complex step = new Complex();

        if (usingDefaultFunctions || function_parser.foundR() || exponent_parser.foundR()) {
            int index = iterations % parser.length;

            if (parser[index].foundN()) {
                parser[index].setNvalue(new Complex(iterations, 0));
            }

            if (parser[index].foundZ()) {
                parser[index].setZvalue(complex[0]);
            }

            if (parser[index].foundC()) {
                parser[index].setCvalue(complex[1]);
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser[index].foundVar(i)) {
                    parser[index].setVarsvalue(i, globalVars[i]);
                }
            }

            step = expr[index].getValue();
        }

        double calculatedExponent = 0;

        if (usingDefaultFunctions) {
            Complex rz = complex[0].times(step);
            Complex f = rz.times(complex[0].r_sub(1));

            if(calculateExponent) {
                calculatedExponent = step.sub_mutable(rz.times_mutable(2)).norm();
            }

            complex[0] = f;
        } else {
            if (function_parser.foundR()) {
                function_parser.setRvalue(step);
            }

            if (function_parser.foundN()) {
                function_parser.setNvalue(new Complex(iterations, 0));
            }

            if (function_parser.foundZ()) {
                function_parser.setZvalue(complex[0]);
            }

            if (function_parser.foundC()) {
                function_parser.setCvalue(complex[1]);
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (function_parser.foundVar(i)) {
                    function_parser.setVarsvalue(i, globalVars[i]);
                }
            }

            Complex f = function_expr.getValue();

            if(calculateExponent) {
                if (exponent_parser.foundR()) {
                    exponent_parser.setRvalue(step);
                }

                if (exponent_parser.foundN()) {
                    exponent_parser.setNvalue(new Complex(iterations, 0));
                }

                if (exponent_parser.foundZ()) {
                    exponent_parser.setZvalue(complex[0]);
                }

                if (exponent_parser.foundC()) {
                    exponent_parser.setCvalue(complex[1]);
                }

                for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                    if (exponent_parser.foundVar(i)) {
                        exponent_parser.setVarsvalue(i, globalVars[i]);
                    }
                }

                calculatedExponent = exponent_expr.getValue().norm();
            }

            complex[0] = f;
        }

        if (calculateExponent && calculatedExponent > 1E-13) {
            sum += Math.log(calculatedExponent);
            samples++;
        }

        setVariables(zold, zold2);

    }

    public Complex[] initialize(Complex pixel) {

        Complex[] complex = super.initialize(pixel);

        setInitVariables(start, zold, zold2);

        return complex;

    }

    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = super.initializeSeed(pixel);

        setInitVariables(start, zold, zold2);

        return complex;

    }

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {

        iterations = 0;
        sum = 0;
        samples = 0;

        calculateExponent = false;
        for(int initialIteration = 0; initialIteration < initializationIterations; initialIteration++) {
            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }
        }
        calculateExponent = true;

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (!skipBailoutCheck && bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        double res = sum / samples;
        
        globalVars[lyapunovVariableId].assign(res);

        if (useLyapunovExponent) {

            double value;
            if (res > 0) {
                value = getFinalValueOut(ColorAlgorithm.MAXIMUM_ITERATIONS);
            }
            else {
                value = getFinalValueIn(Math.abs(res) + max_iterations);
            }

            if (inTrueColorAlgorithm != null) {
                setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
            }

            return  value;
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        return in;

    }

    @Override
    protected double iterateFractalWithPeriodicity(Complex[] complex, Complex pixel) {

        iterations = 0;
        sum = 0;
        samples = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        calculateExponent = false;
        for(int initialIteration = 0; initialIteration < initializationIterations; initialIteration++) {
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }
        }
        calculateExponent = true;

        for (; iterations < max_iterations; iterations++) {

            if (!skipBailoutCheck && bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;

    }

    @Override
    protected void iterateFractalOrbit(Complex[] complex, Complex pixel) {
        iterations = 0;

        Complex temp = null;

        calculateExponent = false;
        for(int initialIteration = 0; initialIteration < initializationIterations; initialIteration++) {
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start);
        }

        for (; iterations < max_iterations; iterations++) {
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start);

            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    @Override
    protected Complex iterateFractalDomain(Complex[] complex, Complex pixel) {

        iterations = 0;

        calculateExponent = false;
        for(int initialIteration = 0; initialIteration < initializationIterations; initialIteration++) {
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start);
        }

        for (; iterations < max_iterations; iterations++) {

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start);

        }

        return complex[0];

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

        if (!usingDefaultFunctions) {
            if (function_parser.foundP()) {
                function_parser.setPvalue(zold);
            }

            if (function_parser.foundPP()) {
                function_parser.setPPvalue(zold2);
            }

            if (exponent_parser.foundP()) {
                exponent_parser.setPvalue(zold);
            }

            if (exponent_parser.foundPP()) {
                exponent_parser.setPPvalue(zold2);
            }
        }

    }

    private void setInitVariables(Complex start, Complex zold, Complex zold2) {

        Complex c_center = new Complex(xCenter, yCenter);
        Complex c_size = new Complex(size, 0);
        Complex c_isize = new Complex(ThreadDraw.IMAGE_SIZE, 0);

        for (int i = 0; i < parser.length; i++) {
            if (parser[i].foundS()) {
                parser[i].setSvalue(start);
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

        if (!usingDefaultFunctions) {
            if (function_parser.foundS()) {
                function_parser.setSvalue(start);
            }

            if (function_parser.foundMaxn()) {
                function_parser.setMaxnvalue(new Complex(max_iterations, 0));
            }

            if (function_parser.foundP()) {
                function_parser.setPvalue(zold);
            }

            if (function_parser.foundPP()) {
                function_parser.setPPvalue(zold2);
            }

            if (function_parser.foundCenter()) {
                function_parser.setCentervalue(c_center);
            }

            if (function_parser.foundSize()) {
                function_parser.setSizevalue(c_size);
            }

            if (function_parser.foundISize()) {
                function_parser.setISizevalue(c_isize);
            }

            if (function_parser.foundPoint()) {
                function_parser.setPointvalue(point);
            }

            if (exponent_parser.foundS()) {
                exponent_parser.setSvalue(start);
            }

            if (exponent_parser.foundMaxn()) {
                exponent_parser.setMaxnvalue(new Complex(max_iterations, 0));
            }

            if (exponent_parser.foundP()) {
                exponent_parser.setPvalue(zold);
            }

            if (exponent_parser.foundPP()) {
                exponent_parser.setPPvalue(zold2);
            }

            if (exponent_parser.foundCenter()) {
                exponent_parser.setCentervalue(c_center);
            }

            if (exponent_parser.foundSize()) {
                exponent_parser.setSizevalue(c_size);
            }

            if (exponent_parser.foundISize()) {
                exponent_parser.setISizevalue(c_isize);
            }

            if (exponent_parser.foundPoint()) {
                exponent_parser.setPointvalue(point);
            }
        }

    }

}
