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

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, periodicity_checking, plane_type, rotation_vals, rotation_center);

    }
    
    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, periodicity_checking, plane_type, rotation_vals, rotation_center);
 
        seed = plane.getPixel(new Complex(xJuliaCenter, yJuliaCenter));

    }

    //orbit
    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center);

    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center);
        
        seed = plane.getPixel(new Complex(xJuliaCenter, yJuliaCenter));
        
        pixel_orbit = this.complex_orbit.get(0);
        
        pixel_orbit = rotation.getPixel(pixel_orbit, false);

    }

    @Override
    public double calculateJulia(Complex pixel) {

        return periodicity_checking ? calculateJuliaWithPeriodicity(rotation.getPixel(pixel, false)) : calculateJuliaWithoutPeriodicity(rotation.getPixel(pixel, false));

    }
    
    @Override
    public double[] calculateJulia3D(Complex pixel) {

        return periodicity_checking ? calculateJulia3DWithPeriodicity(rotation.getPixel(pixel, false)) : calculateJulia3DWithoutPeriodicity(rotation.getPixel(pixel, false));

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
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                return out_color_algorithm.getResult(object);
            }
            zold.assign(complex[0]);
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
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                return out_color_algorithm.getResult(object);
            }
            zold.assign(complex[0]);
            function(complex);
  
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }
    
    public double[] calculateJulia3DWithPeriodicity(Complex pixel) {
      int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();


        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();

        double temp;
        
        for (; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                temp = out_color_algorithm.getResult(object);
                double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                return array;
            }
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                return array;
            }
        }
        
        double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
        return array;
        
    }


    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        
        double temp;

        for (; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                temp = out_color_algorithm.getResult(object);
                double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                return array;
            }
            zold.assign(complex[0]);
            function(complex);
  
        }

        Object[] object = {max_iterations, complex[0]};
        temp = in_color_algorithm.getResult(object);
        double result = temp == max_iterations ? max_iterations : max_iterations + temp - 100820;
        double[] array = {40 * Math.log(result + 1) - 100, temp};
        return array;
        
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
