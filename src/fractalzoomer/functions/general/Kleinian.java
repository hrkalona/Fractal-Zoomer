/*
 * Copyright (C) 2020 hrkalona
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
import fractalzoomer.functions.FractalWithoutConstant;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.*;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class Kleinian extends FractalWithoutConstant {

    private final double error = 1e-8;
    private double u;
    private double v;
    private final Complex t;
    private final int N = 100000000;
    private double K;
    private double M;

    public Kleinian(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, double[] kleinianLine, double kleinianK, double kleinianM, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        u = kleinianLine[0];
        v = kleinianLine[1];
        K = kleinianK;
        M = kleinianM;

        t = new Complex(u, v);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

    }

    //orbit
    public Kleinian(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double[] kleinianLine, double kleinianK, double kleinianM) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        u = kleinianLine[0];
        v = kleinianLine[1];
        K = kleinianK;
        M = kleinianM;

        t = new Complex(u, v);
    }

    @Override
    public void function(Complex[] complex) {

        double re = complex[0].getRe();
        double im = complex[0].getIm();

        re = ((re + 2 * N - 1 + v * im / u) % 2.0) - 1 - v * im / u;

        double f = re >= -v * 0.5 ? 1 : -1;

        double line3_im = u * 0.5 + f * K * u * (1 - Math.exp(-M * Math.abs(re + v * 0.5)));

        complex[0].setRe(re);
        complex[0].setIm(im);

        if (im < line3_im) {
            complex[0] = (t.times(complex[0]).sub_i_mutable(1)).divide_mutable(complex[0].times_i(1).negative_mutable());
        } else {
            complex[0] = (complex[0].times_i(1).plus_mutable(t)).i_divide_mutable(1);
        }

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[1];
        complex[0] = new Complex(pixel);//z

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[0]);

        return complex;

    }

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {
        iterations = 0;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (complex[0].getIm() < 0.0 || complex[0].getIm() > u) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, pixel, start, c0};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0);
                }

                return out;
            }

            //If the iterated points enters a 2-cycle , bail out.
            if (iterations != 0 && complex[0].distance_squared(zold2) < error) {

                Object[] object = {complex[0], zold, zold2, pixel, start, c0};
                double in = in_color_algorithm.getResult(object);

                in = getFinalValueIn(in);

                if (inTrueColorAlgorithm != null) {
                    setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0);
                }

                return in;
            }

            //Store prévious iterates
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, pixel, start, c0);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, pixel, start, c0);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
            }
        }

        Object[] object = {complex[0], zold, zold2, pixel, start, c0};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0);
        }

        return in;

    }

    @Override
    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int converging_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {

        super.OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if (smoothing) {
                    out_color_algorithm = new SmoothEscapeTimeKleinian(u);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if (smoothing) {
                    out_color_algorithm = new SmoothBinaryDecompositionKleinian(u);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if (smoothing) {
                    out_color_algorithm = new SmoothBinaryDecomposition2Kleinian(u);
                }
                break;
            case MainWindow.BIOMORPH:
                if (smoothing) {
                    out_color_algorithm = new SmoothBiomorphsKleinian(u, bailout);
                }
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if (smoothing) {
                    out_color_algorithm = new SmoothEscapeTimeGridKleinian(u, log_bailout_squared);
                }
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES:
                if (smoothing) {
                    out_color_algorithm = new SmoothEscapeTimeFieldLinesKleinian(u, log_bailout_squared);
                }
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES2:
                if (smoothing) {
                    out_color_algorithm = new SmoothEscapeTimeFieldLines2Kleinian(u, log_bailout_squared);
                }
                break;

        }
    }

}
