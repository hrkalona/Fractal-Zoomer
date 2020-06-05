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
    private int durandKernerInitializationMethod;
    public static final Complex A = new Complex(0.4, 0.9);

    public DurandKernerRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots, int degree) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);
        a = A;
        this.degree = degree;
        workSpace = new Complex[degree];
        fz = new Complex[degree];
        durandKernerInitializationMethod = 0;

    }

    //orbit
    public DurandKernerRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int degree) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
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

    private boolean converged(Complex[] complex, Complex[] oldValues) {

        for(int i = 0; i < complex.length; i++) {
            if(complex[i].distance_squared(oldValues[i]) > convergent_bailout) {
                return false;
            }
        }

        return true;
    }

    private void initialize(Complex[] complex, Complex pixel) {

        //TODO: Implement in next version
        if(durandKernerInitializationMethod == 0) {
            complex[0] = new Complex(pixel);//z

            for (int i = 1; i < degree; i++) {
                complex[i] = complex[0].times(a.pow(i));//(a^i) * z
            }
        }
        else if (durandKernerInitializationMethod == 1){
            double r = pixel.norm();
            double arg = pixel.arg();

            double theta = (Math.PI * 2.0) / degree;

            for (int i = 0; i < degree; i++) {
                complex[i] = new Complex(r * Math.cos(i * theta  + arg), r * Math.sin(i * theta  + arg));
            }
        }
        else {
            for (int i = 0; i < degree; i++) {
                complex[i] = pixel.times(Math.cos(i));
            }
        }

    }

    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
        int iterations = 0;
        double temp = 0;

        if (degree <= 0) {
            return 0;
        }

        Complex[] complex = new Complex[degree];

        initialize(complex, pixel);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 && converged(complex, workSpace)) {
                escaped = true;

                temp = complex[0].distance_squared(zold);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, pixel, start);
                }

                Object[] object = {iterations, complex[0], temp, zold, zold2, pixel, start};
                iterationData = object;
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, pixel, start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, pixel, start);
        }

        Object[] object = {complex[0], zold, zold2, pixel, start};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;

    }

    @Override
    public void calculateFractalOrbit() {
        int iterations = 0;

        if (degree <= 0) {
            return;
        }

        Complex[] complex = new Complex[degree];

        initialize(complex, pixel_orbit);

        Complex temp = null;

        for (; iterations < max_iterations; iterations++) {
            function(complex);
            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    @Override
    public Complex iterateFractalDomain(Complex pixel) {
        int iterations = 0;

        if (degree <= 0) {
            return new Complex();
        }

        Complex[] complex = new Complex[degree];

        initialize(complex, pixel);

        for (; iterations < max_iterations; iterations++) {

            function(complex);

        }

        return complex[0];

    }

}
