
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class MandelbrotNth extends Julia {
  private double z_exponent;
  private boolean burning_ship;

    public MandelbrotNth(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane, boolean burning_ship, double z_exponent) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
        this.burning_ship = burning_ship;
        this.z_exponent = z_exponent;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(z_exponent));
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

    public MandelbrotNth(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane, boolean burning_ship, double z_exponent, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
        this.burning_ship = burning_ship;
        this.z_exponent = z_exponent;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(z_exponent));
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
    public MandelbrotNth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane, boolean burning_ship, double z_exponent) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
        this.burning_ship = burning_ship;
        this.z_exponent = z_exponent;

    }

    public MandelbrotNth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane, boolean burning_ship, double z_exponent, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
        this.burning_ship = burning_ship;
        this.z_exponent = z_exponent;

    }

    @Override
    protected Complex function(Complex[] complex) {

        return burning_ship ? new Complex(complex[0].absRe(), complex[0].absIm()).pow(z_exponent).plus(complex[1]) : complex[0].pow(z_exponent).plus(complex[1]);

    }
    
   /* private boolean mandelbrotOptimization(Complex pixel) {

        double temp = pixel.getRe();
        double temp2 = pixel.getIm();


        //If we have reached this part, this thread contains a part of the area that needs to be tested for optimazation.
        //So we need to check if this pixel is inside the area mentioned before. If not, we dont proceed with the equations.
        if(temp > -1.36792 && temp < 0.37505 && temp2 < 0.83794 && temp2 > -0.83794) {
            double temp3 = temp2 * temp2;
            double temp6 = temp + 1.309;

            if(temp6 * temp6 + temp3 < 0.00345) { //bulb 4
                return true;
            }


            double temp5 = temp + 1;

            if(temp5 * temp5 + temp3 < 0.0625) { //bulb 2
                return true;
            }


            double temp4 = temp - 0.25;
            double q = temp4 * temp4 + temp3;

            if(q * (q + temp4) < 0.25 * temp3) { //Cardioid
                return true;
            }

            double temp7 = temp + 0.125;
            double temp8 = temp2 - 0.744;

            if(temp7 * temp7 + temp8 * temp8 < 0.0088) { //bulb 3 lower
                return true;
            }


            double temp9 = temp2 + 0.744;

            if(temp7 * temp7 + temp9 * temp9 < 0.0088) { //bulb 3 upper
                return true;
            }
        }

        return false;

    }*/

    @Override
    protected double calculateFractalWithPeriodicity(Complex pixel) {
     
        //if(mandelbrot_optimization && mandelbrotOptimization(pixel)) {
            //return max_iterations;
        //}

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);
        
        int iterations = 0;

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

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
            
        }

        return iterations;
    }

    @Override
    protected double calculateFractalWithoutPeriodicity(Complex pixel) {

        //if(mandelbrot_optimization && mandelbrotOptimization(pixel)) {
            //return max_iterations;
        //}

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
