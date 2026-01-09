
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.core.Complex;
import fractalzoomer.core.NumericLibrary;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.approximation.series_approximation.MandelbrotCubedApproximation;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.*;
import fractalzoomer.core.reference.DoubleReference;
import fractalzoomer.core.reference.ReferenceData;
import fractalzoomer.core.reference.ReferenceDeepData;
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
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;

import static fractalzoomer.main.Constants.BIGNUM_BIGINT;
import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;

/**
 *
 * @author hrkalona
 */
public class MandelbrotCubed extends Julia {

    private MandelVariation type;
    private MandelVariation type2;

    private boolean not_burning_ship;

    public MandelbrotCubed(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
    }

    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

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

    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

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
    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

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

    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

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
        complex[0].cube_mutable().plus_mutable(complex[1]);
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
        if(referenceOrbit.DetectedPeriod == 0) {
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
            progress.setMaximum(max_iterations - initIterations);
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

        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared;

        if(iterations == 0) {
            //DetectedAtomPeriod = 0;
            referenceOrbit.DetectedPeriod = 0;
        }

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

        Location loc = new Location();

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

        boolean isSeriesInUse = TaskRender.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation();
        boolean isBLAInUse = TaskRender.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation();
        boolean isBLA3InUse = TaskRender.APPROXIMATION_ALGORITHM == 5 && supportsBilinearApproximation3();
        referenceOrbit.RefType = getRefType();

        boolean usesCircleBail = usesCircleBail();
        boolean preCalcNormData = usesCircleBail;

        NormComponents normData = null;

        boolean isMpfrComplex = z instanceof MpfrBigNumComplex;
        boolean isMpirComplex = z instanceof MpirBigNumComplex;

        Complex dzdc = null;
        MantExpComplex mdzdc = null;

        MantExp mradius = null;
        double radius = 0;

        Complex period_dzdc = null;
        MantExpComplex period_mdzdc = null;

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

        Complex cz = null;
        MantExpComplex mcz = null;
        MantExp temp;

        if(useCompressedRef) {
            initializeCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, needsRefSubCp, precalIndexes, z, c, null, start);
        }

        calculatedReferenceIterations = 0;

        MantExpComplex tempmcz = null;

        for (; iterations < max_ref_iterations; iterations++, calculatedReferenceIterations++) {

            if(deepZoom) {
                mcz = loc.getMantExpComplex(z);
                tempmcz = setReferenceDeepValue(referenceDeep, iterations, mcz);
                //ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? mcz.toComplex() : z.toComplex();
                cz = setReferenceValue(reference, iterations, cz);
            }

            mcz = tempmcz;

            if(stopReferenceCalculationOnDetectedPeriod && referenceOrbit.DetectedPeriod != 0) {
                break;
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
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
                        mdzdc = mcz.square().times_mutable(MantExp.THREE).times_mutable(mdzdc).plus_mutable(MantExp.ONE);
                        mdzdc.Normalize();
                    } else {
                        dzdc = cz.square().times_mutable(3).times_mutable(dzdc).plus_mutable(1);
                    }
                }

