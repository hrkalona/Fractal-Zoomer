/*
 * Copyright (C) 2020 hrkalona2
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
package fractalzoomer.functions.root_finding_methods.durand_kerner;

import fractalzoomer.convergent_bailout_conditions.ConvergentBailoutCondition;
import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public abstract class DurandKernerRootFindingMethod extends RootFindingMethods {

    private Complex[] workSpace;
    protected Complex[] fz;
    protected int degree;
    protected Complex a;
    protected int durandKernerInitializationMethod;
    public static final Complex A = new Complex(0.4, 0.9);

    public DurandKernerRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots, int degree) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);
        a = A;
        this.degree = degree;
        workSpace = new Complex[degree];
        fz = new Complex[degree];
        durandKernerInitializationMethod = 0;

    }

    //orbit
    public DurandKernerRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int degree) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);
        a = A;
        this.degree = degree;
        workSpace = new Complex[degree];
        fz = new Complex[degree];
        durandKernerInitializationMethod = 0;

    }

    public Complex[] durandKernerMethod(Complex[] roots, Complex[] fz) {

        Complex temp;

        for (int i = 0; i < degree; i++) {
            Complex denominator = new Complex(1, 0);

            for (int j = 0; j < degree; j++) {
                if (j == i) {
                    continue;
                }
                denominator.times_mutable(roots[i].sub(roots[j]));
            }

            workSpace[i] = roots[i].sub(fz[i].divide_mutable(denominator));
        }

        for (int i = 0; i < degree; i++) {
            temp = roots[i];
            roots[i] = workSpace[i];
            workSpace[i] = temp;
        }

        return roots;
    }

    public static boolean converged(ConvergentBailoutCondition convergent_bailout_algorithm, Complex[] complex, Complex[] oldValues, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        for(int i = complex.length - 1; i >= 0; i--) { //In reversed order in order to get the correct distance at the end

            if(!convergent_bailout_algorithm.converged(complex[i], oldValues[i], zold, zold2, iterations, c, start, c0, pixel)) {
                return false;
            }
        }

        return true;
    }

    public static void initialize(Complex[] complex, Complex pixel, int method, Complex a) {

        if(method == 0) {
            complex[0] = new Complex(pixel);//z

            for (int i = 1; i < complex.length; i++) {
                complex[i] = complex[0].times(a.pow(i));//(a^i) * z
            }
        }
        else if (method == 1) {
            double r = pixel.norm();
            double arg = pixel.arg();

            double theta = (Math.PI * 2.0) / complex.length;

            for (int i = 0; i < complex.length; i++) {
                complex[i] = new Complex(r * Math.cos(i * theta  + arg), r * Math.sin(i * theta  + arg));
            }
        }
        else if (method == 2) {
            for (int i = 0; i < complex.length; i++) {
                complex[i] = pixel.times(a.times(i).cos());
            }
        }
        else {
            for (int i = 0; i < complex.length; i++) {
                complex[i] = pixel.times(a.times(i).exp());
            }
        }

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[degree];

        initialize(complex, pixel, durandKernerInitializationMethod, a);

        for(int i = 0; i < workSpace.length; i++) {
            workSpace[i] = new Complex();
        }

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[0]);

        return complex;

    }

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {
        iterations = 0;

        if (degree <= 0) {
            return 0;
        }

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 && converged(convergent_bailout_algorithm, complex, workSpace, zold, zold2, iterations, pixel, start, c0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], convergent_bailout_algorithm.getDistance(), zold, zold2, pixel, start, c0, pixel};
                iterationData = object;
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out, complex[0]);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
                }

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[0] = preFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, pixel, start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, pixel, start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, pixel, start, c0, pixel};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in, complex[0]);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start, c0, pixel);
        }

        return in;

    }

}
