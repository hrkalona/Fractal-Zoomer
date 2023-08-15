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
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import fractalzoomer.utils.WorkSpaceData;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public class MandelbrotCubed extends Julia {

    private MandelVariation type;
    private MandelVariation type2;

    private boolean not_burning_ship;

    public MandelbrotCubed(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;
    }

    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

        power = 3;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

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

    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

        power = 3;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();
    }

    //orbit
    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

        power = 3;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

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

    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;
        not_burning_ship = !burning_ship;

        power = 3;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    @Override
    public void function(Complex[] complex) {

        type.getValue(complex[0]);
        complex[0].cube_mutable().plus_mutable(complex[1]);
        type2.getValue(complex[0]);

    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || !juliter;
    }

    @Override
    public void calculateReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int[] Iterations, int[] juliaIterations, Location externalLocation, JProgressBar progress) {

        LastCalculationSize = size;

        long time = System.currentTimeMillis();

        int max_ref_iterations = getReferenceMaxIterations();

        int iterations = Iterations[0];
        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        boolean detectPeriod = ThreadDraw.DETECT_PERIOD && supportsPeriod() && getUserPeriod() == 0;
        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, detectPeriod);

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                referenceData.createAndSetShortcut(max_ref_iterations, false, 0);
            }
            else {
                referenceData.deallocate();
            }

            if (deepZoom) {
                referenceDeepData.createAndSetShortcut(max_ref_iterations, false, 0);
            }
        } else if (max_ref_iterations > getReferenceLength()) {
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

        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared, r = null, r0 = null, norm = null;

        if(iterations == 0) {
            //DetectedAtomPeriod = 0;
            DetectedPeriod = 0;
        }

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;
        boolean stopReferenceCalculationOnDetectedPeriod = detectPeriod && ThreadDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD && userPeriod == 0 && canStopOnDetectedPeriod();
        int detectPeriodAlgorithm = getPeriodDetectionAlgorithm();

        if(useBignum) {
            if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
                BigNumComplex bn = inputPixel.toBigNumComplex();
                z = iterations == 0 ? (isJulia ? bn : new BigNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new BigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new BigNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? bn : new BigNumComplex();
                c0 = c;
                pixel = bn;
                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    //referenceData.minValue = iterations == 0 ? BigNum.getMax() : referenceData.minValue;
                    r0 = new BigNum(size);
                    r = iterations == 0 ? new BigNum((BigNum) r0) : referenceData.lastRValue;
                }
            }
            else if(bigNumLib == BIGNUM_BIGINT) {
                BigIntNumComplex bn = inputPixel.toBigIntNumComplex();
                z = iterations == 0 ? (isJulia ? bn : new BigIntNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new BigIntNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new BigIntNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? bn : new BigIntNumComplex();
                c0 = c;
                pixel = bn;
                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    r0 = new BigIntNum(size);
                    r = iterations == 0 ? new BigIntNum((BigIntNum) r0) : referenceData.lastRValue;
                }
            }
            else if(bigNumLib == Constants.BIGNUM_MPFR) {
                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? (isJulia ? bn : new MpfrBigNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new MpfrBigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? new MpfrBigNumComplex(bn) : new MpfrBigNumComplex();
                c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
                pixel = new MpfrBigNumComplex(bn);
                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    //referenceData.minValue = iterations == 0 ? MpfrBigNum.getMax() : referenceData.minValue;
                    r0 = new MpfrBigNum(size);
                    r = iterations == 0 ? new MpfrBigNum((MpfrBigNum) r0) : referenceData.lastRValue;
                }
            }
            else if(bigNumLib == Constants.BIGNUM_MPIR) {
                MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
                z = iterations == 0 ? (isJulia ? bn : new MpirBigNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new MpirBigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? new MpirBigNumComplex(bn) : new MpirBigNumComplex();
                c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
                pixel = new MpirBigNumComplex(bn);

                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    r0 = new MpirBigNum(size);
                    r = iterations == 0 ? new MpirBigNum((MpirBigNum) r0) : referenceData.lastRValue;
                }
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? (isJulia ? ddn : new DDComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : ddn;
                zold = iterations == 0 ? new DDComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new DDComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? ddn : new DDComplex();
                c0 = c;
                pixel = ddn;
                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    //referenceData.minValue = iterations == 0 ? new DoubleDouble(Double.MAX_VALUE) : referenceData.minValue;
                    r0 = new DoubleDouble(size);
                    r = iterations == 0 ? new DoubleDouble((DoubleDouble) r0) : referenceData.lastRValue;
                }
            }
            else {
                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? (isJulia ? bn : new Complex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new Complex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new Complex() : referenceData.thirdTolastZValue;
                start = isJulia ? new Complex(bn) : new Complex();
                c0 = new Complex((Complex) c);
                pixel = new Complex(bn);
                if(detectPeriod && detectPeriodAlgorithm == 0) {
                    //referenceData.minValue = iterations == 0 ? Double.MAX_VALUE : referenceData.minValue;
                    r0 = size.doubleValue();
                    r = iterations == 0 ? r0 : referenceData.lastRValue;
                }
            }
        }
        else {
            z = iterations == 0 ? (isJulia ? inputPixel : new BigComplex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(useBignum, bigNumLib) : inputPixel;
            zold = iterations == 0 ? new BigComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? inputPixel : new BigComplex();
            c0 = c;
            pixel = inputPixel;
            if(detectPeriod && detectPeriodAlgorithm == 0) {
                //referenceData.minValue = iterations == 0 ? new MyApfloat(Double.MAX_VALUE) : referenceData.minValue;
                r0 = size;
                r = iterations == 0 ? r0 : referenceData.lastRValue;
            }
        }

        normSquared = z.normSquared();

        Location loc = new Location();

        refPoint = inputPixel;
        refPointSmall = refPoint.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
        }

        boolean isSeriesInUse = ThreadDraw.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation();
        boolean isBLAInUse = ThreadDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation();
        RefType = getRefType();

        boolean usesCircleBail = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        boolean preCalcNormData = (detectPeriod && detectPeriodAlgorithm == 0) || usesCircleBail;

        NormComponents normData = null;

        boolean isMpfrComplex = z instanceof MpfrBigNumComplex;
        boolean isMpirComplex = z instanceof MpirBigNumComplex;

        Complex dzdc = null;
        MantExpComplex mdzdc = null;

        MantExp mradius = null;
        double radius = 0;

        if(detectPeriod && DetectedPeriod == 0 && detectPeriodAlgorithm == 1) {
            if (iterations == 0) {
                if (deepZoom) {
                    mdzdc = new MantExpComplex(1, 0);
                } else {
                    dzdc = new Complex(1, 0);
                }
            } else {
                if (deepZoom) {
                    mdzdc = referenceData.mdzdc;
                } else {
                    dzdc = referenceData.dzdc;
                }
            }

            if(deepZoom) {
                mradius = externalLocation.getSize().multiply2_mutable();
            }
            else {
                radius = this.size * 2;
            }
        }

        Complex cz = null;
        MantExpComplex mcz = null;

        for (; iterations < max_ref_iterations; iterations++) {

            if(lowPrecReferenceOrbitNeeded) {
                cz = z.toComplex();
                if (cz.isInfinite()) {
                    break;
                }

                setArrayValue(reference, iterations, cz);
            }

            if(deepZoom) {
                mcz = loc.getMantExpComplex(z);
                setArrayDeepValue(referenceDeep, iterations, mcz);
                //ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
            }

            if(stopReferenceCalculationOnDetectedPeriod && DetectedPeriod != 0) {
                break;
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            if(detectPeriod) {
                if(detectPeriodAlgorithm == 0) {
                    if (useBignum) {
                        if (bigNumLib == Constants.BIGNUM_BUILT_IN) {
//                        if (iterations > 0 && ((BigNum) normSquared).compareBothPositive((BigNum) referenceData.minValue) < 0) {
//                            DetectedAtomPeriod = iterations;
//                            referenceData.minValue = normSquared;
//                        }

                            if (DetectedPeriod == 0 && ((BigNum) r).compare((BigNum) (norm = ((BigNum) normSquared).sqrt())) > 0 && iterations > 0) {
                                DetectedPeriod = iterations;
                            }
                        }
                        else if (bigNumLib == Constants.BIGNUM_BIGINT) {
                            if (DetectedPeriod == 0 && ((BigIntNum) r).compare((BigIntNum) (norm = ((BigIntNum) normSquared).sqrt())) > 0 && iterations > 0) {
                                DetectedPeriod = iterations;
                            }
                        }
                        else if (bigNumLib == Constants.BIGNUM_MPFR) {
//                        if (iterations > 0 && ((MpfrBigNum) normSquared).compare((MpfrBigNum) referenceData.minValue) < 0) {
//                            DetectedAtomPeriod = iterations;
//                            ((MpfrBigNum) referenceData.minValue).set((MpfrBigNum)normSquared);
//                        }

                            if (DetectedPeriod == 0 && ((MpfrBigNum) r).compare((MpfrBigNum) (norm = ((MpfrBigNum) normSquared).sqrt(workSpaceData.tempPvar2))) > 0 && iterations > 0) {
                                DetectedPeriod = iterations;
                            }
                        }
                        else if (bigNumLib == Constants.BIGNUM_MPIR) {

                            if (DetectedPeriod == 0 && ((MpirBigNum) r).compare((MpirBigNum) (norm = ((MpirBigNum) normSquared).sqrt(workSpaceData.tempPvar2p))) > 0 && iterations > 0) {
                                DetectedPeriod = iterations;
                            }
                        }
                        else if (bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
//                        if (iterations > 0 && ((DoubleDouble) normSquared).compareTo(referenceData.minValue) < 0) {
//                            DetectedAtomPeriod = iterations;
//                            referenceData.minValue = normSquared;
//                        }

                            if (DetectedPeriod == 0 && ((DoubleDouble) r).compareTo(norm = ((DoubleDouble) normSquared).sqrt()) > 0 && iterations > 0) {
                                DetectedPeriod = iterations;
                            }
                        } else {
//                        if (iterations > 0 && ((double) normSquared) < ((double) referenceData.minValue)){
//                            DetectedAtomPeriod = iterations;
//                            referenceData.minValue = normSquared;
//                        }

                            if (DetectedPeriod == 0 && ((double) r) > (double) (norm = Math.sqrt((double) normSquared)) && iterations > 0) {
                                DetectedPeriod = iterations;
                            }
                        }
                    } else {
//                    if(iterations > 0 && ((Apfloat)normSquared).compareTo((Apfloat)referenceData.minValue) < 0) {
//                        DetectedAtomPeriod = iterations;
//                        referenceData.minValue = normSquared;
//                    }

                        if (DetectedPeriod == 0 && ((Apfloat) r).compareTo((Apfloat) (norm = MyApfloat.fp.sqrt((Apfloat) normSquared))) > 0 && iterations > 0) {
                            DetectedPeriod = iterations;
                        }
                    }
                }
                else {
                    if (DetectedPeriod == 0 && iterations > 0) {
                        if (deepZoom) {
                            if (mradius.multiply(mdzdc.chebychevNorm()).compareToBothPositive(mcz.chebychevNorm()) > 0) {
                                DetectedPeriod = iterations;
                            }
                        } else {
                            if (radius * dzdc.chebychevNorm() > cz.chebychevNorm()) {
                                DetectedPeriod = iterations;
                            }
                        }
                    }
                }
            }

            if (iterations > 0 && bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                break;
            }

            if(!usesCircleBail) {
                zold2.set(zold);
                zold.set(z);
            }

            try {

                if(detectPeriod && DetectedPeriod == 0) {
                    if (detectPeriodAlgorithm == 1) {
                        if (deepZoom) {
                            mdzdc = mcz.square().times_mutable(MantExp.THREE).times_mutable(mdzdc).plus_mutable(MantExp.ONE);
                            mdzdc.Reduce();
                        } else {
                            dzdc = cz.square().times_mutable(3).times_mutable(dzdc).plus_mutable(1);
                        }
                    }
                    else {
                        r = calculateR(r, r0, normSquared, norm, workSpaceData);
                    }
                }

                if(preCalcNormData) {
                    if (burning_ship) {
                        z = z.abs_mutable().cubeFast_mutable(normData).plus_mutable(c);
                    } else {
                        z = z.cubeFast_mutable(normData).plus_mutable(c);
                    }
                }
                else {
                    if(isMpfrComplex) {
                        if (burning_ship) {
                            z = z.abs_mutable().cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(c);
                        }
                        else {
                            z = z.cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(c);
                        }
                    }
                    else if(isMpirComplex) {
                        if (burning_ship) {
                            z = z.abs_mutable().cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(c);
                        }
                        else {
                            z = z.cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(c);
                        }
                    }
                    else {
                        if (burning_ship) {
                            z = z.abs_mutable().cube().plus_mutable(c);
                        } else {
                            z = z.cube().plus_mutable(c);
                        }
                    }
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

        referenceData.lastRValue = r;
        referenceData.lastZValue = z;
        referenceData.secondTolastZValue = zold;
        referenceData.thirdTolastZValue = zold2;
        referenceData.dzdc = dzdc;
        referenceData.mdzdc = mdzdc;

        referenceData.MaxRefIteration = iterations - 1;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        ReferenceCalculationTime = System.currentTimeMillis() - time;

        if(isJulia) {
            calculateJuliaReferencePoint(inputPixel, size, deepZoom, juliaIterations, progress);
        }

        skippedIterations = 0;
        if(isSeriesInUse) {
            calculateSeriesWrapper(size, deepZoom, externalLocation, progress);
        }
        else if(isBLAInUse) {
            calculateBLAWrapper(deepZoom, externalLocation, progress);
        }

    }

    @Override
    public Object calculateR(Object rIn, Object r0In, Object normSquared, Object norm, WorkSpaceData workSpaceData) {

        if(rIn instanceof BigNum) {
            BigNum r = (BigNum)rIn;
            BigNum r0 = (BigNum)r0In;
            BigNum az = ((BigNum)norm);
            BigNum azcube = az.mult((BigNum)normSquared);

            BigNum temp = az.add(r);
            return temp.squareFull().mult(temp).sub(azcube).add(r0);
        }
        else if(rIn instanceof BigIntNum) {
            BigIntNum r = (BigIntNum)rIn;
            BigIntNum r0 = (BigIntNum)r0In;
            BigIntNum az = ((BigIntNum)norm);
            BigIntNum azcube = az.mult((BigIntNum)normSquared);

            BigIntNum temp = az.add(r);
            return temp.square().mult(temp).sub(azcube).add(r0);
        }
        else if(rIn instanceof MpfrBigNum) {
            MpfrBigNum r = (MpfrBigNum)rIn;
            MpfrBigNum r0 = (MpfrBigNum)r0In;
            MpfrBigNum az = (MpfrBigNum)norm;
            MpfrBigNum azcube = az.mult((MpfrBigNum)normSquared, workSpaceData.tempPvar);

            az.add(r, az);
            az.square(r);
            r.mult(az, r);
            r.sub(azcube, r);
            r.add(r0, r);
            return r;
        }
        else if(rIn instanceof MpirBigNum) {
            MpirBigNum r = (MpirBigNum)rIn;
            MpirBigNum r0 = (MpirBigNum)r0In;
            MpirBigNum az = (MpirBigNum)norm;
            MpirBigNum azcube = az.mult((MpirBigNum)normSquared, workSpaceData.tempPvarp);

            az.add(r, az);
            az.square(r);
            r.mult(az, r);
            r.sub(azcube, r);
            r.add(r0, r);
            return r;
        }
        else if(rIn instanceof DoubleDouble) {
            DoubleDouble r = (DoubleDouble)rIn;
            DoubleDouble r0 = (DoubleDouble)r0In;
            DoubleDouble az = ((DoubleDouble)norm);
            DoubleDouble azcube = az.multiply((DoubleDouble)normSquared);

            DoubleDouble temp = az.add(r);
            return temp.sqr().multiply(temp).subtract(azcube).add(r0);
        }
        else if(rIn instanceof Double) {
            double r = (double)rIn;
            double r0 = (double)r0In;
            double az = (double)norm;
            double azcube = az * (double)normSquared;

            double temp = az + r;
            return temp * temp * temp - azcube + r0;
        }
        else {
            Apfloat r = (Apfloat)rIn;
            Apfloat r0 = (Apfloat)r0In;
            Apfloat az = (Apfloat)norm;
            Apfloat azcube = MyApfloat.fp.multiply(az, (Apfloat)normSquared);

            Apfloat temp = MyApfloat.fp.add(az, r);
            return MyApfloat.fp.add(MyApfloat.fp.subtract(MyApfloat.fp.multiply(MyApfloat.fp.multiply(temp, temp), temp), azcube), r0);
        }
    }

    @Override
    protected GenericComplex referenceFunction(GenericComplex z, GenericComplex c, NormComponents normData, GenericComplex[] initialPrecal, GenericComplex[] precalc) {
        if(normData != null) {
            if (burning_ship) {
                z = z.abs_mutable().cubeFast_mutable(normData).plus_mutable(c);
            } else {
                z = z.cubeFast_mutable(normData).plus_mutable(c);
            }
        }
        else {
            if(z instanceof MpfrBigNumComplex) {
                if (burning_ship) {
                    z = z.abs_mutable().cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(c);
                } else {
                    z = z.cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(c);
                }
            }
            else if(z instanceof MpirBigNumComplex) {
                if (burning_ship) {
                    z = z.abs_mutable().cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(c);
                } else {
                    z = z.cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(c);
                }
            }
            else {
                if (burning_ship) {
                    z = z.abs_mutable().cube().plus_mutable(c);
                } else {
                    z = z.cube().plus_mutable(c);
                }
            }
        }

        return z;
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex X = getArrayValue(reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(3).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(3).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube())
//                    .plus_mutable(DeltaSub0);
            return X.square().times_mutable(3).plus_mutable(X.times(3).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
        else {
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double r2 = r*r;
            double i2 = i*i;
            double a2 = a*a;
            double b2 = b*b;
            double ar = a*r;
            double ib = i*b;
            double ab;

            double Dnr = Complex.DiffAbs(r, a);

            ab = r + a;
            Dnr = (r2 - 3 * i2) * Dnr + (2 * ar + a2 - 6 * ib - 3 * b2)* Math.abs(ab);

            double Dni = Complex.DiffAbs(i, b);

            ab = i + b;
            Dni = (3 * r2 - i2) * Dni + (6 * ar + 3 * a2 - 2 * ib - b2) * Math.abs(ab);

            return new Complex(Dnr, Dni).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex X = getArrayDeepValue(referenceDeep, RefIteration);

        if(not_burning_ship) {
             /*return DeltaSubN.times(MantExp.THREE).times_mutable(X.square())
                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.THREE).times_mutable(X))
                    .plus_mutable(DeltaSubN.cube())
                    .plus_mutable(DeltaSub0);*/
            return X.square().times_mutable(MantExp.THREE).plus_mutable(X.times(MantExp.THREE).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.multiply(r);
            MantExp i2 = i.multiply(i);
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab;

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);

            ab = r.add(a);

            Dnr = (r2.subtract(MantExp.THREE.multiply(i2))).multiply_mutable(Dnr)
                    .add_mutable((ar.multiply2().add_mutable(a2).subtract_mutable(MantExp.SIX.multiply(ib)).subtract_mutable(MantExp.THREE.multiply(b2))).multiply_mutable(ab.abs_mutable()));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);

            ab = i.add(b);

            Dni = (MantExp.THREE.multiply(r2).subtract(i2)).multiply_mutable(Dni)
                    .add_mutable((MantExp.SIX.multiply(ar).add_mutable(MantExp.THREE.multiply(a2)).subtract_mutable(ib.multiply2()).subtract_mutable(b2)).multiply_mutable(ab.abs_mutable()));

            return new MantExpComplex(Dnr, Dni).plus_mutable(DeltaSub0);
        }

    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex X = getArrayValue(reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(3).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(3).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return X.square().times_mutable(3).plus_mutable(X.times(3).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
        }
        else {
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double r2 = r*r;
            double i2 = i*i;
            double a2 = a*a;
            double b2 = b*b;
            double ar = a*r;
            double ib = i*b;
            double ab;

            double Dnr = Complex.DiffAbs(r, a);

            ab = r + a;
            Dnr = (r2 - 3 * i2) * Dnr + (2 * ar + a2 - 6 * ib - 3 * b2)* Math.abs(ab);

            double Dni = Complex.DiffAbs(i, b);

            ab = i + b;
            Dni = (3 * r2 - i2) * Dni + (6 * ar + 3 * a2 - 2 * ib - b2) * Math.abs(ab);

            return new Complex(Dnr, Dni);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex X = getArrayDeepValue(referenceDeep, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(MantExp.THREE).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.THREE).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return X.square().times_mutable(MantExp.THREE).plus_mutable(X.times(MantExp.THREE).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.multiply(r);
            MantExp i2 = i.multiply(i);
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab;

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);

            ab = r.add(a);

            Dnr = (r2.subtract(MantExp.THREE.multiply(i2))).multiply_mutable(Dnr)
                    .add_mutable((ar.multiply2().add_mutable(a2).subtract_mutable(MantExp.SIX.multiply(ib)).subtract_mutable(MantExp.THREE.multiply(b2))).multiply_mutable(ab.abs_mutable()));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);

            ab = i.add(b);

            Dni = (MantExp.THREE.multiply(r2).subtract(i2)).multiply_mutable(Dni)
                    .add_mutable((MantExp.SIX.multiply(ar).add_mutable(MantExp.THREE.multiply(a2)).subtract_mutable(ib.multiply2()).subtract_mutable(b2)).multiply_mutable(ab.abs_mutable()));

            return new MantExpComplex(Dnr, Dni);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, ReferenceData data, int RefIteration) {
        Complex X = getArrayValue(data.Reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(3).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(3).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return X.square().times_mutable(3).plus_mutable(X.times(3).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
        }
        else {
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double r2 = r*r;
            double i2 = i*i;
            double a2 = a*a;
            double b2 = b*b;
            double ar = a*r;
            double ib = i*b;
            double ab;

            double Dnr = Complex.DiffAbs(r, a);

            ab = r + a;
            Dnr = (r2 - 3 * i2) * Dnr + (2 * ar + a2 - 6 * ib - 3 * b2)* Math.abs(ab);

            double Dni = Complex.DiffAbs(i, b);

            ab = i + b;
            Dni = (3 * r2 - i2) * Dni + (6 * ar + 3 * a2 - 2 * ib - b2) * Math.abs(ab);

            return new Complex(Dnr, Dni);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, ReferenceDeepData data, int RefIteration) {
        MantExpComplex X = getArrayDeepValue(data.Reference, RefIteration);

        if(not_burning_ship) {
            //            return DeltaSubN.times(MantExp.THREE).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.THREE).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return X.square().times_mutable(MantExp.THREE).plus_mutable(X.times(MantExp.THREE).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
        }
        else {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.multiply(r);
            MantExp i2 = i.multiply(i);
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab;

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);

            ab = r.add(a);

            Dnr = (r2.subtract(MantExp.THREE.multiply(i2))).multiply_mutable(Dnr)
                    .add_mutable((ar.multiply2().add_mutable(a2).subtract_mutable(MantExp.SIX.multiply(ib)).subtract_mutable(MantExp.THREE.multiply(b2))).multiply_mutable(ab.abs_mutable()));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);

            ab = i.add(b);

            Dni = (MantExp.THREE.multiply(r2).subtract(i2)).multiply_mutable(Dni)
                    .add_mutable((MantExp.SIX.multiply(ar).add_mutable(MantExp.THREE.multiply(a2)).subtract_mutable(ib.multiply2()).subtract_mutable(b2)).multiply_mutable(ab.abs_mutable()));

            return new MantExpComplex(Dnr, Dni);
        }
    }

    @Override
    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc, JProgressBar progress) {

        skippedIterations = 0;

        int numCoefficients = ThreadDraw.SERIES_APPROXIMATION_TERMS;

        if (numCoefficients < 2 || dsize.compareTo(MyApfloat.SA_START_SIZE) > 0) {
            return;
        }

        if (numCoefficients > 5) {
            numCoefficients = 5;
        }

        SATerms = numCoefficients;

        long[] logwToThe  = new long[numCoefficients + 1];

        final long[] magCoeff = new long[numCoefficients];

        SASize = loc.getMaxSizeInImage().log2approx();
        logwToThe[1] = SASize;

        for (int i = 2; i <= numCoefficients; i++) {
            logwToThe[i] = logwToThe[1] * i;
        }

        coefficients = new DeepReference(numCoefficients * max_data);

        setSACoefficient(0, 0, new MantExpComplex(1, 0));

        for(int i = 1; i < numCoefficients; i++){
            setSACoefficient(i, 0, new MantExpComplex());
        }

        long oomDiff = ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE;
        int SAMaxSkipIter = ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER;

        int length = deepZoom ? referenceDeep.length() : reference.length();

        int lastIndex = numCoefficients - 1;

        int i;
        for(i = 1; i < length; i++) {

            if(i - 1 > referenceData.MaxRefIteration) {
                skippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                return;
            }

            MantExpComplex ref = null;

            if(deepZoom) {
                ref = getArrayDeepValue(referenceDeep, i - 1);
            }
            else {
                ref = new MantExpComplex(getArrayValue(reference, i - 1));
            }

            MantExpComplex threeRefSquared = ref.square().times_mutable(MantExp.THREE);

            int new_i = i;
            int old_i = (i - 1);

            MantExpComplex coef0i = null;
            MantExpComplex coef1i = null;
            MantExpComplex coef2i = null;
            MantExpComplex coef3i = null;
            MantExpComplex coef4i = null;

            MantExpComplex threeSquareAn = null;
            MantExpComplex sixRef = null;
            MantExpComplex threeSquareBn = null;
            MantExpComplex sixRefAn = null;

            if (numCoefficients >= 1) {
                //An = P * A * (X^(P-1)) + 1;
                coef0i = getSACoefficient(0, old_i);
                MantExpComplex temp = coef0i.times(threeRefSquared).plus_mutable(MantExp.ONE); // 3*Z^2*a_1 + 1
                temp.Reduce();
                magCoeff[0] = temp.log2normApprox() + logwToThe[1];
                setSACoefficient(0, new_i, temp);
            }
            if (numCoefficients >= 2) {
                //Bn = P * B * (X^(P-1)) + ((P*(P-1))/2) * (A^2) * (X^(P-2));
                coef1i = getSACoefficient(1, old_i);
                threeSquareAn = coef0i.square().times_mutable(MantExp.THREE);
                MantExpComplex temp = coef1i.times(threeRefSquared)
                        .plus_mutable(threeSquareAn.times(ref)); //3*Z*a_1^2 + 3*Z^2*a_2
                temp.Reduce();
                magCoeff[1] = temp.log2normApprox() + logwToThe[2];
                setSACoefficient(1, new_i, temp);
            }
            if (numCoefficients >= 3) {
                //Cn = P * C * (X^(P-1)) + (P*(P-1)) * A * B * (X^(P-2)) + ((P*(P-1)*(P-2))/6) * (A^3) * (X^(P-3));
                coef2i = getSACoefficient(2, old_i);
                sixRef = ref.times(MantExp.SIX);
                sixRefAn = sixRef.times(coef0i);
                MantExpComplex temp = coef2i.times(threeRefSquared)
                        .plus_mutable(sixRefAn.times(coef1i))
                        .plus_mutable(coef0i.cube()); // a_1^3 + 6*Z*a_1*a_2 + 3*Z^2*a_3
                temp.Reduce();
                magCoeff[2] = temp.log2normApprox() + logwToThe[3];
                setSACoefficient(2, new_i, temp);
            }
            if (numCoefficients >= 4) {
                //C
                coef3i = getSACoefficient(3, old_i);
                threeSquareBn = coef1i.square().plus_mutable(MantExp.THREE);
                MantExpComplex temp = coef3i.times(threeRefSquared)
                        .plus_mutable(threeSquareAn.times(coef1i))
                        .plus_mutable(threeSquareBn.times_mutable(ref))
                        .plus_mutable(sixRefAn.times(coef2i)); // 3*a_1^2*a_2 + 3*Z*a_2^2 + 6*Z*a_1*a_3 + 3*Z^2*a_4
                temp.Reduce();
                magCoeff[3] = temp.log2normApprox() + logwToThe[4];
                setSACoefficient(3, new_i, temp);
            }
            if (numCoefficients >= 5) {
                //C
                coef4i = getSACoefficient(4, old_i);
                MantExpComplex temp = coef4i.times(threeRefSquared)
                        .plus_mutable(coef0i.times(threeSquareBn))
                        .plus_mutable(threeSquareAn.times(coef2i))
                        .plus_mutable(sixRef.times(coef1i).times_mutable(coef2i))
                        .plus_mutable(sixRefAn.times(coef3i));
                temp.Reduce();
                magCoeff[4] = temp.log2normApprox() + logwToThe[5];
                setSACoefficient(4, new_i, temp);
                // 3*a_1*a_2^2 + 3*a_1^2*a_3 + 6*Z*a_2*a_3 + 6*Z*a_1*a_4 + 3*Z^2*a_5
            }

            //Check to see if the approximation is no longer valid. The validity is checked if an arbitrary point we approximated differs from the point it should be by too much. That is the tolerancy which scales with the depth.
            //if (coefficients[numCoefficients - 2][new_i].times(tempLimit).norm_squared().compareTo(coefficients[numCoefficients - 1][new_i].times(DeltaSub0ToThe[numCoefficients]).norm_squared()) < 0) {
            //if(coefficients[numCoefficients - 2][new_i].norm_squared().divide(coefficients[numCoefficients - 1][new_i].norm_squared()).compareTo(tempLimit2) < 0) {
            if(i > 1 && (i >= SAMaxSkipIter || isLastTermNotNegligible(magCoeff, oomDiff, lastIndex))) {
                //|Bn+1 * d^2 * tolerance| < |Cn+1 * d^3|
                //When we're breaking here, it means that we've found a point where the approximation no longer works. Returning that would create a messed up image. We should move a little further back to get an approximation that is good.
                skippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
                return;
            }

            if(progress != null && i % 1000 == 0) {
                progress.setValue(i);
                progress.setString(SA_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (i) / progress.getMaximum() * 100)) + "%");
            }

        }

        i = length - 1;
        skippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
    }

    @Override
    public void function(GenericComplex[] complex) {
        if(complex[0] instanceof MpfrBigNumComplex) {
            if(not_burning_ship) {
                complex[0] = complex[0].cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(complex[1]);
            }
            else {
                complex[0] = complex[0].abs_mutable().cube_mutable(workSpaceData.temp1, workSpaceData.temp2, workSpaceData.temp3, workSpaceData.temp4).plus_mutable(complex[1]);
            }
        }
        else if(complex[0] instanceof MpirBigNumComplex) {
            if(not_burning_ship) {
                complex[0] = complex[0].cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(complex[1]);
            }
            else {
                complex[0] = complex[0].abs_mutable().cube_mutable(workSpaceData.temp1p, workSpaceData.temp2p, workSpaceData.temp3p, workSpaceData.temp4p).plus_mutable(complex[1]);
            }
        }
        else {
            if (not_burning_ship) {
                complex[0] = complex[0].cube().plus_mutable(complex[1]);
            } else {
                complex[0] = complex[0].abs_mutable().cube().plus_mutable(complex[1]);
            }
        }
    }

    @Override
    public Complex getBlaA(Complex Z) {
        return Z.square().times_mutable(3);
    }

    @Override
    public MantExpComplex getBlaA(MantExpComplex Z) {
        return Z.square().times_mutable(MantExp.THREE);
    }

    @Override
    public boolean supportsSeriesApproximation() {
        return !burning_ship && !isJulia;
    }

    @Override
    public boolean supportsBilinearApproximation() {
        return !burning_ship && !isJulia;
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (burning_ship ? "-Burning Ship" : "") + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public boolean supportsBignum() { return true;}

    @Override
    public boolean supportsBigIntnum() {
        return true;
    }

    @Override
    public boolean supportsPeriod() {
        return !burning_ship && !isJulia;
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean supportsMpirBignum() { return true;}

}
