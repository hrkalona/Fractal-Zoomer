
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Mandelbrot extends Julia {
  private MandelVariation type;

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean burning_ship) {

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

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(2));
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

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, boolean burning_ship, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
      
        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new EscapeTime();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new Smooth(Math.log(bailout_squared), Math.log(2));
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
    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean burning_ship) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
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

    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, boolean burning_ship, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }

    }

    @Override
    protected void function(Complex[] complex) {

        complex[0] = type.getPixel(complex[0]).square().plus(complex[1]);

    }
    
    protected boolean mandelbrotOptimization(Complex pixel) {

        //if(!burning_ship) {
            double temp = pixel.getRe();
            double temp2 = pixel.getIm();

                double temp3 = temp2 * temp2;
                double temp6 = temp + 1.309;
                
                double temp4 = temp - 0.25;
                double q = temp4 * temp4 + temp3;
                
                if(q * (q + temp4) < 0.25 * temp3) { //Cardioid
                    return true;
                }
                
                double temp5 = temp + 1;

                if(temp5 * temp5 + temp3 < 0.0625) { //bulb 2
                    return true;
                }

                if(temp6 * temp6 + temp3 < 0.00345) { //bulb 4
                    return true;
                }

                double temp7 = temp + 0.125;
                double temp8 = temp2 - 0.744;
                double temp10 = temp7 * temp7;

                if(temp10 + temp8 * temp8 < 0.0088) { //bulb 3 lower
                    return true;
                }


                double temp9 = temp2 + 0.744;

                if(temp10 + temp9 * temp9 < 0.0088) { //bulb 3 upper
                    return true;
                }
         
        //}

        return false;

    }

    /*@Override
    protected double calculateFractalWithoutPeriodicity(Complex pixel) {

        int iterations = 0;

        Complex tempz = init_val.getPixel(pixel);
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = pixel;//c
       
        double zre = complex[0].getRe(), zim = complex[0].getIm(), cre = complex[1].getRe(), cim = complex[1].getIm(), zre_square, zim_square;

        zre_square = zre * zre;
        zim_square = zim * zim;
        double temp;
        double counter = 0;
        
        for (; iterations < max_iterations; iterations += 8) {

            counter += (zre_square + zim_square < bailout_squared) ? 1 : 0;
            temp = zre + zim;
            zim = temp * temp - zre_square - zim_square + cim;
            zre = zre_square - zim_square + cre;
            zre_square = zre * zre;
            zim_square = zim * zim;
            
            counter += (zre_square + zim_square < bailout_squared) ? 1 : 0;
            temp = zre + zim;
            zim = temp * temp - zre_square - zim_square + cim;
            zre = zre_square - zim_square + cre;
            zre_square = zre * zre;
            zim_square = zim * zim;
            
            counter += (zre_square + zim_square < bailout_squared) ? 1 : 0;
            temp = zre + zim;
            zim = temp * temp - zre_square - zim_square + cim;
            zre = zre_square - zim_square + cre;
            zre_square = zre * zre;
            zim_square = zim * zim;
            
            counter += (zre_square + zim_square < bailout_squared) ? 1 : 0;
            temp = zre + zim;
            zim = temp * temp - zre_square - zim_square + cim;
            zre = zre_square - zim_square + cre;
            zre_square = zre * zre;
            zim_square = zim * zim;

            counter += (zre_square + zim_square < bailout_squared) ? 1 : 0;
            temp = zre + zim;
            zim = temp * temp - zre_square - zim_square + cim;
            zre = zre_square - zim_square + cre;
            zre_square = zre * zre;
            zim_square = zim * zim;
            
            counter += (zre_square + zim_square < bailout_squared) ? 1 : 0;
            temp = zre + zim;
            zim = temp * temp - zre_square - zim_square + cim;
            zre = zre_square - zim_square + cre;
            zre_square = zre * zre;
            zim_square = zim * zim;
            
            counter += (zre_square + zim_square < bailout_squared) ? 1 : 0;
            temp = zre + zim;
            zim = temp * temp - zre_square - zim_square + cim;
            zre = zre_square - zim_square + cre;
            zre_square = zre * zre;
            zim_square = zim * zim;

            counter += (zre_square + zim_square < bailout_squared) ? 1 : 0;            
            temp = zre + zim;
            zim = temp * temp - zre_square - zim_square + cim;
            zre = zre_square - zim_square + cre;
            zre_square = zre * zre;
            zim_square = zim * zim;
            
            if(zre_square + zim_square >= bailout_squared) {
                //Object[] object = {(double)iterations, complex[0], temp, distance, zold};
                //return color_algorithm.getResult(object);
                return counter;
            }

        }

        return max_iterations;

    }*/
    
}
