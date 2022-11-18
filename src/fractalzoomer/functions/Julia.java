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
package fractalzoomer.functions;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.utils.ColorAlgorithm;
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
public abstract class Julia extends Fractal {

    protected Complex seed;
    protected BigComplex bigSeed;
    private boolean apply_plane_on_julia;
    private boolean apply_plane_on_julia_seed;
    protected boolean updatedJuliter;

    public Julia() {
        super();
    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);
        isJulia = false;
    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        if (apply_plane_on_julia_seed) {
            seed = plane.transform(new Complex(xJuliaCenter, yJuliaCenter));
        } else {
            seed = new Complex(xJuliaCenter, yJuliaCenter);
        }

        this.apply_plane_on_julia_seed = apply_plane_on_julia_seed;
        this.apply_plane_on_julia = apply_plane_on_julia;
        isJulia = true;
        updatedJuliter = false;

    }

    //orbit
    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, false);

    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, true);

        if (apply_plane_on_julia_seed) {
            seed = plane.transform(new Complex(xJuliaCenter, yJuliaCenter));
        } else {
            seed = new Complex(xJuliaCenter, yJuliaCenter);
        }

        this.apply_plane_on_julia_seed = apply_plane_on_julia_seed;
        this.apply_plane_on_julia = apply_plane_on_julia;

        if (!apply_plane_on_julia) { // recalculate the initial pixel because the transform was added to the super constructor
            pixel_orbit = this.complex_orbit.get(0);
            pixel_orbit = rotation.rotate(pixel_orbit);
        }
        updatedJuliter = false;

    }

    public Complex getTransformedPixelJulia(Complex pixel) {

        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory() && !isOrbit && !isDomain) {//orbit?
            return pixel;
        }

        if (apply_plane_on_julia) {
            return plane.transform(rotation.rotate(pixel));
        } else {
            return rotation.rotate(pixel);
        }

    }

    @Override
    public final double calculateJulia(GenericComplex gpixel) {

        escaped = false;
        hasTrueColor = false;
        updatedJuliter = false;

        statValue = 0;
        trapValue = 0;

        resetGlobalVars();

        if(gpixel instanceof Complex) {

            Complex pixel = (Complex)gpixel;

            if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
                Complex pixelWithoutDelta = pixel.plus(refPointSmall);

                if (statistic != null) {
                    statistic.initialize(pixelWithoutDelta, null);
                }

                if (trap != null) {
                    trap.initialize(pixelWithoutDelta);
                }


                try {
                    return iterateJuliaWithPerturbationWithoutPeriodicity(initializeSeed(pixel), pixel);
                }
                catch (Exception ex) {
                    return 0;
                }
            }
            else {
                Complex transformed = getTransformedPixelJulia(pixel);

                if (statistic != null) {
                    statistic.initialize(transformed, pixel);
                }

                if (trap != null) {
                    trap.initialize(transformed);
                }

                return periodicity_checking ? iterateFractalWithPeriodicity(juliter ? initialize(transformed) : initializeSeed(transformed), transformed) : iterateFractalWithoutPeriodicity(juliter ? initialize(transformed) : initializeSeed(transformed), transformed);
            }
        }
        else {
            MantExpComplex pixel = (MantExpComplex) gpixel;

            Complex pix = pixel.toComplex();

            Complex pixelWithoutDelta = pix.plus(refPointSmall);

            if (statistic != null) {
                statistic.initialize(pixelWithoutDelta, null);
            }

            if (trap != null) {
                trap.initialize(pixelWithoutDelta);
            }

            try {
                return iterateJuliaWithPerturbationWithoutPeriodicity(initializeSeed(pix), pixel);
            }
            catch (Exception ex) {
                return 0;
            }
        }
        /*else {
            BigNumComplex pixel = (BigNumComplex) gpixel;
            return iterateFractalArbitraryPrecisionWithoutPeriodicity(initializeSeed(pixel), pixel);
        }*/
        /*else {
            BigComplex pixel = (BigComplex) gpixel;
            return iterateFractalArbitraryPrecisionWithoutPeriodicity(initializeSeed(pixel), pixel);
        }*/


    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = new Complex[2];

        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {

            if(!isOrbit && !isDomain) {
                complex[0] = pixel.plus(refPointSmall);
                complex[1] = new Complex(seed);//c
            }
            else {
                complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));//z
                complex[1] = new Complex(seed);//c
            }

        }
        else {
            complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));//z
            complex[1] = new Complex(seed);//c
        }

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        return complex;

    }

    @Override
    public BigComplex[] initializeSeed(BigComplex pixel) {

        BigComplex[] complex = new BigComplex[2];

        complex[0] = new BigComplex(pixel);//z
        complex[1] = new BigComplex(seed);//c


        //zold = new Complex();
        //zold2 = new Complex();
        //start = new Complex(complex[0]);
        //c0 = new Complex(complex[1]);

        return complex;

    }

    @Override
    public BigNumComplex[] initializeSeed(BigNumComplex pixel) {

        BigNumComplex[] complex = new BigNumComplex[2];

        complex[0] = new BigNumComplex(pixel);//z
        complex[1] = new BigNumComplex(seed);//c


        //zold = new Complex();
        //zold2 = new Complex();
        //start = new Complex(complex[0]);
        //c0 = new Complex(complex[1]);

        return complex;

    }

    @Override
    public void updateValues(Complex[] complex) {
        if(isJulia && juliter && !updatedJuliter && iterations == juliterIterations) {
            updatedJuliter = true;
            complex[1] = new Complex(seed);

            if(!juliterIncludeInitialIterations) {
                iterations = 0;
            }
        }
    }

    @Override
    public final void calculateJuliaOrbit() {

        Complex[] complex = juliter ? initialize(pixel_orbit) : initializeSeed(pixel_orbit);
        iterateFractalOrbit(complex, pixel_orbit);

    }

    @Override
    public final Complex calculateJuliaDomain(Complex pixel) {

        updatedJuliter = false;

        isDomain = true;

        resetGlobalVars();

        Complex transformed = getTransformedPixelJulia(pixel);

        return iterateFractalDomain(juliter ? initialize(transformed) : initializeSeed(transformed), transformed);

    }

    @Override
    public double getJulia3DHeight(double value) {

        return ColorAlgorithm.transformResultToHeight(value, max_iterations);

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

        double norm_squared = complex[0].norm_squared();

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }

            DeltaSubN = perturbationFunction(DeltaSubN, Reference, null, null, RefIteration);

            RefIteration++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            norm_squared = complex[0].norm_squared();
            if (norm_squared < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = complex[0];
                RefIteration = 0;

                MaxRefIteration = Fractal.MaxRef2Iteration;
                Reference = Fractal.SecondReference;
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
        double[] Reference = Fractal.Reference;

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Reduce();
        long exp = DeltaSubN.getExp();

        boolean useFullFloatExp = ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM;

        if(useFullFloatExp || (skippedIterations == 0 && exp <= minExp) || (skippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
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

                DeltaSubN = perturbationFunction(DeltaSubN, ReferenceDeep, null, null, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                if (max_iterations > 1) {
                    z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = z.toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (z.norm_squared().compareTo(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;
                    ReferenceDeep = Fractal.SecondReferenceDeep;
                    MaxRefIteration = MaxRef2Iteration;
                    Reference = Fractal.SecondReference;
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

            double norm_squared = complex[0].norm_squared();

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                CDeltaSubN = perturbationFunction(CDeltaSubN, Reference, null, null, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                norm_squared = complex[0].norm_squared();
                if (norm_squared < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = complex[0];
                    RefIteration = 0;
                    Reference = Fractal.SecondReference;
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
    public boolean applyPlaneOnJulia() {
        return apply_plane_on_julia;
    }

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

            if (deepZoom) {
                SecondReferenceDeep = new DeepReference(max_ref_iterations);
            }
        } else if (max_ref_iterations > getSecondReferenceLength()) {
            SecondReference = Arrays.copyOf(SecondReference, max_ref_iterations << 1);

            if (deepZoom) {
                SecondReferenceDeep.resize(max_ref_iterations);
            }
        }

        Location loc = new Location();

        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared;

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;

        if(useBignum) {
            if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
                BigNumComplex bn = inputPixel.toBigNumComplex();
                z = iterations == 0 ? new BigNumComplex() : lastZ2Value;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new BigNumComplex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new BigNumComplex() : thirdTolastZ2Value;
                start = new BigNumComplex();
                c0 = c;
                pixel = bn;
            }
            else if(bigNumLib == Constants.BIGNUM_MPFR) {
                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? new MpfrBigNumComplex() : lastZ2Value;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new MpfrBigNumComplex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : thirdTolastZ2Value;
                start = new MpfrBigNumComplex();
                c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
                pixel = new MpfrBigNumComplex(bn);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? new DDComplex() : lastZ2Value;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new DDComplex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new DDComplex() : thirdTolastZ2Value;
                start = new DDComplex();
                c0 = c;
                pixel = ddn;
            }
            else {
                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? new Complex() : lastZ2Value;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new Complex() : secondTolastZ2Value;
                zold2 = iterations == 0 ? new Complex() : thirdTolastZ2Value;
                start = new Complex();
                c0 = new Complex((Complex) c);
                pixel = new Complex(bn);
            }
        }
        else {
            z = iterations == 0 ? new BigComplex() : lastZ2Value;
            c = getSeed(useBignum, bigNumLib);
            zold = iterations == 0 ? new BigComplex() : secondTolastZ2Value;
            zold2 = iterations == 0 ? new BigComplex() : thirdTolastZ2Value;
            start = new BigComplex();
            c0 = c;
            pixel = inputPixel;
        }

//        MpfrBigNumComplex seedBug = new MpfrBigNumComplex(new MyApfloat("-1.99996619445037030418434688506350579675531241540724851511761922944801584242342684381376129778868913812287046406560949864353810575744772166485672496092803920095332"), new MyApfloat("+0.00000000000000000000000000000000030013824367909383240724973039775924987346831190773335270174257280120474975614823581185647299288414075519224186504978181625478529"));
//        c = seedBug;

        normSquared = z.normSquared();

        boolean preCalcNormData = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        NormComponents normData = null;

        for (; iterations < max_ref_iterations; iterations++) {

            Complex cz = z.toComplex();

            if (cz.isInfinite()) {
                break;
            }
            setArrayValue(SecondReference, iterations, cz);

            if(deepZoom) {
                setArrayDeepValue(SecondReferenceDeep, iterations, loc.getMantExpComplex(z));
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            if (iterations > 0 && bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                break;
            }

            zold2.set(zold);
            zold.set(z);

            try {
                z = juliaReferenceFunction(z, c, normData);
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

    protected GenericComplex juliaReferenceFunction(GenericComplex z, GenericComplex c, NormComponents normData) {
        return null;
    }

    @Override
    public void setSeed(BigComplex seed) {
        bigSeed = seed;
    }

    public GenericComplex getSeed(boolean useBignum, int bigNumLib) {

        GenericComplex tseed = null;

        if(useBignum) {
            if(bigNumLib == Constants.BIGNUM_BUILT_IN) { //BigNumComplex
                tseed = bigSeed.toBigNumComplex();
            }
            else if(bigNumLib == Constants.BIGNUM_MPFR) { //MpfrBigNumComplex
                tseed = bigSeed.toMpfrBigNumComplex();
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) { //DDComplex
                tseed = bigSeed.toDDComplex();
            }
            else { //Complex
                tseed = bigSeed.toComplex();
            }
        }
        else { //Apfloat
            tseed = bigSeed;
        }

        if (apply_plane_on_julia_seed) {
            try {
                return plane.Transform(tseed);
            }
            catch (Exception ex) {
                return tseed;
            }
        }

        return tseed;
    }

}
