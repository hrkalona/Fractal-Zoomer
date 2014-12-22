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
    

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double [] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        convergent_bailout = 1E-11;
        
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
                    out_color_algorithm = new SmoothEscapeTimeRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2RootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
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
                    out_color_algorithm = new SmoothBiomorphsNova(Math.log(convergent_bailout), converging_smooth_algorithm);
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
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadiusNova();   
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGridNova();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGridNova(Math.log(convergent_bailout), converging_smooth_algorithm);
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

    

     public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double [] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-11;
        
        this.nova_method = nova_method;
        
        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);
        
        this.relaxation = new Complex(relaxation[0], relaxation[1]);
        
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTime();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
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
                    out_color_algorithm = new SmoothBinaryDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
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
                    out_color_algorithm = new SmoothBinaryDecomposition2RootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
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
                    out_color_algorithm = new SmoothBiomorphsNova(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new ColorDecompositionRootFindingMethod();
                }
                else {
                    out_color_algorithm = new SmoothColorDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
                }
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeColorDecompositionRootFindingMethod();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeColorDecompositionRootFindingMethod(Math.log(convergent_bailout), converging_smooth_algorithm);
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
                    out_color_algorithm = new SmoothEscapeTimeGridNova(Math.log(convergent_bailout), converging_smooth_algorithm);
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
    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double [] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
        
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

    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double [] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
        
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
                fz = complex[0].square().sub_mutable(1);
            }
            else if(z_exponent.getRe() == 3) {
                fz = complex[0].cube().sub_mutable(1);
            }
            else if(z_exponent.getRe() == 4) {
                fz = complex[0].fourth().sub_mutable(1);
            }
            else if(z_exponent.getRe() == 5) {
                fz = complex[0].fifth().sub_mutable(1);
            }
            else if(z_exponent.getRe() == 6) {
                fz = complex[0].sixth().sub_mutable(1);
            }
            else if(z_exponent.getRe() == 7) {
                fz = complex[0].seventh().sub_mutable(1); 
            }
            else if(z_exponent.getRe() == 8) {
                fz = complex[0].eighth().sub_mutable(1);
            }
            else if(z_exponent.getRe() == 9) {
                fz = complex[0].ninth().sub_mutable(1); 
            }
            else if(z_exponent.getRe() == 10) {
                fz = complex[0].tenth().sub_mutable(1);
            }
            else {
                fz = complex[0].pow(z_exponent.getRe()).sub_mutable(1);
            }
        }
        else {
            fz = complex[0].pow(z_exponent).sub_mutable(1);
        }
        
        if(nova_method != MainWindow.NOVA_SECANT) {
            if(z_exponent.getIm() == 0) {
                if(z_exponent.getRe() == 2) {
                    dfz = complex[0].times(2); 
                }
                else if(z_exponent.getRe() == 3) {
                    dfz = complex[0].square().times_mutable(3);
                }
                else if(z_exponent.getRe() == 4) {
                    dfz = complex[0].cube().times_mutable(4);
                }
                else if(z_exponent.getRe() == 5) {
                    dfz = complex[0].fourth().times_mutable(5);
                }
                else if(z_exponent.getRe() == 6) {
                    dfz = complex[0].fifth().times_mutable(6);
                }
                else if(z_exponent.getRe() == 7) {
                    dfz = complex[0].sixth().times_mutable(7); 
                }
                else if(z_exponent.getRe() == 8) {
                    dfz = complex[0].seventh().times_mutable(8);
                }
                else if(z_exponent.getRe() == 9) {
                    dfz = complex[0].eighth().times_mutable(9); 
                }
                else if(z_exponent.getRe() == 10) {
                    dfz = complex[0].ninth().times_mutable(10);
                }
                else {
                    dfz = complex[0].pow(z_exponent.getRe() - 1).times_mutable(z_exponent.getRe());
                }
            }
            else {
                dfz = complex[0].pow(z_exponent.sub(1)).times_mutable(z_exponent);
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
                    ddfz = complex[0].square().times_mutable(12);
                }
                else if(z_exponent.getRe() == 5) {
                    ddfz = complex[0].cube().times_mutable(20);
                }
                else if(z_exponent.getRe() == 6) {
                    ddfz = complex[0].fourth().times_mutable(30);
                }
                else if(z_exponent.getRe() == 7) { 
                    ddfz = complex[0].fifth().times_mutable(42);
                }
                else if(z_exponent.getRe() == 8) {
                    ddfz = complex[0].sixth().times_mutable(56);
                }
                else if(z_exponent.getRe() == 9) {
                    ddfz = complex[0].seventh().times_mutable(72);
                }
                else if(z_exponent.getRe() == 10) {
                    ddfz = complex[0].eighth().times_mutable(90);
                }
                else {
                    ddfz = complex[0].pow(z_exponent.getRe() - 2).times_mutable(z_exponent.getRe() * (z_exponent.getRe() - 1));
                }
            }
            else {
                ddfz = complex[0].pow(z_exponent.sub(2)).times_mutable(z_exponent.times(z_exponent.sub(1)));
            }
        }
        
        
        if(nova_method == MainWindow.NOVA_STEFFENSEN) {
            
             Complex temp = complex[0].plus(fz);
             
             if(z_exponent.getIm() == 0) {
                if(z_exponent.getRe() == 2) {
                    ffz = temp.square_mutable().sub_mutable(1);
                }
                else if(z_exponent.getRe() == 3) {
                    ffz = temp.cube_mutable().sub_mutable(1);
                }
                else if(z_exponent.getRe() == 4) {
                    ffz = temp.fourth_mutable().sub_mutable(1);
                }
                else if(z_exponent.getRe() == 5) {
                    ffz = temp.fifth_mutable().sub_mutable(1);
                }
                else if(z_exponent.getRe() == 6) {
                    ffz = temp.sixth_mutable().sub_mutable(1);
                }
                else if(z_exponent.getRe() == 7) {
                    ffz = temp.seventh_mutable().sub_mutable(1); 
                }
                else if(z_exponent.getRe() == 8) {
                    ffz = temp.eighth_mutable().sub_mutable(1);
                }
                else if(z_exponent.getRe() == 9) {
                    ffz = temp.ninth_mutable().sub_mutable(1); 
                }
                else if(z_exponent.getRe() == 10) {
                    ffz = temp.tenth_mutable().sub_mutable(1);
                }
                else {
                    ffz = temp.pow_mutable(z_exponent.getRe()).sub_mutable(1);
                }
            }
            else {
                ffz = temp.pow(z_exponent).sub_mutable(1);
            }
        }
        

        switch (nova_method) {
            
            case MainWindow.NOVA_NEWTON:
                complex[0].sub_mutable(((fz).divide_mutable(dfz)).times_mutable(relaxation)).plus_mutable(complex[1]); //newton
                break;
            case MainWindow.NOVA_HALLEY:
                complex[0].sub_mutable(((fz.times(dfz).times_mutable(2)).divide_mutable((dfz.square_mutable().times_mutable(2)).sub_mutable(fz.times_mutable(ddfz)))).times_mutable(relaxation)).plus_mutable(complex[1]); //halley
                break;
            case MainWindow.NOVA_SCHRODER:
                complex[0].sub_mutable(((fz.times(dfz)).divide_mutable((dfz.square_mutable()).sub_mutable(fz.times_mutable(ddfz)))).times_mutable(relaxation)).plus_mutable(complex[1]);//schroeder
                break;
            case MainWindow.NOVA_HOUSEHOLDER:
                complex[0].sub_mutable(((fz.times_mutable(dfz.square().times_mutable(2).plus_mutable(fz.times(ddfz)))).divide_mutable(dfz.cube_mutable().times_mutable(2))).times_mutable(relaxation)).plus_mutable(complex[1]);//householder
                break;
            case MainWindow.NOVA_SECANT:
                Complex temp = new Complex(complex[0]);
                complex[0].sub_mutable((fz.times((complex[0].sub(complex[2])).divide_mutable(fz.sub(complex[3])))).times_mutable(relaxation)).plus_mutable(complex[1]); //secant
                complex[2].assign(temp);
                complex[3].assign(fz);
                break;
            case MainWindow.NOVA_STEFFENSEN:
                complex[0].sub_mutable(((fz.square()).divide_mutable(ffz.sub_mutable(fz))).times_mutable(relaxation)).plus_mutable(complex[1]); //steffensen
                break;
                
        }

    }
    
    
     @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));
      
        Complex[] complex = new Complex[4];
        complex[0] = tempz;
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
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
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
 
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }
    
     @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));
      
        Complex[] complex = new Complex[4];
        complex[0] = tempz;
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        double temp2;
        
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                temp2 = out_color_algorithm.getResult(object);
                double[] array = {40 * Math.log(temp2 - 100799) - 100, temp2};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
 
        }

        Object[] object = {max_iterations, complex[0]};
        temp2 = in_color_algorithm.getResult(object);
        double result = temp2 == max_iterations ? max_iterations : max_iterations + temp2 - 100820;
        double[] array = {40 * Math.log(result + 1) - 100, temp2};
        return array;
        
    } 
     
    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex[] complex = new Complex[4];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);

        Complex zold = new Complex();
        Complex zold2 = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                double[] array = {40 * Math.log(out_color_algorithm.getResult3D(object) - 100799) - 100, out_color_algorithm.getResult(object)};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);
 
        }

        Object[] object = {max_iterations, complex[0]};
        double temp2 = in_color_algorithm.getResult(object);
        double result = temp2 == max_iterations ? max_iterations : max_iterations + temp2 - 100820;
        double[] array = {40 * Math.log(result + 1) - 100, temp2};
        return array;
        
    }
    
    @Override
    public void calculateFractalOrbit() {
      int iterations = 0;
      
        Complex[] complex = new Complex[4];
        complex[0] = new Complex(pertur_val.getPixel(init_val.getPixel(pixel_orbit)));
        complex[1] = new Complex(pixel_orbit);//c
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
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(seed);//c
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

        