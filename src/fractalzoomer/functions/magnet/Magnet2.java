package fractalzoomer.functions.magnet;


import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2Magnet;
import fractalzoomer.out_coloring_algorithms.BinaryDecompositionMagnet;
import fractalzoomer.out_coloring_algorithms.BiomorphsMagnet;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.DefaultPerturbation;
import fractalzoomer.fractal_options.InitialValue;
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
import fractalzoomer.in_coloring_algorithms.Squares2;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadiusMagnet;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGridMagnet;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecompositionMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphsMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeAlgorithm2Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeColorDecompositionMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeEscapeRadiusMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGaussianInteger3Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGaussianInteger4Magnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGaussianIntegerMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGridMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimePlusImMagnet;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimePlusReMagnet;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Magnet2 extends Julia {
  private double convergent_bailout;

    public Magnet2(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, String user_plane) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane);

        convergent_bailout = 1E-9;
        
        if(perturbation) {
            pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            pertur_val = new DefaultPerturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        
        if(init_value) {
            init_val = new InitialValue(initial_vals[0], initial_vals[1]);
        }
        else {
            init_val = new InitialValue(0, 0);
        }

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeMagnet();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecompositionMagnet();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecompositionMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2Magnet();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2Magnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimePlusRe();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimePlusReMagnet(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimePlusIm();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimePlusImMagnet(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if(!smoothing) {
                    out_color_algorithm = new BiomorphsMagnet(bailout);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphsMagnet(Math.log(bailout_squared), Math.log(convergent_bailout), bailout);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeColorDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeColorDecompositionMagnet(Math.log(convergent_bailout));   
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianIntegerMagnet(Math.log(convergent_bailout));  
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger3();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianInteger3Magnet(Math.log(convergent_bailout));  
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger4();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianInteger4Magnet(Math.log(convergent_bailout));  
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(4);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeAlgorithm2();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeAlgorithm2Magnet(Math.log(convergent_bailout));  
                }
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeEscapeRadiusMagnet(Math.log(bailout_squared));
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeEscapeRadiusMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGridMagnet(Math.log(bailout_squared));
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGridMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;

        } 
        
        switch (in_coloring_algorithm) {
            
            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag();
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike();       
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm();       
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag();       
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared();       
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared();       
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm();       
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares();       
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2();       
                break;
                
        }


    }

    public Magnet2(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-8;

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeMagnet();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecompositionMagnet();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecompositionMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2Magnet();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2Magnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimePlusRe();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimePlusReMagnet(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimePlusIm();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimePlusImMagnet(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if(!smoothing) {
                    out_color_algorithm = new BiomorphsMagnet(bailout);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphsMagnet(Math.log(bailout_squared), Math.log(convergent_bailout), bailout);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeColorDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeColorDecompositionMagnet(Math.log(convergent_bailout));   
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianIntegerMagnet(Math.log(convergent_bailout));  
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger3();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianInteger3Magnet(Math.log(convergent_bailout));  
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGaussianInteger4();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGaussianInteger4Magnet(Math.log(convergent_bailout));  
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(4);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeAlgorithm2();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeAlgorithm2Magnet(Math.log(convergent_bailout));  
                }
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeEscapeRadiusMagnet(Math.log(bailout_squared));
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeEscapeRadiusMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGridMagnet(Math.log(bailout_squared));
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGridMagnet(Math.log(bailout_squared), Math.log(convergent_bailout));
                }
                break;

        } 
        
        switch (in_coloring_algorithm) {
            
            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag();
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike();       
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm();       
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag();       
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared();       
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared();       
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm();       
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares();       
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2();       
                break;
                
        }


    }

    //orbit
    public Magnet2(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, String user_plane) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane);
        
        if(perturbation) {
            pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            pertur_val = new DefaultPerturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        
        if(init_value) {
            init_val = new InitialValue(initial_vals[0], initial_vals[1]);
        }
        else {
            init_val = new InitialValue(0, 0);
        }

    }

    public Magnet2(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, xJuliaCenter, yJuliaCenter);

    }

    @Override
    protected void function(Complex[] complex) {

        Complex temp3 = complex[1].sub(1);
        Complex temp4 = complex[1].sub(2);
        Complex temp2 = temp3.times(temp4);
        
        Complex temp = (complex[0].cube().plus(temp3.times(3).times(complex[0]))).plus(temp2);
        complex[0] = temp.divide(((complex[0].square().times(3).plus(temp4.times(3).times(complex[0]))).plus(temp2)).plus(1));
        complex[0] = complex[0].square();

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

        Complex tempz = pertur_val.getPixel(init_val.getPixel(pixel));
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
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

        Complex tempz = pertur_val.getPixel(init_val.getPixel(pixel));
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex();
        
        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
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
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
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
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
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
    
    @Override
    public double[] calculateFractal3DWithPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp4;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = pertur_val.getPixel(init_val.getPixel(pixel));
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex();
        
        double temp3;

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
           temp2 = bailout_algorithm.escaped(complex[0]);
           if(temp1 || temp2) {
               Object[] object = {iterations, complex[0], temp2, temp4, zold};
               temp3 = out_color_algorithm.getResult(object);
               double[] array = {25 * Math.log(temp3 - 100799) + 15 * Math.log(iterations + 1) - 100, temp3};
               return array;  
           }
           zold = complex[0];
           function(complex);

           if(periodicityCheck(complex[0])) {
                double[] array = {25 * Math.log(max_iterations + 1) + 15 * Math.log(max_iterations + 1) - 100, max_iterations};
                return array;
           }

        }

        double[] array = {25 * Math.log(max_iterations + 1) + 15 * Math.log(max_iterations + 1) - 100, max_iterations};
        return array;
        
    }

    @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp4;

        Complex tempz = pertur_val.getPixel(init_val.getPixel(pixel));
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c

        Complex zold = new Complex();
        
        double temp3;
        
        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
           temp2 = bailout_algorithm.escaped(complex[0]);
           if(temp1 || temp2) {
               Object[] object = {iterations, complex[0], temp2, temp4, zold};
               temp3 = out_color_algorithm.getResult(object);
               double[] array = {25 * Math.log(temp3 - 100799) + 15 * Math.log(iterations + 1) - 100, temp3};
               return array;   
           }
           zold = complex[0];
           function(complex);
 
        }

        Object[] object = {max_iterations, complex[0]};
        temp3 = in_color_algorithm.getResult(object);
        double result = temp3 == max_iterations ? max_iterations : max_iterations + temp3 - 100820;
        double[] array = {25 * Math.log(result + 1) + 15 * Math.log(max_iterations + 1) - 100, temp3};
        return array;
        
    }

    @Override
    public double[] calculateJulia3DWithPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp4;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();
        

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();

        double temp3;
        
        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
           temp2 = bailout_algorithm.escaped(complex[0]);
           if(temp1 ||  temp2) {
               Object[] object = {iterations, complex[0], temp2, temp4, zold};
               temp3 = out_color_algorithm.getResult(object);
               double[] array = {25 * Math.log(temp3 - 100799) + 15 * Math.log(iterations + 1) - 100, temp3};
               return array;    
           }
           zold = complex[0];
           function(complex);

           if(periodicityCheck(complex[0])) {
               double[] array = {25 * Math.log(max_iterations + 1) + 15 * Math.log(max_iterations + 1) - 100, max_iterations};
               return array;
           }

        }

        double[] array = {25 * Math.log(max_iterations + 1) + 15 * Math.log(max_iterations + 1) - 100, max_iterations};
        return array;
                
    }

    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      Boolean temp1, temp2;
      double temp4;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        
        double temp3;

        for (; iterations < max_iterations; iterations++) {
           temp1 = (temp4 = complex[0].distance_squared(1)) <= convergent_bailout;
           temp2 = bailout_algorithm.escaped(complex[0]);
           if(temp1 ||  temp2) {
               Object[] object = {iterations, complex[0], temp2, temp4, zold};
               temp3 = out_color_algorithm.getResult(object);
               double[] array = {25 * Math.log(temp3 - 100799) + 15 * Math.log(iterations + 1) - 100, temp3};
               return array;  
           }
           zold = complex[0];
           function(complex);

        }

        Object[] object = {max_iterations, complex[0]};
        temp3 = in_color_algorithm.getResult(object);
        double result = temp3 == max_iterations ? max_iterations : max_iterations + temp3 - 100820;
        double[] array = {25 * Math.log(result + 1) + 15 * Math.log(max_iterations + 1) - 100, temp3};
        return array;
        
    }

}
