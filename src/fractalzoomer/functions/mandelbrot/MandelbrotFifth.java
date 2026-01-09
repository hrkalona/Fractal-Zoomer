
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.core.Complex;
import fractalzoomer.core.NumericLibrary;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.approximation.series_approximation.MandelbrotFifthApproximation;
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

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class MandelbrotFifth extends Julia {

    private MandelVariation type;
    private MandelVariation type2;

    private boolean not_burning_ship;

    public MandelbrotFifth(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
    }

    public MandelbrotFifth(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

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

    public MandelbrotFifth(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

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
    public MandelbrotFifth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

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

    public MandelbrotFifth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

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
        complex[0].fifth_mutable().plus_mutable(complex[1]);
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
        else if(bigNumLib == BIGNUM_DOUBLE) {
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

        Complex dzdc = null;
        MantExpComplex mdzdc = null;
        MantExp temp;

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
                        mdzdc = mcz.fourth().times_mutable(MantExp.FIVE).times_mutable(mdzdc).plus_mutable(MantExp.ONE);
                        mdzdc.Normalize();
                    } else {
                        dzdc = cz.fourth().times_mutable(5).times_mutable(dzdc).plus_mutable(1);
                    }
                }

                if(preCalcNormData) {
                    if (burning_ship) {
                        z = z.abs_mutable().fifthFast_mutable(normData).plus_mutable(c);
                    } else {
                        z = z.fifthFast_mutable(normData).plus_mutable(c);
                    }
                }
                else {
                    if (burning_ship) {
                        z = z.abs_mutable().fifth().plus_mutable(c);
                    } else {
                        z = z.fifth().plus_mutable(c);
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
                z = z.abs_mutable().fifthFast_mutable(normData).plus_mutable(c);
            } else {
                z = z.fifthFast_mutable(normData).plus_mutable(c);
            }
        }
        else {
            if (burning_ship) {
                z = z.abs_mutable().fifth().plus_mutable(c);
            } else {
                z = z.fifth().plus_mutable(c);
            }
        }

        return z;
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex X = getReferenceValue(reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(5).times_mutable(X.fourth())
//                    .plus_mutable(DeltaSubN.square().times_mutable(10).times_mutable(X.cube()))
//                    .plus_mutable(DeltaSubN.cube().times_mutable(10).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.fourth().times_mutable(5).times_mutable(X))
//                    .plus_mutable(DeltaSubN.fifth())
//                    .plus_mutable(DeltaSub0);
            return X.fourth().times_mutable(5).plus_mutable(X.cube().times_mutable(10).plus_mutable(X.square().times_mutable(10).plus_mutable(X.times(5).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
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
            double ra = r*a;
            double ib = i*b;

            double b2b2 = b2 * b2;
            double r2r2 = r2 * r2;
            double r2i2 = r2 * i2;
            double i2i2 = i2 * i2;
            double r2ra = r2 * ra;
            double r2a2 = r2 * a2;
            double raa2 = ra * a2;
            double a2a2 = a2 * a2;
            double i2ib = i2 * ib;
            double i2b2 = i2 * b2;
            double ibb2 = ib * b2;

            double temp = 20 * (r2 * ib + ra * i2 + ra*b2 + a2*ib)
                    + 10 * (r2 * b2 + a2*i2 + a2*b2)
                    + 40 * ra * ib;

            double Dnr = Complex.DiffAbs(r, a);
            Dnr = Dnr * (r2r2 - 10 * r2i2 + 5 * i2i2) + Math.abs(r + a) * (4 * (r2ra + raa2) + 6 * r2a2 + a2a2 - temp
                    + 20 * (i2ib + ibb2) + 30 * i2b2 + 5 *b2b2);

            double Dni = Complex.DiffAbs(i, b);
            Dni = Dni * (5 * r2r2 - 10 * r2i2 + i2i2) +  Math.abs(i + b) * (20 * (r2ra + raa2) + 30 * r2a2 + 5 * a2a2 - temp
                    + 4 * (i2ib + ibb2) + 6 * i2b2 + b2b2);

            return new Complex(Dnr, Dni).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex X = getReferenceDeepValue(referenceDeep, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(MantExp.FIVE).times_mutable(X.fourth())
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.TEN).times_mutable(X.cube()))
//                    .plus_mutable(DeltaSubN.cube().times_mutable(MantExp.TEN).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.fourth().times_mutable(MantExp.FIVE).times_mutable(X))
//                    .plus_mutable(DeltaSubN.fifth())
//                    .plus_mutable(DeltaSub0);
            return X.fourth().times_mutable(MantExp.FIVE).plus_mutable(X.cube().times_mutable(MantExp.TEN).plus_mutable(X.square().times_mutable(MantExp.TEN).plus_mutable(X.times(MantExp.FIVE).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
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
            MantExp ra = r.multiply(a);
            MantExp ib = i.multiply(b);

            MantExp b2b2 = b2.multiply(b2);
            MantExp r2r2 = r2.multiply(r2);
            MantExp r2i2 = r2.multiply(i2);
            MantExp i2i2 = i2.multiply(i2);
            MantExp r2ra = r2.multiply(ra);
            MantExp r2a2 = r2.multiply(a2);
            MantExp raa2 = ra.multiply(a2);
            MantExp a2a2 = a2.multiply(a2);
            MantExp i2ib = i2.multiply(ib);
            MantExp i2b2 = i2.multiply(b2);
            MantExp ibb2 = ib.multiply(b2);

            MantExp temp = r2.multiply(ib).add_mutable(ra.multiply(i2)).add_mutable(ra.multiply(b2)).add_mutable(a2.multiply(ib)).multiply_mutable(MantExp.TWENTY)
                    .add_mutable(r2.multiply(b2).add_mutable(a2.multiply(i2)).add_mutable(a2.multiply(b2)).multiply_mutable(MantExp.TEN))
                    .add_mutable(ra.multiply(ib).multiply_mutable(MantExp.FOURTY));

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);


            Dnr = Dnr.multiply(r2r2.subtract(r2i2.multiply(MantExp.TEN)).add_mutable(i2i2.multiply(MantExp.FIVE)))
                    .add_mutable(r.add(a).abs_mutable().multiply_mutable(r2ra.add(raa2).multiply4_mutable()
                            .add_mutable(r2a2.multiply(MantExp.SIX))
                            .add_mutable(a2a2)
                            .subtract_mutable(temp)
                            .add_mutable(i2ib.add(ibb2).multiply_mutable(MantExp.TWENTY))
                            .add_mutable(i2b2.multiply(MantExp.THIRTY))
                            .add_mutable(b2b2.multiply(MantExp.FIVE))
                    ));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);


            Dni = Dni.multiply(r2r2.multiply(MantExp.FIVE).subtract_mutable(r2i2.multiply(MantExp.TEN)).add_mutable(i2i2))
                    .add_mutable(i.add(b).abs_mutable().multiply_mutable(r2ra.add(raa2).multiply_mutable(MantExp.TWENTY)
                            .add_mutable(r2a2.multiply(MantExp.THIRTY))
                            .add_mutable(a2a2.multiply(MantExp.FIVE))
                            .subtract_mutable(temp)
                            .add_mutable(i2ib.add(ibb2).multiply4_mutable())
                            .add_mutable(i2b2.multiply(MantExp.SIX))
                            .add_mutable(b2b2)
                    ));

            return MantExpComplex.create(Dnr, Dni).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex X = getReferenceValue(reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(5).times_mutable(X.fourth())
//                    .plus_mutable(DeltaSubN.square().times_mutable(10).times_mutable(X.cube()))
//                    .plus_mutable(DeltaSubN.cube().times_mutable(10).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.fourth().times_mutable(5).times_mutable(X))
//                    .plus_mutable(DeltaSubN.fifth());
            return X.fourth().times_mutable(5).plus_mutable(X.cube().times_mutable(10).plus_mutable(X.square().times_mutable(10).plus_mutable(X.times(5).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
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
            double ra = r*a;
            double ib = i*b;

            double b2b2 = b2 * b2;
            double r2r2 = r2 * r2;
            double r2i2 = r2 * i2;
            double i2i2 = i2 * i2;
            double r2ra = r2 * ra;
            double r2a2 = r2 * a2;
            double raa2 = ra * a2;
            double a2a2 = a2 * a2;
            double i2ib = i2 * ib;
            double i2b2 = i2 * b2;
            double ibb2 = ib * b2;

            double temp = 20 * (r2 * ib + ra * i2 + ra*b2 + a2*ib)
                    + 10 * (r2 * b2 + a2*i2 + a2*b2)
                    + 40 * ra * ib;

            double Dnr = Complex.DiffAbs(r, a);
            Dnr = Dnr * (r2r2 - 10 * r2i2 + 5 * i2i2) + Math.abs(r + a) * (4 * (r2ra + raa2) + 6 * r2a2 + a2a2 - temp
                    + 20 * (i2ib + ibb2) + 30 * i2b2 + 5 *b2b2);

            double Dni = Complex.DiffAbs(i, b);
            Dni = Dni * (5 * r2r2 - 10 * r2i2 + i2i2) +  Math.abs(i + b) * (20 * (r2ra + raa2) + 30 * r2a2 + 5 * a2a2 - temp
                    + 4 * (i2ib + ibb2) + 6 * i2b2 + b2b2);

            return new Complex(Dnr, Dni);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex X = getReferenceDeepValue(referenceDeep, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(MantExp.FIVE).times_mutable(X.fourth())
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.TEN).times_mutable(X.cube()))
//                    .plus_mutable(DeltaSubN.cube().times_mutable(MantExp.TEN).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.fourth().times_mutable(MantExp.FIVE).times_mutable(X))
//                    .plus_mutable(DeltaSubN.fifth());
            return X.fourth().times_mutable(MantExp.FIVE).plus_mutable(X.cube().times_mutable(MantExp.TEN).plus_mutable(X.square().times_mutable(MantExp.TEN).plus_mutable(X.times(MantExp.FIVE).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
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
            MantExp ra = r.multiply(a);
            MantExp ib = i.multiply(b);

            MantExp b2b2 = b2.multiply(b2);
            MantExp r2r2 = r2.multiply(r2);
            MantExp r2i2 = r2.multiply(i2);
            MantExp i2i2 = i2.multiply(i2);
            MantExp r2ra = r2.multiply(ra);
            MantExp r2a2 = r2.multiply(a2);
            MantExp raa2 = ra.multiply(a2);
            MantExp a2a2 = a2.multiply(a2);
            MantExp i2ib = i2.multiply(ib);
            MantExp i2b2 = i2.multiply(b2);
            MantExp ibb2 = ib.multiply(b2);


            MantExp temp = r2.multiply(ib).add_mutable(ra.multiply(i2)).add_mutable(ra.multiply(b2)).add_mutable(a2.multiply(ib)).multiply_mutable(MantExp.TWENTY)
                    .add_mutable(r2.multiply(b2).add_mutable(a2.multiply(i2)).add_mutable(a2.multiply(b2)).multiply_mutable(MantExp.TEN))
                    .add_mutable(ra.multiply(ib).multiply_mutable(MantExp.FOURTY));

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);


            Dnr = Dnr.multiply(r2r2.subtract(r2i2.multiply(MantExp.TEN)).add_mutable(i2i2.multiply(MantExp.FIVE)))
                    .add_mutable(r.add(a).abs_mutable().multiply_mutable(r2ra.add(raa2).multiply4_mutable()
                            .add_mutable(r2a2.multiply(MantExp.SIX))
                            .add_mutable(a2a2)
                            .subtract_mutable(temp)
                            .add_mutable(i2ib.add(ibb2).multiply_mutable(MantExp.TWENTY))
                            .add_mutable(i2b2.multiply(MantExp.THIRTY))
                            .add_mutable(b2b2.multiply(MantExp.FIVE))
                    ));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);


            Dni = Dni.multiply(r2r2.multiply(MantExp.FIVE).subtract_mutable(r2i2.multiply(MantExp.TEN)).add_mutable(i2i2))
                    .add_mutable(i.add(b).abs_mutable().multiply_mutable(r2ra.add(raa2).multiply_mutable(MantExp.TWENTY)
                            .add_mutable(r2a2.multiply(MantExp.THIRTY))
                            .add_mutable(a2a2.multiply(MantExp.FIVE))
                            .subtract_mutable(temp)
                            .add_mutable(i2ib.add(ibb2).multiply4_mutable())
                            .add_mutable(i2b2.multiply(MantExp.SIX))
                            .add_mutable(b2b2)
                    ));

            return MantExpComplex.create(Dnr, Dni);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, ReferenceData data, int RefIteration) {
        Complex X = getReferenceValue(data.Reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(5).times_mutable(X.fourth())
//                    .plus_mutable(DeltaSubN.square().times_mutable(10).times_mutable(X.cube()))
//                    .plus_mutable(DeltaSubN.cube().times_mutable(10).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.fourth().times_mutable(5).times_mutable(X))
//                    .plus_mutable(DeltaSubN.fifth());
            return X.fourth().times_mutable(5).plus_mutable(X.cube().times_mutable(10).plus_mutable(X.square().times_mutable(10).plus_mutable(X.times(5).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
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
            double ra = r*a;
            double ib = i*b;

            double b2b2 = b2 * b2;
            double r2r2 = r2 * r2;
            double r2i2 = r2 * i2;
            double i2i2 = i2 * i2;
            double r2ra = r2 * ra;
            double r2a2 = r2 * a2;
            double raa2 = ra * a2;
            double a2a2 = a2 * a2;
            double i2ib = i2 * ib;
            double i2b2 = i2 * b2;
            double ibb2 = ib * b2;

            double temp = 20 * (r2 * ib + ra * i2 + ra*b2 + a2*ib)
                    + 10 * (r2 * b2 + a2*i2 + a2*b2)
                    + 40 * ra * ib;

            double Dnr = Complex.DiffAbs(r, a);
            Dnr = Dnr * (r2r2 - 10 * r2i2 + 5 * i2i2) + Math.abs(r + a) * (4 * (r2ra + raa2) + 6 * r2a2 + a2a2 - temp
                    + 20 * (i2ib + ibb2) + 30 * i2b2 + 5 *b2b2);

            double Dni = Complex.DiffAbs(i, b);
            Dni = Dni * (5 * r2r2 - 10 * r2i2 + i2i2) +  Math.abs(i + b) * (20 * (r2ra + raa2) + 30 * r2a2 + 5 * a2a2 - temp
                    + 4 * (i2ib + ibb2) + 6 * i2b2 + b2b2);

            return new Complex(Dnr, Dni);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, ReferenceDeepData data, int RefIteration) {
        MantExpComplex X = getReferenceDeepValue(data.Reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(MantExp.FIVE).times_mutable(X.fourth())
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.TEN).times_mutable(X.cube()))
//                    .plus_mutable(DeltaSubN.cube().times_mutable(MantExp.TEN).times_mutable(X.square()))
//                    .plus_mutable(DeltaSubN.fourth().times_mutable(MantExp.FIVE).times_mutable(X))
//                    .plus_mutable(DeltaSubN.fifth());
            return X.fourth().times_mutable(MantExp.FIVE).plus_mutable(X.cube().times_mutable(MantExp.TEN).plus_mutable(X.square().times_mutable(MantExp.TEN).plus_mutable(X.times(MantExp.FIVE).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
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
            MantExp ra = r.multiply(a);
            MantExp ib = i.multiply(b);

            MantExp b2b2 = b2.multiply(b2);
            MantExp r2r2 = r2.multiply(r2);
            MantExp r2i2 = r2.multiply(i2);
            MantExp i2i2 = i2.multiply(i2);
            MantExp r2ra = r2.multiply(ra);
            MantExp r2a2 = r2.multiply(a2);
            MantExp raa2 = ra.multiply(a2);
            MantExp a2a2 = a2.multiply(a2);
            MantExp i2ib = i2.multiply(ib);
            MantExp i2b2 = i2.multiply(b2);
            MantExp ibb2 = ib.multiply(b2);


            MantExp temp = r2.multiply(ib).add_mutable(ra.multiply(i2)).add_mutable(ra.multiply(b2)).add_mutable(a2.multiply(ib)).multiply_mutable(MantExp.TWENTY)
                    .add_mutable(r2.multiply(b2).add_mutable(a2.multiply(i2)).add_mutable(a2.multiply(b2)).multiply_mutable(MantExp.TEN))
                    .add_mutable(ra.multiply(ib).multiply_mutable(MantExp.FOURTY));

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);


            Dnr = Dnr.multiply(r2r2.subtract(r2i2.multiply(MantExp.TEN)).add_mutable(i2i2.multiply(MantExp.FIVE)))
                    .add_mutable(r.add(a).abs_mutable().multiply_mutable(r2ra.add(raa2).multiply4_mutable()
                            .add_mutable(r2a2.multiply(MantExp.SIX))
                            .add_mutable(a2a2)
                            .subtract_mutable(temp)
                            .add_mutable(i2ib.add(ibb2).multiply_mutable(MantExp.TWENTY))
                            .add_mutable(i2b2.multiply(MantExp.THIRTY))
                            .add_mutable(b2b2.multiply(MantExp.FIVE))
                    ));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);


            Dni = Dni.multiply(r2r2.multiply(MantExp.FIVE).subtract_mutable(r2i2.multiply(MantExp.TEN)).add_mutable(i2i2))
                    .add_mutable(i.add(b).abs_mutable().multiply_mutable(r2ra.add(raa2).multiply_mutable(MantExp.TWENTY)
                            .add_mutable(r2a2.multiply(MantExp.THIRTY))
                            .add_mutable(a2a2.multiply(MantExp.FIVE))
                            .subtract_mutable(temp)
                            .add_mutable(i2ib.add(ibb2).multiply4_mutable())
                            .add_mutable(i2b2.multiply(MantExp.SIX))
                            .add_mutable(b2b2)
                    ));

            return MantExpComplex.create(Dnr, Dni);
        }
    }

    @Override
    public double getBlaR(Complex Z, double epsilon) {
        return 0.5 * Z.hypot() * epsilon;
    }

    @Override
    public MantExp getBlaR(MantExpComplex Z, MantExp epsilon) {
        return Z.hypot().divide2_mutable().multiply_mutable(epsilon);
    }

    @Override
    public Complex getBlaA(Complex Z) {
        return Z.fourth().times_mutable(5);
    }

    @Override
    public MantExpComplex getBlaA(MantExpComplex Z) {
        return Z.fourth().times_mutable(MantExp.FIVE);
    }

    @Override
    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc, JProgressBar progress) {
        sa = new MandelbrotFifthApproximation();
        sa.calculateApproximation(dsize, deepZoom, loc, referenceOrbit, progress, this);
        SAskippedIterations = sa.SAskippedIterations;
    }

    @Override
    public void function(GenericComplex[] complex) {
        if(not_burning_ship) {
            complex[0] = complex[0].fifth().plus_mutable(complex[1]);
        }
        else {
            complex[0] = complex[0].abs_mutable().fifth().plus_mutable(complex[1]);
        }
    }

    @Override
    public boolean supportsSeriesApproximation() {
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
            return z.fifth_mutable().plus_mutable(c);
        }
        else {
            return z.abs_mutable().fifth_mutable().plus_mutable(c);
        }
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        if(not_burning_ship) {
            return z.fifth_mutable().plus_mutable(c);
        }
        else {
            return z.abs_mutable().fifth_mutable().plus_mutable(c);
        }
    }

    @Override
    public double getPower() {
        return 5;
    }

    @Override
    public boolean supportsReferenceSavingOrLoading() {
        return true;
    }
}
