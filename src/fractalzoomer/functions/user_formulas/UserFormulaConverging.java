/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.functions.user_formulas;

import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.DefaultInitialValue;
import fractalzoomer.fractal_options.DefaultPerturbation;
import fractalzoomer.fractal_options.InitialValue;
import fractalzoomer.fractal_options.Perturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.in_coloring_algorithms.DecompositionLike;
import fractalzoomer.main.MainWindow;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.Biomorphs;
import fractalzoomer.in_coloring_algorithms.CosMag;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.Squares2;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.out_coloring_algorithms.ColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadiusNova;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGridNova;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2RootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphsNova;
import fractalzoomer.out_coloring_algorithms.SmoothColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGridNova;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeRootFindingMethod;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaConverging extends Julia {
  protected double convergent_bailout;
  private ExpressionNode expr;
  private Parser parser;
  private ExpressionNode expr2;
  private Parser parser2;
  int iterations;

    public UserFormulaConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, String user_formula,  String user_formula2, String user_plane) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane);

        convergent_bailout = 1E-12;
              
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
            init_val = new DefaultInitialValue(initial_vals[0], initial_vals[1]);
        }
        
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTime();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeRootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecompositionRootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2RootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if(!smoothing) {
                    out_color_algorithm = new Biomorphs(2);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphsNova(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new ColorDecompositionRootFindingMethod();
                }
                else {
                    out_color_algorithm = new SmoothColorDecompositionRootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeColorDecompositionRootFindingMethod();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeColorDecompositionRootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                out_color_algorithm = new EscapeTimeGaussianInteger3();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                out_color_algorithm = new EscapeTimeGaussianInteger4();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(3);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2();
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadiusNova();
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGridNova();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGridNova(Math.log(convergent_bailout));
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
        
        parser = new Parser();
        expr = parser.parse(user_formula);
        
        parser2 = new Parser();
        expr2 = parser2.parse(user_formula2); 

    }

    

     public UserFormulaConverging(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_formula,  String user_formula2, String user_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-12;
              
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTime();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeRootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:                
                convergent_bailout = 1E-7; 
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecompositionRootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-7; 
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2RootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm();
                break;
             case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if(!smoothing) {
                    out_color_algorithm = new Biomorphs(2);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphsNova(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new ColorDecompositionRootFindingMethod();
                }
                else {
                    out_color_algorithm = new SmoothColorDecompositionRootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeColorDecompositionRootFindingMethod();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeColorDecompositionRootFindingMethod(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                out_color_algorithm = new EscapeTimeGaussianInteger3();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                out_color_algorithm = new EscapeTimeGaussianInteger4();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(3);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2();
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadiusNova();
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGridNova();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGridNova(Math.log(convergent_bailout));
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
        
        parser = new Parser();
        expr = parser.parse(user_formula);
        
        parser2 = new Parser();
        expr2 = parser2.parse(user_formula2); 

    }

    //orbit
    public UserFormulaConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, String user_formula,  String user_formula2, String user_plane) {

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
            init_val = new DefaultInitialValue(initial_vals[0], initial_vals[1]);
        }
        
        parser = new Parser();
        expr = parser.parse(user_formula);
        
        parser2 = new Parser();
        expr2 = parser2.parse(user_formula2); 

    }

    public UserFormulaConverging(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_formula,  String user_formula2, String user_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, xJuliaCenter, yJuliaCenter);
        
        parser = new Parser();
        expr = parser.parse(user_formula);
        
        parser2 = new Parser();
        expr2 = parser2.parse(user_formula2); 
        
    }

    
    
    @Override
    protected void function(Complex[] complex) {
        
        /** Z= **/
        if(parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }
        
        //expr.accept(new SetVariable("z", complex[0]));
        parser.setZvalue(complex[0]);
        
        if(parser.foundC()) {
            //expr.accept(new SetVariable("c", complex[1]));
            parser.setCvalue(complex[1]);
        }
        
        complex[0] = expr.getValue();
        /****/
        
        /** C= **/
        if(parser2.foundN()) {
            parser2.setNvalue(new Complex(iterations, 0));
        }
        
        if(parser2.foundZ()) {
            parser2.setZvalue(complex[0]);
        }
        
        if(parser2.foundC()) {
            parser2.setCvalue(complex[1]);
        }
        
        complex[1] = expr2.getValue();
        /****/
        
    }
    
    
     @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
      iterations = 0;
      double temp = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));
      
        Complex[] complex = new Complex[2];
        complex[0] = tempz;
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        
        if(parser.foundS()) {
            parser.setSvalue(new Complex(complex[0]));
        }
        
        if(parser2.foundS()) {
            parser2.setSvalue(new Complex(complex[0]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(new Complex());
        }
        
        if(parser2.foundP()) {
            parser2.setPvalue(new Complex());
        }

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
            
            if(parser.foundP()) {
                parser.setPvalue(new Complex(zold));
            }
            
            if(parser2.foundP()) {
                parser2.setPvalue(new Complex(zold));
            }
 
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }
     
     
    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
      iterations = 0;
      double temp = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        
        if(parser.foundS()) {
            parser.setSvalue(new Complex(complex[0]));
        }
        
        if(parser2.foundS()) {
            parser2.setSvalue(new Complex(complex[0]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(new Complex());
        }
        
        if(parser2.foundP()) {
            parser2.setPvalue(new Complex());
        }

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
            
            if(parser.foundP()) {
                parser.setPvalue(new Complex(zold));
            }
            
            if(parser2.foundP()) {
                parser2.setPvalue(new Complex(zold));
            }
 
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }
    
     @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {
      iterations = 0;
      double temp = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));
      
        Complex[] complex = new Complex[2];
        complex[0] = tempz;
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        
        if(parser.foundS()) {
            parser.setSvalue(new Complex(complex[0]));
        }
        
        if(parser2.foundS()) {
            parser2.setSvalue(new Complex(complex[0]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(new Complex());
        }
        
        if(parser2.foundP()) {
            parser2.setPvalue(new Complex());
        }

        double temp2;
        
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                double[] array = {40 * Math.log(out_color_algorithm.getResult3D(object) - 100799) - 100, out_color_algorithm.getResult(object)};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
            
            if(parser.foundP()) {
                parser.setPvalue(new Complex(zold));
            }
            
            if(parser2.foundP()) {
                parser2.setPvalue(new Complex(zold));
            }
 
        }

        Object[] object = {max_iterations, complex[0]};
        temp2 = in_color_algorithm.getResult(object);
        double result = temp2 == max_iterations ? max_iterations : max_iterations + temp2 - 100820;
        double[] array = {40 * Math.log(result + 1) - 100, temp2};
        return array;
        
    } 
     
    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
      iterations = 0;
      double temp = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        
        if(parser.foundS()) {
            parser.setSvalue(new Complex(complex[0]));
        }
        
        if(parser2.foundS()) {
            parser2.setSvalue(new Complex(complex[0]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(new Complex());
        }
        
        if(parser2.foundP()) {
            parser2.setPvalue(new Complex());
        }

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                double[] array = {40 * Math.log(out_color_algorithm.getResult3D(object) - 100799) - 100, out_color_algorithm.getResult(object)};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
            
            if(parser.foundP()) {
                parser.setPvalue(new Complex(zold));
            }
            
            if(parser2.foundP()) {
                parser2.setPvalue(new Complex(zold));
            }
 
        }

        Object[] object = {max_iterations, complex[0]};
        double temp2 = in_color_algorithm.getResult(object);
        double result = temp2 == max_iterations ? max_iterations : max_iterations + temp2 - 100820;
        double[] array = {40 * Math.log(result + 1) - 100, temp2};
        return array;
        
    }
    
    @Override
    public void calculateFractalOrbit() {
        iterations = 0;
      
        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pertur_val.getPixel(init_val.getPixel(pixel_orbit)));
        complex[1] = new Complex(pixel_orbit);//c


        Complex temp = null;
        
        Complex zold = new Complex();
        
        if(parser.foundS()) {
            parser.setSvalue(new Complex(complex[0]));
        }
        
        if(parser2.foundS()) {
            parser2.setSvalue(new Complex(complex[0]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(new Complex());
        }
        
        if(parser2.foundP()) {
            parser2.setPvalue(new Complex());
        }
        
        for (; iterations < max_iterations; iterations++) {
           zold.assign(complex[0]);
           function(complex);
           
           if(parser.foundP()) {
                parser.setPvalue(new Complex(zold));
           }
           
           if(parser2.foundP()) {
                parser2.setPvalue(new Complex(zold));
           }
           
           temp = rotation.getPixel(complex[0], true);
           
           if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
               break;
           }
           
           complex_orbit.add(temp);
        }

    }
    
    @Override
    public void calculateJuliaOrbit() {
        iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(seed);//c

        Complex temp = null;
        
        Complex zold = new Complex();
        
        if(parser.foundS()) {
            parser.setSvalue(new Complex(complex[0]));
        }
        
        if(parser2.foundS()) {
            parser2.setSvalue(new Complex(complex[0]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(new Complex());
        }
        
        if(parser2.foundP()) {
            parser2.setPvalue(new Complex());
        }
        
        for (; iterations < max_iterations; iterations++) {
           zold.assign(complex[0]);
           function(complex);
           
           if(parser.foundP()) {
                parser.setPvalue(new Complex(zold));
           }
           
           if(parser2.foundP()) {
                parser2.setPvalue(new Complex(zold));
            }
           
           temp = rotation.getPixel(complex[0], true);
           
           if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
               break;
           }
           
           complex_orbit.add(temp);
        }

    }
    
}

        
