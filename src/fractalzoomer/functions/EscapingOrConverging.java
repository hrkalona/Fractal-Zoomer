package fractalzoomer.functions;

import fractalzoomer.core.Complex;
import fractalzoomer.core.NumericLibrary;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.*;
import fractalzoomer.core.reference.DoubleReference;
import fractalzoomer.core.reference.ReferenceData;
import fractalzoomer.core.reference.ReferenceDeepData;
import fractalzoomer.fractal_options.iteration_statistics.*;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.*;
import fractalzoomer.utils.ColorAlgorithm;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;

import static fractalzoomer.main.Constants.*;

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
                outColorData.setData(iterations, complex[0], zold, zold2, complex[1], start, c0, pixel, temp2);
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
                outColorData.setData(iterations, complex[0], zold, zold2, complex[1], start, c0, pixel, temp2);
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

        escape_time_algorithm = getEscapeTimeAlgorithm(smoothing, converging_smooth_algorithm, escaping_smooth_algorithm);
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                out_color_algorithm = escape_time_algorithm;
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                out_color_algorithm = new BinaryDecomposition(escape_time_algorithm);
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                out_color_algorithm = new BinaryDecomposition2(escape_time_algorithm);
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe(escape_time_algorithm);
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm(escape_time_algorithm);
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm(escape_time_algorithm);
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm(escape_time_algorithm);
                break;
            case MainWindow.BIOMORPH:
                out_color_algorithm = new Biomorphs(bailout, escape_time_algorithm);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition(escape_time_algorithm);
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger(escape_time_algorithm);
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2(escape_time_algorithm);
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                out_color_algorithm = new EscapeTimeGaussianInteger3(escape_time_algorithm);
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                out_color_algorithm = new EscapeTimeGaussianInteger4(escape_time_algorithm);
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5(escape_time_algorithm);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(escape_time_algorithm);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2(escape_time_algorithm);
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadius(bailout, escape_time_algorithm, bailout_algorithm.getNormImpl());
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                out_color_algorithm = new EscapeTimeGrid(bailout, escape_time_algorithm, false, bailout_algorithm.getNormImpl());
                break;
            case MainWindow.BANDED:
                out_color_algorithm = new Banded();
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES:
                out_color_algorithm = new EscapeTimeFieldLines(bailout, escape_time_algorithm, bailout_algorithm.getNormImpl());
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES2:
                out_color_algorithm = new EscapeTimeFieldLines2(bailout, escape_time_algorithm, bailout_algorithm.getNormImpl());
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if (user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithmEOC(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars, escape_time_algorithm);
                } else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithmEOC(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars, escape_time_algorithm);
                }
                break;
            case ESCAPE_TIME_SQUARES:
                out_color_algorithm = new EscapeTimeSquares(escape_time_algorithm);
                break;
            case ESCAPE_TIME_SQUARES2:
                out_color_algorithm = new EscapeTimeSquares2(escape_time_algorithm);
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

        boolean needsRefSubCp = needsRefSubCp();
        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, false);
        DoubleReference.SHOULD_SAVE_MEMORY = false;
        boolean useCompressedRef = useCompressedRef();
        int[] precalIndexes = getNeededPrecalculatedTermsIndexes();

        initializeReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, max_ref_iterations, needsRefSubCp, useCompressedRef, precalIndexes);

        inputPixel = getInputPixel(inputPixel);

        int bigNumLib = NumericLibrary.getBignumImplementation(size, this);

        GenericComplex z, c, zold, zold2, start, c0, pixel, initVal = getReferenceInitVal(bigNumLib, inputPixel);
        Object normSquared;

        //boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;

        if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
            BigNumComplex bn = inputPixel.toBigNumComplex();
            z = iterations == 0 ? (isJulia ? bn : initVal) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new BigNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigNumComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? bn : initVal;
            c0 = c;
            pixel = bn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            BigIntNumComplex bn = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? (isJulia ? bn : initVal) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new BigIntNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? bn : initVal;
            c0 = c;
            pixel = bn;
        }
        else if(bigNumLib == Constants.BIGNUM_MPFR) {
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            z = iterations == 0 ? (isJulia ? bn : new MpfrBigNumComplex((MpfrBigNumComplex)initVal)) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new MpfrBigNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? new MpfrBigNumComplex(bn) : new MpfrBigNumComplex((MpfrBigNumComplex)initVal);
            c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? (isJulia ? bn : new MpirBigNumComplex((MpirBigNumComplex)initVal)) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new MpirBigNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? new MpirBigNumComplex(bn) : new MpirBigNumComplex((MpirBigNumComplex)initVal);
            c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? (isJulia ? ddn : initVal) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : ddn;
            zold = iterations == 0 ? new DDComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? ddn : initVal;
            c0 = c;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? (isJulia ? bn : new Complex((Complex)initVal)) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new Complex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? new Complex(bn) : new Complex((Complex)initVal);
            c0 = new Complex((Complex) c);
            pixel = new Complex(bn);
        }
        else {
            z = iterations == 0 ? (isJulia ? inputPixel : initVal) : referenceOrbit.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : inputPixel;
            zold = iterations == 0 ? new BigComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceOrbit.thirdTolastZValue;
            start = isJulia ? inputPixel : initVal;
            c0 = c;
            pixel = inputPixel;
        }

        normSquared = z.normSquared();

        Location loc = new Location();

        referenceOrbit.refPoint = inputPixel;

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(referenceOrbit.refPoint);
            Cdeep = loc.getMantExpComplex(c);

            if(isJulia) {
                seedSmallDeep = Cdeep;
            }

            if(lowPrecReferenceOrbitNeeded) {
                C = Cdeep.toComplex();

                if(isJulia) {
                    seedSmall = C;
                }
            }

            refPointSmall = refPointSmallDeep.toComplex();
        }
        else {
            if(lowPrecReferenceOrbitNeeded) {
                C = c.toComplex();

                if(isJulia) {
                    seedSmall = C;
                }
            }

            refPointSmall = referenceOrbit.refPoint.toComplex();
        }

        GenericComplex[] initialPrecal = initializeReferencePrecalculationData(c, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom);

        referenceOrbit.RefType = getRefType();

        boolean preCalcNormData = usesCircleBail();
        NormComponents normData = null;

        if(useCompressedRef) {
            initializeCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, needsRefSubCp, precalIndexes, z, c, initVal, start);
        }

        calculatedReferenceIterations = 0;

        MantExpComplex mcz = null;
        Complex cz = null;
        MantExpComplex tempmcz = null;

        for (; iterations < max_ref_iterations; iterations++, calculatedReferenceIterations++) {

            if(deepZoom) {
                mcz = loc.getMantExpComplex(z);
                if (mcz.isInfinite() || mcz.isNaN()) {
                    break;
                }
                tempmcz = setReferenceDeepValue(referenceDeep, iterations, mcz);
                //ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? mcz.toComplex() : z.toComplex();
                if (cz.isInfinite() || cz.isNaN()) {
                    break;
                }

                cz = setReferenceValue(reference, iterations, cz);
            }

            mcz = tempmcz;

            if (needsRefSubCp) {
                calculateRefSubCp(z, initVal, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom, referenceData, referenceDeepData, iterations, cz, mcz);
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            GenericComplex[] precalculatedData = precalculateReferenceData(z, c, normData, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom, referenceData, referenceDeepData, iterations, cz, mcz);

            if (iterations > 0 && (convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, c, start, c0, pixel)
                    || bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel, cz, mcz))) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                z = referenceFunction(z, c, normData, initialPrecal, precalculatedData);
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
        referenceOrbit.secondTolastZValue = zold;
        referenceOrbit.thirdTolastZValue = zold2;

        referenceOrbit.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            finalizeCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, needsRefSubCp, precalIndexes);
        }

        SAskippedIterations = 0;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        if(TaskRender.SAVE_REFERENCE && supportsReferenceSavingOrLoading()) {
            saveReference(TaskRender.SAVE_REFERENCE_FILE_PATH);
        }

        ReferenceCalculationTime = System.currentTimeMillis() - time;

        if(isJulia) {
            calculateJuliaReferenceOrbit(inputPixel, size, deepZoom, juliaIterations, progress);
        }
    }

    @Override
    protected void calculateJuliaReferenceOrbit(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int[] juliaIterations, JProgressBar progress) {

        int iterations = juliaIterations[0];
        if(defaultInitVal.isStatic() && iterations == 0 && ((!deepZoom && secondReferenceData.Reference != null) || (deepZoom && secondReferenceDeepData.Reference != null))) {
            return;
        }

        long time = System.currentTimeMillis();

        int max_ref_iterations = getReferenceMaxIterations();

        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_ref_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, false);
        DoubleReference.SHOULD_SAVE_MEMORY = false;
        boolean useCompressedRef = useCompressedRef();
        int[] precalIndexes = getNeededPrecalculatedTermsIndexes();
        boolean needsRefSubCp = needsRefSubCp();

        initializeSecondReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, max_ref_iterations, needsRefSubCp, useCompressedRef, precalIndexes);

        Location loc = new Location();

        int bigNumLib = NumericLibrary.getBignumImplementation(size, this);

        GenericComplex z, c = getSeed(bigNumLib), zold, zold2, start, c0, pixel, initVal = getReferenceInitVal(bigNumLib, inputPixel);
        Object normSquared;

        if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
            BigNumComplex bn = inputPixel.toBigNumComplex();
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new BigNumComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigNumComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = bn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            BigIntNumComplex bn = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new BigIntNumComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = bn;
        }
        else if(bigNumLib == Constants.BIGNUM_MPFR) {
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            z = iterations == 0 ? new MpfrBigNumComplex((MpfrBigNumComplex)initVal) : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = new MpfrBigNumComplex((MpfrBigNumComplex)initVal);
            c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? new MpirBigNumComplex((MpirBigNumComplex)initVal) : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new MpirBigNumComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = new MpirBigNumComplex((MpirBigNumComplex)initVal);
            c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new DDComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? new Complex((Complex)initVal) : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new Complex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : secondReferenceOrbit.thirdTolastZValue;
            start = new Complex((Complex)initVal);
            c0 = new Complex((Complex) c);
            pixel = new Complex(bn);
        }
        else {
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new BigComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = inputPixel;
        }

        GenericComplex[] initialPrecal = initializeReferencePrecalculationData(c, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom);

        normSquared = z.normSquared();

        boolean preCalcNormData = usesCircleBail();
        NormComponents normData = null;

        MantExpComplex mcz = null;
        Complex cz = null;

        if(useCompressedRef) {
            initializeSecondCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, needsRefSubCp, precalIndexes, z, c, initVal, start);
        }

        calculatedSecondReferenceIterations = 0;

        MantExpComplex tempmcz = null;

        for (; iterations < max_ref_iterations; iterations++, calculatedSecondReferenceIterations++) {

            if(deepZoom) {
                mcz = loc.getMantExpComplex(z);
                if (mcz.isInfinite() || mcz.isNaN()) {
                    break;
                }
                tempmcz = setReferenceDeepValue(secondReferenceDeepData.Reference, iterations, mcz);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? mcz.toComplex() : z.toComplex();

                if (cz.isInfinite() || cz.isNaN()) {
                    break;
                }
                cz = setReferenceValue(secondReferenceData.Reference, iterations, cz);
            }

            mcz = tempmcz;

            if (needsRefSubCp) {
                calculateRefSubCp(z, initVal, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom, secondReferenceData, secondReferenceDeepData, iterations, cz, mcz);
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            GenericComplex[] precalculatedData = precalculateReferenceData(z, c, normData, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom, secondReferenceData, secondReferenceDeepData, iterations, cz, mcz);

            if (iterations > 0 &&
                    (convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, c, start, c0, pixel)
                     || bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel, cz, mcz))) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                z = referenceFunction(z, c, normData, initialPrecal, precalculatedData);
            }
            catch (Exception ex) {
                break;
            }


            if(progress != null && iterations % 1000 == 0) {
                progress.setValue(iterations - initIterations);
                progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (iterations - initIterations) / progress.getMaximum() * 100)) + "%");
            }

        }

        secondReferenceOrbit.lastZValue = z;
        secondReferenceOrbit.secondTolastZValue = zold;
        secondReferenceOrbit.thirdTolastZValue = zold2;

        secondReferenceOrbit.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            finalizeSecondCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, needsRefSubCp, precalIndexes);
        }

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        if(TaskRender.SAVE_REFERENCE && supportsReferenceSavingOrLoading()) {
            saveReference(TaskRender.SAVE_REFERENCE_FILE_PATH);
        }

        SecondReferenceCalculationTime = System.currentTimeMillis() - time;
    }

    @Override
    public double iterateFractalWithPerturbation(Complex[] complexIn, Complex dpixel) {

        double_iterations = 0;
        rebases = 0;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : SAskippedIterations;
        int RefIteration = iterations;

        int ReferencePeriod = getPeriod();

        int MaxRefIteration = getReferenceFinalIterationNumber(true);

        Complex z = complexIn[0];
        Complex c = complexIn[1];

        Complex refZ;
        Complex zWithoutInitVal = new Complex();

        if(iterations != 0 && RefIteration < MaxRefIteration) {
            refZ = getReferenceValue(reference, RefIteration);
            zWithoutInitVal = getExpressionValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(DeltaSubN);
            z = refZ.plus_mutable(DeltaSubN);
        }
        else if(iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            refZ = getReferenceValue(reference, RefIteration);
            zWithoutInitVal = getExpressionValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(DeltaSubN);
            z = refZ.plus_mutable(DeltaSubN);
        }

        boolean temp1 = false, temp2 = false;

        converged = false;

        Complex pixel = dpixel.plus(refPointSmall);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(z, iterations);
            }

            if ((temp1 = convergent_bailout_algorithm.converged(z, zold, zold2, iterations, c, start, c0, pixel))
                    || (temp2 = bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, 0.0, pixel))) {
                escaped = true;
                converged = temp1;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel, temp2);
                double out = out_color_algorithm.getResult(outColorData);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return getAndAccumulateStatsNotDeep(out);
            }

            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(z);


            if(max_iterations > 1){
                refZ = getReferenceValue(reference, RefIteration);
                zWithoutInitVal = getExpressionValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(DeltaSubN);
                z = refZ.plus_mutable(DeltaSubN);
            }

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
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

        return getAndAccumulateStatsNotDeep(in);

    }

    @Override
    public double iterateFractalWithPerturbation(Complex[] complexIn, MantExpComplex dpixel) {

        float_exp_iterations = 0;
        double_iterations = 0;
        rebases = 0;

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
        int reducedExp = minExp / (int)getPower();

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        boolean temp1 = false, temp2 = false;

        converged = false;

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();
        Complex zc = complexIn[0];
        Complex c = complexIn[1];

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        boolean usedDeepCode = false;

        MantExpComplex refZm;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            usedDeepCode = true;
            MantExpComplex zWithoutInitVal = MantExpComplex.create();
            MantExpComplex z = MantExpComplex.create();
            if(iterations != 0 && RefIteration < MaxRefIteration) {
                refZm = getReferenceDeepValue(referenceDeep, RefIteration);
                zWithoutInitVal = getExpressionDeepValue(referenceDeepData.ReferenceSubCp, RefIteration, refZm).plus_mutable(DeltaSubN);
                z = refZm.plus_mutable(DeltaSubN);
                zc = z.toComplex();
            }
            else if(iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                refZm = getReferenceDeepValue(referenceDeep, RefIteration);
                zWithoutInitVal = getExpressionDeepValue(referenceDeepData.ReferenceSubCp, RefIteration, refZm).plus_mutable(DeltaSubN);
                z = refZm.plus_mutable(DeltaSubN);
                zc = z.toComplex();
            }

            MantExpComplex zoldDeep;

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if(doBailCheck) {
                    if ((temp1 = convergent_bailout_algorithm.converged(zc, zold, zold2, iterations, c, start, c0, pixel))
                            || (temp2 = bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel))) {
                        escaped = true;
                        converged = temp1;

                        finalizeStatistic(true, zc);
                        outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel, temp2);
                        double out = out_color_algorithm.getResult(outColorData);

                        out = getFinalValueOut(out);

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                        }

                        return getAndAccumulateStatsNotScaled(out);
                    }
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(zc);
                zoldDeep = z;

                if (max_iterations > 1) {
                    refZm = getReferenceDeepValue(referenceDeep, RefIteration);
                    zWithoutInitVal = getExpressionDeepValue(referenceDeepData.ReferenceSubCp, RefIteration, refZm).plus_mutable(DeltaSubN);
                    z = refZm.plus_mutable(DeltaSubN);
                    zc = z.toComplex();
                }

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0, z, zoldDeep, null);
                }

                if (zWithoutInitVal.norm_squared().compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
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
            Complex CDeltaSub0 = DeltaSub0.toComplex();

            boolean isZero = CDeltaSub0.isZero();

            Complex zWithoutInitVal = new Complex();

            Complex refZ;

            if(!usedDeepCode && iterations != 0 && RefIteration < MaxRefIteration) {
                refZ = getReferenceValue(reference, RefIteration);
                zWithoutInitVal = getExpressionValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(CDeltaSubN);
                zc = refZ.plus_mutable(CDeltaSubN);
            }
            else if(!usedDeepCode && iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                refZ = getReferenceValue(reference, RefIteration);
                zWithoutInitVal = getExpressionValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(CDeltaSubN);
                zc = refZ.plus_mutable(CDeltaSubN);
            }

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if ((temp1 = convergent_bailout_algorithm.converged(zc, zold, zold2, iterations, c, start, c0, pixel))
                        || (temp2 = bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel))) {
                    escaped = true;
                    converged = temp1;

                    finalizeStatistic(true, zc);
                    outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel, temp2);
                    double out = out_color_algorithm.getResult(outColorData);

                    out = getFinalValueOut(out);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(out);
                }

                if (isZero) {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, RefIteration);
                } else {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, CDeltaSub0, RefIteration);
                }

                RefIteration++;
                double_iterations++;

                zold2.assign(zold);
                zold.assign(zc);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    refZ = getReferenceValue(reference, RefIteration);
                    zWithoutInitVal = getExpressionValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(CDeltaSubN);
                    zc = refZ.plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
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
    public double iterateJuliaWithPerturbation(Complex[] complexIn, Complex dpixel) {

        double_iterations = 0;
        rebases = 0;

        iterations = 0;
        int RefIteration = iterations;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z


        boolean temp1 = false, temp2 = false;

        converged = false;

        Complex pixel = dpixel.plus(refPointSmall);
        Complex z = complexIn[0];
        Complex c = complexIn[1];

        ReferenceData data = referenceData;
        int MaxRefIteration = referenceOrbit.MaxRefIteration;

        Complex zWithoutInitVal = new Complex();

        Complex refZ;

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(z, iterations);
            }

            if ((temp1 = convergent_bailout_algorithm.converged(z, zold, zold2, iterations, c, start, c0, pixel))
                    || (temp2 = bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, 0.0, pixel))) {
                escaped = true;
                converged = temp1;

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixel, temp2);
                double out = out_color_algorithm.getResult(outColorData);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
                }

                return getAndAccumulateStatsNotDeep(out);
            }

            DeltaSubN = perturbationFunction(DeltaSubN, data, RefIteration);

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(z);


            if(max_iterations > 1) {
                refZ = getReferenceValue(data.Reference, RefIteration);
                zWithoutInitVal = getExpressionValue(data.ReferenceSubCp, RefIteration, refZ).plus_mutable(DeltaSubN);
                z = refZ.plus_mutable(DeltaSubN);
            }

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;

                data = secondReferenceData;
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

        float_exp_iterations = 0;
        double_iterations = 0;
        rebases = 0;

        int totalSkippedIterations = 0;
        iterations = 0;
        int RefIteration = iterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z


        int minExp = -1000;
        int reducedExp = minExp / (int)getPower();

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        boolean temp1 = false, temp2 = false;

        converged = false;

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();
        Complex zc = complexIn[0];
        Complex c = complexIn[1];

        ReferenceDeepData deepData = referenceDeepData;
        ReferenceData data = referenceData;
        int MaxRefIteration = referenceOrbit.MaxRefIteration;

        MantExpComplex refZm;

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex zWithoutInitVal = MantExpComplex.create();
            MantExpComplex z = getReferenceDeepValue(deepData.Reference, RefIteration).plus_mutable(DeltaSubN);
            MantExpComplex zoldDeep;
            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if(doBailCheck) {
                    if ((temp1 = convergent_bailout_algorithm.converged(zc, zold, zold2, iterations, c, start, c0, pixel))
                            || (temp2 = bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel))) {
                        escaped = true;
                        converged = temp1;

                        finalizeStatistic(true, zc);
                        outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel, temp2);
                        double out = out_color_algorithm.getResult(outColorData);

                        out = getFinalValueOut(out);

                        if (outTrueColorAlgorithm != null) {
                            setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                        }

                        return getAndAccumulateStatsNotScaled(out);
                    }
                }

                DeltaSubN = perturbationFunction(DeltaSubN, deepData, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(zc);
                zoldDeep = z;

                if (max_iterations > 1) {
                    refZm = getReferenceDeepValue(deepData.Reference, RefIteration);
                    zWithoutInitVal = getExpressionDeepValue(deepData.ReferenceSubCp, RefIteration, refZm).plus_mutable(DeltaSubN);
                    z = refZm.plus_mutable(DeltaSubN);
                    zc = z.toComplex();
                }

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0, z, zoldDeep , null);
                }

                if (zWithoutInitVal.norm_squared().compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = zWithoutInitVal;
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

            Complex zWithoutInitVal = new Complex();

            Complex refZ;

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if ((temp1 = convergent_bailout_algorithm.converged(zc, zold, zold2, iterations, c, start, c0, pixel))
                        || (temp2 = bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel))) {
                    escaped = true;
                    converged = temp1;

                    finalizeStatistic(true, zc);
                    outColorData.setData(iterations, zc, zold, zold2, c, start, c0, pixel, temp2);
                    double out = out_color_algorithm.getResult(outColorData);

                    out = getFinalValueOut(out);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(out);
                }

                CDeltaSubN = perturbationFunction(CDeltaSubN, data, RefIteration);

                RefIteration++;
                double_iterations++;

                zold2.assign(zold);
                zold.assign(zc);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    refZ = getReferenceValue(data.Reference, RefIteration);
                    zWithoutInitVal = getExpressionValue(data.ReferenceSubCp, RefIteration, refZ).plus_mutable(CDeltaSubN);
                    zc = refZ.plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;

                    data = secondReferenceData;
                    MaxRefIteration = secondReferenceOrbit.MaxRefIteration;
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
    public double iterateFractalArbitraryPrecision(GenericComplex[] complex, GenericComplex pixel) {

        iterations = 0;

        complex = precalculateArbitraryData(complex);

        bailout_algorithm.setUseThreads(false);

        Complex start = gstart.toComplex();
        Complex c0 = gc0.toComplex();
        Complex pixelC = pixel.toComplex();

        boolean temp1 = false, temp2 = false;

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0].toComplex(), iterations);
            }

            if ((temp1 = convergent_bailout_algorithm.Converged(complex[0], gzold, gzold2, iterations, complex[1], gstart, gc0, pixel))
                    || (temp2 = bailout_algorithm.Escaped(complex[0], gzold, gzold2, iterations, complex[1], gstart, gc0, null, pixel))) {
                escaped = true;
                converged = temp1;

                Complex z = complex[0].toComplex();
                Complex zold = gzold.toComplex();
                Complex zold2 = gzold2.toComplex();
                Complex c = complex[1].toComplex();

                finalizeStatistic(true, z);
                outColorData.setData(iterations, z, zold, zold2, c, start, c0, pixelC, temp2);
                double out = out_color_algorithm.getResult(outColorData);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixelC);
                }

                return getAndAccumulateHP(out);
            }

            gzold2.set(gzold);
            gzold.set(complex[0]);

            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0].toComplex(), gzold.toComplex(), gzold2.toComplex(), iterations, complex[1].toComplex(), start, c0);
            }
        }

        Complex z = complex[0].toComplex();
        Complex zold = gzold.toComplex();
        Complex zold2 = gzold2.toComplex();
        Complex c = complex[1].toComplex();

        finalizeStatistic(false, z);
        inColorData.setData(z, zold, zold2, c, start, c0, pixelC);
        double in = in_color_algorithm.getResult(inColorData);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixelC);
        }

        return getAndAccumulateHP(in);

    }
}