                if(preCalcNormData) {
                    if (burning_ship) {
                        z = z.abs_mutable().cubeFast_mutable(normData).plus_mutable(c);
                    } else {
                        z = z.cubeFast_mutable(normData).plus_mutable(c);
                    }
                }
                else {
                    if(isMpfrComplex) {
                        if (burning_ship) {
                            z = z.abs_mutable().cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(c);
                        }
                        else {
                            z = z.cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(c);
                        }
                    }
                    else if(isMpirComplex) {
                        if (burning_ship) {
                            z = z.abs_mutable().cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(c);
                        }
                        else {
                            z = z.cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(c);
                        }
                    }
                    else {
                        if (burning_ship) {
                            z = z.abs_mutable().cube().plus_mutable(c);
                        } else {
                            z = z.cube().plus_mutable(c);
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

        referenceOrbit.lastZValue = z;
        referenceOrbit.c = c;
        referenceOrbit.secondTolastZValue = !usesCircleBail ? zold : null;
        referenceOrbit.thirdTolastZValue = !usesCircleBail ? zold2 : null;
        referenceOrbit.dzdc = !deepZoom ? dzdc : null;
        referenceOrbit.mdzdc = deepZoom ? mdzdc : null;
        referenceOrbit.period_dzdc = !deepZoom ? period_dzdc : null;
        referenceOrbit.period_mdzdc = deepZoom ? period_mdzdc : null;

        referenceOrbit.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            finalizeCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, needsRefSubCp, precalIndexes);
        }

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
    protected GenericComplex referenceFunction(GenericComplex z, GenericComplex c, NormComponents normData, GenericComplex[] initialPrecal, GenericComplex[] precalc) {
        if(normData != null) {
            if (burning_ship) {
                z = z.abs_mutable().cubeFast_mutable(normData).plus_mutable(c);
            } else {
                z = z.cubeFast_mutable(normData).plus_mutable(c);
            }
        }
        else {
            if(z instanceof MpfrBigNumComplex) {
                if (burning_ship) {
                    z = z.abs_mutable().cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(c);
                } else {
                    z = z.cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(c);
                }
            }
            else if(z instanceof MpirBigNumComplex) {
                if (burning_ship) {
                    z = z.abs_mutable().cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(c);
                } else {
                    z = z.cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(c);
                }
            }
            else {
                if (burning_ship) {
                    z = z.abs_mutable().cube().plus_mutable(c);
                } else {
                    z = z.cube().plus_mutable(c);
                }
            }
        }

        return z;
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex X = getReferenceValue(reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(3).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(3).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube())
//                    .plus_mutable(DeltaSub0);
            return DeltaSubN.times(X.times(3).times_mutable(DeltaSubN.plus(X)).plus_mutable(DeltaSubN.square())).plus_mutable(DeltaSub0);
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
            double ab;

            double Dnr = Complex.DiffAbs(r, a);

            ab = r + a;
            Dnr = (r2 - 3 * i2) * Dnr + (2 * ar + a2 - 6 * ib - 3 * b2)* Math.abs(ab);

            double Dni = Complex.DiffAbs(i, b);

            ab = i + b;
            Dni = (3 * r2 - i2) * Dni + (6 * ar + 3 * a2 - 2 * ib - b2) * Math.abs(ab);

            return new Complex(Dnr, Dni).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex X = getReferenceDeepValue(referenceDeep, RefIteration);

        if(not_burning_ship) {
             /*return DeltaSubN.times(MantExp.THREE).times_mutable(X.square())
                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.THREE).times_mutable(X))
                    .plus_mutable(DeltaSubN.cube())
                    .plus_mutable(DeltaSub0);*/
            return DeltaSubN.times(X.times(MantExp.THREE).times_mutable(DeltaSubN.plus(X)).plus_mutable(DeltaSubN.square())).plus_mutable(DeltaSub0);
        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.multiply(r);
            MantExp i2 = i.multiply(i);
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab;

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);

            ab = r.add(a);

            Dnr = (r2.subtract(MantExp.THREE.multiply(i2))).multiply_mutable(Dnr)
                    .add_mutable((ar.multiply2().add_mutable(a2).subtract_mutable(MantExp.SIX.multiply(ib)).subtract_mutable(MantExp.THREE.multiply(b2))).multiply_mutable(ab.abs_mutable()));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);

            ab = i.add(b);

            Dni = (MantExp.THREE.multiply(r2).subtract(i2)).multiply_mutable(Dni)
                    .add_mutable((MantExp.SIX.multiply(ar).add_mutable(MantExp.THREE.multiply(a2)).subtract_mutable(ib.multiply2()).subtract_mutable(b2)).multiply_mutable(ab.abs_mutable()));

            return MantExpComplex.create(Dnr, Dni).plus_mutable(DeltaSub0);
        }

    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex X = getReferenceValue(reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(3).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(3).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return DeltaSubN.times(X.times(3).times_mutable(DeltaSubN.plus(X)).plus_mutable(DeltaSubN.square()));
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
            double ab;

            double Dnr = Complex.DiffAbs(r, a);

            ab = r + a;
            Dnr = (r2 - 3 * i2) * Dnr + (2 * ar + a2 - 6 * ib - 3 * b2)* Math.abs(ab);

            double Dni = Complex.DiffAbs(i, b);

            ab = i + b;
            Dni = (3 * r2 - i2) * Dni + (6 * ar + 3 * a2 - 2 * ib - b2) * Math.abs(ab);

            return new Complex(Dnr, Dni);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex X = getReferenceDeepValue(referenceDeep, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(MantExp.THREE).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.THREE).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return DeltaSubN.times(X.times(MantExp.THREE).times_mutable(DeltaSubN.plus(X)).plus_mutable(DeltaSubN.square()));
        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.multiply(r);
            MantExp i2 = i.multiply(i);
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab;

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);

            ab = r.add(a);

