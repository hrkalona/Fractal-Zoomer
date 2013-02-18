
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public abstract class Julia extends Fractal {
  protected Complex seed;

    public Julia(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals);

    }
    
    public Julia(double xCenter, double yCenter, double size, int max_iterations, double bailout, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals);
        
        rotation = new Rotation(rotation_vals[0], rotation_vals[1]);
        
        switch (plane_type) {
            case MainWindow.MU_PLANE:
                plane = new MuPlane();
                break;
            case MainWindow.INVERSED_MU_PLANE:
                plane = new InversedMuPlane();
                break;
            case MainWindow.INVERSED_MU2_PLANE:
                plane = new InversedMu2Plane();
                break;
            case MainWindow.INVERSED_MU3_PLANE:
                plane = new InversedMu3Plane();
                break;
            case MainWindow.INVERSED_MU4_PLANE:
                plane = new InversedMu4Plane();
                break;
            case MainWindow.LAMBDA_PLANE:
                plane = new LambdaPlane();
                break;
            case MainWindow.INVERSED_LAMBDA_PLANE:
                plane = new InversedLambdaPlane();
                break;
        }
        
        seed = plane.getPixel(new Complex(xJuliaCenter, yJuliaCenter));

    }

    //orbit
    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);

    }

    public Julia(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals);
        
        rotation = new Rotation(rotation_vals[0], rotation_vals[1]);
        
        switch (plane_type) {
            case MainWindow.MU_PLANE:
                plane = new MuPlane();
                break;
            case MainWindow.INVERSED_MU_PLANE:
                plane = new InversedMuPlane();
                break;
            case MainWindow.INVERSED_MU2_PLANE:
                plane = new InversedMu2Plane();
                break;
            case MainWindow.INVERSED_MU3_PLANE:
                plane = new InversedMu3Plane();
                break;
            case MainWindow.INVERSED_MU4_PLANE:
                plane = new InversedMu4Plane();
                break;
            case MainWindow.LAMBDA_PLANE:
                plane = new LambdaPlane();
                break;
            case MainWindow.INVERSED_LAMBDA_PLANE:
                plane = new InversedLambdaPlane();
                break;
        }
        
        seed = plane.getPixel(new Complex(xJuliaCenter, yJuliaCenter));
        
        pixel_orbit = this.complex_orbit.get(0);
        
        pixel_orbit = rotation.getPixel(pixel_orbit, false);

    }

    @Override
    public double calculateJulia(Complex pixel) {

        return periodicity_checking ? calculateJuliaWithPeriodicity(rotation.getPixel(pixel, false)) : calculateJuliaWithoutPeriodicity(rotation.getPixel(pixel, false));

    }

    protected double calculateJuliaWithPeriodicity(Complex pixel) {
      int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex(0, 0);


        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex(0, 0);

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].norm_squared()) >= bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, zold};
                return color_algorithm.getResult(object);
            }
            zold = complex[0];
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }
        }
        
        return max_iterations;
    }

    protected double calculateJuliaWithoutPeriodicity(Complex pixel) {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel;//z
        complex[1] = seed;//c

        Complex zold = new Complex(0, 0);

        double temp;
        for (; iterations < max_iterations; iterations++) {
            if((temp = complex[0].norm_squared()) >= bailout_squared) {
                Object[] object = {(double)iterations, complex[0], temp, zold};
                return color_algorithm.getResult(object);
            }
            zold = complex[0];
            function(complex);
  
        }

        return max_iterations;
    }

    @Override
    public void calculateJuliaOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = pixel_orbit;//z
        complex[1] = seed;//c

        for (; iterations < max_iterations; iterations++) {
           function(complex);
           complex_orbit.add(rotation.getPixel(complex[0], true));
           //if(z.getRe() >= (xCenter + size) / 2 || z.getRe() <= (xCenter - size) / 2 || z.getIm() >= (yCenter + size) / 2 || z.getIm() <= (yCenter - size) / 2) {
               //return; //keep only the visible ones
           //}
        }

    }

}
