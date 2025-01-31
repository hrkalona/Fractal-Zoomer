
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.core.*;
import fractalzoomer.core.reference.ReferenceData;
import fractalzoomer.core.reference.ReferenceDeepData;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class MandelbarCubed extends Julia {

    public MandelbarCubed() {
        super();
    }

    public MandelbarCubed(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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

    public MandelbarCubed(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();
    }

    //orbit
    public MandelbarCubed(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

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

    public MandelbarCubed(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();
    }

    @Override
    public void function(Complex[] complex) {

        complex[0].cube_mutable().negate_re_mutable().plus_mutable(complex[1]);

    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || !juliter;
    }

    @Override
    protected GenericComplex referenceFunction(GenericComplex z, GenericComplex c, NormComponents normData, GenericComplex[] initialPrecal, GenericComplex[] precalc) {
        if(normData != null) {
            z = z.cubeFast_mutable(normData).negate_re_mutable().plus_mutable(c);
        }
        else {
            if(z instanceof MpfrBigNumComplex) {
                z = z.cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).negate_re_mutable().plus_mutable(c);
            }
            else if(z instanceof MpirBigNumComplex) {
                z = z.cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).negate_re_mutable().plus_mutable(c);
            }
            else {
                z = z.cube().negate_re_mutable().plus_mutable(c);
            }
        }

        return z;
    }

    @Override
    public void function(GenericComplex[] complex) {
        if(complex[0] instanceof MpfrBigNumComplex) {
            complex[0] = complex[0].cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).negate_re_mutable().plus_mutable(complex[1]);
        }
        else if(complex[0] instanceof MpirBigNumComplex) {
            complex[0] = complex[0].cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).negate_re_mutable().plus_mutable(complex[1]);
        }
        else {
            complex[0] = complex[0].cube().negate_re_mutable().plus_mutable(complex[1]);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex X = getArrayValue(reference, RefIteration);

        double Xr = X.getRe();
        double Xi = X.getIm();
        double xr = DeltaSubN.getRe();
        double xi = DeltaSubN.getIm();
        double xr2 = xr * xr;
        double xi2 = xi * xi;
        double Xr2 = Xr * Xr;
        double Xi2 = Xi * Xi;

        double cr = DeltaSub0.getRe();
        double ci = DeltaSub0.getIm();

        double temp = 2 * Xi * xi + xi2;
        double temp2 = 6 * Xr * xr;

        double xrn = cr - xr * (3 * Xr2 + xr2) + 3 * Xr * (temp - xr2) + 3 * xr * (temp + Xi2);
        double xin = temp2 * Xi + 3 * Xi * xr2 - 3 * Xi2 * xi - 3 * Xi * xi2 + 3 * Xr2 * xi + temp2 * xi + 3 * xr2 * xi - xi2 * xi + ci;


        return new Complex(xrn, xin);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex X = getArrayDeepValue(referenceDeep, RefIteration);

        MantExp Xr = X.getRe();
        MantExp Xi = X.getIm();
        MantExp xr = DeltaSubN.getRe();
        MantExp xi = DeltaSubN.getIm();
        MantExp xr2 = xr.square();
        MantExp xi2 = xi.square();
        MantExp Xr2 = Xr.square();
        MantExp Xi2 = Xi.square();

        MantExp cr = DeltaSub0.getRe();
        MantExp ci = DeltaSub0.getIm();

        MantExp temp = Xi.multiply(xi).multiply2_mutable().add_mutable(xi2);
        MantExp temp2 = Xr.multiply(xr).multiply_mutable(MantExp.SIX);

        MantExp xrn = cr
                .subtract_mutable(xr.multiply(Xr2.multiply(MantExp.THREE).add_mutable(xr2)))   //- xr * (3 * Xr2 + xr2)
                .add_mutable(Xr.multiply(MantExp.THREE).multiply_mutable(temp.subtract(xr2)))//+ 3 * Xr * (xi2 + 2 * Xi * xi - xr2)
                .add_mutable(xr.multiply(MantExp.THREE).multiply_mutable(temp.add(Xi2)));//+ 3 * xr * (Xi2 + 2 * Xi * xi + xi2);

        MantExp xin = temp2.multiply(Xi) //6 * Xr * Xi * xr
                .add_mutable(Xi.multiply(xr2).multiply_mutable(MantExp.THREE)) // + 3 * Xi * xr2
                .subtract_mutable(Xi2.multiply(xi).multiply_mutable(MantExp.THREE))//- 3 * Xi2 * xi
                .subtract_mutable(Xi.multiply(xi2).multiply_mutable(MantExp.THREE))//- 3 * Xi * xi2
                .add_mutable(Xr2.multiply(xi).multiply_mutable(MantExp.THREE))//+ 3 * Xr2 * xi
                .add_mutable(temp2.multiply(xi))//+ 6 * Xr * xr * xi
                .add_mutable(xr2.multiply(xi).multiply_mutable(MantExp.THREE))//+ 3 * xr2 * xi
                .subtract_mutable(xi2.multiply(xi))//- xi2 * xi
                .add_mutable(ci);//+ ci;

        return MantExpComplex.create(xrn, xin);
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex X = getArrayValue(reference, RefIteration);

        double Xr = X.getRe();
        double Xi = X.getIm();
        double xr = DeltaSubN.getRe();
        double xi = DeltaSubN.getIm();
        double xr2 = xr * xr;
        double xi2 = xi * xi;
        double Xr2 = Xr * Xr;
        double Xi2 = Xi * Xi;

        double temp = 2 * Xi * xi + xi2;
        double temp2 = 6 * Xr * xr;

        double xrn = - xr * (3 * Xr2 + xr2) + 3 * Xr * (temp - xr2) + 3 * xr * (temp + Xi2);
        double xin = temp2 * Xi + 3 * Xi * xr2 - 3 * Xi2 * xi - 3 * Xi * xi2 + 3 * Xr2 * xi + temp2 * xi + 3 * xr2 * xi - xi2 * xi;

        return new Complex(xrn, xin);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex X = getArrayDeepValue(referenceDeep, RefIteration);

        MantExp Xr = X.getRe();
        MantExp Xi = X.getIm();
        MantExp xr = DeltaSubN.getRe();
        MantExp xi = DeltaSubN.getIm();
        MantExp xr2 = xr.square();
        MantExp xi2 = xi.square();
        MantExp Xr2 = Xr.square();
        MantExp Xi2 = Xi.square();

        MantExp temp = Xi.multiply(xi).multiply2_mutable().add_mutable(xi2);
        MantExp temp2 = Xr.multiply(xr).multiply_mutable(MantExp.SIX);

        MantExp xrn = xr.multiply(Xr2.multiply(MantExp.THREE).add_mutable(xr2)).negate_mutable()   //- xr * (3 * Xr2 + xr2)
                .add_mutable(Xr.multiply(MantExp.THREE).multiply_mutable(temp.subtract(xr2)))//+ 3 * Xr * (xi2 + 2 * Xi * xi - xr2)
                .add_mutable(xr.multiply(MantExp.THREE).multiply_mutable(temp.add(Xi2)));//+ 3 * xr * (Xi2 + 2 * Xi * xi + xi2);

        MantExp xin = temp2.multiply(Xi) //6 * Xr * Xi * xr
                .add_mutable(Xi.multiply(xr2).multiply_mutable(MantExp.THREE)) // + 3 * Xi * xr2
                .subtract_mutable(Xi2.multiply(xi).multiply_mutable(MantExp.THREE))//- 3 * Xi2 * xi
                .subtract_mutable(Xi.multiply(xi2).multiply_mutable(MantExp.THREE))//- 3 * Xi * xi2
                .add_mutable(Xr2.multiply(xi).multiply_mutable(MantExp.THREE))//+ 3 * Xr2 * xi
                .add_mutable(temp2.multiply(xi))//+ 6 * Xr * xr * xi
                .add_mutable(xr2.multiply(xi).multiply_mutable(MantExp.THREE))//+ 3 * xr2 * xi
                .subtract_mutable(xi2.multiply(xi))//- xi2 * xi
                ;

        return MantExpComplex.create(xrn, xin);
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, ReferenceData data, int RefIteration) {
        Complex X = getArrayValue(data.Reference, RefIteration);

        double Xr = X.getRe();
        double Xi = X.getIm();
        double xr = DeltaSubN.getRe();
        double xi = DeltaSubN.getIm();
        double xr2 = xr * xr;
        double xi2 = xi * xi;
        double Xr2 = Xr * Xr;
        double Xi2 = Xi * Xi;

        double temp = 2 * Xi * xi + xi2;
        double temp2 = 6 * Xr * xr;

        double xrn = - xr * (3 * Xr2 + xr2) + 3 * Xr * (temp - xr2) + 3 * xr * (temp + Xi2);
        double xin = temp2 * Xi + 3 * Xi * xr2 - 3 * Xi2 * xi - 3 * Xi * xi2 + 3 * Xr2 * xi + temp2 * xi + 3 * xr2 * xi - xi2 * xi;

        return new Complex(xrn, xin);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, ReferenceDeepData data, int RefIteration) {
        MantExpComplex X = getArrayDeepValue(data.Reference, RefIteration);

        MantExp Xr = X.getRe();
        MantExp Xi = X.getIm();
        MantExp xr = DeltaSubN.getRe();
        MantExp xi = DeltaSubN.getIm();
        MantExp xr2 = xr.square();
        MantExp xi2 = xi.square();
        MantExp Xr2 = Xr.square();
        MantExp Xi2 = Xi.square();

        MantExp temp = Xi.multiply(xi).multiply2_mutable().add_mutable(xi2);
        MantExp temp2 = Xr.multiply(xr).multiply_mutable(MantExp.SIX);

        MantExp xrn = xr.multiply(Xr2.multiply(MantExp.THREE).add_mutable(xr2)).negate_mutable()   //- xr * (3 * Xr2 + xr2)
                .add_mutable(Xr.multiply(MantExp.THREE).multiply_mutable(temp.subtract(xr2)))//+ 3 * Xr * (xi2 + 2 * Xi * xi - xr2)
                .add_mutable(xr.multiply(MantExp.THREE).multiply_mutable(temp.add(Xi2)));//+ 3 * xr * (Xi2 + 2 * Xi * xi + xi2);

        MantExp xin = temp2.multiply(Xi) //6 * Xr * Xi * xr
                .add_mutable(Xi.multiply(xr2).multiply_mutable(MantExp.THREE)) // + 3 * Xi * xr2
                .subtract_mutable(Xi2.multiply(xi).multiply_mutable(MantExp.THREE))//- 3 * Xi2 * xi
                .subtract_mutable(Xi.multiply(xi2).multiply_mutable(MantExp.THREE))//- 3 * Xi * xi2
                .add_mutable(Xr2.multiply(xi).multiply_mutable(MantExp.THREE))//+ 3 * Xr2 * xi
                .add_mutable(temp2.multiply(xi))//+ 6 * Xr * xr * xi
                .add_mutable(xr2.multiply(xi).multiply_mutable(MantExp.THREE))//+ 3 * xr2 * xi
                .subtract_mutable(xi2.multiply(xi))//- xi2 * xi
                ;

        return MantExpComplex.create(xrn, xin);
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public boolean supportsBignum() { return true;}

    @Override
    public boolean supportsBigIntnum() {
        return true;
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
        return z.cube_mutable().negate_re_mutable().plus_mutable(c);
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        return z.cube_mutable().negate_re_mutable().plus_mutable(c);
    }

    @Override
    public double getPower() {
        return 3;
    }

}
