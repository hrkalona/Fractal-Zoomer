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
package fractalzoomer.functions.lambda;

import fractalzoomer.core.*;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
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
public class Lambda extends Julia {

    public Lambda() {
        super();
    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        defaultInitVal = new InitialValue(0.5, 0);

        power = 2;

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
            init_val = defaultInitVal;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        power = 2;

        defaultInitVal = new InitialValue(0.5, 0);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
    }

    //orbit
    public Lambda(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        power = 2;

        defaultInitVal = new InitialValue(0.5, 0);

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
            init_val = defaultInitVal;
        }

    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
        defaultInitVal = new InitialValue(0.5, 0);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
        power = 2;
    }

    @Override
    public void function(Complex[] complex) {

        complex[0].times_mutable(complex[1].times(complex[0].r_sub(1)));

    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || (isJulia && !juliter);
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + seed : "");
    }

    @Override
    public void calculateReferencePoint(BigComplex pixel, Apfloat size, boolean deepZoom, int iterations, Location externalLocation, JProgressBar progress) {

        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        if (iterations == 0) {
            Reference = new Complex[max_iterations];

            ReferenceSubCp = new Complex[max_iterations];

            PrecalculatedTerms = new Complex[max_iterations];

            if (deepZoom) {
                ReferenceDeep = new MantExpComplex[max_iterations];

                ReferenceSubCpDeep = new MantExpComplex[max_iterations];

                PrecalculatedTermsDeep = new MantExpComplex[max_iterations];

            }
        } else if (max_iterations > Reference.length) {
            Reference = copyReference(Reference, new Complex[max_iterations]);

            ReferenceSubCp = copyReference(ReferenceSubCp, new Complex[max_iterations]);

            PrecalculatedTerms = copyReference(PrecalculatedTerms, new Complex[max_iterations]);

            if (deepZoom) {
                ReferenceDeep = copyDeepReference(ReferenceDeep, new MantExpComplex[max_iterations]);

                ReferenceSubCpDeep = copyDeepReference(ReferenceSubCpDeep, new MantExpComplex[max_iterations]);

                PrecalculatedTermsDeep = copyDeepReference(PrecalculatedTermsDeep, new MantExpComplex[max_iterations]);

            }
        }


        BigComplex initVal = new BigComplex(defaultInitVal.getValue(null));

        BigComplex z = iterations == 0 ? (isJulia ? pixel : initVal) : lastZValue;

        BigComplex c = isJulia ? new BigComplex(seed) : pixel;

        BigComplex zold = iterations == 0 ? new BigComplex() : secondTolastZValue;
        BigComplex zold2 = iterations == 0 ? new BigComplex() : thirdTolastZValue;
        BigComplex start = isJulia ? pixel : initVal;
        BigComplex c0 = c;

        Location loc = new Location();

        refPoint = pixel;
        refPointSmall = refPoint.toComplex();

        C = c.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
            Cdeep = loc.getMantExpComplex(c);
        }

        boolean fullReference = ThreadDraw.CALCULATE_FULL_REFERENCE;
        RefType = getRefType();
        FullRef = fullReference;


        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            Reference[iterations] = cz;

            BigComplex zsubcp = isJulia ? z.sub(refPoint) : z.sub(initVal);
            ReferenceSubCp[iterations] = zsubcp.toComplex();

            BigComplex preCalc = z.times(MyApfloat.TWO).sub(MyApfloat.ONE); //2*Z-1 for catastrophic cancelation
            PrecalculatedTerms[iterations] = preCalc.toComplex();

            if(deepZoom) {
                ReferenceSubCpDeep[iterations] = loc.getMantExpComplex(zsubcp);
                PrecalculatedTermsDeep[iterations] = loc.getMantExpComplex(preCalc);
            }

            if(deepZoom) {
                ReferenceDeep[iterations] = loc.getMantExpComplex(z);
                //ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
            }

            if (!fullReference && iterations > 0 && bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, Apfloat.ZERO, pixel)) {
                break;
            }

            zold2 = zold;
            zold = z;

            try {
                z = z.times(c).times(z.r_sub(MyApfloat.ONE));
                //z = c.times(z.square().negative().plus(point25)).sub(point5);
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
    }


    //(-z^2 - (2*Z - 1)*z)*C + (-z^2 - Z^2 + Z - (2*Z-1)*z) * c
    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        /*Complex X = Reference[RefIteration];

        Complex DnSqrPlustwoDnXn = DeltaSubN.square().plus_mutable(DeltaSubN.times(2).times_mutable(X));

        return DeltaSub0.negative().times(DnSqrPlustwoDnXn.plus(X.square()).sub_mutable(0.25))
                .sub_mutable(refPointSmall.times(DnSqrPlustwoDnXn));*/

        Complex Z = Reference[RefIteration];
        Complex c = DeltaSub0;
        Complex z = DeltaSubN;

        Complex temp = z.square().plus_mutable(PrecalculatedTerms[RefIteration].times(z)).negative_mutable();

        return temp.times(C).plus_mutable(Z.sub(Z.square()).plus_mutable(temp).times_mutable(c));



    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex Z = ReferenceDeep[RefIteration];
        MantExpComplex c = DeltaSub0;
        MantExpComplex z = DeltaSubN;

        MantExpComplex temp = z.square().plus_mutable(PrecalculatedTermsDeep[RefIteration].times(z)).negative_mutable();

        return temp.times(Cdeep).plus_mutable(Z.sub(Z.square()).plus_mutable(temp).times_mutable(c));
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex z = DeltaSubN;

        Complex temp = z.square().plus_mutable(PrecalculatedTerms[RefIteration].times(z)).negative_mutable();

        return temp.times(C);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex z = DeltaSubN;

        MantExpComplex temp = z.square().plus_mutable(PrecalculatedTermsDeep[RefIteration].times(z)).negative_mutable();

        return temp.times(Cdeep);
    }



    @Override
    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, Complex dpixel) {

        iterations = skippedIterations;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        int RefIteration = iterations;


        Complex zWithoutInitVal = new Complex();

        Complex pixel = dpixel.plus(refPointSmall);

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return res;
            }

            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

            RefIteration++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                zWithoutInitVal = ReferenceSubCp[RefIteration].plus(DeltaSubN);
                complex[0] = Reference[RefIteration].plus(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;
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
    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, MantExpComplex dpixel) {

        iterations = skippedIterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        int RefIteration = iterations;

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Reduce();
        long exp = DeltaSubN.getExp();

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        if(ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM || (skippedIterations == 0 && exp <= minExp) || (skippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex z = new MantExpComplex();
            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                if (max_iterations > 1) {
                    z = ReferenceSubCpDeep[RefIteration].plus(DeltaSubN);
                    complex[0] = ReferenceDeep[RefIteration].plus(DeltaSubN).toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (z.norm_squared().compareTo(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;
                }

                DeltaSubN.Reduce();

                if(!ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM) {
                    if (DeltaSubN.getExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM) {
            Complex CDeltaSubN = DeltaSubN.toComplex();
            Complex CDeltaSub0 = DeltaSub0.toComplex();

            boolean isZero = CDeltaSub0.isZero();
            Complex zWithoutInitVal = new Complex();

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                if (isZero) {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, RefIteration);
                } else {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, CDeltaSub0, RefIteration);
                }

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    zWithoutInitVal = ReferenceSubCp[RefIteration].plus(CDeltaSubN);
                    complex[0] = Reference[RefIteration].plus(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
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

    public static void main(String[] args) {
        Lambda a = new Lambda();

        Reference = new Complex[2];
        Reference[0] = new Complex();
        Reference[1] = new Complex(0.32, 7.321);
        ReferenceDeep = new MantExpComplex[2];
        ReferenceDeep[0] = new MantExpComplex(Reference[0]);
        ReferenceDeep[1] = new MantExpComplex(Reference[1]);

        refPointSmall = new Complex(0.4, 0.0002);
        refPointSmallDeep = new MantExpComplex(refPointSmall);

        Complex Dn = new Complex(1.321, -4.21);
        Complex D0 = new Complex();
        MantExpComplex MDn = new MantExpComplex(Dn);
        MantExpComplex MD0 = new MantExpComplex(D0);

        Complex nzD0 = new Complex(-0.5, 1.4);
        MantExpComplex nzMD0 = new MantExpComplex(nzD0);

        System.out.println(a.perturbationFunction(Dn, D0, 1));
        System.out.println(a.perturbationFunction(Dn, 1));
        System.out.println(a.perturbationFunction(MDn, MD0, 1));
        System.out.println("Non Zero D0");
        System.out.println(a.perturbationFunction(Dn, nzD0, 1));
        System.out.println(a.perturbationFunction(MDn, nzMD0, 1));
    }
}
