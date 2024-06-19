package fractalzoomer.functions;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.fractal_options.iteration_statistics.*;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.*;
import fractalzoomer.utils.ColorAlgorithm;
import org.apfloat.Apfloat;

import java.util.ArrayList;

import static fractalzoomer.main.Constants.ESCAPE_TIME_SQUARES;
import static fractalzoomer.main.Constants.ESCAPE_TIME_SQUARES2;

public abstract class EscapingOrConverging extends Julia {

    protected double convergent_bailout;
    protected boolean converged;

    public EscapingOrConverging() {
        super();
    }

    public EscapingOrConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);
        setConvergentBailout(1E-11);
    }

    protected void setConvergentBailout(double val) {
        convergent_bailout = TaskRender.USER_CONVERGENT_BAILOUT > 0 ? TaskRender.USER_CONVERGENT_BAILOUT : val;
    }

    public EscapingOrConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        setConvergentBailout(1E-11);
    }

    //orbit
    public EscapingOrConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

    }

    public EscapingOrConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);

    }


    @Override
    protected double iterateFractalWithPeriodicity(Complex[] complex, Complex pixel) {
        iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        converged = false;

        boolean temp1 = false, temp2 = false;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if ((iterations > 0 && (temp1 = convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel))) || (temp2 = bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel))) {
                escaped = true;
                converged = temp1;

                finalizeStatistic(true, complex[0]);
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel, temp2};
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

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {
        iterations = 0;

        converged = false;

        boolean temp1 = false, temp2 = false;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if ((iterations > 0 && (temp1 = convergent_bailout_algorithm.converged(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel))) || (temp2 = bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel))) {
                escaped = true;
                converged = temp1;

                finalizeStatistic(true, complex[0]);
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel, temp2};
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

        finalizeStatistic(false, complex[0]);
        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }

    @Override
    public double getConvergentBailout() {

        return Math.sqrt(convergent_bailout);

    }

    protected OutColorAlgorithm getEscapeTimeAlgorithm(boolean smoothing, int converging_smooth_algorithm, int escaping_smooth_algorithm) {
        if (!smoothing) {
            escapeTimeAlg = new EscapeTimeEOC();
        } else {
            escapeTimeAlg = new SmoothEscapeTimeEOC(bailout, getConvergentBailout(), escaping_smooth_algorithm, converging_smooth_algorithm, bailout_algorithm.getNormImpl());
        }
        return escapeTimeAlg;
    }

    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int escaping_smooth_algorithm, int converging_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                out_color_algorithm = getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm);
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                out_color_algorithm = new BinaryDecomposition(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                out_color_algorithm = new BinaryDecomposition2(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.BIOMORPH:
                out_color_algorithm = new Biomorphs(bailout, getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                out_color_algorithm = new EscapeTimeGaussianInteger3(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                out_color_algorithm = new EscapeTimeGaussianInteger4(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(2, getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2(getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadius(bailout, getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm), bailout_algorithm.getNormImpl());
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                out_color_algorithm = new EscapeTimeGrid(bailout, getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm), false, bailout_algorithm.getNormImpl());
                break;
            case MainWindow.BANDED:
                out_color_algorithm = new Banded();
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES:
                out_color_algorithm = new EscapeTimeFieldLines(bailout, getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm), bailout_algorithm.getNormImpl());
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES2:
                out_color_algorithm = new EscapeTimeFieldLines2(bailout, getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm), bailout_algorithm.getNormImpl());
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if (user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithmEOC(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                } else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithmEOC(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                }
                break;
            case ESCAPE_TIME_SQUARES:
                out_color_algorithm = new EscapeTimeSquares(6, getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
            case ESCAPE_TIME_SQUARES2:
                out_color_algorithm = new EscapeTimeSquares2(6, getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm));
                break;
        }

    }

    @Override
    public int type() {

        return MainWindow.ESCAPING_AND_CONVERGING;

    }

    @Override
    protected void StatisticFactory(StatisticsSettings sts, double[] plane_transform_center) {

        statisticIncludeEscaped = sts.statisticIncludeEscaped;
        statisticIncludeNotEscaped = sts.statisticIncludeNotEscaped;

        if (sts.statisticGroup == 1) {
            if (sts.statistic_escape_type == MainWindow.ESCAPING) {
                statistic = new UserStatisticColoring(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, bailout, plane_transform_center, globalVars, sts.useAverage, sts.user_statistic_init_value, sts.reductionFunction, sts.useIterations, sts.useSmoothing, sts.lastXItems);
            } else {
                statistic = new UserStatisticColoringEOCConverging(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, getConvergentBailout(), bailout, plane_transform_center, globalVars, sts.useAverage, sts.user_statistic_init_value, sts.reductionFunction, sts.useIterations, sts.useSmoothing, sts.lastXItems);
            }
        }
        else if(sts.statisticGroup == 2) {
            statistic = new Equicontinuity(sts.statistic_intensity, sts.useSmoothing, sts.useAverage, false, sts.equicontinuityDenominatorFactor, sts.equicontinuityInvertFactor, sts.equicontinuityDelta);
        }
        else if(sts.statisticGroup == 0) {

            switch (sts.statistic_type) {
                case MainWindow.STRIPE_AVERAGE:
                    statistic = new StripeAverage(sts.statistic_intensity, sts.stripeAvgStripeDensity, sts.useSmoothing, sts.useAverage, sts.lastXItems);
                    break;
                case MainWindow.CURVATURE_AVERAGE:
                    statistic = new CurvatureAverage(sts.statistic_intensity, sts.useSmoothing, sts.useAverage, sts.lastXItems);
                    break;
                case MainWindow.TRIANGLE_INEQUALITY_AVERAGE:
                    statistic = new TriangleInequalityAverage(sts.statistic_intensity, sts.useSmoothing, sts.useAverage, sts.lastXItems);
                    break;
                case MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE:
                    statistic = new CosArgDivideNormAverage(sts.statistic_intensity, sts.cosArgStripeDensity, sts.useSmoothing, sts.useAverage, sts.lastXItems);
                    break;
                case MainWindow.COS_ARG_DIVIDE_INVERSE_NORM:
                    statistic = new CosArgDivideInverseNorm(sts.statistic_intensity, sts.cosArgInvStripeDensity, sts.StripeDenominatorFactor, sts.useSmoothing, sts.lastXItems);
                    break;
                case MainWindow.ATOM_DOMAIN_BOF60_BOF61:
                    statistic = new AtomDomain(sts.showAtomDomains, sts.statistic_intensity, sts.atomNormType, sts.atomNNorm, sts.lastXItems, sts.atomDomainNormA, sts.atomDomainNormB);
                    break;
                case MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS:
                    statistic = new DiscreteLagrangianDescriptors(sts.statistic_intensity, sts.lagrangianPower, sts.useSmoothing, sts.useAverage, false, sts.langNormType, sts.langNNorm, sts.lastXItems, sts.langNormA, sts.langNormB);
                    break;
                case MainWindow.TWIN_LAMPS:
                    statistic = new TwinLamps(sts.statistic_intensity, sts.twlFunction, sts.twlPoint, sts.useSmoothing, sts.lastXItems);
                    break;
                case MainWindow.CHECKERS:
                    statistic = new Checkers(sts.statistic_intensity, sts.patternScale, sts.checkerNormType, sts.checkerNormValue, sts.useSmoothing, sts.useAverage, sts.lastXItems, sts.checkerNormA, sts.checkerNormB);
                    break;

            }
        }

        statistic.setNormSmoothingImpl(bailout_algorithm.getNormImpl(), bailout);
    }

    @Override
    protected void finalizeStatistic(boolean escaped, Complex z) {
        if(statistic == null) {
            return;
        }

        if(converged) {
            statistic.setMode(GenericStatistic.NORMAL_CONVERGE);
        }

        super.finalizeStatistic(escaped, z);

        statistic.setMode(GenericStatistic.NORMAL_ESCAPE);
    }

    @Override
    protected double getStatistic(double result, boolean escaped) {

        if ((converged && statistic.getType() == MainWindow.ESCAPING) || (!converged && statistic.getType() == MainWindow.CONVERGING)) {
            return result;
        }

        return super.getStatistic(result, escaped);

    }
}
