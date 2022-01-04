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
package fractalzoomer.functions.root_finding_methods.householder3;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public abstract class Householder3RootFindingMethod extends RootFindingMethods {

    public Householder3RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        convergent_bailout = 1e-8;

    }

    //orbit
    public Householder3RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }
    
    public Complex householder3Method(Complex z, Complex fz, Complex dfz, Complex ddfz, Complex dddfz) {

        Complex fz_sqr = fz.square();
        
        z.sub_mutable((fz.times(6).times_mutable(dfz.square()).sub_mutable(fz_sqr.times(3).times_mutable(ddfz)))
                .divide_mutable(dfz.cube().times_mutable(6).sub_mutable(fz.times(dfz).times_mutable(ddfz).times_mutable(6)).plus_mutable(fz_sqr.times(dddfz))));//householder3
        
        return z;
        
    }
    
    public static Complex householder3Method(Complex z, Complex fz, Complex dfz, Complex ddfz, Complex dddfz, Complex relaxation) {

        Complex fz_sqr = fz.square();

        z.sub_mutable((fz.times(6).times_mutable(dfz.square()).sub_mutable(fz_sqr.times(3).times_mutable(ddfz)))
                .divide_mutable(dfz.cube().times_mutable(6).sub_mutable(fz.times(dfz).times_mutable(ddfz).times_mutable(6)).plus_mutable(fz_sqr.times(dddfz)))).times_mutable(relaxation);//householder3

        return z;
        
    }

    public static Complex householder3Step(Complex fz, Complex dfz, Complex ddfz, Complex dddfz) {

        Complex fz_sqr = fz.square();

        return (fz.times(6).times_mutable(dfz.square()).sub_mutable(fz_sqr.times(3).times_mutable(ddfz)))
                .divide_mutable(dfz.cube().times_mutable(6).sub_mutable(fz.times(dfz).times_mutable(ddfz).times_mutable(6)).plus_mutable(fz_sqr.times(dddfz)));//householder3

    }
}
