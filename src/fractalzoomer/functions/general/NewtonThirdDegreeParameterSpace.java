package fractalzoomer.functions.general;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValueWithFactor;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.ExtendedConvergentType;
import fractalzoomer.functions.root_finding_methods.newton.NewtonRootFindingMethod;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.function.Function;

import static fractalzoomer.main.Constants.*;

public class NewtonThirdDegreeParameterSpace extends ExtendedConvergentType {
    private static GenericComplex c4big;
    private static GenericComplex c2big;
    private static GenericComplex csqrbig;
    private static GenericComplex csqr2s3big;
    private static GenericComplex csqrs3big;

    public NewtonThirdDegreeParameterSpace() {
        super();
    }

    public NewtonThirdDegreeParameterSpace(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        defaultInitVal = new DefaultInitialValueWithFactor(1.0 / 3.0);

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
            init_val = defaultInitVal;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        //override some algorithms
        /*switch (out_coloring_algorithm) {
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
                break;
        }*/

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public NewtonThirdDegreeParameterSpace(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        switch (out_coloring_algorithm) {
            case MainWindow.BINARY_DECOMPOSITION:
            case MainWindow.BINARY_DECOMPOSITION2:
                setConvergentBailout(1E-9);
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                setConvergentBailout(1E-7);
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        defaultInitVal = new DefaultInitialValueWithFactor(1.0 / 3.0);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
    }

        //orbit
    public NewtonThirdDegreeParameterSpace(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex > complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        defaultInitVal = new DefaultInitialValueWithFactor(1.0 / 3.0);

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
            init_val = defaultInitVal;
        }

    }

    public NewtonThirdDegreeParameterSpace(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        defaultInitVal = new DefaultInitialValueWithFactor(1.0 / 3.0);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
    }

    @Override
    public void function(Complex[] complex) {
        Complex zsqr = complex[0].square();

        Complex fz = zsqr.times(complex[1].negative()).plus_mutable(complex[1]).sub_mutable(complex[0]).plus_mutable(complex[0].cube());
        Complex dfz = complex[1].times(-2).times_mutable(complex[0]).plus_mutable(zsqr.times(3)).sub_mutable(1);

        NewtonRootFindingMethod.NewtonMethod(complex[0], fz, dfz);
    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }

        return !isJulia || !juliter;
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    protected int[] getNeededPrecalculatedTermsIndexes() {
        if(!isJulia) {
            return new int[] {0, 1, 2, 3, 4, 5, 6, 7};
        }
        return new int[] {0, 1, 2, 3, 4};
    }

    @Override
    protected Function[] getPrecalculatedTermsFunctions(Complex c) {

        Complex c4 = c.times4();
        Complex csqr = c.square();
        Complex csqrs3 = csqr.sub(3);
        Complex csqr2s3 = csqr.times2().sub_mutable(3);
        Complex c2 = c.times2();

        Function<Complex, Complex> f1 = x -> {
            Complex zsqr = x.square();
            return c4.times(x).sub_mutable(zsqr.times(3)).sub_mutable(csqrs3).times_mutable(zsqr).plus_mutable(csqr).sub_mutable(c4.times(x));};

        Function<Complex, Complex> f2 = x -> c.sub(x).times_mutable(x).times_mutable(9).sub_mutable(csqr2s3).times_mutable(x).sub_mutable(c);;

        Function<Complex, Complex> f3 = x -> c2.times(x).sub(x.square().times_mutable(3)).plus_mutable(1);

        Function<Complex, Complex> f4 = x -> {
            Complex zsqr = x.square();
            Complex c4z = c4.times(x);
            return c4z.sub(zsqr.times(3)).times_mutable(3).sub_mutable(csqr2s3.times2()).times_mutable(zsqr).sub_mutable(c4z).sub_mutable(1);
        };

        Function<Complex, Complex> f5 = x -> c.sub(x).times_mutable(x).times(12).sub_mutable(csqrs3.times2()).times_mutable(x).sub_mutable(c4);
        if(!isJulia) {
            Function<Complex, Complex> f6 = x -> c.sub(x).times_mutable(x).plus_mutable(1).times_mutable(x).sub(c);

            Function<Complex, Complex> f7 = x -> c2.times(x).sub(x.square().times_mutable(3).sub_mutable(1)).times_mutable(x);
            Function<Complex, Complex> f8 = x -> {
                Complex zsqr = x.square();
                return zsqr.sub(2).times_mutable(zsqr).plus_mutable(1);
            };

            return new Function[] {f1, f2, f3, f4, f5, f6, f7, f8};
        }
        return new Function[] {f1, f2, f3, f4, f5};
    }

    @Override
    protected Function[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        MantExpComplex c4 = c.times4();
        c4.Normalize();
        MantExpComplex csqr = c.square();
        csqr.Normalize();
        MantExpComplex csqrs3 = csqr.sub(MantExp.THREE);
        csqrs3.Normalize();
        MantExpComplex csqr2s3 = csqr.times2().sub_mutable(MantExp.THREE);
        csqr2s3.Normalize();
        MantExpComplex c2 = c.times2();
        c2.Normalize();

        Function<MantExpComplex, MantExpComplex> f1 = x -> {
            MantExpComplex zsqr = x.square();
            return c4.times(x).sub_mutable(zsqr.times(MantExp.THREE)).sub_mutable(csqrs3).times_mutable(zsqr).plus_mutable(csqr).sub_mutable(c4.times(x));};

        Function<MantExpComplex, MantExpComplex> f2 = x -> c.sub(x).times_mutable(x).times_mutable(MantExp.NINE).sub_mutable(csqr2s3).times_mutable(x).sub_mutable(c);;

        Function<MantExpComplex, MantExpComplex> f3 = x -> c2.times(x).sub(x.square().times_mutable(MantExp.THREE)).plus_mutable(MantExp.ONE);

        Function<MantExpComplex, MantExpComplex> f4 = x -> {
            MantExpComplex zsqr = x.square();
            MantExpComplex c4z = c4.times(x);
            return c4z.sub(zsqr.times(MantExp.THREE)).times_mutable(MantExp.THREE).sub_mutable(csqr2s3.times2()).times_mutable(zsqr).sub_mutable(c4z).sub_mutable(MantExp.ONE);
        };

        Function<MantExpComplex, MantExpComplex> f5 = x -> c.sub(x).times_mutable(x).times(MantExp.TWELVE).sub_mutable(csqrs3.times2()).times_mutable(x).sub_mutable(c4);
        if(!isJulia) {
            Function<MantExpComplex, MantExpComplex> f6 = x -> c.sub(x).times_mutable(x).plus_mutable(MantExp.ONE).times_mutable(x).sub(c);

            Function<MantExpComplex, MantExpComplex> f7 = x -> c2.times(x).sub(x.square().times_mutable(MantExp.THREE).sub_mutable(MantExp.ONE)).times_mutable(x);
            Function<MantExpComplex, MantExpComplex> f8 = x -> {
                MantExpComplex zsqr = x.square();
                return zsqr.sub(MantExp.TWO).times_mutable(zsqr).plus_mutable(MantExp.ONE);
            };

            return new Function[] {f1, f2, f3, f4, f5, f6, f7, f8};
        }
        return new Function[] {f1, f2, f3, f4, f5};
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

        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, false);
        DoubleReference.SHOULD_SAVE_MEMORY = false;
        boolean useCompressedRef = TaskDraw.COMPRESS_REFERENCE_IF_POSSIBLE && supportsReferenceCompression();
        int[] preCalcIndexes = getNeededPrecalculatedTermsIndexes();

        if(iterations == 0) {

            if(lowPrecReferenceOrbitNeeded) {
                referenceData.createAndSetShortcut(max_ref_iterations, true, preCalcIndexes, useCompressedRef);
            }
            else {
                referenceData.deallocate();
            }

            if (deepZoom) {
                referenceDeepData.createAndSetShortcut(max_ref_iterations, true, preCalcIndexes, useCompressedRef);
            }
        }
        else if (max_ref_iterations > getReferenceLength()){
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

        int bigNumLib = TaskDraw.getBignumLibrary(size, this);

        GenericComplex z, c, zold, zold2, start, c0, initVal, pixel;

        if (bigNumLib == Constants.BIGNUM_MPFR) {
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            initVal = bn.divide(3);
            z = iterations == 0 ? (isJulia ? bn : new MpfrBigNumComplex((MpfrBigNumComplex)initVal)) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new MpfrBigNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? new MpfrBigNumComplex(bn) : new MpfrBigNumComplex((MpfrBigNumComplex)initVal);
            c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if (bigNumLib == Constants.BIGNUM_MPIR) {
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            initVal = bn.divide(3);
            z = iterations == 0 ? (isJulia ? bn : new MpirBigNumComplex((MpirBigNumComplex)initVal)) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new MpirBigNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? new MpirBigNumComplex(bn) : new MpirBigNumComplex((MpirBigNumComplex)initVal);
            c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            DDComplex ddn = inputPixel.toDDComplex();
            initVal = ddn.divide(3);
            z = iterations == 0 ? (isJulia ? ddn : initVal) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : ddn;
            zold = iterations == 0 ? new DDComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? ddn : initVal;
            c0 = c;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            BigIntNumComplex bni = inputPixel.toBigIntNumComplex();
            initVal = bni.divide(3);
            z = iterations == 0 ? (isJulia ? bni : initVal) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bni;
            zold = iterations == 0 ? new BigIntNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? bni : initVal;
            c0 = c;
            pixel = bni;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            Complex bn = inputPixel.toComplex();
            initVal = bn.divide(3);
            z = iterations == 0 ? (isJulia ? bn : new Complex((Complex)initVal)) : referenceData.lastZValue;
            c = isJulia ? getSeed(bigNumLib) : bn;
            zold = iterations == 0 ? new Complex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : referenceData.thirdTolastZValue;
            start = isJulia ? new Complex(bn) : new Complex((Complex)initVal);
            c0 = new Complex((Complex) c);
            pixel = new Complex(bn);
        }
        else {
            initVal = inputPixel.divide(MyApfloat.THREE);

            z = iterations == 0 ? (isJulia ? inputPixel : initVal) : referenceData.lastZValue;

            c = isJulia ? getSeed(bigNumLib) : inputPixel;

            zold = iterations == 0 ? new BigComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? inputPixel : initVal;
            c0 = c;
            pixel = inputPixel;
        }


        Location loc = new Location();

        refPoint = inputPixel;

        c4big = c.times4();
        c2big = c.times2();
        csqrbig = c.square();

        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            csqrs3big = csqrbig.sub(3);
        }
        else {
            csqrs3big = csqrbig.sub(MyApfloat.THREE);
        }

        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            csqr2s3big = csqrbig.times2().sub_mutable(3);
        }
        else {
            csqr2s3big = csqrbig.times2().sub(MyApfloat.THREE);
        }

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
            Cdeep = loc.getMantExpComplex(c);

            if(isJulia) {
                seedSmallDeep = loc.getMantExpComplex(c);
            }

            refPointSmall = refPointSmallDeep.toComplex();

            if(lowPrecReferenceOrbitNeeded) {
                C = Cdeep.toComplex();
            }

            if(lowPrecReferenceOrbitNeeded && isJulia) {
                seedSmall = seedSmallDeep.toComplex();
            }
        }
        else {
            refPointSmall = refPoint.toComplex();

            if(lowPrecReferenceOrbitNeeded) {
                C = c.toComplex();
            }

            if(lowPrecReferenceOrbitNeeded && isJulia) {
                seedSmall = c.toComplex();
            }
        }

