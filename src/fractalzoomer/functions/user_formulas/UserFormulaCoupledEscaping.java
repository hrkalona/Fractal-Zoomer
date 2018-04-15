/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
import fractalzoomer.fractal_options.coupling.CosineCoupling;
import fractalzoomer.fractal_options.coupling.Coupling;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.PlanePointOption;
import fractalzoomer.fractal_options.coupling.RandomCoupling;
import fractalzoomer.fractal_options.coupling.SimpleCoupling;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;
import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaCoupledEscaping extends Julia {

    private ExpressionNode expr;
    private Parser parser;
    private ExpressionNode expr2;
    private Parser parser2;
    private int iterations;
    private PlanePointOption init_val2;
    private Coupling coupler;
    private Complex point;

    public UserFormulaCoupledEscaping(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        if (perturbation) {
            if (variable_perturbation) {
                if (user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                } else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
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
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                } else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
            } else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        } else {
            init_val = new DefaultInitialValue();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        parser = new Parser();
        expr = parser.parse(user_formula_coupled[0]);

        parser2 = new Parser();
        expr2 = parser2.parse(user_formula_coupled[1]);

        switch (coupling_method) {
            case 0:
                coupler = new SimpleCoupling(coupling);
                break;
            case 1:
                coupler = new CosineCoupling(coupling, coupling_amplitude, coupling_frequency);
                break;
            case 2:
                coupler = new RandomCoupling(coupling, coupling_amplitude, coupling_seed);
                break;
        }

        init_val2 = new VariableInitialValue(user_formula_coupled[2], xCenter, yCenter, size, max_iterations, plane_transform_center);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public UserFormulaCoupledEscaping(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        parser = new Parser();
        expr = parser.parse(user_formula_coupled[0]);

        parser2 = new Parser();
        expr2 = parser2.parse(user_formula_coupled[1]);

        switch (coupling_method) {
            case 0:
                coupler = new SimpleCoupling(coupling);
                break;
            case 1:
                coupler = new CosineCoupling(coupling, coupling_amplitude, coupling_frequency);
                break;
            case 2:
                coupler = new RandomCoupling(coupling, coupling_amplitude, coupling_seed);
                break;
        }

        init_val2 = new VariableInitialValue(user_formula_coupled[2], xCenter, yCenter, size, max_iterations, plane_transform_center);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    //orbit
    public UserFormulaCoupledEscaping(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        if (perturbation) {
            if (variable_perturbation) {
                if (user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                } else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
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
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                } else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
            } else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        } else {
            init_val = new DefaultInitialValue();
        }

        parser = new Parser();
        expr = parser.parse(user_formula_coupled[0]);

        parser2 = new Parser();
        expr2 = parser2.parse(user_formula_coupled[1]);

        switch (coupling_method) {
            case 0:
                coupler = new SimpleCoupling(coupling);
                break;
            case 1:
                coupler = new CosineCoupling(coupling, coupling_amplitude, coupling_frequency);
                break;
            case 2:
                coupler = new RandomCoupling(coupling, coupling_amplitude, coupling_seed);
                break;
        }

        init_val2 = new VariableInitialValue(user_formula_coupled[2], xCenter, yCenter, size, max_iterations, plane_transform_center);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public UserFormulaCoupledEscaping(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        parser = new Parser();
        expr = parser.parse(user_formula_coupled[0]);

        parser2 = new Parser();
        expr2 = parser2.parse(user_formula_coupled[1]);

        switch (coupling_method) {
            case 0:
                coupler = new SimpleCoupling(coupling);
                break;
            case 1:
                coupler = new CosineCoupling(coupling, coupling_amplitude, coupling_frequency);
                break;
            case 2:
                coupler = new RandomCoupling(coupling, coupling_amplitude, coupling_seed);
                break;
        }

        init_val2 = new VariableInitialValue(user_formula_coupled[2], xCenter, yCenter, size, max_iterations, plane_transform_center);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    @Override
    protected void function(Complex[] complex) {

        /*
        
         /**
         * Z1= *
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
                parser.setVarsvalue(i, vars[i]);
            }
        }

        Complex a1 = expr.getValue();
        /**
         * *
         */
        /**
         * Z2= *
         */
        if (parser2.foundN()) {
            parser2.setNvalue(new Complex(iterations, 0));
        }

        if (parser2.foundZ()) {
            parser2.setZvalue(complex[2]);
        }

        if (parser2.foundC()) {
            parser2.setCvalue(complex[1]);
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser2.foundVar(i)) {
                parser2.setVarsvalue(i, vars[i]);
            }
        }

        Complex a2 = expr2.getValue();
        /**
         * *
         */

        Complex[] res = coupler.couple(a1, a2, iterations);
        complex[0] = res[0];
        complex[2] = res[1];

    }

    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
        iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);
        init_val2.setGlobalVars(vars);

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));
        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
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

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);
        init_val2.setGlobalVars(vars);

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));
        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        for (; iterations < max_iterations; iterations++) {

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;

    }

    @Override
    public double[] calculateFractal3DWithPeriodicity(Complex pixel) {

        iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);
        init_val2.setGlobalVars(vars);

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));
        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        double temp;

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            if (periodicityCheck(complex[0])) {
                double[] array = {max_iterations, ColorAlgorithm.MAXIMUM_ITERATIONS};
                return array;
            }

        }

        double[] array = {max_iterations, ColorAlgorithm.MAXIMUM_ITERATIONS};
        return array;

    }

    @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {

        iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);
        init_val2.setGlobalVars(vars);

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));
        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        double temp;

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
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

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);
        init_val2.setGlobalVars(vars);

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel_orbit)));//z
        complex[1] = new Complex(pixel_orbit);//c
        complex[2] = new Complex(init_val2.getValue(pixel_orbit));//z2

        Complex temp = null;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        for (; iterations < max_iterations; iterations++) {
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
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

        init_val2.setGlobalVars(vars);

        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
        iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        init_val2.setGlobalVars(vars);

        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
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

        init_val2.setGlobalVars(vars);

        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        double temp;

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            if (periodicityCheck(complex[0])) {
                double[] array = {max_iterations, ColorAlgorithm.MAXIMUM_ITERATIONS};
                return array;
            }
        }

        double[] array = {max_iterations, ColorAlgorithm.MAXIMUM_ITERATIONS};
        return array;

    }

    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
        iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        init_val2.setGlobalVars(vars);

        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = tempz2;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        double temp;

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
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

        init_val2.setGlobalVars(vars);

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex(init_val2.getValue(pixel_orbit));

        Complex temp = null;

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        for (; iterations < max_iterations; iterations++) {
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    @Override
    public Complex iterateFractalDomain(Complex pixel) {
        iterations = 0;

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);
        init_val2.setGlobalVars(vars);

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));
        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        for (; iterations < max_iterations; iterations++) {

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

        init_val2.setGlobalVars(vars);

        Complex tempz2 = new Complex(init_val2.getValue(pixel));

        Complex[] complex = new Complex[3];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c
        complex[2] = tempz2;//z2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

        for (; iterations < max_iterations; iterations++) {

            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            setVariables(zold, zold2);

        }

        return complex[0];

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

    private void setInitVariables(Complex start, Complex zold, Complex zold2) {

        if (parser.foundS()) {
            parser.setSvalue(start);
        }

        if (parser2.foundS()) {
            parser2.setSvalue(start);
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

        Complex c_isize = new Complex(ThreadDraw.IMAGE_SIZE, 0);
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
}
