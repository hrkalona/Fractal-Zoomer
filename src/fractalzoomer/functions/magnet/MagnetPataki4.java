package fractalzoomer.functions.magnet;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;

import java.util.ArrayList;
import java.util.function.Function;

public class MagnetPataki4 extends MagnetPatakiType {

    public MagnetPataki4(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, escaping_smooth_algorithm, ots, sts);

        exponent = 4;
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

    public MagnetPataki4(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);

        exponent = 4;
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
    public MagnetPataki4(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        exponent = 4;

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

    public MagnetPataki4(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        exponent = 4;
        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(0, 0);
    }

    @Override
    public void function(Complex[] complex) {

        Complex zfourth = complex[0].fourth();
        complex[0] = zfourth.plus(complex[1]).divide_mutable(zfourth.sub(1));

    }

    @Override
    public void function(GenericComplex[] complex) {
        GenericComplex zfourth = complex[0].fourth();
        if(complex[0] instanceof BigComplex) {
            complex[0] = zfourth.plus(complex[1]).divide(zfourth.sub(MyApfloat.ONE));
        }
        else {
            complex[0] = zfourth.plus(complex[1]).divide_mutable(zfourth.sub(1));
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

    //-(4*(C + 1)*Z^3*z + 6*(C + 1)*Z^2*z^2 + 4*(C + 1)*Z*z^3 + (C + 1)*z^4 - (Z^4 - 1)*c)
    // /
    // (Z^8 + (Z^4 - 1)*z^4 - 2*Z^4 + 4*(Z^5 - Z)*z^3 + 6*(Z^6 - Z^2)*z^2 + 4*(Z^7 - Z^3)*z + 1)
    @Override
    public Complex perturbationFunction(Complex z, Complex c, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);
        Complex ZCube = Z.cube();
        Complex Zsqr = Z.square();

        Complex zfourths1 = getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = z.plus(Z.times4()).times_mutable(z).plus_mutable(Zsqr.times(6)).times_mutable(z).plus_mutable(ZCube.times4()).times_mutable(z);

        Complex num = temp.times(Cp1)
                .sub_mutable(zfourths1.times(c)).negative_mutable();

        Complex denom = temp.times(zfourths1)
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        MantExpComplex ZCube = Z.cube();
        MantExpComplex Zsqr = Z.square();

        MantExpComplex zfourths1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = z.plus(Z.times4()).times_mutable(z).plus_mutable(Zsqr.times(MantExp.SIX)).times_mutable(z).plus_mutable(ZCube.times4()).times_mutable(z);

        MantExpComplex num = temp.times(Cp1Deep)
                .sub_mutable(zfourths1.times(c)).negative_mutable();

        MantExpComplex denom = temp.times(zfourths1)
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);
        Complex ZCube = Z.cube();
        Complex Zsqr = Z.square();

        Complex zfourths1 = getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = z.plus(Z.times4()).times_mutable(z).plus_mutable(Zsqr.times(6)).times_mutable(z).plus_mutable(ZCube.times4()).times_mutable(z);

        Complex num = temp.times(Cp1)
                .negative_mutable();

        Complex denom = temp.times(zfourths1)
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        MantExpComplex ZCube = Z.cube();
        MantExpComplex Zsqr = Z.square();


        MantExpComplex zfourths1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = z.plus(Z.times4()).times_mutable(z).plus_mutable(Zsqr.times(MantExp.SIX)).times_mutable(z).plus_mutable(ZCube.times4()).times_mutable(z);

        MantExpComplex num = temp.times(Cp1Deep)
                .negative_mutable();

        MantExpComplex denom = temp.times(zfourths1)
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {
        Complex Z = getArrayValue(data.Reference, RefIteration);
        Complex ZCube = Z.cube();
        Complex Zsqr = Z.square();

        Complex zfourths1 = getArrayValue(data.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = z.plus(Z.times4()).times_mutable(z).plus_mutable(Zsqr.times(6)).times_mutable(z).plus_mutable(ZCube.times4()).times_mutable(z);

        Complex num = temp.times(Cp1)
                .negative_mutable();

        Complex denom = temp.times(zfourths1)
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {
        MantExpComplex Z = getArrayDeepValue(data.Reference, RefIteration);
        MantExpComplex ZCube = Z.cube();
        MantExpComplex Zsqr = Z.square();


        MantExpComplex zfourths1 = getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = z.plus(Z.times4()).times_mutable(z).plus_mutable(Zsqr.times(MantExp.SIX)).times_mutable(z).plus_mutable(ZCube.times4()).times_mutable(z);

        MantExpComplex num = temp.times(Cp1Deep)
                .negative_mutable();

        MantExpComplex denom = temp.times(zfourths1)
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[1], RefIteration, Z));

        return  num.divide_mutable(denom);

    }

    @Override
    protected Function[] getPrecalculatedTermsFunctions(Complex c) {
        Function<Complex, Complex> f1 = x -> x.fourth().sub_mutable(1);
        Function<Complex, Complex> f2 = x -> {Complex temp = x.fourth(); return temp.sub(2).times_mutable(temp).plus_mutable(1);};
        return new Function[] {f1, f2};
    }

    @Override
    protected Function[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        Function<MantExpComplex, MantExpComplex> f1 = x -> x.fourth().sub_mutable(MantExp.ONE);
        Function<MantExpComplex, MantExpComplex> f2 = x -> {MantExpComplex temp = x.fourth(); return temp.sub(MantExp.TWO).times_mutable(temp).plus_mutable(MantExp.ONE);};
        return new Function[] {f1, f2};
    }

    @Override
    protected GenericComplex[] precalculateReferenceData(GenericComplex z, GenericComplex c, NormComponents normData, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations, Complex cz, MantExpComplex mcz) {
        GenericComplex zfourth;
        if(normData != null) {
            zfourth = z.fourthFast_mutable(normData);
        }
        else {
            zfourth = z.fourth();
        }

        GenericComplex preCalc;
        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            preCalc = zfourth.sub(1);
        }
        else {
            preCalc = zfourth.sub(MyApfloat.ONE);
        }

        GenericComplex preCalc2;
        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            preCalc2 = zfourth.sub(2).times_mutable(zfourth).plus_mutable(1);
        }
        else {
            preCalc2 = zfourth.sub(MyApfloat.TWO).times(zfourth).plus(MyApfloat.ONE);
        }

        MantExpComplex precalM = null;
        MantExpComplex precal2M = null;
        if(deepZoom) {
            precalM = loc.getMantExpComplex(preCalc);
            precal2M = loc.getMantExpComplex(preCalc2);
            setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, precalM, mcz);
            setArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], iterations, precal2M, mcz);
        }

        if(lowPrecReferenceOrbitNeeded) {
            setArrayValue(referenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalM.toComplex() : preCalc.toComplex(), cz);
            setArrayValue(referenceData.PrecalculatedTerms[1], iterations, deepZoom ? precal2M.toComplex() : preCalc2.toComplex(), cz);
        }

        return new GenericComplex[] {zfourth, preCalc};
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
        Complex zfourth = z.fourth();
        return zfourth.plus(c).divide_mutable(zfourth.sub(1));
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        MantExpComplex zfourth = z.fourth();
        return zfourth.plus(c).divide_mutable(zfourth.sub(MantExp.ONE));
    }


}