        RefType = getRefType();

        convergent_bailout_algorithm.setReferenceMode(true);

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[referenceDeep.id] = new ReferenceCompressor(this, iterations == 0 ? z.toMantExpComplex() : referenceData.compressorZm, c.toMantExpComplex(), start.toMantExpComplex());

                MantExpComplex cp = initVal.toMantExpComplex();
                Function<MantExpComplex, MantExpComplex> f = x -> x.sub(cp);
                functions[referenceDeepData.ReferenceSubCp.id] = f;
                subexpressionsCompressor[referenceDeepData.ReferenceSubCp.id] = new ReferenceCompressor(f, true);

                Function<MantExpComplex, MantExpComplex>[] fs = getPrecalculatedTermsFunctionsDeep(c.toMantExpComplex());
                for(int i = 0; i < preCalcIndexes.length; i++) {
                    int id = referenceDeepData.PrecalculatedTerms[preCalcIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i], true);
                }
            }
            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toComplex() : referenceData.compressorZ, c.toComplex(), start.toComplex());

                Complex cp = initVal.toComplex();
                Function<Complex, Complex> f = x -> x.sub(cp);
                functions[referenceData.ReferenceSubCp.id] = f;
                subexpressionsCompressor[referenceData.ReferenceSubCp.id] = new ReferenceCompressor(f);

                Function<Complex, Complex>[] fs = getPrecalculatedTermsFunctions(c.toComplex());
                for(int i = 0; i < preCalcIndexes.length; i++) {
                    int id = referenceData.PrecalculatedTerms[preCalcIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i]);
                }
            }
        }

        calculatedReferenceIterations = 0;
        MantExpComplex tempmcz = null;
        Complex cz = null;

        for (; iterations < max_ref_iterations; iterations++, calculatedReferenceIterations++) {

            GenericComplex zsubcp;
            if(bigNumLib == Constants.BIGNUM_MPFR) {
                zsubcp = z.sub(initVal, workSpaceData.temp1, workSpaceData.temp2);
            }
            else if(bigNumLib == Constants.BIGNUM_MPIR) {
                zsubcp = z.sub(initVal, workSpaceData.temp1p, workSpaceData.temp2p);
            }
            else {
                zsubcp = z.sub(initVal);
            }

            GenericComplex zsqr = z.square();

            GenericComplex zsqr3;

            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                zsqr3 = zsqr.times(3);
            }
            else {
                zsqr3 = zsqr.times(MyApfloat.THREE);
            }


            //4*C*Z^3 - 3*Z^4 - (C^2 - 3)*Z^2 + C^2 - 4*C*Z for catastrophic cancelation
            GenericComplex c4z = c4big.times(z);
            GenericComplex c4zSzsqr3 = c4z.sub(zsqr3);
            GenericComplex preCalc = c4zSzsqr3.sub(csqrs3big).times_mutable(zsqr).plus_mutable(csqrbig).sub_mutable(c4z);

            GenericComplex csztz = c.sub(z).times_mutable(z);
            GenericComplex preCalc2;
            // 9*C*Z^2 - 9*Z^3 - (2*C^2 - 3)*Z- C for catastrophic cancelation
            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                preCalc2 = csztz.times(9).sub_mutable(csqr2s3big).times_mutable(z).sub_mutable(c);
            }
            else {
                preCalc2 = csztz.times(MyApfloat.NINE).sub(csqr2s3big).times(z).sub(c);
            }

            GenericComplex c2z = c2big.times(z);
            GenericComplex preCalc3;
            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                preCalc3 = c2z.sub(zsqr3).plus_mutable(1);
            }
            else {
                preCalc3 = c2z.sub(zsqr3).plus(MyApfloat.ONE);
            }
            
            GenericComplex preCalc4;
            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                preCalc4 = c4zSzsqr3.times(3).sub_mutable(csqr2s3big.times2()).times_mutable(zsqr).sub_mutable(c4z).sub_mutable(1);
            }
            else {
                preCalc4 = c4zSzsqr3.times(MyApfloat.THREE).sub(csqr2s3big.times2()).times(zsqr).sub(c4z).sub(MyApfloat.ONE);
            }

            //12*(C*Z^2 - Z^3)-2*(C^2-3)*Z -4*C

            GenericComplex preCalc5;

            if (bigNumLib != Constants.BIGNUM_APFLOAT) {
                preCalc5 = csztz.times(12).sub_mutable(csqrs3big.times2()).times_mutable(z).sub_mutable(c4big);
            } else {
                preCalc5 = csztz.times(MyApfloat.TWELVE).sub(csqrs3big.times2()).times(z).sub(c4big);
            }

            GenericComplex zsqr3s1;
            if (bigNumLib != Constants.BIGNUM_APFLOAT) {
                zsqr3s1 = zsqr3.sub(1);

            } else {
                zsqr3s1 = zsqr3.sub(MyApfloat.ONE);
            }

            GenericComplex preCalc7 = null;
            GenericComplex preCalc8 = null;
            GenericComplex preCalc6 = null;
            if(!isJulia) {
                ////C*Z^2-Z^3-C+Z
                if (bigNumLib != Constants.BIGNUM_APFLOAT) {
                    preCalc6 = csztz.plus(1).times_mutable(z).sub(c);
                } else {
                    preCalc6 = csztz.plus(MyApfloat.ONE).times(z).sub(c);
                }

                //2*C*Z^2 + (-3*Z^2+1)*Z
                preCalc7 = c2z.sub(zsqr3s1).times_mutable(z);

                ////(Z^2-2)*Z^2 + 1
                if (bigNumLib != Constants.BIGNUM_APFLOAT) {
                    preCalc8 = zsqr.sub(2).times_mutable(zsqr).plus_mutable(1);
                } else {
                    preCalc8 = zsqr.sub(MyApfloat.TWO).times(zsqr).plus(MyApfloat.ONE);
                }
            }

            MantExpComplex zsubcpm = null, czm = null;
            MantExpComplex precalm = null, precal2m = null, precal3m = null, precal4m = null, precal5m = null;
            MantExpComplex precal6m = null, precal7m = null, precal8m = null;
            if(deepZoom) {
                czm = loc.getMantExpComplex(z);
                if (czm.isInfinite() || czm.isNaN()) {
                    break;
                }
                tempmcz = setArrayDeepValue(referenceDeep, iterations, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? czm.toComplex() : z.toComplex();
                if (cz.isInfinite() || cz.isNaN()) {
                    break;
                }

                cz = setArrayValue(reference, iterations, cz);
            }

            czm = tempmcz;

            if(deepZoom) {
                zsubcpm = loc.getMantExpComplex(zsubcp);
                precalm = loc.getMantExpComplex(preCalc);
                precal2m = loc.getMantExpComplex(preCalc2);
                precal3m = loc.getMantExpComplex(preCalc3);
                precal4m = loc.getMantExpComplex(preCalc4);
                precal5m = loc.getMantExpComplex(preCalc5);
                setArrayDeepValue(referenceDeepData.ReferenceSubCp, iterations, zsubcpm, czm);
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, precalm, czm);
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], iterations, precal2m, czm);
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[2], iterations, precal3m, czm);
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[3], iterations, precal4m, czm);
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[4], iterations, precal5m, czm);

                if(!isJulia) {
                    precal6m = loc.getMantExpComplex(preCalc6);
                    precal7m = loc.getMantExpComplex(preCalc7);
                    precal8m = loc.getMantExpComplex(preCalc8);
                    setArrayDeepValue(referenceDeepData.PrecalculatedTerms[5], iterations, precal6m, czm);
                    setArrayDeepValue(referenceDeepData.PrecalculatedTerms[6], iterations, precal7m, czm);
                    setArrayDeepValue(referenceDeepData.PrecalculatedTerms[7], iterations, precal8m, czm);
                }
            }

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.ReferenceSubCp, iterations, deepZoom ? zsubcpm.toComplex() : zsubcp.toComplex(), cz);
                setArrayValue(referenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalm.toComplex() : preCalc.toComplex(), cz);
                setArrayValue(referenceData.PrecalculatedTerms[1], iterations, deepZoom ? precal2m.toComplex() : preCalc2.toComplex(), cz);
                setArrayValue(referenceData.PrecalculatedTerms[2], iterations, deepZoom ? precal3m.toComplex() : preCalc3.toComplex(), cz);
                setArrayValue(referenceData.PrecalculatedTerms[3], iterations, deepZoom ? precal4m.toComplex() : preCalc4.toComplex(), cz);
                setArrayValue(referenceData.PrecalculatedTerms[4], iterations, deepZoom ? precal5m.toComplex() : preCalc5.toComplex(), cz);

                if(!isJulia) {
                    setArrayValue(referenceData.PrecalculatedTerms[5], iterations, deepZoom ? precal6m.toComplex() : preCalc6.toComplex(), cz);
                    setArrayValue(referenceData.PrecalculatedTerms[6], iterations, deepZoom ? precal7m.toComplex() : preCalc7.toComplex(), cz);
                    setArrayValue(referenceData.PrecalculatedTerms[7], iterations, deepZoom ? precal8m.toComplex() : preCalc8.toComplex(), cz);
                }
            }

            if (iterations > 0 && convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, c, start, c0, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);


            try {
                if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                    z = z.sub_mutable(z.sub(1).times_mutable(z.plus(1)).times_mutable(z.sub(c)).divide_mutable(c2z.negative().plus_mutable(zsqr3s1)));
                }
                else {
                    z = z.sub(z.sub(MyApfloat.ONE).times(z.plus(MyApfloat.ONE)).times(z.sub(c)).divide(c2z.negative().plus(zsqr3s1)));
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

        convergent_bailout_algorithm.setReferenceMode(false);

        referenceData.lastZValue = z;
        referenceData.secondTolastZValue = zold;
        referenceData.thirdTolastZValue = zold2;

        referenceData.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[referenceDeep.id].compact(referenceDeep);
                referenceData.compressorZm = referenceCompressor[referenceDeep.id].getZDeep();

                subexpressionsCompressor[referenceDeepData.ReferenceSubCp.id].compact(referenceDeepData.ReferenceSubCp);

                for(int i = 0; i < preCalcIndexes.length; i++) {
                    subexpressionsCompressor[referenceDeepData.PrecalculatedTerms[preCalcIndexes[i]].id].compact(referenceDeepData.PrecalculatedTerms[preCalcIndexes[i]]);
                }
            }

            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[reference.id].compact(reference);
                referenceData.compressorZ = referenceCompressor[reference.id].getZ();

                subexpressionsCompressor[referenceData.ReferenceSubCp.id].compact(referenceData.ReferenceSubCp);

                for(int i = 0; i < preCalcIndexes.length; i++) {
                    subexpressionsCompressor[referenceData.PrecalculatedTerms[preCalcIndexes[i]].id].compact(referenceData.PrecalculatedTerms[preCalcIndexes[i]]);
                }
            }
        }

        SAskippedIterations = 0;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }
        ReferenceCalculationTime = System.currentTimeMillis() - time;

        if(isJulia) {
            calculateJuliaReferencePoint(inputPixel, size, deepZoom, juliaIterations, progress);
        }
    }

    //Todo the whole perturbation implementation still has issues

    @Override
    protected void calculateJuliaReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int[] juliaIterations, JProgressBar progress) {

        int iterations = juliaIterations[0];
        //Since the initial value is variable we need to recalculate each time
//        if(iterations == 0 && ((!deepZoom && SecondReference != null) || (deepZoom && SecondReferenceDeep != null))) {
//            return;
//        }

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
        boolean useCompressedRef = TaskDraw.COMPRESS_REFERENCE_IF_POSSIBLE && supportsReferenceCompression();
        int[] preCalcIndexes = getNeededPrecalculatedTermsIndexes();

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                secondReferenceData.create(max_ref_iterations,true, preCalcIndexes, useCompressedRef);
            }
            else {
                secondReferenceData.deallocate();
            }

            if (deepZoom) {
                secondReferenceDeepData.create(max_ref_iterations,true, preCalcIndexes, useCompressedRef);
            }
        } else if (max_ref_iterations > getSecondReferenceLength()) {
            if(lowPrecReferenceOrbitNeeded) {
                secondReferenceData.resize(max_ref_iterations);
            }
            else {
                secondReferenceData.deallocate();
            }

            if (deepZoom) {
                secondReferenceDeepData.resize(max_ref_iterations);
            }
        }

        Location loc = new Location();

        GenericComplex z, c, zold, zold2, start, c0, pixel, initVal;

        int bigNumLib = TaskDraw.getBignumLibrary(size, this);

        if (bigNumLib == Constants.BIGNUM_MPFR) {
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            initVal = bn.divide(3);
            z = iterations == 0 ? new MpfrBigNumComplex((MpfrBigNumComplex)initVal) : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.thirdTolastZValue;
            start = new MpfrBigNumComplex((MpfrBigNumComplex)initVal);
            c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if (bigNumLib == Constants.BIGNUM_MPIR) {
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            initVal = bn.divide(3);
            z = iterations == 0 ? new MpirBigNumComplex((MpirBigNumComplex)initVal) : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.thirdTolastZValue;
            start = new MpirBigNumComplex((MpirBigNumComplex)initVal);
            c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            DDComplex ddn = inputPixel.toDDComplex();
            initVal = ddn.divide(3);
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new DDComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            BigIntNumComplex bni = inputPixel.toBigIntNumComplex();
            initVal = bni.divide(3);
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new BigIntNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = bni;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            Complex bn = inputPixel.toComplex();
            initVal = bn.divide(3);
            z = iterations == 0 ? new Complex((Complex)initVal) : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new Complex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : secondReferenceData.thirdTolastZValue;
            start = new Complex((Complex)initVal);
            c0 = new Complex((Complex) c);
            pixel = new Complex(bn);
        }
        else {
            initVal = inputPixel.divide(MyApfloat.THREE);

            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new BigComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = inputPixel;
        }

        convergent_bailout_algorithm.setReferenceMode(true);

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[secondReferenceDeepData.Reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toMantExpComplex() : secondReferenceData.compressorZm, c.toMantExpComplex(), start.toMantExpComplex());

                MantExpComplex cp = initVal.toMantExpComplex();
                Function<MantExpComplex, MantExpComplex> f = x -> x.sub(cp);
                functions[secondReferenceDeepData.ReferenceSubCp.id] = f;
                subexpressionsCompressor[secondReferenceDeepData.ReferenceSubCp.id] = new ReferenceCompressor(f, true);

                Function<MantExpComplex, MantExpComplex>[] fs = getPrecalculatedTermsFunctionsDeep(c.toMantExpComplex());
                for(int i = 0; i < preCalcIndexes.length; i++) {
                    int id = secondReferenceDeepData.PrecalculatedTerms[preCalcIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i], true);
                }
            }
            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[secondReferenceData.Reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toComplex() : secondReferenceData.compressorZ, c.toComplex(), start.toComplex());

                Complex cp = initVal.toComplex();
                Function<Complex, Complex> f = x -> x.sub(cp);
                functions[secondReferenceData.ReferenceSubCp.id] = f;
                subexpressionsCompressor[secondReferenceData.ReferenceSubCp.id] = new ReferenceCompressor(f);

                Function<Complex, Complex>[] fs = getPrecalculatedTermsFunctions(c.toComplex());
                for(int i = 0; i < preCalcIndexes.length; i++) {
                    int id = secondReferenceData.PrecalculatedTerms[preCalcIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i]);
                }
            }
        }

        calculatedSecondReferenceIterations = 0;

        MantExpComplex tempczm = null;
        Complex cz = null;

        for (; iterations < max_ref_iterations; iterations++, calculatedSecondReferenceIterations++) {

            GenericComplex zsubcp;
            if(bigNumLib == Constants.BIGNUM_MPFR) {
                zsubcp = z.sub(initVal, workSpaceData.temp1, workSpaceData.temp2);
            }
            else if(bigNumLib == Constants.BIGNUM_MPIR) {
                zsubcp = z.sub(initVal, workSpaceData.temp1p, workSpaceData.temp2p);
            }
            else {
                zsubcp = z.sub(initVal);
            }

            GenericComplex zsqr = z.square();

            GenericComplex zsqr3;

            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                zsqr3 = zsqr.times(3);
            }
            else {
                zsqr3 = zsqr.times(MyApfloat.THREE);
            }

            //4*C*Z^3 - 3*Z^4 - (C^2 - 3)*Z^2 + C^2 - 4*C*Z for catastrophic cancelation
            GenericComplex c4z = c4big.times(z);
            GenericComplex c4zSzsqr3 = c4z.sub(zsqr3);
            GenericComplex preCalc = c4zSzsqr3.sub(csqrs3big).times_mutable(zsqr).plus_mutable(csqrbig).sub_mutable(c4z);

            GenericComplex csztz = c.sub(z).times_mutable(z);
            GenericComplex preCalc2;
            // 9*C*Z^2 - 9*Z^3 - (2*C^2 - 3)*Z- C for catastrophic cancelation
            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                preCalc2 = csztz.times(9).sub_mutable(csqr2s3big).times_mutable(z).sub_mutable(c);
            }
            else {
                preCalc2 = csztz.times(MyApfloat.NINE).sub(csqr2s3big).times(z).sub(c);
            }

            GenericComplex c2z = c2big.times(z);
            GenericComplex preCalc3;
            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                preCalc3 = c2z.sub(zsqr3).plus_mutable(1);
            }
            else {
                preCalc3 = c2z.sub(zsqr3).plus(MyApfloat.ONE);
            }


            GenericComplex preCalc4;
            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                preCalc4 = c4zSzsqr3.times(3).sub_mutable(csqr2s3big.times2()).times_mutable(zsqr).sub_mutable(c4z).sub_mutable(1);
            }
            else {
                preCalc4 = c4zSzsqr3.times(MyApfloat.THREE).sub(csqr2s3big.times2()).times(zsqr).sub(c4z).sub(MyApfloat.ONE);
            }

            //12*(C*Z^2 - Z^3)-2*(C^2-3)*Z -4*C

            GenericComplex preCalc5;

            if (bigNumLib != Constants.BIGNUM_APFLOAT) {
                preCalc5 = csztz.times(12).sub_mutable(csqrs3big.times2()).times_mutable(z).sub_mutable(c4big);
            } else {
                preCalc5 = csztz.times(MyApfloat.TWELVE).sub(csqrs3big.times2()).times(z).sub(c4big);
            }

            MantExpComplex zsubcpm = null, czm = null;
            MantExpComplex precalm = null, precal2m = null, precal3m = null, precal4m = null, precal5m = null;
            if(deepZoom) {
                czm = loc.getMantExpComplex(z);
                if (czm.isInfinite() || czm.isNaN()) {
                    break;
                }
                tempczm = setArrayDeepValue(secondReferenceDeepData.Reference, iterations, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? czm.toComplex() : z.toComplex();
                if (cz.isInfinite() || cz.isNaN()) {
                    break;
                }

                cz = setArrayValue(secondReferenceData.Reference, iterations, cz);
            }

            czm = tempczm;

            if(deepZoom) {
                zsubcpm = loc.getMantExpComplex(zsubcp);
                precalm = loc.getMantExpComplex(preCalc);
                precal2m = loc.getMantExpComplex(preCalc2);
                precal3m = loc.getMantExpComplex(preCalc3);
                precal4m = loc.getMantExpComplex(preCalc4);
                precal5m = loc.getMantExpComplex(preCalc5);
                setArrayDeepValue(secondReferenceDeepData.ReferenceSubCp, iterations, zsubcpm, czm);
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[0], iterations, precalm, czm);
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[1], iterations, precal2m, czm);
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[2], iterations, precal3m, czm);
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[3], iterations, precal4m, czm);
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[4], iterations, precal5m, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.ReferenceSubCp, iterations, deepZoom ? zsubcpm.toComplex() : zsubcp.toComplex(), cz);
                setArrayValue(secondReferenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalm.toComplex() : preCalc.toComplex(), cz);
                setArrayValue(secondReferenceData.PrecalculatedTerms[1], iterations, deepZoom ? precal2m.toComplex() : preCalc2.toComplex(), cz);
                setArrayValue(secondReferenceData.PrecalculatedTerms[2], iterations, deepZoom ? precal3m.toComplex() : preCalc3.toComplex(), cz);
                setArrayValue(secondReferenceData.PrecalculatedTerms[3], iterations, deepZoom ? precal4m.toComplex() : preCalc4.toComplex(), cz);
                setArrayValue(secondReferenceData.PrecalculatedTerms[4], iterations, deepZoom ? precal5m.toComplex() : preCalc5.toComplex(), cz);
            }

            if (iterations > 0 && convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, c, start, c0, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);


            try {
                if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                    z = z.sub_mutable(z.sub(1).times_mutable(z.plus(1)).times_mutable(z.sub(c)).divide_mutable(c2z.negative().plus_mutable(zsqr3).sub_mutable(1)));
                }
                else {
                    z = z.sub(z.sub(MyApfloat.ONE).times(z.plus(MyApfloat.ONE)).times(z.sub(c)).divide(c2z.negative().plus(zsqr3).sub(MyApfloat.ONE)));
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

        convergent_bailout_algorithm.setReferenceMode(false);

        secondReferenceData.lastZValue = z;
        secondReferenceData.secondTolastZValue = zold;
        secondReferenceData.thirdTolastZValue = zold2;

        secondReferenceData.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[secondReferenceDeepData.Reference.id].compact(secondReferenceDeepData.Reference);
                secondReferenceData.compressorZm = referenceCompressor[secondReferenceDeepData.Reference.id].getZDeep();

                subexpressionsCompressor[secondReferenceDeepData.ReferenceSubCp.id].compact(secondReferenceDeepData.ReferenceSubCp);

                for(int i = 0; i < preCalcIndexes.length; i++) {
                    subexpressionsCompressor[secondReferenceDeepData.PrecalculatedTerms[preCalcIndexes[i]].id].compact(secondReferenceDeepData.PrecalculatedTerms[preCalcIndexes[i]]);
                }
            }

            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[secondReferenceData.Reference.id].compact(secondReferenceData.Reference);
                secondReferenceData.compressorZ = referenceCompressor[secondReferenceData.Reference.id].getZ();

                subexpressionsCompressor[secondReferenceData.ReferenceSubCp.id].compact(secondReferenceData.ReferenceSubCp);

                for(int i = 0; i < preCalcIndexes.length; i++) {
                    subexpressionsCompressor[secondReferenceData.PrecalculatedTerms[preCalcIndexes[i]].id].compact(secondReferenceData.PrecalculatedTerms[preCalcIndexes[i]]);
                }
            }
        }


        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        SecondReferenceCalculationTime = System.currentTimeMillis() - time;
    }

    /*
        (2*(2*C*Z - 3*Z^2 + 1)*z^3 + (12*C*Z^2 - 12*Z^3 - 2*(C^2 - 3)*Z - (2*C*Z - 3*Z^2 + 1)*c - 4*C)*z^2 - (Z^4 - 2*Z^2 + 1)*c + 2*(4*C*Z^3 - 3*Z^4 - (C^2 - 3)*Z^2 + C^2 - 4*C*Z - (C*Z^2 - Z^3 - C + Z)*c)*z)/(12*C*Z^3 - 9*Z^4 - 2*(2*C^2 - 3)*Z^2 + 3*(2*C*Z - 3*Z^2 + 1)*z^2 - 4*C*Z - 2*(2*C*Z^2 - 3*Z^3 + Z)*c + 2*(9*C*Z^2 - 9*Z^3 - (2*C^2 - 3)*Z - (2*C*Z - 3*Z^2 + 1)*c - C)*z - 1)
     */

    //4*C*Z^3 - 3*Z^4 - (C^2 - 3)*Z^2 + C^2 - 4*C*Z is 0 when Z = C / 3
    // 9*C*Z^2 - 9*Z^3 - (2*C^2 - 3)*Z- C is 0 when Z = C / 3
    @Override
    public Complex perturbationFunction(Complex z, Complex c, int RefIteration) {


        Complex zsqr = z.square();

        Complex Z = null;
        if(reference.compressed) {
            Z = getArrayValue(reference, RefIteration);
        }

        Complex temp1 = getArrayValue(referenceData.PrecalculatedTerms[2], RefIteration, Z);
        Complex temp9 = temp1.times(c);

        Complex temp11 = getArrayValue(referenceData.PrecalculatedTerms[7], RefIteration, Z);


        Complex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[4], RefIteration, Z).sub_mutable(temp9).times_mutable(zsqr))
                .sub_mutable(temp11.times_mutable(c))
        .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z).sub_mutable(getArrayValue(referenceData.PrecalculatedTerms[5], RefIteration, Z).times_mutable(c)).times2_mutable().times_mutable(z));


        Complex denom = getArrayValue(referenceData.PrecalculatedTerms[3], RefIteration, Z)
                .plus_mutable(temp1.times(zsqr).times_mutable(3))
                .sub_mutable(getArrayValue(referenceData.PrecalculatedTerms[6], RefIteration, Z).times_mutable(c).times2_mutable())
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z).sub_mutable(temp9).times_mutable(z).times2_mutable())
                ;



        return num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {

        MantExpComplex zsqr = z.square();

        MantExpComplex Z = null;
        if(referenceDeep.compressed) {
            Z = getArrayDeepValue(referenceDeep, RefIteration);
        }

        MantExpComplex temp1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[2], RefIteration, Z);
        MantExpComplex temp9 = temp1.times(c);

        MantExpComplex temp11 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[7], RefIteration, Z);


        MantExpComplex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[4], RefIteration, Z).sub_mutable(temp9).times_mutable(zsqr))
                .sub_mutable(temp11.times_mutable(c))
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z).sub_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[5], RefIteration, Z).times_mutable(c)).times2_mutable().times_mutable(z));


        MantExpComplex denom = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[3], RefIteration, Z)
                .plus_mutable(temp1.times(zsqr).times_mutable(MantExp.THREE))
                .sub_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[6], RefIteration, Z).times_mutable(c).times2_mutable())
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z).sub_mutable(temp9).times_mutable(z).times2_mutable())
                ;



        return num.divide_mutable(denom);
    }

    @Override
    public Complex perturbationFunction(Complex z, Complex c, ReferenceData data, int RefIteration) {
        Complex zsqr = z.square();

        Complex Z = null;
        if(data.Reference.compressed) {
            Z = getArrayValue(data.Reference, RefIteration);
        }

        Complex temp1 = getArrayValue(data.PrecalculatedTerms[2], RefIteration, Z);
        Complex temp9 = temp1.times(c);

        Complex temp11 = getArrayValue(data.PrecalculatedTerms[7], RefIteration, Z);


        Complex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[4], RefIteration, Z).sub_mutable(temp9).times_mutable(zsqr))
                .sub_mutable(temp11.times_mutable(c))
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[0], RefIteration, Z).sub_mutable(getArrayValue(data.PrecalculatedTerms[5], RefIteration, Z).times_mutable(c)).times2_mutable().times_mutable(z));


        Complex denom = getArrayValue(data.PrecalculatedTerms[3], RefIteration, Z)
                .plus_mutable(temp1.times(zsqr).times_mutable(3))
                .sub_mutable(getArrayValue(data.PrecalculatedTerms[6], RefIteration, Z).times_mutable(c).times2_mutable())
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[1], RefIteration, Z).sub_mutable(temp9).times_mutable(z).times2_mutable())
                ;



        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, ReferenceDeepData data, int RefIteration) {
        MantExpComplex zsqr = z.square();

        MantExpComplex Z = null;
        if(data.Reference.compressed) {
            Z = getArrayDeepValue(data.Reference, RefIteration);
        }

        MantExpComplex temp1 = getArrayDeepValue(data.PrecalculatedTerms[2], RefIteration, Z);
        MantExpComplex temp9 = temp1.times(c);

        MantExpComplex temp11 = getArrayDeepValue(data.PrecalculatedTerms[7], RefIteration, Z);


        MantExpComplex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[4], RefIteration, Z).sub_mutable(temp9).times_mutable(zsqr))
                .sub_mutable(temp11.times_mutable(c))
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration, Z).sub_mutable(getArrayDeepValue(data.PrecalculatedTerms[5], RefIteration, Z).times_mutable(c)).times2_mutable().times_mutable(z));


        MantExpComplex denom = getArrayDeepValue(data.PrecalculatedTerms[3], RefIteration, Z)
                .plus_mutable(temp1.times(zsqr).times_mutable(MantExp.THREE))
                .sub_mutable(getArrayDeepValue(data.PrecalculatedTerms[6], RefIteration, Z).times_mutable(c).times2_mutable())
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[1], RefIteration, Z).sub_mutable(temp9).times_mutable(z).times2_mutable())
                ;



        return num.divide_mutable(denom);
    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex zsqr = z.square();

        Complex Z = null;
        if(reference.compressed) {
            Z = getArrayValue(reference, RefIteration);
        }

        Complex temp1 = getArrayValue(referenceData.PrecalculatedTerms[2], RefIteration, Z);


        Complex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[4], RefIteration, Z).times_mutable(zsqr))
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z).times2_mutable().times_mutable(z));


        Complex denom = getArrayValue(referenceData.PrecalculatedTerms[3], RefIteration, Z)
                .plus_mutable(temp1.times(zsqr).times_mutable(3))
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z).times_mutable(z).times2_mutable())
                ;


        return num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex zsqr = z.square();

        MantExpComplex Z = null;
        if(referenceDeep.compressed) {
            Z = getArrayDeepValue(referenceDeep, RefIteration);
        }

        MantExpComplex temp1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[2], RefIteration, Z);


        MantExpComplex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[4], RefIteration, Z).times_mutable(zsqr))
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z).times2_mutable().times_mutable(z));


        MantExpComplex denom = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[3], RefIteration, Z)
                .plus_mutable(temp1.times(zsqr).times_mutable(MantExp.THREE))
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z).times_mutable(z).times2_mutable())
                ;


        return num.divide_mutable(denom);
    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {

        Complex zsqr = z.square();

        Complex Z = null;
        if(data.Reference.compressed) {
            Z = getArrayValue(data.Reference, RefIteration);
        }

        Complex temp1 = getArrayValue(data.PrecalculatedTerms[2], RefIteration, Z);


        Complex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[4], RefIteration, Z).times_mutable(zsqr))
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[0], RefIteration, Z).times2_mutable().times_mutable(z));


        Complex denom = getArrayValue(data.PrecalculatedTerms[3], RefIteration, Z)
                .plus_mutable(temp1.times(zsqr).times_mutable(3))
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[1], RefIteration, Z).times_mutable(z).times2_mutable())
                ;


        return num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {

        MantExpComplex zsqr = z.square();

        MantExpComplex Z = null;
        if(data.Reference.compressed) {
            Z = getArrayDeepValue(data.Reference, RefIteration);
        }

        MantExpComplex temp1 = getArrayDeepValue(data.PrecalculatedTerms[2], RefIteration, Z);


        MantExpComplex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[4], RefIteration, Z).times_mutable(zsqr))
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration, Z).times2_mutable().times_mutable(z));


        MantExpComplex denom = getArrayDeepValue(data.PrecalculatedTerms[3], RefIteration, Z)
                .plus_mutable(temp1.times(zsqr).times_mutable(MantExp.THREE))
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[1], RefIteration, Z).times_mutable(z).times2_mutable())
                ;


        return num.divide_mutable(denom);
    }

    @Override
    public Complex[] initializePerturbation(Complex dpixel) {

        Complex[] complex = new Complex[2];

        if(isJulia) {
            return super.initializePerturbation(dpixel);
        }
        else {
            complex[0] = defaultInitVal.getValue(dpixel);
            complex[1] = new Complex(dpixel);
        }

        return complex;

    }

    @Override
    public MantExpComplex[] initializePerturbation(MantExpComplex dpixel) {

        MantExpComplex[] complex = new MantExpComplex[2];

        if(isJulia) {
            return super.initializePerturbation(dpixel);
        }
        else {
            complex[0] = dpixel.divide(MantExp.THREE);
            complex[1] = MantExpComplex.copy(dpixel);
        }

        return complex;

    }

    @Override
    public void function(GenericComplex[] complex) {
        if(complex[0] instanceof BigComplex) {
            complex[0] = complex[0].sub(complex[0].sub(MyApfloat.ONE).times(complex[0].plus(MyApfloat.ONE)).times(complex[0].sub(complex[1])).divide(complex[1].times(complex[0]).times2().negative().plus(complex[0].square().times(MyApfloat.THREE)).sub(MyApfloat.ONE)));
        }
        else {
            complex[0] = complex[0].sub_mutable(complex[0].sub(1).times_mutable(complex[0].plus(1)).times_mutable(complex[0].sub(complex[1])).divide_mutable(complex[1].times(complex[0]).times2_mutable().negative_mutable().plus_mutable(complex[0].square().times_mutable(3)).sub_mutable(1)));
        }
    }

    @Override
    public GenericComplex[] initialize(GenericComplex pixel) {

        GenericComplex[] complex = new GenericComplex[2];

        int lib = TaskDraw.getHighPrecisionLibrary(dsize, this);

        if(lib == ARBITRARY_MPFR) {

            workSpaceData.z.set(((MpfrBigNumComplex) pixel).divide(3));
            complex[0] = workSpaceData.z;//z

            workSpaceData.c.set(pixel);
            complex[1] = workSpaceData.c;//c

            workSpaceData.zold.reset();
            gzold = workSpaceData.zold;

            workSpaceData.zold2.reset();
            gzold2 = workSpaceData.zold2;

            workSpaceData.start.set(complex[0]);
            gstart = workSpaceData.start;

            workSpaceData.c0.set(complex[1]);
            gc0 = workSpaceData.c0;
        }
        else if(lib == ARBITRARY_MPIR) {

            workSpaceData.zp.set(((MpirBigNumComplex) pixel).divide(3));
            complex[0] = workSpaceData.zp;//z

            workSpaceData.cp.set(pixel);
            complex[1] = workSpaceData.cp;//c

            workSpaceData.zoldp.reset();
            gzold = workSpaceData.zoldp;

            workSpaceData.zold2p.reset();
            gzold2 = workSpaceData.zold2p;

            workSpaceData.startp.set(complex[0]);
            gstart = workSpaceData.startp;

            workSpaceData.c0p.set(complex[1]);
            gc0 = workSpaceData.c0p;
        }
        else if(lib == ARBITRARY_BIGINT) {
            complex[0] = ((BigIntNumComplex) pixel).divide(3);//z
            complex[1] = pixel;//c

            gzold = new BigIntNumComplex();
            gzold2 = new BigIntNumComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }
        else if(lib == ARBITRARY_DOUBLEDOUBLE) {
            complex[0] = ((DDComplex) pixel).divide(3);//z
            complex[1] = pixel;//c

            gzold = new DDComplex();
            gzold2 = new DDComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }
        else {
            complex[0] = ((BigComplex) pixel).divide(MyApfloat.THREE);//z
            complex[1] = pixel;//c

            gzold = new BigComplex();
            gzold2 = new BigComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }

        return complex;

    }

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {
        return z.sub(1).times_mutable(z.plus(1)).times_mutable(z.sub(c));
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean supportsMpirBignum() { return true;}

    @Override
    public double getDoubleLimit() {
        return 1.0e-5;
    }

    @Override
    public double getDoubleDoubleLimit() {
        if(TaskDraw.HIGH_PRECISION_CALCULATION) {
            return super.getDoubleDoubleLimit();
        }

        return 1.0e-12;
    }

    @Override
    public boolean supportsDouble() {
        return false;
    }

    @Override
    public boolean needsExtendedRange() {
        return TaskDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM || (TaskDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT && size < 1e-30);
    }

    @Override
    public boolean supportsBigIntnum() {
        return true;
    }

     /*
     @Override
    public boolean needsMultiReference() {
        return true;
    }

    @Override
    public int[] getStartingIterations() {
        return isJulia ? new int[] {0} : new int[] {0, 0, 0, 0};
    }

    @Override
    public int[] getSecondStartingIterations() {
        return new int[] {0, 0, 0, 0};
    }

    @Override
    public int[] getNextIterations() {
        return isJulia ? new int[] {multiReferenceData[0].MaxRefIteration + 1} : new int[] {multiReferenceData[0].MaxRefIteration + 1 , multiReferenceData[1].MaxRefIteration + 1, multiReferenceData[2].MaxRefIteration + 1, multiReferenceData[3].MaxRefIteration + 1};
    }

    @Override
    public int[] getSecondNextIterations() {
        return new int[] {secondMultiReferenceData[0].MaxRefIteration + 1 , secondMultiReferenceData[1].MaxRefIteration + 1, secondMultiReferenceData[2].MaxRefIteration + 1, secondMultiReferenceData[3].MaxRefIteration + 1};

    }*/

//    @Override
//    protected boolean additionalConvergentBailoutTest(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
//        return z.distance_squared(1) <= 1e-3 || z.distance_squared(-1) <= 1e-3;
//    }

    @Override
    public boolean supportsReferenceCompression() {
        return true;
    }

    @Override
    public Complex function(Complex z, Complex c) {
        return z.sub_mutable(z.sub(1).times_mutable(z.plus(1)).times_mutable(z.sub(c)).divide_mutable(c.times(z).times2_mutable().negative_mutable().plus_mutable(z.square().times_mutable(3)).sub_mutable(1)));
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        return z.sub_mutable(z.sub(MantExp.ONE).times_mutable(z.plus(MantExp.ONE)).times_mutable(z.sub(c)).divide_mutable(c.times(z).times2_mutable().negative_mutable().plus_mutable(z.square().times_mutable(MantExp.THREE)).sub_mutable(MantExp.ONE)));
    }

    @Override
    protected boolean needsRefSubCp() {
        return true;
    }

    @Override
    public double getPower() {
        return 3;
    }
}
