/* 
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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

import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.main.MainWindow;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.iteration_statistics.AtomDomain;
import fractalzoomer.fractal_options.iteration_statistics.CosArgDivideNormAverage;
import fractalzoomer.fractal_options.iteration_statistics.CurvatureAverage;
import fractalzoomer.fractal_options.iteration_statistics.StripeAverage;
import fractalzoomer.fractal_options.iteration_statistics.TriangleInequalityAverage;
import fractalzoomer.fractal_options.iteration_statistics.UserStatisticColoring;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;

import fractalzoomer.out_coloring_algorithms.DistanceEstimator;
import fractalzoomer.utils.ColorAlgorithm;
import java.util.ArrayList;

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

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

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
    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

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
    }

    //orbit
    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

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

    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

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

    }

    @Override
    protected void function(Complex[] complex) {

        type.getValue(complex[0]);
        complex[0].square_mutable_plus_c_mutable(complex[1]);
        type2.getValue(complex[0]);

    }

    private double mandel_2d_np_de_normal(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                if (inverse_dem) {
                    if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                        return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                    } else {

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                        }

                        Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                        double res = out_color_algorithm.getResult(object);

                        res = getFinalValueOut(res);

                        return res;
                    }

                } else if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                    }

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }

            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;
    }

    private double mandel_2d_np_de_dem(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                if (inverse_dem) {
                    if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                        return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                    } else {

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                        }

                        Object[] object = {iterations, complex[0], dc};
                        double res = out_color_algorithm.getResult(object);

                        res = getFinalValueOut(res);

                        return res;
                    }

                } else if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                    }

                    Object[] object = {iterations, complex[0], dc};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }

            dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;
    }

    private double mandel_2d_np_nde_normal(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                return res;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;
    }

    private double mandel_2d_np_nde_dem(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], dc};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                return res;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;
    }

    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {

        if (exterior_de) {
            if (special_alg == 0) {
                return mandel_2d_np_de_normal(pixel);
            } else {
                return mandel_2d_np_de_dem(pixel);
            }
        } else if (special_alg == 0) {
            return mandel_2d_np_nde_normal(pixel);
        } else {
            return mandel_2d_np_nde_dem(pixel);
        }

    }

    private double mandel_2d_p_de_normal(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);
        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                if (inverse_dem) {
                    if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                        return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                    } else {

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                        }

                        Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                        double res = out_color_algorithm.getResult(object);

                        res = getFinalValueOut(res);

                        return res;
                    }

                } else if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                    }

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double mandel_2d_p_de_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                if (inverse_dem) {
                    if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                        return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                    } else {

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                        }

                        Object[] object = {iterations, complex[0], dc};
                        double res = out_color_algorithm.getResult(object);

                        res = getFinalValueOut(res);

                        return res;
                    }

                } else if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                    }

                    Object[] object = {iterations, complex[0], dc};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }

            dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double mandel_2d_p_nde_normal(Complex pixel) {

        int iterations = 0;

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

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                return res;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double mandel_2d_p_nde_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], dc};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                return res;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2).plus_mutable(1);
            function(complex);

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
    public double calculateFractalWithPeriodicity(Complex pixel) {

        if (exterior_de) {
            if (special_alg == 0) {
                return mandel_2d_p_de_normal(pixel);
            } else {
                return mandel_2d_p_de_dem(pixel);
            }
        } else if (special_alg == 0) {
            return mandel_2d_p_nde_normal(pixel);
        } else {
            return mandel_2d_p_nde_dem(pixel);
        }
    }

    private double julia_2d_p_de_normal(Complex pixel) {

        int iterations = 0;

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
        Complex dc = new Complex(1, 0);
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                if (inverse_dem) {
                    if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                        return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                    } else {

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                        }

                        Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                        double res = out_color_algorithm.getResult(object);

                        res = getFinalValueOut(res);

                        return res;
                    }

                } else if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                    }

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }
        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double julia_2d_p_de_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                if (inverse_dem) {
                    if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                        return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                    } else {

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                        }

                        Object[] object = {iterations, complex[0], dc};
                        double res = out_color_algorithm.getResult(object);

                        res = getFinalValueOut(res);

                        return res;
                    }

                } else if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                    }

                    Object[] object = {iterations, complex[0], dc};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }

            dc.times_mutable(complex[0]).times_mutable(2);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }
        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double julia_2d_p_nde_normal(Complex pixel) {

        int iterations = 0;

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

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                return res;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }
        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    private double julia_2d_p_nde_dem(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], dc};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                return res;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2);
            function(complex);

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
    public double calculateJuliaWithPeriodicity(Complex pixel) {

        if (exterior_de) {
            if (special_alg == 0) {
                return julia_2d_p_de_normal(pixel);
            } else {
                return julia_2d_p_de_dem(pixel);
            }
        } else if (special_alg == 0) {
            return julia_2d_p_nde_normal(pixel);
        } else {
            return julia_2d_p_nde_dem(pixel);
        }
    }

    private double julia_2d_np_de_normal(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex dc = new Complex(1, 0);
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                if (inverse_dem) {
                    if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                        return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                    } else {

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                        }

                        Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                        double res = out_color_algorithm.getResult(object);

                        res = getFinalValueOut(res);

                        return res;
                    }

                } else if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                    }

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;
    }

    private double julia_2d_np_de_dem(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                double temp2 = complex[0].norm_squared();
                double temp3 = Math.log(temp2);

                if (inverse_dem) {
                    if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                        return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                    } else {

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                        }

                        Object[] object = {iterations, complex[0], dc};
                        double res = out_color_algorithm.getResult(object);

                        res = getFinalValueOut(res);

                        return res;
                    }

                } else if ((temp2 * temp3 * temp3) > (dc.norm_squared() * limit)) {
                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                    }

                    Object[] object = {iterations, complex[0], dc};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    return res;
                } else {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }

            dc.times_mutable(complex[0]).times_mutable(2);
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;
    }

    private double julia_2d_np_nde_normal(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                return res;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;
    }

    private double julia_2d_np_nde_dem(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex dc = new Complex(1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], dc};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                return res;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            dc.times_mutable(complex[0]).times_mutable(2);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;
    }

    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {

        if (exterior_de) {
            if (special_alg == 0) {
                return julia_2d_np_de_normal(pixel);
            } else {
                return julia_2d_np_de_dem(pixel);
            }
        } else if (special_alg == 0) {
            return julia_2d_np_nde_normal(pixel);
        } else {
            return julia_2d_np_nde_dem(pixel);
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

        switch (sts.statistic_type) {
            case MainWindow.STRIPE_AVERAGE:
                statistic = new StripeAverage(sts.statistic_intensity, sts.stripeAvgStripeDensity, log_bailout_squared);
                break;
            case MainWindow.CURVATURE_AVERAGE:
                statistic = new CurvatureAverage(sts.statistic_intensity, log_bailout_squared);
                break;
            case MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE:
                statistic = new CosArgDivideNormAverage(sts.statistic_intensity, sts.cosArgStripeDensity, log_bailout_squared);
                break;
            case MainWindow.TRIANGLE_INEQUALITY_AVERAGE:
                statistic = new TriangleInequalityAverage(sts.statistic_intensity, log_bailout_squared);
                break;
            case MainWindow.ATOM_DOMAIN_BOF60_BOF61:
                statistic = new AtomDomain(sts.showAtomDomains, sts.statistic_intensity);
                break;

        }
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
}
