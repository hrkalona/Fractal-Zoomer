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

package fractalzoomer.functions;

import fractalzoomer.core.Complex;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public abstract class Julia extends Fractal {

    protected Complex seed;
    protected boolean apply_plane_on_julia;
    protected boolean apply_plane_on_julia_seed;

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        if(apply_plane_on_julia_seed) {
            seed = plane.transform(new Complex(xJuliaCenter, yJuliaCenter));
        }
        else {
            seed = new Complex(xJuliaCenter, yJuliaCenter);
        }
        
        this.apply_plane_on_julia = apply_plane_on_julia;
 
    }

    //orbit
    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        if(apply_plane_on_julia_seed) {
            seed = plane.transform(new Complex(xJuliaCenter, yJuliaCenter));
        }
        else {
            seed = new Complex(xJuliaCenter, yJuliaCenter);
        }
        
        this.apply_plane_on_julia = apply_plane_on_julia;

        if(!apply_plane_on_julia) { // recalculate the initial pixel because the transform was added to the super constructor
            pixel_orbit = this.complex_orbit.get(0);
            pixel_orbit = rotation.rotate(pixel_orbit);
        }

    }

    @Override
    public double calculateJulia(Complex pixel) {

        if(apply_plane_on_julia) {
            return periodicity_checking ? calculateJuliaWithPeriodicity(plane.transform(rotation.rotate(pixel))) : calculateJuliaWithoutPeriodicity(plane.transform(rotation.rotate(pixel)));
        }
        else {
            return periodicity_checking ? calculateJuliaWithPeriodicity(rotation.rotate(pixel)) : calculateJuliaWithoutPeriodicity(rotation.rotate(pixel));
        }

    }

    @Override
    public double[] calculateJulia3D(Complex pixel) {

        if(apply_plane_on_julia) {
            return periodicity_checking ? calculateJulia3DWithPeriodicity(plane.transform(rotation.rotate(pixel))) : calculateJulia3DWithoutPeriodicity(plane.transform(rotation.rotate(pixel)));
        }
        else {
            return periodicity_checking ? calculateJulia3DWithPeriodicity(rotation.rotate(pixel)) : calculateJulia3DWithoutPeriodicity(rotation.rotate(pixel));
        }

    }

    protected double calculateJuliaWithPeriodicity(Complex pixel) {
        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }

        return max_iterations;
    }

    protected double calculateJuliaWithoutPeriodicity(Complex pixel) {
        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);

    }

    protected double[] calculateJulia3DWithPeriodicity(Complex pixel) {
        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {max_iterations, max_iterations};
                return array;
            }
        }

        double[] array = {max_iterations, max_iterations};
        return array;

    }

    protected double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        double temp;
        
        Complex[] vars = createGlobalVars();

        for(; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, vars)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, vars};
                temp = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;

    }

    @Override
    public void calculateJuliaOrbit() {
        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(seed);//c

        Complex temp = null;

        for(; iterations < max_iterations; iterations++) {
            function(complex);
            temp = rotation.rotateInverse(complex[0]);

            if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }
    
    @Override
    public Complex calculateJuliaDomain(Complex pixel) {
        
        if(apply_plane_on_julia) {
            return iterateJuliaDomain(plane.transform(rotation.rotate(pixel)));
        }
        else {
            return iterateJuliaDomain(rotation.rotate(pixel));
        }
     
    }
    
    protected Complex iterateJuliaDomain(Complex pixel) {
        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        for(; iterations < max_iterations; iterations++) {
            
            function(complex);

        }

        return complex[0];

    }

}
