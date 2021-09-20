package fractalzoomer.functions.root_finding_methods.aberth_ehrlich;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.functions.root_finding_methods.durand_kerner.DurandKernerRootFindingMethod;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class AberthEhrlichRootFindingMethod extends RootFindingMethods {

    private Complex[] workSpace;
    protected Complex[] fz;
    protected Complex[] dfz;
    protected int degree;
    protected Complex a;
    protected int aberthEhrlichInitializationMethod;
    public static final Complex A = new Complex(0.4, 0.9);

    public AberthEhrlichRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots, int degree) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);
        a = A;
        this.degree = degree;
        workSpace = new Complex[degree];
        fz = new Complex[degree];
        dfz = new Complex[degree];
        aberthEhrlichInitializationMethod = 0;

    }

    //orbit
    public AberthEhrlichRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int degree) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
        a = A;
        this.degree = degree;
        workSpace = new Complex[degree];
        fz = new Complex[degree];
        dfz = new Complex[degree];
        aberthEhrlichInitializationMethod = 0;

    }

    public Complex[] aberthEhrlichMethod(Complex[] roots, Complex[] fz, Complex[] dfz) {

        Complex temp;

        for (int i = 0; i < degree; i++) {
            Complex sum = new Complex();

            for (int j = 0; j < degree; j++) {
                if (j == i) {
                    continue;
                }
                sum.plus_mutable(roots[i].sub(roots[j]).reciprocal_mutable());
            }

            Complex temp2 = fz[i].divide(dfz[i]);
            workSpace[i] = roots[i].sub(temp2.divide_mutable(temp2.times(sum).r_sub_mutable(1)));
        }

        for (int i = 0; i < degree; i++) {
            temp = roots[i];
            roots[i] = workSpace[i];
            workSpace[i] = temp;
        }

        return roots;
    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[degree];

        DurandKernerRootFindingMethod.initialize(complex, pixel, aberthEhrlichInitializationMethod, a);

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[0]);

        return complex;

    }

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {
        iterations = 0;
        double temp = 0;

        if (degree <= 0) {
            return 0;
        }

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 && DurandKernerRootFindingMethod.converged(complex, workSpace, convergent_bailout)) {
                escaped = true;

                temp = complex[0].distance_squared(zold);

                Object[] object = {iterations, complex[0], temp, zold, zold2, pixel, start, c0};
                iterationData = object;
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0);
                }

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, pixel, start, c0);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, pixel, start, c0);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, pixel, start, c0};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0);
        }

        return in;

    }
}