            Dnr = (r2.subtract(MantExp.THREE.multiply(i2))).multiply_mutable(Dnr)
                    .add_mutable((ar.multiply2().add_mutable(a2).subtract_mutable(MantExp.SIX.multiply(ib)).subtract_mutable(MantExp.THREE.multiply(b2))).multiply_mutable(ab.abs_mutable()));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);

            ab = i.add(b);

            Dni = (MantExp.THREE.multiply(r2).subtract(i2)).multiply_mutable(Dni)
                    .add_mutable((MantExp.SIX.multiply(ar).add_mutable(MantExp.THREE.multiply(a2)).subtract_mutable(ib.multiply2()).subtract_mutable(b2)).multiply_mutable(ab.abs_mutable()));

            return MantExpComplex.create(Dnr, Dni);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, ReferenceData data, int RefIteration) {
        Complex X = getReferenceValue(data.Reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(3).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(3).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return DeltaSubN.times(X.times(3).times_mutable(DeltaSubN.plus(X)).plus_mutable(DeltaSubN.square()));
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
            double ab;

            double Dnr = Complex.DiffAbs(r, a);

            ab = r + a;
            Dnr = (r2 - 3 * i2) * Dnr + (2 * ar + a2 - 6 * ib - 3 * b2)* Math.abs(ab);

            double Dni = Complex.DiffAbs(i, b);

            ab = i + b;
            Dni = (3 * r2 - i2) * Dni + (6 * ar + 3 * a2 - 2 * ib - b2) * Math.abs(ab);

            return new Complex(Dnr, Dni);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, ReferenceDeepData data, int RefIteration) {
        MantExpComplex X = getReferenceDeepValue(data.Reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(MantExp.THREE).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.THREE).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return DeltaSubN.times(X.times(MantExp.THREE).times_mutable(DeltaSubN.plus(X)).plus_mutable(DeltaSubN.square()));
        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.multiply(r);
            MantExp i2 = i.multiply(i);
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab;

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);

            ab = r.add(a);

            Dnr = (r2.subtract(MantExp.THREE.multiply(i2))).multiply_mutable(Dnr)
                    .add_mutable((ar.multiply2().add_mutable(a2).subtract_mutable(MantExp.SIX.multiply(ib)).subtract_mutable(MantExp.THREE.multiply(b2))).multiply_mutable(ab.abs_mutable()));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);

            ab = i.add(b);

            Dni = (MantExp.THREE.multiply(r2).subtract(i2)).multiply_mutable(Dni)
                    .add_mutable((MantExp.SIX.multiply(ar).add_mutable(MantExp.THREE.multiply(a2)).subtract_mutable(ib.multiply2()).subtract_mutable(b2)).multiply_mutable(ab.abs_mutable()));

            return MantExpComplex.create(Dnr, Dni);
        }
    }

    @Override
    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc, JProgressBar progress) {
        sa = new MandelbrotCubedApproximation();
        sa.calculateApproximation(dsize, deepZoom, loc, referenceOrbit, progress, this);
        SAskippedIterations = sa.SAskippedIterations;
    }

    @Override
    public void function(GenericComplex[] complex) {
        if(complex[0] instanceof MpfrBigNumComplex) {
            if(not_burning_ship) {
                complex[0] = complex[0].cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(complex[1]);
            }
            else {
                complex[0] = complex[0].abs_mutable().cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(complex[1]);
            }
        }
        else if(complex[0] instanceof MpirBigNumComplex) {
            if(not_burning_ship) {
                complex[0] = complex[0].cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(complex[1]);
            }
            else {
                complex[0] = complex[0].abs_mutable().cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(complex[1]);
            }
        }
        else {
            if (not_burning_ship) {
                complex[0] = complex[0].cube().plus_mutable(complex[1]);
            } else {
                complex[0] = complex[0].abs_mutable().cube().plus_mutable(complex[1]);
            }
        }
    }

    @Override
    public double getBlaR(Complex Z, double epsilon) {
        return Z.hypot() * epsilon;
    }

    @Override
    public MantExp getBlaR(MantExpComplex Z, MantExp epsilon) {
        return Z.hypot().multiply_mutable(epsilon);
    }

    @Override
    public Complex getBlaA(Complex Z) {
        return Z.square().times_mutable(3);
    }

    @Override
    public MantExpComplex getBlaA(MantExpComplex Z) {
        return Z.square().times_mutable(MantExp.THREE);
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
    public boolean supportsBilinearApproximation3() {
        return !burning_ship && !isJulia;
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (burning_ship ? "-Burning Ship" : "") + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public boolean supportsBignum() { return true;}

    @Override
    public boolean supportsBigIntnum() {
        return true;
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
            return z.cube_mutable().plus_mutable(c);
        }
        else {
            return z.abs_mutable().cube_mutable().plus_mutable(c);
        }
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        if(not_burning_ship) {
            return z.cube_mutable().plus_mutable(c);
        }
        else {
            return z.abs_mutable().cube_mutable().plus_mutable(c);
        }
    }

    @Override
    public double getPower() {
        return 3;
    }

    @Override
    public boolean supportsReferenceSavingOrLoading() {
        return true;
    }

}
