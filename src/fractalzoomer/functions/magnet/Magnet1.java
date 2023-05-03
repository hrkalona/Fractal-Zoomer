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
import fractalzoomer.core.location.Location;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;

/**
 *
 * @author hrkalona
 */
public class Magnet1 extends MagnetType {
    private static GenericComplex cs24big;
    private GenericComplex cs23big;
    private static GenericComplex c12s8big;
    private static GenericComplex precalc2big;
    private static GenericComplex precalc4big;
    private static GenericComplex cs2big;
    private static GenericComplex precalc11big;
    private static GenericComplex precalc10big;
    private static GenericComplex precalclbig;
    private static GenericComplex cs212big;
    private static GenericComplex precalc5big;
    private static GenericComplex cs1big;
    private static GenericComplex precalc6big;
    private static GenericComplex precalc12big;
    private static GenericComplex precalc14big;

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
            progress.setMaximum(max_ref_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        boolean lowPrecReferenceOrbitNeeded = !needsOnlyExtendedReferenceOrbit(deepZoom, false);

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                if(!isJulia) {
                    referenceData.createAndSetShortcut(max_ref_iterations, false, 8);
                }
                else {
                    referenceData.createAndSetShortcut(max_ref_iterations, false, new int[] {0, 1, 2, 3, 6});
                }
            }
            else {
                referenceData.deallocate();
            }

