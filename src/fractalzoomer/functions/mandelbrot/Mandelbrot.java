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
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.nanomb1.Nanomb1;
import fractalzoomer.core.nanomb1.biPoly;
import fractalzoomer.core.nanomb1.tmpPoly;
import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.DistanceEstimator;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTime;
import fractalzoomer.utils.ColorAlgorithm;
import fractalzoomer.utils.NormComponents;
import fractalzoomer.utils.WorkSpaceData;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static fractalzoomer.main.Constants.*;

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

    private double exterior_de_factor;

    private boolean not_burning_ship;

    public Mandelbrot(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double height_ratio) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

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
        this.exterior_de_factor = exterior_de_factor;

        if (exterior_de) {
            if(height_ratio == 1) {
                limit = (size / ThreadDraw.IMAGE_SIZE) * exterior_de_factor;
            }
            else {
                double a = size / ThreadDraw.IMAGE_SIZE;
                double b = a * height_ratio;
                double c = Math.sqrt(a * a + b * b);
                limit = c * exterior_de_factor;
            }
            limit = limit * limit;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);


        if((ThreadDraw.PERTURBATION_THEORY || ThreadDraw.HIGH_PRECISION_CALCULATION) && out_coloring_algorithm == MainWindow.DISTANCE_ESTIMATOR) {
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

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double height_ratio, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

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
        this.exterior_de_factor = exterior_de_factor;

        if (exterior_de) {
            if(height_ratio == 1) {
                limit = (size / ThreadDraw.IMAGE_SIZE) * exterior_de_factor;
            }
            else {
                double a = size / ThreadDraw.IMAGE_SIZE;
                double b = a * height_ratio;
                double c = Math.sqrt(a * a + b * b);
                limit = c * exterior_de_factor;
            }
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
        not_burning_ship = !burning_ship;

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
        not_burning_ship = !burning_ship;

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

                    res = getFinalValueOut(res, complex[0]);

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

        in = getFinalValueIn(in, complex[0]);

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

                    res = getFinalValueOut(res, complex[0]);

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

        in = getFinalValueIn(in, complex[0]);

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

                res = getFinalValueOut(res, complex[0]);

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

        in = getFinalValueIn(in, complex[0]);

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

                res = getFinalValueOut(res, complex[0]);

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

        in = getFinalValueIn(in, complex[0]);

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

                    res = getFinalValueOut(res, complex[0]);

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

                    res = getFinalValueOut(res, complex[0]);

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

                res = getFinalValueOut(res, complex[0]);

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

                res = getFinalValueOut(res, complex[0]);

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
    public void calculateReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int[] Iterations, int[] juliaIterations, Location externalLocation, JProgressBar progress) {

        LastCalculationSize = size;

        long time = System.currentTimeMillis();

        int max_ref_iterations = getReferenceMaxIterations();

        int iterations = Iterations[0];
        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_ref_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        boolean detectPeriod = ThreadDraw.DETECT_PERIOD && supportsPeriod() && getUserPeriod() == 0;
        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, detectPeriod);

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                referenceData.createAndSetShortcut(max_ref_iterations,false, 0);
            }
            else {
                referenceData.deallocate();
            }

            if (deepZoom) {
                referenceDeepData.createAndSetShortcut(max_ref_iterations,false, 0);
            }
        } else if (max_ref_iterations > getReferenceLength()) {
            if(lowPrecReferenceOrbitNeeded) {
                referenceData.resize(max_ref_iterations);
            }
            else {
                referenceData.deallocate();
            }

            if (deepZoom) {
                referenceDeepData.resize(max_ref_iterations);
            }
        }

        if(iterations == 0) {
            //DetectedAtomPeriod = 0;
            DetectedPeriod = 0;
        }

        boolean gatherTinyRefPts = ThreadDraw.PERTUBATION_PIXEL_ALGORITHM == 1 && supportsScaledIterations() && deepZoom && ThreadDraw.GATHER_TINY_REF_INDEXES;

        Location loc = new Location();

        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared, r = null, r0 = null, norm = null;

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;
        int detectPeriodAlgorithm = getPeriodDetectionAlgorithm();

        if(useBignum) {
            if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
                BigNumComplex bn = inputPixel.toBigNumComplex();
                z = iterations == 0 ? (isJulia ? bn : new BigNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new BigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new BigNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? bn : new BigNumComplex();
                c0 = c;
                pixel = bn;
                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    r0 = new BigNum(size);
                    r = iterations == 0 ? new BigNum((BigNum) r0) : referenceData.lastRValue;
                }
            }
            else if(bigNumLib == BIGNUM_BIGINT) {
                BigIntNumComplex bn = inputPixel.toBigIntNumComplex();
                z = iterations == 0 ? (isJulia ? bn : new BigIntNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new BigIntNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new BigIntNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? bn : new BigIntNumComplex();
                c0 = c;
                pixel = bn;
                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    r0 = new BigIntNum(size);
                    r = iterations == 0 ? new BigIntNum((BigIntNum) r0) : referenceData.lastRValue;
                }
            }
            else if(bigNumLib == Constants.BIGNUM_MPFR) {
                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? (isJulia ? bn : new MpfrBigNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new MpfrBigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? new MpfrBigNumComplex(bn) : new MpfrBigNumComplex();
                c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
                pixel = new MpfrBigNumComplex(bn);
                if(detectPeriod && detectPeriodAlgorithm == 0) {
//                    referenceData.minValue = iterations == 0 ? MpfrBigNum.getMax() : referenceData.minValue;
                    r0 = new MpfrBigNum(size);
                    r = iterations == 0 ? new MpfrBigNum((MpfrBigNum) r0) : referenceData.lastRValue;
                }
            }
            else if(bigNumLib == Constants.BIGNUM_MPIR) {
                MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
                z = iterations == 0 ? (isJulia ? bn : new MpirBigNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new MpirBigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? new MpirBigNumComplex(bn) : new MpirBigNumComplex();
                c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
                pixel = new MpirBigNumComplex(bn);

                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    r0 = new MpirBigNum(size);
                    r = iterations == 0 ? new MpirBigNum((MpirBigNum) r0) : referenceData.lastRValue;
                }
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? (isJulia ? ddn : new DDComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : ddn;
                zold = iterations == 0 ? new DDComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new DDComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? ddn : new DDComplex();
                c0 = c;
                pixel = ddn;
                if(detectPeriod && detectPeriodAlgorithm == 0) {
//                    referenceData.minValue = iterations == 0 ? new DoubleDouble(Double.MAX_VALUE) : referenceData.minValue;
                    r0 = new DoubleDouble(size);
                    r = iterations == 0 ? new DoubleDouble((DoubleDouble) r0) : referenceData.lastRValue;
                }
            }
            else {
                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? (isJulia ? bn : new Complex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new Complex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new Complex() : referenceData.thirdTolastZValue;
                start = isJulia ? new Complex(bn) : new Complex();
                c0 = new Complex((Complex) c);
                pixel = new Complex(bn);
                if(detectPeriod && detectPeriodAlgorithm == 0) {
                   // referenceData.minValue = iterations == 0 ? Double.MAX_VALUE : referenceData.minValue;
                    r0 = size.doubleValue();
                    r = iterations == 0 ? r0 : referenceData.lastRValue;
                }
            }
        }
        else {
            z = iterations == 0 ? (isJulia ? inputPixel : new BigComplex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(useBignum, bigNumLib) : inputPixel;
            zold = iterations == 0 ? new BigComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? inputPixel : new BigComplex();
            c0 = c;
            pixel = inputPixel;
            if(detectPeriod && detectPeriodAlgorithm == 0) {
//                referenceData.minValue = iterations == 0 ? new MyApfloat(Double.MAX_VALUE) : referenceData.minValue;
                r0 = size;
                r = iterations == 0 ? r0 : referenceData.lastRValue;
            }
        }

        normSquared = z.normSquared();

        refPoint = inputPixel;
        refPointSmall = refPoint.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
        }

        RefType = getRefType();

        boolean isNanoMb1InUse = ThreadDraw.APPROXIMATION_ALGORITHM == 3 && supportsNanomb1();
        boolean isSeriesInUse = ThreadDraw.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation();
        boolean isBLAInUse = ThreadDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation();
        boolean isBLA2InUse = ThreadDraw.APPROXIMATION_ALGORITHM == 4 && supportsBilinearApproximation2();

        boolean usesCircleBail = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        boolean preCalcNormData = (detectPeriod && detectPeriodAlgorithm == 0);
        NormComponents normData = null;

        boolean isMpfrComplex = z instanceof MpfrBigNumComplex;
        boolean isMpirComplex = z instanceof MpirBigNumComplex;

        boolean stopReferenceCalculationOnDetectedPeriod = detectPeriod && ThreadDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD && userPeriod == 0 && canStopOnDetectedPeriod();

        Complex dzdc = null;
        MantExpComplex mdzdc = null;

        MantExp mradius = null;
        double radius = 0;

        if(detectPeriod && DetectedPeriod == 0 && detectPeriodAlgorithm == 1) {
            if (iterations == 0) {
                if (deepZoom) {
                    mdzdc = new MantExpComplex(1, 0);
                } else {
                    dzdc = new Complex(1, 0);
                }
            } else {
                if (deepZoom) {
                    mdzdc = referenceData.mdzdc;
                } else {
                    dzdc = referenceData.dzdc;
                }
            }

            if(deepZoom) {
                mradius = externalLocation.getSize().multiply2_mutable();
            }
            else {
                radius = this.size * 2;
            }
        }

        Complex cz = null;
        MantExpComplex mcz = null;

        for (; iterations < max_ref_iterations; iterations++) {

            if(lowPrecReferenceOrbitNeeded) {
                cz = z.toComplex();

                if (cz.isInfinite()) {
                    break;
                }
                setArrayValue(reference, iterations, cz);

                if(gatherTinyRefPts) {
                    if (burning_ship) {
                        if(cz.getAbsRe() < scaledE || cz.getAbsIm() < scaledE || cz.hypot() < scaledE) {
                            tinyRefPts.add(iterations);
                        }
                    } else {
                        if(cz.hypot() < scaledE) {
                            tinyRefPts.add(iterations);
                        }
                    }
                }
            }

            if(deepZoom) {
                mcz = loc.getMantExpComplex(z);
                setArrayDeepValue(referenceDeep, iterations, mcz);
            }

            if(stopReferenceCalculationOnDetectedPeriod && DetectedPeriod != 0) {
                break;
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            if(detectPeriod) {
               if(detectPeriodAlgorithm == 0) {
                   if (useBignum) {
                       if (bigNumLib == Constants.BIGNUM_BUILT_IN) {
//                        if (iterations > 0 && ((BigNum) normSquared).compareBothPositive((BigNum) referenceData.minValue) < 0) {
//                            DetectedAtomPeriod = iterations;
//                            referenceData.minValue = normSquared;
//                        }

                           if (DetectedPeriod == 0 && ((BigNum) r).compare((BigNum) (norm = ((BigNum) normSquared).sqrt())) > 0 && iterations > 0) {
                               DetectedPeriod = iterations;
                           }
                       }
                       else if (bigNumLib == Constants.BIGNUM_BIGINT) {
                           if (DetectedPeriod == 0 && ((BigIntNum) r).compare((BigIntNum) (norm = ((BigIntNum) normSquared).sqrt())) > 0 && iterations > 0) {
                               DetectedPeriod = iterations;
                           }
                       }
                       else if (bigNumLib == Constants.BIGNUM_MPFR) {
//                        if (iterations > 0 && ((MpfrBigNum) normSquared).compare((MpfrBigNum) referenceData.minValue) < 0) {
//                            DetectedAtomPeriod = iterations;
//                            ((MpfrBigNum) referenceData.minValue).set((MpfrBigNum)normSquared);
//                        }

                           if (DetectedPeriod == 0 && ((MpfrBigNum) r).compare((MpfrBigNum) (norm = ((MpfrBigNum) normSquared).sqrt(workSpaceData.tempPvar2))) > 0 && iterations > 0) {
                               DetectedPeriod = iterations;
                           }
                       }
                       else if (bigNumLib == Constants.BIGNUM_MPIR) {

                           if (DetectedPeriod == 0 && ((MpirBigNum) r).compare((MpirBigNum) (norm = ((MpirBigNum) normSquared).sqrt(workSpaceData.tempPvar2p))) > 0 && iterations > 0) {
                               DetectedPeriod = iterations;
                           }
                       }
                       else if (bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
//                        if (iterations > 0 && ((DoubleDouble) normSquared).compareTo(referenceData.minValue) < 0) {
//                            DetectedAtomPeriod = iterations;
//                            referenceData.minValue = normSquared;
//                        }

                           if (DetectedPeriod == 0 && ((DoubleDouble) r).compareTo(norm = ((DoubleDouble) normSquared).sqrt()) > 0 && iterations > 0) {
                               DetectedPeriod = iterations;
                           }
                       } else {
//                        if (iterations > 0 && ((double) normSquared) < ((double) referenceData.minValue)){
//                            DetectedAtomPeriod = iterations;
//                            referenceData.minValue = normSquared;
//                        }

                           if (DetectedPeriod == 0 && ((double) r) > (double) (norm = Math.sqrt((double) normSquared)) && iterations > 0) {
                               DetectedPeriod = iterations;
                           }
                       }
                   } else {
//                    if(iterations > 0 && ((Apfloat)normSquared).compareTo((Apfloat)referenceData.minValue) < 0) {
//                        DetectedAtomPeriod = iterations;
//                        referenceData.minValue = normSquared;
//                    }

                       if (DetectedPeriod == 0 && ((Apfloat) r).compareTo((Apfloat) (norm = MyApfloat.fp.sqrt((Apfloat) normSquared))) > 0 && iterations > 0) {
                           DetectedPeriod = iterations;
                       }
                   }
               }
               else {
                   if (DetectedPeriod == 0 && iterations > 0) {
                       if (deepZoom) {
                           if (mradius.multiply(mdzdc.chebychevNorm()).compareToBothPositive(mcz.chebychevNorm()) > 0) {
                               DetectedPeriod = iterations;
                           }
                       } else {
                           if (radius * dzdc.chebychevNorm() > cz.chebychevNorm()) {
                               DetectedPeriod = iterations;
                           }
                       }
                   }
               }
            }

            if (iterations > 0 && bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel, cz, mcz)) {
                break;
            }

            if(!usesCircleBail) {
                zold2.set(zold);
                zold.set(z);
            }

            try {
                if(detectPeriod && DetectedPeriod == 0) {
                    if (detectPeriodAlgorithm == 1) {
                        if (deepZoom) {
                            mdzdc = mcz.times2().times_mutable(mdzdc).plus_mutable(MantExp.ONE);
                            mdzdc.Reduce();
                        } else {
                            dzdc = cz.times2().times_mutable(dzdc).plus_mutable(1);
                        }
                    }
                    else {
                        r = calculateR(r, r0, normSquared, norm, workSpaceData);
                    }
                }

                if(preCalcNormData) {
                    if (burning_ship) {
                        z = z.abs_mutable().squareFast_plus_c_mutable(normData, c);
                    } else {
                        z = z.squareFast_plus_c_mutable(normData, c);
                    }
                }
                else {
                    if(isMpfrComplex) {
                        if (burning_ship) {
                            z = z.abs_mutable().square_plus_c_mutable(c, workSpaceData.temp1, workSpaceData.temp2);
                        }
                        else {
                            z = z.square_plus_c_mutable(c, workSpaceData.temp1, workSpaceData.temp2);
                        }
                    }
                    else if(isMpirComplex) {
                        if (burning_ship) {
                            z = z.abs_mutable().square_plus_c_mutable(c, workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p);
                        }
                        else {
                            z = z.square_plus_c_mutable(c, workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p);
                        }
                    }
                    else {
                        if (burning_ship) {
                            z = z.abs_mutable().square_plus_c_mutable(c);
                        } else {
                            z = z.square_plus_c_mutable(c);
                        }
                    }
                }
            }
            catch (Exception ex) {
                break;
            }


            if(progress != null && iterations % 1000 == 0) {
                progress.setValue(iterations - initIterations);
                progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (iterations - initIterations) / progress.getMaximum() * 100)) + "%");
            }

        }

        referenceData.lastRValue = r;
        referenceData.lastZValue = z;
        referenceData.secondTolastZValue = zold;
        referenceData.thirdTolastZValue = zold2;
        referenceData.dzdc = dzdc;
        referenceData.mdzdc = mdzdc;

        referenceData.MaxRefIteration = iterations - 1;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        ReferenceCalculationTime = System.currentTimeMillis() - time;

        if(isJulia) {
            calculateJuliaReferencePoint(inputPixel, size, deepZoom, juliaIterations, progress);
        }

        skippedIterations = 0;
        if(isSeriesInUse) {
            calculateSeriesWrapper(size, deepZoom, externalLocation, progress);
        }
        else if(isNanoMb1InUse) {
            calculateNanomb1Wrapper(deepZoom, progress);
        }
        else if(isBLAInUse) {
            calculateBLAWrapper(deepZoom, externalLocation, progress);
        }
        else if(isBLA2InUse) {
            calculateBLA2Wrapper(deepZoom, externalLocation, progress);
        }


        if(gatherTinyRefPts) {
            tinyRefPtsArray = tinyRefPts.stream().mapToInt(i -> i).toArray();
        }
        else {
            tinyRefPtsArray = new int[0];
        }
    }

    @Override
    public Object calculateR(Object rIn, Object r0In, Object normSquared, Object norm, WorkSpaceData workSpaceData) {

        if(rIn instanceof BigNum) {
            BigNum r = (BigNum)rIn;
            BigNum r0 = (BigNum)r0In;
            BigNum az = ((BigNum)norm);
            BigNum azsquare = (BigNum)normSquared;

            return az.add(r).squareFull().sub(azsquare).add(r0);
        }
        else if(rIn instanceof BigIntNum) {
            BigIntNum r = (BigIntNum)rIn;
            BigIntNum r0 = (BigIntNum)r0In;
            BigIntNum az = ((BigIntNum)norm);
            BigIntNum azsquare = (BigIntNum)normSquared;

            return az.add(r).square().sub(azsquare).add(r0);
        }
        else if(rIn instanceof MpfrBigNum) {
            MpfrBigNum r = (MpfrBigNum)rIn;
            MpfrBigNum r0 = (MpfrBigNum)r0In;
            MpfrBigNum az = (MpfrBigNum)norm;
            MpfrBigNum azsquare = (MpfrBigNum)normSquared;

            MpfrBigNum.r_ball_pow2(r, az, r0, azsquare);
            return r;
        }
        else if(rIn instanceof MpirBigNum) {
            MpirBigNum r = (MpirBigNum)rIn;
            MpirBigNum r0 = (MpirBigNum)r0In;
            MpirBigNum az = (MpirBigNum)norm;
            MpirBigNum azsquare = (MpirBigNum)normSquared;

            MpirBigNum.r_ball_pow2(r, az, r0, azsquare);
            return r;
        }
        else if(rIn instanceof DoubleDouble) {
            DoubleDouble r = (DoubleDouble)rIn;
            DoubleDouble r0 = (DoubleDouble)r0In;
            DoubleDouble az = ((DoubleDouble)norm);
            DoubleDouble azsquare = (DoubleDouble)normSquared;
            return az.add(r).sqr().subtract(azsquare).add(r0);
        }
        else if(rIn instanceof Double) {
            double r = (double)rIn;
            double r0 = (double)r0In;
            double az = (double)norm;
            double azsquare = (double)normSquared;

            double temp = az + r;
            return temp * temp - azsquare + r0;
        }
        else {
            Apfloat r = (Apfloat)rIn;
            Apfloat r0 = (Apfloat)r0In;
            Apfloat az = (Apfloat)norm;
            Apfloat azsquare = (Apfloat)normSquared;
            Apfloat temp = MyApfloat.fp.add(az, r);
            return MyApfloat.fp.add(MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp, temp), azsquare), r0);
        }
    }

    @Override
    protected GenericComplex referenceFunction(GenericComplex z, GenericComplex c, NormComponents normData, GenericComplex[] initialPrecal, GenericComplex[] precalc) {
        if(normData != null) {
            if (burning_ship) {
                z = z.abs_mutable().squareFast_plus_c_mutable(normData, c);
            } else {
                z = z.squareFast_plus_c_mutable(normData, c);
            }
        }
        else {
            if(z instanceof MpfrBigNumComplex) {
                if (burning_ship) {
                    z = z.abs_mutable().square_plus_c_mutable(c, workSpaceData.temp1, workSpaceData.temp2);
                }
                else {
                    z = z.square_plus_c_mutable(c, workSpaceData.temp1, workSpaceData.temp2);
                }
            }
            else if(z instanceof MpirBigNumComplex) {
                if (burning_ship) {
                    z = z.abs_mutable().square_plus_c_mutable(c, workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p);
                }
                else {
                    z = z.square_plus_c_mutable(c, workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p);
                }
            }
            else {
                if (burning_ship) {
                    z = z.abs_mutable().square_plus_c_mutable(c);
                } else {
                    z = z.square_plus_c_mutable(c);
                }
            }
        }

        return z;
    }

    @Override
    protected void calculateNanomb1(boolean deepZoom, JProgressBar progress) {

        long value = ((long)getNanomb1MaxIterations() * 2 - 1);
        long divisor = value > Constants.MAX_PROGRESS_VALUE ? value / 100 : 1;

        int m = ThreadDraw.NANOMB1_M;
        int n = ThreadDraw.NANOMB1_N;
        biPoly fp = new biPoly(m, n);
        int max_ref_iterations_period = getNanomb1MaxIterations();
        long total = 1;
        for(int iteration = 1; iteration < max_ref_iterations_period; iteration++, total++) {
            if(deepZoom) {
                fp.cstep(getArrayDeepValue(referenceDeep, iteration));
            }
            else {
                fp.cstep(new MantExpComplex(getArrayValue(reference, iteration)));
            }

            if(progress != null && total % 50 == 0) {
                int val = (int)(total / divisor);
                progress.setValue(val);
                progress.setString(NANOMB1_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }
        }

        //fp.print();

        MantExp Bout = fp.getRadius();

        //System.out.println("R == " + Bout);

        tmpPoly tp = new tmpPoly(fp);
        MantExpComplex nucleusPos = tp.getRoot();

        //System.out.println("Root == " + nucleusPos);

        MantExpComplex zlo1 = new MantExpComplex();
        MantExpComplex zlo;

        DeepReference ref1Deep = null;

        int max_ref_iterations = getReferenceMaxIterations();

        DoubleReference ref1 = new DoubleReference(max_ref_iterations_period, max_ref_iterations);

        if (deepZoom) {
            ref1Deep = new DeepReference(max_ref_iterations_period, max_ref_iterations);
        }

        boolean gatherTinyRefPts = ThreadDraw.PERTUBATION_PIXEL_ALGORITHM == 1 && supportsScaledIterations() && deepZoom && ThreadDraw.GATHER_TINY_REF_INDEXES;

        if(gatherTinyRefPts && !tinyRefPts.isEmpty()) {
            tinyRefPts.clear();
        }

        MantExpComplex temp;
        for(int iteration = 0; iteration < max_ref_iterations_period; iteration++, total++){

            if(deepZoom) {
                zlo = getArrayDeepValue(referenceDeep, iteration);
                temp = zlo.plus(zlo1);
                setArrayDeepValue(ref1Deep, iteration, temp);

                Complex cz = temp.toComplex();
                setArrayValue(ref1, iteration, cz);

                if(gatherTinyRefPts && cz.hypot() < scaledE) {//burning_ship is not implemented for this
                    tinyRefPts.add(iteration);
                }
            }
            else {
                zlo = new MantExpComplex(getArrayValue(reference, iteration));
                setArrayValue(ref1, iteration, zlo.plus(zlo1).toComplex());
            }

            zlo1 = zlo1.times(zlo1.plus(zlo.times2())).plus_mutable(nucleusPos);
            zlo1.Reduce();

            if(progress != null && total % 50 == 0) {
                int val = (int)(total / divisor);
                progress.setValue(val);
                progress.setString(NANOMB1_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }
        }

        referenceData.setReference(ref1);

        if(deepZoom) {
            referenceDeepData.setReference(ref1Deep);
        }

        nanomb1 = new Nanomb1(fp, nucleusPos, Bout);
    }

    @Override
    public Complex perturbationFunctionScaled(Complex DeltaSubN, Complex DeltaSub0, double s, int RefIteration) {
        if(not_burning_ship) {
            if(s == 0) {
                return getArrayValue(reference, RefIteration).times_mutable(2).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
            }
            else {
                return getArrayValue(reference, RefIteration).times_mutable(2).plus_mutable(DeltaSubN.times(s)).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
            }
        }
        else {
            Complex X = getArrayValue(reference, RefIteration);
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();

            if(s == 0) {
                return new Complex(2.0 * (r * a - i * b), 2.0 * Complex.sign(r) * Complex.sign(i) * (r * b + i * a)).plus_mutable(DeltaSub0);
            }
            else {
                double scaledA = a * s;
                double scaledB = b * s;
                return new Complex((2.0 * r + scaledA) * a - (2.0 * i + scaledB) * b, Complex.DiffAbs(r * i / s, r * b + (i + scaledB) * a) * 2).plus_mutable(DeltaSub0);
            }
        }
    }

    @Override
    public Complex perturbationFunctionScaled(Complex DeltaSubN, double s, int RefIteration) {
        if(not_burning_ship) {
            if(s == 0) {
                return getArrayValue(reference, RefIteration).times_mutable(2).times_mutable(DeltaSubN);
            }
            else {
                return getArrayValue(reference, RefIteration).times_mutable(2).plus_mutable(DeltaSubN.times(s)).times_mutable(DeltaSubN);
            }
        }
        else {
            Complex X = getArrayValue(reference, RefIteration);
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();

            if(s == 0) {
                return new Complex(2.0 * (r * a - i * b), 2.0 * Complex.sign(r) * Complex.sign(i) * (r * b + i * a));
            }
            else {
                double scaledA = a * s;
                double scaledB = b * s;
                return new Complex((2.0 * r + scaledA) * a - (2.0 * i + scaledB) * b, Complex.DiffAbs(r * i / s, r * b + (i + scaledB) * a) * 2);
            }
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayValue(Reference, RefIteration).times_mutable(2)).plus_mutable(DeltaSubN.square()).plus_mutable(DeltaSub0);
            return getArrayValue(reference, RefIteration).times_mutable(2).plus_mutable(DeltaSubN).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
        else {
            Complex X = getArrayValue(reference, RefIteration);
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();

            return new Complex((2.0 * r + a) * a - (2.0 * i + b) * b, Complex.DiffAbs(r * i, r * b + (i + b) * a) * 2).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayDeepValue(ReferenceDeep, RefIteration).times2_mutable()).plus_mutable(DeltaSubN.square()).plus_mutable(DeltaSub0);
            return getArrayDeepValue(referenceDeep, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
        else {
            MantExpComplex X = getArrayDeepValue(referenceDeep, RefIteration);
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();


            return new MantExpComplex(r.multiply2().add_mutable(a).multiply_mutable(a).subtract_mutable(i.multiply2().add_mutable(b).multiply_mutable(b)), MantExpComplex.DiffAbs(r.multiply(i), r.multiply(b).add_mutable(i.add(b).multiply_mutable(a))).multiply2_mutable()).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayValue(Reference, RefIteration).times_mutable(2)).plus_mutable(DeltaSubN.square());
            return getArrayValue(reference, RefIteration).times_mutable(2).plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
        }
        else {
            Complex X = getArrayValue(reference, RefIteration);
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            return new Complex((2.0 * r + a) * a - (2.0 * i + b) * b, Complex.DiffAbs(r * i, r * b + (i + b) * a) * 2);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayDeepValue(ReferenceDeep, RefIteration).times2_mutable()).plus_mutable(DeltaSubN.square());
            return getArrayDeepValue(referenceDeep, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
        }
        else {
            MantExpComplex X = getArrayDeepValue(referenceDeep, RefIteration);
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();

            return new MantExpComplex(r.multiply2().add_mutable(a).multiply_mutable(a).subtract_mutable(i.multiply2().add_mutable(b).multiply_mutable(b)), MantExpComplex.DiffAbs(r.multiply(i), r.multiply(b).add_mutable(i.add(b).multiply_mutable(a))).multiply2_mutable());
        }
    }

    @Override
    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc, JProgressBar progress) {

        skippedIterations = 0;

        int numCoefficients = ThreadDraw.SERIES_APPROXIMATION_TERMS;

        if (numCoefficients < 2 || dsize.compareTo(MyApfloat.SA_START_SIZE) > 0) {
            return;
        }

        SATerms = numCoefficients;

        /*MantExpComplex[] DeltaSub0ToThe = new MantExpComplex[numCoefficients + 1];*/

        long[] logwToThe  = new long[numCoefficients + 1];

        final long[] magCoeff = new long[numCoefficients];

        /*if(deepZoom) {
            DeltaSub0ToThe[1] = new MantExpComplex(dsizeMantExp, dsizeMantExp);
        }
        else {
            //DeltaSub0ToThe[1] = new MantExpComplex(sqrt2 * size, sqrt2 * size);
        }*/

        SASize = loc.getMaxSizeInImage().log2approx();
        logwToThe[1] = SASize;

        for (int i = 2; i <= numCoefficients; i++) {
            //DeltaSub0ToThe[i] = DeltaSub0ToThe[i - 1].times(DeltaSub0ToThe[1]);
            //DeltaSub0ToThe[i].Reduce();
            logwToThe[i] = logwToThe[1] * i;
        }

        coefficients = new DeepReference(numCoefficients * max_data);

        setSACoefficient(0, 0, new MantExpComplex(1, 0));

        for(int i = 1; i < numCoefficients; i++){
            setSACoefficient(i, 0, new MantExpComplex());
        }

        //MantExp limit = DeltaSub0ToThe[numCoefficients].norm_squared().multiply_mutable(new MantExp(MyApfloat.reciprocal(ThreadDraw.SERIES_APPROXIMATION_TOLERANCE.multiply(ThreadDraw.SERIES_APPROXIMATION_TOLERANCE))));

        long oomDiff = ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE;
        int SAMaxSkipIter = ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER;

        //int length = max_iterations;
        //int dataLength = deepZoom ? ReferenceDeep.length() : (Reference.length >> 1);
        int length = deepZoom ? referenceDeep.length() : reference.length();

        //int batches = 8;
        //int batchSize = numCoefficients / batches;
        //int leftOvers = numCoefficients % batches;
        //int batchLooplastIndex = batchSize * batches - 1;
        int lastIndex = numCoefficients - 1;
        //boolean doExtra = batchSize % 2 == 1;
        //int batchLoopLength = batchSize >> 1;
        boolean useThreads = ThreadDraw.USE_THREADS_FOR_SA;//numCoefficients > 32;


        int i;
        //int circleIndex = 0;
        for(i = 1; i < length; i++) {

            /*
            int index;
            if(i - 1 > MaxRefIteration) {
                index = circleIndex % dataLength + 1;
                circleIndex++;
                circleIndex = circleIndex % (dataLength - 1);

            }
            else {
                index = (i - 1);
            }*/

            if(i - 1 > referenceData.MaxRefIteration) {
                skippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                return;
            }

            MantExpComplex twoRef = null;

            if(deepZoom) {
                twoRef = getArrayDeepValue(referenceDeep, i - 1).times2_mutable();
            }
            else {
               twoRef = new MantExpComplex(getArrayValue(reference, i - 1).times_mutable(2));
            }

            //MantExpComplex twoAn = null;

            int new_i = i;
            int old_i = (i - 1);

            /*MantExpComplex coef0i = null;
            MantExpComplex coef1i = null;
            MantExpComplex coef2i = null;
            MantExpComplex coef3i = null;
            MantExpComplex coef4i = null;

            if (numCoefficients >= 1) {
                //A
                coef0i = getSACoefficient(0, old_i);
                MantExpComplex temp = coef0i.times(twoRef).plus_mutable(MantExp.ONE); // An+1 = 2XnAn + 1
                temp.Reduce();
                magCoeff[0] = temp.log2normApprox() + logwToThe[1];
                setSACoefficient(0, new_i, temp);
            }
            if (numCoefficients >= 2) {
                //B
                coef1i = getSACoefficient(1, old_i);
                MantExpComplex temp = coef1i.times(twoRef).plus_mutable(coef0i.square()); // Bn+1 = 2XnBn + An^2
                temp.Reduce();
                magCoeff[1] = temp.log2normApprox() + logwToThe[2];
                setSACoefficient(1, new_i, temp);
            }
            if (numCoefficients >= 3) {
                //C
                coef2i = getSACoefficient(2, old_i);
                twoAn = coef0i.times2();
                MantExpComplex temp = coef2i.times(twoRef).plus_mutable(coef1i.times(twoAn)); // Cn+1 = 2XnCn + 2AnBn
                temp.Reduce();
                magCoeff[2] = temp.log2normApprox() + logwToThe[3];
                setSACoefficient(2, new_i, temp);
            }
            if (numCoefficients >= 4) {
                //D
                coef3i = getSACoefficient(3, old_i);
                MantExpComplex temp = coef3i.times(twoRef).plus_mutable(twoAn.times(coef2i)).plus_mutable(coef1i.square()); //Dn+1 = 2XnCn + 2AnCn + Bn^2
                temp.Reduce();
                magCoeff[3] = temp.log2normApprox() + logwToThe[4];
                setSACoefficient(3, new_i, temp);
            }
            if (numCoefficients >= 5) {
                //E
                coef4i = getSACoefficient(4, old_i);
                MantExpComplex temp = coef4i.times(twoRef).plus_mutable(twoAn.times(coef3i)).plus_mutable(coef1i.times(coef2i).times2_mutable()); //En+1 = 2XnEn + 2AnDn + 2BnCn
                temp.Reduce();
                magCoeff[4] = temp.log2normApprox() + logwToThe[5];
                setSACoefficient(4, new_i, temp);
            }*/

            //if(numCoefficients >= 6) {
            /*//k = 5
            for(int k = 0; k < numCoefficients; k++) {
                MantExpComplex sum = k == 0 ? new MantExpComplex(1, 0) : new MantExpComplex();

                int calcLength = (k >> 1);

                int j = 0;

                if(calcLength != 0) {
                    for (; j < calcLength; j++) {
                        sum = sum.plus_mutable(getSACoefficient(j, old_i).times(getSACoefficient(k - j - 1, old_i)));
                    }

                    sum = sum.times2_mutable();
                }

                if(k % 2 == 1) {
                    sum = sum.plus_mutable(getSACoefficient(j, old_i).square());
                }

                MantExpComplex temp = getSACoefficient(k, old_i).times(twoRef).plus_mutable(sum);

                temp.Reduce();
                magCoeff[k] = temp.log2normApprox() + logwToThe[k + 1];
                setSACoefficient(k, new_i, temp);
            }*/


            final MantExpComplex twoRefFinal = twoRef;

            if(useThreads) {
               // if(batchSize != 0) {
//                    IntStream.range(0, batches).parallel().forEach(b -> {
//
//                        int offset = b * batchSize;
//                        for (int m = 0, k = offset; m < batchSize; m++, k++) { //Split in segments
//                            calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
//                        }
//////                        for (int m = 0, k = b; m < batchSize; m++, k += batches) { // Get one every multiple of batch + b
//////                            calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
//////                        }
////
////                        int k = b;
////                        for (int m = 0; m < batchLoopLength; m++, k += batches) { // Get first-last gauss sum style
////                            calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
////                            calcCoeffs(batchLooplastIndex - k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
////                        }
////                        if(doExtra) {
////                            calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
////                        }
//                    });


                //}

//                int offset = batches * batchSize;
//                for(int m = 0, k = offset; m < leftOvers; m++, k++) {
//                    calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
//                }

                IntStream.range(0, numCoefficients)
                        .parallel().forEach(k ->
                    calcCoeffs(lastIndex - k, old_i, new_i, twoRefFinal, magCoeff, logwToThe)
                );

            }
            else {
                for(int k = 0; k < numCoefficients; k++) {
                    calcCoeffs(k, old_i, new_i, twoRef, magCoeff, logwToThe);
                }
            }

            //Check to see if the approximation is no longer valid. The validity is checked if an arbitrary point we approximated differs from the point it should be by too much. That is the tolerancy which scales with the depth.
            //if (coefficients[numCoefficients - 2][new_i].times(tempLimit).norm_squared().compareTo(coefficients[numCoefficients - 1][new_i].times(DeltaSub0ToThe[numCoefficients]).norm_squared()) < 0) {
            //if(coefficients[numCoefficients - 2][new_i].norm_squared().divide(coefficients[numCoefficients - 1][new_i].norm_squared()).compareTo(tempLimit2) < 0) {
            if(i > 1 && (i >= SAMaxSkipIter || isLastTermNotNegligible(magCoeff, oomDiff, lastIndex))) {
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

    public static void calcCoeffs(int k, int old_i, int new_i, MantExpComplex twoRef, long[] magCoeff, long[] logwToThe) {
        MantExpComplex sum = k == 0 ? new MantExpComplex(1, 0) : new MantExpComplex();

        int calcLength = (k >> 1);

        int j = 0;

        int tempK1 = k - 1;
        if (calcLength != 0) {
            for (; j < calcLength; j++) {
                sum = sum.plus_mutable(getSACoefficient(j, old_i).times_mutable(getSACoefficient(tempK1 - j, old_i)));
            }

            sum = sum.times2_mutable();
        }

        if (k % 2 == 1) {
            sum = sum.plus_mutable(getSACoefficient(j, old_i).square_mutable());
        }

        MantExpComplex temp = getSACoefficient(k, old_i).times_mutable(twoRef).plus_mutable(sum);

        temp.Reduce();
        magCoeff[k] = temp.log2normApprox() + logwToThe[k + 1];
        setSACoefficient(k, new_i, temp);
    }

    @Override
    public Complex getBlaA(Complex Z) {
        return Z.times(2);
    }

    @Override
    public MantExpComplex getBlaA(MantExpComplex Z) {
        return Z.times2();
    }


    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || !juliter;
    }

    @Override
    public boolean supportsSeriesApproximation() {
        return !burning_ship && !isJulia;
    }

    @Override
    public boolean supportsBilinearApproximation() {
        return !burning_ship && !isJulia;
    }

    @Override
    public boolean supportsBilinearApproximation2() {
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
        return super.getRefType() + (burning_ship ? "-Burning Ship" : "") + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public void function(GenericComplex[] complex) {

        if(complex[0] instanceof MpfrBigNumComplex) {
            if(not_burning_ship) {
                complex[0] = complex[0].square_plus_c_mutable(complex[1], workSpaceData.temp1, workSpaceData.temp2);
            }
            else {
                complex[0] = complex[0].abs_mutable().square_plus_c_mutable(complex[1], workSpaceData.temp1, workSpaceData.temp2);
            }
        }
        else if(complex[0] instanceof MpirBigNumComplex) {
            if(not_burning_ship) {
                complex[0] = complex[0].square_plus_c_mutable_no_threads(complex[1], workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p);
            }
            else {
                complex[0] = complex[0].abs_mutable().square_plus_c_mutable_no_threads(complex[1], workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p);
            }
        }
        else {
            if(not_burning_ship) {
                complex[0] = complex[0].square_plus_c_mutable_no_threads(complex[1]);
            }
            else {
                complex[0] = complex[0].abs_mutable().square_plus_c_mutable_no_threads(complex[1]);
            }
        }

    }

    @Override
    public boolean supportsBignum() { return true;}

    @Override
    public boolean supportsBigIntnum() {
        return true;
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean supportsMpirBignum() {
        return true;
    }

    @Override
    public boolean supportsPeriod() {
        return !burning_ship && !isJulia;
    }

    @Override
    public boolean supportsScaledIterations() { return !isJulia; }

    @Override
    public boolean supportsNanomb1() {
        return !burning_ship && !isJulia && (getPeriod() != 0 || ThreadDraw.DETECT_PERIOD && supportsPeriod()) && size < 0x1.0p-32;
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, ReferenceData data, int RefIteration) {
        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayValue(Reference, RefIteration).times_mutable(2)).plus_mutable(DeltaSubN.square());
            return getArrayValue(data.Reference, RefIteration).times_mutable(2).plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
        }
        else {
            Complex X = getArrayValue(data.Reference, RefIteration);
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            return new Complex((2.0 * r + a) * a - (2.0 * i + b) * b, Complex.DiffAbs(r * i, r * b + (i + b) * a) * 2);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, ReferenceDeepData data, int RefIteration) {
        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayDeepValue(ReferenceDeep, RefIteration).times2_mutable()).plus_mutable(DeltaSubN.square());
            return getArrayDeepValue(data.Reference, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
        }
        else {
            MantExpComplex X = getArrayDeepValue(data.Reference, RefIteration);
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();

            return new MantExpComplex(r.multiply2().add_mutable(a).multiply_mutable(a).subtract_mutable(i.multiply2().add_mutable(b).multiply_mutable(b)), MantExpComplex.DiffAbs(r.multiply(i), r.multiply(b).add_mutable(i.add(b).multiply_mutable(a))).multiply2_mutable());
        }
    }

    @Override
    public boolean requiresVariablePixelSize() {
        return  exterior_de || super.requiresVariablePixelSize();
    }

    @Override
    public void setVariablePixelSize(MantExp pixelSize) {
        if(exterior_de) {
            limit = pixelSize.toDouble() * exterior_de_factor;
            limit = limit * limit;
        }
        super.setVariablePixelSize(pixelSize);
    }


    /*public static int secondOrderBallPeriod(Complex c0, double  size, int n) {
        double dx = size, dy = size;
        double r0 = Math.min(Math.abs(dx),Math.abs(dy));
        Complex z = new Complex();
        Complex dz = new Complex();
        double r = r0;
        double maxR = 1e5;
        double az = z.norm();
        double adz = dz.norm();
        double minz = 1e16;
        int minIter = -1;

        double mpow = 2;

        for (int k = 1; k < n; k++) {
        r = Math.pow(r, mpow) + 2 * (az + r0 * adz) * r + Math.pow(r0, mpow) * Math.pow(adz,  mpow);
        dz = z.times(dz).times(mpow).plus(1);//2 * z. * dz + 1;
        z = z.pow(mpow).plus(c0);
        az = z.norm();
        adz = dz.norm();
        if (az < minz){
                minz = az;
                minIter = k;
            }
        if ((r + r0 * adz) > az) {
            System.out.println("Ball: N-period found: " +k + ", atom: " + minIter);
            //fprintf('Ball: N-period found: %d, atom: %d\n', k, minIter);
            //if (~doCont)
              //  break;
            //end
            return k;
        }

        if (az > maxR || r > maxR) {
            System.out.println("Ball: escaping");
        break;
    }
    }
        return 0;
    }

    public static int firstOrderBallPeriod(Complex c0, double size, int n) {
        double dx = size, dy = size;
        double r0 = Math.min(Math.abs(dx),Math.abs(dy));
        Complex z = new Complex();
        double r = (r0);
        ArrayList<Integer> p = new ArrayList<>();
        double maxR = 1e5;
        double az = z.norm();

        double mpow = 2;

        for(int k = 1; k < n; k++) {
            r = Math.pow(az + r, mpow) - Math.pow(az, mpow) + r0;
            z = z.pow(mpow).plus(c0);
            az = z.norm();
            if (r > az) {
                p.add(k);
                System.out.println("findPeriodBallM3: N-period found: " +k);
                return k;
            }
            if (az > maxR || r > maxR) {
                System.out.println("Ball: escaping");
                break;
            }
        }

        return 0;
    }*/

    @Override
    public void createLowPrecisionOrbit(int maxRefIteration, ReferenceData refData, ReferenceDeepData refDeepData) {
        int length = maxRefIteration + 1;
        refData.createAndSetShortcut(length, false, 0);
        DoubleReference reference = refData.Reference;
        DeepReference deepReference = refDeepData.Reference;

        for (int i = 0; i < length; i++) {
            Fractal.setArrayValue(reference, i, Fractal.getArrayDeepValue(deepReference, i).toComplex());
        }

        reference.setLengthOverride(deepReference.length());
    }
}
