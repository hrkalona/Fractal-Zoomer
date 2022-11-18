/* 
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.functions.lambda;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;

/**
 *
 * @author hrkalona
 */
public class Lambda extends Julia {

    public Lambda() {
        super();
    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        defaultInitVal = new InitialValue(0.5, 0);

        power = 2;

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
            init_val = defaultInitVal;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        power = 2;

        defaultInitVal = new InitialValue(0.5, 0);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
    }

    //orbit
    public Lambda(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        power = 2;

        defaultInitVal = new InitialValue(0.5, 0);

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
            init_val = defaultInitVal;
        }

    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
        defaultInitVal = new InitialValue(0.5, 0);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
        power = 2;
    }

    @Override
    public void function(Complex[] complex) {

        complex[0].times_mutable(complex[1].times(complex[0].r_sub(1)));

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
        return super.getRefType() + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public void calculateReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int iterations, int iterations2, Location externalLocation, JProgressBar progress) {

        long time = System.currentTimeMillis();

        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        if (iterations == 0) {
            Reference = new double[max_iterations << 1];
            ReferenceSubCp = new double[max_iterations << 1];
            PrecalculatedTerms = new double[max_iterations << 1];

            if(!isJulia) {
                PrecalculatedTerms2 = new double[max_iterations << 1];
            }

            if (deepZoom) {
                ReferenceDeep = new DeepReference(max_iterations);
                ReferenceSubCpDeep = new DeepReference(max_iterations);
                PrecalculatedTermsDeep = new DeepReference(max_iterations);

                if(!isJulia) {
                    PrecalculatedTerms2Deep = new DeepReference(max_iterations);
                }
            }
        } else if (max_iterations > getReferenceLength()) {
            Reference = Arrays.copyOf(Reference, max_iterations << 1);
            ReferenceSubCp = Arrays.copyOf(ReferenceSubCp, max_iterations << 1);
            PrecalculatedTerms = Arrays.copyOf(PrecalculatedTerms, max_iterations << 1);

            if(!isJulia) {
                PrecalculatedTerms2 = Arrays.copyOf(PrecalculatedTerms2, max_iterations << 1);
            }

            if (deepZoom) {
                ReferenceDeep.resize(max_iterations);
                ReferenceSubCpDeep.resize(max_iterations);
                PrecalculatedTermsDeep.resize(max_iterations);

                if(!isJulia) {
                    PrecalculatedTerms2Deep.resize(max_iterations);
                }
            }
        }

        GenericComplex z, c, zold, zold2, start, c0, initVal, pixel;
        Object normSquared;

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;

        if(useBignum) {

            if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
                initVal = new BigNumComplex(defaultInitVal.getValue(null));

                BigNumComplex bn = inputPixel.toBigNumComplex();
                z = iterations == 0 ? (isJulia ? bn : initVal) : lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new BigNumComplex() : secondTolastZValue;
                zold2 = iterations == 0 ? new BigNumComplex() : thirdTolastZValue;
                start = isJulia ? bn : initVal;
                c0 = c;
                pixel = bn;
            }
            else if(bigNumLib == Constants.BIGNUM_MPFR) {
                initVal = new MpfrBigNumComplex(defaultInitVal.getValue(null));

                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? (isJulia ? bn : new MpfrBigNumComplex((MpfrBigNumComplex)initVal)) : lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new MpfrBigNumComplex() : secondTolastZValue;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : thirdTolastZValue;
                start = isJulia ? new MpfrBigNumComplex(bn) : new MpfrBigNumComplex((MpfrBigNumComplex)initVal);
                c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
                pixel = new MpfrBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                initVal = new DDComplex(defaultInitVal.getValue(null));

                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? (isJulia ? ddn : initVal) : lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : ddn;
                zold = iterations == 0 ? new DDComplex() : secondTolastZValue;
                zold2 = iterations == 0 ? new DDComplex() : thirdTolastZValue;
                start = isJulia ? ddn : initVal;
                c0 = c;
                pixel = ddn;
            }
            else {
                initVal = defaultInitVal.getValue(null);

                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? (isJulia ? bn : new Complex((Complex)initVal)) : lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new Complex() : secondTolastZValue;
                zold2 = iterations == 0 ? new Complex() : thirdTolastZValue;
                start = isJulia ? new Complex(bn) : new Complex((Complex)initVal);
                c0 = new Complex((Complex) c);
                pixel = new Complex(bn);
            }
        }
        else {
            initVal = new BigComplex(defaultInitVal.getValue(null));

            z = iterations == 0 ? (isJulia ? inputPixel : initVal) : lastZValue;
            c = isJulia ? getSeed(useBignum, bigNumLib) : inputPixel;
            zold = iterations == 0 ? new BigComplex() : secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : thirdTolastZValue;
            start = isJulia ? inputPixel : initVal;
            c0 = c;
            pixel = inputPixel;
        }

        normSquared = z.normSquared();

        Location loc = new Location();

        refPoint = inputPixel;
        refPointSmall = refPoint.toComplex();

        C = c.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
            Cdeep = loc.getMantExpComplex(c);
        }

        RefType = getRefType();

        boolean preCalcNormData = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        NormComponents normData = null;

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            setArrayValue(Reference, iterations, cz);

            GenericComplex zsubcp = z.sub(initVal);
            setArrayValue(ReferenceSubCp, iterations, zsubcp.toComplex());

            GenericComplex preCalc;

            if(useBignum) {
                preCalc = z.times2().sub_mutable(1); //2*Z-1 for catastrophic cancelation
            }
            else {
                preCalc = z.times2().sub(MyApfloat.ONE); //2*Z-1 for catastrophic cancelation
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            GenericComplex preCalc2 = null;

            if(preCalcNormData) {
                if(useBignum && bigNumLib == Constants.BIGNUM_MPFR) {
                    preCalc2 = z.sub(z.squareFast_non_mutable(normData)); //Z-Z^2 for catastrophic cancelation
                }
                else {
                    preCalc2 = z.sub(z.squareFast(normData)); //Z-Z^2 for catastrophic cancelation
                }
            }
            else {
                preCalc2 = z.sub(z.square()); //Z-Z^2 for catastrophic cancelation
            }

            setArrayValue(PrecalculatedTerms, iterations, preCalc.toComplex());

            if(!isJulia) {
                setArrayValue(PrecalculatedTerms2, iterations, preCalc2.toComplex());
            }

            if(deepZoom) {
                setArrayDeepValue(ReferenceDeep, iterations, loc.getMantExpComplex(z));
                setArrayDeepValue(ReferenceSubCpDeep, iterations, loc.getMantExpComplex(zsubcp));
                setArrayDeepValue(PrecalculatedTermsDeep, iterations, loc.getMantExpComplex(preCalc));

                if(!isJulia) {
                    setArrayDeepValue(PrecalculatedTerms2Deep, iterations, loc.getMantExpComplex(preCalc2));
                }
            }

            if (iterations > 0 && bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                z = preCalc2.times_mutable(c);
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

        if(isJulia) {
            calculateJuliaReferencePoint(inputPixel, size, deepZoom, iterations2, progress);
        }

    }

    @Override
    protected void calculateJuliaReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int iterations, JProgressBar progress) {

        if(iterations == 0 && ((!deepZoom && SecondReference != null) || (deepZoom && SecondReferenceDeep != null))) {
            return;
        }

        long time = System.currentTimeMillis();

        int max_ref_iterations = getReferenceMaxIterations();

        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_ref_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        if (iterations == 0) {
            SecondReference = new double[max_ref_iterations << 1];
            SecondReferenceSubCp = new double[max_ref_iterations << 1];
            SecondPrecalculatedTerms = new double[max_ref_iterations << 1];

            if (deepZoom) {
                SecondReferenceDeep = new DeepReference(max_ref_iterations);
                SecondReferenceSubCpDeep = new DeepReference(max_ref_iterations);
                SecondPrecalculatedTermsDeep = new DeepReference(max_ref_iterations);
            }
        } else if (max_ref_iterations > getSecondReferenceLength()) {
            SecondReference = Arrays.copyOf(SecondReference, max_ref_iterations << 1);
            SecondReferenceSubCp = Arrays.copyOf(SecondReferenceSubCp, max_ref_iterations << 1);
            SecondPrecalculatedTerms = Arrays.copyOf(SecondPrecalculatedTerms, max_ref_iterations << 1);

            if (deepZoom) {
                SecondReferenceDeep.resize(max_ref_iterations);
                SecondReferenceSubCpDeep.resize(max_ref_iterations);
                SecondPrecalculatedTermsDeep.resize(max_ref_iterations);
            }
        }

        Location loc = new Location();

        GenericComplex z, c, zold, zold2, start, c0, pixel, initVal;
        Object normSquared;

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;

        if(useBignum) {

            if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
                initVal = new BigNumComplex(defaultInitVal.getValue(null));

                BigNumComplex bn = inputPixel.toBigNumComplex();
                z = iterations == 0 ? initVal : lastZ2Value;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new BigNumComplex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new BigNumComplex() : thirdTolastZ2Value;
                start = initVal;
                c0 = c;
                pixel = bn;
            }
            else if(bigNumLib == Constants.BIGNUM_MPFR) {
                initVal = new MpfrBigNumComplex(defaultInitVal.getValue(null));

                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? new MpfrBigNumComplex((MpfrBigNumComplex)initVal) : lastZ2Value;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new MpfrBigNumComplex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : thirdTolastZ2Value;
                start = new MpfrBigNumComplex((MpfrBigNumComplex)initVal);
                c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
                pixel = new MpfrBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                initVal = new DDComplex(defaultInitVal.getValue(null));

                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? initVal : lastZ2Value;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new DDComplex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new DDComplex() : thirdTolastZ2Value;
                start = initVal;
                c0 = c;
                pixel = ddn;
            }
            else {
                initVal = defaultInitVal.getValue(null);

                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? new Complex((Complex)initVal) : lastZ2Value;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new Complex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new Complex() : thirdTolastZ2Value;
                start = new Complex((Complex)initVal);
                c0 = new Complex((Complex) c);
                pixel = new Complex(bn);
            }
        }
        else {
            initVal = new BigComplex(defaultInitVal.getValue(null));

            z = iterations == 0 ? initVal : lastZ2Value;
            c = getSeed(useBignum, bigNumLib);
            zold = iterations == 0 ? new BigComplex() : secondTolastZ2Value;
            zold2 = iterations == 0 ? new BigComplex() : thirdTolastZ2Value;
            start = initVal;
            c0 = c;
            pixel = inputPixel;
        }

        normSquared = z.normSquared();

        boolean preCalcNormData = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        NormComponents normData = null;

        for (; iterations < max_ref_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            setArrayValue(SecondReference, iterations, cz);

            GenericComplex zsubcp = z.sub(initVal);
            setArrayValue(SecondReferenceSubCp, iterations, zsubcp.toComplex());

            GenericComplex preCalc;

            if(useBignum) {
                preCalc = z.times2().sub_mutable(1); //2*Z-1 for catastrophic cancelation
            }
            else {
                preCalc = z.times2().sub(MyApfloat.ONE); //2*Z-1 for catastrophic cancelation
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            GenericComplex preCalc2 = null;

            if(preCalcNormData) {
                if(useBignum && bigNumLib == Constants.BIGNUM_MPFR) {
                    preCalc2 = z.sub(z.squareFast_non_mutable(normData));
                }
                else {
                    preCalc2 = z.sub(z.squareFast(normData));
                }
            }
            else {
                preCalc2 = z.sub(z.square());
            }

            setArrayValue(SecondPrecalculatedTerms, iterations, preCalc.toComplex());

            if(deepZoom) {
                setArrayDeepValue(SecondReferenceDeep, iterations, loc.getMantExpComplex(z));
                setArrayDeepValue(SecondReferenceSubCpDeep, iterations, loc.getMantExpComplex(zsubcp));
                setArrayDeepValue(SecondPrecalculatedTermsDeep, iterations, loc.getMantExpComplex(preCalc));
            }

            if (iterations > 0 && bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                z = preCalc2.times_mutable(c);
            }
            catch (Exception ex) {
                break;
            }

            if(progress != null && iterations % 1000 == 0) {
                progress.setValue(iterations - initIterations);
                progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (iterations - initIterations) / progress.getMaximum() * 100)) + "%");
            }

        }

        lastZ2Value = z;
        secondTolastZ2Value = zold;
        thirdTolastZ2Value = zold2;

        MaxRef2Iteration = iterations - 1;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        SecondReferenceCalculationTime = System.currentTimeMillis() - time;
    }


    //(-z^2 - (2*Z - 1)*z)*C + (-z^2 - Z^2 + Z - (2*Z-1)*z) * c
    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        /*Complex X = Reference[RefIteration];

        Complex DnSqrPlustwoDnXn = DeltaSubN.square().plus_mutable(DeltaSubN.times(2).times_mutable(X));

        return DeltaSub0.negative().times(DnSqrPlustwoDnXn.plus(X.square()).sub_mutable(0.25))
                .sub_mutable(refPointSmall.times(DnSqrPlustwoDnXn));*/

        Complex c = DeltaSub0;
        Complex z = DeltaSubN;

        Complex temp = z.plus(getArrayValue(PrecalculatedTerms, RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(C).plus_mutable(getArrayValue(PrecalculatedTerms2, RefIteration).plus_mutable(temp).times_mutable(c));



    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex c = DeltaSub0;
        MantExpComplex z = DeltaSubN;

        MantExpComplex temp = z.plus(getArrayDeepValue(PrecalculatedTermsDeep, RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep).plus_mutable(getArrayDeepValue(PrecalculatedTerms2Deep, RefIteration).plus_mutable(temp).times_mutable(c));
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex z = DeltaSubN;

        Complex temp = z.plus(getArrayValue(PrecalculatedTerms, RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(C);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex z = DeltaSubN;

        MantExpComplex temp = z.plus(getArrayDeepValue(PrecalculatedTermsDeep, RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep);
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, double[] Reference, double[] PrecalculatedTerms, double[] PrecalculatedTerms2, int RefIteration) {

        Complex z = DeltaSubN;

        Complex temp = z.plus(getArrayValue(PrecalculatedTerms, RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(C);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, DeepReference ReferenceDeep, DeepReference PrecalculatedTermsDeep, DeepReference PrecalculatedTerms2Deep, int RefIteration) {

        MantExpComplex z = DeltaSubN;

        MantExpComplex temp = z.plus(getArrayDeepValue(PrecalculatedTermsDeep, RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep);
    }



    @Override
    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, Complex dpixel) {


        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : skippedIterations;
        int RefIteration = iterations;

        int ReferencePeriod = iterationPeriod;

        Complex zWithoutInitVal = new Complex();

        if(iterations != 0 && RefIteration < MaxRefIteration) {
            complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
        }
        else if(iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
        }

        Complex pixel = dpixel.plus(refPointSmall);

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }

            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

            RefIteration++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                zWithoutInitVal = getArrayValue(ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }

    @Override
    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, MantExpComplex dpixel) {

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : skippedIterations;
        int RefIteration = iterations;

        int ReferencePeriod = iterationPeriod;

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Reduce();
        long exp = DeltaSubN.getExp();

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        boolean useFullFloatExp = ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM;

        boolean usedDeepCode = false;

        if(useFullFloatExp || (skippedIterations == 0 && exp <= minExp) || (skippedIterations != 0 && exp <= reducedExp)) {
            usedDeepCode = true;
            MantExpComplex z = new MantExpComplex();
            if(iterations != 0 && RefIteration < MaxRefIteration) {
                z = getArrayDeepValue(ReferenceSubCpDeep, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
            }
            else if(iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                z = getArrayDeepValue(ReferenceSubCpDeep, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
            }
            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (useFullFloatExp && bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                if (max_iterations > 1) {
                    z = getArrayDeepValue(ReferenceSubCpDeep, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (z.norm_squared().compareTo(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;
                }

                DeltaSubN.Reduce();

                if(!useFullFloatExp) {
                    if (DeltaSubN.getExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!useFullFloatExp) {
            Complex CDeltaSubN = DeltaSubN.toComplex();
            Complex CDeltaSub0 = DeltaSub0.toComplex();

            boolean isZero = CDeltaSub0.isZero();
            Complex zWithoutInitVal = new Complex();

            if(!usedDeepCode && iterations != 0 && RefIteration < MaxRefIteration) {
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
            }
            else if(!usedDeepCode && iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
            }

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                if (isZero) {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, RefIteration);
                } else {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, CDeltaSub0, RefIteration);
                }

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    zWithoutInitVal = getArrayValue(ReferenceSubCp, RefIteration).plus_mutable(CDeltaSubN);
                    complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
                }

            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }

    @Override
    public double iterateJuliaWithPerturbationWithoutPeriodicity(Complex[] complex, Complex dpixel) {

        iterations = 0;

        int RefIteration = iterations;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z

        Complex pixel = dpixel.plus(refPointSmall);

        int MaxRefIteration = Fractal.MaxRefIteration;
        double[] Reference = Fractal.Reference;
        double[] ReferenceSubCp = Fractal.ReferenceSubCp;
        double[] PrecalculatedTerms = Fractal.PrecalculatedTerms;

        Complex zWithoutInitVal = new Complex();

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }

            DeltaSubN = perturbationFunction(DeltaSubN, Reference, PrecalculatedTerms, null, RefIteration);

            RefIteration++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                zWithoutInitVal = getArrayValue(ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;

                MaxRefIteration = Fractal.MaxRef2Iteration;
                Reference = Fractal.SecondReference;
                ReferenceSubCp = Fractal.SecondReferenceSubCp;
                PrecalculatedTerms = Fractal.SecondPrecalculatedTerms;
            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }

    @Override
    public double iterateJuliaWithPerturbationWithoutPeriodicity(Complex[] complex, MantExpComplex dpixel) {


        iterations = 0;

        int RefIteration = iterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        int MaxRefIteration = Fractal.MaxRefIteration;
        DeepReference ReferenceDeep = Fractal.ReferenceDeep;
        DeepReference ReferenceSubCpDeep = Fractal.ReferenceSubCpDeep;
        DeepReference PrecalculatedTermsDeep = Fractal.PrecalculatedTermsDeep;

        double[] Reference = Fractal.Reference;
        double[] ReferenceSubCp = Fractal.ReferenceSubCp;
        double[] PrecalculatedTerms = Fractal.PrecalculatedTerms;

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Reduce();
        long exp = DeltaSubN.getExp();

        boolean useFullFloatExp = ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM;

        if(useFullFloatExp || (skippedIterations == 0 && exp <= minExp) || (skippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex z = getArrayDeepValue(ReferenceSubCpDeep, RefIteration).plus_mutable(DeltaSubN);

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (useFullFloatExp && bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                DeltaSubN = perturbationFunction(DeltaSubN, ReferenceDeep, PrecalculatedTermsDeep, null, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                if (max_iterations > 1) {
                    z = getArrayDeepValue(ReferenceSubCpDeep, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (z.norm_squared().compareTo(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;

                    ReferenceDeep = Fractal.SecondReferenceDeep;
                    ReferenceSubCpDeep = Fractal.SecondReferenceSubCpDeep;
                    PrecalculatedTermsDeep = Fractal.SecondPrecalculatedTermsDeep;

                    MaxRefIteration = MaxRef2Iteration;

                    Reference = Fractal.SecondReference;
                    ReferenceSubCp = Fractal.SecondReferenceSubCp;
                    PrecalculatedTerms = Fractal.SecondPrecalculatedTerms;
                }

                DeltaSubN.Reduce();

                if(!useFullFloatExp) {
                    if (DeltaSubN.getExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!useFullFloatExp) {
            Complex CDeltaSubN = DeltaSubN.toComplex();

            Complex zWithoutInitVal = new Complex();

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                CDeltaSubN = perturbationFunction(CDeltaSubN, Reference, PrecalculatedTerms, null, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    zWithoutInitVal = getArrayValue(ReferenceSubCp, RefIteration).plus_mutable(CDeltaSubN);
                    complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
                    Reference = Fractal.SecondReference;
                    ReferenceSubCp = Fractal.SecondReferenceSubCp;
                    PrecalculatedTerms = Fractal.SecondPrecalculatedTerms;
                    MaxRefIteration = Fractal.MaxRef2Iteration;
                }

            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }

    @Override
    public boolean supportsBignum() { return true;}

    @Override
    public boolean supportsMpfrBignum() { return true;}
}
