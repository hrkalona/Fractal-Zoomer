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
import java.util.Arrays;

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
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex Z = getArrayValue(Reference, RefIteration);
        Complex z = DeltaSubN;

        Complex temp = Z.times(2).plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayValue(PrecalculatedTerms, RefIteration)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(ReferenceDeep, RefIteration);
        MantExpComplex z = DeltaSubN;

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayDeepValue(PrecalculatedTermsDeep, RefIteration)).sub_mutable(z.divide2()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, double[] Reference, double[] PrecalculatedTerms, double[] PrecalculatedTerms2, int RefIteration) {

        Complex Z = getArrayValue(Reference, RefIteration);
        Complex z = DeltaSubN;

        Complex temp = Z.times(2).plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayValue(PrecalculatedTerms, RefIteration)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, DeepReference ReferenceDeep, DeepReference PrecalculatedTermsDeep, DeepReference PrecalculatedTerms2Deep, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(ReferenceDeep, RefIteration);
        MantExpComplex z = DeltaSubN;

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayDeepValue(PrecalculatedTermsDeep, RefIteration)).sub_mutable(z.divide2()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
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

        if(iterations == 0) {
            Reference = new double[max_iterations << 1];
            ReferenceSubCp = new double[max_iterations << 1];
            PrecalculatedTerms = new double[max_iterations << 1];

            if (deepZoom) {
                ReferenceDeep = new DeepReference(max_iterations);
                ReferenceSubCpDeep = new DeepReference(max_iterations);
                PrecalculatedTermsDeep = new DeepReference(max_iterations);
            }
        }
        else if (max_iterations > getReferenceLength()){
            Reference = Arrays.copyOf(Reference, max_iterations << 1);
            ReferenceSubCp = Arrays.copyOf(ReferenceSubCp, max_iterations << 1);
            PrecalculatedTerms = Arrays.copyOf(PrecalculatedTerms, max_iterations << 1);

            if (deepZoom) {
                ReferenceDeep.resize(max_iterations);
                ReferenceSubCpDeep.resize(max_iterations);
                PrecalculatedTermsDeep.resize(max_iterations);
            }

        }

        //Due to zero, all around zero will not work
        if(inputPixel instanceof BigComplex && ((BigComplex)inputPixel).norm().compareTo(new MyApfloat(1e-4)) < 0) {
            inputPixel = new BigComplex(1e-4, 1e-4);
        }
        else if(inputPixel instanceof MpfrBigNumComplex && ((MpfrBigNumComplex)inputPixel).norm().compare(1e-4) < 0) {
            inputPixel = new MpfrBigNumComplex(1e-4, 1e-4);
        }
        else if(inputPixel instanceof DDComplex && ((DDComplex)inputPixel).norm().compareTo(new DoubleDouble(1e-4)) < 0) {
            inputPixel = new DDComplex(1e-4, 1e-4);
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
                z = iterations == 0 ? bn : lastZValue;
                zold = iterations == 0 ? new MpfrBigNumComplex() : secondTolastZValue;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : thirdTolastZValue;
                start = new MpfrBigNumComplex(bn);
                pixel = new MpfrBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                initVal = new DDComplex(1, 0);
                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? ddn : lastZValue;
                zold = iterations == 0 ? new DDComplex() : secondTolastZValue;
                zold2 = iterations == 0 ? new DDComplex() : thirdTolastZValue;
                start = ddn;
                pixel = ddn;
            }
            else {
                initVal = new Complex(1, 0);
                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? bn : lastZValue;
                zold = iterations == 0 ? new Complex() : secondTolastZValue;
                zold2 = iterations == 0 ? new Complex() : thirdTolastZValue;
                start = new Complex(bn);
                pixel = new Complex(bn);
            }
        }
        else {
            initVal = new BigComplex(1, 0);
            z = iterations == 0 ? inputPixel : lastZValue;
            zold = iterations == 0 ? new BigComplex() : secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : thirdTolastZValue;
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

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            setArrayValue(Reference, iterations, cz);

            GenericComplex zsubcp = z.sub(initVal);

            GenericComplex preCalc;
            if(useBignum) {
                preCalc = z.fourth().sub_mutable(z); //Z^4-Z for catastrophic cancelation
            }
            else {
                preCalc = z.fourth().sub(z); //Z^4-Z for catastrophic cancelation
            }

            setArrayValue(ReferenceSubCp, iterations, zsubcp.toComplex());
            setArrayValue(PrecalculatedTerms, iterations, preCalc.toComplex());

            if(deepZoom) {
                setArrayDeepValue(ReferenceDeep, iterations, loc.getMantExpComplex(z));
                setArrayDeepValue(ReferenceSubCpDeep, iterations, loc.getMantExpComplex(zsubcp));
                setArrayDeepValue(PrecalculatedTermsDeep, iterations, loc.getMantExpComplex(preCalc));
            }

            if (iterations > 0 && convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, pixel, start, pixel, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                if(useBignum) {
                    z = z.sub_mutable(z.cube().sub_mutable(1).divide_mutable(z.square().times_mutable(3)));
                }
                else {
                    z = z.sub(z.cube().sub(MyApfloat.ONE).divide(z.square().times(MyApfloat.THREE)));
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

        calculateSecondReferencePoint(inputPixel, size, deepZoom, iterations2, progress);

    }

    protected void calculateSecondReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int iterations, JProgressBar progress) {

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

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;

        if(useBignum) {
            if(bigNumLib == Constants.BIGNUM_MPFR) {
                initVal = new MpfrBigNumComplex(1, 0);
                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? initVal : lastZ2Value;
                zold = iterations == 0 ? new MpfrBigNumComplex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : thirdTolastZ2Value;
                start = new MpfrBigNumComplex((MpfrBigNumComplex) initVal);
                pixel = new MpfrBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                initVal = new DDComplex(1, 0);
                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? initVal : lastZ2Value;
                zold = iterations == 0 ? new DDComplex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new DDComplex() : thirdTolastZ2Value;
                start = initVal;
                pixel = ddn;
            }
            else {
                initVal = new Complex(1, 0);
                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? initVal : lastZ2Value;
                zold = iterations == 0 ? new Complex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new Complex() : thirdTolastZ2Value;
                start = new Complex((Complex) initVal);
                pixel = new Complex(bn);
            }
        }
        else {
            initVal = new BigComplex(1, 0);
            z = iterations == 0 ? initVal : lastZ2Value;
            zold = iterations == 0 ? new BigComplex() : secondTolastZ2Value;
            zold2 = iterations == 0 ? new BigComplex() : thirdTolastZ2Value;
            start = initVal;
            pixel = inputPixel;
        }


        for (; iterations < max_ref_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            setArrayValue(SecondReference, iterations, cz);

            GenericComplex zsubcp = z.sub(initVal);

            GenericComplex preCalc;
            if(useBignum) {
                preCalc = z.fourth().sub_mutable(z); //Z^4-Z for catastrophic cancelation
            }
            else {
                preCalc = z.fourth().sub(z); //Z^4-Z for catastrophic cancelation
            }

            setArrayValue(SecondReferenceSubCp, iterations, zsubcp.toComplex());
            setArrayValue(SecondPrecalculatedTerms, iterations, preCalc.toComplex());

            if(deepZoom) {
                setArrayDeepValue(SecondReferenceDeep, iterations, loc.getMantExpComplex(z));
                setArrayDeepValue(SecondReferenceSubCpDeep, iterations, loc.getMantExpComplex(zsubcp));
                setArrayDeepValue(SecondPrecalculatedTermsDeep, iterations, loc.getMantExpComplex(preCalc));
            }

            if (iterations > 0 && convergent_bailout_algorithm.Converged(z, zold, zold2, iterations, pixel, start, pixel, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                if(useBignum) {
                    z = z.sub_mutable(z.cube().sub_mutable(1).divide_mutable(z.square().times_mutable(3)));
                }
                else {
                    z = z.sub(z.cube().sub(MyApfloat.ONE).divide(z.square().times(MyApfloat.THREE)));
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

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {
        return z.cube().sub_mutable(1);
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean needsSecondReference() {
        return true;
    }

    @Override
    public boolean requiresDifferentDoubleOrDoubleDoubleLimits() { return true; }

}