            if (deepZoom) {
                if(!isJulia) {
                    referenceDeepData.createAndSetShortcut(max_ref_iterations, false, 8);
                }
                else {
                    referenceDeepData.createAndSetShortcut(max_ref_iterations, false, new int[] {0, 1, 2, 3, 6});
                }
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

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE && bigNumLib != Constants.BIGNUM_APFLOAT;


        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared, root;

        if(useBignum ) {
            if (bigNumLib == Constants.BIGNUM_MPFR) {
                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? (isJulia ? bn : new MpfrBigNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new MpfrBigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? new MpfrBigNumComplex(bn) : new MpfrBigNumComplex();
                c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
                pixel = new MpfrBigNumComplex(bn);
                root = new MpfrBigNum(1);
            }
            else if (bigNumLib == Constants.BIGNUM_MPIR) {
                MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
                z = iterations == 0 ? (isJulia ? bn : new MpirBigNumComplex()) : referenceData.lastZValue;
                c = isJulia ? getSeed(useBignum, bigNumLib) : bn;
                zold = iterations == 0 ? new MpirBigNumComplex() : referenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpirBigNumComplex() : referenceData.thirdTolastZValue;
                start = isJulia ? new MpirBigNumComplex(bn) : new MpirBigNumComplex();
                c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
                pixel = new MpirBigNumComplex(bn);
                root = new MpirBigNum(1);
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
                root = new DoubleDouble(1.0);
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
                root = 1.0;
            }
        }
        else {
            z = iterations == 0 ? (isJulia ? inputPixel : new BigComplex()) : referenceData.lastZValue;
            c = isJulia ? getSeed(useBignum, bigNumLib) : inputPixel;
            zold = iterations == 0 ? new BigComplex() : referenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : referenceData.thirdTolastZValue;
            start = isJulia ? inputPixel : new BigComplex();
            pixel = inputPixel;
            c0 = c;
            root = MyApfloat.ONE;
        }

        normSquared = z.normSquared();

        Location loc = new Location();

        refPoint = inputPixel;
        refPointSmall = refPoint.toComplex();


        if(lowPrecReferenceOrbitNeeded) {
            C = c.toComplex();
        }

        GenericComplex c2big = c.times2();

        if(useBignum) {
            cs2big = c.sub(2);
        }
        else {
            cs2big = c.sub(MyApfloat.TWO);
        }

        if(useBignum) {
            cs1big = c.sub(1);
        }
        else {
            cs1big = c.sub(MyApfloat.ONE);
        }


        GenericComplex cs6big;

        if(useBignum) {
            cs6big = c.sub(6);
        }
        else {
            cs6big = c.sub(MyApfloat.SIX);
        }

        cs24big = cs2big.times4();

        if(useBignum) {
            cs212big = cs2big.times(12);
        }
        else {
            cs212big = cs2big.times(MyApfloat.TWELVE);
        }


        if(useBignum) {
            cs23big = cs2big.times(3);
        }
        else {
            cs23big = cs2big.times(MyApfloat.THREE);
        }

        GenericComplex c12big;

        if(useBignum) {
            c12big = c.times(12);
        }
        else {
            c12big = c.times(MyApfloat.TWELVE);
        }


        if(useBignum) {
            c12s8big = c12big.sub(8);
        }
        else {
            c12s8big = c12big.sub(MyApfloat.EIGHT);
        }

        GenericComplex c12s6big;

        if(useBignum) {
            c12s6big = c12big.sub(6);
        }
        else {
            c12s6big = c12big.sub(MyApfloat.SIX);
        }

        GenericComplex csqrbig = c.square();

        GenericComplex csqr4big = csqrbig.times4();

        GenericComplex csqr7big;

        if(useBignum) {
            csqr7big = csqrbig.times(7);
        }
        else {
            csqr7big = csqrbig.times(MyApfloat.SEVEN);
        }

        GenericComplex csqr24big;

        if(useBignum) {
            csqr24big = csqrbig.times(24);
        }
        else {
            csqr24big = csqrbig.times(MyApfloat.TWENTYFOUR);
        }

        GenericComplex ccubebig = c.cube();

        GenericComplex c4big = c.times4();

        GenericComplex csqrsc4big = csqrbig.sub(c4big);


        if(useBignum) {
            precalclbig = csqrbig.sub(c.times(3)).plus_mutable(2);
        }
        else {
            precalclbig = csqrbig.sub(c.times(MyApfloat.THREE)).plus(MyApfloat.TWO);
        }

        if(useBignum) {
            precalc2big = csqrsc4big.plus(4);
        }
        else {
            precalc2big = csqrsc4big.plus(MyApfloat.FOUR);
        }

        GenericComplex precalc3big;

        if(useBignum) {
            precalc3big =  csqrsc4big.plus(3).times2_mutable();
        }
        else {
            precalc3big = csqrsc4big.plus(MyApfloat.THREE).times2();
        }

        if(useBignum) {
            precalc4big =  ccubebig.sub(csqrbig.times(6));
        }
        else {
            precalc4big = ccubebig.sub(csqrbig.times(MyApfloat.SIX));
        }

        if(useBignum) {
            precalc5big =  precalc4big.plus(c12s8big).times_mutable(8);
        }
        else {
            precalc5big =  precalc4big.plus(c12s8big).times(MyApfloat.EIGHT);
        }

        if(useBignum) {
            precalc6big =  ccubebig.sub(csqrbig.times(7)).plus_mutable(c12s6big);
        }
        else {
            precalc6big =  ccubebig.sub(csqrbig.times(MyApfloat.SEVEN)).plus(c12s6big);
        }

        GenericComplex precalc7big;

        if(useBignum) {
            precalc7big =  csqrbig.sub(c.times(6)).plus_mutable(4);
        }
        else {
            precalc7big =  csqrbig.sub(c.times(MyApfloat.SIX)).plus(MyApfloat.FOUR);
        }

        GenericComplex precalc8big;

        if(useBignum) {
            precalc8big = c.sub(3).times2_mutable();
        }
        else {
            precalc8big =  c.sub(MyApfloat.THREE).times2();
        }


        GenericComplex precalc9big;

        if(useBignum) {
            precalc9big = csqrbig.sub(c2big).plus_mutable(1);
        }
        else {
            precalc9big = csqrbig.sub(c2big).plus(MyApfloat.ONE);
        }

        if(useBignum) {
            precalc10big = c.fourth().sub_mutable(ccubebig.times(8)).plus_mutable(csqr24big).sub_mutable(c.times(32)).plus_mutable(16);
        }
        else {
            precalc10big = c.fourth().sub(ccubebig.times(MyApfloat.EIGHT)).plus(csqr24big).sub(c.times(MyApfloat.THIRTYTWO)).plus(MyApfloat.SIXTEEN);
        }

        if(useBignum) {
            precalc11big = ccubebig.sub(csqr7big).plus_mutable(c12s6big);
        }
        else {
            precalc11big = ccubebig.sub(csqr7big).plus(c12s6big);
        }

        if(useBignum) {
            precalc12big = ccubebig.negative().plus_mutable(csqr4big).sub_mutable(c.times(5)).plus_mutable(2);
        }
        else {
            precalc12big = ccubebig.negative().plus(csqr4big).sub(c.times(MyApfloat.FIVE)).plus(MyApfloat.TWO);
        }

        GenericComplex precalc13big;

        if(useBignum) {
            precalc13big = c2big.sub(3);
        }
        else {
            precalc13big = c2big.sub(MyApfloat.THREE);
        }

        precalc14big = precalclbig.times2();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
            Cdeep = loc.getMantExpComplex(c);
        }

        RefType = getRefType();

        boolean preCalcNormData = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        NormComponents normData = null;

        convergent_bailout_algorithm.setReferenceMode(true);

        for (; iterations < max_ref_iterations; iterations++) {

            if (lowPrecReferenceOrbitNeeded) {
                Complex cz = z.toComplex();
                if (cz.isInfinite()) {
                    break;
                }

                setArrayValue(reference, iterations, cz);
            }

            if (deepZoom) {
                setArrayDeepValue(referenceDeep, iterations, loc.getMantExpComplex(z));
            }

            if (preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }
            GenericComplex zsqr;

            if (preCalcNormData) {
                zsqr = z.squareFast(normData);
            } else {
                zsqr = z.square();
            }

            GenericComplex zcube = z.cube();
            GenericComplex zfourth = z.fourth();

            GenericComplex zsqr4 = zsqr.times4();
            GenericComplex cs24tzbig = cs24big.times(z);
            GenericComplex preCalc = precalc2big.plus(cs24tzbig).plus_mutable(zsqr4); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4

            if (lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[0], iterations, preCalc.toComplex());
            }

            //3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - 5*C + 2
            GenericComplex preCalc2 = cs23big.times(zcube).plus_mutable(zfourth.times2()).plus_mutable(precalc2big.times(zsqr)).plus_mutable(precalc14big.times(z))
                    .plus_mutable(precalc6big).times_mutable(z).plus_mutable(precalc12big);

            if (lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[1], iterations, preCalc2.toComplex());
            }

