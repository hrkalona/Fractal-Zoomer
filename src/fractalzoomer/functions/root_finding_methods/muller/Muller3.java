/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
import fractalzoomer.main.MainWindow;
import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class Muller3 extends MullerRootFindingMethod {

    public Muller3(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm) {

        super(xCenter, yCenter, size, max_iterations,  plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        switch (out_coloring_algorithm) {

            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-7;
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-7;
                break;           
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                convergent_bailout = 1E-7;
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
       
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

    }

    //orbit
    public Muller3(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }
    
   @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex[] complex = new Complex[5];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(1e-10, 0);//z-1
        complex[2] = new Complex();//z-2
        complex[3] = complex[1].cube().sub_mutable(1); //fz-1
        complex[4] = new Complex(-1, 0); //fz-2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2, pixel, start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
 
        }

        Object[] object = {complex[0], zold, zold2, pixel, start, vars};
        return in_color_algorithm.getResult(object);
        
    }
    
    @Override
    public void calculateFractalOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[5];
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(1e-10, 0);//z-1
        complex[2] = new Complex();//z-2
        complex[3] = complex[1].cube().sub_mutable(1); //fz-1
        complex[4] = new Complex(-1, 0); //fz-2

        Complex temp = null;
        
        for (; iterations < max_iterations; iterations++) {
           function(complex);
           temp = rotation.rotateInverse(complex[0]);
           
           if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
               break;
           }
           
           complex_orbit.add(temp);
        }

    }
    
    @Override
    public Complex iterateFractalDomain(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[5];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(1e-10, 0);//z-1
        complex[2] = new Complex();//z-2
        complex[3] = complex[1].cube().sub_mutable(1); //fz-1
        complex[4] = new Complex(-1, 0); //fz-2

        for (; iterations < max_iterations; iterations++) {
  
            function(complex);
 
        }

        return complex[0];
        
    }
    
    @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex[] complex = new Complex[5];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(1e-10, 0);//z-1
        complex[2] = new Complex();//z-2
        complex[3] = complex[1].cube().sub_mutable(1); //fz-1
        complex[4] = new Complex(-1, 0); //fz-2

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2, pixel, start, vars};
                double[] array = {out_color_algorithm.transformResultToHeight(out_color_algorithm.getResult3D(object), max_iterations), out_color_algorithm.getResult(object)};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
 
        }

        Object[] object = {complex[0], zold, zold2, pixel, start, vars};
        double temp2 = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp2, max_iterations), temp2};
        return array;
        
    }

    @Override
    protected void function(Complex[] complex) {        
        
        Complex fz = complex[0].cube().sub_mutable(1);
   
        mullerMethod(complex[0], complex[1], complex[2], fz, complex[3], complex[4]);

    }

}
