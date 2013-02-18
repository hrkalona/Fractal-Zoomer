
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

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals);

        convergent_bailout = 1E-12;
        
        if(perturbation) {
            init_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            init_val = new Perturbation(0, 0);
        }

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTimeMagnet();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new SmoothMagnet1(Math.log(bailout_squared), Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecompositionMagnet();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                color_algorithm = new BinaryDecomposition2Magnet();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new BiomorphsMagnet(bailout);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                color_algorithm = new ColorDecompositionConverge();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                color_algorithm = new EscapeTimeColorDecompositionConverge();
                break;
                
        }

    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-12;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTimeMagnet();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new SmoothMagnet1(Math.log(bailout_squared), Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecompositionMagnet();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                color_algorithm = new BinaryDecomposition2Magnet();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new BiomorphsMagnet(bailout);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                color_algorithm = new ColorDecompositionConverge();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                color_algorithm = new EscapeTimeColorDecompositionConverge();
                break;

        } 

    }

    //orbit
    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        if(perturbation) {
            init_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            init_val = new Perturbation(0, 0);
        }

    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);

    }

    @Override
    protected void function(Complex[] complex) {

        complex[0] = (complex[0].square().plus(complex[1].sub(1))).divide(complex[0].times(2).plus(complex[1].sub(2))).square();

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

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex(0, 0);

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(new Complex(1, 0)).norm_squared()) <= convergent_bailout;
           temp2 = (temp3 = complex[0].norm_squared()) >= bailout_squared;
           if(temp1 || temp2) {
               Object[] object = {(double)iterations, complex[0], temp3, temp2, temp4, zold};
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

    @Override
    protected double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp3, temp4;

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex(0, 0);
        
        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(new Complex(1, 0)).norm_squared()) <= convergent_bailout;
           temp2 = (temp3 = complex[0].norm_squared()) >= bailout_squared;
           if(temp1 || temp2) {
               Object[] object = {(double)iterations, complex[0], temp3, temp2, temp4, zold};
               return color_algorithm.getResult(object);
           }
           zold = complex[0];
           function(complex);
 
        }

        return max_iterations;
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
           temp1 = (temp4 = complex[0].sub(new Complex(1, 0)).norm_squared()) <= convergent_bailout;
           temp2 = (temp3 = complex[0].norm_squared()) >= bailout_squared;
           if(temp1 ||  temp2) {
               Object[] object = {(double)iterations, complex[0], temp3, temp2, temp4, zold};
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

    @Override
    protected double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp3, temp4;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex(0, 0);

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(new Complex(1, 0)).norm_squared()) <= convergent_bailout;
           temp2 = (temp3 = complex[0].norm_squared()) >= bailout_squared;
           if(temp1 ||  temp2) {
               Object[] object = {(double)iterations, complex[0], temp3, temp2, temp4, zold};
               return color_algorithm.getResult(object);
           }
           zold = complex[0];
           function(complex);

        }

        return max_iterations;
    }

}
