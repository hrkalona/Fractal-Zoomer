
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class MandelbrotNth extends Julia {
  private double z_exponent;
  private MandelVariation type;

    public MandelbrotNth(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean burning_ship, double z_exponent) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        if(perturbation) {
            init_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            init_val = new InitialValue(perturbation_vals[0], perturbation_vals[1]);
        }
        
        this.z_exponent = z_exponent;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(z_exponent));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                color_algorithm = new BinaryDecomposition2();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new Biomorphs(bailout);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                color_algorithm = new EscapeTimeColorDecomposition();
                break;
                
        }

    }

    public MandelbrotNth(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean burning_ship, double z_exponent, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        this.z_exponent = z_exponent;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(z_exponent));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                color_algorithm = new BinaryDecomposition2();
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                color_algorithm = new Biomorphs(bailout);
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                color_algorithm = new EscapeTimeColorDecomposition();
                break;

        }

    }

    //orbit
    public MandelbrotNth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean burning_ship, double z_exponent) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        this.z_exponent = z_exponent;
        
        if(perturbation) {
            init_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            init_val = new InitialValue(perturbation_vals[0], perturbation_vals[1]);
        }

    }

    public MandelbrotNth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean burning_ship, double z_exponent, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        this.z_exponent = z_exponent;

    }

    @Override
    protected void function(Complex[] complex) {

        complex[0] = type.getPixel(complex[0]).pow(z_exponent).plus(complex[1]);

    }
    
}
