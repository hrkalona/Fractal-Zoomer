
package fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze;

import fractalzoomer.core.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.reference.ReferenceData;
import fractalzoomer.core.reference.ReferenceDeepData;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.Constants;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

import java.util.ArrayList;
import java.util.function.Function;

/**
 *
 * @author hrkalona2
 */
public class Formula48 extends Julia {

    public Formula48(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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
            init_val = new InitialValue(1, 0);
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
        defaultInitVal = new InitialValue(1, 0);
    }

    public Formula48(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(1, 0);
        defaultInitVal = new InitialValue(1, 0);
    }

    //orbit
    public Formula48(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

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
            init_val = new InitialValue(1, 0);
        }
        defaultInitVal = new InitialValue(1, 0);

    }

    public Formula48(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(1, 0);
        defaultInitVal = new InitialValue(1, 0);
    }

    @Override
    public void function(Complex[] complex) {

        complex[0].square_mutable();
        (complex[0].plus_mutable(complex[0].reciprocal())).times_mutable(complex[1]);

    }

    //Todo has glitches
    @Override
    public double iterateFractalWithPerturbation(Complex[] complexIn, Complex dpixel) {


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

        Complex refZ;
        Complex zWithoutInitVal = new Complex();
        Complex z = complexIn[0];
        Complex c = complexIn[1];

        if(iterations != 0 && RefIteration < MaxRefIteration) {
            refZ = getArrayValue(reference, RefIteration);
            zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(DeltaSubN);
            z = refZ.plus_mutable(DeltaSubN);
        }
        else if(iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            refZ = getArrayValue(reference, RefIteration);
            zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(DeltaSubN);
            z = refZ.plus_mutable(DeltaSubN);
        }

        Complex pixel = dpixel.plus(refPointSmall);

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(z, iterations);
            }

            if (bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, 0.0, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                Object[] object = {iterations, z, zold, zold2, c, start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel, object);
                }

                return getAndAccumulateStatsNotDeep(res);
            }

            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(z);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                refZ = getArrayValue(reference, RefIteration);
                zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(DeltaSubN);
                z = refZ.plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;
                rebases++;
            }

        }

        finalizeStatistic(false, z);
        Object[] object = {z, zold, zold2, c, start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsNotDeep(in);

    }

    @Override
    public double iterateFractalWithPerturbation(Complex[] complexIn, MantExpComplex dpixel) {

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
        int reducedExp = minExp / (int)getPower();

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        MantExpComplex cDeep = dpixel.plus(refPointSmallDeep);
        Complex pixel = cDeep.toComplex();
        Complex zc = complexIn[0];
        Complex c = complexIn[1];

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        boolean usedDeepCode = false;

        MantExpComplex refZm;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            usedDeepCode = true;
            MantExpComplex zWithoutInitVal = MantExpComplex.create();
            MantExpComplex z = MantExpComplex.create();
            if(iterations != 0 && RefIteration < MaxRefIteration) {
                refZm = getArrayDeepValue(referenceDeep, RefIteration);
                zWithoutInitVal = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration, refZm).plus_mutable(DeltaSubN);
                z = refZm.plus_mutable(DeltaSubN);
                zc = z.toComplex();
            }
            else if(iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                refZm = getArrayDeepValue(referenceDeep, RefIteration);
                zWithoutInitVal = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration, refZm).plus_mutable(DeltaSubN);
                z = refZm.plus_mutable(DeltaSubN);
                zc = z.toComplex();
            }

            MantExpComplex zoldDeep;


            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if (doBailCheck && bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel)) {
                    escaped = true;

                    finalizeStatistic(true, zc);
                    Object[] object = {iterations, zc, zold, zold2, c, start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel, object);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(zc);
                zoldDeep = z;

                if (max_iterations > 1) {
                    refZm = getArrayDeepValue(referenceDeep, RefIteration);
                    zWithoutInitVal = getArrayDeepValue(referenceDeepData.ReferenceSubCp, RefIteration, refZm).plus_mutable(DeltaSubN);
                    z = refZm.plus_mutable(DeltaSubN);
                    zc = z.toComplex();
                }

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0, z, zoldDeep, cDeep);
                }

                if (zWithoutInitVal.norm_squared().compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = zWithoutInitVal;
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

            Complex refZ;

            if(!usedDeepCode && iterations != 0 && RefIteration < MaxRefIteration) {
                refZ = getArrayValue(reference, RefIteration);
                zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(CDeltaSubN);
                zc = refZ.plus_mutable(CDeltaSubN);
            }
            else if(!usedDeepCode && iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                refZ = getArrayValue(reference, RefIteration);
                zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(CDeltaSubN);
                zc = refZ.plus_mutable(CDeltaSubN);
            }

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if (bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel)) {
                    escaped = true;

                    finalizeStatistic(true, zc);
                    Object[] object = {iterations, zc, zold, zold2, c, start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel, object);
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
                zold.assign(zc);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    refZ = getArrayValue(reference, RefIteration);
                    zWithoutInitVal = getArrayValue(referenceData.ReferenceSubCp, RefIteration, refZ).plus_mutable(CDeltaSubN);
                    zc = refZ.plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0);
                }

                if (zWithoutInitVal.norm_squared() < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = zWithoutInitVal;
                    RefIteration = 0;
                    rebases++;
                }

            }
        }

        finalizeStatistic(false, zc);
        Object[] object = {zc, zold, zold2, c, start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(zc, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsNotScaled(in);

    }

    @Override
    public double iterateJuliaWithPerturbation(Complex[] complexIn, Complex dpixel) {

        double_iterations = 0;
        rebases = 0;

        iterations = 0;

        int RefIteration = iterations;

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z

        Complex pixel = dpixel.plus(refPointSmall);
        Complex z = complexIn[0];
        Complex c = complexIn[1];

        ReferenceData data = referenceData;
        int MaxRefIteration = data.MaxRefIteration;

        Complex zWithoutInitVal = new Complex();

        Complex refZ;

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(z, iterations);
            }

            if (bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, 0.0, pixel)) {
                escaped = true;

                finalizeStatistic(true, z);
                Object[] object = {iterations, z, zold, zold2, c, start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel, object);
                }

                return getAndAccumulateStatsNotDeep(res);
            }

            DeltaSubN = perturbationFunction(DeltaSubN, data, RefIteration);

            RefIteration++;
            double_iterations++;

            zold2.assign(zold);
            zold.assign(z);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1) {
                refZ = getArrayValue(data.Reference, RefIteration);
                zWithoutInitVal = getArrayValue(data.ReferenceSubCp, RefIteration, refZ).plus_mutable(DeltaSubN);
                z = refZ.plus_mutable(DeltaSubN);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(z, zold, zold2, iterations, c, start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;

                data = secondReferenceData;
                MaxRefIteration = data.MaxRefIteration;
                rebases++;
            }
        }

        finalizeStatistic(false, z);
        Object[] object = {z, zold, zold2, c, start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(z, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsNotDeep(in);

    }

    @Override
    public double iterateJuliaWithPerturbation(Complex[] complexIn, MantExpComplex dpixel) {

        float_exp_iterations = 0;
        double_iterations = 0;
        rebases = 0;

        int totalSkippedIterations = 0;

        iterations = 0;

        int RefIteration = iterations;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z

        MantExpComplex cDeep = dpixel.plus(refPointSmallDeep);
        Complex pixel = cDeep.toComplex();
        Complex zc = complexIn[0];
        Complex c = complexIn[1];

        ReferenceDeepData deepData = referenceDeepData;
        ReferenceData data = referenceData;
        int MaxRefIteration = data.MaxRefIteration;

        int minExp = -1000;
        int reducedExp = minExp / (int)getPower();

        MantExpComplex refZm;

        DeltaSubN.Normalize();
        long exp = DeltaSubN.getMinExp();

        boolean useFullFloatExp = useFullFloatExp();
        boolean doBailCheck = useFullFloatExp || TaskRender.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE;

        if(useFullFloatExp || (totalSkippedIterations == 0 && exp <= minExp) || (totalSkippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex zWithoutInitVal = MantExpComplex.create();
            MantExpComplex z = getArrayDeepValue(deepData.Reference, RefIteration).plus_mutable(DeltaSubN);
            MantExpComplex zoldDeep;

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if (doBailCheck && bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel)) {
                    escaped = true;

                    finalizeStatistic(true, zc);
                    Object[] object = {iterations, zc, zold, zold2, c, start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel, object);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                DeltaSubN = perturbationFunction(DeltaSubN, deepData, RefIteration);

                RefIteration++;
                float_exp_iterations++;

                zold2.assign(zold);
                zold.assign(zc);
                zoldDeep = z;

                if (max_iterations > 1) {
                    refZm = getArrayDeepValue(deepData.Reference, RefIteration);
                    zWithoutInitVal = getArrayDeepValue(deepData.ReferenceSubCp, RefIteration, refZm).plus_mutable(DeltaSubN);
                    z = refZm.plus_mutable(DeltaSubN);
                    zc = z.toComplex();
                }

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0, z, zoldDeep , cDeep);
                }

                if (zWithoutInitVal.norm_squared().compareToBothPositive(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = zWithoutInitVal;
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

            Complex refZ;

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(zc, iterations);
                }

                if (bailout_algorithm.escaped(zc, zold, zold2, iterations, c, start, c0, 0.0, pixel)) {
                    escaped = true;

                    finalizeStatistic(true, zc);
                    Object[] object = {iterations, zc, zold, zold2, c, start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel, object);
                    }

                    return getAndAccumulateStatsNotScaled(res);
                }

                CDeltaSubN = perturbationFunction(CDeltaSubN, data, RefIteration);

                RefIteration++;
                double_iterations++;

                zold2.assign(zold);
                zold.assign(zc);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    refZ = getArrayValue(data.Reference, RefIteration);
                    zWithoutInitVal = getArrayValue(data.ReferenceSubCp, RefIteration, refZ).plus_mutable(CDeltaSubN);
                    zc = refZ.plus_mutable(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(zc, zold, zold2, iterations, c, start, c0);
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

        finalizeStatistic(false, zc);
        Object[] object = {zc, zold, zold2, c, start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(zc, zold, zold2, iterations, c, start, c0, pixel);
        }

        return getAndAccumulateStatsNotScaled(in);

    }

    @Override
    protected GenericComplex[] precalculateReferenceData(GenericComplex z, GenericComplex c, NormComponents normData, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations, Complex cz, MantExpComplex mcz) {

        GenericComplex preCalc;

        //5*Z^4 - 1
        if (bigNumLib == Constants.BIGNUM_APFLOAT) {
            preCalc = z.fourth().times(MyApfloat.FIVE).sub(MyApfloat.ONE);
        }
        else {
            preCalc = z.fourth().times_mutable(5).sub_mutable(1);
        }

        GenericComplex preCalc2;

        //2*Z*(Z^4 - 1)
        if (bigNumLib == Constants.BIGNUM_APFLOAT) {
            preCalc2 = z.fourth().sub(MyApfloat.ONE).times(z).times2();
        }
        else {
            preCalc2 = z.fourth().sub_mutable(1).times_mutable(z).times2_mutable();
        }

        MantExpComplex precalcm = null;
        MantExpComplex precalcm2 = null;
        if(deepZoom) {
            precalcm = loc.getMantExpComplex(preCalc);
            precalcm2 = loc.getMantExpComplex(preCalc2);
            setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, precalcm, mcz);
            setArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], iterations, precalcm2, mcz);
        }
        if(lowPrecReferenceOrbitNeeded) {
            setArrayValue(referenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalcm.toComplex() : preCalc.toComplex(), cz);
        }
        if(lowPrecReferenceOrbitNeeded) {
            setArrayValue(referenceData.PrecalculatedTerms[1], iterations, deepZoom ? precalcm2.toComplex() : preCalc2.toComplex(), cz);
        }

        return new GenericComplex[] {};
    }

    @Override
    protected int[] getNeededPrecalculatedTermsIndexes() {

        return new int[] {0, 1};

    }

    @Override
    protected Function[] getPrecalculatedTermsFunctions(Complex c) {
        Function<Complex, Complex> f1 = x -> x.fourth().times_mutable(5).sub_mutable(1);
        Function<Complex, Complex> f2 = x -> x.fourth().sub_mutable(1).times_mutable(x).times2_mutable();
        return new Function[] {f1, f2};
    }

    @Override
    protected Function[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        Function<MantExpComplex, MantExpComplex> f1 = x -> x.fourth().times_mutable(MantExp.FIVE).sub_mutable(MantExp.ONE);
        Function<MantExpComplex, MantExpComplex> f2 = x -> x.fourth().sub_mutable(MantExp.ONE).times_mutable(x).times2_mutable();
        return new Function[] {f1, f2};
    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || !juliter;
    }
    @Override
    public double getDoubleLimit() {
        return 1.0e-5;
    }

    @Override
    public double getDoubleDoubleLimit() {
        return 1.0e-12;
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (isJulia ? "-Julia-" + bigSeed.toStringPretty() : "");
    }

    @Override
    public void function(GenericComplex[] complex) {

        if(complex[0].isZero()) {
            throw new ArithmeticException("Division by zero");
        }

        complex[0] = complex[0].square_mutable();
        complex[0] = (complex[0].plus_mutable(complex[0].reciprocal())).times_mutable(complex[1]);

    }

    /*
 (C * (((z + 4*Z)*z^3)*Z^2 + ((5*Z^4 - 1)*z + 2*Z*(Z^4 - 1))*z)
  + c * (((z + 4*Z)*z^3)*Z^2 + ((2*(3*z + 2*Z)*z + Z^2)*Z^2 + 1)*Z^2))
  /  (Z^2 + (2*Z + z)*z) * Z^2
     */

    @Override
    public Complex perturbationFunction(Complex z, Complex c, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);
        Complex Zsqr = Z.square();
        Complex Z2 = Z.times2();

        Complex temp = z.plus(Z.times4()).times_mutable(z.cube()).times_mutable(Zsqr);

        Complex nom = C.times(temp.plus(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z).times_mutable(z).plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z)).times_mutable(z)))
                .plus_mutable(c.times(temp.plus(z.times(3).plus_mutable(Z2).times_mutable(z).times2_mutable().plus_mutable(Zsqr).times_mutable(Zsqr).plus_mutable(1).times_mutable(Zsqr))));

        Complex denom = Zsqr.plus(Z2.plus(z).times_mutable(z)).times_mutable(Zsqr);

        return nom.divide_mutable(denom);


    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);
        MantExpComplex Zsqr = Z.square();
        MantExpComplex Z2 = Z.times2();

        MantExpComplex temp = z.plus(Z.times4()).times_mutable(z.cube()).times_mutable(Zsqr);

        MantExpComplex nom = Cdeep.times(temp.plus(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z).times_mutable(z).plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z)).times_mutable(z)))
                .plus_mutable(c.times(temp.plus(z.times(MantExp.THREE).plus_mutable(Z2).times_mutable(z).times2_mutable().plus_mutable(Zsqr).times_mutable(Zsqr).plus_mutable(MantExp.ONE).times_mutable(Zsqr))));

        MantExpComplex denom = Zsqr.plus(Z2.plus(z).times_mutable(z)).times_mutable(Zsqr);

        return nom.divide_mutable(denom);
    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex Z = getArrayValue(reference, RefIteration);
        Complex Zsqr = Z.square();
        Complex Z2 = Z.times2();

        Complex temp = z.plus(Z.times4()).times_mutable(z.cube()).times_mutable(Zsqr);

        Complex nom = C.times(temp.plus(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, Z).times_mutable(z).plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, Z)).times_mutable(z)));

        Complex denom = Zsqr.plus(Z2.plus(z).times_mutable(z)).times_mutable(Zsqr);

        return nom.divide_mutable(denom);

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(referenceDeep, RefIteration);
        MantExpComplex Zsqr = Z.square();
        MantExpComplex Z2 = Z.times2();

        MantExpComplex temp = z.plus(Z.times4()).times_mutable(z.cube()).times_mutable(Zsqr);

        MantExpComplex nom = Cdeep.times(temp.plus(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, Z).times_mutable(z).plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, Z)).times_mutable(z)));

        MantExpComplex denom = Zsqr.plus(Z2.plus(z).times_mutable(z)).times_mutable(Zsqr);

        return nom.divide_mutable(denom);
    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {

        Complex Z = getArrayValue(data.Reference, RefIteration);
        Complex Zsqr = Z.square();
        Complex Z2 = Z.times2();

        Complex temp = z.plus(Z.times4()).times_mutable(z.cube()).times_mutable(Zsqr);

        Complex nom = C.times(temp.plus(getArrayValue(data.PrecalculatedTerms[0], RefIteration, Z).times_mutable(z).plus_mutable(getArrayValue(data.PrecalculatedTerms[1], RefIteration, Z)).times_mutable(z)));

        Complex denom = Zsqr.plus(Z2.plus(z).times_mutable(z)).times_mutable(Zsqr);

        return nom.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(data.Reference, RefIteration);
        MantExpComplex Zsqr = Z.square();
        MantExpComplex Z2 = Z.times2();

        MantExpComplex temp = z.plus(Z.times4()).times_mutable(z.cube()).times_mutable(Zsqr);

        MantExpComplex nom = Cdeep.times(temp.plus(getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration, Z).times_mutable(z).plus_mutable(getArrayDeepValue(data.PrecalculatedTerms[1], RefIteration, Z)).times_mutable(z)));

        MantExpComplex denom = Zsqr.plus(Z2.plus(z).times_mutable(z)).times_mutable(Zsqr);

        return nom.divide_mutable(denom);
    }


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
    protected void calculateRefSubCp(GenericComplex z, GenericComplex initVal, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations, Complex cz, MantExpComplex mcz) {

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

        MantExpComplex zsubcpm = null;
        if(deepZoom) {
            zsubcpm = loc.getMantExpComplex(zsubcp);
            setArrayDeepValue(referenceDeepData.ReferenceSubCp, iterations, zsubcpm, mcz);
        }

        if(lowPrecReferenceOrbitNeeded) {
            setArrayValue(referenceData.ReferenceSubCp, iterations, deepZoom ? zsubcpm.toComplex() : zsubcp.toComplex(), cz);
        }

    }

    @Override
    protected GenericComplex referenceFunction(GenericComplex z, GenericComplex c, NormComponents normData, GenericComplex[] initialPrecal, GenericComplex[] precalc) {
        if(z.isZero()) {
            throw new ArithmeticException("Division by zero");
        }
        z = z.square_mutable();
        return (z.plus_mutable(z.reciprocal())).times_mutable(c);
    }

    @Override
    public boolean supportsReferenceCompression() {
        return true;
    }

    @Override
    public Complex function(Complex z, Complex c) {
        z.square_mutable();
        return (z.plus_mutable(z.reciprocal())).times_mutable(c);
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        z.square_mutable();
        return (z.plus_mutable(z.reciprocal())).times_mutable(c);
    }

    @Override
    protected GenericComplex getInputPixel(GenericComplex inputPixel) {
        if(isJulia) {
            return sanitizeInputPixel(inputPixel);
        }
        return inputPixel;
    }

    @Override
    public double getPower() {
        return 4;
    }
}
