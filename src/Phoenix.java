
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Phoenix extends Julia {

    public Phoenix(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane) {

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

    public Phoenix(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane, double xJuliaCenter, double yJuliaCenter) {

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
    public Phoenix(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);

    }

    public Phoenix(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);

    }

    @Override
    protected Complex function(Complex[] complex) {


        double temp = complex[1].getIm();
        return complex[0].square().plus(new Complex(temp * complex[2].getRe() + complex[1].getRe(), temp * complex[2].getIm()));

    }

    @Override
    protected double calculateFractalWithPeriodicity(Complex pixel) {
      int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);

        Complex[] complex = new Complex[3];
        complex[0] = pixel;//z
        complex[1] = pixel;//c
        complex[2] = new Complex(0, 0);//s

        Complex temp_z;
        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, distance};
                return color_algorithm.getResult(object);
            }
            temp_z = function(complex);
            complex[2] = complex[0];
            complex[0] = temp_z;

            if(periodicityCheck(complex[0])) {
               return max_iterations;
            }

        }

        return iterations;
    }

    @Override
    protected double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[3];
        complex[0] = pixel;//z
        complex[1] = pixel;//c
        complex[2] = new Complex(0, 0);//s
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }

        Complex temp_z;
        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared || trapped) {
                Object[] object = {(double)iterations, complex[0], temp, distance};
                return color_algorithm.getResult(object);
            }
            temp_z = function(complex);
            complex[2] = complex[0];
            complex[0] = temp_z;
            
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

        Complex[] complex = new Complex[3];
        complex[0] = pixel;//z
        complex[1] = seed;//c
        complex[2] = new Complex(0, 0);//s
 
        Complex temp_z;
        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, distance};
                return color_algorithm.getResult(object);
            }
            temp_z = function(complex);
            complex[2] = complex[0];
            complex[0] = temp_z;

            if(periodicityCheck(complex[0])) {
               return max_iterations;
            }
            
        }

        return iterations;
    }

    @Override
    protected double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[3];
        complex[0] = pixel;//z
        complex[1] = seed;//c
        complex[2] = new Complex(0, 0);//s
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }

        Complex temp_z;
        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].magnitude()) > bailout_squared || trapped) {
                Object[] object = {(double)iterations, complex[0], temp, distance};
                return color_algorithm.getResult(object);
            }
            temp_z = function(complex);
            complex[2] = complex[0];
            complex[0] = temp_z;
            
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

        Complex[] complex = new Complex[3];
        complex[0] = pixel_orbit;//z
        complex[1] = pixel_orbit;//c
        complex[2] = new Complex(0, 0);//s

        Complex temp;
        for(; iterations < max_iterations; iterations++) {
           temp = function(complex);
           complex[2] = complex[0];
           complex[0] = temp;
           complex_orbit.add(complex[0]);
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return;  //keep only the visible ones
           //}
        }

    }

    @Override
    public void calculateJuliaOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[3];
        complex[0] = pixel_orbit;//z
        complex[1] = seed;//c
        complex[2] = new Complex(0, 0);//s

        Complex temp;
        for(; iterations < max_iterations; iterations++) {
           temp = function(complex);
           complex[2] = complex[0];
           complex[0] = temp;
           complex_orbit.add(complex[0]);
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return; //keep only the visible ones
           //}
        }

    }

}
