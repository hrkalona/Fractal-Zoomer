
package fractalzoomer.functions.general;

import fractalzoomer.bailout_conditions.CircleBailoutCondition;
import fractalzoomer.core.Complex;
import fractalzoomer.functions.FractalWithoutConstant;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.*;
import org.apfloat.Apfloat;

import java.util.ArrayList;

import static fractalzoomer.main.Constants.ESCAPE_TIME_SQUARES;
import static fractalzoomer.main.Constants.ESCAPE_TIME_SQUARES2;

/**
 *
 * @author hrkalona
 */
public class Kleinian extends FractalWithoutConstant {

    private final static double error = 1e-8;
    private double u;
    private double v;
    private final Complex t;
    private final static int N = 100000000;
    private double K;
    private double M;

    public Kleinian(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, double[] kleinianLine, double kleinianK, double kleinianM, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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

        bailout_algorithm = new CircleBailoutCondition(4, false, this);

    }

    //orbit
    public Kleinian(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double[] kleinianLine, double kleinianK, double kleinianM) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

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

                finalizeStatistic(true, complex[0]);
                Object[] object = {iterations, complex[0], zold, zold2, pixel, start, c0, pixel};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0, pixel, object);
                }

                return out;
            }

            //If the iterated points enters a 2-cycle , bail out.
            if (iterations != 0 && complex[0].distance_squared(zold2) < error) {

                finalizeStatistic(false, complex[0]);
                Object[] object = {complex[0], zold, zold2, pixel, start, c0, pixel};
                double in = in_color_algorithm.getResult(object);

                in = getFinalValueIn(in);

                if (inTrueColorAlgorithm != null) {
                    setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
                }

                return in;
            }

            //Store prévious iterates
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
            }
        }

        finalizeStatistic(false, complex[0]);
        Object[] object = {complex[0], zold, zold2, pixel, start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
        }

        return in;

    }

    @Override
    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int escaping_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {

        super.OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        escape_time_algorithm = new SmoothEscapeTimeKleinian(u);
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if (smoothing) {
                    out_color_algorithm = escape_time_algorithm;
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if (smoothing) {
                    out_color_algorithm = new BinaryDecomposition(escape_time_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if (smoothing) {
                    out_color_algorithm = new BinaryDecomposition2(escape_time_algorithm);
                }
                break;
            case MainWindow.BIOMORPH:
                if (smoothing) {
                    out_color_algorithm = new Biomorphs(bailout, escape_time_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if (smoothing) {
                    out_color_algorithm = new EscapeTimeGrid(bailout, escape_time_algorithm, false, bailout_algorithm.getNormImpl());
                }
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES:
                if (smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLines(bailout, escape_time_algorithm, bailout_algorithm.getNormImpl());
                }
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES2:
                if (smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLines2(bailout, escape_time_algorithm, bailout_algorithm.getNormImpl());
                }
                break;
            case ESCAPE_TIME_SQUARES:
                if (smoothing) {
                    out_color_algorithm = new EscapeTimeSquares(5, escape_time_algorithm);
                }
                break;
            case ESCAPE_TIME_SQUARES2:
                if (smoothing) {
                    out_color_algorithm = new EscapeTimeSquares2(5, escape_time_algorithm);
                }
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if (user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithm(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars, escape_time_algorithm);
                } else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithm(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars, escape_time_algorithm);
                }
                break;

        }
    }

}
