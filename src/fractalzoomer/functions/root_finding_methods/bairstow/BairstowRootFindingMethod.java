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
package fractalzoomer.functions.root_finding_methods.bairstow;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public abstract class BairstowRootFindingMethod extends RootFindingMethods {

    protected double[] a;
    protected double[] b;
    protected double[] f;
    protected int n;

    public BairstowRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

    }

    //orbit
    public BairstowRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public Complex bairstowMethod(Complex z) {

        double u = z.getRe();
        double v = z.getIm();

        for (int i = n - 2; i >= 0; i--) {
            b[i] = a[i + 2] - u * b[i + 1] - v * b[i + 2];
            f[i] = b[i + 2] - u * f[i + 1] - v * f[i + 2];
        }

        double c = a[1] - u * b[0] - v * b[1];
        double d = a[0] - v * b[0];

        double g = b[1] - u * f[0] - v * f[1];
        double h = b[0] - v * f[0];

        double ug = u * g;
        double vg = v * g;

        double factor = 1 / (vg * g + h * (h - ug));

        return z.sub_mutable(new Complex((-h * c + g * d) * (factor), (-vg * c + (ug - h) * d) * (factor)));

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[1];
        complex[0] = new Complex(-2 * pixel.getRe(), pixel.getRe() * pixel.getRe() + pixel.getIm() * pixel.getAbsIm());//z = -2s + (s^2 + t|t|)i

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);

        return complex;

    }

    @Override
    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {

        if (n < 2) {
            return 0;
        }

        return super.iterateFractalWithoutPeriodicity(complex, pixel);

    }

    @Override
    protected void iterateFractalOrbit(Complex[] complex, Complex pixel) {

        if (n < 2) {
            return ;
        }

        super.iterateFractalOrbit(complex, pixel);

    }

    @Override
    protected Complex iterateFractalDomain(Complex[] complex, Complex pixel) {

        if (n < 2) {
            return new Complex();
        }

        return super.iterateFractalDomain(complex, pixel);

    }

}
