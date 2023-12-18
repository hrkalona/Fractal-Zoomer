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

package fractalzoomer.functions.root_finding_methods.newton;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.function.Function;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;

/**
 *
 * @author hrkalona
 */
public class Newton3 extends NewtonRootFindingMethod {

    public Newton3(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations,  plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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
    public Newton3(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

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

        Complex Z = getArrayValue(reference, RefIteration);

        Complex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z)).sub_mutable(z.divide2()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {

        Complex Z = getArrayValue(data.Reference, RefIteration);

        Complex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayValue(data.PrecalculatedTerms[0], RefIteration, Z)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(data.Reference, RefIteration);

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration, Z)).sub_mutable(z.divide2()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
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
    protected Function[] getPrecalculatedTermsFunctions(Complex c) {
        Function<Complex, Complex> f1 = x -> x.fourth().sub_mutable(x);
        return new Function[] {f1};
    }

    @Override
    protected Function[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        Function<MantExpComplex, MantExpComplex> f1 = x -> x.fourth().sub_mutable(x);
        return new Function[] {f1};
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
                referenceData.createAndSetShortcut(max_ref_iterations,true, preCalcIndexes, useCompressedRef);
            }
            else {
                referenceData.deallocate();
            }

            if (deepZoom) {
                referenceDeepData.createAndSetShortcut(max_ref_iterations,true, preCalcIndexes, useCompressedRef);
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

        //Due to zero, all around zero will not work
        inputPixel = sanitizeInputPixel(inputPixel);

        int bigNumLib = TaskDraw.getBignumLibrary(size, this);

        GenericComplex z, zold, zold2, start, pixel, initVal;

        if(bigNumLib == Constants.BIGNUM_MPFR) {
            initVal = new MpfrBigNumComplex(defaultInitVal.getValue(null));
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            z = iterations == 0 ? bn : referenceData.lastZValue;
            zold = iterations == 0 ? new MpfrBigNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceData.thirdTolastZValue;
            start = new MpfrBigNumComplex(bn);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            initVal = new MpirBigNumComplex(defaultInitVal.getValue(null));
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? bn : referenceData.lastZValue;
            zold = iterations == 0 ? new MpirBigNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceData.thirdTolastZValue;
            start = new MpirBigNumComplex(bn);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            initVal = new DDComplex(defaultInitVal.getValue(null));
            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? ddn : referenceData.lastZValue;
            zold = iterations == 0 ? new DDComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : referenceData.thirdTolastZValue;
            start = ddn;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            initVal = new BigIntNumComplex(defaultInitVal.getValue(null));
            BigIntNumComplex bin = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? bin : referenceData.lastZValue;
            zold = iterations == 0 ? new BigIntNumComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : referenceData.thirdTolastZValue;
            start = bin;
            pixel = bin;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            initVal = new Complex(defaultInitVal.getValue(null));
            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? bn : referenceData.lastZValue;
            zold = iterations == 0 ? new Complex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : referenceData.thirdTolastZValue;
            start = new Complex(bn);
            pixel = new Complex(bn);
        }
        else {
            initVal = new BigComplex(defaultInitVal.getValue(null));
            z = iterations == 0 ? inputPixel : referenceData.lastZValue;
            zold = iterations == 0 ? new BigComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceData.thirdTolastZValue;
            start = inputPixel;
            pixel = inputPixel;
        }

        Location loc = new Location();

        refPoint = inputPixel;

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
            refPointSmall = refPointSmallDeep.toComplex();

            seedSmallDeep = MantExpComplex.create();

            if(lowPrecReferenceOrbitNeeded) {
                seedSmall = new Complex();
            }
        }
        else {
            refPointSmall = refPoint.toComplex();

            if(lowPrecReferenceOrbitNeeded) {
                seedSmall = new Complex();
            }
        }

        RefType = getRefType();

        convergent_bailout_algorithm.setReferenceMode(true);

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[referenceDeep.id] = new ReferenceCompressor(this, iterations == 0 ? z.toMantExpComplex() : referenceData.compressorZm, MantExpComplex.create(), start.toMantExpComplex());

                MantExpComplex cp = initVal.toMantExpComplex();
                Function<MantExpComplex, MantExpComplex> f = x -> x.sub(cp);
                functions[referenceDeepData.ReferenceSubCp.id] = f;
                subexpressionsCompressor[referenceDeepData.ReferenceSubCp.id] = new ReferenceCompressor(f, true);

                Function<MantExpComplex, MantExpComplex>[] fs = getPrecalculatedTermsFunctionsDeep(null);
                for(int i = 0; i < preCalcIndexes.length; i++) {
                    int id = referenceDeepData.PrecalculatedTerms[preCalcIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i], true);
                }
            }
            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toComplex() : referenceData.compressorZ, new Complex(), start.toComplex());

                Complex cp = initVal.toComplex();
                Function<Complex, Complex> f = x -> x.sub(cp);
                functions[referenceData.ReferenceSubCp.id] = f;
                subexpressionsCompressor[referenceData.ReferenceSubCp.id] = new ReferenceCompressor(f);

                Function<Complex, Complex>[] fs = getPrecalculatedTermsFunctions(null);
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
                precalcM = loc.getMantExpComplex(preCalc);
                zsubcpm = loc.getMantExpComplex(zsubcp);
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, precalcM, czm);
                setArrayDeepValue(referenceDeepData.ReferenceSubCp, iterations, zsubcpm, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalcM.toComplex() : preCalc.toComplex(), cz);
                setArrayValue(referenceData.ReferenceSubCp, iterations, deepZoom ? zsubcpm.toComplex() : zsubcp.toComplex(), cz);
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

        GenericComplex z, zold, zold2, start, pixel, initVal;

        int bigNumLib = TaskDraw.getBignumLibrary(size, this);

        if(bigNumLib == Constants.BIGNUM_MPFR) {
            initVal = new MpfrBigNumComplex(defaultInitVal.getValue(null));
            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            zold = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.thirdTolastZValue;
            start = new MpfrBigNumComplex((MpfrBigNumComplex) initVal);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            initVal = new MpirBigNumComplex(defaultInitVal.getValue(null));
            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            zold = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.thirdTolastZValue;
            start = new MpirBigNumComplex((MpirBigNumComplex) initVal);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            initVal = new DDComplex(defaultInitVal.getValue(null));
            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            zold = iterations == 0 ? new DDComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            initVal = new BigIntNumComplex(defaultInitVal.getValue(null));
            BigIntNumComplex bin = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            zold = iterations == 0 ? new BigIntNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            pixel = bin;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            initVal = new Complex(defaultInitVal.getValue(null));
            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            zold = iterations == 0 ? new Complex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : secondReferenceData.thirdTolastZValue;
            start = new Complex((Complex) initVal);
            pixel = new Complex(bn);
        }
        else {
            initVal = new BigComplex(defaultInitVal.getValue(null));
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            zold = iterations == 0 ? new BigComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            pixel = inputPixel;
        }

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[secondReferenceDeepData.Reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toMantExpComplex() : secondReferenceData.compressorZm, MantExpComplex.create(), start.toMantExpComplex());

                MantExpComplex cp = initVal.toMantExpComplex();
                Function<MantExpComplex, MantExpComplex> f = x -> x.sub(cp);
                functions[secondReferenceDeepData.ReferenceSubCp.id] = f;
                subexpressionsCompressor[secondReferenceDeepData.ReferenceSubCp.id] = new ReferenceCompressor(f, true);

                Function<MantExpComplex, MantExpComplex>[] fs = getPrecalculatedTermsFunctionsDeep(null);
                for(int i = 0; i < preCalcIndexes.length; i++) {
                    int id = secondReferenceDeepData.PrecalculatedTerms[preCalcIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i], true);
                }
            }
            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[secondReferenceData.Reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toComplex() : secondReferenceData.compressorZ, new Complex(), start.toComplex());

                Complex cp = initVal.toComplex();
                Function<Complex, Complex> f = x -> x.sub(cp);
                functions[secondReferenceData.ReferenceSubCp.id] = f;
                subexpressionsCompressor[secondReferenceData.ReferenceSubCp.id] = new ReferenceCompressor(f);

                Function<Complex, Complex>[] fs = getPrecalculatedTermsFunctions(null);
                for(int i = 0; i < preCalcIndexes.length; i++) {
                    int id = secondReferenceData.PrecalculatedTerms[preCalcIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i]);
                }
            }
        }

        convergent_bailout_algorithm.setReferenceMode(true);

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
                tempmcz = setArrayDeepValue(secondReferenceDeepData.Reference, iterations, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? czm.toComplex() : z.toComplex();
                if (cz.isInfinite() || cz.isNaN()) {
                    break;
                }

                cz = setArrayValue(secondReferenceData.Reference, iterations, cz);
            }

            czm = tempmcz;

            if(deepZoom) {
                precalcm = loc.getMantExpComplex(preCalc);
                zsubcpm = loc.getMantExpComplex(zsubcp);
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[0], iterations, precalcm, czm);
                setArrayDeepValue(secondReferenceDeepData.ReferenceSubCp, iterations, zsubcpm, czm);
            }

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalcm.toComplex() : preCalc.toComplex(), cz);
                setArrayValue(secondReferenceData.ReferenceSubCp, iterations, deepZoom ? zsubcpm.toComplex() : zsubcp.toComplex(), cz);
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
        if(TaskDraw.HIGH_PRECISION_CALCULATION) {
            return super.getDoubleDoubleLimit();
        }

        return 1.0e-18;
    }

    @Override
    public boolean needsExtendedRange() {
        return TaskDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM || (TaskDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT && size < 1.0e-14);
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
}
