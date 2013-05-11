package fractalzoomer.functions;


import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.InitialValue;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public abstract class RootFindingMethods extends Fractal { 
  protected double convergent_bailout;

    public RootFindingMethods(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, 0, 0, false, plane_type, rotation_vals);

        convergent_bailout = 1E-10;

    }

    //orbit
    public RootFindingMethods(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        init_val = new InitialValue(complex_orbit.get(0).getRe(), complex_orbit.get(0).getIm());

    }

    
    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex[] complex = new Complex[1];
        complex[0] = pixel;//z

        Complex zold = null;
        Complex zold2 = null;

        for (; iterations < max_iterations; iterations++) {
            if((iterations > 1 && ((temp = (complex[0].sub(zold)).norm_squared()) <= convergent_bailout))) {
                Object[] object = {iterations, complex[0], temp, zold, zold2};
                return out_color_algorithm.getResult(object);
            }
            zold2 = zold;
            zold = complex[0];
            function(complex);
 
        }

        Object[] object = {max_iterations, complex[0]};
        return in_color_algorithm.getResult(object);
        
    }
    
 
    @Override
    public double calculateJulia(Complex pixel) {
        return 0;
    }

    @Override
    public void calculateJuliaOrbit() {}
    
}
