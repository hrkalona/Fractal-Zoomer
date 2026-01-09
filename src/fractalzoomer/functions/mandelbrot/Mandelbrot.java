
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.core.Complex;
import fractalzoomer.core.NumericLibrary;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.approximation.la_zhuoran.LAstep;
import fractalzoomer.core.approximation.mip_la_claude.BLA;
import fractalzoomer.core.approximation.mip_la_zhuoran.MipLAStep;
import fractalzoomer.core.approximation.nanomb1.Nanomb1;
import fractalzoomer.core.approximation.nanomb1.biPoly;
import fractalzoomer.core.approximation.nanomb1.tmpPoly;
import fractalzoomer.core.approximation.series_approximation.MandelbrotApproximation;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.*;
import fractalzoomer.core.reference.*;
import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.filter.NoFunctionFilter;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.plane_influence.NoPlaneInfluence;
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
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
    protected boolean mandel_grass;

    public Mandelbrot(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double height_ratio) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
        this.mandel_grass = mandel_grass;

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
                limit = (size / Math.min(TaskRender.WIDTH, TaskRender.HEIGHT)) * exterior_de_factor;
            }
            else {
                double a = size / Math.min(TaskRender.WIDTH, TaskRender.HEIGHT);
                double b = a * height_ratio;
                double c = Math.sqrt(a * a + b * b);
                limit = c * exterior_de_factor;
            }
            limit = limit * limit;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);


        if((TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) && out_coloring_algorithm == MainWindow.DISTANCE_ESTIMATOR) {
            if (!smoothing) {
                out_color_algorithm = new EscapeTime();
            } else {
                out_color_algorithm = new SmoothEscapeTime(bailout, escaping_smooth_algorithm, bailout_algorithm.getNormImpl());
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

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double height_ratio, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
        this.mandel_grass = mandel_grass;

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
                limit = (size / Math.min(TaskRender.WIDTH, TaskRender.HEIGHT)) * exterior_de_factor;
            }
            else {
                double a = size / Math.min(TaskRender.WIDTH, TaskRender.HEIGHT);
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
    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
        this.mandel_grass = mandel_grass;

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

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
        this.mandel_grass = mandel_grass;

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

    private double mandel_np_de_normal(Complex[] complex, Complex pixel) {

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
                    finalizeStatistic(true, complex[0]);
                    outColorData.setData(iterations, complex[0], zold, zold2, complex[1], start, c0, pixel);
                    double res = out_color_algorithm.getResult(outColorData);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                } else {
                    return ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
                }

            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            if(isJulia && (!juliter || iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times2_mutable();
            }
            else {
                dc.times_mutable(complex[0]).times2_mutable().plus_mutable(1);
            }

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        finalizeStatistic(false, complex[0]);
        inColorData.setData(complex[0], zold, zold2, complex[1], start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;
    }

    private double mandel_np_de_dem(Complex[] complex, Complex pixel) {

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
                    finalizeStatistic(true, complex[0]);
                    outColorData.setData(iterations, complex[0], dc);
                    double res = out_color_algorithm.getResult(outColorData);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                } else {
                    return ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
                }
            }

            if(isJulia && (!juliter || iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times2_mutable();
            }
            else {
                dc.times_mutable(complex[0]).times2_mutable().plus_mutable(1);
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

        finalizeStatistic(false, complex[0]);
        inColorData.setData(complex[0], zold, zold2, complex[1], start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;
    }

    private boolean useOptimizedPath() {
        return not_burning_ship && !juliter && !mandel_grass && planeInfluence instanceof NoPlaneInfluence
        && preFilter instanceof NoFunctionFilter && postFilter instanceof NoFunctionFilter;
    }

    private double mandel_np_nde_normal(Complex[] complex, Complex pixel) {

        iterations = 0;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                finalizeStatistic(true, complex[0]);
                outColorData.setData(iterations, complex[0], zold, zold2, complex[1], start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

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

        finalizeStatistic(false, complex[0]);
        inColorData.setData(complex[0], zold, zold2, complex[1], start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;
    }

    private double mandel_optimized(Complex[] complexIn, Complex pixel) {

        Complex z = complexIn[0];
        Complex c = complexIn[1];

        iterations = 0;
        double zre = z.getRe(), zim = z.getIm();
        double cre = c.getRe(), cim = c.getIm();
        double norm_squared, zre_sqr, zim_sqr;
        double temp;


        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(z, iterations);
            }

            zre_sqr = zre * zre;
            zim_sqr = zim * zim;
            norm_squared = zre_sqr + zim_sqr;

            if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return res;
            }
            zold2.assign(zold);
            zold.assign(z);

            temp = zre * zim;
            zim = temp + temp + cim;
            zre = zre_sqr - zim_sqr + cre;
            z.assign(zre, zim);

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

        }

        finalizeStatistic(false, z);
        inColorData.setData(z, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return in;
    }

    private double mandel_np_nde_dem(Complex[] complex, Complex pixel) {

        iterations = 0;

        Complex dc = new Complex(1, 0);

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                finalizeStatistic(true, complex[0]);
                outColorData.setData(iterations, complex[0], dc);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);

            if(isJulia && (!juliter || iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times2_mutable();
            }
            else {
                dc.times_mutable(complex[0]).times2_mutable().plus_mutable(1);
            }

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        finalizeStatistic(false, complex[0]);
        inColorData.setData(complex[0], zold, zold2, complex[1], start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

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
                return mandel_np_de_normal(complex, pixel);
            } else {
                return mandel_np_de_dem(complex, pixel);
            }
        } else if (special_alg == 0) {
            if(useOptimizedPath()) {
                return mandel_optimized(complex, pixel);
            }
            return mandel_np_nde_normal(complex, pixel);
        } else {
            return mandel_np_nde_dem(complex, pixel);
        }

    }

    private double mandel_p_de_normal(Complex[] complex, Complex pixel) {

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
                    finalizeStatistic(true, complex[0]);
                    outColorData.setData(iterations, complex[0], zold, zold2, complex[1], start, c0, pixel);
                    double res = out_color_algorithm.getResult(outColorData);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                } else {
                    return ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
                }
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            if(isJulia && (!juliter || iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times2_mutable();
            }
            else {
                dc.times_mutable(complex[0]).times2_mutable().plus_mutable(1);
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

    private double mandel_p_de_dem(Complex[] complex, Complex pixel) {

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
                    finalizeStatistic(true, complex[0]);
                    outColorData.setData(iterations, complex[0], dc);
                    double res = out_color_algorithm.getResult(outColorData);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                } else {
                    return ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
                }
            }

            if(isJulia && (!juliter || iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times2_mutable();
            }
            else {
                dc.times_mutable(complex[0]).times2_mutable().plus_mutable(1);
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

    private double mandel_p_nde_normal(Complex[] complex, Complex pixel) {

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

                finalizeStatistic(true, complex[0]);
                outColorData.setData(iterations, complex[0], zold, zold2, complex[1], start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

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

    private double mandel_p_nde_dem(Complex[] complex, Complex pixel) {

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

                finalizeStatistic(true, complex[0]);
                outColorData.setData(iterations, complex[0], dc);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);
            if(isJulia && (!juliter || iterations >= juliterIterations)) {
                dc.times_mutable(complex[0]).times2_mutable();
            }
            else {
                dc.times_mutable(complex[0]).times2_mutable().plus_mutable(1);
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
                return mandel_p_de_normal(complex, pixel);
            } else {
                return mandel_p_de_dem(complex, pixel);
            }
        } else if (special_alg == 0) {
            return mandel_p_nde_normal(complex, pixel);
        } else {
            return mandel_p_nde_dem(complex, pixel);
        }
    }

    @Override
    public boolean shouldRecalculateForPeriodDetection(boolean deepZoom, Location externalLocation) {
        if(referenceOrbit.DetectedPeriod == 0 || (TaskRender.APPROXIMATION_ALGORITHM == 3 && supportsNanomb1())) {
            return true;
        }

        initializeReferenceDecompressor();

        if (deepZoom) {
            if(referenceOrbit.period_mdzdc == null) {
                return true;
            }

            MantExpComplex mdzdc = referenceOrbit.period_mdzdc;
            MantExp mradius = externalLocation.getSize().multiply2_mutable();
            MantExp temp = mdzdc.times(mradius).chebyshevNorm();

            if (temp.compareToBothPositiveReduced(getReferenceDeepValue(referenceDeep, referenceOrbit.DetectedPeriod).chebyshevNorm()) > 0) {
                return false;
            }
        } else {
            if(referenceOrbit.period_dzdc == null) {
                return true;
            }

            Complex dzdc = referenceOrbit.period_dzdc;
            double radius = this.size * 2;

            if (radius * dzdc.chebyshevNorm() > getReferenceValue(reference, referenceOrbit.DetectedPeriod).chebyshevNorm()) {
                return false;
            }
        }

        return true;

    }

    @Override
    public void calculateReferenceOrbit(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int[] Iterations, int[] juliaIterations, Location externalLocation, JProgressBar progress) {

        referenceOrbit.LastCalculationSize = size;

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

        boolean detectPeriod = detectPeriod();
        boolean lowPrecReferenceOrbitNeeded = usesReferenceSavingOrLoading() || !needsOnlyExtendedReferenceOrbit(deepZoom, detectPeriod);
        boolean stopReferenceCalculationOnDetectedPeriod = stopReferenceCalculationOnDetectedPeriod();

        DoubleReference.SHOULD_SAVE_MEMORY = stopReferenceCalculationOnDetectedPeriod;
        boolean useCompressedRef = useCompressedRef();
        boolean needsRefSubCp = needsRefSubCp();
        int[] precalIndexes = getNeededPrecalculatedTermsIndexes();

        initializeReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, max_ref_iterations, needsRefSubCp, useCompressedRef, precalIndexes);

        if(iterations == 0) {
            //DetectedAtomPeriod = 0;
            referenceOrbit.DetectedPeriod = 0;
        }

        boolean gatherTinyRefPts = gatherTinyRefPts(deepZoom);

        if (gatherTinyRefPts && referenceOrbit.tinyRefPts == null) {
            referenceOrbit.tinyRefPts = new ArrayList<>();
        }

        Location loc = new Location();

        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared;

        int bigNumLib = NumericLibrary.getBignumImplementation(size, this);

        if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
            BigNumComplex bn = inputPixel.toBigNumComplex();
            z = iterations == 0 ? (isJulia ? bn : new BigNumComplex()) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new BigNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigNumComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? bn : new BigNumComplex();
            c0 = c;
            pixel = bn;
        }
        else if(bigNumLib == BIGNUM_BIGINT) {
            BigIntNumComplex bn = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? (isJulia ? bn : new BigIntNumComplex()) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new BigIntNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? bn : new BigIntNumComplex();
            c0 = c;
            pixel = bn;
        }
        else if(bigNumLib == Constants.BIGNUM_MPFR) {
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            z = iterations == 0 ? (isJulia ? bn : new MpfrBigNumComplex()) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new MpfrBigNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? new MpfrBigNumComplex(bn) : new MpfrBigNumComplex();
            c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? (isJulia ? bn : new MpirBigNumComplex()) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new MpirBigNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? new MpirBigNumComplex(bn) : new MpirBigNumComplex();
            c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? (isJulia ? ddn : new DDComplex()) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : ddn;
            zold = iterations == 0 ? new DDComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? ddn : new DDComplex();
            c0 = c;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? (isJulia ? bn : new Complex()) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new Complex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? new Complex(bn) : new Complex();
            c0 = new Complex((Complex) c);
            pixel = new Complex(bn);
        }
        else {
            z = iterations == 0 ? (isJulia ? inputPixel : new BigComplex()) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : inputPixel;
            zold = iterations == 0 ? new BigComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? inputPixel : new BigComplex();
            c0 = c;
            pixel = inputPixel;
        }
        normSquared = z.normSquared();

        referenceOrbit.refPoint = inputPixel;

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(referenceOrbit.refPoint);
            refPointSmall = refPointSmallDeep.toComplex();
            if(isJulia) {
                seedSmallDeep = loc.getMantExpComplex(c);
            }

            if(lowPrecReferenceOrbitNeeded && isJulia) {
                seedSmall = seedSmallDeep.toComplex();
            }
        }
        else {
            refPointSmall = referenceOrbit.refPoint.toComplex();
            if(lowPrecReferenceOrbitNeeded && isJulia) {
                seedSmall = c.toComplex();
            }
        }

        referenceOrbit.RefType = getRefType();

        boolean isNanoMb1InUse = TaskRender.APPROXIMATION_ALGORITHM == 3 && supportsNanomb1();
        boolean isSeriesInUse = TaskRender.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation();
        boolean isBLAInUse = TaskRender.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation();
        boolean isBLA2InUse = TaskRender.APPROXIMATION_ALGORITHM == 4 && supportsBilinearApproximation2();
        boolean isBLA3InUse = TaskRender.APPROXIMATION_ALGORITHM == 5 && supportsBilinearApproximation3();

        boolean usesCircleBail = usesCircleBail();

        boolean isMpfrComplex = z instanceof MpfrBigNumComplex;
        boolean isMpirComplex = z instanceof MpirBigNumComplex;

        Complex dzdc = null;
        MantExpComplex mdzdc = null;

        MantExp mradius = null;
        double radius = 0;
        MantExp temp;

        if(detectPeriod && referenceOrbit.DetectedPeriod == 0) {
            if (iterations == 0) {
                if (deepZoom) {
                    mdzdc = MantExpComplex.create(1, 0);
                } else {
                    dzdc = new Complex(1, 0);
                }
            } else {
                if (deepZoom) {
                    mdzdc = referenceOrbit.mdzdc;
                } else {
                    dzdc = referenceOrbit.dzdc;
                }
            }

            if(deepZoom) {
                mradius = externalLocation.getSize().multiply2_mutable();
            }
            else {
                radius = this.size * 2;
            }
        }

        Complex period_dzdc = null;
        MantExpComplex period_mdzdc = null;

        calculatedReferenceIterations = 0;

        boolean combineReductionWithFunction = (bigNumLib == Constants.BIGNUM_MPIR || bigNumLib == Constants.BIGNUM_MPFR);
        boolean notCombineReductionWithFunction = !combineReductionWithFunction;

        Complex cz = combineReductionWithFunction && lowPrecReferenceOrbitNeeded ? new Complex() : null;
        MantExpComplex mcz = combineReductionWithFunction && deepZoom ? MantExpComplex.create() : null;

        if(combineReductionWithFunction && deepZoom) {
            mcz = loc.getMantExpComplex(z);
        }

        if(combineReductionWithFunction && lowPrecReferenceOrbitNeeded && !deepZoom) {
            cz = z.toComplex();
        }

        if(useCompressedRef) {
            initializeCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, needsRefSubCp, precalIndexes, z, c, null, start);
        }

        MantExpComplex tempmcz = null;

        for (; iterations < max_ref_iterations; iterations++, calculatedReferenceIterations++) {

            if(deepZoom) {
                if(notCombineReductionWithFunction) {
                    mcz = loc.getMantExpComplex(z);
                }

                tempmcz = setReferenceDeepValue(referenceDeep, iterations, mcz);
            }

            if(lowPrecReferenceOrbitNeeded) {
                if(notCombineReductionWithFunction) {
                    cz = deepZoom ? mcz.toComplex() : z.toComplex();
                }
                else if(deepZoom) {
                    cz = mcz.toComplex();
                }

                cz = setReferenceValue(reference, iterations, cz);

                if(gatherTinyRefPts) {
                    if (burning_ship) {
                        if(cz.getAbsRe() < scaledE || cz.getAbsIm() < scaledE || cz.hypot() < scaledE) {
                            referenceOrbit.tinyRefPts.add(iterations);
                        }
                    } else {
                        if(cz.hypot() < scaledE) {
                            referenceOrbit.tinyRefPts.add(iterations);
                        }
                    }
                }
            }

            mcz = tempmcz;

            if(stopReferenceCalculationOnDetectedPeriod && referenceOrbit.DetectedPeriod != 0) {
                break;
            }

            if (detectPeriod && referenceOrbit.DetectedPeriod == 0 && iterations > 0) {
                if (deepZoom) {
                    temp = mdzdc.times(mradius).chebyshevNorm();
                    if (temp.compareToBothPositiveReduced(mcz.chebyshevNorm()) > 0) {
                        referenceOrbit.DetectedPeriod = iterations;
                        period_mdzdc = MantExpComplex.copy(mdzdc);
                    }
                } else {
                    if (radius * dzdc.chebyshevNorm() > cz.chebyshevNorm()) {
                        referenceOrbit.DetectedPeriod = iterations;
                        period_dzdc = new Complex(dzdc);
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
                if(detectPeriod && (referenceOrbit.DetectedPeriod == 0 || stopReferenceCalculationOnDetectedPeriod)) {
                    if (deepZoom) {
                        mdzdc = mcz.times2().times_mutable(mdzdc).plus_mutable(MantExp.ONE);
                        mdzdc.Normalize();
                    } else {
                        dzdc = cz.times2().times_mutable(dzdc).plus_mutable(1);
                    }
                }

                if(isMpfrComplex) {
                    if (burning_ship) {
                        z = z.abs_mutable().square_plus_c_mutable_with_reduction(c, workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, deepZoom, cz, mcz);
                    }
                    else {
                        z = z.square_plus_c_mutable_with_reduction(c, workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, deepZoom, cz, mcz);
                    }
                }
                else if(isMpirComplex) {
                    if (burning_ship) {
                        z = z.abs_mutable().square_plus_c_mutable_with_reduction(c, workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, deepZoom, cz, mcz);
                    }
                    else {
                        z = z.square_plus_c_mutable_with_reduction(c, workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, deepZoom, cz, mcz);
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
            catch (Exception ex) {
                break;
            }


            if(progress != null && iterations % 1000 == 0) {
                progress.setValue(iterations - initIterations);
                progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (iterations - initIterations) / progress.getMaximum() * 100)) + "%");
            }

        }

        referenceOrbit.lastZValue = z;
        referenceOrbit.c = c;
        referenceOrbit.secondTolastZValue = !usesCircleBail ? zold : null;
        referenceOrbit.thirdTolastZValue = !usesCircleBail ? zold2 : null;
        referenceOrbit.dzdc = !deepZoom ? dzdc : null;
        referenceOrbit.mdzdc = deepZoom ? mdzdc : null;
        referenceOrbit.period_dzdc = !deepZoom ? period_dzdc : null;
        referenceOrbit.period_mdzdc = deepZoom ? period_mdzdc : null;
        referenceOrbit.tinyRefPts = !gatherTinyRefPts ? null : referenceOrbit.tinyRefPts;

        referenceOrbit.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            finalizeCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, needsRefSubCp, precalIndexes);
        }

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        if(gatherTinyRefPts) {
            referenceOrbit.tinyRefPts = referenceOrbit.tinyRefPts.stream()
                    .distinct()
                    .collect(Collectors.toList());
        }

        if(TaskRender.SAVE_REFERENCE && supportsReferenceSavingOrLoading()) {
            saveReference(TaskRender.SAVE_REFERENCE_FILE_PATH);
        }

        ReferenceCalculationTime = System.currentTimeMillis() - time;

        if(isJulia) {
            calculateJuliaReferenceOrbit(inputPixel, size, deepZoom, juliaIterations, progress);
        }

        SAskippedIterations = 0;
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
        else if(isBLA3InUse) {
            calculateBLA3Wrapper(deepZoom, progress);
        }

        if(gatherTinyRefPts) {
            tinyRefPtsArray = referenceOrbit.tinyRefPts.stream().mapToInt(i -> i).toArray();
        }
        else {
            tinyRefPtsArray = new int[0];
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
                    z = z.abs_mutable().square_plus_c_mutable(c, workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3);
                }
                else {
                    z = z.square_plus_c_mutable(c, workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3);
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

        if(size >= 0x1.0p-32) {
            return;
        }

        long value = ((long)getNanomb1MaxIterations() * 2 - 1);
        long divisor = value > Constants.MAX_PROGRESS_VALUE ? value / Constants.PROGRESS_SCALE : 1;

        int m = TaskRender.NANOMB1_M;
        int n = TaskRender.NANOMB1_N;
        biPoly fp = new biPoly(m, n);
        int max_ref_iterations_period = getNanomb1MaxIterations();
        long total = 1;
        for(int iteration = 1; iteration < max_ref_iterations_period; iteration++, total++) {
            if(deepZoom) {
                fp.cstep(getReferenceDeepValue(referenceDeep, iteration));
            }
            else {
                fp.cstep(getReferenceValue(reference, iteration).toMantExpComplex());
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

        MantExpComplex zlo1 = MantExpComplex.create();
        MantExpComplex zlo;

        DeepReference ref1Deep = null;

        int max_ref_iterations = getReferenceMaxIterations();

        DoubleReference ref1;

        ReferenceCompressor referenceCompressor = null;
        ReferenceCompressor referenceCompressorDeep = null;
        ReferenceDecompressor referenceDecompressor = null;
        ReferenceDecompressor referenceDecompressorDeep = null;

        if(TaskRender.COMPRESS_REFERENCE) {
            Complex initVal = defaultInitVal.getValue(null);
            ref1 = new CompressedDoubleReference(max_ref_iterations_period, max_ref_iterations, ReferenceType.NORMAL);
            referenceCompressor = new ReferenceCompressor(this, new Complex(initVal), new Complex(refPointSmall), new Complex(initVal));
            referenceDecompressor = new ReferenceDecompressor(this, new Complex(refPointSmall), new Complex(initVal));

            if (deepZoom) {
                ref1Deep = new CompressedDeepReference(max_ref_iterations_period, max_ref_iterations, ReferenceType.NORMAL);
                referenceCompressorDeep = new ReferenceCompressor(this, MantExpComplex.create(initVal), MantExpComplex.copy(refPointSmallDeep), MantExpComplex.create(initVal));
                referenceDecompressorDeep = new ReferenceDecompressor(this, MantExpComplex.copy(refPointSmallDeep), MantExpComplex.create(initVal));
            }
        }
        else {
            ref1 = new DoubleReference(max_ref_iterations_period, max_ref_iterations, ReferenceType.NORMAL);
            if (deepZoom) {
                ref1Deep = new DeepReference(max_ref_iterations_period, max_ref_iterations, ReferenceType.NORMAL);
            }
        }

        boolean gatherTinyRefPts = gatherTinyRefPts(deepZoom);

        if(gatherTinyRefPts && !referenceOrbit.tinyRefPts.isEmpty()) {
            referenceOrbit.tinyRefPts.clear();
        }

        MantExpComplex temp;
        for(int iteration = 0; iteration < max_ref_iterations_period; iteration++, total++){

            if(deepZoom) {
                zlo = getReferenceDeepValue(referenceDecompressorDeep, referenceDeep, iteration);
                temp = zlo.plus(zlo1);
                setReferenceDeepValue(referenceCompressorDeep, ref1Deep, iteration, temp);

                Complex cz = temp.toComplex();
                cz = setReferenceValue(referenceCompressor, ref1, iteration, cz);

                if(gatherTinyRefPts && cz.hypot() < scaledE) {//burning_ship is not implemented for this
                    referenceOrbit.tinyRefPts.add(iteration);
                }
            }
            else {
                zlo = MantExpComplex.create(getReferenceValue(referenceDecompressor, reference, iteration));
                setReferenceValue(referenceCompressor, ref1, iteration, zlo.plus(zlo1).toComplex());
            }

            zlo1 = zlo1.times(zlo1.plus(zlo.times2())).plus_mutable(nucleusPos);
            zlo1.Normalize();

            if(progress != null && total % 50 == 0) {
                int val = (int)(total / divisor);
                progress.setValue(val);
                progress.setString(NANOMB1_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }
        }

        if(TaskRender.COMPRESS_REFERENCE) {
            if(deepZoom) {
                ReferenceCompressor.compact(ref1Deep);
            }

            ReferenceCompressor.compact(ref1);
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
                return getReferenceValue(reference, RefIteration).times2_mutable().times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
            }
            else {
                return getReferenceValue(reference, RefIteration).times2_mutable().plus_mutable(DeltaSubN.times(s)).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
            }
        }
        else {
            Complex X = getReferenceValue(reference, RefIteration);
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
                return getReferenceValue(reference, RefIteration).times2_mutable().times_mutable(DeltaSubN);
            }
            else {
                return getReferenceValue(reference, RefIteration).times2_mutable().plus_mutable(DeltaSubN.times(s)).times_mutable(DeltaSubN);
            }
        }
        else {
            Complex X = getReferenceValue(reference, RefIteration);
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
            //return DeltaSubN.times(getArrayValue(Reference, RefIteration).times2_mutable()).plus_mutable(DeltaSubN.square()).plus_mutable(DeltaSub0);
            return getReferenceValue(reference, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
        else {
            Complex X = getReferenceValue(reference, RefIteration);
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
            return getReferenceDeepValue(referenceDeep, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
        else {
            MantExpComplex X = getReferenceDeepValue(referenceDeep, RefIteration);
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();


            return MantExpComplex.create(r.multiply2().add_mutable(a).multiply_mutable(a).subtract_mutable(i.multiply2().add_mutable(b).multiply_mutable(b)), MantExpComplex.DiffAbs(r.multiply(i), r.multiply(b).add_mutable(i.add(b).multiply_mutable(a))).multiply2_mutable()).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayValue(Reference, RefIteration).times2_mutable()).plus_mutable(DeltaSubN.square());
            return getReferenceValue(reference, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
        }
        else {
            Complex X = getReferenceValue(reference, RefIteration);
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
            return getReferenceDeepValue(referenceDeep, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
        }
        else {
            MantExpComplex X = getReferenceDeepValue(referenceDeep, RefIteration);
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();

            return MantExpComplex.create(r.multiply2().add_mutable(a).multiply_mutable(a).subtract_mutable(i.multiply2().add_mutable(b).multiply_mutable(b)), MantExpComplex.DiffAbs(r.multiply(i), r.multiply(b).add_mutable(i.add(b).multiply_mutable(a))).multiply2_mutable());
        }
    }

    @Override
    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc, JProgressBar progress) {
        sa = new MandelbrotApproximation();
        sa.calculateApproximation(dsize, deepZoom, loc, referenceOrbit, progress, this);
        SAskippedIterations = sa.SAskippedIterations;
    }

    @Override
    public Complex getBlaA(Complex Z) {
        return Z.times2();
    }

    @Override
    public double getBlaR(Complex Z, double epsilon) {
        return 2 * Z.hypot() * epsilon;
    }

    @Override
    public MantExp getBlaR(MantExpComplex Z, MantExp epsilon) {
        return Z.hypot().multiply2_mutable().multiply_mutable(epsilon);
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
    public boolean supportsReferenceSavingOrLoading() {
        return true;
    }

    @Override
    public boolean supportsReferenceCompression() {
        return true;
    }

    @Override
    public boolean supportsExtendedReferenceCompression() {
        if(isJuliaMap) {
            return false;
        }
        return !burning_ship && !isJulia;
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

    @Override
    public boolean supportsBilinearApproximation3() {
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
     dz = z.times(dz).times2();//2 * z * dz;
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
     dcdz = (z.times(dcdz).plus(dz.times(dc))).times2();//2 * (z * dcdz + dz * dc);
     dc = z.times(dc).times2().plus(1);//2 * z * dc + 1;
     dzdz = (dz.times(dz).plus(z.times(dzdz))).times2();//2 * (dz * dz + z * dzdz);
     dz = z.times(dz).times2();//2 * z * dz;
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
                complex[0] = complex[0].square_plus_c_mutable_no_threads(complex[1], workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3);
            }
            else {
                complex[0] = complex[0].abs_mutable().square_plus_c_mutable_no_threads(complex[1], workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3);
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
        return !burning_ship && !isJulia && !(TaskRender.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation());
    }

    @Override
    public boolean supportsScaledIterations() {
        return !isJulia;
    }

    @Override
    public boolean supportsNanomb1() {
        return !burning_ship && !isJulia && (getPeriod() != 0 || TaskRender.DETECT_PERIOD && supportsPeriod());
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, ReferenceData data, int RefIteration) {
        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayValue(Reference, RefIteration).times2_mutable()).plus_mutable(DeltaSubN.square());
            return getReferenceValue(data.Reference, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
        }
        else {
            Complex X = getReferenceValue(data.Reference, RefIteration);
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            return new Complex((2.0 * r + a) * a - (2.0 * i + b) * b, Complex.DiffAbs(r * i, r * b + (i + b) * a) * 2);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, DoubleReference ref, int RefIteration) {
        //Not for burning ship
        return getReferenceValue(ref, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex dz, DeepReference data, int RefIteration) {
        //Not for burning ship
        return getReferenceDeepValue(data, RefIteration).times2_mutable().plus_mutable(dz).times_mutable(dz);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, ReferenceDeepData data, int RefIteration) {
        if(not_burning_ship) {
            //return DeltaSubN.times(getArrayDeepValue(ReferenceDeep, RefIteration).times2_mutable()).plus_mutable(DeltaSubN.square());
            return getReferenceDeepValue(data.Reference, RefIteration).times2_mutable().plus_mutable(DeltaSubN).times_mutable(DeltaSubN);
        }
        else {
            MantExpComplex X = getReferenceDeepValue(data.Reference, RefIteration);
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();

            return MantExpComplex.create(r.multiply2().add_mutable(a).multiply_mutable(a).subtract_mutable(i.multiply2().add_mutable(b).multiply_mutable(b)), MantExpComplex.DiffAbs(r.multiply(i), r.multiply(b).add_mutable(i.add(b).multiply_mutable(a))).multiply2_mutable());
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
    public void createLowPrecisionOrbit(int length, ReferenceData refData, ReferenceDeepData refDeepData) {

        if(refDeepData.Reference.compressed) {
            refData.createAndSetShortcut(length, false, new int[0], true);
            CompressedDoubleReference reference = (CompressedDoubleReference) refData.Reference;
            CompressedDeepReference deepReference = (CompressedDeepReference) refDeepData.Reference;
            ReferenceCompressor.createLowPrecisionOrbit(reference, deepReference);
            reference.setLengthOverride(deepReference.length());
        }
        else {
            DoubleReference.SHOULD_SAVE_MEMORY = false;
            refData.createAndSetShortcut(length, false, new int[0], false);
            DoubleReference reference = refData.Reference;
            DeepReference deepReference = refDeepData.Reference;

            for (int i = 0; i < length; i++) {
                setReferenceValue(reference, i, getReferenceDeepValue(deepReference, i).toComplex());
            }

            reference.setLengthOverride(deepReference.length());
        }
    }

    @Override
    public double iterateFractalWithPerturbation(Complex[] complexIn, Complex dpixel) {

        if(TaskRender.COMPRESS_REFERENCE || burning_ship) {
            return super.iterateFractalWithPerturbation(complexIn, dpixel);
        }

        double_iterations = 0;
        rebases = 0;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : SAskippedIterations;

        int RefIteration = iterations;

        int ReferencePeriod = getPeriod();

        int MaxRefIteration = getReferenceFinalIterationNumber(true);

        double norm_squared = 0;

        double dre = DeltaSubN.getRe(), dim = DeltaSubN.getIm();
        double tempre, tempim, d0re = DeltaSub0.getRe(), d0im = DeltaSub0.getIm();
        double temp, zre = 0, zim = 0;

        double[] RefRe = reference.re;
        double[] RefIm = reference.im;

        Complex z = complexIn[0];
        Complex c = complexIn[1];

        if (iterations != 0 && RefIteration < MaxRefIteration) {
            zre = RefRe[RefIteration] + dre;
            zim = RefIm[RefIteration] + dim;
            norm_squared = zre * zre + zim * zim;
            z.assign(zre, zim);
        } else if (iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            zre = RefRe[RefIteration] + dre;
            zim = RefIm[RefIteration] + dim;
            norm_squared = zre * zre + zim * zim;
            z.assign(zre, zim);
        }

        double RefReVal = RefRe[RefIteration];
        double RefImVal = RefIm[RefIteration];

        Complex pixel = dpixel.plus(refPointSmall);

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(z, iterations);
            }

            if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return getAndAccumulateStatsNotDeep(res);
            }

            tempre = 2 * RefReVal + dre;
            tempim = 2 * RefImVal + dim;

            temp = tempre * dre - tempim * dim;
            tempim = tempre * dim + tempim * dre;
            tempre = temp;

            dre = tempre + d0re;
            dim = tempim + d0im;

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(z);

            //No Plane influence work
            //No Pre filters work
            if (max_iterations > 1) {
                RefReVal = RefRe[RefIteration];
                RefImVal = RefIm[RefIteration];
                zre = RefReVal + dre;
                zim = RefImVal + dim;
                z.assign(zre, zim);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            norm_squared = zre * zre + zim * zim;
            if (norm_squared < dre * dre + dim * dim || RefIteration >= MaxRefIteration) {
                dre = zre;
                dim = zim;
                RefIteration = 0;
                RefReVal = RefRe[RefIteration];
                RefImVal = RefIm[RefIteration];
                rebases++;
            }

        }

        finalizeStatistic(false, z);
        inColorData.setData(z, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsNotDeep(in);

    }

    @Override
    public double iterateFractalWithPerturbationBLA(Complex[] complexIn, Complex dpixel) {

        if(TaskRender.COMPRESS_REFERENCE) {
            return super.iterateFractalWithPerturbationBLA(complexIn, dpixel);
        }

        bla_steps = 0;
        bla_iterations = 0;
        perturb_iterations = 0;
        rebases = 0;
        iterations = 0;

        int RefIteration = iterations;

        int MaxRefIteration = getReferenceFinalIterationNumber(true);

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        double dre = DeltaSubN.getRe(), dim = DeltaSubN.getIm();
        double tempre, tempim, d0re = DeltaSub0.getRe(), d0im = DeltaSub0.getIm();
        double temp, zre = 0, zim = 0;

        double normSquared = 0;
        double DeltaNormSquared;

        Complex pixel = dpixel.plus(refPointSmall);
        Complex z = complexIn[0];
        Complex c = complexIn[1];

        double[] RefRe = reference.re;
        double[] RefIm = reference.im;

        while (iterations < max_iterations) {

            //No update values

            if (trap != null) {
                trap.check(z, iterations);
            }

            if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return getAndAccumulateStatsBLA(res);
            }

            // perturbation iteration
            tempre = 2 * RefRe[RefIteration] + dre;
            tempim = 2 * RefIm[RefIteration] + dim;

            temp = tempre * dre - tempim * dim;
            tempim = tempre * dim + tempim * dre;
            tempre = temp;

            dre = tempre + d0re;
            dim = tempim + d0im;

            RefIteration++;
            perturb_iterations++;

            zold2.assign(zold);
            zold.assign(z);

            //No Plane influence work
            //No Pre filters work
            //No Post filters work
            if (max_iterations > 1) {
                zre = RefRe[RefIteration] + dre;
                zim = RefIm[RefIteration] + dim;
                z.assign(zre, zim);
            }

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            iterations++;

            DeltaNormSquared = dre * dre + dim * dim;

            while (B.isValid && iterations < max_iterations) {

                BLA b = B.lookupBackwards(RefIteration, DeltaNormSquared, iterations, max_iterations);
                if (b == null) {
                    break;
                }

                if (trap != null) {
                    trap.check(z, iterations);
                }

                if(TaskRender.CHECK_BAILOUT_DURING_MIP_BLA_STEP) {
                    normSquared = zre * zre + zim * zim;
                    if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                        escaped = true;

                        finalizeStatistic(true, z);
                        outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                        double res = out_color_algorithm.getResult(outColorData);

                        res = getFinalValueOut(res);

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                        }

                        return getAndAccumulateStatsBLA(res);
                    }
                }

                int l = b.getL();

                RefIteration += l;
                iterations += l;
                bla_steps++;
                bla_iterations += l;

                DeltaSubN = b.getValue(dre, dim, d0re, d0im);
                dre = DeltaSubN.getRe();
                dim = DeltaSubN.getIm();

                zold2.assign(zold);
                zold.assign(z);

                //No Plane influence work
                //No Pre filters work
                //No Post filters work
                zre = RefRe[RefIteration] + dre;
                zim = RefIm[RefIteration] + dim;
                z.assign(zre, zim);

                if (statistic != null) {
                    statistic.insert(z, zold, zold2, iterations, c, start, c0, b);
                }

                DeltaNormSquared = dre * dre + dim * dim;
            }

            //rebase
            normSquared = zre * zre + zim * zim;

            if (normSquared < DeltaNormSquared || (RefIteration >= MaxRefIteration)) {
                dre = zre;
                dim = zim;
                RefIteration = 0;
                rebases++;
            }
        }

        finalizeStatistic(false, z);
        inColorData.setData(z, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsBLA(in);

    }


    @Override
    public double iterateFractalWithPerturbationBLA2(Complex[] complexIn, Complex dpixel) {

        if(TaskRender.COMPRESS_REFERENCE) {
            return super.iterateFractalWithPerturbationBLA2(complexIn, dpixel);
        }

        bla_steps = 0;
        bla_iterations = 0;
        perturb_iterations = 0;
        rebases = 0;
        iterations = 0;

        int MaxRefIteration = getReferenceFinalIterationNumber(true);

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        iterations = BLA2SkippedIterations;
        bla_iterations = BLA2SkippedIterations;
        bla_steps = BLA2SkippedSteps;

        int RefIteration = iterations;

        Complex pixel = dpixel.plus(refPointSmall);

        int ReferencePeriod = getPeriod();

        double dre = DeltaSubN.getRe(), dim = DeltaSubN.getIm();
        double d0re = DeltaSub0.getRe(), d0im = DeltaSub0.getIm();
        double zre = 0, zim = 0;
        Complex Z;

        Complex z = complexIn[0];
        Complex c = complexIn[1];

        double[] RefRe = reference.re;
        double[] RefIm = reference.im;

        if (iterations != 0 && RefIteration < MaxRefIteration) {
            zre = RefRe[RefIteration] + dre;
            zim = RefIm[RefIteration] + dim;
            z.assign(zre, zim);
        } else if (iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            zre = RefRe[RefIteration] + dre;
            zim = RefIm[RefIteration] + dim;
            z.assign(zre, zim);
        }

        double CDeltaSub0ChebyshevNorm = Math.max(Math.abs(d0re), Math.abs(d0im));

        int CurrentLAStage = laReference.isValid ? laReference.LAStageCount : 0;

        RefIteration = laReference.getBegin(CurrentLAStage - 1);

        while (CurrentLAStage > 0) {

            CurrentLAStage--;

            int begin = laReference.getBegin(CurrentLAStage);

            if(laReference.isLAStageInvalid(begin, CDeltaSub0ChebyshevNorm)) {
                RefIteration = laReference.getNextStageLAIndex(begin);
                continue;
            }

            int end = laReference.getEnd(CurrentLAStage);


            while(iterations < max_iterations) {

                LAstep las = laReference.getLA(this, RefIteration, dre, dim, iterations, max_iterations);

                if(las.unusable) {
                    RefIteration = las.NextStageLAIndex;
                    break;
                }

                //No update values

                if (trap != null) {
                    trap.check(z, iterations);
                }

                int l = las.step;
                iterations += l;
                bla_steps++;
                bla_iterations += l;

                //DeltaSubN = las.Evaluate(d0re, d0im);
                //dre = DeltaSubN.getRe();
                //dim = DeltaSubN.getIm();
                las.Evaluate(d0re, d0im);
                dre = las.outdre;
                dim = las.outdim;

                RefIteration++;

                zold2.assign(zold);
                zold.assign(z);

                //No Plane influence work
                //No Pre filters work
                Z = las.getRefp1();
                zre = Z.getRe() + dre;
                zim = Z.getIm() + dim;
                z.assign(zre, zim);
                //No Post filters work

                // rebase

                if(Math.max(Math.abs(zre), Math.abs(zim)) < Math.max(Math.abs(dre), Math.abs(dim)) || RefIteration >= end) {
                    dre = zre;
                    dim = zim;
                    RefIteration = begin;
                    rebases++;
                }

                if (statistic != null) {
                    statistic.insert(z, zold, zold2, iterations, c, start, c0, las);
                }
            }

            if (iterations >= max_iterations) {
                break;
            }
        }

        return PerturbationAfterBLA2(z, c, pixel, zre, zim, dre, dim, d0re, d0im, RefIteration, MaxRefIteration, false);

    }

    private double PerturbationAfterBLA2(Complex z, Complex c, Complex pixel, double zre, double zim, double dre, double dim, double d0re, double d0im, int RefIteration, int MaxRefIteration, boolean isZero) {
        double normSquared =  0;
        double tempre, tempim, temp;

        if(iterations < max_iterations) {
            normSquared = zre * zre + zim * zim;
        }

        boolean isNonZero = !isZero;

        double[] RefRe = reference.re;
        double[] RefIm = reference.im;

        double RefReVal = RefRe[RefIteration];
        double RefImVal = RefIm[RefIteration];

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(z, iterations);
            }

            if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return getAndAccumulateStatsBLA(res);
            }

            // perturbation iteration
            tempre = 2 * RefReVal + dre;
            tempim = 2 * RefImVal + dim;

            temp = tempre * dre - tempim * dim;
            dim = tempre * dim + tempim * dre;
            dre = temp;

            if(isNonZero) {
                dre += d0re;
                dim += d0im;
            }

            RefIteration++;
            perturb_iterations++;

            // rebase
            zold2.assign(zold);
            zold.assign(z);

            //No Plane influence work
            //No Pre filters work
            if (max_iterations > 1) {
                RefReVal = RefRe[RefIteration];
                RefImVal = RefIm[RefIteration];
                zre = RefReVal + dre;
                zim = RefImVal + dim;
                z.assign(zre, zim);
            }
            //No Post filters work
            normSquared = zre * zre + zim * zim;

            if (normSquared < dre * dre + dim * dim  || (RefIteration >= MaxRefIteration)) { //* 64
                dre = zre;
                dim = zim;
                RefIteration = 0;
                RefReVal = RefRe[RefIteration];
                RefImVal = RefIm[RefIteration];
                rebases++;
            }

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }
        }

        finalizeStatistic(false, z);
        inColorData.setData(z, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsBLA(in);
    }

    @Override
    public double iterateFractalWithPerturbationBLA2(Complex[] complexIn, MantExpComplex dpixel) {

        if(TaskRender.COMPRESS_REFERENCE) {
            return super.iterateFractalWithPerturbationBLA2(complexIn, dpixel);
        }

        bla_steps = 0;
        bla_iterations = 0;
        perturb_iterations = 0;
        rebases = 0;

        iterations = 0;

        int MaxRefIteration = getReferenceFinalIterationNumber(true);

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        MantExp DeltaSub0ChebyshevNorm = DeltaSub0.chebyshevNorm();

        iterations = BLA2SkippedIterations;
        bla_iterations = BLA2SkippedIterations;
        bla_steps = BLA2SkippedSteps;

        int RefIteration = iterations;

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        MantExpComplex z = MantExpComplex.create();

        MantExpComplex zoldDeep = MantExpComplex.create();
        MantExpComplex zoldDeep2 = MantExpComplex.create();

        Complex zc = complexIn[0];
        Complex c = complexIn[1];

        int ReferencePeriod = getPeriod();
        if (iterations != 0 && RefIteration < MaxRefIteration) {
            z = getReferenceDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN);
            zc = z.toComplex();
        } else if (iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            z = getReferenceDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN);
            zc = z.toComplex();
        }

        int CurrentLAStage = laReference.isValid ? laReference.LAStageCount : 0;

        boolean DPEvaluation = false;

        boolean needsComplexZ = trap != null || statistic != null;

        RefIteration = laReference.getBegin(CurrentLAStage - 1);

        while (CurrentLAStage > 0) {
            if (laReference.useDoublePrecisionAtStage(CurrentLAStage - 1)) {
                DPEvaluation = true;
                break;
            }

            CurrentLAStage--;

            int begin = laReference.getBegin(CurrentLAStage);

            if(laReference.isLAStageInvalid(begin, DeltaSub0ChebyshevNorm)) {
                RefIteration = laReference.getNextStageLAIndex(begin);
                continue;
            }

            int end = laReference.getEnd(CurrentLAStage);


            while(iterations < max_iterations) {

                LAstep las = laReference.getLA(this, RefIteration, DeltaSubN, iterations, max_iterations);

                if(las.unusable) {
                    RefIteration = las.NextStageLAIndex;
                    break;
                }

                //No update values

                if (trap != null) {
                    trap.check(zc, iterations);
                }

                int l = las.step;
                iterations += l;
                bla_steps++;
                bla_iterations += l;

                DeltaSubN = las.Evaluate(DeltaSub0);

                RefIteration++;

                if(needsComplexZ) {
                    zold2.assign(zold);
                    zold.assign(zc);
                    zoldDeep = z;
                }
                else {
                    zoldDeep2 = zoldDeep;
                    zoldDeep = z;
                }

                //No Plane influence work
                //No Pre filters work
                z = las.getZ(DeltaSubN);

                if(needsComplexZ) {
                    zc = z.toComplex();
                }
                //No Post filters work

                // rebase

                if(z.chebyshevNorm().compareToBothPositiveReduced(DeltaSubN.chebyshevNorm()) < 0|| RefIteration >= end) {
                    DeltaSubN = z;
                    RefIteration = begin;
                    rebases++;
                }

                DeltaSubN.Normalize();

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0, las, z, zoldDeep);
                }
            }

            if (iterations >= max_iterations) {
                break;
            }
        }

        if(!needsComplexZ) {
            zc = z.toComplex();
            zold = zoldDeep.toComplex();
            zold2 = zoldDeep2.toComplex();
        }

        DPEvaluation = DPEvaluation || laReference.performDoublePrecisionSimplePerturbation;

        if(!DPEvaluation) {

            MantExp norm_squared_m = z.norm_squared();

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if (bailout_algorithm2.escaped(zc, zold, zold2, iterations, c, start, c0, norm_squared_m.toDouble(), pixel)) {
                    escaped = true;

                    finalizeStatistic(true, zc);
                    outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel);
                    double res = out_color_algorithm.getResult(outColorData);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                    }

                    return getAndAccumulateStatsBLA(res);
                }

                // perturbation iteration
                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;
                perturb_iterations++;

                // rebase
                zold2.assign(zold);
                zold.assign(zc);
                zoldDeep = z;

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    z = getReferenceDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN);
                    zc = z.toComplex();
                }
                //No Post filters work

                norm_squared_m = z.norm_squared();
                if (norm_squared_m.compareToBothPositive(DeltaSubN.norm_squared()) < 0 || (RefIteration >= MaxRefIteration)) { //* 64
                    DeltaSubN = z;
                    RefIteration = 0;
                    rebases++;
                }

                DeltaSubN.Normalize();

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0, z, zoldDeep, null);
                }
            }
        }

        if(DPEvaluation && iterations < max_iterations) {
            Complex CDeltaSubN = DeltaSubN.toComplex();
            Complex CDeltaSub0 = DeltaSub0.toComplex();
            double zre = zc.getRe(), zim = zc.getIm();
            double dre = CDeltaSubN.getRe(), dim = CDeltaSubN.getIm();
            double d0re = CDeltaSub0.getRe(), d0im = CDeltaSub0.getIm();
            Complex Z;

            boolean isZero = CDeltaSub0.isZero();

            double CDeltaSub0ChebyshevNorm = DeltaSub0ChebyshevNorm.toDouble();

            while (CurrentLAStage > 0) {
                CurrentLAStage--;

                int begin = laReference.getBegin(CurrentLAStage);

                if(laReference.isLAStageInvalid(begin, CDeltaSub0ChebyshevNorm)) {
                    RefIteration = laReference.getNextStageLAIndex(begin);
                    continue;
                }

                int end = laReference.getEnd(CurrentLAStage);


                while(iterations < max_iterations) {

                    LAstep las = laReference.getLA(this, RefIteration, dre, dim, iterations, max_iterations);

                    if(las.unusable) {
                        RefIteration = las.NextStageLAIndex;
                        break;
                    }

                    //No update values

                    if (trap != null) {
                        trap.check(zc, iterations);
                    }

                    int l = las.step;
                    iterations += l;
                    bla_steps++;
                    bla_iterations += l;

                    if(isZero) {
                       // CDeltaSubN = las.EvaluateWithZeroD0Fast();
                        las.EvaluateWithZeroD0Fast();
                    }
                    else {
                        //CDeltaSubN = las.Evaluate(d0re, d0im);
                        las.Evaluate(d0re, d0im);
                    }
                    //dre = CDeltaSubN.getRe();
                    //dim = CDeltaSubN.getIm();

                    dre = las.outdre;
                    dim = las.outdim;

                    RefIteration++;

                    zold2.assign(zold);
                    zold.assign(zc);

                    //No Plane influence work
                    //No Pre filters work
                    Z = las.getRefp1();
                    zre = Z.getRe() + dre;
                    zim = Z.getIm() + dim;
                    zc.assign(zre, zim);
                    //No Post filters work

                    // rebase

                    if(Math.max(Math.abs(zre), Math.abs(zim)) < Math.max(Math.abs(dre), Math.abs(dim)) || RefIteration >= end) {
                        dre = zre;
                        dim = zim;
                        RefIteration = begin;
                        rebases++;
                    }

                    if (statistic != null) {
                        statistic.insert(zc, zold, zold2, iterations, c, start, c0, las);
                    }
                }

                if (iterations >= max_iterations) {
                    break;
                }
            }

            return PerturbationAfterBLA2(zc, c, pixel, zre, zim, dre, dim, d0re, d0im, RefIteration, MaxRefIteration, isZero);
        }


        finalizeStatistic(false, zc);
        inColorData.setData(zc, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(zc, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsBLA(in);


    }

    @Override
    protected double PerturbationAfterExtendedRange(Complex z, Complex c, Complex pixel, MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration, int MaxRefIteration, int ReferencePeriod, boolean usedDeepCode) {
        if(TaskRender.COMPRESS_REFERENCE || burning_ship) {
            return super.PerturbationAfterExtendedRange(z, c, pixel, DeltaSubN, DeltaSub0, RefIteration, MaxRefIteration, ReferencePeriod, usedDeepCode);
        }

        Complex CDeltaSubN = DeltaSubN.toComplex();
        Complex CDeltaSub0 = DeltaSub0.toComplex();

        double zre = z.getRe(), zim = z.getIm();
        double dre = CDeltaSubN.getRe(), dim = CDeltaSubN.getIm();
        double d0re = CDeltaSub0.getRe(), d0im = CDeltaSub0.getIm();
        double tempre, tempim, temp;

        double[] RefRe = reference.re;
        double[] RefIm = reference.im;

        boolean isNonZero = !CDeltaSub0.isZero();

        if (!usedDeepCode && iterations != 0 && RefIteration < MaxRefIteration) {
            zre = RefRe[RefIteration] + dre;
            zim = RefIm[RefIteration] + dim;
            z.assign(zre, zim);
        } else if (!usedDeepCode && iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            zre = RefRe[RefIteration] + dre;
            zim = RefIm[RefIteration] + dim;
            z.assign(zre, zim);
        }

        double RefReVal = RefRe[RefIteration];
        double RefImVal = RefIm[RefIteration];

        double norm_squared = zre * zre + zim * zim;

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(z, iterations);
            }

            if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return res;
            }

            // perturbation iteration
            tempre = 2 * RefReVal + dre;
            tempim = 2 * RefImVal + dim;

            temp = tempre * dre - tempim * dim;
            dim = tempre * dim + tempim * dre;
            dre = temp;

            if(isNonZero) {
                dre += d0re;
                dim += d0im;
            }

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(z);

            //No Plane influence work
            //No Pre filters work
            if (max_iterations > 1) {
                RefReVal = RefRe[RefIteration];
                RefImVal = RefIm[RefIteration];
                zre = RefReVal + dre;
                zim = RefImVal + dim;
                z.assign(zre, zim);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            norm_squared = zre * zre + zim * zim;

            if (norm_squared < dre * dre + dim * dim || RefIteration >= MaxRefIteration) {
                dre = zre;
                dim = zim;
                RefIteration = 0;
                RefReVal = RefRe[RefIteration];
                RefImVal = RefIm[RefIteration];
                rebases++;
            }

        }

        finalizeStatistic(false, z);
        inColorData.setData(z, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return in;
    }

    @Override
    public double iterateJuliaWithPerturbation(Complex[] complexIn, Complex dpixel) {

        if(TaskRender.COMPRESS_REFERENCE || burning_ship) {
            return super.iterateJuliaWithPerturbation(complexIn, dpixel);
        }

        double_iterations = 0;
        rebases = 0;

        iterations = 0;

        int RefIteration = iterations;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z

        Complex pixel = dpixel.plus(refPointSmall);

        ReferenceData data = referenceData;
        double[] RefRe = data.Reference.re;
        double[] RefIm = data.Reference.im;
        int MaxRefIteration = referenceOrbit.MaxRefIteration;

        Complex z = complexIn[0];
        Complex c = complexIn[1];
        double zre = z.getRe(), zim = z.getIm();
        double dre = DeltaSubN.getRe(), dim = DeltaSubN.getIm();
        double tempre, tempim, temp;

        double norm_squared = zre * zre + zim * zim;

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(z, iterations);
            }

            if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, norm_squared, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return getAndAccumulateStatsNotDeep(res);
            }

            tempre = 2 * RefRe[RefIteration] + dre;
            tempim = 2 * RefIm[RefIteration] + dim;

            temp = tempre * dre - tempim * dim;
            dim = tempre * dim + tempim * dre;
            dre = temp;

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(z);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                zre = RefRe[RefIteration] + dre;
                zim = RefIm[RefIteration] + dim;
                z.assign(zre, zim);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            norm_squared = zre * zre + zim * zim;
            if (norm_squared < dre * dre + dim * dim || RefIteration >= MaxRefIteration) {
                dre = zre;
                dim = zim;
                RefIteration = 0;

                data = secondReferenceData;
                RefRe = data.Reference.re;
                RefIm = data.Reference.im;
                MaxRefIteration = secondReferenceOrbit.MaxRefIteration;
                rebases++;
            }
        }

        finalizeStatistic(false, z);
        inColorData.setData(z, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsNotDeep(in);

    }

    @Override
    public double iterateJuliaWithPerturbation(Complex[] complexIn, MantExpComplex dpixel) {

        if(TaskRender.COMPRESS_REFERENCE || burning_ship) {
            return super.iterateJuliaWithPerturbation(complexIn, dpixel);
        }

        float_exp_iterations = 0;
        double_iterations = 0;
        rebases = 0;

        iterations = 0;

        int totalSkippedIterations = 0;

        int RefIteration = iterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        Complex zc = complexIn[0];
        Complex c = complexIn[1];

        ReferenceDeepData deepData = referenceDeepData;
        ReferenceData data = referenceData;
        int MaxRefIteration = referenceOrbit.MaxRefIteration;

        int minExp = -1000;
        int reducedExp = minExp / (int)getPower();

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex z = getReferenceDeepValue(deepData.Reference, RefIteration).plus_mutable(DeltaSubN);
            MantExpComplex zoldDeep;

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if (doBailCheck && bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel)) {
                    escaped = true;

                    finalizeStatistic(true, zc);
                    outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel);
                    double res = out_color_algorithm.getResult(outColorData);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                DeltaSubN = perturbationFunction(DeltaSubN, deepData, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(zc);
                zoldDeep = z;

                if (max_iterations > 1) {
                    z = getReferenceDeepValue(deepData.Reference, RefIteration).plus_mutable(DeltaSubN);
                    zc = z.toComplex();
                }

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0, z, zoldDeep , null);
                }

                if (z.norm_squared().compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;

                    deepData = secondReferenceDeepData;
                    data = secondReferenceData;
                    MaxRefIteration = secondReferenceOrbit.MaxRefIteration;
                    rebases++;
                }

                DeltaSubN.Normalize();

                if(!useFullFloatExp) {
                    if (DeltaSubN.getMinExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!useFullFloatExp) {
            Complex CDeltaSubN = DeltaSubN.toComplex();

            double zre = zc.getRe(), zim = zc.getIm();
            double dre = CDeltaSubN.getRe(), dim = CDeltaSubN.getIm();
            double tempre, tempim, temp;

            double[] RefRe = data.Reference.re;
            double[] RefIm = data.Reference.im;

            double norm_squared = zre * zre + zim * zim;

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if (bailout_algorithm2.escaped(zc, zold, zold2, iterations, c, start, c0, norm_squared, pixel)) {
                    escaped = true;

                    finalizeStatistic(true, zc);
                    outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel);
                    double res = out_color_algorithm.getResult(outColorData);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                tempre = 2 * RefRe[RefIteration] + dre;
                tempim = 2 * RefIm[RefIteration] + dim;

                temp = tempre * dre - tempim * dim;
                dim = tempre * dim + tempim * dre;
                dre = temp;

                RefIteration++;
                double_iterations++;

                zold2.assign(zold);
                zold.assign(zc);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    zre = RefRe[RefIteration] + dre;
                    zim = RefIm[RefIteration] + dim;
                    zc.assign(zre, zim);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0);
                }

                norm_squared = zre * zre + zim * zim;
                if (norm_squared < dre * dre + dim * dim || RefIteration >= MaxRefIteration) {
                    dre = zre;
                    dim = zim;
                    RefIteration = 0;
                    data = secondReferenceData;
                    MaxRefIteration = secondReferenceOrbit.MaxRefIteration;
                    RefRe = data.Reference.re;
                    RefIm = data.Reference.im;
                    rebases++;
                }

            }
        }

        finalizeStatistic(false, zc);
        inColorData.setData(zc, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(zc, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsNotScaled(in);

    }

    @Override
    public double iterateFractalWithPerturbationScaled(Complex[] complexIn, MantExpComplex dpixel) {

        if(TaskRender.COMPRESS_REFERENCE || burning_ship) {
            return super.iterateFractalWithPerturbationScaled(complexIn, dpixel);
        }

        float_exp_iterations = 0;
        double_iterations = 0;
        scaled_iterations = 0;
        rebases = 0;
        realigns = 0;

        double power = getPower();
        double reAlignThreshold = power == 2 ? 1e100 : Math.exp(Math.log(1e200) / power);

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        int totalSkippedIterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : SAskippedIterations;
        iterations = totalSkippedIterations;

        int RefIteration = iterations;

        int ReferencePeriod = getPeriod();

        int MaxRefIteration = getReferenceFinalIterationNumber(true);

        int minExp = -1000;
        int reducedExp = minExp / (int) getPower();

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        boolean useFullFloatExp = useFullFloatExp();

        boolean doBailCheck = useFullFloatExp || TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();
        Complex zc = complexIn[0];
        Complex c = complexIn[1];

        boolean usedDeepCode = false;
        if (useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            usedDeepCode = true;

            MantExpComplex z = MantExpComplex.create();
            double norm_squared = 0;
            if (iterations != 0 && RefIteration < MaxRefIteration) {
                z = getReferenceDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN);
                zc = z.toComplex();
                norm_squared = zc.norm_squared();
            } else if (iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                z = getReferenceDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN);
                zc = z.toComplex();
                norm_squared = zc.norm_squared();
            }

            GenericComplex zoldDeep;
            GenericComplex zDeep = z;

            MantExpComplex tempDeltaSubN = MantExpComplex.copy(DeltaSubN);
            MantExpComplex tempDeltaSub0 = MantExpComplex.copy(DeltaSub0);
            long exponent = -DeltaSubN.getAverageExp();
            tempDeltaSub0.addExp(exponent);
            tempDeltaSubN.addExp(exponent);


            MantExp S = tempDeltaSubN.norm();
            double s = S.toDoubleSub(exponent);
            double ss = s * s;

            Complex DeltaSubNscaled = tempDeltaSubN.divide(S).toComplex();
            Complex DeltaSub0scaled = tempDeltaSub0.divide(S).toComplex();
            double dnReScaled = DeltaSubNscaled.getRe();
            double dnImScaled = DeltaSubNscaled.getIm();
            double d0ReScaled = DeltaSub0scaled.getRe();
            double d0ImScaled = DeltaSub0scaled.getIm();
            double temp, tempre, tempim;
            double zre = zc.getRe();
            double zim = zc.getIm();

            double[] RefRe = reference.re;
            double[] RefIm = reference.im;

            boolean isDeltaSub0NotZero = !DeltaSub0scaled.isZero();

            int nextTinyRefIndex = 0;
            int nextTinyRefIteration = 0;
            int tinyRefPtsLength = tinyRefPtsArray.length;
            do { //find the next tiny ref iteration which is after the skipped iterations
                if (nextTinyRefIndex < tinyRefPtsLength) {
                    nextTinyRefIteration = tinyRefPtsArray[nextTinyRefIndex];
                    nextTinyRefIndex++;
                } else {
                    nextTinyRefIteration = RefIteration == 0 ? 0 : max_iterations;
                }

            } while (nextTinyRefIteration < RefIteration);

            for (; iterations < max_iterations; iterations++) {

                if (RefIteration == nextTinyRefIteration) {
                    //No update values

                    if (trap != null) {
                        trap.check(zc, iterations);
                    }

                    if (doBailCheck && bailout_algorithm2.escaped(zc, zold, zold2, iterations, c, start, c0, norm_squared, pixel)) {
                        escaped = true;

                        finalizeStatistic(true, zc);
                        outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel);
                        double res = out_color_algorithm.getResult(outColorData);

                        res = getFinalValueOut(res);

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                        }

                        return getAndAccumulateStatsScaled(res);
                    }

                    DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                    RefIteration++;

                    float_exp_iterations++;

                    zold2.assign(zold);
                    zold.assign(zc);
                    zoldDeep = zDeep;

                    //No Plane influence work
                    //No Pre filters work
                    if (max_iterations > 1) {
                        zDeep = z = getReferenceDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN);
                        zc = z.toComplex();
                    }
                    //No Post filters work

                    if (statistic != null) {
                        statistic.insert(zc, zold, zold2, iterations, c, start, c0, zDeep, zoldDeep, null);
                    }

                    MantExp normSquared = z.norm_squared();

                    if(doBailCheck) {
                        norm_squared = normSquared.toDouble();
                    }

                    MantExp deltaNormSquared = DeltaSubN.norm_squared();
                    MantExp rescaledNorm = deltaNormSquared;
                    if (normSquared.compareToBothPositive(deltaNormSquared) < 0 || RefIteration >= MaxRefIteration) {
                        DeltaSubN = z;
                        RefIteration = 0;
                        nextTinyRefIndex = 0;
                        rescaledNorm = normSquared;
                        rebases++;
                    }

                    DeltaSubN.Normalize();

                    if (!useFullFloatExp) {
                        if (DeltaSubN.getMinExp() > reducedExp) {
                            iterations++;
                            break;
                        }
                    }

                    if (nextTinyRefIndex < tinyRefPtsLength) {
                        nextTinyRefIteration = tinyRefPtsArray[nextTinyRefIndex];
                        nextTinyRefIndex++;
                    } else {
                        nextTinyRefIteration = max_iterations;
                    }

                    if(RefIteration == nextTinyRefIteration) {
                        continue;
                    }

                    tempDeltaSubN.assign(DeltaSubN);
                    tempDeltaSub0.assign(DeltaSub0);

                    S = rescaledNorm.sqrt();

                    exponent = -S.getExp();
                    tempDeltaSubN.addExp(exponent);
                    tempDeltaSub0.addExp(exponent);
                    S.setExp(0);

                    //rescale
                    s = S.toDoubleSub(exponent);
                    ss = s * s;
                    DeltaSubNscaled = tempDeltaSubN.divide(S).toComplex();
                    DeltaSub0scaled = tempDeltaSub0.divide(S).toComplex();

                    dnReScaled = DeltaSubNscaled.getRe();
                    dnImScaled = DeltaSubNscaled.getIm();
                    d0ReScaled = DeltaSub0scaled.getRe();
                    d0ImScaled = DeltaSub0scaled.getIm();

                    isDeltaSub0NotZero = !DeltaSub0scaled.isZero();

                } else {
                    //No update values

                    if (trap != null) {
                        trap.check(zc, iterations);
                    }

                    if (doBailCheck && bailout_algorithm2.escaped(zc, zold, zold2, iterations, c, start, c0, norm_squared, pixel)) {
                        escaped = true;

                        finalizeStatistic(true, zc);
                        outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel);
                        double res = out_color_algorithm.getResult(outColorData);

                        res = getFinalValueOut(res);

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                        }

                        return getAndAccumulateStatsScaled(res);
                    }

                    if(s == 0) {
                        tempre = 2 * RefRe[RefIteration];
                        tempim = 2 * RefIm[RefIteration];

                        temp = tempre * dnReScaled - tempim * dnImScaled;
                        dnImScaled = tempre * dnImScaled + tempim * dnReScaled;
                        dnReScaled = temp;
                    }
                    else {
                        tempre = 2 * RefRe[RefIteration] + dnReScaled * s;
                        tempim = 2 * RefIm[RefIteration] + dnImScaled * s;

                        temp = tempre * dnReScaled - tempim * dnImScaled;
                        dnImScaled = tempre * dnImScaled + tempim * dnReScaled;
                        dnReScaled = temp;
                    }

                    if(isDeltaSub0NotZero) {
                        dnReScaled += d0ReScaled;
                        dnImScaled += d0ImScaled;
                    }

                    RefIteration++;

                    scaled_iterations++;

                    zold2.assign(zold);
                    zold.assign(zc);

                    //No Plane influence work
                    //No Pre filters work
                    if (max_iterations > 1) {
                        if(RefIteration == nextTinyRefIteration) {
                            DeltaSubN = MantExpComplex.create(dnReScaled, dnImScaled).times_mutable(S);
                            DeltaSubN.subExp(exponent);
                            zDeep = z = getReferenceDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN);
                            zc = z.toComplex();
                            zre = zc.getRe();
                            zim = zc.getIm();
                        }
                        else {
                            zre = RefRe[RefIteration] + dnReScaled * s;
                            zim = RefIm[RefIteration] + dnImScaled * s;
                            zc.assign(zre, zim);
                            zDeep = new Complex(zc);
                        }
                    }
                    //No Post filters work

                    if (statistic != null) {
                        statistic.insert(zc, zold, zold2, iterations, c, start, c0);
                    }

                    double DeltaSubNscaledNormSqr = dnReScaled * dnReScaled + dnImScaled * dnImScaled;
                    norm_squared = zre * zre + zim * zim;
                    if (norm_squared < DeltaSubNscaledNormSqr * ss || RefIteration >= MaxRefIteration) {
                        boolean isTiny = RefIteration == nextTinyRefIteration;
                        RefIteration = 0;

                        if(isTiny) {
                            DeltaSubN = z;
                            DeltaSubN.Normalize();
                        }
                        else {
                            DeltaSubN = MantExpComplex.create(zc);
                        }
                        rebases++;

                        if (!useFullFloatExp) {
                            if (DeltaSubN.getMinExp() > reducedExp) {
                                iterations++;
                                break;
                            }
                        }

                        tempDeltaSubN.assign(DeltaSubN);
                        tempDeltaSub0.assign(DeltaSub0);

                        //rescale
                        if(isTiny) {
                            exponent = -DeltaSubN.getAverageExp();

                            tempDeltaSubN.addExp(exponent);
                            tempDeltaSub0.addExp(exponent);

                            S = tempDeltaSubN.norm();
                            s = S.toDoubleSub(exponent);
                            ss = s * s;

                        }
                        else {
                            ss = norm_squared;
                            s = Math.sqrt(ss);
                            S = new MantExp(s);
                            exponent = -S.getExp();
                            tempDeltaSubN.addExp(exponent);
                            tempDeltaSub0.addExp(exponent);
                            S.setExp(0);
                        }

                        DeltaSubNscaled = tempDeltaSubN.divide(S).toComplex();
                        DeltaSub0scaled = tempDeltaSub0.divide(S).toComplex();

                        dnReScaled = DeltaSubNscaled.getRe();
                        dnImScaled = DeltaSubNscaled.getIm();
                        d0ReScaled = DeltaSub0scaled.getRe();
                        d0ImScaled = DeltaSub0scaled.getIm();

                        isDeltaSub0NotZero = !DeltaSub0scaled.isZero();

                        nextTinyRefIndex = 0;
                        if (nextTinyRefIndex < tinyRefPtsLength) {
                            nextTinyRefIteration = tinyRefPtsArray[nextTinyRefIndex];
                            nextTinyRefIndex++;
                        } else {
                            nextTinyRefIteration = max_iterations;
                        }

                        continue;
                    }


                    if (DeltaSubNscaledNormSqr >= reAlignThreshold) {
                        DeltaSubN = MantExpComplex.create(dnReScaled, dnImScaled).times_mutable(S);
                        DeltaSubN.Normalize();
                        DeltaSubN.subExp(exponent);

                        if (!useFullFloatExp) {
                            if (DeltaSubN.getMinExp() > reducedExp) {
                                iterations++;
                                break;
                            }
                        }

                        tempDeltaSubN.assign(DeltaSubN);
                        tempDeltaSub0.assign(DeltaSub0);

                        exponent = -DeltaSubN.getAverageExp();
                        tempDeltaSub0.addExp(exponent);
                        tempDeltaSubN.addExp(exponent);

                        //rescale
                        S = tempDeltaSubN.norm();
                        s = S.toDoubleSub(exponent);
                        ss = s * s;

                        DeltaSubNscaled = tempDeltaSubN.divide(S).toComplex();
                        DeltaSub0scaled = tempDeltaSub0.divide(S).toComplex();

                        dnReScaled = DeltaSubNscaled.getRe();
                        dnImScaled = DeltaSubNscaled.getIm();
                        d0ReScaled = DeltaSub0scaled.getRe();
                        d0ImScaled = DeltaSub0scaled.getIm();

                        isDeltaSub0NotZero = !DeltaSub0scaled.isZero();
                        realigns++;
                    }
                }
            }
        }

        if (!useFullFloatExp) {
            return getAndAccumulateStatsScaled(PerturbationAfterExtendedRange(zc, c, pixel, DeltaSubN, DeltaSub0, RefIteration, MaxRefIteration, ReferencePeriod, usedDeepCode));
        }

        finalizeStatistic(false, zc);
        inColorData.setData(zc, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(zc, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsScaled(in);

    }

    @Override
    public double iterateFractalWithPerturbationBLA3(Complex[] complexIn, Complex dpixel) {

        if(TaskRender.COMPRESS_REFERENCE) {
            return super.iterateFractalWithPerturbationBLA3(complexIn, dpixel);
        }

        bla_steps = 0;
        bla_iterations = 0;
        perturb_iterations = 0;
        rebases = 0;
        iterations = 0;

        int RefIteration = iterations;

        int MaxRefIteration = getReferenceFinalIterationNumber(true);

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        double dre = DeltaSubN.getRe(), dim = DeltaSubN.getIm();
        double tempre, tempim, d0re = DeltaSub0.getRe(), d0im = DeltaSub0.getIm();
        double temp, zre = 0, zim = 0;

        double normSquared = 0;

        Complex pixel = dpixel.plus(refPointSmall);
        Complex z = complexIn[0];
        Complex c = complexIn[1];

        double magD0 = Math.max(Math.abs(d0re), Math.abs(d0im));

        double[] RefRe = reference.re;
        double[] RefIm = reference.im;

        while (iterations < max_iterations) {

            //No update values

            if (trap != null) {
                trap.check(z, iterations);
            }

            if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                double res = out_color_algorithm.getResult(outColorData);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return getAndAccumulateStatsBLA(res);
            }

            // perturbation iteration
            tempre = 2 * RefRe[RefIteration] + dre;
            tempim = 2 * RefIm[RefIteration] + dim;

            temp = tempre * dre - tempim * dim;
            tempim = tempre * dim + tempim * dre;
            tempre = temp;

            dre = tempre + d0re;
            dim = tempim + d0im;

            RefIteration++;
            perturb_iterations++;

            zold2.assign(zold);
            zold.assign(z);

            //No Plane influence work
            //No Pre filters work
            //No Post filters work
            if (max_iterations > 1) {
                zre = RefRe[RefIteration] + dre;
                zim = RefIm[RefIteration] + dim;
                z.assign(zre, zim);
            }

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            iterations++;

            while (mLA.valid && iterations < max_iterations) {
                MipLAStep step = mLA.LookupBackwards(RefIteration, Math.max(Math.abs(dre), Math.abs(dim)), magD0, iterations, max_iterations);
                if(step == null) {
                    break;
                }

                if (trap != null) {
                    trap.check(z, iterations);
                }

                if(TaskRender.CHECK_BAILOUT_DURING_MIP_BLA_STEP) {
                    normSquared = zre * zre + zim * zim;
                    if (bailout_algorithm2.escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                        escaped = true;

                        finalizeStatistic(true, z);
                        outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel);
                        double res = out_color_algorithm.getResult(outColorData);

                        res = getFinalValueOut(res);

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                        }

                        return getAndAccumulateStatsBLA(res);
                    }
                }

                int l = step.getL();
                RefIteration += l;
                iterations += l;
                bla_steps++;
                bla_iterations += l;

                DeltaSubN = step.getValue(dre, dim, d0re, d0im);
                dre = DeltaSubN.getRe();
                dim = DeltaSubN.getIm();

                zold2.assign(zold);
                zold.assign(z);

                //No Plane influence work
                //No Pre filters work
                //No Post filters work
                zre = RefRe[RefIteration] + dre;
                zim = RefIm[RefIteration] + dim;
                z.assign(zre, zim);

                if (statistic != null) {
                    statistic.insert(z, zold, zold2, iterations, c, start, c0, step, l);
                }
            }


            //rebase
            normSquared = zre * zre + zim * zim;

            if (normSquared < dre * dre + dim * dim || (RefIteration >= MaxRefIteration)) {
                dre = zre;
                dim = zim;
                RefIteration = 0;
                rebases++;
            }
        }

        finalizeStatistic(false, z);
        inColorData.setData(z, zold, zold2, c, start, c0, pixel);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsBLA(in);

    }

    @Override
    public Complex function(Complex z, Complex c) {
        if(not_burning_ship) {
            return z.square_mutable_plus_c_mutable(c);
        }
        else {
            return z.abs_mutable().square_mutable_plus_c_mutable(c);
        }
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        if(not_burning_ship) {
            return z.square_mutable().plus_mutable(c);
        }
        else {
            return z.abs_mutable().square_mutable().plus_mutable(c);
        }
    }

    @Override
    protected double[] iterateFractalVectorized(Complex[][] complexes, Complex[] pixels) {

        /*VectorSpecies<Double> DOUBLE_SPECIES = DoubleVector.SPECIES_PREFERRED;
        int laneCount = pixels.length;

        double[] cre = new double[laneCount];
        double[] cim = new double[laneCount];
        double[] zre = new double[laneCount];
        double[] zim = new double[laneCount];
        double[] iters;

        for(int i = 0; i < laneCount; i++) {
            zre[i] = complexes[i][0].getRe();
            zim[i] = complexes[i][0].getIm();
            cre[i] = complexes[i][1].getRe();
            cim[i] = complexes[i][1].getIm();
        }
        DoubleVector creals = DoubleVector.fromArray(DOUBLE_SPECIES, cre, 0);
        DoubleVector cimaginarys = DoubleVector.fromArray(DOUBLE_SPECIES, cim, 0);
        DoubleVector zreals = DoubleVector.fromArray(DOUBLE_SPECIES, zre, 0);
        DoubleVector zimaginarys = DoubleVector.fromArray(DOUBLE_SPECIES, zim, 0);

        DoubleVector zreals_sqr;
        DoubleVector zimaginarys_sqr;
        DoubleVector norms;
        VectorMask<Double> maskNorm;
        DoubleVector temp_re;
        DoubleVector temp_im;

        double bailout_squared = bailout * bailout;

        DoubleVector bailouts = DoubleVector.broadcast(DOUBLE_SPECIES, bailout_squared);

        LongVector itersl = LongVector.zero(LongVector.SPECIES_PREFERRED);
        LongVector ones = LongVector.broadcast(LongVector.SPECIES_PREFERRED, 1);

        for(iterations = 0; iterations < max_iterations; iterations++) {

            zreals_sqr = zreals.mul(zreals);
            zimaginarys_sqr = zimaginarys.mul(zimaginarys);
            norms = zreals_sqr.add(zimaginarys_sqr);
            maskNorm = norms.lt(bailouts);

            if(!maskNorm.anyTrue()) {
                break;
            }

            temp_re = zreals_sqr.sub(zimaginarys_sqr).add(creals);
            temp_im = zreals.add(zreals).fma(zimaginarys, cimaginarys);

            if(maskNorm.allTrue()) {
                zreals = temp_re;
                zimaginarys = temp_im;
                itersl = itersl.add(ones);
            }
            else {
                zreals = zreals.blend(temp_re, maskNorm);
                zimaginarys = zimaginarys.blend(temp_im, maskNorm);
                itersl = itersl.add(ones, maskNorm.cast(LongVector.SPECIES_PREFERRED));
            }

        }

        iters = itersl.toDoubleArray();

        for(int i = 0; i < laneCount; i++) {
            if (iters[i] == max_iterations) {
                iters[i] = ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
            else {
                vescaped[i] = true;
            }
        }

        return iters;*/
        return null;

    }

    @Override
    public double getPower() {
        return 2;
    }
}
