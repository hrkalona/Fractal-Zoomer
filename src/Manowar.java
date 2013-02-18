
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class Manowar extends Julia {

    public Manowar(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals);

        if(perturbation) {
            init_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            init_val = new InitialValue(perturbation_vals[0], perturbation_vals[1]);
        }
                
        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(2));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                color_algorithm = new BinaryDecomposition2();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new Biomorphs(bailout);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                color_algorithm = new EscapeTimeColorDecomposition();
                break;

        }

    }

    public Manowar(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(2));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                color_algorithm = new BinaryDecomposition2();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new Biomorphs(bailout);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                color_algorithm = new EscapeTimeColorDecomposition();
                break;

        }

    }

    //orbit
    public Manowar(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        if(perturbation) {
            init_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            init_val = new InitialValue(perturbation_vals[0], perturbation_vals[1]);
        }

    }

    public Manowar(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);

    }

    @Override
    protected void function(Complex[] complex) {

        Complex temp = complex[0].square().plus(complex[1]).plus(complex[2]);
        complex[1] = complex[0];
        complex[0] = temp;

    }


    @Override
    protected double calculateFractalWithPeriodicity(Complex pixel) {
      int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = tempz;//z1
        complex[2] = pixel;//c

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].norm_squared()) >= bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp};
                return color_algorithm.getResult(object);
            }
            function(complex);

            if(periodicityCheck(complex[0])) {
               return max_iterations;
            }
            
        }

        return max_iterations;
    }

    @Override
    protected double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = tempz;//z1
        complex[2] = pixel;//c
        
        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].norm_squared()) >= bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp};
                return color_algorithm.getResult(object);
            }
            function(complex);

        }

        return max_iterations;
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
        complex[1] = pixel;//z1
        complex[2] = seed;//c

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].norm_squared()) >= bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp};
                return color_algorithm.getResult(object);
            }
            function(complex);

            if(periodicityCheck(complex[0])) {
               return max_iterations;
            }

        }

        return max_iterations;
    }

    @Override
    protected double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      
     

        Complex[] complex = new Complex[3];
        complex[0] = pixel;//z
        complex[1] = pixel;//z1
        complex[2] = seed;//c

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].norm_squared()) >= bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp};
                return color_algorithm.getResult(object);
            }
            function(complex);

        }

        return max_iterations;
    }

    @Override
    public void calculateFractalOrbit() {
      int iterations = 0;

        Complex tempz = init_val.getPixel(pixel_orbit);
        
        Complex[] complex = new Complex[3];
        complex[0] = tempz;//z
        complex[1] = tempz;//z1
        complex[2] = pixel_orbit;//c

        for (; iterations < max_iterations; iterations++) {
           function(complex);
           complex_orbit.add(rotation.getPixel(complex[0], true));
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
        complex[1] = pixel_orbit;//z1
        complex[2] = seed;//c
        
        for (; iterations < max_iterations; iterations++) {
           function(complex);
           complex_orbit.add(rotation.getPixel(complex[0], true));
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return; //keep only the visible ones
           //}
        }

    }

}
    

