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
import org.apfloat.Apfloat;

import java.util.ArrayList;
import java.util.function.Function;

public class MagnetPataki2 extends MagnetPatakiType {

    public MagnetPataki2(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, escaping_smooth_algorithm, ots, sts);

        exponent = 2;
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

    public MagnetPataki2(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);

        exponent = 2;
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
    public MagnetPataki2(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        exponent = 2;

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

    public MagnetPataki2(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        exponent = 2;
        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(0, 0);
    }

    @Override
    protected double getLogPower() {

        double v;
        if(bailout > 1000000) {
            v = 2.0;
        }
        else if(bailout < 10) {
            v = 1.6999999;
        }
        else {
            double x = bailout;
            double x1, y1, x2, y2;
            if(bailout >= 10 && bailout < 100) {
                x1 = 10;
                x2 = 100;
                y1 = 1.6999999;
                y2 = 1.84999999;
            }
            else if(bailout >= 100 && bailout < 1000) {
                x1 = 100;
                x2 = 1000;
                y1 = 1.84999999;
                y2 = 1.8999999;
            }
            else if(bailout >= 1000 && bailout < 10000) {
                x1 = 1000;
                x2 = 10000;
                y1 = 1.8999999;
                y2 = 1.925;
            }
            else if(bailout >= 10000 && bailout < 100000) {
                x1 = 10000;
                x2 = 100000;
                y1 = 1.925;
                y2 = 1.94;
            }
            else {
                x1 = 100000;
                x2 = 1000000;
                y1 = 1.94;
                y2 = 1.95;
            }

            v = y1 + (x - x1)*(y2 - y1)/(x2 - x1);
        }

        return Math.log(Math.sqrt(v));
    }

    @Override
    public void function(Complex[] complex) {

        Complex zsqr = complex[0].square();
        complex[0] = zsqr.plus(complex[1]).divide_mutable(zsqr.sub(1));

    }

    @Override
    public void function(GenericComplex[] complex) {
        GenericComplex zsqr = complex[0].square();
        if(complex[0] instanceof BigComplex) {
            complex[0] = zsqr.plus(complex[1]).divide(zsqr.sub(MyApfloat.ONE));
        }
        else {
            complex[0] = zsqr.plus(complex[1]).divide_mutable(zsqr.sub(1));
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

    //-(2*(C + 1)*Z*z + (C + 1)*z^2 - (Z^2 - 1)*c)/(Z^4 + (Z^2 - 1)*z^2 - 2*Z^2 + 2*(Z^3 - Z)*z + 1)
    @Override
    public Complex perturbationFunction(Complex z, Complex c, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);

        Complex zsqrs1 = getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = Z.times2().plus(z).times_mutable(z); //(2*Z + z)*z

        Complex num = temp
                .times(Cp1)
                .sub_mutable(zsqrs1.times(c)).negative_mutable();

        Complex denom = temp.times(zsqrs1)
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z))
                ;

        return num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        MantExpComplex zsqrs1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = Z.times2().plus(z).times_mutable(z); //(2*Z + z)*z

        MantExpComplex num = temp
                .times(Cp1Deep)
                .sub_mutable(zsqrs1.times(c)).negative_mutable();

        MantExpComplex denom = temp.times(zsqrs1)
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z));

        return num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);

        Complex zsqrs1 = getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = Z.times2().plus(z).times_mutable(z); //(2*Z + z)*z

        Complex num = temp
                .times(Cp1)
                .negative_mutable();

        Complex denom = temp.times(zsqrs1)
                .plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z));

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        MantExpComplex zsqrs1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = Z.times2().plus(z).times_mutable(z); //(2*Z + z)*z

        MantExpComplex num = temp
                .times(Cp1Deep)
                .negative_mutable();

        MantExpComplex denom = temp.times(zsqrs1)
                .plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z));

        return num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {
        Complex Z = getArrayValue(data.Reference, RefIteration);

        Complex zsqrs1 = getArrayValue(data.PrecalculatedTerms[0], RefIteration, Z);

        Complex temp = Z.times2().plus(z).times_mutable(z); //(2*Z + z)*z

        Complex num = temp
                .times(Cp1)
                .negative_mutable();

        Complex denom = temp.times(zsqrs1)
                .plus_mutable(getArrayValue(data.PrecalculatedTerms[1], RefIteration, Z));

        return num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {
        MantExpComplex Z = getArrayDeepValue(data.Reference, RefIteration);

        MantExpComplex zsqrs1 = getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration, Z);

        MantExpComplex temp = Z.times2().plus(z).times_mutable(z); //(2*Z + z)*z

        MantExpComplex num = temp
                .times(Cp1Deep)
                .negative_mutable();

        MantExpComplex denom = temp.times(zsqrs1)
                .plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[1], RefIteration, Z));

        return num.divide_mutable(denom);

    }

    @Override
    protected Function[] getPrecalculatedTermsFunctions(Complex c) {
        Function<Complex, Complex> f1 = x -> x.square().sub_mutable(1);
        Function<Complex, Complex> f2 = x -> {Complex temp = x.square(); return temp.sub(2).times_mutable(temp).plus_mutable(1);};
        return new Function[] {f1, f2};
    }

    @Override
    protected Function[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        Function<MantExpComplex, MantExpComplex> f1 = x -> x.square().sub_mutable(MantExp.ONE);
        Function<MantExpComplex, MantExpComplex> f2 = x -> {MantExpComplex temp = x.square(); return temp.sub(MantExp.TWO).times_mutable(temp).plus_mutable(MantExp.ONE);};
        return new Function[] {f1, f2};
    }

    @Override
    protected GenericComplex[] precalculateReferenceData(GenericComplex z, GenericComplex c, NormComponents normData, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations, Complex cz, MantExpComplex mcz) {
        GenericComplex zsqr;
        if(normData != null) {
            zsqr = z.squareFast_mutable(normData);
        }
        else {
            zsqr = z.square();
        }

        GenericComplex preCalc;
        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            preCalc = zsqr.sub(1);
        }
        else {
            preCalc = zsqr.sub(MyApfloat.ONE);
        }

        GenericComplex preCalc2;
        if(bigNumLib != Constants.BIGNUM_APFLOAT) {
            preCalc2 = zsqr.sub(2).times_mutable(zsqr).plus_mutable(1);
        }
        else {
            preCalc2 = zsqr.sub(MyApfloat.TWO).times(zsqr).plus(MyApfloat.ONE);
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

        return new GenericComplex[] {zsqr, preCalc};
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
        Complex zsqr = z.square();
        return zsqr.plus(c).divide_mutable(zsqr.sub(1));
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        MantExpComplex zsqr = z.square();
        return zsqr.plus(c).divide_mutable(zsqr.sub(MantExp.ONE));
    }
}
