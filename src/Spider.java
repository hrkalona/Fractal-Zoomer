
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Spider extends Julia {

    public Spider(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(2));
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

    public Spider(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(2));
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
    public Spider(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);

    }

    public Spider(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);

    }

    @Override
    protected Complex function(Complex[] complex) {

        return complex[0].square().plus(complex[1]);

    }

    private Complex function2(Complex[] complex) {

        return complex[1].divideNormal(2).plus(complex[0]);

    }

    @Override
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

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, distance};
                return color_algorithm.getResult(object);
            }
            complex[0] = function(complex);
            complex[1] = function2(complex);

            if(periodicityCheck(complex[0])) {
               return max_iterations;
            }
            
        }

        return iterations;
    }

    @Override
    protected double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = pixel;//c
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared || trapped) {
                Object[] object = {(double)iterations, complex[0], temp, distance};
                return color_algorithm.getResult(object);
            }
            complex[0] = function(complex);
            complex[1] = function2(complex);
            
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

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, distance};
                return color_algorithm.getResult(object);
            }
            complex[0] = function(complex);
            complex[1] = function2(complex);

            if(periodicityCheck(complex[0])) {
               return max_iterations;
            }

        }

        return iterations;
    }

    @Override
    protected double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared || trapped) {
                Object[] object = {(double)iterations, complex[0], temp, distance};
                return color_algorithm.getResult(object);
            }
            complex[0] = function(complex);
            complex[1] = function2(complex);
            
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

        Complex z = pixel_orbit;
        Complex c = pixel_orbit;
        for (; iterations < max_iterations; iterations++) {
           Complex[] complex = {z , c};
           complex[0] = z = function(complex);
           c = function2(complex);
           complex_orbit.add(z);
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return;  //keep only the visible ones
           //}
        }

    }

    @Override
    public void calculateJuliaOrbit() {
      int iterations = 0;

        Complex z = pixel_orbit;
        Complex c = seed;
        for (; iterations < max_iterations; iterations++) {
           Complex[] complex = {z , c};
           complex[0] = z = function(complex);
           c = function2(complex);
           complex_orbit.add(z);
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return; //keep only the visible ones
           //}
        }

    }

}
