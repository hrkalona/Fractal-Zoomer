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
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class Lambda extends Julia {

    public Lambda() {
        super();
    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

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
    public Lambda(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

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

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        defaultInitVal = new InitialValue(0.5, 0);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
        power = 2;
    }

    @Override
    public void function(Complex[] complex) {

        complex[0] = complex[1].times(complex[0].sub(complex[0].square()));

    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || !juliter;
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public void function(GenericComplex[] complex) {

        if(complex[0] instanceof MpfrBigNumComplex) {
            complex[0] = complex[1].times(complex[0].sub(complex[0].square(), workSpaceData.temp1, workSpaceData.temp2));
        }
        else if(complex[0] instanceof MpirBigNumComplex) {
            complex[0] = complex[1].times(complex[0].sub(complex[0].square(), workSpaceData.temp1p, workSpaceData.temp2p));
        }
        else {
            complex[0] = complex[1].times(complex[0].sub(complex[0].square()));
        }

    }

    //(-z^2 - (2*Z - 1)*z)*C + (-z^2 - Z^2 + Z - (2*Z-1)*z) * c
    @Override
    public Complex perturbationFunction(Complex z, Complex c, int RefIteration) {

        /*Complex X = Reference[RefIteration];

        Complex DnSqrPlustwoDnXn = DeltaSubN.square().plus_mutable(DeltaSubN.times2().times_mutable(X));

        return DeltaSub0.negative().times(DnSqrPlustwoDnXn.plus(X.square()).sub_mutable(0.25))
                .sub_mutable(refPointSmall.times(DnSqrPlustwoDnXn));*/


        Complex temp = z.plus(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(C).plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration).plus_mutable(temp).times_mutable(c));



    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {

        MantExpComplex temp = z.plus(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep).plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration).plus_mutable(temp).times_mutable(c));
    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex temp = z.plus(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(C);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex temp = z.plus(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep);
    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {

        Complex temp = z.plus(getArrayValue(data.PrecalculatedTerms[0], RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(C);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {

        MantExpComplex temp = z.plus(getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep);
    }



    @Override
    public double iterateFractalWithPerturbation(Complex[] complex, Complex dpixel) {


        double_iterations = 0;
        rebases = 0;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : SAskippedIterations;
        int RefIteration = iterations;

        int ReferencePeriod = getPeriod();

        int MaxRefIteration = getReferenceFinalIterationNumber(true, referenceData);

        Complex zWithoutInitVal = new Complex();

        if(iterations != 0 && RefIteration < MaxRefIteration) {
            complex[0] = getArrayValue(reference, RefIteration).plus_mutable(DeltaSubN);
        }
        else if(iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            complex[0] = getArrayValue(reference, RefIteration).plus_mutable(DeltaSubN);
        }

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

                res = getFinalValueOut(res, complex[0]);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return getAndAccumulateStatsNotDeep(res);
            }

            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayValue(reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;
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
    public double iterateFractalWithPerturbation(Complex[] complex, MantExpComplex dpixel) {

        float_exp_iterations = 0;
        double_iterations = 0;
        rebases = 0;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        precalculatePerturbationData(DeltaSub0);

        int totalSkippedIterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : SAskippedIterations;
        iterations = totalSkippedIterations;
        int RefIteration = iterations;

        int ReferencePeriod = getPeriod();

        int MaxRefIteration = getReferenceFinalIterationNumber(true, referenceData);

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        MantExpComplex cDeep = dpixel.plus(refPointSmallDeep);
        Complex pixel = cDeep.toComplex();

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        boolean usedDeepCode = false;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            usedDeepCode = true;
            MantExpComplex z = MantExpComplex.create();
            if(iterations != 0 && RefIteration < MaxRefIteration) {
                z = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
            }
            else if(iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                z = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
            }

            MantExpComplex zoldDeep;

            MantExp norm_squared_m = null;
            if(doBailCheck) {
                norm_squared_m = z.norm_squared();
            }

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (doBailCheck && bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared_m.toDouble(), pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res, complex[0]);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(complex[0]);
                zoldDeep = z;

                if (max_iterations > 1) {
                    z = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = getArrayDeepValue(referenceDeep, RefIteration).plus_mutable(DeltaSubN).toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0, z, zoldDeep, cDeep);
                }

                norm_squared_m = z.norm_squared();
                if (norm_squared_m.compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;
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
            Complex CDeltaSub0 = DeltaSub0.toComplex();

            boolean isZero = CDeltaSub0.isZero();
            Complex zWithoutInitVal = new Complex();

            if(!usedDeepCode && iterations != 0 && RefIteration < MaxRefIteration) {
                complex[0] = getArrayValue(reference, RefIteration).plus_mutable(CDeltaSubN);
            }
            else if(!usedDeepCode && iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                complex[0] = getArrayValue(reference, RefIteration).plus_mutable(CDeltaSubN);
            }

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res, complex[0]);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                if (isZero) {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, RefIteration);
                } else {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, CDeltaSub0, RefIteration);
                }

                RefIteration++;
                double_iterations++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration).plus_mutable(CDeltaSubN);
                    complex[0] = getArrayValue(reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
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
                zWithoutInitVal = getArrayValue(data.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = getArrayValue(data.Reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
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

        int totalSkippedIterations = 0;

        iterations = 0;

        int RefIteration = iterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        ReferenceDeepData deepData = referenceDeepData;
        ReferenceData data = referenceData;
        int MaxRefIteration = data.MaxRefIteration;

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex z = getArrayDeepValue(deepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);

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

                if (max_iterations > 1) {
                    z = getArrayDeepValue(deepData.ReferenceSubCp, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = getArrayDeepValue(deepData.Reference, RefIteration).plus_mutable(DeltaSubN).toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
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
                    zWithoutInitVal = getArrayValue(data.ReferenceSubCp, RefIteration).plus_mutable(CDeltaSubN);
                    complex[0] = getArrayValue(data.Reference, RefIteration).plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
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
    public boolean supportsBignum() { return true;}

    @Override
    public boolean supportsBigIntnum() {
        return true;
    }

    @Override
    public boolean supportsMpfrBignum() { return true;}

    @Override
    public boolean supportsMpirBignum() { return true;}


    @Override
    protected boolean needsRefSubCp() {
        return true;
    }

    @Override
    protected GenericComplex[] precalculateReferenceData(GenericComplex z, GenericComplex c, NormComponents normData, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations) {

        GenericComplex preCalc;

        if(bigNumLib == Constants.BIGNUM_MPFR) {
            preCalc = z.times2(workSpaceData.temp1, workSpaceData.temp2).sub_mutable(1); //2*Z-1 for catastrophic cancelation
        }
        else if(bigNumLib == Constants.BIGNUM_MPIR) {
            preCalc = z.times2(workSpaceData.temp1p, workSpaceData.temp2p).sub_mutable(1); //2*Z-1 for catastrophic cancelation
        } else if (bigNumLib == Constants.BIGNUM_APFLOAT) {
            preCalc = z.times2().sub(MyApfloat.ONE); //2*Z-1 for catastrophic cancelation
        } else {
            preCalc = z.times2().sub_mutable(1); //2*Z-1 for catastrophic cancelation
        }

        if(lowPrecReferenceOrbitNeeded) {
            setArrayValue(referenceData.PrecalculatedTerms[0], iterations, preCalc.toComplex());
        }
        if(deepZoom) {
            setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, loc.getMantExpComplex(preCalc));
        }

        GenericComplex preCalc2;

        //Maybe this is not needed, as Z^2 - Z does not go to 0 when Z = 0.5
        if(normData != null) {
            preCalc2 = z.sub(z.squareFast(normData));
        }
        else {
            preCalc2 = z.sub(z.square());
        }

        if(!isJulia) {
            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[1], iterations, preCalc2.toComplex());
            }

            if(deepZoom) {
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], iterations, loc.getMantExpComplex(preCalc2));
            }
        }

        return new GenericComplex[] {preCalc2};
    }

    @Override
    protected int[] getNeededPrecalculatedTermsIndexes() {

        if (!isJulia) {
            return new int[] {0, 1};
        }
        return new int[] {0};
    }

    @Override
    protected void calculateRefSubCp(GenericComplex z, GenericComplex initVal, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations) {

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

        if(lowPrecReferenceOrbitNeeded) {
            setArrayValue(referenceData.ReferenceSubCp, iterations, zsubcp.toComplex());
        }
        if(deepZoom) {
            setArrayDeepValue(referenceDeepData.ReferenceSubCp, iterations, loc.getMantExpComplex(zsubcp));
        }

    }

    @Override
    protected GenericComplex referenceFunction(GenericComplex z, GenericComplex c, NormComponents normData, GenericComplex[] initialPrecal, GenericComplex[] precalc) {
        return precalc[0].times_mutable(c);
    }
}
