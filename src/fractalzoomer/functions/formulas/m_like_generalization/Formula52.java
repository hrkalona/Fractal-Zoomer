package fractalzoomer.functions.formulas.m_like_generalization;

import fractalzoomer.core.Complex;
import fractalzoomer.core.NumericLibrary;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.*;
import fractalzoomer.core.numerics.mpir.MpirBigNum;
import fractalzoomer.core.reference.ReferenceData;
import fractalzoomer.core.reference.ReferenceDeepData;
import fractalzoomer.core.reference.SerializableFunction;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValueWithFactor;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.EscapingOrConverging;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import java.util.ArrayList;

import static fractalzoomer.main.Constants.*;

public class Formula52 extends EscapingOrConverging {

    public Formula52(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        defaultInitVal = new DefaultInitialValueWithFactor(-1.5);

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
            init_val = defaultInitVal;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public Formula52(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        defaultInitVal = new DefaultInitialValueWithFactor(-1.5);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
    }

    //orbit
    public Formula52(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        defaultInitVal = new DefaultInitialValueWithFactor(-1.5);
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
            init_val = defaultInitVal;
        }

    }

    public Formula52(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        defaultInitVal = new DefaultInitialValueWithFactor(-1.5);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
    }

    @Override
    public void function(Complex[] complex) {

        complex[0] = complex[0].cube().divide_mutable(complex[0].plus(complex[1]));

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

    //-(Z^3*c - (C + Z)*z^3 - 3*((C + Z)*Z)*z^2 - ((3*C + 2*Z)*Z^2)*z)/(C^2 + (2*C + Z)*Z + (C + Z)*c + (C + Z)*z)
    @Override
    public Complex perturbationFunction(Complex z, Complex c, int RefIteration) {
        Complex Z = getReferenceValue(reference, RefIteration);
        Complex precalc = getExpressionValue(referenceData.PrecalculatedTerms[0], RefIteration, Z);

        Complex num = precalc.times(z.square())
                .plus_mutable(getExpressionValue(referenceData.PrecalculatedTerms[2], RefIteration, Z).times_mutable(z))
                .plus_mutable(getExpressionValue(referenceData.PrecalculatedTerms[3], RefIteration, Z)).times_mutable(z)
                .sub_mutable(Z.cube().times_mutable(c));

        Complex denom = getExpressionValue(referenceData.PrecalculatedTerms[1], RefIteration, Z)
                .plus_mutable(precalc.times(c))
                .plus_mutable(precalc.times(z));

        return num.divide_mutable(denom);
    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {
        Complex Z = getReferenceValue(data.Reference, RefIteration);
        Complex precalc = getExpressionValue(data.PrecalculatedTerms[0], RefIteration, Z);

        Complex num = precalc.times(z.square())
                .plus_mutable(getExpressionValue(data.PrecalculatedTerms[2], RefIteration, Z).times_mutable(z))
                .plus_mutable(getExpressionValue(data.PrecalculatedTerms[3], RefIteration, Z)).times_mutable(z);

        Complex denom = getExpressionValue(data.PrecalculatedTerms[1], RefIteration, Z)
                .plus_mutable(precalc.times(z));

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {
        MantExpComplex Z = getReferenceDeepValue(data.Reference, RefIteration);

        MantExpComplex precalc = getExpressionDeepValue(data.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex num = precalc.times(z.square())
                .plus_mutable(getExpressionDeepValue(data.PrecalculatedTerms[2], RefIteration, Z).times_mutable(z))
                .plus_mutable(getExpressionDeepValue(data.PrecalculatedTerms[3], RefIteration, Z)).times_mutable(z);

        MantExpComplex denom = getExpressionDeepValue(data.PrecalculatedTerms[1], RefIteration, Z)
                .plus_mutable(precalc.times(z));

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {
        MantExpComplex Z = getReferenceDeepValue(referenceDeep, RefIteration);
        MantExpComplex precalc = getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex num = precalc.times(z.square())
                .plus_mutable(getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[2], RefIteration, Z).times_mutable(z))
                .plus_mutable(getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[3], RefIteration, Z)).times_mutable(z)
                .sub_mutable(Z.cube().times_mutable(c));

        MantExpComplex denom = getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z)
                .plus_mutable(precalc.times(c))
                .plus_mutable(precalc.times(z));

        return num.divide_mutable(denom);
    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex Z = getReferenceValue(reference, RefIteration);
        Complex precalc = getExpressionValue(referenceData.PrecalculatedTerms[0], RefIteration, Z);

        Complex num = precalc.times(z.square())
                .plus_mutable(getExpressionValue(referenceData.PrecalculatedTerms[2], RefIteration, Z).times_mutable(z))
                .plus_mutable(getExpressionValue(referenceData.PrecalculatedTerms[3], RefIteration, Z)).times_mutable(z);

        Complex denom = getExpressionValue(referenceData.PrecalculatedTerms[1], RefIteration, Z)
                .plus_mutable(precalc.times(z));

        return num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {
        MantExpComplex Z = getReferenceDeepValue(referenceDeep, RefIteration);
        MantExpComplex precalc = getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex num = precalc.times(z.square())
                .plus_mutable(getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[2], RefIteration, Z).times_mutable(z))
                .plus_mutable(getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[3], RefIteration, Z)).times_mutable(z);

        MantExpComplex denom = getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z)
                .plus_mutable(precalc.times(z));

        return num.divide_mutable(denom);
    }

