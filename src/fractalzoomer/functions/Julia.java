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
package fractalzoomer.functions;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.utils.ColorAlgorithm;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public abstract class Julia extends Fractal {

    protected Complex seed;
    private boolean apply_plane_on_julia;
    protected boolean updatedJuliter;

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);
        isJulia = false;
    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        if (apply_plane_on_julia_seed) {
            seed = plane.transform(new Complex(xJuliaCenter, yJuliaCenter));
        } else {
            seed = new Complex(xJuliaCenter, yJuliaCenter);
        }

        this.apply_plane_on_julia = apply_plane_on_julia;
        isJulia = true;
        updatedJuliter = false;

    }

    //orbit
    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
        isJulia = false;
    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        if (apply_plane_on_julia_seed) {
            seed = plane.transform(new Complex(xJuliaCenter, yJuliaCenter));
        } else {
            seed = new Complex(xJuliaCenter, yJuliaCenter);
        }

        this.apply_plane_on_julia = apply_plane_on_julia;

        if (!apply_plane_on_julia) { // recalculate the initial pixel because the transform was added to the super constructor
            pixel_orbit = this.complex_orbit.get(0);
            pixel_orbit = rotation.rotate(pixel_orbit);
        }
        isJulia = true;
        updatedJuliter = false;

    }

    public Complex getTransformedPixelJulia(Complex pixel) {

        if (apply_plane_on_julia) {
            return plane.transform(rotation.rotate(pixel));
        } else {
            return rotation.rotate(pixel);
        }

    }

    @Override
    public final double calculateJulia(GenericComplex gpixel) {

        escaped = false;
        hasTrueColor = false;
        updatedJuliter = false;

        statValue = 0;
        trapValue = 0;

        Complex pixel = (Complex)gpixel;

        resetGlobalVars();

        Complex transformed = getTransformedPixelJulia(pixel);

        if (statistic != null) {
            statistic.initialize(transformed, pixel);
        }

        if (trap != null) {
            trap.initialize(transformed);
        }

        return periodicity_checking ? iterateFractalWithPeriodicity(juliter ? initialize(transformed) : initializeSeed(transformed), transformed) : iterateFractalWithoutPeriodicity(juliter ? initialize(transformed) : initializeSeed(transformed), transformed);

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        return complex;

    }

    @Override
    public void updateValues(Complex[] complex) {
        if(isJulia && juliter && !updatedJuliter && iterations == juliterIterations) {
            updatedJuliter = true;
            complex[1] = new Complex(seed);

            if(!juliterIncludeInitialIterations) {
                iterations = 0;
            }
        }
    }

    @Override
    public final void calculateJuliaOrbit() {

        Complex[] complex = juliter ? initialize(pixel_orbit) : initializeSeed(pixel_orbit);
        iterateFractalOrbit(complex, pixel_orbit);

    }

    @Override
    public final Complex calculateJuliaDomain(Complex pixel) {

        updatedJuliter = false;

        resetGlobalVars();

        Complex transformed = getTransformedPixelJulia(pixel);

        return iterateFractalDomain(juliter ? initialize(transformed) : initializeSeed(transformed), transformed);

    }

    @Override
    public double getJulia3DHeight(double value) {

        return ColorAlgorithm.transformResultToHeight(value, max_iterations);

    }

}
