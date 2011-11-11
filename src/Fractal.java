
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public abstract class Fractal {
  protected double xCenter;
  protected double yCenter;
  protected double size;
  protected int max_iterations;
  protected int bailout;
  protected int bailout_squared;
  protected int out_coloring_algorithm;
  protected ColorAlgorithm color_algorithm;
  protected Plane plane;
  protected boolean periodicity_checking;
  protected ArrayList<Complex> complex_orbit;
  protected Complex pixel_orbit;
  protected int check;
  protected int check_counter;
  protected int update;
  protected int update_counter;
  protected Complex period;
  protected boolean trapped;
  protected double trap_size;
  protected double distance;


    public Fractal(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.bailout = bailout;
        bailout_squared = bailout * bailout;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.periodicity_checking = periodicity_checking;
        trapped = false;
        trap_size = 0.05;
        distance = 1E20;

        if(inverse_plane) {
            plane = new InversedPlane();
        }
        else {
            plane = new NormalPlane();
        }

    }

    //orbit
    public Fractal(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.complex_orbit = complex_orbit;
        pixel_orbit = this.complex_orbit.get(0);
        pixel_orbit = inverse_plane ? new Complex(1, 0).divide(pixel_orbit) : pixel_orbit;

    }

    protected abstract Complex function(Complex[] complex);

    protected double getPeriodSize() {

        return 1e-13;

    }

    protected boolean periodicityCheck(Complex z) {

        
        //Check for period
        if(z.sub(period).magnitude() < getPeriodSize()) {
            return true;
        }

                //Update history
        if(check == check_counter) {
            check_counter = 0;

            //Double the value of check
            if(update == update_counter) {
                update_counter = 0;
                check *= 2;
            }
            update_counter++;

            period = z;  
        } //End of update history

        check_counter++;

        return false;

    }

    public double calculateFractal(Complex pixel) {
        
        return periodicity_checking ? calculateFractalWithPeriodicity(plane.getPixel(pixel)) : calculateFractalWithoutPeriodicity(plane.getPixel(pixel));

    }

    protected double calculateFractalWithPeriodicity(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = pixel;//c

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

    protected double calculateFractalWithoutPeriodicity(Complex pixel) {

        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = pixel;//c
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }

        Complex zold = new Complex(0, 0);

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
    
    /*protected double calculateFractalCrossTraps(Complex pixel) {
        
        int iterations = 0;
        
        double distance = 0;
        
        boolean trapped = false;
        double trap_size = 0.05;
        
        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = pixel;//c

        for (; iterations < max_iterations; iterations++) {
            
            complex[0] = function(complex);
            
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
            
            if(trapped || complex[0].magnitude() > bailout_squared) {
                break;
            }
             
        }
        
        return iterations == max_iterations ? iterations : distance / trap_size * 100;
        
    }
    
    protected double calculateFractalEpsilonCrossBailout(Complex pixel) {
        
        int iterations = 0;
        
        double distance = 1e20;
        
        Complex[] complex = new Complex[2];
        complex[0] = new Complex(0, 0);//z
        complex[1] = pixel;//c

        for (; iterations < max_iterations; iterations++) {
            
            complex[0] = function(complex);
                       
            double zMinusPointModulus = Math.min(complex[0].absRe(), complex[0].absIm());
            
            if(zMinusPointModulus < distance) {
                distance = zMinusPointModulus;
            }
            
            if(zMinusPointModulus > bailout_squared) {
                break;
            }
  
        }
   
        return distance * 100;
      
    }*/
  
    public void calculateFractalOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel_orbit;//z
        complex[1] = pixel_orbit;//c

        for (; iterations < max_iterations; iterations++) {
           complex[0] = function(complex);
           complex_orbit.add(complex[0]);
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return;  //keep only the visible ones
           //}
        }

    }

    protected abstract void calculateJuliaOrbit();
    
    protected abstract double calculateJulia(Complex pixel);

    protected double getXCenter() {

        return xCenter;

    }

    protected double getYCenter() {

        return yCenter;

    }

    protected double getSize() {

        return size;

    }

    protected int getMaxIterations() {

        return max_iterations;

    }

}
