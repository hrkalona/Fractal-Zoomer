package fractalzoomer.functions;


import fractalzoomer.core.Complex;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public abstract class Julia extends Fractal {
  protected Complex seed;

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, boolean periodicity_checking, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, periodicity_checking, plane_type, rotation_vals);

    }
    
    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, boolean periodicity_checking, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, periodicity_checking, plane_type, rotation_vals);
 
        seed = plane.getPixel(new Complex(xJuliaCenter, yJuliaCenter));

    }

    //orbit
    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);

    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        seed = plane.getPixel(new Complex(xJuliaCenter, yJuliaCenter));
        
        pixel_orbit = this.complex_orbit.get(0);
        
        pixel_orbit = rotation.getPixel(pixel_orbit, false);

    }

    @Override
    public double calculateJulia(Complex pixel) {

        return periodicity_checking ? calculateJuliaWithPeriodicity(rotation.getPixel(pixel, false)) : calculateJuliaWithoutPeriodicity(rotation.getPixel(pixel, false));

    }

    public double calculateJuliaWithPeriodicity(Complex pixel) {
      int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();


        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                return out_color_algorithm.getResult(object);
            }
            zold = complex[0];
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }
        
        return max_iterations;
    }

    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                return out_color_algorithm.getResult(object);
            }
            zold = complex[0];
            function(complex);
  
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }

    @Override
    public void calculateJuliaOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel_orbit;//z
        complex[1] = seed;//c

        Complex temp = null;
        
        for (; iterations < max_iterations; iterations++) {
           function(complex);
           temp = rotation.getPixel(complex[0], true);
           
           if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
               break;
           }
           
           complex_orbit.add(temp);
        }

    }

}
