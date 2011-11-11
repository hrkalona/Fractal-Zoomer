
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

    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);

    }
    
    public Julia(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
        seed = new Complex(xJuliaCenter, yJuliaCenter);
        seed = inverse_plane ? new Complex(1, 0).divide(seed) : seed;

    }

    //orbit
    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);

    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
        seed = new Complex(xJuliaCenter, yJuliaCenter);
        seed = inverse_plane ? new Complex(1, 0).divide(seed) : seed;
        pixel_orbit = this.complex_orbit.get(0);

    }

    @Override
    public double calculateJulia(Complex pixel) {

        return periodicity_checking ? calculateJuliaWithPeriodicity(pixel) : calculateJuliaWithoutPeriodicity(pixel);

    }

    protected double calculateJuliaWithPeriodicity(Complex pixel) {
      int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);


        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex(0, 0);

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, distance, zold};
                return color_algorithm.getResult(object);
            }
            zold = complex[0];
            complex[0] = function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }
        
        return iterations;
    }

    protected double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex(0, 0);
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared || trapped) {
                Object[] object = {(double)iterations, complex[0], temp, distance, zold};
                return color_algorithm.getResult(object);
            }
            zold = complex[0];
            complex[0] = function(complex);
            
            if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
                if(complex[0].absRe() < trap_size) {
                    distance = complex[0].absRe();
                    trapped = true;
                }
                else {
                    if(complex[0].absIm() < trap_size) {
                        distance = complex[0].absIm();
                        trapped = true;
                    }    
                } 
            }

        }

        return iterations;
    }

    @Override
    public void calculateJuliaOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel_orbit;//z
        complex[1] = seed;//c

        for (; iterations < max_iterations; iterations++) {
           complex[0] = function(complex);
           complex_orbit.add(complex[0]);
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return; //keep only the visible ones
           //}
        }

    }

}