    @Override
    public void function(GenericComplex[] complex) {

        complex[0] = complex[0].cube().divide_mutable(complex[0].plus(complex[1]));

    }

    @Override
    public boolean supportsBigIntnum() {
        return true;
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean supportsMpirBignum() { return true;}

    @Override
    protected GenericComplex referenceFunction(GenericComplex z, GenericComplex c, NormComponents normData, GenericComplex[] initialPrecal, GenericComplex[] precalc) {
        if(normData != null) {
            return z.cubeFast(normData).divide_mutable(z.plus(c));
        }
        else {
            return z.cube().divide_mutable(z.plus(c));
        }
    }

    @Override
    public boolean supportsReferenceCompression() {
        return true;
    }

    @Override
    public boolean supportsReferenceSavingOrLoading() {
        return true;
    }

    @Override
    public Complex function(Complex z, Complex c) {
        return z.cube().divide_mutable(z.plus(c));
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        return z.cube().divide_mutable(z.plus(c));
    }

    @Override
    public double getPower() {
        return 3;
    }

    @Override
    public boolean hasFiniteBailoutCheck() {
        return true;
    }

    @Override
    protected GenericComplex getReferenceInitVal(int bigNumLib, GenericComplex inputPixel) {
        if(bigNumLib == Constants.BIGNUM_BIGINT) {
            return inputPixel.toBigIntNumComplex().times(-1.5);
        }
        else if(bigNumLib == Constants.BIGNUM_MPFR) {
            return new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex()).times(-1.5);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            return new MpirBigNumComplex(inputPixel.toMpirBigNumComplex()).times(new MpirBigNum(-1.5));
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            return inputPixel.toDDComplex().times(-1.5);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            return inputPixel.toComplex().times(-1.5);
        }
        else {
            return inputPixel.times(new MyApfloat(-1.5));
        }
    }

    @Override
    protected int[] getNeededPrecalculatedTermsIndexes() {
        return new int[] {0, 1, 2, 3};
    }

    @Override
    protected boolean needsRefSubCp() {
        return true;
    }

    @Override
    protected GenericComplex[] precalculateReferenceData(GenericComplex z, GenericComplex c, NormComponents normData, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations, Complex cz, MantExpComplex mcz) {

        GenericComplex zsqr;
        if(normData != null) {
            zsqr = z.squareFast(normData);
        }
        else {
            zsqr = z.square();
        }

        GenericComplex preCalc = z.plus(c); // Z + C
        GenericComplex preCalc2 = z.plus(c.times2()).times_mutable(z).plus_mutable(c.square()); //C^2 + (2*C + Z)*Z

        GenericComplex preCalc3;
        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            preCalc3 = preCalc.times(z).times_mutable(3); // 3*((C + Z)*Z)
        } else {
            preCalc3 = preCalc.times(z).times(MyApfloat.THREE); // 3*((C + Z)*Z)
        }

