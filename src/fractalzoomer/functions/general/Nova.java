/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.functions.general;

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
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.out_coloring_algorithms.ColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2Nova;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecompositionNova;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphsNova;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeColorDecompositionRootFindingMethod;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeRootFindingMethod;
import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class Nova extends Julia {
  protected Complex z_exponent;
  protected Complex relaxation;
  protected double convergent_bailout;
  protected int nova_method;
    

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, double[] z_exponent, double[] relaxation, int nova_method) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, false, plane_type, rotation_vals);

        convergent_bailout = 1E-12;
        
        this.nova_method = nova_method;
        
        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);
        
        this.relaxation = new Complex(relaxation[0], relaxation[1]);
        
        
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
                    out_color_algorithm = new SmoothBinaryDecompositionNova(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2Nova(Math.log(convergent_bailout));
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
                    out_color_algorithm = new SmoothBiomorphsNova(Math.log(convergent_bailout), 2);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
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
      

        }
        
        switch (in_coloring_algorithm) {
            
            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(smoothing);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike(smoothing);       
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm(smoothing);       
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag(smoothing);       
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared(smoothing);       
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared(smoothing);       
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm(smoothing);       
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares(smoothing);       
                break;
                
        }

    }

    

     public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int plane_type, double[] rotation_vals, double[] z_exponent, double[] relaxation, int nova_method, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, false, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-12;
        
        this.nova_method = nova_method;
        
        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);
        
        this.relaxation = new Complex(relaxation[0], relaxation[1]);
        
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
                if(nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_HOUSEHOLDER) {
                   convergent_bailout = 1E-4;
                }
                else if(nova_method == MainWindow.NOVA_NEWTON || nova_method == MainWindow.NOVA_STEFFENSEN) {
                   convergent_bailout = 1E-9; 
                }
                else if(nova_method == MainWindow.NOVA_SCHRODER) {
                   convergent_bailout = 1E-6; 
                }
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecompositionNova(Math.log(convergent_bailout));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_HOUSEHOLDER) {
                   convergent_bailout = 1E-4;
                }
                else if(nova_method == MainWindow.NOVA_NEWTON || nova_method == MainWindow.NOVA_STEFFENSEN) {
                   convergent_bailout = 1E-9; 
                }
                else if(nova_method == MainWindow.NOVA_SCHRODER) {
                   convergent_bailout = 1E-6; 
                }
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2Nova(Math.log(convergent_bailout));
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
                    out_color_algorithm = new SmoothBiomorphsNova(Math.log(convergent_bailout), 2);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecompositionRootFindingMethod();
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
                
 
        }
        
        switch (in_coloring_algorithm) {
            
            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(smoothing);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike(smoothing);       
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm(smoothing);       
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag(smoothing);       
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared(smoothing);       
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared(smoothing);       
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm(smoothing);       
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares(smoothing);       
                break;
                
        }

    }

    //orbit
    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, double[] z_exponent, double[] relaxation, int nova_method) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        this.nova_method = nova_method;
        
        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);
        
        this.relaxation = new Complex(relaxation[0], relaxation[1]);
        
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

    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] z_exponent, double[] relaxation, int nova_method, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
        
        this.nova_method = nova_method;
        
        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);
        
        this.relaxation = new Complex(relaxation[0], relaxation[1]);
        
    }

    
    
    @Override
    protected void function(Complex[] complex) {
        
        Complex fz = null;
        Complex dfz = null;
        Complex ddfz = null;
        Complex ffz = null;
        
        if(z_exponent.getIm() == 0) {
            if(z_exponent.getRe() == 2) {
                fz = complex[0].square().sub(1);
            }
            else if(z_exponent.getRe() == 3) {
                fz = complex[0].cube().sub(1);
            }
            else if(z_exponent.getRe() == 4) {
                fz = complex[0].fourth().sub(1);
            }
            else if(z_exponent.getRe() == 5) {
                fz = complex[0].fifth().sub(1);
            }
            else if(z_exponent.getRe() == 6) {
                fz = complex[0].sixth().sub(1);
            }
            else if(z_exponent.getRe() == 7) {
                fz = complex[0].seventh().sub(1); 
            }
            else if(z_exponent.getRe() == 8) {
                fz = complex[0].eighth().sub(1);
            }
            else if(z_exponent.getRe() == 9) {
                fz = complex[0].ninth().sub(1); 
            }
            else if(z_exponent.getRe() == 10) {
                fz = complex[0].tenth().sub(1);
            }
            else {
                fz = complex[0].pow(z_exponent.getRe()).sub(1);
            }
        }
        else {
            fz = complex[0].pow(z_exponent).sub(1);
        }
        
        if(nova_method != MainWindow.NOVA_SECANT) {
            if(z_exponent.getIm() == 0) {
                if(z_exponent.getRe() == 2) {
                    dfz = complex[0].times(2); 
                }
                else if(z_exponent.getRe() == 3) {
                    dfz = complex[0].square().times(3);
                }
                else if(z_exponent.getRe() == 4) {
                    dfz = complex[0].cube().times(4);
                }
                else if(z_exponent.getRe() == 5) {
                    dfz = complex[0].fourth().times(5);
                }
                else if(z_exponent.getRe() == 6) {
                    dfz = complex[0].fifth().times(6);
                }
                else if(z_exponent.getRe() == 7) {
                    dfz = complex[0].sixth().times(7); 
                }
                else if(z_exponent.getRe() == 8) {
                    dfz = complex[0].seventh().times(8);
                }
                else if(z_exponent.getRe() == 9) {
                    dfz = complex[0].eighth().times(9); 
                }
                else if(z_exponent.getRe() == 10) {
                    dfz = complex[0].ninth().times(10);
                }
                else {
                    dfz = complex[0].pow(z_exponent.getRe() - 1).times(z_exponent.getRe());
                }
            }
            else {
                dfz = complex[0].pow(z_exponent.sub(1)).times(z_exponent);
            }
        }
        
        if(nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_SCHRODER || nova_method == MainWindow.NOVA_HOUSEHOLDER) {
            if(z_exponent.getIm() == 0) {
                if(z_exponent.getRe() == 2) {
                    ddfz = new Complex(2, 0);
                }
                else if(z_exponent.getRe() == 3) {
                    ddfz = complex[0].times(6);
                }
                else if(z_exponent.getRe() == 4) {
                    ddfz = complex[0].square().times(12);
                }
                else if(z_exponent.getRe() == 5) {
                    ddfz = complex[0].cube().times(20);
                }
                else if(z_exponent.getRe() == 6) {
                    ddfz = complex[0].fourth().times(30);
                }
                else if(z_exponent.getRe() == 7) { 
                    ddfz = complex[0].fifth().times(42);
                }
                else if(z_exponent.getRe() == 8) {
                    ddfz = complex[0].sixth().times(56);
                }
                else if(z_exponent.getRe() == 9) {
                    ddfz = complex[0].seventh().times(72);
                }
                else if(z_exponent.getRe() == 10) {
                    ddfz = complex[0].eighth().times(90);
                }
                else {
                    ddfz = complex[0].pow(z_exponent.getRe() - 2).times(z_exponent.getRe() * (z_exponent.getRe() - 1));
                }
            }
            else {
                ddfz = complex[0].pow(z_exponent.sub(2)).times(z_exponent.times(z_exponent.sub(1)));
            }
        }
        
        
        if(nova_method == MainWindow.NOVA_STEFFENSEN) {
            
             Complex temp = complex[0].plus(fz);
             
             if(z_exponent.getIm() == 0) {
                if(z_exponent.getRe() == 2) {
                    ffz = temp.square().sub(1);
                }
                else if(z_exponent.getRe() == 3) {
                    ffz = temp.cube().sub(1);
                }
                else if(z_exponent.getRe() == 4) {
                    ffz = temp.fourth().sub(1);
                }
                else if(z_exponent.getRe() == 5) {
                    ffz = temp.fifth().sub(1);
                }
                else if(z_exponent.getRe() == 6) {
                    ffz = temp.sixth().sub(1);
                }
                else if(z_exponent.getRe() == 7) {
                    ffz = temp.seventh().sub(1); 
                }
                else if(z_exponent.getRe() == 8) {
                    ffz = temp.eighth().sub(1);
                }
                else if(z_exponent.getRe() == 9) {
                    ffz = temp.ninth().sub(1); 
                }
                else if(z_exponent.getRe() == 10) {
                    ffz = temp.tenth().sub(1);
                }
                else {
                    ffz = temp.pow(z_exponent.getRe()).sub(1);
                }
            }
            else {
                ffz = temp.pow(z_exponent).sub(1);
            }
        }
        

        switch (nova_method) {
            
            case MainWindow.NOVA_NEWTON:
                complex[0] = complex[0].sub((fz.divide(dfz)).times(relaxation)).plus(complex[1]); //newton
                break;
            case MainWindow.NOVA_HALLEY:
                complex[0] = complex[0].sub(((fz.times(dfz).times(2)).divide((dfz.square().times(2)).sub(fz.times(ddfz)))).times(relaxation)).plus(complex[1]); //halley
                break;
            case MainWindow.NOVA_SCHRODER:
                complex[0] = complex[0].sub(((fz.times(dfz)).divide((dfz.square()).sub(fz.times(ddfz)))).times(relaxation)).plus(complex[1]); //schroder
                break;
            case MainWindow.NOVA_HOUSEHOLDER:
                complex[0] = complex[0].sub((fz.times(dfz.square().times(2).plus(fz.times(ddfz)))).divide(dfz.cube().times(2)).times(relaxation)).plus(complex[1]); //householder
                break;
            case MainWindow.NOVA_SECANT:
                Complex temp = complex[0];
                complex[0] = complex[0].sub((fz.times((complex[0].sub(complex[2])).divide(fz.sub(complex[3])))).times(relaxation)).plus(complex[1]); //secant
                complex[2] = temp;
                complex[3] = fz;
                break;
            case MainWindow.NOVA_STEFFENSEN:
                complex[0] = complex[0].sub(((fz.square()).divide(ffz.sub(fz))).times(relaxation)).plus(complex[1]); //steffensen
                break;
                
        }

    }
    
    
     @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex tempz = pertur_val.getPixel(init_val.getPixel(pixel));
      
        Complex[] complex = new Complex[4];
        complex[0] = tempz;
        complex[1] = pixel;//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2 = zold;
            zold = complex[0];
            function(complex);
 
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }
     
     
    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex[] complex = new Complex[4];
        complex[0] = pixel;
        complex[1] = seed;//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);

        Complex zold = null;
        Complex zold2 = null;

        for (; iterations < max_iterations; iterations++) {
            if((iterations > 1 && ((temp = complex[0].distance_squared(zold)) <= convergent_bailout))) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2 = zold;
            zold = complex[0];
            function(complex);
 
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }
    
    @Override
    public void calculateFractalOrbit() {
      int iterations = 0;
      
        Complex[] complex = new Complex[4];
        complex[0] = pertur_val.getPixel(init_val.getPixel(pixel_orbit));
        complex[1] = pixel_orbit;//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);

        Complex temp = null;
        
        for (; iterations < max_iterations; iterations++) {
           function(complex);
           temp = rotation.getPixel(complex[0], true);
           
           if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
               break;
           }
           
           complex_orbit.add(temp);
        }

    }
    
    @Override
    public void calculateJuliaOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[4];
        complex[0] = pixel_orbit;//z
        complex[1] = seed;//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);

        Complex temp = null;
        
        for (; iterations < max_iterations; iterations++) {
           function(complex);
           temp = rotation.getPixel(complex[0], true);
           
           if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
               break;
           }
           
           complex_orbit.add(temp);
        }

    }
    
}

        