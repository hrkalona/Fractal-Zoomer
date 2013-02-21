
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class HouseholderPoly extends RootFindingMethods {
  private double[] coefficients;

    public HouseholderPoly(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int plane_type, double[] rotation_vals, double[] coefficients) {

        super(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
        
        this.coefficients = coefficients;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new SmoothRootFindingMethod(Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-4;
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-4;
                color_algorithm = new BinaryDecomposition2();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                convergent_bailout = 1E-3;
                color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                convergent_bailout = 1E-3;
                color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-1;
                color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                convergent_bailout = 1E-3;
                color_algorithm = new ColorDecompositionConverge();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                convergent_bailout = 1E-3;
                color_algorithm = new EscapeTimeColorDecompositionConverge();
                break;

        }

    }

    //orbit
    public HouseholderPoly(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] coefficients) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        this.coefficients = coefficients;

    }

    
    @Override
    protected void function(Complex[] complex) {
            
        Complex fz = complex[0].tenth().times(coefficients[0]).plus(complex[0].ninth().times(coefficients[1])).plus(complex[0].eighth().times(coefficients[2])).plus(complex[0].seventh().times(coefficients[3])).plus(complex[0].sixth().times(coefficients[4])).plus(complex[0].fifth().times(coefficients[5])).plus(complex[0].fourth().times(coefficients[6])).plus(complex[0].cube().times(coefficients[7])).plus(complex[0].square().times(coefficients[8])).plus(complex[0].times(coefficients[9])).plus(coefficients[10]);
        Complex dfz = complex[0].ninth().times(10 * coefficients[0]).plus(complex[0].eighth().times(9 * coefficients[1])).plus(complex[0].seventh().times(8 *coefficients[2])).plus(complex[0].sixth().times(7 * coefficients[3])).plus(complex[0].fifth().times(6 * coefficients[4])).plus(complex[0].fourth().times(5 * coefficients[5])).plus(complex[0].cube().times(4 * coefficients[6])).plus(complex[0].square().times(3 * coefficients[7])).plus(complex[0].times(2 * coefficients[8])).plus(coefficients[9]);
        Complex ddfz = complex[0].eighth().times(90 * coefficients[0]).plus(complex[0].seventh().times(72 * coefficients[1])).plus(complex[0].sixth().times(56 * coefficients[2])).plus(complex[0].fifth().times(42 * coefficients[3])).plus(complex[0].fourth().times(30 * coefficients[4])).plus(complex[0].cube().times(20 * coefficients[5])).plus(complex[0].square().times(12 * coefficients[6])).plus(complex[0].times(6 * coefficients[7])).plus(2 * coefficients[8]);

        complex[0] = complex[0].sub((fz.times(dfz.square().times(2).plus(fz.times(ddfz)))).divide(dfz.cube().times(2)));//householder

    }
  
}
