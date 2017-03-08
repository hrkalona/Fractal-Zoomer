/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
package fractalzoomer.functions.root_finding_methods.muller;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public abstract class MullerRootFindingMethod extends RootFindingMethods {

    public MullerRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, boolean[] user_outcoloring_special_color, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    //orbit
    public MullerRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }
    
    public Complex mullerMethod(Complex z, Complex z1, Complex z2, Complex fz, Complex fz1, Complex fz2) {
        
        Complex hk = z.sub(z1);
        Complex hk1 = z1.sub(z2);
        Complex rk = hk.divide(hk1);
        
        Complex rkp1 = rk.plus(1);
        Complex rksqr = rk.square();
        Complex ck = fz.times(rkp1);
        Complex bk = fz.times(rk.times(2).plus_mutable(1)).sub(fz1.times(rkp1.square())).plus(fz2.times(rksqr));
        Complex ak = fz.times(rk).sub(fz1.times(rkp1.times(rk))).plus(fz2.times(rksqr));       
        
        Complex ck2 = ck.times(2);
        Complex temp = (bk.square().sub(ak.times(ck).times_mutable(4))).sqrt();
        
        Complex denom1 = bk.plus(temp);
        Complex denom2 = bk.sub(temp);
        
        Complex qk;
        if(denom1.norm_squared() > denom2.norm_squared()) {
            qk = ck2.divide(denom1);
        }
        else {
            qk = ck2.divide(denom2);
        }
        
        z2.assign(z1);
        z1.assign(z);
        z.sub_mutable(hk.times_mutable(qk));
        fz2.assign(fz1);
        fz1.assign(fz);
        
        return z;
        
    }
}
