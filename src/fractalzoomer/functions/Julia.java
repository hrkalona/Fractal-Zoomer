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
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.utils.ColorAlgorithm;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.function.Function;

import static fractalzoomer.main.Constants.*;

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

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);
        isJulia = false;
    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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
    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, false);

    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, true);

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

    @Override
    public Complex getTransformedPixelJulia(Complex pixel) {

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

            if(TaskDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
                Complex pixelWithoutDelta = pixel.plus(refPointSmall);

                if (statistic != null) {
                    statistic.initialize(pixelWithoutDelta, null);
                }

                if (trap != null) {
                    trap.initialize(pixelWithoutDelta);
                }


                try {
                    return iterateJuliaWithPerturbation(initializeSeed(pixel), pixel);
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
        else if (gpixel instanceof MantExpComplex){
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
                return iterateJuliaWithPerturbation(initializeSeed(pix), pixel);
            }
            catch (Exception ex) {
                return 0;
            }
        }
         else {
            gpixel = sanitize(gpixel);

            Complex pix = gpixel.toComplex();

            if (statistic != null) {
                statistic.initialize(pix, null);
            }

            if (trap != null) {
                trap.initialize(pix);
            }

            try {
                return iterateFractalArbitraryPrecision(initializeSeed(gpixel), gpixel);
            } catch (Exception ex) {
                return 0;
            }
        }

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = new Complex[2];

        if(TaskDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {

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
    public GenericComplex[] initializeSeed(GenericComplex pixel) {

        GenericComplex[] complex = new GenericComplex[2];

        int lib = TaskDraw.getHighPrecisionLibrary(dsize, this);

        if(lib == ARBITRARY_MPFR) {
            workSpaceData.z.set(pixel);
            complex[0] = workSpaceData.z;//z

            workSpaceData.c.set(getHighPrecisionSeed(lib));
            complex[1] = workSpaceData.c;//c

            workSpaceData.zold.reset();
            gzold = workSpaceData.zold;

            workSpaceData.zold2.reset();
            gzold2 = workSpaceData.zold2;

            workSpaceData.start.set(complex[0]);
            gstart = workSpaceData.start;

            workSpaceData.c0.set(complex[1]);
            gc0 = workSpaceData.c0;
        }
        else if(lib == ARBITRARY_MPIR) {
            workSpaceData.zp.set(pixel);
            complex[0] = workSpaceData.zp;//z

            workSpaceData.cp.set(getHighPrecisionSeed(lib));
            complex[1] = workSpaceData.cp;//c

            workSpaceData.zoldp.reset();
            gzold = workSpaceData.zoldp;

            workSpaceData.zold2p.reset();
            gzold2 = workSpaceData.zold2p;

            workSpaceData.startp.set(complex[0]);
            gstart = workSpaceData.startp;

            workSpaceData.c0p.set(complex[1]);
            gc0 = workSpaceData.c0p;
        }
        else if (lib == ARBITRARY_BUILT_IN) {
            complex[0] = pixel;//z
            complex[1] = getHighPrecisionSeed(lib);//c

            gzold = new BigNumComplex();
            gzold2 = new BigNumComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }
        else if (lib == ARBITRARY_BIGINT) {
            complex[0] = pixel;//z
            complex[1] = getHighPrecisionSeed(lib);//c

            gzold = new BigIntNumComplex();
            gzold2 = new BigIntNumComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }
        else if(lib == ARBITRARY_DOUBLEDOUBLE) {
            complex[0] = pixel;//z
            complex[1] = getHighPrecisionSeed(lib);//c

            gzold = new DDComplex();
            gzold2 = new DDComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }
        else {
            complex[0] = pixel;//z
            complex[1] = getHighPrecisionSeed(lib);//c

            gzold = new BigComplex();
            gzold2 = new BigComplex();
            gstart = complex[0];
            gc0 = complex[1];
        }

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

        resetGlobalVars();
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
    public double iterateJuliaWithPerturbation(Complex[] complex, Complex dpixel) {

        double_iterations = 0;
        rebases = 0;

        iterations = 0;

        int RefIteration = iterations;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z

        Complex pixel = dpixel.plus(refPointSmall);

        ReferenceData data = referenceData;
        int MaxRefIteration = data.MaxRefIteration;

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

                res = getFinalValueOut(res, complex[0]);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return getAndAccumulateStatsNotDeep(res);
            }

            DeltaSubN = perturbationFunction(DeltaSubN, data, RefIteration);

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                complex[0] = getArrayValue(data.Reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            norm_squared = complex[0].norm_squared();
            if (norm_squared < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = complex[0];
                RefIteration = 0;

                data = secondReferenceData;
                MaxRefIteration = data.MaxRefIteration;
                rebases++;
            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, complex[0]);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return getAndAccumulateStatsNotDeep(in);

    }

    @Override
    public double iterateJuliaWithPerturbation(Complex[] complex, MantExpComplex dpixel) {


        float_exp_iterations = 0;
        double_iterations = 0;
        rebases = 0;

        iterations = 0;

        int totalSkippedIterations = 0;

        int RefIteration = iterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        ReferenceDeepData deepData = referenceDeepData;
        ReferenceData data = referenceData;
        int MaxRefIteration = data.MaxRefIteration;

        int minExp = -1000;
        int reducedExp = minExp / (int)getPower();

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex z = getArrayDeepValue(deepData.Reference, RefIteration).plus_mutable(DeltaSubN);
            MantExpComplex zoldDeep;

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (doBailCheck && bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res, complex[0]);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                DeltaSubN = perturbationFunction(DeltaSubN, deepData, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(complex[0]);
                zoldDeep = z;

                if (max_iterations > 1) {
                    z = getArrayDeepValue(deepData.Reference, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = z.toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0, z, zoldDeep , null);
                }

                if (z.norm_squared().compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;

                    deepData = secondReferenceDeepData;
                    data = secondReferenceData;
                    MaxRefIteration = data.MaxRefIteration;
                    rebases++;
                }

                DeltaSubN.Normalize();

                if(!useFullFloatExp) {
                    if (DeltaSubN.getMinExp() > reducedExp) {
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

                    res = getFinalValueOut(res, complex[0]);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                CDeltaSubN = perturbationFunction(CDeltaSubN, data, RefIteration);

                RefIteration++;
                double_iterations++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    complex[0] = getArrayValue(data.Reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                norm_squared = complex[0].norm_squared();
                if (norm_squared < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = complex[0];
                    RefIteration = 0;
                    data = secondReferenceData;
                    MaxRefIteration = data.MaxRefIteration;
                    rebases++;
                }

            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, complex[0]);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return getAndAccumulateStatsNotScaled(in);

    }

    @Override
    public boolean applyPlaneOnJulia() {
        return apply_plane_on_julia;
    }

    protected void calculateJuliaReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int[] juliaIterations, JProgressBar progress) {

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
        DoubleReference.SHOULD_SAVE_MEMORY = false;
        boolean useCompressedRef = TaskDraw.COMPRESS_REFERENCE_IF_POSSIBLE && supportsReferenceCompression();
        int[] precalIndexes = getNeededPrecalculatedTermsIndexes();

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                secondReferenceData.create(max_ref_iterations, needsRefSubCp(), precalIndexes, useCompressedRef);
            }
            else {
                secondReferenceData.deallocate();
            }

            if (deepZoom) {
                secondReferenceDeepData.create(max_ref_iterations, needsRefSubCp(), precalIndexes, useCompressedRef);
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
        Object normSquared;

        int bigNumLib = TaskDraw.getBignumLibrary(size, this);

        if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
            initVal = new BigNumComplex(defaultInitVal.getValue(null));

            BigNumComplex bn = inputPixel.toBigNumComplex();
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new BigNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigNumComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = bn;
        }
        else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            initVal = new BigIntNumComplex(defaultInitVal.getValue(null));

            BigIntNumComplex bn = inputPixel.toBigIntNumComplex();
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new BigIntNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigIntNumComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = bn;
        }
        else if(bigNumLib == Constants.BIGNUM_MPFR) {
            initVal = new MpfrBigNumComplex(defaultInitVal.getValue(null));

            MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
            z = iterations == 0 ? new MpfrBigNumComplex((MpfrBigNumComplex)initVal) : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.thirdTolastZValue;
            start = new MpfrBigNumComplex((MpfrBigNumComplex)initVal);
            c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
            pixel = new MpfrBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            initVal = new MpirBigNumComplex(defaultInitVal.getValue(null));

            MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
            z = iterations == 0 ? new MpirBigNumComplex((MpirBigNumComplex)initVal) : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.thirdTolastZValue;
            start = new MpirBigNumComplex((MpirBigNumComplex)initVal);
            c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
            pixel = new MpirBigNumComplex(bn);
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            initVal = new DDComplex(defaultInitVal.getValue(null));

            DDComplex ddn = inputPixel.toDDComplex();
            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new DDComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new DDComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = ddn;
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            initVal = defaultInitVal.getValue(null);

            Complex bn = inputPixel.toComplex();
            z = iterations == 0 ? new Complex((Complex)initVal) : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new Complex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new Complex() : secondReferenceData.thirdTolastZValue;
            start = new Complex((Complex)initVal);
            c0 = new Complex((Complex) c);
            pixel = new Complex(bn);
        }
        else {
            initVal = new BigComplex(defaultInitVal.getValue(null));

            z = iterations == 0 ? initVal : secondReferenceData.lastZValue;
            c = getSeed(bigNumLib);
            zold = iterations == 0 ? new BigComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : secondReferenceData.thirdTolastZValue;
            start = initVal;
            c0 = c;
            pixel = inputPixel;
        }

        GenericComplex[] initialPrecal = initializeReferencePrecalculationData(c, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom);

        normSquared = z.normSquared();

        boolean preCalcNormData = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        NormComponents normData = null;

        MantExpComplex mcz = null;
        Complex cz = null;

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[secondReferenceDeepData.Reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toMantExpComplex() : secondReferenceData.compressorZm, c.toMantExpComplex(), start.toMantExpComplex());

                if(needsRefSubCp()) {
                    MantExpComplex cp = initVal.toMantExpComplex();
                    Function<MantExpComplex, MantExpComplex> f = x -> x.sub(cp);
                    functions[secondReferenceDeepData.ReferenceSubCp.id] = f;
                    subexpressionsCompressor[secondReferenceDeepData.ReferenceSubCp.id] = new ReferenceCompressor(f, true);
                }

                Function<MantExpComplex, MantExpComplex>[] fs = getPrecalculatedTermsFunctionsDeep(c.toMantExpComplex());
                for(int i = 0; i < precalIndexes.length; i++) {
                    int id = secondReferenceDeepData.PrecalculatedTerms[precalIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i], true);
                }
            }
            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[secondReferenceData.Reference.id] = new ReferenceCompressor(this, iterations == 0 ? z.toComplex() : secondReferenceData.compressorZ, c.toComplex(), start.toComplex());

                if(needsRefSubCp()) {
                    Complex cp = initVal.toComplex();
                    Function<Complex, Complex> f = x -> x.sub(cp);
                    functions[secondReferenceData.ReferenceSubCp.id] = f;
                    subexpressionsCompressor[secondReferenceData.ReferenceSubCp.id] = new ReferenceCompressor(f);
                }

                Function<Complex, Complex>[] fs = getPrecalculatedTermsFunctions(c.toComplex());
                for(int i = 0; i < precalIndexes.length; i++) {
                    int id = secondReferenceData.PrecalculatedTerms[precalIndexes[i]].id;
                    functions[id] = fs[i];
                    subexpressionsCompressor[id] = new ReferenceCompressor(fs[i]);
                }
            }
        }

        calculatedSecondReferenceIterations = 0;

        MantExpComplex tempmcz = null;

        for (; iterations < max_ref_iterations; iterations++, calculatedSecondReferenceIterations++) {

            if(deepZoom) {
                mcz = loc.getMantExpComplex(z);
                if (mcz.isInfinite() || mcz.isNaN()) {
                    break;
                }
                tempmcz = setArrayDeepValue(secondReferenceDeepData.Reference, iterations, mcz);
            }

            if(lowPrecReferenceOrbitNeeded) {
                cz = deepZoom ? mcz.toComplex() : z.toComplex();

                if (cz.isInfinite() || cz.isNaN()) {
                    break;
                }
                cz = setArrayValue(secondReferenceData.Reference, iterations, cz);
            }

            mcz = tempmcz;

            calculateRefSubCp(z, initVal, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom, secondReferenceData, secondReferenceDeepData, iterations, cz, mcz);

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            GenericComplex[] precalculatedData = precalculateReferenceData(z, c, normData, loc, bigNumLib, lowPrecReferenceOrbitNeeded, deepZoom, secondReferenceData, secondReferenceDeepData, iterations, cz, mcz);

            if (iterations > 0 && bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                break;
            }

            if(!preCalcNormData) {
                zold2.set(zold);
                zold.set(z);
            }

            try {
                z = referenceFunction(z, c, normData, initialPrecal, precalculatedData);
            }
            catch (Exception ex) {
                break;
            }


            if(progress != null && iterations % 1000 == 0) {
                progress.setValue(iterations - initIterations);
                progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (iterations - initIterations) / progress.getMaximum() * 100)) + "%");
            }

        }

        secondReferenceData.lastZValue = z;
        secondReferenceData.secondTolastZValue = zold;
        secondReferenceData.thirdTolastZValue = zold2;

        secondReferenceData.MaxRefIteration = iterations - 1;

        if(useCompressedRef) {
            if(deepZoom) {
                referenceCompressor[secondReferenceDeepData.Reference.id].compact(secondReferenceDeepData.Reference);
                secondReferenceData.compressorZm = referenceCompressor[secondReferenceDeepData.Reference.id].getZDeep();

                if(needsRefSubCp()) {
                    subexpressionsCompressor[secondReferenceDeepData.ReferenceSubCp.id].compact(secondReferenceDeepData.ReferenceSubCp);
                }

                for(int i = 0; i < precalIndexes.length; i++) {
                    subexpressionsCompressor[secondReferenceDeepData.PrecalculatedTerms[precalIndexes[i]].id].compact(secondReferenceDeepData.PrecalculatedTerms[precalIndexes[i]]);
                }
            }

            if(lowPrecReferenceOrbitNeeded) {
                referenceCompressor[secondReferenceData.Reference.id].compact(secondReferenceData.Reference);
                secondReferenceData.compressorZ = referenceCompressor[secondReferenceData.Reference.id].getZ();

                if(needsRefSubCp()) {
                    subexpressionsCompressor[secondReferenceData.ReferenceSubCp.id].compact(secondReferenceData.ReferenceSubCp);
                }

                for(int i = 0; i < precalIndexes.length; i++) {
                    subexpressionsCompressor[secondReferenceData.PrecalculatedTerms[precalIndexes[i]].id].compact(secondReferenceData.PrecalculatedTerms[precalIndexes[i]]);
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
    public void setSeed(BigComplex seed) {
        bigSeed = seed;
    }

    @Override
    public GenericComplex getSeed(int bigNumLib) {

        GenericComplex tseed = null;

        if(bigNumLib == Constants.BIGNUM_BUILT_IN) { //BigNumComplex
            tseed = bigSeed.toBigNumComplex();
        }
        else if(bigNumLib == BIGNUM_BIGINT) { //BigIntNumComplex
            tseed = bigSeed.toBigIntNumComplex();
        }
        else if(bigNumLib == Constants.BIGNUM_MPFR) { //MpfrBigNumComplex
            tseed = bigSeed.toMpfrBigNumComplex();
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) { //MpirBigNumComplex
            if(!LibMpfr.hasError()) {
                try {
                    tseed = new MpirBigNumComplex(bigSeed.toMpfrBigNumComplex());
                }
                catch (Error e) {
                    tseed = bigSeed.toMpirBigNumComplex();
                }
            }
            else {
                tseed = bigSeed.toMpirBigNumComplex();
            }
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) { //DDComplex
            tseed = bigSeed.toDDComplex();
        }
        else if(bigNumLib == Constants.BIGNUM_DOUBLE) {  //Complex
            tseed = bigSeed.toComplex();
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

    public GenericComplex getHighPrecisionSeed(int bigNumLib) {

        GenericComplex tseed = null;

        if(bigNumLib == ARBITRARY_BUILT_IN) { //BigNumComplex
            tseed = bigSeed.toBigNumComplex();
        }
        else if(bigNumLib == ARBITRARY_BIGINT) { //BigIntNumComplex
            tseed = bigSeed.toBigIntNumComplex();
        }
        else if(bigNumLib == ARBITRARY_MPFR) { //MpfrBigNumComplex
            workSpaceData.seed.set(bigSeed);
            tseed = workSpaceData.seed;
        }
        else if(bigNumLib == ARBITRARY_MPIR) { //MpfrBigNumComplex
            workSpaceData.seedp.set(bigSeed);
            tseed = workSpaceData.seedp;
        }
        else if(bigNumLib == ARBITRARY_DOUBLEDOUBLE) { //DDComplex
            tseed = bigSeed.toDDComplex();
        }
        else { //BigComplex
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
