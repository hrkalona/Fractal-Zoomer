package fractalzoomer.functions.mandelbrot;


import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.Biomorphs;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.DefaultInitialValue;
import fractalzoomer.in_coloring_algorithms.CosMag;
import fractalzoomer.in_coloring_algorithms.DecompositionLike;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.fractal_options.DefaultPerturbation;
import fractalzoomer.fractal_options.InitialValue;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.main.MainWindow;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.Perturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.Squares2;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.out_coloring_algorithms.DistanceEstimator;
import fractalzoomer.out_coloring_algorithms.DistanceEstimator2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadius;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGrid;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphs;
import fractalzoomer.out_coloring_algorithms.SmoothDistanceEstimator2;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTime;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGrid;
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
  private MandelVariation type2;
  int dem;

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);   
        }
        else {
            type2 = new NormalMandel();
        }
        
        if(perturbation) {
            pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            pertur_val = new DefaultPerturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        
        if(init_value) {
            init_val = new InitialValue(initial_vals[0], initial_vals[1]);
        }
        else {
            init_val = new DefaultInitialValue(initial_vals[0], initial_vals[1]);
        }
        
        dem = 0;

        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTime();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTime(Math.log(bailout_squared));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition(Math.log(bailout_squared));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2(Math.log(bailout_squared));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if(!smoothing) {
                    out_color_algorithm = new Biomorphs(bailout);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphs(Math.log(bailout_squared), bailout);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                out_color_algorithm = new EscapeTimeGaussianInteger3();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                out_color_algorithm = new EscapeTimeGaussianInteger4();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(2);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2();
                break;
            case MainWindow.DISTANCE_ESTIMATOR:
                out_color_algorithm = new DistanceEstimator();
                dem = 1;
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadius(Math.log(bailout_squared));
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGrid(Math.log(bailout_squared));
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGrid(Math.log(bailout_squared));
                }
                break;
            case MainWindow.DISTANCE_ESTIMATOR2:
                if(!smoothing) {
                    out_color_algorithm = new DistanceEstimator2(size, max_iterations, bailout);
                }
                else {
                    out_color_algorithm = new SmoothDistanceEstimator2(size, max_iterations, bailout, Math.log(bailout_squared));
                }
                dem = 2;
                break;
                         
        }


        switch (in_coloring_algorithm) {
            
            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(smoothing);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike(smoothing);       
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm(smoothing);       
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag(smoothing);       
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared(smoothing);       
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared(smoothing);       
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm(smoothing);       
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares(smoothing);       
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2();       
                break;
                
        }
        
       
    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, xJuliaCenter, yJuliaCenter);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);   
        }
        else {
            type2 = new NormalMandel();
        }
        
        dem = 0;
      
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTime();
                }
                else {
                    out_color_algorithm = new SmoothEscapeTime(Math.log(bailout_squared));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition(Math.log(bailout_squared));
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if(!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                }
                else {
                    out_color_algorithm = new SmoothBinaryDecomposition2(Math.log(bailout_squared));
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if(!smoothing) {
                    out_color_algorithm = new Biomorphs(bailout);
                }
                else {
                    out_color_algorithm = new SmoothBiomorphs(Math.log(bailout_squared), bailout);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow. ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                out_color_algorithm = new EscapeTimeGaussianInteger3();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                out_color_algorithm = new EscapeTimeGaussianInteger4();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(2);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2();
                break;
            case MainWindow.DISTANCE_ESTIMATOR:
                out_color_algorithm = new DistanceEstimator();
                dem = 1;
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadius(Math.log(bailout_squared));
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if(!smoothing) {
                    out_color_algorithm = new EscapeTimeGrid(Math.log(bailout_squared));
                }
                else {
                    out_color_algorithm = new SmoothEscapeTimeGrid(Math.log(bailout_squared));
                }
                break;
            case MainWindow.DISTANCE_ESTIMATOR2:
                if(!smoothing) {
                    out_color_algorithm = new DistanceEstimator2(size, max_iterations, bailout);
                }
                else {
                    out_color_algorithm = new SmoothDistanceEstimator2(size, max_iterations, bailout, Math.log(bailout_squared));
                }
                dem = 2;
                break;

        }
        
        switch (in_coloring_algorithm) {
            
            case MainWindow.MAXIMUM_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(smoothing);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike(smoothing);       
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm(smoothing);       
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag(smoothing);       
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared(smoothing);       
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared(smoothing);       
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm(smoothing);       
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares(smoothing);       
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2();       
                break;
                
        }

    }

    //orbit
    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_value, double[] initial_vals, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);   
        }
        else {
            type2 = new NormalMandel();
        }
        
        if(perturbation) {
            pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        else {
            pertur_val = new DefaultPerturbation(perturbation_vals[0], perturbation_vals[1]);
        }
        
        if(init_value) {
            init_val = new InitialValue(initial_vals[0], initial_vals[1]);
        }
        else {
            init_val = new DefaultInitialValue(initial_vals[0], initial_vals[1]);
        }

    }

    public Mandelbrot(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, xJuliaCenter, yJuliaCenter);
        
        if(burning_ship) {
            type = new BurningShip();   
        }
        else {
            type = new NormalMandel();
        }
        
        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);   
        }
        else {
            type2 = new NormalMandel();
        }

    }

    @Override
    protected void function(Complex[] complex) {

        type.getPixel(complex[0]);
        complex[0].square_mutable().plus_mutable(complex[1]);
        type2.getPixel(complex[0]);
   
    }
    
    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
        
        if(dem > 0) {
            if(dem > 1) {
                int iterations = 0;

                Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

                Complex[] complex = new Complex[2];
                complex[0] = tempz;//z
                complex[1] = new Complex(pixel);//c

                Complex de = new Complex(1, 0);
                
                Complex zold = new Complex();

                for (; iterations < max_iterations; iterations++) {

                     if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de, zold};
                        return out_color_algorithm.getResult(object);
                     }

                     zold.assign(complex[0]);
                     de.times_mutable(complex[0]).times_mutable(2).plus(1);
                     function(complex);          

                }


                Object[] object = {max_iterations, complex[0]};
                return in_color_algorithm.getResult(object);
            }
            else {
                int iterations = 0;

                Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

                Complex[] complex = new Complex[2];
                complex[0] = tempz;//z
                complex[1] = new Complex(pixel);//c

                Complex de = new Complex(0.7, 0.7);

                for (; iterations < max_iterations; iterations++) {

                     if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de};
                        return out_color_algorithm.getResult(object);
                     }

                     de.times_mutable(complex[0]);
                     function(complex);          

                }


                Object[] object = {max_iterations, complex[0]};
                return in_color_algorithm.getResult(object);
            }
        }
        else {
            int iterations = 0;

            Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

            Complex[] complex = new Complex[2];
            complex[0] = tempz;//z
            complex[1] = new Complex(pixel);//c


            Complex zold = new Complex();

            for (; iterations < max_iterations; iterations++) {

                if(bailout_algorithm.escaped(complex[0])) {
                    Object[] object = {iterations, complex[0], zold};
                    return out_color_algorithm.getResult(object);
                }
                zold.assign(complex[0]);
                function(complex);

            }


            Object[] object = {max_iterations, complex[0]};
            return in_color_algorithm.getResult(object);
        }
  
    }
    
    @Override
    public double calculateFractalWithPeriodicity(Complex pixel) {

        if(dem > 0) {
            if(dem > 1) {
                int iterations = 0;

                check = 3;
                check_counter = 0;

                update = 10;
                update_counter = 0;

                period = new Complex();

                Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

                Complex[] complex = new Complex[2];
                complex[0] = tempz;//z
                complex[1] = new Complex(pixel);//c

                Complex de = new Complex(1, 0);
                
                Complex zold = new Complex();

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de, zold};
                        return out_color_algorithm.getResult(object);
                    }
                    
                    zold.assign(complex[0]);
                    de.times_mutable(complex[0]).times_mutable(2).plus(1);
                    function(complex);

                    if(periodicityCheck(complex[0])) {
                        return max_iterations;
                    }

                }

                return max_iterations;
            }
            else {
                int iterations = 0;

                check = 3;
                check_counter = 0;

                update = 10;
                update_counter = 0;

                period = new Complex();

                Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

                Complex[] complex = new Complex[2];
                complex[0] = tempz;//z
                complex[1] = new Complex(pixel);//c

                Complex de = new Complex(0.7, 0.7);

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de};
                        return out_color_algorithm.getResult(object);
                    }
                    de.times_mutable(complex[0]);
                    function(complex);

                    if(periodicityCheck(complex[0])) {
                        return max_iterations;
                    }

                }

                return max_iterations;
            }
        }
        else {
            int iterations = 0;

            check = 3;
            check_counter = 0;

            update = 10;
            update_counter = 0;

            period = new Complex();

            Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

            Complex[] complex = new Complex[2];
            complex[0] = tempz;//z
            complex[1] = new Complex(pixel);//c

            Complex zold = new Complex();

            for (; iterations < max_iterations; iterations++) {
                if(bailout_algorithm.escaped(complex[0])) {
                    Object[] object = {iterations, complex[0], zold};
                    return out_color_algorithm.getResult(object);
                }
                zold.assign(complex[0]);
                function(complex);

                if(periodicityCheck(complex[0])) {
                    return max_iterations;
                }

            }

            return max_iterations;
        }

    }
    
    @Override
    public double calculateJuliaWithPeriodicity(Complex pixel) {
        
        if(dem > 0) {
            if(dem > 1) {
                int iterations = 0;

                check = 3;
                check_counter = 0;

                update = 10;
                update_counter = 0;

                period = new Complex();


                Complex[] complex = new Complex[2];
                complex[0] = pixel;//z
                complex[1] = new Complex(seed);//c

                Complex de = new Complex(1, 0);
                
                Complex zold = new Complex();

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de, zold};
                        return out_color_algorithm.getResult(object);
                    }
                    
                    zold.assign(complex[0]);
                    de.times_mutable(complex[0]).times_mutable(2).plus(1);
                    function(complex);

                    if(periodicityCheck(complex[0])) {
                        return max_iterations;
                    }
                }

                return max_iterations;
            }
            else {
                int iterations = 0;

                check = 3;
                check_counter = 0;

                update = 10;
                update_counter = 0;

                period = new Complex();


                Complex[] complex = new Complex[2];
                complex[0] = pixel;//z
                complex[1] = new Complex(seed);//c

                Complex de = new Complex(0.7, 0.7);

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de};
                        return out_color_algorithm.getResult(object);
                    }
                    de.times_mutable(complex[0]);
                    function(complex);

                    if(periodicityCheck(complex[0])) {
                        return max_iterations;
                    }
                }

                return max_iterations;
            }
        }
        else {
            int iterations = 0;

            check = 3;
            check_counter = 0;

            update = 10;
            update_counter = 0;

            period = new Complex();


            Complex[] complex = new Complex[2];
            complex[0] = pixel;//z
            complex[1] = new Complex(seed);//c

            Complex zold = new Complex();

            for (; iterations < max_iterations; iterations++) {
                if(bailout_algorithm.escaped(complex[0])) {
                    Object[] object = {iterations, complex[0], zold};
                    return out_color_algorithm.getResult(object);
                }
                zold.assign(complex[0]);
                function(complex);

                if(periodicityCheck(complex[0])) {
                    return max_iterations;
                }
            }

            return max_iterations;
        }
    }


    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
    
       if(dem > 0) {
            if(dem > 1) {
                int iterations = 0;

                Complex[] complex = new Complex[2];
                complex[0] = pixel;//z
                complex[1] = new Complex(seed);//c

                Complex de = new Complex(1, 0);
                
                Complex zold = new Complex();

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de, zold};
                        return out_color_algorithm.getResult(object);
                    }
                    zold.assign(complex[0]);
                    de.times_mutable(complex[0]).times_mutable(2).plus(1);
                    function(complex);

                }

                Object[] object = {max_iterations, complex[0]};
                return in_color_algorithm.getResult(object);
            }
            else {
                int iterations = 0;

                Complex[] complex = new Complex[2];
                complex[0] = pixel;//z
                complex[1] = new Complex(seed);//c

                Complex de = new Complex(0.7, 0.7);

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de};
                        return out_color_algorithm.getResult(object);
                    }
                    de.times_mutable(complex[0]);
                    function(complex);

                }

                Object[] object = {max_iterations, complex[0]};
                return in_color_algorithm.getResult(object);
            }
       }
       else {
            int iterations = 0;

            Complex[] complex = new Complex[2];
            complex[0] = pixel;//z
            complex[1] = new Complex(seed);//c

            Complex zold = new Complex();

            for (; iterations < max_iterations; iterations++) {
                if(bailout_algorithm.escaped(complex[0])) {
                    Object[] object = {iterations, complex[0], zold};
                    return out_color_algorithm.getResult(object);
                }
                zold.assign(complex[0]);
                function(complex);

            }

            Object[] object = {max_iterations, complex[0]};
            return in_color_algorithm.getResult(object);
       }
        
    }
    
    @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {
        
        if(dem > 0) {
            if(dem > 1) {
                int iterations = 0;

                Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

                Complex[] complex = new Complex[2];
                complex[0] = tempz;//z
                complex[1] = new Complex(pixel);//c


                Complex de = new Complex(1, 0);
                double temp;
                
                Complex zold = new Complex();

                for (; iterations < max_iterations; iterations++) {

                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de, zold};
                        double result = out_color_algorithm.getResult(object);
                        temp = result == max_iterations ? result : result - 100800;
                        double[] array = {40 * Math.log(temp + 1) - 100, result};
                        return array;

                    }
                    zold.assign(complex[0]);
                    de.times_mutable(complex[0]).times_mutable(2).plus(1);
                    function(complex);

                }


                Object[] object = {max_iterations, complex[0]};
                temp = in_color_algorithm.getResult(object);
                double result = temp == max_iterations ? max_iterations : max_iterations + temp - 100820;
                double[] array = {40 * Math.log(result + 1) - 100, temp};
                return array;
            }
            else {
                int iterations = 0;

                Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

                Complex[] complex = new Complex[2];
                complex[0] = tempz;//z
                complex[1] = new Complex(pixel);//c


                Complex de = new Complex(0.7, 0.7);
                double temp;

                for (; iterations < max_iterations; iterations++) {

                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de};
                        temp = out_color_algorithm.getResult(object);
                        double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                        return array;

                    }
                    de.times_mutable(complex[0]);
                    function(complex);

                }


                Object[] object = {max_iterations, complex[0]};
                temp = in_color_algorithm.getResult(object);
                double result = temp == max_iterations ? max_iterations : max_iterations + temp - 100820;
                double[] array = {40 * Math.log(result + 1) - 100, temp};
                return array;
            }
        }
        else {
            int iterations = 0;

            Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

            Complex[] complex = new Complex[2];
            complex[0] = tempz;//z
            complex[1] = new Complex(pixel);//c


            Complex zold = new Complex();
            double temp;

            for (; iterations < max_iterations; iterations++) {

                if(bailout_algorithm.escaped(complex[0])) {
                    Object[] object = {iterations, complex[0], zold};
                    temp = out_color_algorithm.getResult(object);
                    double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                    return array;

                }
                zold.assign(complex[0]);
                function(complex);

            }


            Object[] object = {max_iterations, complex[0]};
            temp = in_color_algorithm.getResult(object);
            double result = temp == max_iterations ? max_iterations : max_iterations + temp - 100820;
            double[] array = {40 * Math.log(result + 1) - 100, temp};
            return array;
        }

    }
    
    @Override
    public double[] calculateFractal3DWithPeriodicity(Complex pixel) {

        if(dem > 0) {
            if(dem > 1) {
                int iterations = 0;

                check = 3;
                check_counter = 0;

                update = 10;
                update_counter = 0;

                period = new Complex();

                Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

                Complex[] complex = new Complex[2];
                complex[0] = tempz;//z
                complex[1] = new Complex(pixel);//c

                Complex de = new Complex(1, 0);
                
                Complex zold = new Complex();

                double temp;

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de, zold};
                        double result = out_color_algorithm.getResult(object);
                        temp = result == max_iterations ? result : result - 100800;
                        double[] array = {40 * Math.log(temp + 1) - 100, result};
                        return array;
                    }
                    zold.assign(complex[0]);
                    de.times_mutable(complex[0]).times_mutable(2).plus(1);
                    function(complex);

                    if(periodicityCheck(complex[0])) {
                        double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                        return array;
                    }

                }

                double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                return array;
            }
            else {
                int iterations = 0;

                check = 3;
                check_counter = 0;

                update = 10;
                update_counter = 0;

                period = new Complex();

                Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

                Complex[] complex = new Complex[2];
                complex[0] = tempz;//z
                complex[1] = new Complex(pixel);//c

                Complex de = new Complex(0.7, 0.7);

                double temp;

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de};
                        temp = out_color_algorithm.getResult(object);
                        double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                        return array;
                    }
                    de.times_mutable(complex[0]);
                    function(complex);

                    if(periodicityCheck(complex[0])) {
                        double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                        return array;
                    }

                }

                double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                return array;
            }
        }
        else {
            int iterations = 0;

            check = 3;
            check_counter = 0;

            update = 10;
            update_counter = 0;

            period = new Complex();

            Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));

            Complex[] complex = new Complex[2];
            complex[0] = tempz;//z
            complex[1] = new Complex(pixel);//c

            Complex zold = new Complex();

            double temp;

            for (; iterations < max_iterations; iterations++) {
                if(bailout_algorithm.escaped(complex[0])) {
                    Object[] object = {iterations, complex[0], zold};
                    temp = out_color_algorithm.getResult(object);
                    double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                    return array;
                }
                zold.assign(complex[0]);
                function(complex);

                if(periodicityCheck(complex[0])) {
                    double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                    return array;
                }

            }

            double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
            return array;
        }

    }
    
    @Override
    public double[] calculateJulia3DWithPeriodicity(Complex pixel) {

        if(dem > 0) {
            if(dem > 1) {
                int iterations = 0;

                check = 3;
                check_counter = 0;

                update = 10;
                update_counter = 0;

                period = new Complex();


                Complex[] complex = new Complex[2];
                complex[0] = pixel;//z
                complex[1] = new Complex(seed);//c

                Complex de = new Complex(1, 0);
                
                Complex zold = new Complex();

                double temp;

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de, zold};
                        double result = out_color_algorithm.getResult(object);
                        temp = result == max_iterations ? result : result - 100800;
                        double[] array = {40 * Math.log(temp + 1) - 100, result};
                        return array;
                    }
                    zold.assign(complex[0]);
                    de.times_mutable(complex[0]).times_mutable(2).plus(1);
                    function(complex);

                    if(periodicityCheck(complex[0])) {
                        double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                        return array;
                    }
                }

                double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                return array;
            }
            else {
                int iterations = 0;

                check = 3;
                check_counter = 0;

                update = 10;
                update_counter = 0;

                period = new Complex();


                Complex[] complex = new Complex[2];
                complex[0] = pixel;//z
                complex[1] = new Complex(seed);//c

                Complex de = new Complex(0.7, 0.7);

                double temp;

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de};
                        temp = out_color_algorithm.getResult(object);
                        double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                        return array;
                    }
                    de.times_mutable(complex[0]);
                    function(complex);

                    if(periodicityCheck(complex[0])) {
                        double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                        return array;
                    }
                }

                double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                return array;
            }
        }
        else {
            int iterations = 0;

            check = 3;
            check_counter = 0;

            update = 10;
            update_counter = 0;

            period = new Complex();


            Complex[] complex = new Complex[2];
            complex[0] = pixel;//z
            complex[1] = new Complex(seed);//c

            Complex zold = new Complex();

            double temp;

            for (; iterations < max_iterations; iterations++) {
                if(bailout_algorithm.escaped(complex[0])) {
                    Object[] object = {iterations, complex[0], zold};
                    temp = out_color_algorithm.getResult(object);
                    double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                    return array;
                }
                zold.assign(complex[0]);
                function(complex);

                if(periodicityCheck(complex[0])) {
                    double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                    return array;
                }
            }

            double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
            return array;
        }
        
    }


    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
        
        if(dem > 0) {
            if(dem > 1) {
                int iterations = 0;

                Complex[] complex = new Complex[2];
                complex[0] = pixel;//z
                complex[1] = new Complex(seed);//c

                Complex de = new Complex(1, 0);
                
                Complex zold = new Complex();

                double temp;

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de, zold};
                        double result = out_color_algorithm.getResult(object);
                        temp = result == max_iterations ? result : result - 100800;
                        double[] array = {40 * Math.log(temp + 1) - 100, result};
                        return array;
                    }
                    zold.assign(complex[0]);
                    de.times_mutable(complex[0]).times_mutable(2).plus(1);
                    function(complex);

                }

                Object[] object = {max_iterations, complex[0]};
                temp = in_color_algorithm.getResult(object);
                double result = temp == max_iterations ? max_iterations : max_iterations + temp - 100820;
                double[] array = {40 * Math.log(result + 1) - 100, temp};
                return array;
            }
            else {
                int iterations = 0;

                Complex[] complex = new Complex[2];
                complex[0] = pixel;//z
                complex[1] = new Complex(seed);//c

                Complex de = new Complex(0.7, 0.7);

                double temp;

                for (; iterations < max_iterations; iterations++) {
                    if(bailout_algorithm.escaped(complex[0])) {
                        Object[] object = {iterations, complex[0], de};
                        temp = out_color_algorithm.getResult(object);
                        double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                        return array;
                    }
                    de.times_mutable(complex[0]);
                    function(complex);

                }

                Object[] object = {max_iterations, complex[0]};
                temp = in_color_algorithm.getResult(object);
                double result = temp == max_iterations ? max_iterations : max_iterations + temp - 100820;
                double[] array = {40 * Math.log(result + 1) - 100, temp};
                return array;
            }
        }
        else {
            int iterations = 0;

            Complex[] complex = new Complex[2];
            complex[0] = pixel;//z
            complex[1] = new Complex(seed);//c

            Complex zold = new Complex();

            double temp;

            for (; iterations < max_iterations; iterations++) {
                if(bailout_algorithm.escaped(complex[0])) {
                    Object[] object = {iterations, complex[0], zold};
                    temp = out_color_algorithm.getResult(object);
                    double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                    return array;
                }
                zold.assign(complex[0]);
                function(complex);

            }

            Object[] object = {max_iterations, complex[0]};
            temp = in_color_algorithm.getResult(object);
            double result = temp == max_iterations ? max_iterations : max_iterations + temp - 100820;
            double[] array = {40 * Math.log(result + 1) - 100, temp};
            return array;
        }
        
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
         
        

        return false;

    }
 
}
