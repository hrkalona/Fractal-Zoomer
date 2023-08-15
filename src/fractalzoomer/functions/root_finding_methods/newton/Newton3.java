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

    public Newton3(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations,  plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

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

        power = 3;

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
       
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);
        
        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    //orbit
    public Newton3(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        power = 3;

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

        Complex temp = Z.times(2).plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration)).sub_mutable(z.divide2()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {

        Complex Z = getArrayValue(data.Reference, RefIteration);

        Complex temp = Z.times(2).plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayValue(data.PrecalculatedTerms[0], RefIteration)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(data.Reference, RefIteration);

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration)).sub_mutable(z.divide2()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
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

        if(iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                referenceData.createAndSetShortcut(max_ref_iterations,true, 1);
            }
            else {
                referenceData.deallocate();
            }

            if (deepZoom) {
                referenceDeepData.createAndSetShortcut(max_ref_iterations,true, 1);
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
        if(inputPixel instanceof BigComplex && ((BigComplex)inputPixel).norm().compareTo(new MyApfloat(1e-4)) < 0) {
            inputPixel = new BigComplex(1e-4, 1e-4);
        }
        else if(inputPixel instanceof MpfrBigNumComplex && ((MpfrBigNumComplex)inputPixel).norm().compare(1e-4) < 0) {
            inputPixel = new MpfrBigNumComplex(1e-4, 1e-4);
        }
        else if(inputPixel instanceof MpirBigNumComplex && ((MpirBigNumComplex)inputPixel).norm().compare(1e-4) < 0) {
            inputPixel = new MpirBigNumComplex(1e-4, 1e-4);
        }
        else if(inputPixel instanceof DDComplex && ((DDComplex)inputPixel).norm().compareTo(new DoubleDouble(1e-4)) < 0) {
            inputPixel = new DDComplex(1e-4, 1e-4);
        }
        else if(inputPixel instanceof BigIntNumComplex && ((BigIntNumComplex)inputPixel).norm().compare(new BigIntNum(1e-4)) < 0) {
            inputPixel = new BigIntNumComplex(1e-4, 1e-4);
        }
        else if(inputPixel instanceof Complex && ((Complex)inputPixel).norm() < 1e-4) {
            inputPixel = new Complex(1e-4, 1e-4);
        }


        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && bigNumLib != Constants.BIGNUM_APFLOAT;

        GenericComplex z, zold, zold2, start, pixel, initVal;

        if(useBignum) {
            if(bigNumLib == Constants.BIGNUM_MPFR) {
                initVal = new MpfrBigNumComplex(1, 0);
                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? bn : referenceData.lastZValue;
                zold = iterations == 0 ? new MpfrBigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceData.thirdTolastZValue;
                start = new MpfrBigNumComplex(bn);
                pixel = new MpfrBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_MPIR) {
                initVal = new MpirBigNumComplex(1, 0);
                MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
                z = iterations == 0 ? bn : referenceData.lastZValue;
                zold = iterations == 0 ? new MpirBigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceData.thirdTolastZValue;
                start = new MpirBigNumComplex(bn);
                pixel = new MpirBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                initVal = new DDComplex(1, 0);
                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? ddn : referenceData.lastZValue;
                zold = iterations == 0 ? new DDComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new DDComplex() : referenceData.thirdTolastZValue;
                start = ddn;
                pixel = ddn;
            }
            else if(bigNumLib == Constants.BIGNUM_BIGINT) {
                initVal = new BigIntNumComplex(1, 0);
                BigIntNumComplex bin = inputPixel.toBigIntNumComplex();
                z = iterations == 0 ? bin : referenceData.lastZValue;
                zold = iterations == 0 ? new BigIntNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new BigIntNumComplex() : referenceData.thirdTolastZValue;
                start = bin;
                pixel = bin;
            }
            else {
                initVal = new Complex(1, 0);
                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? bn : referenceData.lastZValue;
                zold = iterations == 0 ? new Complex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new Complex() : referenceData.thirdTolastZValue;
                start = new Complex(bn);
                pixel = new Complex(bn);
            }
        }
        else {
            initVal = new BigComplex(1, 0);
            z = iterations == 0 ? inputPixel : referenceData.lastZValue;
            zold = iterations == 0 ? new BigComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceData.thirdTolastZValue;
            start = inputPixel;
            pixel = inputPixel;
        }

        Location loc = new Location();

        refPoint = inputPixel;

        refPointSmall = refPoint.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
        }

        RefType = getRefType();

        convergent_bailout_algorithm.setReferenceMode(true);

        for (; iterations < max_ref_iterations; iterations++) {

            if(lowPrecReferenceOrbitNeeded) {
                Complex cz = z.toComplex();
                if (cz.isInfinite()) {
                    break;
                }

                setArrayValue(reference, iterations, cz);
            }

            GenericComplex zsubcp;
            if(useBignum && bigNumLib == Constants.BIGNUM_MPFR) {
                zsubcp = z.sub(initVal, workSpaceData.temp1, workSpaceData.temp2);
            }
            else if(useBignum && bigNumLib == Constants.BIGNUM_MPIR) {
                zsubcp = z.sub(initVal, workSpaceData.temp1p, workSpaceData.temp2p);
            }
            else {
                zsubcp = z.sub(initVal);
            }

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.ReferenceSubCp, iterations, zsubcp.toComplex());
            }
            if(deepZoom) {
                setArrayDeepValue(referenceDeepData.ReferenceSubCp, iterations, loc.getMantExpComplex(zsubcp));
            }

            GenericComplex zcubes1;

            if(useBignum) {
                zcubes1 = z.cube().sub_mutable(1);
            }
            else {
                zcubes1 = z.cube().sub(MyApfloat.ONE);
            }

            GenericComplex preCalc;
            preCalc = zcubes1.times(z); //Z^4-Z for catastrophic cancelation

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[0], iterations, preCalc.toComplex());
            }

            if(deepZoom) {
                setArrayDeepValue(referenceDeep, iterations, loc.getMantExpComplex(z));
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, loc.getMantExpComplex(preCalc));
            }

            if (iterations > 0 && convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, pixel, start, pixel, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                if(useBignum) {
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

        skippedIterations = 0;

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

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                secondReferenceData.create(max_ref_iterations,true, 1);
            }
            else {
                secondReferenceData.deallocate();
            }

            if (deepZoom) {
                secondReferenceDeepData.create(max_ref_iterations,true, 1);
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

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;

        if(useBignum) {
            if(bigNumLib == Constants.BIGNUM_MPFR) {
                initVal = new MpfrBigNumComplex(1, 0);
                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
                zold = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.thirdTolastZValue;
                start = new MpfrBigNumComplex((MpfrBigNumComplex) initVal);
                pixel = new MpfrBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_MPIR) {
                initVal = new MpirBigNumComplex(1, 0);
                MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
                z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
                zold = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.thirdTolastZValue;
                start = new MpirBigNumComplex((MpirBigNumComplex) initVal);
                pixel = new MpirBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                initVal = new DDComplex(1, 0);
                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
                zold = iterations == 0 ? new DDComplex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new DDComplex() : secondReferenceData.thirdTolastZValue;
                start = initVal;
                pixel = ddn;
            }
            else if(bigNumLib == Constants.BIGNUM_BIGINT) {
                initVal = new BigIntNumComplex(1, 0);
                BigIntNumComplex bin = inputPixel.toBigIntNumComplex();
                z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
                zold = iterations == 0 ? new BigIntNumComplex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new BigIntNumComplex() : secondReferenceData.thirdTolastZValue;
                start = initVal;
                pixel = bin;
            }
            else {
                initVal = new Complex(1, 0);
                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
                zold = iterations == 0 ? new Complex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new Complex() : secondReferenceData.thirdTolastZValue;
                start = new Complex((Complex) initVal);
                pixel = new Complex(bn);
            }
        }
        else {
            initVal = new BigComplex(1, 0);
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            zold = iterations == 0 ? new BigComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            pixel = inputPixel;
        }


        convergent_bailout_algorithm.setReferenceMode(true);

        for (; iterations < max_ref_iterations; iterations++) {

            if(lowPrecReferenceOrbitNeeded) {
                Complex cz = z.toComplex();
                if (cz.isInfinite()) {
                    break;
                }

                setArrayValue(secondReferenceData.Reference, iterations, cz);
            }

            GenericComplex zsubcp;
            if(useBignum && bigNumLib == Constants.BIGNUM_MPFR) {
                zsubcp = z.sub(initVal, workSpaceData.temp1, workSpaceData.temp2);
            }
            else if(useBignum && bigNumLib == Constants.BIGNUM_MPIR) {
                zsubcp = z.sub(initVal, workSpaceData.temp1p, workSpaceData.temp2p);
            }
            else {
                zsubcp = z.sub(initVal);
            }

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.ReferenceSubCp, iterations, zsubcp.toComplex());
            }
            if(deepZoom) {
                setArrayDeepValue(secondReferenceDeepData.ReferenceSubCp, iterations, loc.getMantExpComplex(zsubcp));
            }

            GenericComplex zcubes1;

            if(useBignum) {
                zcubes1 = z.cube().sub_mutable(1);
            }
            else {
                zcubes1 = z.cube().sub(MyApfloat.ONE);
            }

            GenericComplex preCalc;
            preCalc = zcubes1.times(z); //Z^4-Z for catastrophic cancelation

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.PrecalculatedTerms[0], iterations, preCalc.toComplex());
            }

            if(deepZoom) {
                setArrayDeepValue(secondReferenceDeepData.Reference, iterations, loc.getMantExpComplex(z));
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[0], iterations, loc.getMantExpComplex(preCalc));
            }

            if (iterations > 0 && convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, pixel, start, pixel, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                if(useBignum) {
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
        if(ThreadDraw.HIGH_PRECISION_CALCULATION) {
            return super.getDoubleDoubleLimit();
        }

        return 1.0e-20;
    }

    @Override
    public boolean needsExtendedRange() {
        return ThreadDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM || (ThreadDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT && size < 1.0e-14);
    }

    @Override
    public boolean supportsBigIntnum() {
        return true;
    }

}
