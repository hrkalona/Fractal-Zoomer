
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.reference.*;
import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import fractalzoomer.utils.WorkSpaceData;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class MandelbrotFourth extends Julia {

    private MandelVariation type;
    private MandelVariation type2;

    private boolean not_burning_ship;

    public MandelbrotFourth(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
    }

    public MandelbrotFourth(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        if(init_value) {
            if(variable_init_value) {
                if(user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
                else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new DefaultInitialValue();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public MandelbrotFourth(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();
    }

    //orbit
    public MandelbrotFourth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        if(init_value) {
            if(variable_init_value) {
                if(user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
                else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new DefaultInitialValue();
        }

    }

    public MandelbrotFourth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    @Override
    public void function(Complex[] complex) {

        type.getValue(complex[0]);
        complex[0].fourth_mutable().plus_mutable(complex[1]);
        type2.getValue(complex[0]);

    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || !juliter;
    }

    @Override
    public boolean shouldRecalculateForPeriodDetection(boolean deepZoom, Location externalLocation) {
        if(getPeriodDetectionAlgorithm() == 0 || DetectedPeriod == 0) {
            return true;
        }

        initializeReferenceDecompressor();

        if (deepZoom) {
            if(referenceData.period_mdzdc == null) {
                return true;
            }

            MantExpComplex mdzdc = referenceData.period_mdzdc;
            MantExp mradius = externalLocation.getSize().multiply2_mutable();
            MantExp temp = mdzdc.times(mradius).chebyshevNorm();

            if (temp.compareToBothPositiveReduced(getArrayDeepValue(referenceDeep, DetectedPeriod).chebyshevNorm()) > 0) {
                return false;
            }
        } else {
            if(referenceData.period_dzdc == null) {
                return true;
            }

            Complex dzdc = referenceData.period_dzdc;
            double radius = this.size * 2;

            if (radius * dzdc.chebyshevNorm() > getArrayValue(reference, DetectedPeriod).chebyshevNorm()) {
                return false;
            }
        }

        return true;

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

        boolean detectPeriod = TaskRender.DETECT_PERIOD && supportsPeriod() && getUserPeriod() == 0;
        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, detectPeriod);
        boolean stopReferenceCalculationOnDetectedPeriod = detectPeriod && TaskRender.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD && userPeriod == 0 && canStopOnDetectedPeriod();

        DoubleReference.SHOULD_SAVE_MEMORY = stopReferenceCalculationOnDetectedPeriod;
        boolean useCompressedRef = TaskRender.COMPRESS_REFERENCE_IF_POSSIBLE && supportsReferenceCompression();

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                referenceData.createAndSetShortcut(max_ref_iterations, false, 0, useCompressedRef);
            }
            else {
                referenceData.deallocate();
            }

            if (deepZoom) {
                referenceDeepData.createAndSetShortcut(max_ref_iterations, false, 0, useCompressedRef);
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

        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared, r = null, r0 = null, norm = null;

        if(iterations == 0) {
            //DetectedAtomPeriod = 0;
            DetectedPeriod = 0;
        }

        int bigNumLib = NumericLibrary.getBignumImplementation(size, this);
        int detectPeriodAlgorithm = getPeriodDetectionAlgorithm();

        if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
            BigNumComplex bn = inputPixel.toBigNumComplex();
            z = iterations == 0 ? (isJulia ? bn : new BigNumComplex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new BigNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigNumComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? bn : new BigNumComplex();
            c0 = c;
            pixel = bn;
            if(detectPeriod && detectPeriodAlgorithm == 0) {
                //referenceData.minValue = iterations == 0 ? BigNum.getMax() : referenceData.minValue;
                r0 = BigNum.create(size);
                r = iterations == 0 ? BigNum.copy((BigNum) r0) : referenceData.lastRValue;
            }
        }
        else if(bigNumLib == BIGNUM_BIGINT) {
            BigIntNumComplex bn = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? (isJulia ? bn : new BigIntNumComplex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
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
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new MpfrBigNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? new MpfrBigNumComplex(bn) : new MpfrBigNumComplex();
            c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
            pixel = new MpfrBigNumComplex(bn);
            if(detectPeriod && detectPeriodAlgorithm == 0) {
                //referenceData.minValue = iterations == 0 ? MpfrBigNum.getMax() : referenceData.minValue;
                r0 = new MpfrBigNum(size);
                r = iterations == 0 ? new MpfrBigNum((MpfrBigNum) r0) : referenceData.lastRValue;
            }
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? (isJulia ? bn : new MpirBigNumComplex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new MpirBigNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? new MpirBigNumComplex(bn) : new MpirBigNumComplex();
            c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
            pixel = new MpirBigNumComplex(bn);

            if(detectPeriod && detectPeriodAlgorithm == 0) {
                r0 = MpirBigNum.fromApfloat(size);
                r = iterations == 0 ? new MpirBigNum((MpirBigNum) r0) : referenceData.lastRValue;
            }
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? (isJulia ? ddn : new DDComplex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : ddn;
            zold = iterations == 0 ? new DDComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? ddn : new DDComplex();
            c0 = c;
            pixel = ddn;
            if(detectPeriod && detectPeriodAlgorithm == 0) {
                // referenceData.minValue = iterations == 0 ? new DoubleDouble(Double.MAX_VALUE) : referenceData.minValue;
                r0 = new DoubleDouble(size);
                r = iterations == 0 ? new DoubleDouble((DoubleDouble) r0) : referenceData.lastRValue;
            }
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? (isJulia ? bn : new Complex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new Complex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : referenceData.thirdTolastZValue;
            start = isJulia ? new Complex(bn) : new Complex();
            c0 = new Complex((Complex) c);
            pixel = new Complex(bn);
            if(detectPeriod && detectPeriodAlgorithm == 0) {
                //referenceData.minValue = iterations == 0 ? Double.MAX_VALUE : referenceData.minValue;
                r0 = size.doubleValue();
                r = iterations == 0 ? r0 : referenceData.lastRValue;
            }
        }
        else {
            z = iterations == 0 ? (isJulia ? inputPixel : new BigComplex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : inputPixel;
            zold = iterations == 0 ? new BigComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? inputPixel : new BigComplex();
            c0 = c;
            pixel = inputPixel;
            if(detectPeriod && detectPeriodAlgorithm == 0) {
                //referenceData.minValue = iterations == 0 ? new MyApfloat(Double.MAX_VALUE) : referenceData.minValue;
                r0 = size;
                r = iterations == 0 ? r0 : referenceData.lastRValue;
            }
        }

        normSquared = z.normSquared();

        refPoint = inputPixel;

        Location loc = new Location();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
            refPointSmall = refPointSmallDeep.toComplex();
            if(isJulia) {
                seedSmallDeep = loc.getMantExpComplex(c);
            }

            if(lowPrecReferenceOrbitNeeded && isJulia) {
                seedSmall = seedSmallDeep.toComplex();
            }
        }
        else {
            refPointSmall = refPoint.toComplex();
            if(lowPrecReferenceOrbitNeeded && isJulia) {
                seedSmall = c.toComplex();
            }
        }

        boolean isSeriesInUse = TaskRender.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation();
        boolean isBLAInUse = TaskRender.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation();
        boolean isBLA3InUse = TaskRender.APPROXIMATION_ALGORITHM == 5 && supportsBilinearApproximation3();
        RefType = getRefType();

        boolean usesCircleBail = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        boolean preCalcNormData = (detectPeriod && detectPeriodAlgorithm == 0) || usesCircleBail;
        NormComponents normData = null;

        Complex dzdc = null;
        MantExpComplex mdzdc = null;

        MantExp mradius = null;
        double radius = 0;

        Complex period_dzdc = null;
        MantExpComplex period_mdzdc = null;

        if(detectPeriod && DetectedPeriod == 0 && detectPeriodAlgorithm == 1) {
            if (iterations == 0) {
                if (deepZoom) {
                    mdzdc = MantExpComplex.create(1, 0);
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
        MantExp temp;

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[referenceDeep.id] = new ReferenceCompressor(this, iterations == 0 ? z.toMantExpComplex() : referenceData.compressorZm, c.toMantExpComplex(), start.toMantExpComplex());
            }
            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toComplex() : referenceData.compressorZ, c.toComplex(), start.toComplex());
            }
        }

        calculatedReferenceIterations = 0;

        MantExpComplex tempmcz = null;

        for (; iterations < max_ref_iterations; iterations++, calculatedReferenceIterations++) {

            if(deepZoom) {
                mcz = loc.getMantExpComplex(z);
                tempmcz = setArrayDeepValue(referenceDeep, iterations, mcz);
                //ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? mcz.toComplex() : z.toComplex();
                cz = setArrayValue(reference, iterations, cz);
            }

            mcz = tempmcz;

            if(stopReferenceCalculationOnDetectedPeriod && DetectedPeriod != 0) {
                break;
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            if(detectPeriod) {
                if(detectPeriodAlgorithm == 0) {
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
                    } else if (bigNumLib == Constants.BIGNUM_DOUBLE) {
//                        if (iterations > 0 && ((double) normSquared) < ((double) referenceData.minValue)){
//                            DetectedAtomPeriod = iterations;
//                            referenceData.minValue = normSquared;
//                        }

                        if (DetectedPeriod == 0 && ((double) r) > (double) (norm = Math.sqrt((double) normSquared)) && iterations > 0) {
                            DetectedPeriod = iterations;
                        }
                    }
                    else {
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
                            temp = mdzdc.times(mradius).chebyshevNorm();
                            if (temp.compareToBothPositiveReduced(mcz.chebyshevNorm()) > 0) {
                                DetectedPeriod = iterations;
                                period_mdzdc = MantExpComplex.copy(mdzdc);
                            }
                        } else {
                            if (radius * dzdc.chebyshevNorm() > cz.chebyshevNorm()) {
                                DetectedPeriod = iterations;
                                period_dzdc = new Complex(dzdc);
                            }
                        }
                    }
                }
            }

            if (iterations > 0 && bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                break;
            }

            if(!usesCircleBail) {
                zold2.set(zold);
                zold.set(z);
            }

            try {

                if(detectPeriod && (DetectedPeriod == 0 || stopReferenceCalculationOnDetectedPeriod)) {
                    if (detectPeriodAlgorithm == 1) {
                        if (deepZoom) {
                            mdzdc = mcz.cube().times_mutable(MantExp.FOUR).times_mutable(mdzdc).plus_mutable(MantExp.ONE);
                            mdzdc.Normalize();
                        } else {
                            dzdc = cz.cube().times4_mutable().times_mutable(dzdc).plus_mutable(1);
                        }
                    }
                    else {
                        r = calculateR(r, r0, normSquared, norm, workSpaceData);
                    }
                }

                if(preCalcNormData) {
                    if (burning_ship) {
                        z = z.abs_mutable().fourthFast_mutable(normData).plus_mutable(c);
                    } else {
                        z = z.fourthFast_mutable(normData).plus_mutable(c);
                    }
                }
                else {
                    if (burning_ship) {
                        z = z.abs_mutable().fourth().plus_mutable(c);
                    } else {
                        z = z.fourth().plus_mutable(c);
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
        referenceData.period_dzdc = period_dzdc;
        referenceData.period_mdzdc = period_mdzdc;

        referenceData.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[referenceDeep.id].compact(referenceDeep);
                referenceData.compressorZm = referenceCompressor[referenceDeep.id].getZDeep();
            }

            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[reference.id].compact(reference);
                referenceData.compressorZ = referenceCompressor[reference.id].getZ();
            }
        }

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        ReferenceCalculationTime = System.currentTimeMillis() - time;

        if(isJulia) {
            calculateJuliaReferencePoint(inputPixel, size, deepZoom, juliaIterations, progress);
        }

        SAskippedIterations = 0;
        if(isSeriesInUse) {
            calculateSeriesWrapper(size, deepZoom, externalLocation, progress);
        }
        else if(isBLAInUse) {
            calculateBLAWrapper(deepZoom, externalLocation, progress);
        }
        else if(isBLA3InUse) {
            calculateBLA3Wrapper(deepZoom, progress);
        }

    }

    @Override
    public Object calculateR(Object rIn, Object r0In, Object normSquared, Object norm, WorkSpaceData workSpaceData) {

        if(rIn instanceof BigNum) {
            BigNum r = (BigNum)rIn;
            BigNum r0 = (BigNum)r0In;
            BigNum az = ((BigNum)norm);
            BigNum azfourth = ((BigNum)normSquared).squareFull();

            BigNum temp = az.add(r);
            return temp.squareFull().squareFull().sub(azfourth).add(r0);
        }
        else if(rIn instanceof BigIntNum) {
            BigIntNum r = (BigIntNum)rIn;
            BigIntNum r0 = (BigIntNum)r0In;
            BigIntNum az = ((BigIntNum)norm);
            BigIntNum azfourth = ((BigIntNum)normSquared).square();

            BigIntNum temp = az.add(r);
            return temp.square().square().sub(azfourth).add(r0);
        }
        else if(rIn instanceof MpfrBigNum) {
            MpfrBigNum r = (MpfrBigNum)rIn;
            MpfrBigNum r0 = (MpfrBigNum)r0In;
            MpfrBigNum az = (MpfrBigNum)norm;
            MpfrBigNum azfourth = ((MpfrBigNum)normSquared).square(workSpaceData.tempPvar);

            az.add(r, az);
            az.square(r);
            r.square(r);
            r.sub(azfourth, r);
            r.add(r0, r);
            return r;
        }
        else if(rIn instanceof MpirBigNum) {
            MpirBigNum r = (MpirBigNum)rIn;
            MpirBigNum r0 = (MpirBigNum)r0In;
            MpirBigNum az = (MpirBigNum)norm;
            MpirBigNum azfourth = ((MpirBigNum)normSquared).square(workSpaceData.tempPvarp);

            az.add(r, az);
            az.square(r);
            r.square(r);
            r.sub(azfourth, r);
            r.add(r0, r);
            return r;
        }
        else if(rIn instanceof DoubleDouble) {
            DoubleDouble r = (DoubleDouble)rIn;
            DoubleDouble r0 = (DoubleDouble)r0In;
            DoubleDouble az = ((DoubleDouble)norm);
            DoubleDouble azfourth = ((DoubleDouble)normSquared).sqr();

            DoubleDouble temp = az.add(r);
            return temp.sqr().sqr().subtract(azfourth).add(r0);
        }
        else if(rIn instanceof Double) {
            double r = (double)rIn;
            double r0 = (double)r0In;
            double az = (double)norm;
            double azfourth = (double)normSquared * (double)normSquared;

            double temp = az + r;
            double temp2 = temp * temp;
            return temp2 * temp2 - azfourth + r0;
        }
        else {
            Apfloat r = (Apfloat)rIn;
            Apfloat r0 = (Apfloat)r0In;
            Apfloat az = (Apfloat)norm;
            Apfloat azfourth = MyApfloat.fp.multiply((Apfloat)normSquared, (Apfloat)normSquared);

            Apfloat temp = MyApfloat.fp.add(az, r);
            Apfloat temp2 = MyApfloat.fp.multiply(temp, temp);
            return MyApfloat.fp.add(MyApfloat.fp.subtract(MyApfloat.fp.multiply(temp2, temp2), azfourth), r0);
        }
    }

    @Override
    protected GenericComplex referenceFunction(GenericComplex z, GenericComplex c, NormComponents normData, GenericComplex[] initialPrecal, GenericComplex[] precalc) {
        if(normData != null) {
            if (burning_ship) {
                z = z.abs_mutable().fourthFast_mutable(normData).plus_mutable(c);
            } else {
                z = z.fourthFast_mutable(normData).plus_mutable(c);
            }
        }
        else {
            if (burning_ship) {
                z = z.abs_mutable().fourth().plus_mutable(c);
            } else {
                z = z.fourth().plus_mutable(c);
            }
        }

        return z;
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex X = getArrayValue(reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.fourth()
//                    .plus_mutable(DeltaSubN.cube().times4_mutable().times_mutable(X))
//                    .plus_mutable(DeltaSubN.square().times_mutable(6).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.times4().times_mutable(X.cube()))
//                    .plus_mutable(DeltaSub0);
            return DeltaSubN.plus(X.times4()).times_mutable(DeltaSubN).plus_mutable(X.square().times_mutable(6)).times_mutable(DeltaSubN).plus_mutable(X.cube().times4_mutable()).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
        else {
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double r2 = r*r;
            double i2 = i*i;
            double a2 = a*a;
            double b2 = b*b;
            double ar = a*r;
            double ib = i*b;
            double ab = a * b;
            double ri = r * i;
            double rb = r * b;
            double ai = a * i;

            double Dnr = 4 * (r2*ar + ar*a2 + i2*ib + ib*b2)
                    + 6 * (r2*a2 + i2*b2 - r2*b2 - a2*i2 - a2*b2)
                    + a2 * a2
                    + b2 * b2
                    - 12 * (r2*ib + ar*i2 + ar*b2 + a2*ib)
                    - 24 * ar*ib;

            double Dni = Complex.DiffAbs(ri, rb + ai + ab);

            Dni = 4 * ((r2 - i2)*(Dni) + Math.abs(ri + rb + ai + ab) * (2 * (ar - ib) + a2 - b2));

            return new Complex(Dnr, Dni).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex X = getArrayDeepValue(referenceDeep, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.fourth()
//                    .plus_mutable(DeltaSubN.cube().times4_mutable().times_mutable(X))
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.SIX).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.times4().times_mutable(X.cube()))
//                    .plus_mutable(DeltaSub0);
            return DeltaSubN.plus(X.times4()).times_mutable(DeltaSubN).plus_mutable(X.square().times_mutable(MantExp.SIX)).times_mutable(DeltaSubN).plus_mutable(X.cube().times4_mutable()).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);

        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.square();
            MantExp i2 = i.square();
            MantExp a2 = a.square();
            MantExp b2 = b.square();
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab = a.multiply(b);
            MantExp ri = r.multiply(i);
            MantExp rb = r.multiply(b);
            MantExp ai = a.multiply(i);

            MantExp Dnr = r2.multiply(ar).add_mutable(ar.multiply(a2)).add_mutable(i2.multiply(ib)).add_mutable(ib.multiply(b2)).multiply4_mutable()
                    .add_mutable(r2.multiply(a2).add_mutable(i2.multiply(b2)).subtract_mutable(r2.multiply(b2)).subtract_mutable(a2.multiply(i2)).subtract_mutable(a2.multiply(b2)).multiply_mutable(MantExp.SIX))
                    .add_mutable(a2.square())
                    .add_mutable(b2.square())
                    .subtract_mutable(r2.multiply(ib).add_mutable(ar.multiply(i2)).add_mutable(ar.multiply(b2)).add_mutable(a2.multiply(ib)).multiply_mutable(MantExp.TWELVE))
                    .subtract_mutable(ar.multiply(ib).multiply_mutable(MantExp.TWENTYFOUR));

            MantExp Dni = MantExpComplex.DiffAbs(ri, rb.add(ai).add_mutable(ab));

            Dni = r2.subtract(i2).multiply_mutable(Dni)
                    .add_mutable(ri.add(rb).add_mutable(ai).add_mutable(ab).abs_mutable().multiply_mutable(ar.subtract(ib).multiply2_mutable().add_mutable(a2).subtract_mutable(b2))).multiply4_mutable();

            return MantExpComplex.create(Dnr, Dni).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex X = getArrayValue(reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.fourth()
//                    .plus_mutable(DeltaSubN.cube().times4_mutable().times_mutable(X))
//                    .plus_mutable(DeltaSubN.square().times_mutable(6).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.times4().times_mutable(X.cube()));
            return DeltaSubN.plus(X.times4()).times_mutable(DeltaSubN).plus_mutable(X.square().times_mutable(6)).times_mutable(DeltaSubN).plus_mutable(X.cube().times4_mutable()).times_mutable(DeltaSubN);
        }
        else {
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double r2 = r*r;
            double i2 = i*i;
            double a2 = a*a;
            double b2 = b*b;
            double ar = a*r;
            double ib = i*b;
            double ab = a * b;
            double ri = r * i;
            double rb = r * b;
            double ai = a * i;

            double Dnr = 4 * (r2*ar + ar*a2 + i2*ib + ib*b2)
                    + 6 * (r2*a2 + i2*b2 - r2*b2 - a2*i2 - a2*b2)
                    + a2 * a2
                    + b2 * b2
                    - 12 * (r2*ib + ar*i2 + ar*b2 + a2*ib)
                    - 24 * ar*ib;

            double Dni = Complex.DiffAbs(ri, rb + ai + ab);

            Dni = 4 * ((r2 - i2)*(Dni) + Math.abs(ri + rb + ai + ab) * (2 * (ar - ib) + a2 - b2));

            return new Complex(Dnr, Dni);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex X = getArrayDeepValue(referenceDeep, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.fourth()
//                    .plus_mutable(DeltaSubN.cube().times4_mutable().times_mutable(X))
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.SIX).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.times4().times_mutable(X.cube()));
            return DeltaSubN.plus(X.times4()).times_mutable(DeltaSubN).plus_mutable(X.square().times_mutable(MantExp.SIX)).times_mutable(DeltaSubN).plus_mutable(X.cube().times4_mutable()).times_mutable(DeltaSubN);
        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.square();
            MantExp i2 = i.square();
            MantExp a2 = a.square();
            MantExp b2 = b.square();
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab = a.multiply(b);
            MantExp ri = r.multiply(i);
            MantExp rb = r.multiply(b);
            MantExp ai = a.multiply(i);

            MantExp Dnr = r2.multiply(ar).add_mutable(ar.multiply(a2)).add_mutable(i2.multiply(ib)).add_mutable(ib.multiply(b2)).multiply4_mutable()
                    .add_mutable(r2.multiply(a2).add_mutable(i2.multiply(b2)).subtract_mutable(r2.multiply(b2)).subtract_mutable(a2.multiply(i2)).subtract_mutable(a2.multiply(b2)).multiply_mutable(MantExp.SIX))
                    .add_mutable(a2.square())
                    .add_mutable(b2.square())
                    .subtract_mutable(r2.multiply(ib).add_mutable(ar.multiply(i2)).add_mutable(ar.multiply(b2)).add_mutable(a2.multiply(ib)).multiply_mutable(MantExp.TWELVE))
                    .subtract_mutable(ar.multiply(ib).multiply_mutable(MantExp.TWENTYFOUR));

            MantExp Dni = MantExpComplex.DiffAbs(ri, rb.add(ai).add_mutable(ab));

            Dni = r2.subtract(i2).multiply_mutable(Dni)
                    .add_mutable(ri.add(rb).add_mutable(ai).add_mutable(ab).abs_mutable().multiply_mutable(ar.subtract(ib).multiply2_mutable().add_mutable(a2).subtract_mutable(b2))).multiply4_mutable();

            return MantExpComplex.create(Dnr, Dni);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, ReferenceData data, int RefIteration) {
        Complex X = getArrayValue(data.Reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.fourth()
//                    .plus_mutable(DeltaSubN.cube().times4_mutable().times_mutable(X))
//                    .plus_mutable(DeltaSubN.square().times_mutable(6).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.times4().times_mutable(X.cube()));
            return DeltaSubN.plus(X.times4()).times_mutable(DeltaSubN).plus_mutable(X.square().times_mutable(6)).times_mutable(DeltaSubN).plus_mutable(X.cube().times4_mutable()).times_mutable(DeltaSubN);
        }
        else {
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double r2 = r*r;
            double i2 = i*i;
            double a2 = a*a;
            double b2 = b*b;
            double ar = a*r;
            double ib = i*b;
            double ab = a * b;
            double ri = r * i;
            double rb = r * b;
            double ai = a * i;

            double Dnr = 4 * (r2*ar + ar*a2 + i2*ib + ib*b2)
                    + 6 * (r2*a2 + i2*b2 - r2*b2 - a2*i2 - a2*b2)
                    + a2 * a2
                    + b2 * b2
                    - 12 * (r2*ib + ar*i2 + ar*b2 + a2*ib)
                    - 24 * ar*ib;

            double Dni = Complex.DiffAbs(ri, rb + ai + ab);

            Dni = 4 * ((r2 - i2)*(Dni) + Math.abs(ri + rb + ai + ab) * (2 * (ar - ib) + a2 - b2));

            return new Complex(Dnr, Dni);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, ReferenceDeepData data, int RefIteration) {
        MantExpComplex X = getArrayDeepValue(data.Reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.fourth()
//                    .plus_mutable(DeltaSubN.cube().times4_mutable().times_mutable(X))
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.SIX).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.times4().times_mutable(X.cube()));
            return DeltaSubN.plus(X.times4()).times_mutable(DeltaSubN).plus_mutable(X.square().times_mutable(MantExp.SIX)).times_mutable(DeltaSubN).plus_mutable(X.cube().times4_mutable()).times_mutable(DeltaSubN);
        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.square();
            MantExp i2 = i.square();
            MantExp a2 = a.square();
            MantExp b2 = b.square();
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab = a.multiply(b);
            MantExp ri = r.multiply(i);
            MantExp rb = r.multiply(b);
            MantExp ai = a.multiply(i);

            MantExp Dnr = r2.multiply(ar).add_mutable(ar.multiply(a2)).add_mutable(i2.multiply(ib)).add_mutable(ib.multiply(b2)).multiply4_mutable()
                    .add_mutable(r2.multiply(a2).add_mutable(i2.multiply(b2)).subtract_mutable(r2.multiply(b2)).subtract_mutable(a2.multiply(i2)).subtract_mutable(a2.multiply(b2)).multiply_mutable(MantExp.SIX))
                    .add_mutable(a2.square())
                    .add_mutable(b2.square())
                    .subtract_mutable(r2.multiply(ib).add_mutable(ar.multiply(i2)).add_mutable(ar.multiply(b2)).add_mutable(a2.multiply(ib)).multiply_mutable(MantExp.TWELVE))
                    .subtract_mutable(ar.multiply(ib).multiply_mutable(MantExp.TWENTYFOUR));

            MantExp Dni = MantExpComplex.DiffAbs(ri, rb.add(ai).add_mutable(ab));

            Dni = r2.subtract(i2).multiply_mutable(Dni)
                    .add_mutable(ri.add(rb).add_mutable(ai).add_mutable(ab).abs_mutable().multiply_mutable(ar.subtract(ib).multiply2_mutable().add_mutable(a2).subtract_mutable(b2))).multiply4_mutable();

            return MantExpComplex.create(Dnr, Dni);
        }
    }

    @Override
    public Complex getBlaA(Complex Z) {
        return Z.cube().times4_mutable();
    }

    @Override
    public MantExpComplex getBlaA(MantExpComplex Z) {
        return Z.cube().times4_mutable();
    }

    @Override
    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc, JProgressBar progress) {

        SAskippedIterations = 0;

        int numCoefficients = TaskRender.SERIES_APPROXIMATION_TERMS;

        if (numCoefficients < 2 || dsize.compareTo(MyApfloat.SA_START_SIZE) > 0) {
            return;
        }

        if (numCoefficients > 5) {
            numCoefficients = 5;
        }

        SATerms = numCoefficients;

        long[] logwToThe  = new long[numCoefficients + 1];

        final long[] magCoeff = new long[numCoefficients];

        SASize = loc.getMaxSizeInImage().log2approx();
        logwToThe[1] = SASize;

        for (int i = 2; i <= numCoefficients; i++) {
            logwToThe[i] = logwToThe[1] * i;
        }

        coefficients = new DeepReference(numCoefficients * max_data);

        setSACoefficient(0, 0, MantExpComplex.create(1, 0));

        for(int i = 1; i < numCoefficients; i++){
            setSACoefficient(i, 0, MantExpComplex.create());
        }

        long oomDiff = TaskRender.SERIES_APPROXIMATION_OOM_DIFFERENCE;
        int SAMaxSkipIter = TaskRender.SERIES_APPROXIMATION_MAX_SKIP_ITER;

        int length = deepZoom ? referenceDeep.length() : reference.length();

        int lastIndex = numCoefficients - 1;

        int i;
        for(i = 1; i < length; i++) {

            if(i - 1 > referenceData.MaxRefIteration) {
                SAskippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                return;
            }

            MantExpComplex ref = null;

            if(deepZoom) {
                ref = getArrayDeepValue(referenceDeep, i - 1);
            }
            else {
                ref = MantExpComplex.create(getArrayValue(reference, i - 1));
            }

            MantExpComplex fourRefCubed = ref.cube().times4_mutable();

            MantExpComplex refSquared = ref.square();

            int new_i = i;
            int old_i = (i - 1);

            MantExpComplex coef0i = null;
            MantExpComplex coef1i = null;
            MantExpComplex coef2i = null;
            MantExpComplex coef3i = null;
            MantExpComplex coef4i = null;

            MantExpComplex anSquared = null;
            MantExpComplex sixRefSquared = null;
            MantExpComplex twelveRefSquared = null;
            MantExpComplex fourAnCube = null;
            MantExpComplex bnSquared = null;
            MantExpComplex twelveRef = null;
            MantExpComplex twelveRefSquaredAn = null;

            if (numCoefficients >= 1) {
                //An+1 = P * A * (X^(P-1)) + 1
                coef0i = getSACoefficient(0, old_i);
                MantExpComplex temp = coef0i.times(fourRefCubed).plus_mutable(MantExp.ONE); //4*Z^3*a_1 + 1
                temp.Normalize();
                magCoeff[0] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[1]);
                setSACoefficient(0, new_i, temp);
            }
            if (numCoefficients >= 2) {
                //Bn+1 = P * B * (X^(P-1)) + ((P*(P-1))/2) * (A^2) * (X^(P-2))
                coef1i = getSACoefficient(1, old_i);
                anSquared = coef0i.square();
                sixRefSquared = refSquared.times(MantExp.SIX);
                MantExpComplex temp = coef1i.times(fourRefCubed)
                        .plus_mutable(anSquared.times(sixRefSquared)); //6*Z^2*a_1^2 + 4*Z^3*a_2
                temp.Normalize();
                magCoeff[1] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[2]);
                setSACoefficient(1, new_i, temp);
            }
            if (numCoefficients >= 3) {
                //Cn+1 = P * C * (X^(P-1)) + (P*(P-1)) * A * B * (X^(P-2)) + ((P*(P-1)*(P-2))/6) * (A^3) * (X^(P-3))
                coef2i = getSACoefficient(2, old_i);
                twelveRefSquared = refSquared.times(MantExp.TWELVE);
                twelveRefSquaredAn = twelveRefSquared.times(coef0i);
                fourAnCube = coef0i.cube().times4_mutable();
                MantExpComplex temp = coef2i.times(fourRefCubed)
                        .plus_mutable(twelveRefSquaredAn.times(coef1i))
                        .plus_mutable(fourAnCube.times(ref)); //4*Z*a_1^3 + 12*Z^2*a_1*a_2 + 4*Z^3*a_3
                temp.Normalize();
                magCoeff[2] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[3]);
                setSACoefficient(2, new_i, temp);
            }

            if (numCoefficients >= 4) {
                coef3i = getSACoefficient(3, old_i);
                bnSquared = coef1i.square();
                twelveRef = ref.times(MantExp.TWELVE);
                MantExpComplex temp = coef3i.times(fourRefCubed)
                        .plus_mutable(coef0i.fourth())
                        .plus_mutable(anSquared.times(twelveRef).times_mutable(coef1i))
                        .plus_mutable(sixRefSquared.times(bnSquared))
                        .plus_mutable(twelveRefSquaredAn.times(coef2i)); //a_1^4 + 12*Z*a_1^2*a_2 + 6*Z^2*a_2^2 + 12*Z^2*a_1*a_3 + 4*Z^3*a_4
                temp.Normalize();
                magCoeff[3] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[4]);
                setSACoefficient(3, new_i, temp);
            }

            if (numCoefficients >= 5) {
                coef4i = getSACoefficient(4, old_i);
                MantExpComplex temp = coef4i.times(fourRefCubed)
                        .plus_mutable(fourAnCube.times(coef1i))
                        .plus_mutable(bnSquared.times(coef0i).times_mutable(twelveRef))
                        .plus_mutable(anSquared.times(coef2i).times_mutable(twelveRef))
                        .plus_mutable(coef1i.times(coef2i).times_mutable(twelveRefSquared))
                        .plus_mutable(twelveRefSquaredAn.times(coef3i)); //4*a_1^3*a_2 + 12*Z*a_1*a_2^2 + 12*Z*a_1^2*a_3 + 12*Z^2*a_2*a_3 + 12*Z^2*a_1*a_4 + 4*Z^3*a_5
                temp.Normalize();
                magCoeff[4] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[5]);
                setSACoefficient(4, new_i, temp);
            }

            //Check to see if the approximation is no longer valid. The validity is checked if an arbitrary point we approximated differs from the point it should be by too much. That is the tolerancy which scales with the depth.
            //if (coefficients[numCoefficients - 2][new_i].times(tempLimit).norm_squared().compareTo(coefficients[numCoefficients - 1][new_i].times(DeltaSub0ToThe[numCoefficients]).norm_squared()) < 0) {
            //if(coefficients[numCoefficients - 2][new_i].norm_squared().divide(coefficients[numCoefficients - 1][new_i].norm_squared()).compareTo(tempLimit2) < 0) {
            if(i > 1 && (i >= SAMaxSkipIter || isLastTermNotNegligible(magCoeff, oomDiff, lastIndex))) {
                //|Bn+1 * d^2 * tolerance| < |Cn+1 * d^3|
                //When we're breaking here, it means that we've found a point where the approximation no longer works. Returning that would create a messed up image. We should move a little further back to get an approximation that is good.
                SAskippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
                return;
            }

            if(progress != null && i % 1000 == 0) {
                progress.setValue(i);
                progress.setString(SA_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (i) / progress.getMaximum() * 100)) + "%");
            }

        }

        i = length - 1;
        SAskippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
    }

    @Override
    public void function(GenericComplex[] complex) {
        if(not_burning_ship) {
            complex[0] = complex[0].fourth().plus_mutable(complex[1]);
        }
        else {
            complex[0] = complex[0].abs_mutable().fourth().plus_mutable(complex[1]);
        }
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (burning_ship ? "-Burning Ship" : "") + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public boolean supportsSeriesApproximation() {
        return !burning_ship && !isJulia;
    }

    @Override
    public boolean supportsBignum() { return true;}

    @Override
    public boolean supportsBigIntnum() {
        return true;
    }

    @Override
    public boolean supportsBilinearApproximation() {
        return !burning_ship && !isJulia;
    }

    @Override
    public boolean supportsBilinearApproximation3() {
        return !burning_ship && !isJulia;
    }

    @Override
    public boolean supportsPeriod() {
        return !burning_ship && !isJulia && !(TaskRender.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation());
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean supportsMpirBignum() { return true;}

    @Override
    public boolean supportsReferenceCompression() {
        return true;
    }

    @Override
    public Complex function(Complex z, Complex c) {
        if(not_burning_ship) {
            return z.fourth_mutable().plus_mutable(c);
        }
        else {
            return z.abs_mutable().fourth_mutable().plus_mutable(c);
        }
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        if(not_burning_ship) {
            return z.fourth_mutable().plus_mutable(c);
        }
        else {
            return z.abs_mutable().fourth_mutable().plus_mutable(c);
        }
    }

    @Override
    public double getPower() {
        return 4;
    }

}
