

package fractalzoomer.functions.root_finding_methods.newton;

import fractalzoomer.core.Complex;
import fractalzoomer.core.NumericLibrary;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.*;
import fractalzoomer.core.reference.DoubleReference;
import fractalzoomer.core.reference.ReferenceData;
import fractalzoomer.core.reference.ReferenceDeepData;
import fractalzoomer.core.reference.SerializableFunction;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;

/**
 *
 * @author hrkalona
 */
public class Newton3 extends NewtonRootFindingMethod {

    public Newton3(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations,  plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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
        
        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        defaultInitVal = new InitialValue(1, 0);
    }

    //orbit
    public Newton3(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        defaultInitVal = new InitialValue(1, 0);

    }
    
   

    @Override
    public void function(Complex[] complex) {

        Complex fz = complex[0].cube().sub_mutable(1);
        Complex dfz = complex[0].square().times_mutable(3);
        
        newtonMethod(complex[0], fz, dfz);

    }

    @Override
    public boolean supportsPerturbationTheory() {
        return true;
    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex Z = getReferenceValue(reference, RefIteration);

        Complex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getExpressionValue(referenceData.PrecalculatedTerms[0], RefIteration, Z)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex Z = getReferenceDeepValue(referenceDeep, RefIteration);

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getExpressionDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z)).sub_mutable(z.divide2()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {

        Complex Z = getReferenceValue(data.Reference, RefIteration);

        Complex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getExpressionValue(data.PrecalculatedTerms[0], RefIteration, Z)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {

        MantExpComplex Z = getReferenceDeepValue(data.Reference, RefIteration);

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getExpressionDeepValue(data.PrecalculatedTerms[0], RefIteration, Z)).sub_mutable(z.divide2()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
    }

    @Override
    public void function(GenericComplex[] complex) {
        if(complex[0] instanceof BigComplex) {
            complex[0] = complex[0].sub(complex[0].cube().sub(MyApfloat.ONE).divide(complex[0].square().times(MyApfloat.THREE)));
        }
        else {
            complex[0] = complex[0].sub_mutable(complex[0].cube().sub_mutable(1).divide_mutable(complex[0].square().times_mutable(3)));
        }
    }

    @Override
    protected int[] getNeededPrecalculatedTermsIndexes() {
        return new int[] {0};
    }

    @Override
    protected SerializableFunction[] getPrecalculatedTermsFunctions(Complex c) {
        SerializableFunction<Complex, Complex> f1 = x -> x.fourth().sub_mutable(x);
        return new SerializableFunction[] {f1};
    }

    @Override
    protected SerializableFunction[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        SerializableFunction<MantExpComplex, MantExpComplex> f1 = x -> x.fourth().sub_mutable(x);
        return new SerializableFunction[] {f1};
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

        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, false);
        DoubleReference.SHOULD_SAVE_MEMORY = false;
        boolean useCompressedRef = useCompressedRef();
        int[] preCalcIndexes = getNeededPrecalculatedTermsIndexes();
        boolean needsRefSubCp = needsRefSubCp();

        initializeReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, max_ref_iterations, needsRefSubCp, useCompressedRef, preCalcIndexes);

        inputPixel = getInputPixel(inputPixel);

        int bigNumLib = NumericLibrary.getBignumImplementation(size, this);

        GenericComplex z, zold, zold2, start, pixel, initVal;

        if(bigNumLib == Constants.BIGNUM_MPFR) {
            initVal = new MpfrBigNumComplex(defaultInitVal.getValue(null));
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            z = iterations == 0 ? bn : referenceOrbit.lastZValue;
            zold = iterations == 0 ? new MpfrBigNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceOrbit.thirdTolastZValue;
            start = new MpfrBigNumComplex(bn);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            initVal = new MpirBigNumComplex(defaultInitVal.getValue(null));
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? bn : referenceOrbit.lastZValue;
            zold = iterations == 0 ? new MpirBigNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceOrbit.thirdTolastZValue;
            start = new MpirBigNumComplex(bn);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            initVal = new DDComplex(defaultInitVal.getValue(null));
            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? ddn : referenceOrbit.lastZValue;
            zold = iterations == 0 ? new DDComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : referenceOrbit.thirdTolastZValue;
            start = ddn;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            initVal = new BigIntNumComplex(defaultInitVal.getValue(null));
            BigIntNumComplex bin = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? bin : referenceOrbit.lastZValue;
            zold = iterations == 0 ? new BigIntNumComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : referenceOrbit.thirdTolastZValue;
            start = bin;
            pixel = bin;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            initVal = new Complex(defaultInitVal.getValue(null));
            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? bn : referenceOrbit.lastZValue;
            zold = iterations == 0 ? new Complex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : referenceOrbit.thirdTolastZValue;
            start = new Complex(bn);
            pixel = new Complex(bn);
        }
        else {
            initVal = new BigComplex(defaultInitVal.getValue(null));
            z = iterations == 0 ? inputPixel : referenceOrbit.lastZValue;
            zold = iterations == 0 ? new BigComplex() : referenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceOrbit.thirdTolastZValue;
            start = inputPixel;
            pixel = inputPixel;
        }

        Location loc = new Location();

        referenceOrbit.refPoint = inputPixel;

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(referenceOrbit.refPoint);
            refPointSmall = refPointSmallDeep.toComplex();

            seedSmallDeep = MantExpComplex.create();

            if(lowPrecReferenceOrbitNeeded) {
                seedSmall = new Complex();
            }
        }
        else {
            refPointSmall = referenceOrbit.refPoint.toComplex();

            if(lowPrecReferenceOrbitNeeded) {
                seedSmall = new Complex();
            }
        }

        referenceOrbit.RefType = getRefType();

        if(useCompressedRef) {
            initializeCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, needsRefSubCp, preCalcIndexes, z, null, initVal, start);
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

            GenericComplex zcubes1;

            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                zcubes1 = z.cube().sub_mutable(1);
            }
            else {
                zcubes1 = z.cube().sub(MyApfloat.ONE);
            }

            GenericComplex preCalc;
            preCalc = zcubes1.times(z); //Z^4-Z for catastrophic cancelation

            MantExpComplex czm = null;
            MantExpComplex precalcM = null;
            MantExpComplex zsubcpm = null;

            if(deepZoom) {
                czm = loc.getMantExpComplex(z);
                if (czm.isInfinite() || czm.isNaN()) {
                    break;
                }
                tempmcz = setReferenceDeepValue(referenceDeep, iterations, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? czm.toComplex() : z.toComplex();
                if (cz.isInfinite() || cz.isNaN()) {
                    break;
                }

                cz = setReferenceValue(reference, iterations, cz);
            }

            czm = tempmcz;

            if(deepZoom) {
                precalcM = loc.getMantExpComplex(preCalc);
                zsubcpm = loc.getMantExpComplex(zsubcp);
                setExpressionDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, precalcM, czm);
                setExpressionDeepValue(referenceDeepData.ReferenceSubCp, iterations, zsubcpm, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                setExpressionValue(referenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalcM.toComplex() : preCalc.toComplex(), cz);
                setExpressionValue(referenceData.ReferenceSubCp, iterations, deepZoom ? zsubcpm.toComplex() : zsubcp.toComplex(), cz);
            }

            if (iterations > 0 && convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, pixel, start, pixel, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                    z = z.sub_mutable(zcubes1.divide_mutable(z.square().times_mutable(3)));
                }
                else {
                    z = z.sub(zcubes1.divide(z.square().times(MyApfloat.THREE)));
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
        referenceOrbit.secondTolastZValue = zold;
        referenceOrbit.thirdTolastZValue = zold2;

        referenceOrbit.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            finalizeCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, needsRefSubCp, preCalcIndexes);
        }

        SAskippedIterations = 0;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        if(TaskRender.SAVE_REFERENCE && supportsReferenceSavingOrLoading()) {
            saveReference(TaskRender.SAVE_REFERENCE_FILE_PATH);
        }

        ReferenceCalculationTime = System.currentTimeMillis() - time;

        calculateSecondReferencePoint(inputPixel, size, deepZoom, juliaIterations, progress);

    }

    protected void calculateSecondReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int[] juliaIterations, JProgressBar progress) {

        int iterations = juliaIterations[0];
        if(iterations == 0 && ((!deepZoom && secondReferenceData.Reference != null) || (deepZoom && secondReferenceDeepData.Reference != null))) {
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

        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, false);
        boolean useCompressedRef = useCompressedRef();
        int[] preCalcIndexes = getNeededPrecalculatedTermsIndexes();
        boolean needsRefSubCp = needsRefSubCp();

        initializeSecondReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, max_ref_iterations, needsRefSubCp, useCompressedRef, preCalcIndexes);

        Location loc = new Location();

        GenericComplex z, zold, zold2, start, pixel, initVal;

        int bigNumLib = NumericLibrary.getBignumImplementation(size, this);

        if(bigNumLib == Constants.BIGNUM_MPFR) {
            initVal = new MpfrBigNumComplex(defaultInitVal.getValue(null));
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = new MpfrBigNumComplex((MpfrBigNumComplex) initVal);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            initVal = new MpirBigNumComplex(defaultInitVal.getValue(null));
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new MpirBigNumComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = new MpirBigNumComplex((MpirBigNumComplex) initVal);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            initVal = new DDComplex(defaultInitVal.getValue(null));
            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new DDComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = initVal;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            initVal = new BigIntNumComplex(defaultInitVal.getValue(null));
            BigIntNumComplex bin = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new BigIntNumComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = initVal;
            pixel = bin;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            initVal = new Complex(defaultInitVal.getValue(null));
            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new Complex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : secondReferenceOrbit.thirdTolastZValue;
            start = new Complex((Complex) initVal);
            pixel = new Complex(bn);
        }
        else {
            initVal = new BigComplex(defaultInitVal.getValue(null));
            z = iterations == 0 ? initVal : secondReferenceOrbit.lastZValue;
            zold = iterations == 0 ? new BigComplex() : secondReferenceOrbit.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : secondReferenceOrbit.thirdTolastZValue;
            start = initVal;
            pixel = inputPixel;
        }

        if(useCompressedRef) {
            initializeSecondCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, iterations, needsRefSubCp, preCalcIndexes, z, null, initVal, start);
        }

        MantExpComplex tempmcz = null;
        Complex cz = null;

        for (; iterations < max_ref_iterations; iterations++) {

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

            GenericComplex zcubes1;

            if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                zcubes1 = z.cube().sub_mutable(1);
            }
            else {
                zcubes1 = z.cube().sub(MyApfloat.ONE);
            }

            GenericComplex preCalc;
            preCalc = zcubes1.times(z); //Z^4-Z for catastrophic cancelation

            MantExpComplex czm = null;
            MantExpComplex precalcm = null;
            MantExpComplex zsubcpm = null;
            if(deepZoom) {
                czm = loc.getMantExpComplex(z);
                if (czm.isInfinite() || czm.isNaN()) {
                    break;
                }
                tempmcz = setReferenceDeepValue(secondReferenceDeepData.Reference, iterations, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? czm.toComplex() : z.toComplex();
                if (cz.isInfinite() || cz.isNaN()) {
                    break;
                }

                cz = setReferenceValue(secondReferenceData.Reference, iterations, cz);
            }

            czm = tempmcz;

            if(deepZoom) {
                precalcm = loc.getMantExpComplex(preCalc);
                zsubcpm = loc.getMantExpComplex(zsubcp);
                setExpressionDeepValue(secondReferenceDeepData.PrecalculatedTerms[0], iterations, precalcm, czm);
                setExpressionDeepValue(secondReferenceDeepData.ReferenceSubCp, iterations, zsubcpm, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                setExpressionValue(secondReferenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalcm.toComplex() : preCalc.toComplex(), cz);
                setExpressionValue(secondReferenceData.ReferenceSubCp, iterations, deepZoom ? zsubcpm.toComplex() : zsubcp.toComplex(), cz);
            }


            if (iterations > 0 && convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, pixel, start, pixel, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                if(bigNumLib != Constants.BIGNUM_APFLOAT) {
                    z = z.sub_mutable(zcubes1.divide_mutable(z.square().times_mutable(3)));
                }
                else {
                    z = z.sub(zcubes1.divide(z.square().times(MyApfloat.THREE)));
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

        secondReferenceOrbit.lastZValue = z;
        secondReferenceOrbit.secondTolastZValue = zold;
        secondReferenceOrbit.thirdTolastZValue = zold2;

        secondReferenceOrbit.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            finalizeSecondCompressedReference(deepZoom, lowPrecReferenceOrbitNeeded, needsRefSubCp, preCalcIndexes);
        }

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        SecondReferenceCalculationTime = System.currentTimeMillis() - time;
    }

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {
        return z.cube().sub_mutable(1);
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean supportsMpirBignum() { return true;}

    @Override
    public boolean needsSecondReference() {
        return true;
    }

    @Override
    public double getDoubleLimit() {
        return 1.0e-5;
    }

    @Override
    public double getDoubleDoubleLimit() {
        if(TaskRender.HIGH_PRECISION_CALCULATION) {
            return super.getDoubleDoubleLimit();
        }

        return 1.0e-18;
    }

    @Override
    public boolean needsExtendedRange() {
        return TaskRender.USE_FULL_FLOATEXP_FOR_ALL_ZOOM || (TaskRender.USE_CUSTOM_FLOATEXP_REQUIREMENT && size < 1.0e-14);
    }

    @Override
    public boolean supportsBigIntnum() {
        return true;
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
        return z.sub_mutable(z.cube().sub_mutable(1).divide_mutable(z.square().times_mutable(3)));
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        return z.sub_mutable(z.cube().sub_mutable(MantExp.ONE).divide_mutable(z.square().times_mutable(MantExp.THREE)));
    }

    @Override
    protected boolean needsRefSubCp() {
        return true;
    }

    @Override
    public double getPower() {
        return 3;
    }

    @Override
    protected GenericComplex getInputPixel(GenericComplex inputPixel) {
        return sanitizeInputPixel(inputPixel);
    }
}
