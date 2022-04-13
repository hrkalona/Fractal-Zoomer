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
package fractalzoomer.functions.magnet;

import fractalzoomer.core.*;
import fractalzoomer.core.DeepReference;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
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
public class Magnet1 extends MagnetType {
    private static Complex C12s8;
    private static Complex Cm2;
    private static Complex Cm24;
    private static Complex Cm212;
    private static Complex Cm23;
    private static Complex Cm6;
    private static Complex Precalc1;
    private static Complex Precalc2;
    private static Complex Precalc3;
    private static Complex Precalc4;
    private static Complex Precalc5;
    private static Complex Precalc6;
    private static Complex Precalc7;
    private static Complex Precalc8;
    private static Complex Precalc9;
    private static Complex Precalc10;
    private static Complex Precalc11;
    private static Complex Precalc12;
    private static Complex Precalc13;
    private static Complex Precalc14;

    private static MantExpComplex C12s8Deep;
    private static MantExpComplex Cm2Deep;
    private static MantExpComplex Cm24Deep;
    private static MantExpComplex Cm212Deep;
    private static MantExpComplex Cm23Deep;
    private static MantExpComplex Cm6Deep;
    private static MantExpComplex Precalc1Deep;
    private static MantExpComplex Precalc2Deep;
    private static MantExpComplex Precalc3Deep;
    private static MantExpComplex Precalc4Deep;
    private static MantExpComplex Precalc5Deep;
    private static MantExpComplex Precalc6Deep;
    private static MantExpComplex Precalc7Deep;
    private static MantExpComplex Precalc8Deep;
    private static MantExpComplex Precalc9Deep;
    private static MantExpComplex Precalc10Deep;
    private static MantExpComplex Precalc11Deep;
    private static MantExpComplex Precalc12Deep;
    private static MantExpComplex Precalc13Deep;
    private static MantExpComplex Precalc14Deep;

    public Magnet1() {
        super();
    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        convergent_bailout = 1E-12;

        power = 4;

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
            init_val = new InitialValue(0, 0);
        }

