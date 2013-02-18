
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Newton4 extends RootFindingMethods {

    public Newton4(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
        
        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new SmoothNewton(Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-9;
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-9;
                color_algorithm = new BinaryDecomposition2();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                convergent_bailout = 1E-6;
                color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                convergent_bailout = 1E-6;
                color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-1;
                color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                convergent_bailout = 1E-6;
                color_algorithm = new ColorDecompositionConverge();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                convergent_bailout = 1E-6;
                color_algorithm = new EscapeTimeColorDecompositionConverge();
                break;

        }

    }

    //orbit
    public Newton4(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);

    }

    
    @Override
    protected void function(Complex[] complex) {
       
        Complex fz = complex[0].fourth().sub(1);
        Complex dfz = complex[0].cube().times(4);

        complex[0] = complex[0].sub((fz).divide(dfz)); 
       
    }

}
