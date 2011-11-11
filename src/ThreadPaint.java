import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public abstract class ThreadPaint extends Thread {
  protected Fractal fractal;
  protected int FROMx;
  protected int TOx;
  protected int FROMy;
  protected int TOy;
  protected boolean anti_aliasing;
  protected boolean image_buffering;
  protected int out_coloring_algorithm;
  protected boolean edges;
  protected boolean emboss;
  protected boolean first_part_done;
  protected boolean julia;
  protected boolean fast_julia_filters;
  protected MainWindow ptr;
  protected BufferedImage image;
  protected int drawing_done;
  protected Color fractal_color;
  protected ProgressChecker progress;
  protected int max_iterations;
  protected boolean color_cycling;
  protected int color_cycling_location;
  protected double[][] image_iterations;
  protected int action;
  protected int thread_slices;
  public static final int NORMAL = 0;
  public static final int FAST_JULIA = 1;
  public static final int COLOR_CYCLING = 2;
  public static final int APPLY_PALETTE_AND_FILTER = 3;
  public static final int JULIA_MADE_IMAGE = 4;

    //Fractal
    public ThreadPaint(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, double[][] image_iterations, int bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.anti_aliasing = anti_aliasing;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.edges = edges;
        this.emboss = emboss;
        this.image_buffering = image_buffering;
        this.image = image;
        this.fractal_color = fractal_color;
        this.image_iterations = image_iterations;
        this.color_cycling_location = color_cycling_location;
        thread_slices = 7;
        action = NORMAL;
        julia = false;

        boolean mandelbrot_optimization = false;

        /*if((function == 0 || function == MainWindow.MANDELBROTNTH && z_exponent == 2) && !burning_ship && !inverse_plane && out_coloring_algorithm != MainWindow.CROSS_ORBIT_TRAPS) {
            int image_size = ptr.getImageSize();

            double temp_xcenter_size = xCenter - size / 2;
            double temp_ycenter_size = yCenter - size / 2;
            double temp_size_image_size = size / image_size;
            

            double point_x = temp_xcenter_size + temp_size_image_size * FROMx;
            double point_y = temp_ycenter_size + temp_size_image_size * FROMy;

            double point2_x = temp_xcenter_size + temp_size_image_size * TOx;
            double point2_y = temp_ycenter_size + temp_size_image_size * TOy;

            //This code section checks if the thread contains a part of rectangle. Inside of this rectangle are the areas need to be tested with the optimization equations.
            //We dont test every pixel with those equations cause even if they speed up the process in some areas, is some others they offer nothing more, infact they just slow us down!
            if((point_x > -1.36792 && point_x < 0.37505 && point_y > -0.83794 && point_y < 0.83794) //first test for intersection
                    || (point2_x > -1.36792 && point2_x < 0.37505 && point2_y > -0.83794 && point2_y < 0.83794) //second test for intersection
                    || (point_x <= -1.36792 && point2_x >= -1.36792 && ((point_y <= 0.83794 && point2_y >= 0.83794) || (point_y <= -0.83794 && point2_y >= -0.83794)) //are the points (-1.36792, -0.83794) and (-1.36792, 0.83794) inside the thread area?
                    || (point_x <= 0.37505 && point2_x >= 0.37505 && ((point_y <= 0.83794 && point2_y >= 0.83794) || (point_y <= -0.83794 && point2_y >= -0.83794)))) //are the points (0.37505, -0.83794) and (0.37505, 0.83794) inside the thread area?
                    || (point_x > -1.36792 && point_x < 0.37505 && point_y < -0.83794 && point2_y > 0.83794) || (point_x < -1.36792 && point2_x > 0.37505 && point_y > -0.83794 && point_y < 0.83794) || (point_x > -1.36792 && point_x < 0.37505 && point_y > -0.83794 && point_y < 0.83794) || (point_x > -1.36792 && point_x < 0.37505 && point_y < -0.83794 && point2_y > -0.83794) || (point_x > -1.36792 && point_x < 0.37505 && point_y > -0.83794 && point_y < 0.83794) || (point_x < -1.36792 && point2_x > -1.36792 && point_y > -0.83794 && point_y < 0.83794)) { //least used, mainly for backup

                mandelbrot_optimization = true;

            }

        }*/
        
        
        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case 1:
                fractal = new MandelbrotCubbed(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, z_exponent);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
            case MainWindow.NEWTON3:
                fractal = new Newton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, inverse_plane);
                break;
            case MainWindow.NEWTON4:
                fractal = new Newton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, inverse_plane);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                fractal = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, inverse_plane);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                fractal = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, inverse_plane);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane);
                break;
        }
        
        first_part_done = false;
        drawing_done = 0;
        
        progress = new ProgressChecker(ptr);

    }

    //Julia
    public ThreadPaint(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, double[][] image_iterations, int bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.anti_aliasing = anti_aliasing;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.edges = edges;
        this.emboss = emboss;
        this.image_buffering = image_buffering;
        this.image = image;
        this.fractal_color = fractal_color;
        this.image_iterations = image_iterations;
        this.color_cycling_location = color_cycling_location;
        thread_slices = 7;
        action = NORMAL;
        julia = true;

        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubbed(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
        }
                        
        first_part_done = false;
        drawing_done = 0;

        progress = new ProgressChecker(ptr);
       
    }
    
    /*//Julia Made Image
    public ThreadPaint(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, int bailout, double[][] image_iterations, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location) {
        
        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.anti_aliasing = anti_aliasing;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.edges = edges;
        this.emboss = emboss;
        this.image_buffering = image_buffering;
        this.image = image;
        this.fractal_color = fractal_color;
        this.image_iterations = image_iterations;
        this.color_cycling_location = color_cycling_location;
        action = JULIA_MADE_IMAGE;
        
        double temp_xcenter_size = xCenter - size / 2;
        double temp_ycenter_size = yCenter - size / 2;
        double temp_size_image_size = size / image.getHeight();
        
        double xJuliaCenter = temp_xcenter_size + temp_size_image_size * ((TOx + FROMx) / 2);
        double yJuliaCenter = temp_ycenter_size + temp_size_image_size * ((TOy + FROMy) / 2);
        
        julia = true;
        
        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubbed(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
        }
        
        first_part_done = false;
        drawing_done = 0;

        progress = new ProgressChecker(ptr);
        
    }*/

    //Julia Preview
    public ThreadPaint(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, boolean inverse_plane, int out_coloring_algorithm, boolean anti_aliasing, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.fast_julia_filters = fast_julia_filters;
        this.anti_aliasing = anti_aliasing;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.edges = edges;
        this.emboss = emboss;
        this.image = image;
        this.fractal_color = fractal_color;
        this.color_cycling_location = color_cycling_location;
        thread_slices = 7;
        action = FAST_JULIA;
        julia = true;

        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubbed(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, inverse_plane, xJuliaCenter, yJuliaCenter);
                break;
        }

        first_part_done = false;

    }

    //Color Cycling
    public ThreadPaint(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, double[][] image_iterations, MainWindow ptr, Color fractal_color, BufferedImage image, int out_coloring_algorithm, int color_cycling_location) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.image_iterations = image_iterations;
        this.color_cycling_location = color_cycling_location;
        this.fractal_color = fractal_color;
        action = COLOR_CYCLING;
        first_part_done = false;
        color_cycling = true;

    }

    //Apply Filter
    public ThreadPaint(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, double[][] image_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int out_coloring_algorithm, int color_cycling_location, boolean image_buffering, boolean anti_aliasing, boolean edges, boolean emboss) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.fractal_color = fractal_color;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.image_iterations = image_iterations;
        this.color_cycling_location = color_cycling_location;
        this.image_buffering = image_buffering;
        this.anti_aliasing = anti_aliasing;
        this.edges = edges;
        this.emboss = emboss;
        action = APPLY_PALETTE_AND_FILTER;
        first_part_done = false;

        drawing_done = 0;

        progress = new ProgressChecker(ptr);

    }


    @Override
    public void run() {

        switch (action) {

            case NORMAL:
                paint(ptr.getMainPanel().getGraphics());
                break;
            case FAST_JULIA:
                fastJuliaPaint();
                break;
            case COLOR_CYCLING:
                colorCycling();
                break;
            case APPLY_PALETTE_AND_FILTER:
                applyPaletteAndFilter(ptr.getMainPanel().getGraphics());
                break;
            /*case JULIA_MADE_IMAGE:
                juliaMadeImage(ptr.getMainPanel().getGraphics());
                break;*/
                
        }
                      
    }

    protected abstract Color getDrawingColor(double result);

    protected abstract Color getDrawingColorSmooth(double result);

    private void paint(Graphics brush) {

         int image_size = image.getHeight();

         if(julia) {
             if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                 if(image_buffering) {
                     paintJuliaSmoothBuffering(image_size);
                 }
                 else {
                     paintJuliaSmoothDrawing(brush, image_size);
                 }
             }
             else {
                 if(image_buffering) {
                     paintJuliaNormalBuffering(image_size);
                 }
                 else {
                     paintJuliaNormalDrawing(brush, image_size);
                 }
             }
         }
         else {
             if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                 if(image_buffering) {
                     paintFractalSmoothBuffering(image_size);
                 }
                 else {
                     paintFractalSmoothDrawing(brush, image_size);
                 }
             }
             else {
                 if(image_buffering) {
                     paintFractalNormalBuffering(image_size);                 
                 }
                 else {
                     paintFractalNormalDrawing(brush, image_size);                  
                 }
             }
         }
         

         if(drawing_done != 0) {
             progress.update(drawing_done);
         }

         boolean whole_done_temp = false;
         
         synchronized(this) {
             
             first_part_done = true;

             whole_done_temp = ptr.isDrawingDone();
             
         }

         if(whole_done_temp) {           

             if(edges) {
                 filterEdgeDetection();
             }

             if(emboss) {
                 filterEmboss();
             }

             if(anti_aliasing) {
                 filterAntiAliasing();
             }
           
                                     
             ptr.setOptions(true);
             ptr.setWholeImageDone(true);
             ptr.repaint();
             ptr.getProgressBar().setValue((image_size * image_size) + (image_size *  image_size / 100));
         }

         brush.dispose();

         try {
             finalize();
         }
         catch (Throwable ex) {}
        
    }

    private void paintFractalNormalDrawing(Graphics brush, int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         /*for(int y = FROMy; y < TOy; y++) {

             temp_y0 = temp_ycenter_size + temp_size_image_size * y;
             
             for(int x = FROMx; x < TOx; x++) {

                 image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_xcenter_size + temp_size_image_size * x, temp_y0));
                 color = image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColor(image_iterations[x][y] + color_cycling_location);

                 full_image_g.setColor(color);
                 full_image_g.drawLine(x, y, x, y);
                 brush.setColor(color);
                 brush.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }*/
         
         //full_image_g.setColor(Color.WHITE);
         //full_image_g.fillRect(FROMx, FROMy, tread_size_width, tread_size_height);
         //brush.setColor(Color.WHITE);
         //brush.fillRect(FROMx, FROMy, tread_size_width, tread_size_height);

         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         double temp_starting_pixel_cicle;
         Color temp_starting_pixel_color;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;              
                        
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
                 
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     x = slice_FROMx + step;
                     temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
                     
                     temp_starting_pixel_cicle = image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                     
                     if(image_iterations[x][y] == max_iterations) {
                         temp_starting_pixel_color = fractal_color;
                     }
                     else {
                         temp_starting_pixel_color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                     }
                     
                             
                     for(; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 
                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 image_iterations[l][k] = temp_starting_pixel_cicle;
                                 full_image_g.setColor(temp_starting_pixel_color);
                                 full_image_g.drawLine(l, k, l, k);
                                 brush.setColor(temp_starting_pixel_color);
                                 brush.drawLine(l, k, l, k);
                             }
                         }
                         
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }
                 
         full_image_g.dispose();
         full_image_g = null;

    }
    
    private void paintFractalNormalBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         double temp_starting_pixel_cicle;
         Color temp_starting_pixel_color;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
                               
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
                 
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     x = slice_FROMx + step;
                     temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
                     
                     temp_starting_pixel_cicle = image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                     
                     if(image_iterations[x][y] == max_iterations) {
                         temp_starting_pixel_color = fractal_color;
                     }
                     else {
                         temp_starting_pixel_color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                     }
                     
                             
                     for(; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 
                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         
                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y); 

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 image_iterations[l][k] = temp_starting_pixel_cicle;
                                 full_image_g.setColor(temp_starting_pixel_color);
                                 full_image_g.drawLine(l, k, l, k);
                             }
                         }
                         
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }

    /*private void paintFractalNormalBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         double temp_y0;

         for(int y = FROMy; y < TOy; y++) {

             temp_y0 = temp_ycenter_size + temp_size_image_size * y;

             for(int x = FROMx; x < TOx; x++) {

                 image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_xcenter_size + temp_size_image_size * x, temp_y0));
                 full_image_g.setColor(image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColor(image_iterations[x][y] + color_cycling_location));
                 full_image_g.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;

    }*/

    private void paintFractalSmoothDrawing(Graphics brush, int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
         
         
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
         
         
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     for(x = slice_FROMx + step; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 
                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 image_iterations[l][k] = max_iterations;
                                 full_image_g.setColor(fractal_color);
                                 full_image_g.drawLine(l, k, l, k);
                                 brush.setColor(fractal_color);
                                 brush.drawLine(l, k, l, k);
                             }
                         }
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void paintFractalSmoothBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
         
         
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
         
         
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     for(x = slice_FROMx + step; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 
                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 image_iterations[l][k] = max_iterations;
                                 full_image_g.setColor(fractal_color);
                                 full_image_g.drawLine(l, k, l, k);
                             }
                         }
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void paintJuliaNormalDrawing(Graphics brush, int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         double temp_starting_pixel_cicle;
         Color temp_starting_pixel_color;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;              
                        
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
                 
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     x = slice_FROMx + step;
                     temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
                     
                     temp_starting_pixel_cicle = image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     
                     if(image_iterations[x][y] == max_iterations) {
                         temp_starting_pixel_color = fractal_color;
                     }
                     else {
                         temp_starting_pixel_color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                     }
                     
                             
                     for(; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 
                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 image_iterations[l][k] = temp_starting_pixel_cicle;
                                 full_image_g.setColor(temp_starting_pixel_color);
                                 full_image_g.drawLine(l, k, l, k);
                                 brush.setColor(temp_starting_pixel_color);
                                 brush.drawLine(l, k, l, k);
                             }
                         }
                         
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void paintJuliaNormalBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         double temp_starting_pixel_cicle;
         Color temp_starting_pixel_color;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
                               
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
                 
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     x = slice_FROMx + step;
                     temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
                     
                     temp_starting_pixel_cicle = image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     
                     if(image_iterations[x][y] == max_iterations) {
                         temp_starting_pixel_color = fractal_color;
                     }
                     else {
                         temp_starting_pixel_color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                     }
                     
                             
                     for(; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 
                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         
                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y); 

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(image_iterations[x][y] == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(image_iterations[x][y] + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 image_iterations[l][k] = temp_starting_pixel_cicle;
                                 full_image_g.setColor(temp_starting_pixel_color);
                                 full_image_g.drawLine(l, k, l, k);
                             }
                         }
                         
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void paintJuliaSmoothDrawing(Graphics brush, int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();
        
         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
         
         
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
         
         
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     for(x = slice_FROMx + step; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 
                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         brush.setColor(color);
                         brush.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 image_iterations[l][k] = max_iterations;
                                 full_image_g.setColor(fractal_color);
                                 full_image_g.drawLine(l, k, l, k);
                                 brush.setColor(fractal_color);
                                 brush.drawLine(l, k, l, k);
                             }
                         }
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void paintJuliaSmoothBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
         
         
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
         
         
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     for(x = slice_FROMx + step; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 
                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(image_iterations[x][y] == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 image_iterations[l][k] = max_iterations;
                                 full_image_g.setColor(fractal_color);
                                 full_image_g.drawLine(l, k, l, k);
                             }
                         }
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void fastJuliaPaint() {

        int image_size = image.getHeight();

        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            paintFastJuliaSmoothBuffering(image_size);
        }
        else {
            paintFastJuliaNormalBuffering(image_size);
        }

        boolean whole_done_temp = false;

        synchronized(this) {

             first_part_done = true;

             whole_done_temp = ptr.isDrawingDone();

       }

       if(whole_done_temp) {

           if(fast_julia_filters) {
               if(edges) {
                   filterEdgeDetection();
               }

               if(emboss) {
                   filterEmboss();
               }

               if(anti_aliasing) {
                   filterAntiAliasing();
               }
           }

           ((Graphics2D)ptr.getMainPanel().getGraphics()).drawImage(image, ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), ptr);

       }

       try {
            finalize();
       }
       catch (Throwable ex) {}


    }

    private void paintFastJuliaNormalBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         
         double temp_starting_pixel_cicle;
         Color temp_starting_pixel_color;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
                               
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
         
         double temp_iteration = 0;
                 
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     x = slice_FROMx + step;
                     temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
                     
                     temp_starting_pixel_cicle = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     
                     if(temp_starting_pixel_cicle == max_iterations) {
                         temp_starting_pixel_color = fractal_color;
                     }
                     else {
                         temp_starting_pixel_color = getDrawingColor(temp_starting_pixel_cicle + color_cycling_location);
                     }
                     
                             
                     for(; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 
                         if(temp_iteration == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(temp_iteration == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(temp_iteration + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(temp_iteration == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(temp_iteration == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(temp_iteration + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                

                         if(temp_iteration == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(temp_iteration == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(temp_iteration + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y); 
  
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(temp_iteration == temp_starting_pixel_cicle) {
                             color = temp_starting_pixel_color;
                         }
                         else {
                             if(temp_iteration == max_iterations) {
                                 color = fractal_color;
                             }
                             else {
                                 color = getDrawingColor(temp_iteration + color_cycling_location);
                             }
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                         
                     }
                     y++;
             
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 full_image_g.setColor(temp_starting_pixel_color);
                                 full_image_g.drawLine(l, k, l, k);
                             }
                         }
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void paintFastJuliaSmoothBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / image_size;

         
         Color color = null;
                 
         int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
         
         
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
         
         double temp_iteration;
                 
         
         for(int i = 0; i < thread_slices; i++) {
             for(int j = 0; j < thread_slices; j++) {
               
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     for(x = slice_FROMx + step; x < slice_TOx - step; x++) {

                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 
                         if(temp_iteration == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(temp_iteration + color_cycling_location);
                             whole_area = false;
                         }
                 

                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                       
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(temp_iteration == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(temp_iteration + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);
                
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                

                         if(temp_iteration == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(temp_iteration + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(temp_iteration == max_iterations) {
                             color = fractal_color;
                         }
                         else {
                             color = getDrawingColorSmooth(temp_iteration + color_cycling_location);
                             whole_area = false;
                         }
                 
                         full_image_g.setColor(color);
                         full_image_g.drawLine(x, y, x, y);

                     }
                     y++;
             
                     if(whole_area) {
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             for(int l = x + 1 ; l < slice_TOx - step - 1; l++) {
                                 full_image_g.setColor(fractal_color);
                                 full_image_g.drawLine(l, k, l, k);
                             }
                         }
                         break;
                     }
                 }
             }
         }

         full_image_g.dispose();
         full_image_g = null;

    }
   
    private void colorCycling() {

         if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
             ColorCyclingSmooth();
         }
         else {
             ColorCyclingNormal();
         }

    }

    private void ColorCyclingNormal() {

         if(!color_cycling) {

             return;

         }

         Graphics2D full_image_g = image.createGraphics();

         first_part_done = false;
         ptr.setWholeImageDone(false);

         color_cycling_location++;

         color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;
                
         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx; x < TOx; x++) {
                 full_image_g.setColor(image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColor(image_iterations[x][y] + color_cycling_location));
                 full_image_g.drawLine(x, y, x, y);
             }
         }


         first_part_done = true;
         ptr.setWholeImageDone(true);

         ptr.repaint();

         try {
             Thread.sleep(40);
         }
         catch (InterruptedException ex) {}

         ColorCyclingNormal();

    }

    private void ColorCyclingSmooth() {

        if(!color_cycling) {

             return;

         }

         Graphics2D full_image_g = image.createGraphics();

         first_part_done = false;
         ptr.setWholeImageDone(false);


         color_cycling_location++;

         color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;

         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx; x < TOx; x++) {
                 full_image_g.setColor(image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location));
                 full_image_g.drawLine(x, y, x, y);
             }
         }


         first_part_done = true;
         ptr.setWholeImageDone(true);

         ptr.repaint();

         try {
             Thread.sleep(40);
         }
         catch (InterruptedException ex) {}

         ColorCyclingSmooth();
        
    }
    
    private void applyPaletteAndFilter(Graphics brush) {
        
         int image_size = image.getHeight();

          
         if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
             if(image_buffering) {
                 applyPaletteAndFilterSmoothBuffering(image_size);
             }
             else {
                 applyPaletteAndFilterSmoothDrawing(brush, image_size);
             }
         }
         else {
             if(image_buffering) {
                 applyPaletteAndFilterNormalBuffering(image_size);
             }
             else {
                 applyPaletteAndFilterNormalDrawing(brush, image_size);
             }
         }
                  
         if(drawing_done != 0) {
             progress.update(drawing_done);
         }

         boolean whole_done_temp = false;
         
         synchronized(this) {
             
             first_part_done = true;

             whole_done_temp = ptr.isDrawingDone();
             
         }

         if(whole_done_temp) {

             if(edges) {
                 filterEdgeDetection();
             }

             if(emboss) {
                 filterEmboss();
             }

             if(anti_aliasing) {
                 filterAntiAliasing();
             }

             ptr.setOptions(true);
             ptr.setWholeImageDone(true);
             ptr.repaint();
             ptr.getProgressBar().setValue((image_size * image_size) + (image_size *  image_size / 100));
         }

         brush.dispose();

         try {
             finalize();
         }
         catch (Throwable ex) {}
        
    }

    private void applyPaletteAndFilterNormalDrawing(Graphics brush, int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         int pixel_percent = image_size *  image_size / 100;

         Color color;

         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx; x < TOx; x++) {

                 color = image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColor(image_iterations[x][y] + color_cycling_location);

                 full_image_g.setColor(color);
                 full_image_g.drawLine(x, y, x, y);
                 brush.setColor(color);
                 brush.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void applyPaletteAndFilterNormalBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         int pixel_percent = image_size *  image_size / 100;


         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx; x < TOx; x++) {

                 full_image_g.setColor(image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColor(image_iterations[x][y] + color_cycling_location));
                 full_image_g.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void applyPaletteAndFilterSmoothDrawing(Graphics brush, int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         int pixel_percent = image_size *  image_size / 100;

         Color color;

         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx; x < TOx; x++) {

                 color = image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);

                 full_image_g.setColor(color);
                 full_image_g.drawLine(x, y, x, y);
                 brush.setColor(color);
                 brush.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;

    }

    private void applyPaletteAndFilterSmoothBuffering(int image_size) {

         Graphics2D full_image_g = image.createGraphics();

         int pixel_percent = image_size *  image_size / 100;

         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx; x < TOx; x++) {

                 full_image_g.setColor(image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location));
                 full_image_g.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;

    }
    
    /*private void juliaMadeImage(Graphics brush) {

        int image_size = image.getHeight();
         
        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            if(image_buffering) {
                juliaMadeImageSmoothBuffering(image_size);
            }
            else {
                juliaMadeImageSmoothDrawing(brush, image_size);
            }
        }
        else {
            if(image_buffering) {
                juliaMadeImageNormalBuffering(image_size);
            }
            else {
                juliaMadeImageNormalDrawing(brush, image_size);
            }
        }
        
        if(drawing_done != 0) {
             progress.update(drawing_done);
         }

         boolean whole_done_temp = false;
         
         synchronized(this) {
             
             first_part_done = true;

             whole_done_temp = ptr.isDrawingDone();
             
         }

         if(whole_done_temp) {           

             if(edges) {
                 filterEdgeDetection();
             }

             if(emboss) {
                 filterEmboss();
             }

             if(anti_aliasing) {
                 filterAntiAliasing();
             }
           
                                     
             ptr.setOptions(true);
             ptr.setWholeImageDone(true);
             ptr.repaint();
             ptr.getProgressBar().setValue((image_size * image_size) + (image_size *  image_size / 100));
         }

         brush.dispose();

         try {
             finalize();
         }
         catch (Throwable ex) {}
        
    }
    
    private void juliaMadeImageNormalDrawing(Graphics brush, int image_size) {
        
         Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / (TOx - FROMx);

         int pixel_percent = image_size *  image_size / 100;

         Color color;

         double temp_y0;

         for(int y = FROMy, k = 0; y < TOy; y++, k++) {

             temp_y0 = temp_ycenter_size + temp_size_image_size * k;

             for(int x = FROMx, l = 0; x < TOx; x++, l++) {

                 image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size * l, temp_y0));
                 color = image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColor(image_iterations[x][y] + color_cycling_location);

                 full_image_g.setColor(color);
                 full_image_g.drawLine(x, y, x, y);
                 brush.setColor(color);
                 brush.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;
        
    }
    
    private void juliaMadeImageNormalBuffering(int image_size) {
        
        Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / (TOx - FROMx);

         int pixel_percent = image_size *  image_size / 100;

         double temp_y0;

         for(int y = FROMy, k = 0; y < TOy; y++, k++) {

             temp_y0 = temp_ycenter_size + temp_size_image_size * k;

             for(int x = FROMx, l = 0; x < TOx; x++, l++) {

                 image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size * l, temp_y0));
                 full_image_g.setColor(image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColor(image_iterations[x][y] + color_cycling_location));
                 full_image_g.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;
        
    }
    
    private void juliaMadeImageSmoothDrawing(Graphics brush, int image_size) {
        
        Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();
        
         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / (TOx - FROMx);

         int pixel_percent = image_size *  image_size / 100;

         Color color;

         double temp_y0;

         for(int y = FROMy, k = 0; y < TOy; y++, k++) {

             temp_y0 = temp_ycenter_size + temp_size_image_size * k;

             for(int x = FROMx, l = 0; x < TOx; x++, l++) {

                 image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size * l, temp_y0));
                 color = image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location);

                 full_image_g.setColor(color);
                 full_image_g.drawLine(x, y, x, y);
                 brush.setColor(color);
                 brush.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;
        
    }
    
    private void juliaMadeImageSmoothBuffering(int image_size) {
        
        Graphics2D full_image_g = image.createGraphics();

         double size = fractal.getSize();

         double temp_xcenter_size = fractal.getXCenter() - size / 2;
         double temp_ycenter_size = fractal.getYCenter() - size / 2;
         double temp_size_image_size = size / (TOx - FROMx);

         int pixel_percent = image_size *  image_size / 100;

         double temp_y0;

         for(int y = FROMy, k = 0; y < TOy; y++, k++) {

             temp_y0 = temp_ycenter_size + temp_size_image_size * k;

             for(int x = FROMx, l = 0; x < TOx; x++, l++) {

                 image_iterations[x][y] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size * l, temp_y0));
                 full_image_g.setColor(image_iterations[x][y] == max_iterations ? fractal_color : getDrawingColorSmooth(image_iterations[x][y] + color_cycling_location));
                 full_image_g.drawLine(x, y, x, y);

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }

         }

         full_image_g.dispose();
         full_image_g = null;
        
    }*/

    private void filterEmboss() {

       int image_size = image.getHeight();

       BufferedImage newSource = new BufferedImage (image_size, image_size, BufferedImage.TYPE_INT_RGB);

       for (int i = 0; i < image_size; i++) {
           for (int j = 0; j < image_size; j++) {
               int current = image.getRGB (j, i);

               int upperLeft = 0;
               if(i > 0 && j > 0) {
                   upperLeft = image.getRGB (j - 1, i - 1);
               }

               int rDiff = ((current >> 16) & 255) - ((upperLeft >> 16) & 255);
               int gDiff = ((current >> 8) & 255) - ((upperLeft >> 8) & 255);
               int bDiff = (current & 255) - (upperLeft & 255);

               int diff = rDiff;
               if (Math.abs (gDiff) > Math.abs (diff)) {
                   diff = gDiff;
               }
               if (Math.abs (bDiff) > Math.abs (diff)) {
                   diff = bDiff;
               }

               int grayLevel = Math.max(Math.min (128 + diff, 255), 0);
               newSource.setRGB (j, i, (grayLevel << 16) + (grayLevel << 8) + grayLevel);
           }
       }


       image.getGraphics().drawImage(newSource, 0, 0, image_size , image_size, null);

       newSource = null;


    }

    private void filterEdgeDetection() {

       /*float[] EDGES = {7.0f,   7.0f,  7.0f,
                         7.0f, -56.0f,  7.0f,
                         7.0f,   7.0f,  7.0f};*/

        float[] EDGES = {-1.0f, -1.0f, -2.0f, -1.0f, -1.0f,
                         -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
                         -2.0f, -4.0f, 44.0f, -4.0f, -2.0f,
                         -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
                         -1.0f, -1.0f, -2.0f, -1.0f, -1.0f};


        int image_size = image.getHeight();

        Kernel kernel = new Kernel(5, 5, EDGES);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        BufferedImage newSource = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, 0, 0, image_size , image_size, null);

        cop.filter(newSource, image);

        graphics.dispose();
        graphics = null;
        EDGES = null;
        kernel = null;
        cop = null;
        newSource = null;

    }

    private void  filterAntiAliasing() {

       /*     float b = 0.05f;
                float a = 1.0f - (8.0f * b);


               float[] AA = {b, b, b,    // low-pass filter
                             b, a, b,
                             b, b, b};


                /* float c = 0.00390625f;
                 float b = 0.046875f;
                 float a = 1.0f - (16.0f * c + 8.0f * b);

                 float[] AA = {c, c, c, c, c,    // low-pass filter
                               c, b, b, b, c,
                               c, b, a, b, c,
                               c, b, b, b, c,
                               c, c, c, c, c};*/




               /*  float d = 1.0f / 3096.0f;
                 float c = 12.0f * d;
                 float b = 12.0f * c;
                 float a = 1.0f - (24.0f * d + 16.0f * c + 8.0f * b);

                 float[] AA = {d, d, d, d, d, d, d,    // low-pass filter
                               d, c, c, c, c, c, d,
                               d, c, b, b, b, c, d,
                               d, c, b, a, b, c, d,
                               d, c, b, b, b, c, d,
                               d, c, c, c, c, c, d,
                               d, d, d, d, d, d, d};*/





       /* float e = 1.0f / 37184.0f;
        float d = 12.0f * e;
        float c = 12.0f * d;
        float b = 12.0f * c;
        float a = 1.0f - (32.0f * e + 24.0f * d + 16.0f * c + 8.0f * b);

        float[] AA  = {e, e, e, e, e, e, e, e, e,    // low-pass filter
                       e, d, d, d, d, d, d, d, e,
                       e, d, c, c, c, c, c, d, e,
                       e, d, c, b, b, b, c, d, e,
                       e, d, c, b, a, b, c, d, e,
                       e, d, c, b, b, b, c, d, e,
                       e, d, c, c, c, c, c, d, e,
                       e, d, d, d, d, d, d, d, e,
                       e, e, e, e, e, e, e, e, e};*/


        float h = 1.0f / 64260344.0f;
        float g = 12.0f * h;
        float f = 12.0f * g;
        float e = 12.0f * f;
        float d = 12.0f * e;
        float c = 12.0f * d;
        float b = 12.0f * c;
        float a = 1.0f - (56.0f * h + 48.0f * g + 40.0f * f + 32.0f * e + 24.0f * d + 16.0f * c + 8.0f * b);

        float[] AA  = {h, h, h, h, h, h, h, h, h, h, h, h, h, h, h,    // low-pass filter
                       h, g, g, g, g, g, g, g, g, g, g, g, g, g, h,
                       h, g, f, f, f, f, f, f, f, f, f, f, f, g, h,
                       h, g, f, e, e, e, e, e, e, e, e, e, f, g, h,
                       h, g, f, e, d, d, d, d, d, d, d, e, f, g, h,
                       h, g, f, e, d, c, c, c, c, c, d, e, f, g, h,
                       h, g, f, e, d, c, b, b, b, c, d, e, f, g, h,
                       h, g, f, e, d, c, b, a, b, c, d, e, f, g, h,
                       h, g, f, e, d, c, b, b, b, c, d, e, f, g, h,
                       h, g, f, e, d, c, c, c, c, c, d, e, f, g, h,
                       h, g, f, e, d, d, d, d, d, d, d, e, f, g, h,
                       h, g, f, e, e, e, e, e, e, e, e, e, f, g, h,
                       h, g, f, f, f, f, f, f, f, f, f, f, f, g, h,
                       h, g, g, g, g, g, g, g, g, g, g, g, g, g, h,
                       h, h, h, h, h, h, h, h, h, h, h, h, h, h, h};


        //resize the picture to cover the image edges
        int kernelWidth = (int)Math.sqrt((double)AA.length);
        int kernelHeight = kernelWidth;
        int xOffset = (kernelWidth - 1) / 2;
        int yOffset = xOffset;

        int image_size = image.getHeight();

        BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, xOffset, yOffset, null);


        Kernel kernel = new Kernel(kernelWidth, kernelHeight, AA);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(newSource, image);

        graphics.dispose();
        graphics = null;
        AA = null;
        kernel = null;
        cop = null;
        newSource = null;

    } 

    public void setColorCycling(Boolean temp) {

        color_cycling = false;

    }

    public int getColorCyclingLocation() {

        return color_cycling_location;

    }
 

    public boolean isFirstPartDone() {

        return first_part_done;
        
    }
  
}