            GenericComplex temp8 = cs2big.times(zcube); //(C - 2)*Z^3

            GenericComplex temp1Z = precalc2big.times(z);

            GenericComplex preCalc3;
            if (useBignum) {
                preCalc3 = precalc10big.plus(temp8.times(32)).plus_mutable(zcube.times(16).plus_mutable(temp1Z.times(24)).plus_mutable(precalc5big).times_mutable(z));

            } else {
                preCalc3 = precalc10big.plus(temp8.times(MyApfloat.THIRTYTWO)).plus(zcube.times(MyApfloat.SIXTEEN).plus(temp1Z.times(MyApfloat.TWENTYFOUR)).plus(precalc5big).times(z));
            }

            if (lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[2], iterations, preCalc3.toComplex());
            }

            GenericComplex preCalc4;
            if (useBignum) {
                preCalc4 = precalc4big.plus(cs212big.times(zsqr)).plus_mutable(zcube.times(8)).plus_mutable(temp1Z.times(6)).plus_mutable(c12s8big);
            } else {
                preCalc4 = precalc4big.plus(cs212big.times(zsqr)).plus(zcube.times(MyApfloat.EIGHT)).plus(temp1Z.times(MyApfloat.SIX)).plus(c12s8big);
            }

            if (lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[3], iterations, preCalc4.toComplex());
            }

            GenericComplex temp = precalc8big.times(z); //2*(C-3)*Z

            GenericComplex preCalc5 = null, preCalc6 = null, preCalc8 = null;
            if (!isJulia) {
                preCalc5 = precalc9big.sub(precalc2big.plus(zsqr4).sub_mutable(zcube.sub(temp)).times_mutable(z));

                if (lowPrecReferenceOrbitNeeded) {
                    setArrayValue(referenceData.PrecalculatedTerms[4], iterations, preCalc5.toComplex());
                }


                preCalc6 = zcube.plus(temp).sub_mutable(cs24big).times_mutable(z).plus_mutable(precalc13big);

                if (lowPrecReferenceOrbitNeeded) {
                    setArrayValue(referenceData.PrecalculatedTerms[5], iterations, preCalc6.toComplex());
                }
            }


            GenericComplex preCalc7;
            if(useBignum) {
                preCalc7 = precalc2big.plus(cs24tzbig).times_mutable(zsqr).times_mutable(3)
                        .plus_mutable(zfourth.times(10))
                        .plus_mutable(precalc11big).plus_mutable(precalclbig.times4().times_mutable(z));
            }
            else {
                preCalc7 = precalc2big.plus(cs24tzbig).times(zsqr).times(MyApfloat.THREE)
                        .plus(zfourth.times(MyApfloat.TEN))
                        .plus(precalc11big).plus(precalclbig.times4().times(z));
            }

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[6], iterations, preCalc7.toComplex());
            }

            if (!isJulia) {
                preCalc8 = cs6big.times(zcube).plus_mutable(zfourth.times2()).plus_mutable(precalc7big.times(z)).plus_mutable(zsqr4)
                        .sub_mutable(precalc3big).times_mutable(z).plus_mutable(precalclbig);

                if (lowPrecReferenceOrbitNeeded) {
                    setArrayValue(referenceData.PrecalculatedTerms[7], iterations, preCalc8.toComplex());
                }
            }

            if(deepZoom) {
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, loc.getMantExpComplex(preCalc));
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], iterations, loc.getMantExpComplex(preCalc2));
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[2], iterations, loc.getMantExpComplex(preCalc3));
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[3], iterations, loc.getMantExpComplex(preCalc4));
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[6], iterations, loc.getMantExpComplex(preCalc7));

                if(!isJulia) {
                    setArrayDeepValue(referenceDeepData.PrecalculatedTerms[4], iterations, loc.getMantExpComplex(preCalc5));
                    setArrayDeepValue(referenceDeepData.PrecalculatedTerms[5], iterations, loc.getMantExpComplex(preCalc6));
                    setArrayDeepValue(referenceDeepData.PrecalculatedTerms[7], iterations, loc.getMantExpComplex(preCalc8));
                }
            }

            if (iterations > 0 && (convergent_bailout_algorithm.Converged(z, root, zold, zold2, iterations, c, start, c0, pixel)
                    || bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel))) {
                break;
            }

            if(!preCalcNormData) {
                zold2.set(zold);
                zold.set(z);
            }

            try {

                z = (zsqr.plus(cs1big)).divide_mutable(z.times2().plus_mutable(cs2big)).square_mutable();

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

        if(isJulia) {
            calculateJuliaReferencePoint(inputPixel, size, deepZoom, juliaIterations, progress);
        }

    }

    @Override
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

        if (iterations == 0) {
            if(lowPrecReferenceOrbitNeeded) {
                secondReferenceData.create(max_ref_iterations,false, new int[] {0, 1, 2, 3, 6});
            }
            else {
                secondReferenceData.deallocate();
            }

            if (deepZoom) {
                secondReferenceDeepData.create(max_ref_iterations,false, new int[] {0, 1, 2, 3, 6});
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

        int bigNumLib = ThreadDraw.getBignumLibrary(size, this);
        boolean useBignum = ThreadDraw.USE_BIGNUM_FOR_REF_IF_POSSIBLE  && bigNumLib != Constants.BIGNUM_APFLOAT;

        GenericComplex z, c, zold, zold2, start, c0, pixel;
        Object normSquared, root;

        if(useBignum ) {
            if (bigNumLib == Constants.BIGNUM_MPFR) {
                MpfrBigNumComplex bn = new MpfrBigNumComplex(inputPixel.toMpfrBigNumComplex());
                z = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.lastZValue;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpfrBigNumComplex() : secondReferenceData.thirdTolastZValue;
                start = new MpfrBigNumComplex();
                c0 = new MpfrBigNumComplex((MpfrBigNumComplex)c);
                pixel = new MpfrBigNumComplex(bn);
                root = new MpfrBigNum(1);
            }
            else if (bigNumLib == Constants.BIGNUM_MPIR) {
                MpirBigNumComplex bn = new MpirBigNumComplex(inputPixel.toMpirBigNumComplex());
                z = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.lastZValue;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new MpirBigNumComplex() : secondReferenceData.thirdTolastZValue;
                start = new MpirBigNumComplex();
                c0 = new MpirBigNumComplex((MpirBigNumComplex)c);
                pixel = new MpirBigNumComplex(bn);
                root = new MpirBigNum(1);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                DDComplex ddn = inputPixel.toDDComplex();
                z = iterations == 0 ? new DDComplex() : secondReferenceData.lastZValue;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new DDComplex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new DDComplex() : secondReferenceData.thirdTolastZValue;
                start = new DDComplex();
                c0 = c;
                pixel = ddn;
                root = new DoubleDouble(1.0);
            }
            else {
                Complex bn = inputPixel.toComplex();
                z = iterations == 0 ? new Complex() : secondReferenceData.lastZValue;
                c = getSeed(useBignum, bigNumLib);
                zold = iterations == 0 ? new Complex() : secondReferenceData.secondTolastZValue;
                zold2 = iterations == 0 ? new Complex() : secondReferenceData.thirdTolastZValue;
                start = new Complex();
                c0 = new Complex((Complex) c);
                pixel = new Complex(bn);
                root = 1.0;
            }
        }
        else {
            z = iterations == 0 ? new BigComplex() : secondReferenceData.lastZValue;
            c = getSeed(useBignum, bigNumLib);
            zold = iterations == 0 ? new BigComplex() : secondReferenceData.secondTolastZValue;
            zold2 = iterations == 0 ? new BigComplex() : secondReferenceData.thirdTolastZValue;
            start = new BigComplex();
            pixel = inputPixel;
            c0 = c;
            root = MyApfloat.ONE;
        }

        normSquared = z.normSquared();
        boolean preCalcNormData = bailout_algorithm2.getId() == MainWindow.BAILOUT_CONDITION_CIRCLE;
        NormComponents normData = null;

        convergent_bailout_algorithm.setReferenceMode(true);

        for (; iterations < max_ref_iterations; iterations++) {

            if(lowPrecReferenceOrbitNeeded) {
                Complex cz = z.toComplex();
                if (cz.isInfinite()) {
                    break;
                }

                setArrayValue(secondReferenceData.Reference, iterations, cz);
            }

            if(deepZoom) {
                setArrayDeepValue(secondReferenceDeepData.Reference, iterations, loc.getMantExpComplex(z));
            }

            if(preCalcNormData) {
                normData = z.normSquaredWithComponents(normData);
                normSquared = normData.normSquared;
            }

            GenericComplex zsqr;

            if (preCalcNormData) {
                zsqr = z.squareFast(normData);
            } else {
                zsqr = z.square();
            }

            GenericComplex zcube = z.cube();
            GenericComplex zfourth = z.fourth();

            GenericComplex zsqr4 = zsqr.times4();
            GenericComplex cs24tzbig = cs24big.times(z);
            GenericComplex preCalc = precalc2big.plus(cs24tzbig).plus_mutable(zsqr4); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4

            if (lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.PrecalculatedTerms[0], iterations, preCalc.toComplex());
            }

            //3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - 5*C + 2
            GenericComplex preCalc2 = cs23big.times(zcube).plus_mutable(zfourth.times2()).plus_mutable(precalc2big.times(zsqr)).plus_mutable(precalc14big.times(z))
                    .plus_mutable(precalc6big).times_mutable(z).plus_mutable(precalc12big);

            if (lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.PrecalculatedTerms[1], iterations, preCalc2.toComplex());
            }

            GenericComplex temp8 = cs2big.times(zcube); //(C - 2)*Z^3

            GenericComplex temp1Z = precalc2big.times(z);

            GenericComplex preCalc3;
            if (useBignum) {
                preCalc3 = precalc10big.plus(temp8.times(32)).plus_mutable(zcube.times(16).plus_mutable(temp1Z.times(24)).plus_mutable(precalc5big).times_mutable(z));

            } else {
                preCalc3 = precalc10big.plus(temp8.times(MyApfloat.THIRTYTWO)).plus(zcube.times(MyApfloat.SIXTEEN).plus(temp1Z.times(MyApfloat.TWENTYFOUR)).plus(precalc5big).times(z));
            }

            if (lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.PrecalculatedTerms[2], iterations, preCalc3.toComplex());
            }

            GenericComplex preCalc4;
            if (useBignum) {
                preCalc4 = precalc4big.plus(cs212big.times(zsqr)).plus_mutable(zcube.times(8)).plus_mutable(temp1Z.times(6)).plus_mutable(c12s8big);
            } else {
                preCalc4 = precalc4big.plus(cs212big.times(zsqr)).plus(zcube.times(MyApfloat.EIGHT)).plus(temp1Z.times(MyApfloat.SIX)).plus(c12s8big);
            }

            if (lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.PrecalculatedTerms[3], iterations, preCalc4.toComplex());
            }


            GenericComplex preCalc7;
            if(useBignum) {
                preCalc7 = precalc2big.plus(cs24tzbig).times_mutable(zsqr).times_mutable(3)
                        .plus_mutable(zfourth.times(10))
                        .plus_mutable(precalc11big).plus_mutable(precalclbig.times4().times_mutable(z));
            }
            else {
                preCalc7 = precalc2big.plus(cs24tzbig).times(zsqr).times(MyApfloat.THREE)
                        .plus(zfourth.times(MyApfloat.TEN))
                        .plus(precalc11big).plus(precalclbig.times4().times(z));
            }

            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(secondReferenceData.PrecalculatedTerms[6], iterations, preCalc7.toComplex());
            }

            if(deepZoom) {
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[0], iterations, loc.getMantExpComplex(preCalc));
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[1], iterations, loc.getMantExpComplex(preCalc2));
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[2], iterations, loc.getMantExpComplex(preCalc3));
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[3], iterations, loc.getMantExpComplex(preCalc4));
                setArrayDeepValue(secondReferenceDeepData.PrecalculatedTerms[6], iterations, loc.getMantExpComplex(preCalc7));
            }

            if (iterations > 0 && (convergent_bailout_algorithm.Converged(z, root, zold, zold2, iterations, c, start, c0, pixel)
                    || bailout_algorithm2.Escaped(z, zold, zold2, iterations, c, start, c0, normSquared, pixel))) {
                break;
            }

            if(!preCalcNormData) {
                zold2.set(zold);
                zold.set(z);
            }

            try {

                z = (zsqr.plus(cs1big)).divide_mutable(z.times2().plus_mutable(cs2big)).square_mutable();

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
    public void function(GenericComplex[] complex) {
        if(complex[0] instanceof BigComplex) {
            complex[0] = (complex[0].square().plus(complex[1].sub(MyApfloat.ONE))).divide(complex[0].times2().plus(complex[1].sub(MyApfloat.TWO))).square();
        }
        else {
            complex[0] = (complex[0].square().plus_mutable(complex[1].sub(1))).divide_mutable(complex[0].times2().plus_mutable(complex[1].sub(2))).square_mutable();
        }
    }


    @Override
    public Complex perturbationFunction(Complex z, Complex c, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)

        Complex csqr = c.square();
        Complex zsqr = z.square();


        Complex temp2 = getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration);//C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        Complex temp9 = temp2.times(c);
        Complex temp10 = getArrayValue(referenceData.PrecalculatedTerms[3], RefIteration);

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
        Complex denom = getArrayValue(referenceData.PrecalculatedTerms[2], RefIteration)
                .plus_mutable(temp2.times(csqr))
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(temp10.times(c).times2_mutable())
                .plus_mutable((temp10.plus(temp9)).times(z).times4_mutable());



        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z


        Complex a1 = temp2.times(z.fourth());

        Complex b1 = (temp2.times(Z)).times(z.cube()).times4_mutable();

        Complex c1 = getArrayValue(referenceData.PrecalculatedTerms[5], RefIteration).times_mutable(csqr);

        Complex d1 = getArrayValue(referenceData.PrecalculatedTerms[6], RefIteration).plus_mutable(temp9).times2_mutable().times_mutable(zsqr);

        Complex e1 = getArrayValue(referenceData.PrecalculatedTerms[7], RefIteration).times2_mutable().times_mutable(c);

        Complex f1 = (getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration).sub_mutable(getArrayValue(referenceData.PrecalculatedTerms[4], RefIteration).times_mutable(c))
                ).times4_mutable().times_mutable(z);

        Complex num = a1
                .plus_mutable(b1)
                .sub_mutable(c1)
                .plus_mutable(d1)
                .sub_mutable(e1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {


        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)

        MantExpComplex csqr = c.square();
        MantExpComplex zsqr = z.square();


        MantExpComplex temp2 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration);//C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        MantExpComplex temp9 = temp2.times(c);
        MantExpComplex temp10 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[3], RefIteration);

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
        MantExpComplex denom = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[2], RefIteration)
                .plus_mutable(temp2.times(csqr))
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(temp10.times(c).times2_mutable())
                .plus_mutable((temp10.plus(temp9)).times(z).times4_mutable());



        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z


        MantExpComplex a1 = temp2.times(z.fourth());

        MantExpComplex b1 = (temp2.times(Z)).times(z.cube()).times4_mutable();

        MantExpComplex c1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[5], RefIteration).times_mutable(csqr);

        MantExpComplex d1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[6], RefIteration).plus_mutable(temp9).times2_mutable().times_mutable(zsqr);

        MantExpComplex e1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[7], RefIteration).times2_mutable().times_mutable(c);

        MantExpComplex f1 = (getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration).sub_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[4], RefIteration).times_mutable(c))
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
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)

        Complex zsqr = z.square();


        Complex temp2 = getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration);//C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        Complex temp10 = getArrayValue(referenceData.PrecalculatedTerms[3], RefIteration);

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
        Complex denom = getArrayValue(referenceData.PrecalculatedTerms[2], RefIteration)
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(temp10.times(z).times4_mutable());



        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z


        Complex a1 = temp2.times(z.fourth());

        Complex b1 = (temp2.times(Z)).times(z.cube()).times4_mutable();


        Complex d1 = getArrayValue(referenceData.PrecalculatedTerms[6], RefIteration).times2_mutable().times_mutable(zsqr);


        Complex f1 = (getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration)
        ).times4_mutable().times_mutable(z);

        Complex num = a1
                .plus_mutable(b1)
                .plus_mutable(d1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)

        MantExpComplex zsqr = z.square();


        MantExpComplex temp2 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration);//C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        MantExpComplex temp10 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[3], RefIteration);

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
        MantExpComplex denom = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[2], RefIteration)
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(temp10.times(z).times4_mutable());



        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z


        MantExpComplex a1 = temp2.times(z.fourth());

        MantExpComplex b1 = (temp2.times(Z)).times(z.cube()).times4_mutable();


        MantExpComplex d1 = getArrayDeepValue(referenceDeepData.PrecalculatedTerms[6], RefIteration).times2_mutable().times_mutable(zsqr);


        MantExpComplex f1 = (getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration)
        ).times4_mutable().times_mutable(z);

        MantExpComplex num = a1
                .plus_mutable(b1)
                .plus_mutable(d1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {

        Complex Z = getArrayValue(data.Reference, RefIteration);

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)

        Complex zsqr = z.square();


        Complex temp2 = getArrayValue(data.PrecalculatedTerms[0], RefIteration);//C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        Complex temp10 = getArrayValue(data.PrecalculatedTerms[3], RefIteration);

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
        Complex denom = getArrayValue(data.PrecalculatedTerms[2], RefIteration)
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(temp10.times(z).times4_mutable());



        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z


        Complex a1 = temp2.times(z.fourth());

        Complex b1 = (temp2.times(Z)).times(z.cube()).times4_mutable();


        Complex d1 = getArrayValue(data.PrecalculatedTerms[6], RefIteration).times2_mutable().times_mutable(zsqr);


        Complex f1 = (getArrayValue(data.PrecalculatedTerms[1], RefIteration)
        ).times4_mutable().times_mutable(z);

        Complex num = a1
                .plus_mutable(b1)
                .plus_mutable(d1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(data.Reference, RefIteration);

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)

        MantExpComplex zsqr = z.square();


        MantExpComplex temp2 = getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration);//C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        MantExpComplex temp10 = getArrayDeepValue(data.PrecalculatedTerms[3], RefIteration);

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
        MantExpComplex denom = getArrayDeepValue(data.PrecalculatedTerms[2], RefIteration)
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(temp10.times(z).times4_mutable());



        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z


        MantExpComplex a1 = temp2.times(z.fourth());

        MantExpComplex b1 = (temp2.times(Z)).times(z.cube()).times4_mutable();


        MantExpComplex d1 = getArrayDeepValue(data.PrecalculatedTerms[6], RefIteration).times2_mutable().times_mutable(zsqr);


        MantExpComplex f1 = (getArrayDeepValue(data.PrecalculatedTerms[1], RefIteration)
        ).times4_mutable().times_mutable(z);

        MantExpComplex num = a1
                .plus_mutable(b1)
                .plus_mutable(d1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);

    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean supportsMpirBignum() { return true;}
}
