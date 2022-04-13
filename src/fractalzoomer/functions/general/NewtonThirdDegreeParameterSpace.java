package fractalzoomer.functions.general;

import fractalzoomer.core.*;
import fractalzoomer.core.DeepReference;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValueWithFactor;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.ExtendedConvergentType;
import fractalzoomer.functions.root_finding_methods.newton.NewtonRootFindingMethod;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;

public class NewtonThirdDegreeParameterSpace extends ExtendedConvergentType {

    private static Complex Csqr;
    private static MantExpComplex CsqrDeep;
    private static Complex C4;
    private static MantExpComplex C4Deep;
    private static Complex C2;
    private static MantExpComplex C2Deep;
    private static Complex CsqrS3;
    private static MantExpComplex CsqrS3Deep;
    private static Complex Csqr2S3;
    private static MantExpComplex Csqr2S3Deep;

    public NewtonThirdDegreeParameterSpace() {
        super();
    }

    public NewtonThirdDegreeParameterSpace(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        power = 3;
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

    public NewtonThirdDegreeParameterSpace(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        switch (out_coloring_algorithm) {
            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-9;
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-9;
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                convergent_bailout = 1E-7;
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        power = 3;
        defaultInitVal = new DefaultInitialValueWithFactor(1.0 / 3.0);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
    }

        //orbit
    public NewtonThirdDegreeParameterSpace(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex > complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        power = 3;
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

    public NewtonThirdDegreeParameterSpace(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
        power = 3;
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

        return !isJulia || (isJulia && !juliter);
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + seed : "");
    }

    @Override
    public void calculateReferencePoint(GenericComplex gpixel, Apfloat size, boolean deepZoom, int iterations, Location externalLocation, JProgressBar progress) {

        long time = System.currentTimeMillis();
        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        if(iterations == 0) {
            Reference = new double[max_iterations << 1];

            if(isJulia) {
                ReferenceSubPixel = new double[max_iterations << 1];
            }
            else {
                ReferenceSubCp = new double[max_iterations << 1];
            }

            PrecalculatedTerms = new double[max_iterations << 1];
            PrecalculatedTerms2 = new double[max_iterations << 1];

            if (deepZoom) {
                ReferenceDeep = new DeepReference(max_iterations);

                if(isJulia) {
                    ReferenceSubPixelDeep = new DeepReference(max_iterations);
                }
                else {
                    ReferenceSubCpDeep = new DeepReference(max_iterations);
                }

                PrecalculatedTermsDeep = new DeepReference(max_iterations);
                PrecalculatedTerms2Deep = new DeepReference(max_iterations);
            }
        }
        else if (max_iterations > getReferenceLength()){
            Reference = Arrays.copyOf(Reference, max_iterations << 1);

            if(isJulia) {
                ReferenceSubPixel = Arrays.copyOf(ReferenceSubPixel, max_iterations << 1);
            }
            else {
                ReferenceSubCp = Arrays.copyOf(ReferenceSubCp, max_iterations << 1);
            }

            PrecalculatedTerms = Arrays.copyOf(PrecalculatedTerms, max_iterations << 1);
            PrecalculatedTerms2 = Arrays.copyOf(PrecalculatedTerms2, max_iterations << 1);

            if (deepZoom) {
                ReferenceDeep.resize(max_iterations);

                if(isJulia) {
                    ReferenceSubPixelDeep.resize(max_iterations);
                }
                else {
                    ReferenceSubCpDeep.resize(max_iterations);
                }

                PrecalculatedTermsDeep.resize(max_iterations);
                PrecalculatedTerms2Deep.resize(max_iterations);
            }

            System.gc();
        }

        BigComplex pixel = (BigComplex)gpixel;

        BigComplex initVal = pixel.divide(new MyApfloat(3.0));

        BigComplex z = iterations == 0 ? (isJulia ? pixel : initVal) : (BigComplex)lastZValue;

        BigComplex c = isJulia ? new BigComplex(seed) : pixel;

        BigComplex zold = iterations == 0 ? new BigComplex() : (BigComplex)secondTolastZValue;
        BigComplex zold2 = iterations == 0 ? new BigComplex() : (BigComplex)thirdTolastZValue;
        BigComplex start = isJulia ? pixel : initVal;
        BigComplex c0 = c;

        Location loc = new Location();

        refPoint = pixel;

        refPointSmall = refPoint.toComplex();
        C = c.toComplex();

        Apfloat three = new MyApfloat(3.0);

        BigComplex c4big = c.times(new MyApfloat(4.0));
        C4 = c4big.toComplex();

        BigComplex c2big = c.times(new MyApfloat(2.0));
        C2 = c2big.toComplex();

        BigComplex csqrbig = c.square();
        Csqr = csqrbig.toComplex();

        BigComplex csqrs3big = csqrbig.sub(three);
        CsqrS3 = csqrs3big.toComplex();

        BigComplex csqr2s3big = csqrbig.times(MyApfloat.TWO).sub(three);
        Csqr2S3 = csqr2s3big.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
            Cdeep = loc.getMantExpComplex(c);
            C4Deep = loc.getMantExpComplex(c4big);
            C2Deep =  loc.getMantExpComplex(c2big);
            CsqrDeep =  loc.getMantExpComplex(csqrbig);
            CsqrS3Deep = loc.getMantExpComplex(csqrs3big);
            Csqr2S3Deep = loc.getMantExpComplex(csqr2s3big);
        }

        RefType = getRefType();

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            setArrayValue(Reference, iterations, cz);

            BigComplex zsubcp = null;
            BigComplex zsubpixel = null;
            if(isJulia) {
                zsubpixel = z.sub(pixel);
                setArrayValue(ReferenceSubPixel, iterations, zsubpixel.toComplex());
            }
            else {
                zsubcp = z.sub(initVal);
                setArrayValue(ReferenceSubCp, iterations, zsubcp.toComplex());
            }

            BigComplex zsqr = z.square();

            BigComplex preCalc = z.fourth().sub(zsqr.times(MyApfloat.TWO)).plus(MyApfloat.ONE);  // //Z^4-2*Z^2+1 for catastrophic cancelation

            setArrayValue(PrecalculatedTerms, iterations, preCalc.toComplex());

            BigComplex zsqr3 = zsqr.times(three);

            BigComplex preCalc2 = zsqr3.negative().plus(MyApfloat.ONE);

            setArrayValue(PrecalculatedTerms2, iterations, preCalc2.toComplex());

            if(deepZoom) {
                setArrayDeepValue(ReferenceDeep, iterations, loc.getMantExpComplex(z));

                if(isJulia) {
                    setArrayDeepValue(ReferenceSubPixelDeep, iterations, loc.getMantExpComplex(zsubpixel));
                }
                else {
                    setArrayDeepValue(ReferenceSubCpDeep, iterations, loc.getMantExpComplex(zsubcp));
                }

                setArrayDeepValue(PrecalculatedTermsDeep, iterations, loc.getMantExpComplex(preCalc));
                setArrayDeepValue(PrecalculatedTerms2Deep, iterations, loc.getMantExpComplex(preCalc2));
            }

            if (iterations > 0 && convergent_bailout_algorithm.converged(z, zold, zold2, iterations, c, start, c0, pixel)) {
                break;
            }

            zold2 = zold;
            zold = z;


            try {
                z = z.sub(z.sub(MyApfloat.ONE).times(z.plus(MyApfloat.ONE)).times(z.sub(c)).divide(c.times(z).times(MyApfloat.TWO).negative().plus(zsqr3).sub(MyApfloat.ONE)));
            }
            catch (Exception ex) {
                break;
            }

            if(progress != null && iterations % 1000 == 0) {
                progress.setValue(iterations - initIterations);
                progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (iterations - initIterations) / progress.getMaximum() * 100)) + "%");
            }

        }

        lastZValue = z;
        secondTolastZValue = zold;
        thirdTolastZValue = zold2;

        MaxRefIteration = iterations - 1;

        skippedIterations = 0;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }
        ReferenceCalculationTime = System.currentTimeMillis() - time;
    }



    /*

        (2*(2*C*Z - 3*Z^2 + 1)*z^3 + (12*C*Z^2 - 12*Z^3 - 2*(C^2 - 3)*Z - (2*C*Z - 3*Z^2 + 1)*c - 4*C)*z^2 - (Z^4 - 2*Z^2 + 1)*c + 2*(4*C*Z^3 - 3*Z^4 - (C^2 - 3)*Z^2 + C^2 - 4*C*Z - (C*Z^2 - Z^3 - C + Z)*c)*z)/(12*C*Z^3 - 9*Z^4 - 2*(2*C^2 - 3)*Z^2 + 3*(2*C*Z - 3*Z^2 + 1)*z^2 - 4*C*Z - 2*(2*C*Z^2 - 3*Z^3 + Z)*c + 2*(9*C*Z^2 - 9*Z^3 - (2*C^2 - 3)*Z - (2*C*Z - 3*Z^2 + 1)*c - C)*z - 1)
     */
    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex Z = getArrayValue(Reference, RefIteration);
        Complex z = DeltaSubN;
        Complex c = DeltaSub0;

        Complex Zsqr = Z.square();
        Complex zsqr = z.square();
        Complex Zcube = Z.cube();

        Complex temp10 = getArrayValue(PrecalculatedTerms2, RefIteration);
        Complex temp8 = C.times(Zsqr);
        Complex temp1 = C2.times(Z).plus_mutable(temp10);
        Complex temp2 = temp8.sub(Zcube);
        Complex temp5 = Zcube.times(C4).sub_mutable(Z.fourth().times_mutable(3));
        Complex temp6 = C4.times(Z);
        Complex temp9 = temp1.times(c);

        Complex num = temp1.times(2).times_mutable(z.cube())
                .plus_mutable(temp2.times(12).sub_mutable(CsqrS3.times(2).times_mutable(Z)).sub_mutable(temp9).sub_mutable(C4).times_mutable(zsqr))
                 .sub_mutable(getArrayValue(PrecalculatedTerms, RefIteration).times_mutable(c))
                .plus_mutable(temp5.sub(CsqrS3.times(Zsqr)).plus_mutable(Csqr).sub_mutable(temp6).sub_mutable(temp2.sub(C).plus_mutable(Z).times_mutable(c)).times_mutable(2).times_mutable(z));


        Complex denom = temp5.times(3)
                .sub_mutable(Csqr2S3.times(Zsqr).times_mutable(2))
                .plus_mutable(temp1.times(zsqr).times_mutable(3))
                .sub_mutable(temp6)
                .sub_mutable(temp8.times(2).plus_mutable(temp10.times(Z)).times_mutable(c).times_mutable(2))
                .plus_mutable(temp2.times(9).sub_mutable(Csqr2S3.times(Z)).sub_mutable(temp9).sub_mutable(C).times_mutable(z).times_mutable(2))
                .sub_mutable(1);


        return num.divide_mutable(denom);


    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {
        MantExpComplex Z = getArrayDeepValue(ReferenceDeep, RefIteration);
        MantExpComplex z = DeltaSubN;
        MantExpComplex c = DeltaSub0;

        MantExpComplex Zsqr = Z.square();
        MantExpComplex zsqr = z.square();
        MantExpComplex Zcube = Z.cube();

        MantExpComplex temp10 = getArrayDeepValue(PrecalculatedTerms2Deep, RefIteration);
        MantExpComplex temp8 = Cdeep.times(Zsqr);
        MantExpComplex temp1 = C2Deep.times(Z).plus_mutable(temp10);
        MantExpComplex temp2 = temp8.sub(Zcube);
        MantExpComplex temp5 = Zcube.times(C4Deep).sub_mutable(Z.fourth().times_mutable(MantExp.THREE));
        MantExpComplex temp6 = C4Deep.times(Z);
        MantExpComplex temp9 = temp1.times(c);

        MantExpComplex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(temp2.times(MantExp.TWELVE).sub_mutable(CsqrS3Deep.times2().times_mutable(Z)).sub_mutable(temp9).sub_mutable(C4Deep).times_mutable(zsqr))
                .sub_mutable(getArrayDeepValue(PrecalculatedTermsDeep, RefIteration).times_mutable(c))
                .plus_mutable(temp5.sub(CsqrS3Deep.times(Zsqr)).plus_mutable(CsqrDeep).sub_mutable(temp6).sub_mutable(temp2.sub(Cdeep).plus_mutable(Z).times_mutable(c)).times2_mutable().times_mutable(z));


        MantExpComplex denom = temp5.times(MantExp.THREE)
                .sub_mutable(Csqr2S3Deep.times(Zsqr).times2_mutable())
                .plus_mutable(temp1.times(zsqr).times_mutable(MantExp.THREE))
                .sub_mutable(temp6)
                .sub_mutable(temp8.times2().plus_mutable(temp10.times(Z)).times_mutable(c).times2_mutable())
                .plus_mutable(temp2.times(MantExp.NINE).sub_mutable(Csqr2S3Deep.times(Z)).sub_mutable(temp9).sub_mutable(Cdeep).times_mutable(z).times2_mutable())
                .sub_mutable(MantExp.ONE);


        return num.divide_mutable(denom);
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {
        Complex Z = getArrayValue(Reference, RefIteration);
        Complex z = DeltaSubN;

        Complex Zsqr = Z.square();
        Complex zsqr = z.square();
        Complex Zcube = Z.cube();

        Complex temp10 = getArrayValue(PrecalculatedTerms2, RefIteration);
        Complex temp8 = C.times(Zsqr);
        Complex temp1 = C2.times(Z).plus_mutable(temp10);
        Complex temp2 = temp8.sub(Zcube);
        Complex temp5 = Zcube.times(C4).sub_mutable(Z.fourth().times_mutable(3));
        Complex temp6 = C4.times(Z);

        Complex num = temp1.times(2).times_mutable(z.cube())
                .plus_mutable(temp2.times(12).sub_mutable(CsqrS3.times(2).times_mutable(Z)).sub_mutable(C4).times_mutable(zsqr))
                .plus_mutable(temp5.sub(CsqrS3.times(Zsqr)).plus_mutable(Csqr).sub_mutable(temp6).times_mutable(2).times_mutable(z));


        Complex denom = temp5.times(3)
                .sub_mutable(Csqr2S3.times(Zsqr).times_mutable(2))
                .plus_mutable(temp1.times(zsqr).times_mutable(3))
                .sub_mutable(temp6)
                .plus_mutable(temp2.times(9).sub_mutable(Csqr2S3.times(Z)).sub_mutable(C).times_mutable(z).times_mutable(2))
                .sub_mutable(1);


        return num.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {
        MantExpComplex Z = getArrayDeepValue(ReferenceDeep, RefIteration);
        MantExpComplex z = DeltaSubN;

        MantExpComplex Zsqr = Z.square();
        MantExpComplex zsqr = z.square();
        MantExpComplex Zcube = Z.cube();

        MantExpComplex temp10 = getArrayDeepValue(PrecalculatedTerms2Deep, RefIteration);
        MantExpComplex temp8 = Cdeep.times(Zsqr);
        MantExpComplex temp1 = C2Deep.times(Z).plus_mutable(temp10);
        MantExpComplex temp2 = temp8.sub(Zcube);
        MantExpComplex temp5 = Zcube.times(C4Deep).sub_mutable(Z.fourth().times_mutable(MantExp.THREE));
        MantExpComplex temp6 = C4Deep.times(Z);

        MantExpComplex num = temp1.times2().times_mutable(z.cube())
                .plus_mutable(temp2.times(MantExp.TWELVE).sub_mutable(CsqrS3Deep.times2().times_mutable(Z)).sub_mutable(C4Deep).times_mutable(zsqr))
                .plus_mutable(temp5.sub(CsqrS3Deep.times(Zsqr)).plus_mutable(CsqrDeep).sub_mutable(temp6).times2_mutable().times_mutable(z));


        MantExpComplex denom = temp5.times(MantExp.THREE)
                .sub_mutable(Csqr2S3Deep.times(Zsqr).times2_mutable())
                .plus_mutable(temp1.times(zsqr).times_mutable(MantExp.THREE))
                .sub_mutable(temp6)
                .plus_mutable(temp2.times(MantExp.NINE).sub_mutable(Csqr2S3Deep.times(Z)).sub_mutable(Cdeep).times_mutable(z).times2_mutable())
                .sub_mutable(MantExp.ONE);


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

            if(skippedIterations != 0) {

                complex[0] = (Complex) initializeFromSeries(dpixel);

            }
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
            complex[0] = dpixel.divide(new MantExp(3.0));
            complex[1] = new MantExpComplex(dpixel);

            if(skippedIterations != 0) {

                complex[0] = (MantExpComplex) initializeFromSeries(dpixel);

            }
        }

        return complex;

    }

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {
        return z.sub(1).times_mutable(z.plus(1)).times_mutable(z.sub(c));
    }
}
