
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Magnet1 extends Julia {
  private double convergent_bailout;

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);

        convergent_bailout = 1E-12;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new SmoothMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new IterationsPlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new Biomorphs(bailout);
                break;
            case MainWindow.CROSS_ORBIT_TRAPS:
                color_algorithm = new CrossOrbitTraps(trap_size);
                break;

        }

    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-12;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new SmoothMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new IterationsPlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new Biomorphs(bailout);
                break;
            case MainWindow.CROSS_ORBIT_TRAPS:
                color_algorithm = new CrossOrbitTraps(trap_size);
                break;

        } 

    }

    //orbit
    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);

    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);

    }

    @Override
    protected Complex function(Complex[] complex) {

        complex[0] = (complex[0].square().plus(complex[1].subNormal(1))).divide(complex[0].timesNormal(2).plus(complex[1].subNormal(2)));
        return complex[0].square();

    }

    @Override
    protected double getPeriodSize() {

        return 1e-26;

    }

    @Override
    protected double calculateFractalWithPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp3, temp4;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(0, 0);//z
        complex[1] = pixel;//c

        Complex zold = new Complex(0, 0);

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(new Complex(1, 0)).magnitude()) < convergent_bailout;
           temp2 = (temp3 = complex[0].magnitude()) > bailout_squared;
           if(temp1 || temp2) {
               Object[] object = {(double)iterations, complex[0], temp3, distance, temp2, temp4, zold};
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

    @Override
    protected double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp3, temp4;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(0, 0);//z
        complex[1] = pixel;//c

        Complex zold = new Complex(0, 0);
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(new Complex(1, 0)).magnitude()) < convergent_bailout;
           temp2 = (temp3 = complex[0].magnitude()) > bailout_squared;
           if(temp1 || temp2 || trapped) {
               Object[] object = {(double)iterations, complex[0], temp3, distance, temp2, temp4, zold};
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
    public void calculateFractalOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(0, 0);//z
        complex[1] = pixel_orbit;//c

        for (; iterations < max_iterations; iterations++) {
           complex[0] = function(complex);
           complex_orbit.add(complex[0]);
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return;  //keep only the visible ones
           //}
        }

    }

    @Override
    protected double calculateJuliaWithPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp3, temp4;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);
        

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex(0, 0);

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(new Complex(1, 0)).magnitude()) < convergent_bailout;
           temp2 = (temp3 = complex[0].magnitude()) > bailout_squared;
           if(temp1 ||  temp2) {
               Object[] object = {(double)iterations, complex[0], temp3, distance, temp2, temp4, zold};
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

    @Override
    protected double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp3, temp4;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex(0, 0);
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(new Complex(1, 0)).magnitude()) < convergent_bailout;
           temp2 = (temp3 = complex[0].magnitude()) > bailout_squared;
           if(temp1 ||  temp2 || trapped) {
               Object[] object = {(double)iterations, complex[0], temp3, distance, temp2, temp4, zold};
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

}
