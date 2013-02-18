
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class Schroder3 extends RootFindingMethods {

    public Schroder3(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new SmoothSchroder(Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-6;
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-6;
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
    public Schroder3(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
 
    }
   
    @Override
    protected void function(Complex[] complex) {
        
        Complex fz = complex[0].cube().sub(1);
        Complex dfz = complex[0].square().times(3);
        Complex ddfz = complex[0].times(6);

        complex[0] = complex[0].sub((fz.times(dfz)).divide((dfz.square()).sub(fz.times(ddfz))));//schroeder

    }
   
}
