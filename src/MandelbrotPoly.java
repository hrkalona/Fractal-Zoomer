
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class MandelbrotPoly extends Julia {
  private MandelVariation type;
  private double[] coefficients;

    public MandelbrotPoly(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean burning_ship, double[] coefficients) {

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
        
        this.coefficients = coefficients;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared));
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

    public MandelbrotPoly(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean burning_ship, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        this.coefficients = coefficients;
      
        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared));
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
    public MandelbrotPoly(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean burning_ship, double[] coefficients) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        this.coefficients = coefficients;
        
        if(perturbation) {
            init_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            init_val = new InitialValue(perturbation_vals[0], perturbation_vals[1]);
        }

    }

    public MandelbrotPoly(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean burning_ship, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        this.coefficients = coefficients;

    }

    @Override
    protected void function(Complex[] complex) {

        Complex pixel = type.getPixel(complex[0]);
        complex[0] = pixel.tenth().times(coefficients[0]).plus(pixel.ninth().times(coefficients[1])).plus(pixel.eighth().times(coefficients[2])).plus(pixel.seventh().times(coefficients[3])).plus(pixel.sixth().times(coefficients[4])).plus(pixel.fifth().times(coefficients[5])).plus(pixel.fourth().times(coefficients[6])).plus(pixel.cube().times(coefficients[7])).plus(pixel.square().times(coefficients[8])).plus(pixel.times(coefficients[9])).plus(coefficients[10]).plus(complex[1]);
 
    } 
    
}
