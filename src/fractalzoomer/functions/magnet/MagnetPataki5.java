package fractalzoomer.functions.magnet;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.reference.ReferenceData;
import fractalzoomer.core.reference.ReferenceDeepData;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import java.util.ArrayList;
import java.util.function.Function;

public class MagnetPataki5 extends MagnetPatakiType {

    public MagnetPataki5(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, escaping_smooth_algorithm, ots, sts);

        exponent = 5;
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
            init_val = new InitialValue(0, 0);
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
            statistic.setLogPower(getLogPower());
        }
    }

    public MagnetPataki5(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);

        exponent = 5;
        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
            statistic.setLogPower(getLogPower());
        }

        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(0, 0);
    }

    //orbit
    public MagnetPataki5(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        exponent = 5;

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
            init_val = new InitialValue(0, 0);
        }

    }

    public MagnetPataki5(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        exponent = 5;
        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(0, 0);
    }

    @Override
    public void function(Complex[] complex) {

        Complex zfifth = complex[0].fifth();
        complex[0] = zfifth.plus(complex[1]).divide_mutable(zfifth.sub(1));

    }

    @Override
    public void function(GenericComplex[] complex) {
        GenericComplex zfifth = complex[0].fifth();
        if(complex[0] instanceof BigComplex) {
            complex[0] = zfifth.plus(complex[1]).divide(zfifth.sub(MyApfloat.ONE));
        }
        else {
            complex[0] = zfifth.plus(complex[1]).divide_mutable(zfifth.sub(1));
        }
    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || !juliter;
    }

    @Override
    public boolean supportsMpfrBignum() {
        return true;
    }

    @Override
    public boolean supportsMpirBignum() { return true;}

    // -(5*(C + 1)*Z^4*z + 10*(C + 1)*Z^3*z^2 + 10*(C + 1)*Z^2*z^3 + 5*(C + 1)*Z*z^4 + (C + 1)*z^5 - (Z^5 - 1)*c)
    // /
    // (Z^10 + (Z^5 - 1)*z^5 - 2*Z^5 + 5*(Z^6 - Z)*z^4 + 10*(Z^7 - Z^2)*z^3 + 10*(Z^8 - Z^3)*z^2 + 5*(Z^9 - Z^4)*z + 1)
    @Override
    public Complex perturbationFunction(Complex z, Complex c, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);
        Complex ZFourth = Z.fourth();
        Complex ZCube = Z.cube();
        Complex Zsqr = Z.square();

        Complex zfifths1 = getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = ZFourth.times(5).plus_mutable(ZCube.times(10).plus_mutable(Zsqr.times(10).plus_mutable(Z.times(5).plus_mutable(z).times_mutable(z)).times_mutable(z)).times_mutable(z)).times_mutable(z);

        Complex num = temp.times(Cp1)
                .sub_mutable(zfifths1.times(c)).negative_mutable();

        Complex denom = temp.times(zfifths1)
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        MantExpComplex ZFourth = Z.fourth();
        MantExpComplex ZCube = Z.cube();
        MantExpComplex Zsqr = Z.square();

        MantExpComplex zfifths1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = ZFourth.times(MantExp.FIVE).plus_mutable(ZCube.times(MantExp.TEN).plus_mutable(Zsqr.times(MantExp.TEN).plus_mutable(Z.times(MantExp.FIVE).plus_mutable(z).times_mutable(z)).times_mutable(z)).times_mutable(z)).times_mutable(z);

        MantExpComplex num = temp.times(Cp1Deep)
                .sub_mutable(zfifths1.times(c)).negative_mutable();

        MantExpComplex denom = temp.times(zfifths1)
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);
        Complex ZFourth = Z.fourth();
        Complex ZCube = Z.cube();
        Complex Zsqr = Z.square();

        Complex zfifths1 = getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = ZFourth.times(5).plus_mutable(ZCube.times(10).plus_mutable(Zsqr.times(10).plus_mutable(Z.times(5).plus_mutable(z).times_mutable(z)).times_mutable(z)).times_mutable(z)).times_mutable(z);

        Complex num = temp.times(Cp1)
                .negative_mutable();

        Complex denom = temp.times(zfifths1)
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        MantExpComplex ZFourth = Z.fourth();
        MantExpComplex ZCube = Z.cube();
        MantExpComplex Zsqr = Z.square();

        MantExpComplex zfifths1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = ZFourth.times(MantExp.FIVE).plus_mutable(ZCube.times(MantExp.TEN).plus_mutable(Zsqr.times(MantExp.TEN).plus_mutable(Z.times(MantExp.FIVE).plus_mutable(z).times_mutable(z)).times_mutable(z)).times_mutable(z)).times_mutable(z);

        MantExpComplex num = temp.times(Cp1Deep)
                .negative_mutable();

        MantExpComplex denom = temp.times(zfifths1)
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {
        Complex Z = getArrayValue(data.Reference, RefIteration);

        Complex ZFourth = Z.fourth();
        Complex ZCube = Z.cube();
        Complex Zsqr = Z.square();

        Complex zfifths1 = getArrayValue(data.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = ZFourth.times(5).plus_mutable(ZCube.times(10).plus_mutable(Zsqr.times(10).plus_mutable(Z.times(5).plus_mutable(z).times_mutable(z)).times_mutable(z)).times_mutable(z)).times_mutable(z);

        Complex num = temp.times(Cp1)
                .negative_mutable();

        Complex denom = temp.times(zfifths1)
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {
        MantExpComplex Z = getArrayDeepValue(data.Reference, RefIteration);

        MantExpComplex ZFourth = Z.fourth();
        MantExpComplex ZCube = Z.cube();
        MantExpComplex Zsqr = Z.square();

        MantExpComplex zfifths1 = getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = ZFourth.times(MantExp.FIVE).plus_mutable(ZCube.times(MantExp.TEN).plus_mutable(Zsqr.times(MantExp.TEN).plus_mutable(Z.times(MantExp.FIVE).plus_mutable(z).times_mutable(z)).times_mutable(z)).times_mutable(z)).times_mutable(z);

        MantExpComplex num = temp.times(Cp1Deep)
                .negative_mutable();

        MantExpComplex denom = temp.times(zfifths1)
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    protected Function[] getPrecalculatedTermsFunctions(Complex c) {
        Function<Complex, Complex> f1 = x -> x.fifth().sub_mutable(1);
        Function<Complex, Complex> f2 = x -> {Complex temp = x.fifth(); return temp.sub(2).times_mutable(temp).plus_mutable(1);};
        return new Function[] {f1, f2};
    }

    @Override
    protected Function[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        Function<MantExpComplex, MantExpComplex> f1 = x -> x.fifth().sub_mutable(MantExp.ONE);
        Function<MantExpComplex, MantExpComplex> f2 = x -> {MantExpComplex temp = x.fifth(); return temp.sub(MantExp.TWO).times_mutable(temp).plus_mutable(MantExp.ONE);};
        return new Function[] {f1, f2};
    }

    @Override
    protected GenericComplex[] precalculateReferenceData(GenericComplex z, GenericComplex c, NormComponents normData, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations, Complex cz, MantExpComplex mcz) {
        GenericComplex zfifth;
        if(normData != null) {
            zfifth = z.fifthFast_mutable(normData);
        }
        else {
            zfifth = z.fifth();
        }

        GenericComplex preCalc;
        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            preCalc = zfifth.sub(1);
        }
        else {
            preCalc = zfifth.sub(MyApfloat.ONE);
        }

        GenericComplex preCalc2;
        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            preCalc2 = zfifth.sub(2).times_mutable(zfifth).plus_mutable(1);
        }
        else {
            preCalc2 = zfifth.sub(MyApfloat.TWO).times(zfifth).plus(MyApfloat.ONE);
        }

        MantExpComplex precalcm = null;
        MantExpComplex precalc2m = null;
        if(deepZoom) {
            precalcm = loc.getMantExpComplex(preCalc);
            precalc2m = loc.getMantExpComplex(preCalc2);
            setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, precalcm, mcz);
            setArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], iterations, precalc2m, mcz);
        }
        if(lowPrecReferenceOrbitNeeded) {
            setArrayValue(referenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalcm.toComplex() : preCalc.toComplex(), cz);
            setArrayValue(referenceData.PrecalculatedTerms[1], iterations, deepZoom ? precalc2m.toComplex() : preCalc2.toComplex(), cz);
        }

        return new GenericComplex[] {zfifth, preCalc};
    }

    @Override
    public boolean supportsBigIntnum() {
        return true;
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public boolean supportsReferenceCompression() {
        return true;
    }

    @Override
    public Complex function(Complex z, Complex c) {
        Complex zfifth = z.fifth();
        return zfifth.plus(c).divide_mutable(zfifth.sub(1));
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        MantExpComplex zfifth = z.fifth();
        return zfifth.plus(c).divide_mutable(zfifth.sub(MantExp.ONE));
    }

}
