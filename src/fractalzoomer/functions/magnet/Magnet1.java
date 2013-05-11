package fractalzoomer.functions.magnet;


import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2Magnet;
import fractalzoomer.out_coloring_algorithms.BinaryDecompositionMagnet;
import fractalzoomer.out_coloring_algorithms.BiomorphsMagnet;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.core.Complex;
import fractalzoomer.in_coloring_algorithms.CosMag;
import fractalzoomer.in_coloring_algorithms.DecompositionLike;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.out_coloring_algorithms.EscapeTimeMagnet;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.main.MainWindow;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.fractal_options.Perturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.out_coloring_algorithms.SmoothMagnet1;
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

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, int out_coloring_algorithm, int in_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, periodicity_checking, plane_type, rotation_vals);

        convergent_bailout = 1E-12;
        
        if(perturbation) {
            init_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            init_val = new Perturbation(0, 0);
        }

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                out_color_algorithm = new EscapeTimeMagnet();
                break;
            case MainWindow.SMOOTH_COLOR:
                out_color_algorithm = new SmoothMagnet1(Math.log(bailout_squared), Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                out_color_algorithm = new BinaryDecompositionMagnet();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                out_color_algorithm = new BinaryDecomposition2Magnet();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                out_color_algorithm = new BiomorphsMagnet(bailout);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger();
                break;
                
        }
        
        switch (in_coloring_algorithm) {
            
            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(out_coloring_algorithm);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike(out_coloring_algorithm);       
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm(out_coloring_algorithm);       
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag(out_coloring_algorithm);       
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared(out_coloring_algorithm);       
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared(out_coloring_algorithm);       
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm(out_coloring_algorithm);       
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares(out_coloring_algorithm);       
                break;
                
        }

    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations,int  bailout_test_algorithm, double bailout, int out_coloring_algorithm, int in_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-12;

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                out_color_algorithm = new EscapeTimeMagnet();
                break;
            case MainWindow.SMOOTH_COLOR:
                out_color_algorithm = new SmoothMagnet1(Math.log(bailout_squared), Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                out_color_algorithm = new BinaryDecompositionMagnet();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                out_color_algorithm = new BinaryDecomposition2Magnet();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                out_color_algorithm = new BiomorphsMagnet(bailout);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger();
                break;

        } 
        
        switch (in_coloring_algorithm) {
            
            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(out_coloring_algorithm);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike(out_coloring_algorithm);       
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm(out_coloring_algorithm);       
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag(out_coloring_algorithm);       
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared(out_coloring_algorithm);       
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared(out_coloring_algorithm);       
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm(out_coloring_algorithm);       
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares(out_coloring_algorithm);       
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
    public double calculateFractalWithPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp4;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(1).norm_squared()) <= convergent_bailout;
           temp2 = bailout_algorithm.escaped(complex[0]);
           if(temp1 || temp2) {
               Object[] object = {iterations, complex[0], temp2, temp4, zold};
               return out_color_algorithm.getResult(object);   
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
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp4;

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex();
        
        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(1).norm_squared()) <= convergent_bailout;
           temp2 = bailout_algorithm.escaped(complex[0]);
           if(temp1 || temp2) {
               Object[] object = {iterations, complex[0], temp2, temp4, zold};
               return out_color_algorithm.getResult(object);
           }
           zold = complex[0];
           function(complex);
 
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }

    @Override
    public double calculateJuliaWithPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp4;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();
        

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(1).norm_squared()) <= convergent_bailout;
           temp2 = bailout_algorithm.escaped(complex[0]);
           if(temp1 ||  temp2) {
               Object[] object = {iterations, complex[0], temp2, temp4, zold};
               return out_color_algorithm.getResult(object);
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
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp4;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].sub(1).norm_squared()) <= convergent_bailout;
           temp2 = bailout_algorithm.escaped(complex[0]);
           if(temp1 ||  temp2) {
               Object[] object = {iterations, complex[0], temp2, temp4, zold};
               return out_color_algorithm.getResult(object);
           }
           zold = complex[0];
           function(complex);

        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }

}
