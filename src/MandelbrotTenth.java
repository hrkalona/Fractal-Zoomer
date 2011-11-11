
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class MandelbrotTenth extends Julia {
  private boolean burning_ship;

    public MandelbrotTenth(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane, boolean burning_ship) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
        this.burning_ship = burning_ship;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(10));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new IterationsPlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new Biomorphs(bailout);
                break;
            case MainWindow.CROSS_ORBIT_TRAPS:
                color_algorithm = new CrossOrbitTraps(trap_size);
                break;
    
        }

    }

    public MandelbrotTenth(double xCenter, double yCenter, double size, int max_iterations, int bailout, int out_coloring_algorithm, boolean periodicity_checking, boolean inverse_plane, boolean burning_ship, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
        this.burning_ship = burning_ship;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(10));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new IterationsPlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new Biomorphs(bailout);
                break;
            case MainWindow.CROSS_ORBIT_TRAPS:
                color_algorithm = new CrossOrbitTraps(trap_size);
                break;

        }

    }

    //orbit
    public MandelbrotTenth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane, boolean burning_ship) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);
        this.burning_ship = burning_ship;

    }

    public MandelbrotTenth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane, boolean burning_ship, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane, xJuliaCenter, yJuliaCenter);
        this.burning_ship = burning_ship;

    }

    @Override
    protected Complex function(Complex[] complex) {

        return burning_ship ? new Complex(complex[0].absRe(), complex[0].absIm()).tenth().plus(complex[1]) : complex[0].tenth().plus(complex[1]);

    }

}
