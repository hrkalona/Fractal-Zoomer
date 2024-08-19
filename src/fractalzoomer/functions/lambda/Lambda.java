
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
import org.apfloat.Apfloat;

import java.util.ArrayList;
import java.util.function.Function;

/**
 *
 * @author hrkalona
 */
public class Lambda extends Julia {

    public Lambda() {
        super();
    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

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

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);

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
    public Lambda(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

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

    public Lambda(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);
        defaultInitVal = new InitialValue(0.5, 0);
        pertur_val = new DefaultPerturbation();
        init_val = defaultInitVal;
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
        Complex refZ = null;
        if(reference.compressed) {
            refZ = getArrayValue(reference, RefIteration);
        }

        Complex temp = z.plus(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, refZ)).times_mutable(z).negative_mutable();

        return temp.times(C).plus_mutable(getArrayValue(referenceData.PrecalculatedTerms[1], RefIteration, refZ).plus_mutable(temp).times_mutable(c));



    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, MantExpComplex c, int RefIteration) {

        MantExpComplex refZ = null;
        if(referenceDeep.compressed) {
            refZ = getArrayDeepValue(referenceDeep, RefIteration);
        }

        MantExpComplex temp = z.plus(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, refZ)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep).plus_mutable(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], RefIteration, refZ).plus_mutable(temp).times_mutable(c));
    }

    @Override
    public Complex perturbationFunction(Complex z, int RefIteration) {

        Complex refZ = null;
        if(reference.compressed) {
            refZ = getArrayValue(reference, RefIteration);
        }

        Complex temp = z.plus(getArrayValue(referenceData.PrecalculatedTerms[0], RefIteration, refZ)).times_mutable(z).negative_mutable();

        return temp.times(C);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, int RefIteration) {

        MantExpComplex refZ = null;
        if(referenceDeep.compressed) {
            refZ = getArrayDeepValue(referenceDeep, RefIteration);
        }

        MantExpComplex temp = z.plus(getArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], RefIteration, refZ)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep);
    }

    @Override
    public Complex perturbationFunction(Complex z, ReferenceData data, int RefIteration) {

        Complex refZ = null;
        if(data.Reference.compressed) {
            refZ = getArrayValue(data.Reference, RefIteration);
        }

        Complex temp = z.plus(getArrayValue(data.PrecalculatedTerms[0], RefIteration, refZ)).times_mutable(z).negative_mutable();

        return temp.times(C);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex z, ReferenceDeepData data, int RefIteration) {

        MantExpComplex refZ = null;
        if(data.Reference.compressed) {
            refZ = getArrayDeepValue(data.Reference, RefIteration);
        }

        MantExpComplex temp = z.plus(getArrayDeepValue(data.PrecalculatedTerms[0], RefIteration, refZ)).times_mutable(z).negative_mutable();

        return temp.times(Cdeep);
    }



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
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
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

        finalizeStatistic(false,z);
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
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
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
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
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
                    setTrueColorOut(z, zold, zold2, iterations, c, start, c0, pixel);
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
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
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
                        setTrueColorOut(zc, zold, zold2, iterations, c, start, c0, pixel);
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
    protected GenericComplex[] precalculateReferenceData(GenericComplex z, GenericComplex c, NormComponents normData, Location loc, int bigNumLib, boolean lowPrecReferenceOrbitNeeded, boolean deepZoom, ReferenceData referenceData, ReferenceDeepData referenceDeepData, int iterations, Complex cz, MantExpComplex mcz) {

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

        MantExpComplex precalcm = null;
        if(deepZoom) {
            precalcm = loc.getMantExpComplex(preCalc);
            setArrayDeepValue(referenceDeepData.PrecalculatedTerms[0], iterations, precalcm, mcz);
        }
        if(lowPrecReferenceOrbitNeeded) {
            setArrayValue(referenceData.PrecalculatedTerms[0], iterations, deepZoom ? precalcm.toComplex() : preCalc.toComplex(), cz);
        }

        GenericComplex preCalc2;

        //Maybe this is not needed, as Z-Z^2 does not go to 0 when Z = 0.5
        if(normData != null) {
            preCalc2 = z.sub(z.squareFast(normData));
        }
        else {
            preCalc2 = z.sub(z.square());
        }

        if(!isJulia) {
            MantExpComplex precalc2m = null;
            if(deepZoom) {
                precalc2m = loc.getMantExpComplex(preCalc2);
                setArrayDeepValue(referenceDeepData.PrecalculatedTerms[1], iterations, precalc2m, mcz);
            }
            if(lowPrecReferenceOrbitNeeded) {
                setArrayValue(referenceData.PrecalculatedTerms[1], iterations, deepZoom ? precalc2m.toComplex() : preCalc2.toComplex(), cz);
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
    protected Function[] getPrecalculatedTermsFunctions(Complex c) {
        Function<Complex, Complex> f1 = x -> x.times2().sub_mutable(1);
        if (!isJulia) {
            Function<Complex, Complex> f2 = x -> x.sub(x.square());
            return new Function[] {f1, f2};
        }
        return new Function[] {f1};
    }

    @Override
    protected Function[] getPrecalculatedTermsFunctionsDeep(MantExpComplex c) {
        Function<MantExpComplex, MantExpComplex> f1 = x -> x.times2().sub_mutable(MantExp.ONE);
        if (!isJulia) {
            Function<MantExpComplex, MantExpComplex> f2 = x -> x.sub(x.square());
            return new Function[] {f1, f2};
        }
        return new Function[] {f1};
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
        return precalc[0].times_mutable(c);
    }

    @Override
    public boolean supportsReferenceCompression() {
        return true;
    }

    @Override
    public Complex function(Complex z, Complex c) {
        return c.times(z.sub(z.square()));
    }

    @Override
    public MantExpComplex function(MantExpComplex z, MantExpComplex c) {
        return c.times(z.sub(z.square()));
    }

    @Override
    public double getPower() {
        return 2;
    }
}
