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
import fractalzoomer.core.DeepReference;
import fractalzoomer.core.location.Location;
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
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;
import static fractalzoomer.main.Constants.SA_CALCULATION_STR;

/**
 *
 * @author hrkalona
 */
public class MandelbrotCubed extends Julia {

    private MandelVariation type;
    private MandelVariation type2;

    public MandelbrotCubed(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
    }

    public MandelbrotCubed(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        this.burning_ship = burning_ship;

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
        return !isJulia || (isJulia && !juliter);
    }

    @Override
    public void calculateReferencePoint(GenericComplex inputPixel, Apfloat size, boolean deepZoom, int iterations, Location externalLocation, JProgressBar progress) {

        long time = System.currentTimeMillis();

        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        boolean lowPrecReferenceOrbitNeeded = !(deepZoom && ThreadDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation());

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                Reference = new double[max_iterations << 1];
            }

            if(isJulia) {
                ReferenceSubPixel = new double[max_iterations << 1];
            }

            if (deepZoom) {
                ReferenceDeep = new DeepReference(max_iterations);

                if(isJulia) {
                    ReferenceSubPixelDeep = new DeepReference(max_iterations);
                }

            }
        } else if (max_iterations > getReferenceLength()) {
            if(lowPrecReferenceOrbitNeeded) {
                Reference = Arrays.copyOf(Reference, max_iterations << 1);
            }

            if(isJulia) {
                ReferenceSubPixel = Arrays.copyOf(ReferenceSubPixel, max_iterations << 1);
            }

            if (deepZoom) {
                ReferenceDeep.resize(max_iterations);

                if(isJulia) {
                    ReferenceSubPixelDeep.resize(max_iterations);
                }

            }

            System.gc();
        }

        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared;

        if(iterations == 0) {
            DetectedPeriod = 0;
        }

        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE;
        if(useBignum) {
            BigNumComplex bn = inputPixel.toBigNumComplex();
            z = iterations == 0 ? (isJulia ? bn : new BigNumComplex()) : lastZValue;
            c = isJulia ? new BigNumComplex(seed) : bn;
            zold = iterations == 0 ? new BigNumComplex() : secondTolastZValue;
            zold2 = iterations == 0 ? new BigNumComplex() : thirdTolastZValue;
            start = isJulia ? bn : new BigNumComplex();
            c0 = c;
            pixel = bn;
            normSquared = new BigNum();
            minValue = iterations == 0 ? BigNum.getMax() : minValue;
        }
        else {
            z = iterations == 0 ? (isJulia ? inputPixel : new BigComplex()) : lastZValue;
            c = isJulia ? new BigComplex(seed) : inputPixel;
            zold = iterations == 0 ? new BigComplex() : secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : thirdTolastZValue;
            start = isJulia ? inputPixel : new BigComplex();
            c0 = c;
            pixel = inputPixel;
            normSquared = Apfloat.ZERO;
            minValue = iterations == 0 ? new MyApfloat(Integer.MAX_VALUE) : minValue;
        }

        Location loc = new Location();

        refPoint = inputPixel;
        refPointSmall = refPoint.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
        }

        boolean isSeriesInUse = ThreadDraw.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation();
        boolean isBLAInUse = ThreadDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation();
        boolean detectPeriod = ThreadDraw.DETECT_PERIOD && supportsPeriod();
        RefType = getRefType();

        boolean preCalcNormData = detectPeriod || bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        NormComponents normData = null;

        for (; iterations < max_iterations; iterations++) {

            if(lowPrecReferenceOrbitNeeded) {
                Complex cz = z.toComplex();
                if (cz.isInfinite()) {
                    break;
                }

                setArrayValue(Reference, iterations, cz);
            }

            if(isJulia) {
                GenericComplex zsubpixel = z.sub(pixel);
                setArrayValue(ReferenceSubPixel, iterations, zsubpixel.toComplex());

                if(deepZoom) {
                    setArrayDeepValue(ReferenceSubPixelDeep, iterations, loc.getMantExpComplex(zsubpixel));
                }
            }

            if(deepZoom) {
                setArrayDeepValue(ReferenceDeep, iterations, loc.getMantExpComplex(z));
                //ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents();
                normSquared = normData.normSquared;
            }

            if(detectPeriod) {
                if(useBignum) {
                    if(iterations > 0 && ((BigNum)normSquared).compare((BigNum)minValue) < 0) {
                        DetectedPeriod = iterations;
                        minValue = normSquared;
                    }
                }
                else {
                    if(iterations > 0 && ((Apfloat)normSquared).compareTo((Apfloat)minValue) < 0) {
                        DetectedPeriod = iterations;
                        minValue = normSquared;
                    }
                }
            }

            if (iterations > 0 && bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel)) {
                break;
            }

            zold2 = zold;
            zold = z;

            try {
                if(preCalcNormData) {
                    if (burning_ship) {
                        z = z.abs().cubeFast(normData).plus(c);
                    } else {
                        z = z.cubeFast(normData).plus(c);
                    }
                }
                else {
                    if (burning_ship) {
                        z = z.abs().cube().plus(c);
                    } else {
                        z = z.cube().plus(c);
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

        lastZValue = z;
        secondTolastZValue = zold;
        thirdTolastZValue = zold2;

        MaxRefIteration = iterations - 1;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        ReferenceCalculationTime = System.currentTimeMillis() - time;

        skippedIterations = 0;
        if(isSeriesInUse) {
            calculateSeriesWrapper(size, deepZoom, externalLocation, progress);
        }
        else if(isBLAInUse) {
            calculateBLAWrapper(deepZoom, externalLocation, progress);
        }

    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex X = getArrayValue(Reference, RefIteration);

        if(burning_ship) {
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
        else
        {
//            return DeltaSubN.times(3).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(3).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube())
//                    .plus_mutable(DeltaSub0);
            return X.square().times_mutable(3).plus_mutable(X.times(3).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex X = getArrayDeepValue(ReferenceDeep, RefIteration);

        if(burning_ship) {
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
        else {
            /*return DeltaSubN.times(MantExp.THREE).times_mutable(X.square())
                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.THREE).times_mutable(X))
                    .plus_mutable(DeltaSubN.cube())
                    .plus_mutable(DeltaSub0);*/
            return X.square().times_mutable(MantExp.THREE).plus_mutable(X.times(MantExp.THREE).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN).plus_mutable(DeltaSub0);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex X = getArrayValue(Reference, RefIteration);

        if(burning_ship) {

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
        else {
//            return DeltaSubN.times(3).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(3).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return X.square().times_mutable(3).plus_mutable(X.times(3).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex X = getArrayDeepValue(ReferenceDeep, RefIteration);

        if(burning_ship) {
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
        else {
//            return DeltaSubN.times(MantExp.THREE).times_mutable(X.square())
//                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.THREE).times_mutable(X))
//                    .plus_mutable(DeltaSubN.cube());
            return X.square().times_mutable(MantExp.THREE).plus_mutable(X.times(MantExp.THREE).plus_mutable(DeltaSubN).times_mutable(DeltaSubN)).times_mutable(DeltaSubN);
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

        int length = deepZoom ? ReferenceDeep.length() : (Reference.length >> 1);

        int lastIndex = numCoefficients - 1;

        int i;
        for(i = 1; i < length; i++) {

            if(i - 1 > MaxRefIteration) {
                skippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                return;
            }

            MantExpComplex ref = null;

            if(deepZoom) {
                ref = getArrayDeepValue(ReferenceDeep, i - 1);
            }
            else {
                ref = new MantExpComplex(getArrayValue(Reference, i - 1));
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
        return super.getRefType() + (burning_ship ? "-Burning Ship" : "") + (isJulia ? "-Julia-" + seed : "");
    }

    @Override
    public boolean supportsBignum() { return true;}

    @Override
    public boolean supportsPeriod() {
        return !burning_ship && !isJulia;
    }

}