        GenericComplex preCalc4;
        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            preCalc4 = z.times2().plus_mutable(c.times(3)).times_mutable(zsqr); //(3*C + 2*Z)*Z^2
        } else {
            preCalc4 = z.times2().plus_mutable(c.times(MyApfloat.THREE)).times_mutable(zsqr); //(3*C + 2*Z)*Z^2
        }

        MantExpComplex precalcm = null;
        MantExpComplex precalc2m = null;
        MantExpComplex precalc3m = null;
        MantExpComplex precalc4m = null;
        if(deepZoom) {
            precalcm = loc.getMantExpComplex(preCalc);
            precalc2m = loc.getMantExpComplex(preCalc2);
            precalc3m = loc.getMantExpComplex(preCalc3);
            precalc4m = loc.getMantExpComplex(preCalc4);
            setExpressionDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, precalcm, mcz);
            setExpressionDeepValue(referenceDeepData.PrecalculatedTerms[1], iterations, precalc2m, mcz);
            setExpressionDeepValue(referenceDeepData.PrecalculatedTerms[2], iterations, precalc3m, mcz);
            setExpressionDeepValue(referenceDeepData.PrecalculatedTerms[3], iterations, precalc4m, mcz);
        }
        if(lowPrecReferenceOrbitNeeded) {
            setExpressionValue(referenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalcm.toComplex() : preCalc.toComplex(), cz);
            setExpressionValue(referenceData.PrecalculatedTerms[1], iterations, deepZoom ? precalc2m.toComplex() : preCalc2.toComplex(), cz);
            setExpressionValue(referenceData.PrecalculatedTerms[2], iterations, deepZoom ? precalc3m.toComplex() : preCalc3.toComplex(), cz);
            setExpressionValue(referenceData.PrecalculatedTerms[3], iterations, deepZoom ? precalc4m.toComplex() : preCalc4.toComplex(), cz);
        }

        return new GenericComplex[] {};
    }

    @Override
    protected SerializableFunction[] getPrecalculatedTermsFunctions(Complex c) {
        SerializableFunction<Complex, Complex> f1 = x -> x.plus(c);

        SerializableFunction<Complex, Complex> f2 = x -> x.plus(c.times2()).times_mutable(x).plus_mutable(c.square());

        SerializableFunction<Complex, Complex> f3 = x -> x.plus(c).times_mutable(x).times_mutable(3);

        SerializableFunction<Complex, Complex> f4 = x -> x.times2().plus_mutable(c.times(3)).times_mutable(x.square());

        return new SerializableFunction[] {f1, f2, f3, f4};
    }

    @Override
    protected SerializableFunction[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        SerializableFunction<MantExpComplex, MantExpComplex> f1 = x -> x.plus(c);

        SerializableFunction<MantExpComplex, MantExpComplex> f2 = x -> x.plus(c.times2()).times_mutable(x).plus_mutable(c.square());

        SerializableFunction<MantExpComplex, MantExpComplex> f3 = x -> x.plus(c).times_mutable(x).times_mutable(MantExp.THREE);

        SerializableFunction<MantExpComplex, MantExpComplex> f4 = x -> x.times2().plus_mutable(c.times(MantExp.THREE)).times_mutable(x.square());

        return new SerializableFunction[] {f1, f2, f3, f4};
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
            complex[0] = dpixel.times(-1.5);
            complex[1] = MantExpComplex.copy(dpixel);
        }

        return complex;

    }

    @Override
    protected GenericComplex getInputPixel(GenericComplex inputPixel) {
        return sanitizeInputPixel(inputPixel);
    }

    @Override
    public GenericComplex[] initialize(GenericComplex pixel) {

        GenericComplex[] complex = new GenericComplex[2];

        int lib = NumericLibrary.getHighPrecisionImplementation(dsize, this);

        if(lib == ARBITRARY_MPFR) {

            workSpaceData.z.set(((MpfrBigNumComplex) pixel).times(-1.5));
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

            workSpaceData.zp.set(((MpirBigNumComplex) pixel).times(new MpirBigNum(-1.5)));
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
            complex[0] = ((BigIntNumComplex) pixel).times(-1.5);//z
            complex[1] = pixel;//c

            gzold = new BigIntNumComplex();
            gzold2 = new BigIntNumComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }
        else if(lib == ARBITRARY_DOUBLEDOUBLE) {
            complex[0] = ((DDComplex) pixel).times(-1.5);//z
            complex[1] = pixel;//c

            gzold = new DDComplex();
            gzold2 = new DDComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }
        else {
            complex[0] = ((BigComplex) pixel).times(new MyApfloat(-1.5));//z
            complex[1] = pixel;//c

            gzold = new BigComplex();
            gzold2 = new BigComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }

        return complex;

    }
}
