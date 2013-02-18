
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
  protected double bailout;
  protected double bailout_squared;
  protected int out_coloring_algorithm;
  protected ColorAlgorithm color_algorithm;
  protected Plane plane;
  protected Rotation rotation;
  protected InitialValue init_val;
  protected boolean periodicity_checking;
  protected ArrayList<Complex> complex_orbit;
  protected Complex pixel_orbit;
  protected int check;
  protected int check_counter;
  protected int update;
  protected int update_counter;
  protected Complex period;


    public Fractal(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.bailout = bailout;
        bailout_squared = bailout * bailout;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.periodicity_checking = periodicity_checking;
   
        rotation = new Rotation(rotation_vals[0], rotation_vals[1]);

        switch (plane_type) {
            case MainWindow.MU_PLANE:
                plane = new MuPlane();
                break;
            case MainWindow.INVERSED_MU_PLANE:
                plane = new InversedMuPlane();
                break;
            case MainWindow.INVERSED_MU2_PLANE:
                plane = new InversedMu2Plane();
                break;
            case MainWindow.INVERSED_MU3_PLANE:
                plane = new InversedMu3Plane();
                break;
            case MainWindow.INVERSED_MU4_PLANE:
                plane = new InversedMu4Plane();
                break;
            case MainWindow.LAMBDA_PLANE:
                plane = new LambdaPlane();
                break;
            case MainWindow.INVERSED_LAMBDA_PLANE:
                plane = new InversedLambdaPlane();
                break;
        }
        

    }

    //orbit
    public Fractal(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.complex_orbit = complex_orbit;
        pixel_orbit = this.complex_orbit.get(0);
        
        rotation = new Rotation(rotation_vals[0], rotation_vals[1]);
        
        switch (plane_type) {
            case MainWindow.MU_PLANE:
                plane = new MuPlane();
                break;
            case MainWindow.INVERSED_MU_PLANE:
                plane = new InversedMuPlane();
                break;
            case MainWindow.INVERSED_MU2_PLANE:
                plane = new InversedMu2Plane();
                break;
            case MainWindow.INVERSED_MU3_PLANE:
                plane = new InversedMu3Plane();
                break;
            case MainWindow.INVERSED_MU4_PLANE:
                plane = new InversedMu4Plane();
                break;
            case MainWindow.LAMBDA_PLANE:
                plane = new LambdaPlane();
                break;
            case MainWindow.INVERSED_LAMBDA_PLANE:
                plane = new InversedLambdaPlane();
                break;
        }
        
        pixel_orbit = plane.getPixel(rotation.getPixel(pixel_orbit, false));

    }

    protected abstract void function(Complex[] complex);

    protected double getPeriodSize() {

        return 1e-13;

    }

    protected boolean periodicityCheck(Complex z) {

        
        //Check for period
        if(z.sub(period).norm_squared() < getPeriodSize()) {
            return true;
        }

                //Update history
        if(check == check_counter) {
            check_counter = 0;

            //Double the value of check
            if(update == update_counter) {
                update_counter = 0;
                check <<= 1;
            }
            update_counter++;

            period = z;  
        } //End of update history

        check_counter++;

        return false;

    }

    public double calculateFractal(Complex pixel) {
        
        return periodicity_checking ? calculateFractalWithPeriodicity(plane.getPixel(rotation.getPixel(pixel, false))) : calculateFractalWithoutPeriodicity(plane.getPixel(rotation.getPixel(pixel, false)));

    }

    protected double calculateFractalWithPeriodicity(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex(0, 0);

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].norm_squared()) >= bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, zold};
                return color_algorithm.getResult(object);
            }
            zold = complex[0];
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }

        }

        return max_iterations;

    }

    protected double calculateFractalWithoutPeriodicity(Complex pixel) {

        int iterations = 0;

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c


        Complex zold = new Complex(0, 0);

        double temp;

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].norm_squared()) >= bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, zold};
                return color_algorithm.getResult(object);   
            }
            zold = complex[0];
            function(complex);
            
        }

        return max_iterations;

    }
  
    public void calculateFractalOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = init_val.getPixel(pixel_orbit);//z
        complex[1] = pixel_orbit;//c
        

        for (; iterations < max_iterations; iterations++) {
           function(complex);
           complex_orbit.add(rotation.getPixel(complex[0], true));
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
