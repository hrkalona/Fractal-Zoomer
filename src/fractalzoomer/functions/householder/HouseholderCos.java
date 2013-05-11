package fractalzoomer.functions.householder;


import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.ColorDecompositionConverge;
import fractalzoomer.core.Complex;
import fractalzoomer.in_coloring_algorithms.CosMag;
import fractalzoomer.in_coloring_algorithms.DecompositionLike;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecompositionConverge;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.main.MainWindow;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.functions.RootFindingMethods;
import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.out_coloring_algorithms.SmoothRootFindingMethod;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class HouseholderCos extends RootFindingMethods {

    public HouseholderCos(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int in_coloring_algorithm, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                out_color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                out_color_algorithm = new SmoothRootFindingMethod(Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-2;
                out_color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-4;
                out_color_algorithm = new BinaryDecomposition2();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                convergent_bailout = 1E-3;
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                convergent_bailout = 1E-3;
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-1;
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                convergent_bailout = 1E-3;
                out_color_algorithm = new ColorDecompositionConverge();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                convergent_bailout = 1E-3;
                out_color_algorithm = new EscapeTimeColorDecompositionConverge();
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
    public HouseholderCos(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
 
    }
   
    @Override
    protected void function(Complex[] complex) {
        
        Complex[] temp = complex[0].der012_cos();
        
        Complex fz = temp[0];
        Complex dfz = temp[1];
        Complex ddfz = temp[2];

        complex[0] = complex[0].sub((fz.times(dfz.square().times(2).plus(fz.times(ddfz)))).divide(dfz.cube().times(2)));//householder

    }
  
}
