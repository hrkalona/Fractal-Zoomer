/*
 * Copyright (C) 2020 hrkalona
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
package fractalzoomer.functions.root_finding_methods.traub_ostrowski;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public abstract class TraubOstrowskiRootFindingMethod extends RootFindingMethods {

    public TraubOstrowskiRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

        setConvergentBailout(1E-8);
        
    }

    //orbit
    public TraubOstrowskiRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

    }
    
    public static Complex getFunctionArgument(Complex z, Complex fz, Complex dfz) {
        
        return z.sub(fz.divide(dfz));
        
    }
    
    public Complex traubOstrowskiMethod(Complex z, Complex fz, Complex dfz, Complex fnewtons) {
        
        z.sub_mutable((fz.times(fnewtons.sub(fz))).divide_mutable(dfz.times(fnewtons.times2().sub_mutable(fz))));
        
        return z;
        
    }
    
    public static Complex traubOstrowskiMethod(Complex z, Complex fz, Complex dfz, Complex fnewtons, Complex relaxation) {

        z.sub_mutable((fz.times(fnewtons.sub(fz))).divide_mutable(dfz.times(fnewtons.times2().sub_mutable(fz))).times_mutable(relaxation));
        
        return z;
        
    }

    public static Complex traubOstrowskiStep(Complex fz, Complex dfz, Complex fnewtons) {

        return (fz.times(fnewtons.sub(fz))).divide_mutable(dfz.times(fnewtons.times2().sub_mutable(fz)));

    }
    
}
