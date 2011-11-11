
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Newton4 extends Fractal {
  private double convergent_bailout;

    public Newton4(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, 0, out_coloring_algorithm, false, inverse_plane);

        convergent_bailout = 1E-9;

        switch (out_coloring_algorithm) {

            case MainWindow.NORMAL_COLOR:
                color_algorithm = new Iterations();
                break;
            case MainWindow.SMOOTH_COLOR:
                color_algorithm = new SmoothNewton(Math.log(convergent_bailout));
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                color_algorithm = new BinaryDecomposition();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                color_algorithm = new IterationsPlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.CROSS_ORBIT_TRAPS:
                color_algorithm = new CrossOrbitTraps(trap_size);
                break;

        }

    }

    //orbit
    public Newton4(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, boolean inverse_plane) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, inverse_plane);

    }

    
    @Override
    protected Complex function(Complex[] complex) {

        Complex fz = complex[0].fourth().subNormal(1);
        Complex dfz = complex[0].cube().timesNormal(4);

        return complex[0].sub((fz).divide(dfz)); 
       
    }

    @Override
    protected double calculateFractalWithoutPeriodicity(Complex pixel) {
      int iterations = 0;
      double temp = 0;

        Complex[] complex = new Complex[1];
        complex[0] = pixel;//z

        Complex zold = new Complex(0, 0);
        Complex zold2 = new Complex(0, 0);
        
        if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
            distance = 1e20;
            trapped = false;      
        }
     
        for (; iterations < max_iterations; iterations++) {
            if((zold != null && ((temp = (complex[0].sub(zold)).magnitude()) < convergent_bailout) || trapped)) {
                Object[] object = {(double)iterations, complex[0], temp, distance, zold, zold2};
                return color_algorithm.getResult(object);
            }
            zold2 = zold;
            zold = complex[0];
            complex[0] = function(complex);
            
            if(out_coloring_algorithm == MainWindow.CROSS_ORBIT_TRAPS) {
                if(complex[0].absRe() < trap_size) {
                    distance = complex[0].absRe();
                    trapped = true;
                }
                else {
                    if(complex[0].absIm() < trap_size) {
                        distance = complex[0].absIm();
                        trapped = true;
                    }    
                } 
            }
            
        }

        return iterations;
    }
   
    @Override
    public double calculateJulia(Complex pixel) {
        return 0;
    }

    @Override
    public void calculateJuliaOrbit() {}

}