        switch (out_coloring_algorithm) {

            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-12;

        power = 4;

        switch (out_coloring_algorithm) {
        
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                break;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(0, 0);
    }

    //orbit
    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        power = 4;

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
            init_val = new InitialValue(0, 0);
        }

    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
        power = 4;
        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(0, 0);
    }

    @Override
    public void function(Complex[] complex) {

        complex[0] = (complex[0].square().plus_mutable(complex[1].sub(1))).divide_mutable(complex[0].times(2).plus_mutable(complex[1].sub(2))).square_mutable();

    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || (isJulia && !juliter);
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

        if (iterations == 0) {
            Reference = new double[max_iterations << 1];

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
            Reference = Arrays.copyOf(Reference, max_iterations << 1);

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

        BigComplex pixel = (BigComplex)gpixel;

        BigComplex z = iterations == 0 ? (isJulia ? pixel : new BigComplex()) : (BigComplex)lastZValue;

        BigComplex c = isJulia ? new BigComplex(seed) : pixel;

        BigComplex zold = iterations == 0 ? new BigComplex() : (BigComplex)secondTolastZValue;
        BigComplex zold2 = iterations == 0 ? new BigComplex() : (BigComplex)thirdTolastZValue;
        BigComplex start = isJulia ? pixel : new BigComplex();
        BigComplex c0 = c;

        Object normSquared = Apfloat.ZERO;

        Location loc = new Location();

        refPoint = pixel;
        refPointSmall = refPoint.toComplex();

        Apfloat three = new MyApfloat(3.0);
        Apfloat four = new MyApfloat(4.0);
        Apfloat five = new MyApfloat(5.0);
        Apfloat six = new MyApfloat(6.0);
        Apfloat seven = new MyApfloat(7.0);
        Apfloat eight = new MyApfloat(8.0);
        Apfloat twelve = new MyApfloat(12.0);
        Apfloat sixteen = new MyApfloat(16.0);
        Apfloat twentyfour = new MyApfloat(24.0);
        Apfloat thirtytwo = new MyApfloat(32.0);

        C = c.toComplex();

        BigComplex c2big = c.times(MyApfloat.TWO);

        BigComplex cs2big = c.sub(MyApfloat.TWO);
        Cm2 = cs2big.toComplex();

        BigComplex cs6big = c.sub(six);
        Cm6 = cs6big.toComplex();

        BigComplex cs24big = cs2big.times(four);
        Cm24 = cs24big.toComplex();

        BigComplex cs212big = cs2big.times(twelve);
        Cm212 = cs212big.toComplex();

        BigComplex cs23big = cs2big.times(three);
        Cm23 = cs23big.toComplex();

        BigComplex c12big = c.times(twelve);

        BigComplex c12s8big = c12big.sub(eight);
        C12s8 = c12s8big.toComplex();

        BigComplex c12s6big = c12big.sub(six);

        BigComplex csqrbig = c.square();

        BigComplex csqr4big = csqrbig.times(four);

        BigComplex csqr7big = csqrbig.times(seven);

        BigComplex csqr24big = csqrbig.times(twentyfour);

        BigComplex ccubebig = c.cube();

        BigComplex c4big = c.times(four);
        BigComplex csqrsc4big = csqrbig.sub(c4big);

        BigComplex precalclbig = csqrbig.sub(c.times(three)).plus(MyApfloat.TWO);
        Precalc1 = precalclbig.toComplex();

        BigComplex precalc2big = csqrsc4big.plus(four);
        Precalc2 = precalc2big.toComplex();

        BigComplex precalc3big = csqrsc4big.plus(three).times(MyApfloat.TWO);
        Precalc3 = precalc3big.toComplex();

        BigComplex precalc4big = ccubebig.sub(csqrbig.times(six));
        Precalc4 = precalc4big.toComplex();

        BigComplex precalc5big = precalc4big.plus(c12s8big).times(eight);
        Precalc5 = precalc5big.toComplex();

        BigComplex precalc6big = ccubebig.sub(csqrbig.times(seven)).plus(c12s6big);
        Precalc6 = precalc6big.toComplex();

        BigComplex precalc7big = csqrbig.sub(c.times(six)).plus(four);
        Precalc7 = precalc7big.toComplex();

        BigComplex precalc8big = c.sub(three).times(MyApfloat.TWO);
        Precalc8 = precalc8big.toComplex();

        BigComplex precalc9big = csqrbig.sub(c2big).plus(MyApfloat.ONE);
        Precalc9 = precalc9big.toComplex();

        BigComplex precalc10big = c.fourth().sub(ccubebig.times(eight)).plus(csqr24big).sub(c.times(thirtytwo)).plus(sixteen);
        Precalc10 = precalc10big.toComplex();

        BigComplex precalc11big = ccubebig.sub(csqr7big).plus(c12s6big);
        Precalc11 = precalc11big.toComplex();

        BigComplex precalc12big = ccubebig.negative().plus(csqr4big).sub(c.times(five)).plus(MyApfloat.TWO);
        Precalc12 = precalc12big.toComplex();

        BigComplex precalc13big = c2big.sub(three);
        Precalc13 = precalc13big.toComplex();

        BigComplex precalc14big = precalclbig.times(MyApfloat.TWO);
        Precalc14 = precalc14big.toComplex();


        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
            Cdeep = loc.getMantExpComplex(c);
            C12s8Deep = loc.getMantExpComplex(c12s8big);
            Cm2Deep = loc.getMantExpComplex(cs2big);
            Cm24Deep = loc.getMantExpComplex(cs24big);
            Cm212Deep = loc.getMantExpComplex(cs212big);
            Cm23Deep = loc.getMantExpComplex(cs23big);
            Cm6Deep = loc.getMantExpComplex(cs6big);
            Precalc1Deep = loc.getMantExpComplex(precalclbig);
            Precalc2Deep = loc.getMantExpComplex(precalc2big);
            Precalc3Deep = loc.getMantExpComplex(precalc3big);
            Precalc4Deep = loc.getMantExpComplex(precalc4big);
            Precalc5Deep = loc.getMantExpComplex(precalc5big);
            Precalc6Deep = loc.getMantExpComplex(precalc6big);
            Precalc7Deep = loc.getMantExpComplex(precalc7big);
            Precalc8Deep = loc.getMantExpComplex(precalc8big);
            Precalc9Deep = loc.getMantExpComplex(precalc9big);
            Precalc10Deep = loc.getMantExpComplex(precalc10big);
            Precalc11Deep = loc.getMantExpComplex(precalc11big);
            Precalc12Deep = loc.getMantExpComplex(precalc12big);
            Precalc13Deep = loc.getMantExpComplex(precalc13big);
            Precalc14Deep = loc.getMantExpComplex(precalc14big);
        }

        RefType = getRefType();

        Apfloat convergentB = new MyApfloat(convergent_bailout);
        BigComplex one = new BigComplex(MyApfloat.ONE, MyApfloat.ZERO);

        boolean preCalcNormData = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        NormComponents normData = null;

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            setArrayValue(Reference, iterations, cz);

            if(isJulia) {
                BigComplex zsubpixel = z.sub(pixel);
                setArrayValue(ReferenceSubPixel, iterations, zsubpixel.toComplex());

                if(deepZoom) {
                    setArrayDeepValue(ReferenceSubPixelDeep, iterations, loc.getMantExpComplex(zsubpixel));
                }
            }

            if(deepZoom) {
                setArrayDeepValue(ReferenceDeep, iterations, loc.getMantExpComplex(z));
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents();
                normSquared = normData.normSquared;
            }

            if (iterations > 0 && (z.distance_squared(one).compareTo(convergentB) <= 0 || bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel))) {
                break;
            }

            zold2 = zold;
            zold = z;

            try {
                if(preCalcNormData) {
                    z = (z.squareFast(normData).plus(c.sub(MyApfloat.ONE))).divide(z.times(MyApfloat.TWO).plus(c.sub(MyApfloat.TWO))).square();
                }
                else {
                    z = (z.square().plus(c.sub(MyApfloat.ONE))).divide(z.times(MyApfloat.TWO).plus(c.sub(MyApfloat.TWO))).square();
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

    }


    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex Z = getArrayValue(Reference, RefIteration);
        Complex z = DeltaSubN;
        Complex c = DeltaSub0;

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)


        Complex Zsqr = Z.square();
        Complex Zcube = Z.cube();
        Complex Zfourth = Z.fourth();
        Complex csqr = c.square();
        Complex zsqr = z.square();

        Complex Cm24Z = Cm24.times(Z);

        Complex temp1Z = Precalc2.times(Z);
        Complex temp1Zsqr = Precalc2.times(Zsqr);
        Complex temp2 = Precalc2.plus(Cm24Z).plus_mutable(Zsqr.times(4)); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        Complex temp3 = Precalc4.plus(Cm212.times(Zsqr)).plus_mutable(Zcube.times(8)).plus_mutable(temp1Z.times(6));//C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z
        Complex temp8 = Cm2.times(Zcube); //(C - 2)*Z^3
        Complex temp9 = temp2.times(c);
        Complex temp10 = temp3.plus(C12s8);

        //        C^4
        //        + 32*(C - 2)*Z^3
        //        + 16*Z^4
        //        - 8*C^3
        //        + 24*(C^2 - 4*C + 4)*Z^2
        //        + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2
        //        + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2
        //        + 24*C^2
        //        + 8*(C^3 - 6*C^2 + 12*C - 8)*Z
        //        + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c
        //        + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z
        //        - 32*C
        //        + 16
        Complex denom = Precalc10
                .plus(temp8.times(32))
                .plus_mutable(Zfourth.times(16))
                .plus_mutable(temp1Zsqr.times(24))
                .plus_mutable(temp2.times(csqr))
                .plus_mutable(temp2.times(zsqr).times_mutable(4))
                .plus_mutable(Precalc5.times(Z))
                .plus_mutable(temp10.times(c).times_mutable(2))
                .plus_mutable((temp10.plus(temp9)).times(z).times_mutable(4));


        Complex temp5 = Precalc8.times(Zsqr); // 2*(C - 3)*Z^2


        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z

        Complex Zfifth2 = Z.fifth().times_mutable(2);
        Complex Zcube4 = Zcube.times(4);

        Complex a1 = temp2.times(z.fourth());

        Complex b1 = (Cm24.times(Zsqr).plus_mutable(Zcube4).plus_mutable(temp1Z)).times(z.cube()).times_mutable(4);

        Complex c1 = (Zfourth.plus(temp5).sub_mutable(Cm24Z).plus_mutable(Precalc13)).times_mutable(csqr);

        Complex d1 = (temp8.times(12).plus_mutable(Zfourth.times(10)).plus_mutable(Precalc11).plus_mutable(temp1Zsqr.times(3))
                .plus_mutable(Precalc1.times(4).times_mutable(Z)).plus_mutable(temp9)).times_mutable(2).times_mutable(zsqr);

        Complex e1 = (Cm6.times(Zfourth).plus_mutable(Zfifth2).plus_mutable(Precalc7.times(Zsqr)).plus_mutable(Zcube4)
                .sub_mutable(Precalc3.times(Z)).plus_mutable(Precalc1)).times_mutable(2).times_mutable(c);

        Complex f1 = (Cm23.times(Zfourth).plus_mutable(Zfifth2).plus_mutable(Precalc2.times(Zcube)).plus_mutable(Precalc12).plus_mutable(Precalc14.times(Zsqr))
                .plus_mutable(Precalc6.times(Z)).sub_mutable((Zfourth.sub(temp5).sub_mutable(Zcube4).plus_mutable(Precalc9).sub_mutable(temp1Z)).times_mutable(c))
                ).times_mutable(4).times_mutable(z);

        Complex num = a1
                .plus_mutable(b1)
                .sub_mutable(c1)
                .plus_mutable(d1)
                .sub_mutable(e1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {


        MantExpComplex Z = getArrayDeepValue(ReferenceDeep, RefIteration);
        MantExpComplex z = DeltaSubN;
        MantExpComplex c = DeltaSub0;

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)


        MantExpComplex Zsqr = Z.square();
        MantExpComplex Zcube = Z.cube();
        MantExpComplex Zfourth = Z.fourth();
        MantExpComplex csqr = c.square();
        MantExpComplex zsqr = z.square();

        MantExpComplex Cm24Z = Cm24Deep.times(Z);

        MantExpComplex temp1Z = Precalc2Deep.times(Z);
        MantExpComplex temp1Zsqr = Precalc2Deep.times(Zsqr);
        MantExpComplex temp2 = Precalc2Deep.plus(Cm24Z).plus_mutable(Zsqr.times4()); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        MantExpComplex temp3 = Precalc4Deep.plus(Cm212Deep.times(Zsqr)).plus_mutable(Zcube.times8()).plus_mutable(temp1Z.times(MantExp.SIX));//C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z
        MantExpComplex temp8 = Cm2Deep.times(Zcube); //(C - 2)*Z^3
        MantExpComplex temp9 = temp2.times(c);
        MantExpComplex temp10 = temp3.plus(C12s8Deep);

        //        C^4
        //        + 32*(C - 2)*Z^3
        //        + 16*Z^4
        //        - 8*C^3
        //        + 24*(C^2 - 4*C + 4)*Z^2
        //        + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2
        //        + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2
        //        + 24*C^2
        //        + 8*(C^3 - 6*C^2 + 12*C - 8)*Z
        //        + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c
        //        + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z
        //        - 32*C
        //        + 16
        MantExpComplex denom = Precalc10Deep
                .plus(temp8.times32())
                .plus_mutable(Zfourth.times16())
                .plus_mutable(temp1Zsqr.times(MantExp.TWENTYFOUR))
                .plus_mutable(temp2.times(csqr))
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(Precalc5Deep.times(Z))
                .plus_mutable(temp10.times(c).times2_mutable())
                .plus_mutable((temp10.plus(temp9)).times(z).times4_mutable());


        MantExpComplex temp5 = Precalc8Deep.times(Zsqr); // 2*(C - 3)*Z^2


        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z

        MantExpComplex Zfifth2 = Z.fifth().times2_mutable();
        MantExpComplex Zcube4 = Zcube.times4();

        MantExpComplex a1 = temp2.times(z.fourth());

        MantExpComplex b1 = (Cm24Deep.times(Zsqr).plus_mutable(Zcube4).plus_mutable(temp1Z)).times(z.cube()).times4_mutable();

        MantExpComplex c1 = (Zfourth.plus(temp5).sub_mutable(Cm24Z).plus_mutable(Precalc13Deep)).times_mutable(csqr);

        MantExpComplex d1 = (temp8.times(MantExp.TWELVE).plus_mutable(Zfourth.times(MantExp.TEN)).plus_mutable(Precalc11Deep).plus_mutable(temp1Zsqr.times(MantExp.THREE))
                .plus_mutable(Precalc1Deep.times4().times_mutable(Z)).plus_mutable(temp9)).times2_mutable().times_mutable(zsqr);

        MantExpComplex e1 = (Cm6Deep.times(Zfourth).plus_mutable(Zfifth2).plus_mutable(Precalc7Deep.times(Zsqr)).plus_mutable(Zcube4)
                .sub_mutable(Precalc3Deep.times(Z)).plus_mutable(Precalc1Deep)).times2_mutable().times_mutable(c);

        MantExpComplex f1 = (Cm23Deep.times(Zfourth).plus_mutable(Zfifth2).plus_mutable(Precalc2Deep.times(Zcube)).plus_mutable(Precalc12Deep).plus_mutable(Precalc14Deep.times(Zsqr))
                .plus_mutable(Precalc6Deep.times(Z)).sub_mutable((Zfourth.sub(temp5).sub_mutable(Zcube4).plus_mutable(Precalc9Deep).sub_mutable(temp1Z)).times_mutable(c))
        ).times4_mutable().times_mutable(z);

        MantExpComplex num = a1
                .plus_mutable(b1)
                .sub_mutable(c1)
                .plus_mutable(d1)
                .sub_mutable(e1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex Z = getArrayValue(Reference, RefIteration);
        Complex z = DeltaSubN;

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)


        Complex Zsqr = Z.square();
        Complex Zcube = Z.cube();
        Complex Zfourth = Z.fourth();
        Complex zsqr = z.square();

        Complex Cm24Z = Cm24.times(Z);

        Complex temp1Z = Precalc2.times(Z);
        Complex temp1Zsqr = Precalc2.times(Zsqr);
        Complex temp2 = Precalc2.plus(Cm24Z).plus_mutable(Zsqr.times(4)); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        Complex temp3 = Precalc4.plus(Cm212.times(Zsqr)).plus_mutable(Zcube.times(8)).plus_mutable(temp1Z.times(6));//C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z
        Complex temp8 = Cm2.times(Zcube); //(C - 2)*Z^3
        Complex temp10 = temp3.plus(C12s8);

        //        C^4
        //        + 32*(C - 2)*Z^3
        //        + 16*Z^4
        //        - 8*C^3
        //        + 24*(C^2 - 4*C + 4)*Z^2
        //        + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2
        //        + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2
        //        + 24*C^2
        //        + 8*(C^3 - 6*C^2 + 12*C - 8)*Z
        //        + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c
        //        + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z
        //        - 32*C
        //        + 16
        Complex denom = Precalc10
                .plus(temp8.times(32))
                .plus_mutable(Zfourth.times(16))
                .plus_mutable(temp1Zsqr.times(24))
                .plus_mutable(temp2.times(zsqr).times_mutable(4))
                .plus_mutable(Precalc5.times(Z))
                .plus_mutable(temp10.times(z).times_mutable(4));



        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z

        Complex Zfifth2 = Z.fifth().times_mutable(2);
        Complex Zcube4 = Zcube.times(4);

        Complex a1 = temp2.times(z.fourth());

        Complex b1 = (Cm24.times(Zsqr).plus_mutable(Zcube4).plus_mutable(temp1Z)).times(z.cube()).times_mutable(4);

        Complex d1 = (temp8.times(12).plus_mutable(Zfourth.times(10)).plus_mutable(Precalc11).plus_mutable(temp1Zsqr.times(3))
                .plus_mutable(Precalc1.times(4).times_mutable(Z))).times_mutable(2).times_mutable(zsqr);

        Complex f1 = (Cm23.times(Zfourth).plus_mutable(Zfifth2).plus_mutable(Precalc2.times(Zcube)).plus_mutable(Precalc12).plus_mutable(Precalc14.times(Zsqr))
                .plus_mutable(Precalc6.times(Z))
        ).times_mutable(4).times_mutable(z);

        Complex num = a1
                .plus_mutable(b1)
                .plus_mutable(d1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(ReferenceDeep, RefIteration);
        MantExpComplex z = DeltaSubN;

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)


        MantExpComplex Zsqr = Z.square();
        MantExpComplex Zcube = Z.cube();
        MantExpComplex Zfourth = Z.fourth();
        MantExpComplex zsqr = z.square();

        MantExpComplex Cm24Z = Cm24Deep.times(Z);

        MantExpComplex temp1Z = Precalc2Deep.times(Z);
        MantExpComplex temp1Zsqr = Precalc2Deep.times(Zsqr);
        MantExpComplex temp2 = Precalc2Deep.plus(Cm24Z).plus_mutable(Zsqr.times4()); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        MantExpComplex temp3 = Precalc4Deep.plus(Cm212Deep.times(Zsqr)).plus_mutable(Zcube.times8()).plus_mutable(temp1Z.times(MantExp.SIX));//C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z
        MantExpComplex temp8 = Cm2Deep.times(Zcube); //(C - 2)*Z^3
        MantExpComplex temp10 = temp3.plus(C12s8Deep);

        //        C^4
        //        + 32*(C - 2)*Z^3
        //        + 16*Z^4
        //        - 8*C^3
        //        + 24*(C^2 - 4*C + 4)*Z^2
        //        + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2
        //        + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2
        //        + 24*C^2
        //        + 8*(C^3 - 6*C^2 + 12*C - 8)*Z
        //        + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c
        //        + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z
        //        - 32*C
        //        + 16
        MantExpComplex denom = Precalc10Deep
                .plus(temp8.times32())
                .plus_mutable(Zfourth.times16())
                .plus_mutable(temp1Zsqr.times(MantExp.TWENTYFOUR))
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(Precalc5Deep.times(Z))
                .plus_mutable(temp10.times(z).times4_mutable());


        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z

        MantExpComplex Zfifth2 = Z.fifth().times2_mutable();
        MantExpComplex Zcube4 = Zcube.times4();

        MantExpComplex a1 = temp2.times(z.fourth());

        MantExpComplex b1 = (Cm24Deep.times(Zsqr).plus_mutable(Zcube4).plus_mutable(temp1Z)).times(z.cube()).times4_mutable();

        MantExpComplex d1 = (temp8.times(MantExp.TWELVE).plus_mutable(Zfourth.times(MantExp.TEN)).plus_mutable(Precalc11Deep).plus_mutable(temp1Zsqr.times(MantExp.THREE))
                .plus_mutable(Precalc1Deep.times4().times_mutable(Z))).times2_mutable().times_mutable(zsqr);

        MantExpComplex f1 = (Cm23Deep.times(Zfourth).plus_mutable(Zfifth2).plus_mutable(Precalc2Deep.times(Zcube)).plus_mutable(Precalc12Deep).plus_mutable(Precalc14Deep.times(Zsqr))
                .plus_mutable(Precalc6Deep.times(Z))
        ).times4_mutable().times_mutable(z);

        MantExpComplex num = a1
                .plus_mutable(b1)
                .plus_mutable(d1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);

    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + seed : "");
    }
}
