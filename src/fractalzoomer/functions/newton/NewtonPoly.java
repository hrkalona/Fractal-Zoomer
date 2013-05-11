package fractalzoomer.functions.newton;


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
import fractalzoomer.functions.RootFindingMethods;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
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
public class NewtonPoly extends RootFindingMethods {
  private double[] coefficients;

    public NewtonPoly(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int in_coloring_algorithm, int plane_type, double[] rotation_vals, double[] coefficients) {

        super(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);

        this.coefficients = coefficients;

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                out_color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                out_color_algorithm = new SmoothRootFindingMethod(Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-9;
                out_color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-9;
                out_color_algorithm = new BinaryDecomposition2();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                convergent_bailout = 1E-6;
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                convergent_bailout = 1E-6;
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                convergent_bailout = 1E-6;
                out_color_algorithm = new ColorDecompositionConverge();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                convergent_bailout = 1E-6;
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
    public NewtonPoly(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] coefficients) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        this.coefficients = coefficients;
       
    }

    
    @Override
    protected void function(Complex[] complex) {
            
        Complex fz = complex[0].tenth().times(coefficients[0]).plus(complex[0].ninth().times(coefficients[1])).plus(complex[0].eighth().times(coefficients[2])).plus(complex[0].seventh().times(coefficients[3])).plus(complex[0].sixth().times(coefficients[4])).plus(complex[0].fifth().times(coefficients[5])).plus(complex[0].fourth().times(coefficients[6])).plus(complex[0].cube().times(coefficients[7])).plus(complex[0].square().times(coefficients[8])).plus(complex[0].times(coefficients[9])).plus(coefficients[10]);
        Complex dfz = complex[0].ninth().times(10 * coefficients[0]).plus(complex[0].eighth().times(9 * coefficients[1])).plus(complex[0].seventh().times(8 *coefficients[2])).plus(complex[0].sixth().times(7 * coefficients[3])).plus(complex[0].fifth().times(6 * coefficients[4])).plus(complex[0].fourth().times(5 * coefficients[5])).plus(complex[0].cube().times(4 * coefficients[6])).plus(complex[0].square().times(3 * coefficients[7])).plus(complex[0].times(2 * coefficients[8])).plus(coefficients[9]);

        complex[0] = complex[0].sub((fz).divide(dfz)); 

    }
  
}
