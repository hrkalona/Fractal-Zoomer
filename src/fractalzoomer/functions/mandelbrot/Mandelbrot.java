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
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.core.*;
import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.iteration_statistics.*;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.DistanceEstimator;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTime;
import fractalzoomer.utils.ColorAlgorithm;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;
import static fractalzoomer.main.Constants.SA_CALCULATION_STR;

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
    public static Complex[] Referencex2;
    public static MantExpComplex[] ReferenceDeepx2;

    public Mandelbrot(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        this.burning_ship = burning_ship;

        power = 2;

        if (burning_ship) {
            type = new BurningShip();
        } else {
            type = new NormalMandel();
        }

        if (mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        } else {
            type2 = new NormalMandel();
        }

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

        this.exterior_de = exterior_de;
        this.inverse_dem = inverse_dem;

        if (exterior_de) {
            limit = (size / ThreadDraw.IMAGE_SIZE) * exterior_de_factor;
            limit = limit * limit;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);


        if(ThreadDraw.PERTURBATION_THEORY && out_coloring_algorithm == MainWindow.DISTANCE_ESTIMATOR) {
            if (!smoothing) {
                out_color_algorithm = new EscapeTime();
            } else {
                out_color_algorithm = new SmoothEscapeTime(log_bailout_squared, escaping_smooth_algorithm);
            }
        }
        else {
            special_alg = 0;

            switch (out_coloring_algorithm) {

                case MainWindow.DISTANCE_ESTIMATOR:
                    out_color_algorithm = new DistanceEstimator();
                    special_alg = 1;
                    break;

            }
        }

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;

        power = 2;

        if (burning_ship) {
            type = new BurningShip();
        } else {
            type = new NormalMandel();
        }

        if (mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        } else {
            type2 = new NormalMandel();
        }

        this.exterior_de = exterior_de;
        this.inverse_dem = inverse_dem;

        if (exterior_de) {
            limit = (size / ThreadDraw.IMAGE_SIZE) * exterior_de_factor;
            limit = limit * limit;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        special_alg = 0;

        switch (out_coloring_algorithm) {

            case MainWindow.DISTANCE_ESTIMATOR:
                out_color_algorithm = new DistanceEstimator();
                special_alg = 1;
                break;

        }

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();
    }

    //orbit
    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        this.burning_ship = burning_ship;

        power = 2;

        if (burning_ship) {
            type = new BurningShip();
        } else {
            type = new NormalMandel();
        }

        if (mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        } else {
            type2 = new NormalMandel();
        }

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

    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;

        power = 2;

        if (burning_ship) {
            type = new BurningShip();
        } else {
            type = new NormalMandel();
        }

        if (mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        } else {
            type2 = new NormalMandel();
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    @Override
    public void function(Complex[] complex) {

        type.getValue(complex[0]);
        complex[0].square_mutable_plus_c_mutable(complex[1]);
        type2.getValue(complex[0]);

    }

    private double mandel_2d_np_de_normal(Complex[] complex, Complex pixel) {

        iterations = 0;

        Complex dc = new Complex(1, 0);

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                boolean condition = inverse_dem ? (temp2 * temp3 * temp3) <= (dc.norm_squared() * limit) : (temp2 * temp3 * temp3) > (dc.norm_squared() * limit);

                if (condition) {
                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }

            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            if(isJulia && (!juliter || juliter && iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times_mutable(2);
            }
            else {
                dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            }

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;
    }

    private double mandel_2d_np_de_dem(Complex[] complex, Complex pixel) {

        iterations = 0;

        Complex dc = new Complex(1, 0);

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                boolean condition = inverse_dem ? (temp2 * temp3 * temp3) <= (dc.norm_squared() * limit) : (temp2 * temp3 * temp3) > (dc.norm_squared() * limit);

                if (condition) {
                    Object[] object = {iterations, complex[0], dc};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }

            if(isJulia && (!juliter || juliter && iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times_mutable(2);
            }
            else {
                dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            }

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;
    }

    private double mandel_2d_np_nde_normal(Complex[] complex, Complex pixel) {

        iterations = 0;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;
    }

    private double mandel_2d_np_nde_dem(Complex[] complex, Complex pixel) {

        iterations = 0;

        Complex dc = new Complex(1, 0);

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], dc};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);

            if(isJulia && (!juliter || juliter && iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times_mutable(2);
            }
            else {
                dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            }

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;
    }

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {

        if (exterior_de) {
            if (special_alg == 0) {
                return mandel_2d_np_de_normal(complex, pixel);
            } else {
                return mandel_2d_np_de_dem(complex, pixel);
            }
        } else if (special_alg == 0) {
            return mandel_2d_np_nde_normal(complex, pixel);
        } else {
            return mandel_2d_np_nde_dem(complex, pixel);
        }

    }

    private double mandel_2d_p_de_normal(Complex[] complex, Complex pixel) {

        iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex dc = new Complex(1, 0);

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                boolean condition = inverse_dem ? (temp2 * temp3 * temp3) <= (dc.norm_squared() * limit) : (temp2 * temp3 * temp3) > (dc.norm_squared() * limit);

                if (condition) {
                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            if(isJulia && (!juliter || juliter && iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times_mutable(2);
            }
            else {
                dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            }

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double mandel_2d_p_de_dem(Complex[] complex, Complex pixel) {

        iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex dc = new Complex(1, 0);

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                boolean condition = inverse_dem ? (temp2 * temp3 * temp3) <= (dc.norm_squared() * limit) : (temp2 * temp3 * temp3) > (dc.norm_squared() * limit);

                if (condition) {
                    Object[] object = {iterations, complex[0], dc};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }

            if(isJulia && (!juliter || juliter && iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times_mutable(2);
            }
            else {
                dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double mandel_2d_p_nde_normal(Complex[] complex, Complex pixel) {

        iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double mandel_2d_p_nde_dem(Complex[] complex, Complex pixel) {

        iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex dc = new Complex(1, 0);

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], dc};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            if(isJulia && (!juliter || juliter && iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times_mutable(2);
            }
            else {
                dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            }

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    @Override
    protected double iterateFractalWithPeriodicity(Complex[] complex, Complex pixel) {

        if (exterior_de) {
            if (special_alg == 0) {
                return mandel_2d_p_de_normal(complex, pixel);
            } else {
                return mandel_2d_p_de_dem(complex, pixel);
            }
        } else if (special_alg == 0) {
            return mandel_2d_p_nde_normal(complex, pixel);
        } else {
            return mandel_2d_p_nde_dem(complex, pixel);
        }
    }

    @Override
    protected void StatisticFactory(StatisticsSettings sts, double[] plane_transform_center) {

        statisticIncludeEscaped = sts.statisticIncludeEscaped;
        statisticIncludeNotEscaped = sts.statisticIncludeNotEscaped;

        if (sts.statisticGroup == 1) {
            statistic = new UserStatisticColoring(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, bailout, plane_transform_center, globalVars, sts.useAverage, sts.user_statistic_init_value, sts.reductionFunction, sts.useIterations, sts.useSmoothing);
            return;
        }
        else if(sts.statisticGroup == 2) {
            if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
                return;
            }
            statistic = new Equicontinuity(sts.statistic_intensity, sts.useSmoothing, sts.useAverage, log_bailout_squared, false, 0, sts.equicontinuityDenominatorFactor, sts.equicontinuityInvertFactor, sts.equicontinuityDelta);
            return;
        }
        else if(sts.statisticGroup == 3) {
            statistic = new NormalMap(sts.statistic_intensity, power, sts.normalMapHeight, sts.normalMapAngle, sts.normalMapUseSecondDerivative, size, sts.normalMapDEfactor, isJulia, sts.normalMapUseDE, sts.normalMapInvertDE, sts.normalMapColoring, sts.useNormalMap);
            return;
        }

        switch (sts.statistic_type) {
            case MainWindow.STRIPE_AVERAGE:
                statistic = new StripeAverage(sts.statistic_intensity, sts.stripeAvgStripeDensity, log_bailout_squared, sts.useSmoothing, sts.useAverage);
                break;
            case MainWindow.CURVATURE_AVERAGE:
                statistic = new CurvatureAverage(sts.statistic_intensity, log_bailout_squared, sts.useSmoothing, sts.useAverage);
                break;
            case MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE:
                statistic = new CosArgDivideNormAverage(sts.statistic_intensity, sts.cosArgStripeDensity, log_bailout_squared, sts.useSmoothing, sts.useAverage);
                break;
            case MainWindow.TRIANGLE_INEQUALITY_AVERAGE:
                statistic = new TriangleInequalityAverage(sts.statistic_intensity, log_bailout_squared, sts.useSmoothing, sts.useAverage);
                break;
            case MainWindow.ATOM_DOMAIN_BOF60_BOF61:
                statistic = new AtomDomain(sts.showAtomDomains, sts.statistic_intensity);
                break;
            case MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS:
                statistic = new DiscreteLagrangianDescriptors(sts.statistic_intensity, sts.lagrangianPower, log_bailout_squared, sts.useSmoothing, sts.useAverage, false, 0);
                break;
            case MainWindow.TWIN_LAMPS:
                statistic = new TwinLamps(sts.statistic_intensity, sts.twlFunction, sts.twlPoint);
                break;

        }
    }

    //private static BigComplex lastZGlitchCheckValue;

    @Override
    public void calculateReferencePoint(BigComplex pixel, Apfloat size, boolean deepZoom, int iterations, Location externalLocation, JProgressBar progress) {

        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        if (iterations == 0) {
            Reference = new Complex[max_iterations];

            if(isJulia) {
                ReferenceSubCp = new Complex[max_iterations];
            }

            if (!burning_ship) {
                Referencex2 = new Complex[max_iterations];
            }

            if (deepZoom) {
                ReferenceDeep = new MantExpComplex[max_iterations];

                if(isJulia) {
                    ReferenceSubCpDeep = new MantExpComplex[max_iterations];
                }

                if (!burning_ship) {
                    ReferenceDeepx2 = new MantExpComplex[max_iterations];
                }
            }
        } else if (max_iterations > Reference.length) {
            Reference = copyReference(Reference, new Complex[max_iterations]);

            if(isJulia) {
                ReferenceSubCp = copyReference(ReferenceSubCp, new Complex[max_iterations]);
            }

            if (!burning_ship) {
                Referencex2 = copyReference(Referencex2, new Complex[max_iterations]);
            }

            if (deepZoom) {
                ReferenceDeep = copyDeepReference(ReferenceDeep, new MantExpComplex[max_iterations]);

                if(isJulia) {
                    ReferenceSubCpDeep = copyDeepReference(ReferenceSubCpDeep, new MantExpComplex[max_iterations]);
                }

                if (!burning_ship) {
                    ReferenceDeepx2 = copyDeepReference(ReferenceDeepx2, new MantExpComplex[max_iterations]);
                }
            }
        }


        Location loc = new Location();

        //BigComplex z = iterations == 0 ? (isJulia ? pixel : new BigComplex()) : lastZValue;

        //new BigComplex(new MyApfloat("-1.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332"), new MyApfloat("+0.00000000000000000000000000000000030013824367909383240724973039775924987346831190773335270174257280120474975614823581185647299288414075519224186504978181625478529"))
        //BigComplex c = isJulia ? new BigComplex(seed) : pixel; //new BigComplex(seed)


        BigComplex z = iterations == 0 ? (isJulia ? pixel : new BigComplex()) : lastZValue;
        BigComplex c = isJulia ? new BigComplex(seed) : pixel;

        //BigComplex zGlitchCheck = iterations == 0 ? new BigComplex() : lastZGlitchCheckValue;
        //BigComplex cGlitchCheck = c;

        BigComplex zold = iterations == 0 ? new BigComplex() : secondTolastZValue;
        BigComplex zold2 = iterations == 0 ? new BigComplex() : thirdTolastZValue;
        BigComplex start = isJulia ? pixel : new BigComplex();
        BigComplex c0 = c;

        refPoint = pixel;
        refPointSmall = pixel.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
        }

        RefType = getRefType();

        boolean isSeriesInUse = ThreadDraw.SERIES_APPROXIMATION && supportsSeriesApproximation();
        boolean fullReference = ThreadDraw.CALCULATE_FULL_REFERENCE;

        FullRef = fullReference;

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            Reference[iterations] = cz;

            if(isJulia) {
                BigComplex zsubcp = z.sub(refPoint);
                ReferenceSubCp[iterations] = zsubcp.toComplex();

                if(deepZoom) {
                    ReferenceSubCpDeep[iterations] = loc.getMantExpComplex(zsubcp);
                }
            }

            //BigComplex z2 = null;
            if(!burning_ship) {
                //z2 = z.times(MyApfloat.TWO);
                //Referencex2[iterations] = z2.toComplex();
                Referencex2[iterations] = Reference[iterations].times(2);
            }

            if(deepZoom) {
                ReferenceDeep[iterations] = loc.getMantExpComplex(z);
                if(!burning_ship) {
                    //ReferenceDeepx2[iterations] = loc.getMantExpComplex(z2);
                    ReferenceDeepx2[iterations] = ReferenceDeep[iterations].times2();
                }

                /*ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
                if(!burning_ship) {
                    ReferenceDeepx2[iterations] = new MantExpComplex(Referencex2[iterations]);
                }*/
            }

            if (!fullReference && iterations > 0 && bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, Apfloat.ZERO, pixel)) {
                break;
            }

            zold2 = zold;
            zold = z;

            try {
                if (burning_ship) {
                    z = z.abs().square_plus_c(c);
                } else {
                    z = z.square_plus_c(c);
                }

                /*if(isJulia) {
                    zGlitchCheck = zGlitchCheck.square_plus_c(cGlitchCheck);
                }*/
            }
            catch (Exception ex) {
                break;
            }



            if(progress != null && iterations % 1000 == 0) {
                progress.setValue(iterations - initIterations);
                progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (iterations - initIterations) / progress.getMaximum() * 100)) + "%");
            }

        }

        //lastZGlitchCheckValue = zGlitchCheck;
        lastZValue = z;
        secondTolastZValue = zold;
        thirdTolastZValue = zold2;

        MaxRefIteration = iterations - 1;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }


        skippedIterations = 0;
        if(isSeriesInUse) {
            calculateSeriesWrapper(size, deepZoom, externalLocation, progress);
        }

    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        if(burning_ship) {

            Complex X = Reference[RefIteration];
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double a2 = a * a;
            double b2 = b * b;
            return new Complex(2.0 * a * r + a2 - 2.0 * b * i - b2, Complex.DiffAbs(r * i, r * b + i * a + a * b) * 2).plus_mutable(DeltaSub0);
        }
        else {
            return DeltaSubN.times(Referencex2[RefIteration]).plus_mutable(DeltaSubN.square()).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        if(burning_ship) {
            MantExpComplex X = ReferenceDeep[RefIteration];
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);

            return new MantExpComplex(a.multiply2().multiply_mutable(r).add_mutable(a2).subtract_mutable(b.multiply2().multiply_mutable(i)).subtract_mutable(b2), MantExpComplex.DiffAbs(r.multiply(i), r.multiply(b).add_mutable(i.multiply(a)).add_mutable(a.multiply(b))).multiply2_mutable()).plus_mutable(DeltaSub0);
        }
        else {
            return DeltaSubN.times(ReferenceDeepx2[RefIteration]).plus_mutable(DeltaSubN.square()).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        if (burning_ship) {

            Complex X = Reference[RefIteration];
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double a2 = a * a;
            double b2 = b * b;
            return new Complex(2.0 * a * r + a2 - 2.0 * b * i - b2, Complex.DiffAbs(r * i, r * b + i * a + a * b) * 2);
        } else {
            return DeltaSubN.times(Referencex2[RefIteration]).plus_mutable(DeltaSubN.square());
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        if(burning_ship) {
            MantExpComplex X = ReferenceDeep[RefIteration];
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);

            return new MantExpComplex(a.multiply2().multiply_mutable(r).add_mutable(a2).subtract_mutable(b.multiply2().multiply_mutable(i)).subtract_mutable(b2), MantExpComplex.DiffAbs(r.multiply(i), r.multiply(b).add_mutable(i.multiply(a)).add_mutable(a.multiply(b))).multiply2_mutable());
        }
        else {
            return DeltaSubN.times(ReferenceDeepx2[RefIteration]).plus_mutable(DeltaSubN.square());
        }
    }

    /*public Complex perturbationFunctionGlitch(Complex DeltaSubN, int RefIteration) {
        return DeltaSubN.times(ReferenceSubCp[RefIteration].times(2)).plus_mutable(DeltaSubN.square());
    }*/


    @Override
    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc, JProgressBar progress) {

        skippedIterations = 0;

        int numCoefficients = ThreadDraw.SERIES_APPROXIMATION_TERMS;

        if (numCoefficients < 2 || dsize.compareTo(MyApfloat.SA_START_SIZE) > 0) {
            return;
        }

        /*MantExpComplex[] DeltaSub0ToThe = new MantExpComplex[numCoefficients + 1];*/

        long[] logwToThe  = new long[numCoefficients + 1];

        final long[] magCoeff = new long[numCoefficients];

        /*if(deepZoom) {
            DeltaSub0ToThe[1] = new MantExpComplex(dsizeMantExp, dsizeMantExp);
        }
        else {
            //DeltaSub0ToThe[1] = new MantExpComplex(sqrt2 * size, sqrt2 * size);
        }*/

        logwToThe[1] = loc.getSeriesApproxSize().log2approx();

        for (int i = 2; i <= numCoefficients; i++) {
            //DeltaSub0ToThe[i] = DeltaSub0ToThe[i - 1].times(DeltaSub0ToThe[1]);
            //DeltaSub0ToThe[i].Reduce();
            logwToThe[i] = logwToThe[1] * i;
        }

        coefficients = new MantExpComplex[numCoefficients][max_data];


        coefficients[0][0] = new MantExpComplex(1, 0);

        for(int i = 1; i < numCoefficients; i++){
            coefficients[i][0] = new MantExpComplex();
        }

        //MantExp limit = DeltaSub0ToThe[numCoefficients].norm_squared().multiply_mutable(new MantExp(MyApfloat.reciprocal(ThreadDraw.SERIES_APPROXIMATION_TOLERANCE.multiply(ThreadDraw.SERIES_APPROXIMATION_TOLERANCE))));

        long oomDiff = ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE;

        int length = deepZoom ? ReferenceDeep.length : Reference.length;

        int i;
        for(i = 1; i < length; i++) {

            if(deepZoom) {
                if(ReferenceDeep[i - 1] == null) {
                    skippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                    return;
                }
            }
            else {
                if(Reference[i - 1] == null) {
                    skippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                    return;
                }
            }

            MantExpComplex twoRef = null;

            if(deepZoom) {
                twoRef = ReferenceDeepx2[i - 1];
            }
            else {
               twoRef = new MantExpComplex(Referencex2[i - 1]);
            }

            MantExpComplex twoAn = null;

            int new_i = i % max_data;
            int old_i = (i - 1) % max_data;

            if (numCoefficients >= 1) {
                //A
                coefficients[0][new_i] = coefficients[0][old_i].times(twoRef).plus_mutable(MantExp.ONE); // An+1 = 2XnAn + 1
            }
            if (numCoefficients >= 2) {
                //B
                coefficients[1][new_i] = coefficients[1][old_i].times(twoRef).plus_mutable(coefficients[0][old_i].square()); // Bn+1 = 2XnBn + An^2
            }
            if (numCoefficients >= 3) {
                //C
                twoAn = coefficients[0][old_i].times2();
                coefficients[2][new_i] = coefficients[2][old_i].times(twoRef).plus_mutable(coefficients[1][old_i].times(twoAn)); // Cn+1 = 2XnCn + 2AnBn
            }
            if (numCoefficients >= 4) {
                //D
                coefficients[3][new_i] = coefficients[3][old_i].times(twoRef).plus_mutable(twoAn.times(coefficients[2][old_i])).plus_mutable(coefficients[1][old_i].square()); //Dn+1 = 2XnCn + 2AnCn + Bn^2
            }
            if (numCoefficients >= 5) {
                //E
                coefficients[4][new_i] = coefficients[4][old_i].times(twoRef).plus_mutable(twoAn.times(coefficients[3][old_i])).plus_mutable(coefficients[1][old_i].times(coefficients[2][old_i]).times2_mutable()); //En+1 = 2XnEn + 2AnDn + 2BnCn
            }

            if(numCoefficients >= 6) {
                for(int k = 5; k < numCoefficients; k++) {
                    MantExpComplex sum = new MantExpComplex();

                    for(int j = 0; j < k; j++) {
                        sum = sum.plus_mutable(coefficients[j][old_i].times(coefficients[k - j - 1][old_i]));
                    }

                    coefficients[k][new_i] = coefficients[k][old_i].times(twoRef).plus_mutable(sum);

                    /*if( k == 0) {
                        coefficients[k][new_i] = coefficients[k][new_i].plus_mutable(1);
                    }*/
                }
            }

            for (int j = 0; j < numCoefficients; j++) {
                coefficients[j][new_i].Reduce();
                magCoeff[j] = coefficients[j][new_i].log2normApprox() + logwToThe[j + 1];
            }

            //Check to see if the approximation is no longer valid. The validity is checked if an arbitrary point we approximated differs from the point it should be by too much. That is the tolerancy which scales with the depth.
            //if (coefficients[numCoefficients - 2][new_i].times(tempLimit).norm_squared().compareTo(coefficients[numCoefficients - 1][new_i].times(DeltaSub0ToThe[numCoefficients]).norm_squared()) < 0) {
            //if(coefficients[numCoefficients - 2][new_i].norm_squared().divide(coefficients[numCoefficients - 1][new_i].norm_squared()).compareTo(tempLimit2) < 0) {
            if(i > 1 && isLastTermNotNegligible(magCoeff, oomDiff, numCoefficients)) {
            //if(i > 1 && isLastTermNotNegligible(coefficients, DeltaSub0ToThe, limit, new_i, numCoefficients)) {
                //|Bn+1 * d^2 * tolerance| < |Cn+1 * d^3|
                //When we're breaking here, it means that we've found a point where the approximation no longer works. Returning that would create a messed up image. We should move a little further back to get an approximation that is good.
                skippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
                return;
            }

            if(progress != null && i % 1000 == 0) {
                progress.setValue(i);
                progress.setString(SA_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (i) / progress.getMaximum() * 100)) + "%");
            }

        }

        i = length - 1;
        skippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
    }


    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || (isJulia && !juliter);
    }

    @Override
    public boolean supportsSeriesApproximation() {
        return !burning_ship && !isJulia;
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

    @Override
    public String getRefType() {
        return super.getRefType() + (burning_ship ? "-Burning Ship" : "") + (isJulia ? "-Julia-" + seed : "");
    }

    @Override
    public void function(BigComplex[] complex) {
        complex[0] = complex[0].square_plus_c(complex[1]);
    }

    public static void main(String[] args) {
        Mandelbrot a = new Mandelbrot(false);

        Reference = new Complex[2];
        Reference[0] = new Complex();
        Reference[1] = new Complex(0.32, 7.321);

        Referencex2 = new Complex[2];
        Referencex2[0] = new Complex();
        Referencex2[1] = Reference[0].times(2);

        ReferenceDeep = new MantExpComplex[2];
        ReferenceDeep[0] = new MantExpComplex(Reference[0]);
        ReferenceDeep[1] = new MantExpComplex(Reference[1]);

        ReferenceDeepx2 = new MantExpComplex[2];
        ReferenceDeepx2[0] = new MantExpComplex(Referencex2[0]);
        ReferenceDeepx2[1] = new MantExpComplex(Referencex2[1]);

        refPointSmall = new Complex(0.4, 0.0002);
        refPointSmallDeep = new MantExpComplex(refPointSmall);

        Complex Dn = new Complex(1.321, -4.21);
        Complex D0 = new Complex();
        MantExpComplex MDn = new MantExpComplex(Dn);
        MantExpComplex MD0 = new MantExpComplex(D0);

        Complex nzD0 = new Complex(-0.5, 1.4);
        MantExpComplex nzMD0 = new MantExpComplex(nzD0);

        System.out.println(a.perturbationFunction(Dn, D0, 1));
        System.out.println(a.perturbationFunction(Dn, 1));
        System.out.println(a.perturbationFunction(MDn, MD0, 1));
        System.out.println("Non Zero D0");
        System.out.println(a.perturbationFunction(Dn, nzD0, 1));
        System.out.println(a.perturbationFunction(MDn, nzMD0, 1));

        Mandelbrot b = new Mandelbrot(true);

        System.out.println();
        System.out.println(b.perturbationFunction(Dn, D0, 1));
        System.out.println(b.perturbationFunction(Dn, 1));
        System.out.println(b.perturbationFunction(MDn, MD0, 1));
        System.out.println("Non Zero D0");
        System.out.println(b.perturbationFunction(Dn, nzD0, 1));
        System.out.println(b.perturbationFunction(MDn, nzMD0, 1));
    }

}
