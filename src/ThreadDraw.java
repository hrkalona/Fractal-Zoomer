import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferInt;
import java.awt.image.Kernel;
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public abstract class ThreadDraw extends Thread {
  protected static double[] image_iterations;
  protected int [] rgbs;
  protected Fractal fractal;
  protected int FROMx;
  protected int TOx;
  protected int FROMy;
  protected int TOy;
  protected boolean[] filters;
  protected int out_coloring_algorithm;
  protected boolean first_part_done;
  protected boolean julia;
  protected boolean fast_julia_filters;
  protected boolean boundary_tracing;
  protected MainWindow ptr;
  protected BufferedImage image;
  protected int drawing_done;
  protected double calculated;
  protected Color fractal_color;
  protected ProgressChecker progress;
  protected int max_iterations;
  protected boolean color_cycling;
  protected int color_cycling_location;
  protected int action;
  protected int thread_slices;
  protected double antialiasing_size;
  protected double[] AntialiasingData;
  protected double[] FastJuliaData;
  protected int[] Done;
  protected int QueueSize;
  protected int[] Queue;
  protected int QueueHead;
  protected int QueueTail;
  public static final int NORMAL = 0;
  public static final int FAST_JULIA = 1;
  public static final int COLOR_CYCLING = 2;
  public static final int APPLY_PALETTE_AND_FILTER = 3;
  public static final int JULIA_MAP = 4;
  public static final int Loaded = 1, Queued = 2;
  
  
  
    static {

       image_iterations = new double[MainWindow.image_size * MainWindow.image_size];
               
    }

    //Fractal
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.image = image;
        this.fractal_color = fractal_color;
        this.color_cycling_location = color_cycling_location;
        
        if(filters[0]) {
            antialiasing_size = (size / image.getHeight()) * 0.25;
        }
        // thread_slices = 10;
        action = NORMAL;
        julia = false;
        
        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        
        
        this.boundary_tracing = boundary_tracing;
        
        if(boundary_tracing) {
            int width = TOx - FROMx;
            int height = TOy - FROMy;
            QueueSize = (width + height) * 4;
            Queue = new int[QueueSize];
            Done = new int[width * height];
            QueueHead = 0;
            QueueTail = 0;
            
            if(filters[0]) {
                AntialiasingData = new double[width * height];
            }
        }
        
        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship, z_exponent);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, burning_ship, coefficients);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.NEWTON3:
                fractal = new Newton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTON4:
                fractal = new Newton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                fractal = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                fractal = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
             case MainWindow.NEWTONPOLY:
                fractal = new NewtonPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals, coefficients);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals);
                break;
            case MainWindow.SIERPINSKI_GASKET:
                fractal = new SierpinskiGasket(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEY3:
                fractal = new Halley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEY4:
                fractal = new Halley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEYGENERALIZED3:
                fractal = new HalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEYGENERALIZED8:
                fractal = new HalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEYPOLY:
                fractal = new HalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals, coefficients);
                break;
            case MainWindow.SCHRODER3:
                fractal = new Schroder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODER4:
                fractal = new Schroder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODERGENERALIZED3:
                fractal = new SchroderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODERGENERALIZED8:
                fractal = new SchroderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODERPOLY:
                fractal = new SchroderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals, coefficients);
                break;
            case MainWindow.HOUSEHOLDER3:
                fractal = new Householder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDER4:
                fractal = new Householder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED3:
                fractal = new HouseholderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED8:
                fractal = new HouseholderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDERPOLY:
                fractal = new HouseholderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, plane_type, rotation_vals, coefficients);
                break;
        }
        
        first_part_done = false;
        drawing_done = 0;
        calculated = 0;
        
        progress = new ProgressChecker(ptr);

    }

    //Julia
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.image = image;
        this.fractal_color = fractal_color;
        this.color_cycling_location = color_cycling_location;
        
        if(filters[0]) {
            antialiasing_size = (size / image.getHeight()) * 0.25;
        }
        //thread_slices = 10;
        action = NORMAL;
        julia = true;
        
        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        
        this.boundary_tracing = boundary_tracing;
        
        if(boundary_tracing) {
            int width = TOx - FROMx;
            int height = TOy - FROMy;
            QueueSize = (width + height) * 4;
            Queue = new int[QueueSize];
            Done = new int[width * height];
            QueueHead = 0;
            QueueTail = 0;
            
            if(filters[0]) {
                AntialiasingData = new double[width * height];
            }
        }

        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, coefficients, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
        }
                        
        first_part_done = false;
        drawing_done = 0;
        calculated = 0;

        progress = new ProgressChecker(ptr);
       
    }
    
    //Julia Map
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {
        
        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.image = image;
        this.fractal_color = fractal_color;
        this.color_cycling_location = color_cycling_location;
        
        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        
        if(filters[0]) {
            antialiasing_size = (size / image.getHeight()) * 0.25;
        }
        
        action = JULIA_MAP;
        
        double temp_xcenter_size = xCenter - size * 0.5;
        double temp_ycenter_size = yCenter - size * 0.5;
        double temp_size_image_size = size / image.getHeight();
        
        double temp1 = temp_xcenter_size + temp_size_image_size * ((TOx + FROMx) * 0.5);
        double temp2 = temp_ycenter_size + temp_size_image_size * ((TOy + FROMy) * 0.5);
        
        double xJuliaCenter = temp1 * rotation_vals[0] - temp2 * rotation_vals[1];
        double yJuliaCenter = temp1* rotation_vals[1] + temp2 * rotation_vals[0];
        
        julia = true;
        
        switch (function) {
            case 0:
                fractal = new Mandelbrot(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, coefficients, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(0.5, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(0, 0, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
        }
        
        first_part_done = false;
        drawing_done = 0;

        progress = new ProgressChecker(ptr);
        
    }

    //Julia Preview
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, int out_coloring_algorithm, boolean[] filters, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.fast_julia_filters = fast_julia_filters;
        this.filters = filters;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.image = image;
        this.fractal_color = fractal_color;
        this.color_cycling_location = color_cycling_location;
        
        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();    
             
        if(filters[0]) {
            antialiasing_size = (size / image.getHeight()) * 0.25;
        }
        //thread_slices = 10;
        action = FAST_JULIA;
        julia = true;
        
        this.boundary_tracing = boundary_tracing;
        
        int width = TOx - FROMx;
        int height = TOy - FROMy;
        QueueSize = (width + height) * 4;
        Queue = new int[QueueSize];
        Done = new int[width * height];
        
        FastJuliaData = new double[width * height];
        
        QueueHead = 0;
        QueueTail = 0;
        
        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, coefficients, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout, out_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
        }

        first_part_done = false;

    }

    //Color Cycling
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, BufferedImage image, int out_coloring_algorithm, int color_cycling_location) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.color_cycling_location = color_cycling_location;
        this.fractal_color = fractal_color;
        action = COLOR_CYCLING;
        first_part_done = false;
        color_cycling = true;
        
        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    }

    //Apply Filter
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int out_coloring_algorithm, int color_cycling_location, boolean[] filters) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.fractal_color = fractal_color;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.color_cycling_location = color_cycling_location;
        this.filters = filters;
        thread_slices = 10;
        action = APPLY_PALETTE_AND_FILTER;
        first_part_done = false;
        
        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        drawing_done = 0;

        progress = new ProgressChecker(ptr);

    }


    @Override
    public void run() {

        switch (action) {

            case NORMAL:
                draw();
                break;
            case FAST_JULIA:
                fastJuliaDraw();
                break;
            case COLOR_CYCLING:
                colorCycling();
                break;
            case APPLY_PALETTE_AND_FILTER:
                applyPaletteAndFilter();
                break;
            case JULIA_MAP:
                juliaMap();
                break;
                
        }
                      
    }

    protected abstract Color getDrawingColor(double result);

    protected abstract Color getDrawingColorSmooth(double result);

    private void draw() {

         int image_size = image.getHeight();

         if(julia) {
             if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                 if(filters[0]) {
                      drawJuliaSmoothAntialiased(image_size);
                 }
                 else {
                      drawJuliaSmooth(image_size);
                 }  
             }
             else {
                 if(filters[0]) {
                      drawJuliaNormalAntialiased(image_size);
                 }
                 else {
                      drawJuliaNormal(image_size);
                 }  
             }
         }
         else {
             if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
                 if(filters[0]) {
                     drawFractalSmoothAntialiased(image_size);
                 }
                 else {
                     drawFractalSmooth(image_size);
                 }     
             }
             else {
                 if(filters[0]) {
                     drawFractalNormalAntialiased(image_size);
                 }
                 else {
                     drawFractalNormal(image_size);
                 }                
             }
         }
         

         if(drawing_done != 0) {
             progress.update(drawing_done);
         }
         
         AntialiasingData = null;

         boolean whole_done_temp = false;
         
         synchronized(this) {
             
             first_part_done = true;

             whole_done_temp = ptr.isDrawingDone();
             
             ptr.setCalculated(calculated);
             
         }

         if(whole_done_temp) {     
             
             if(filters[5]) {
                 filterInvertColors();
             }
                       
             if(filters[1] && filters[6]) {
                 filterEdgeDetection();
             }
             else {
                 if(filters[1]) {
                     filterEdgeDetection();
                 }
                 else if(filters[6]) {
                     filterEdgeDetection2();
                 } 
             }
             
             if(filters[3]) {
                 filterSharpness();
             }
             
             if(filters[4] && filters[2]) {
                 filterEmbossColored();
             }
             else {
                 if(filters[2]) {
                     filterEmboss();
                 }
                 else if(filters[4]) {
                     filterEmbossColored();
                 } 
             }

            // if(filters[0]) {
                 //filterAntiAliasing();
             //}
           
                                     
             ptr.setOptions(true);
             ptr.setWholeImageDone(true);
             ptr.getMainPanel().repaint();
             ptr.getProgressBar().setValue((image_size * image_size) + (image_size *  image_size / 100));
             ptr.getProgressBar().setToolTipText(System.currentTimeMillis() - ptr.getCalculationTime() + " ms  # " + String.format("%6.2f", (ptr.getCalculated()) / (image_size * image_size) * 100) + "% Calculated.");
         }

         try {
             finalize();
         }
         catch (Throwable ex) {}
        
    }
    
    private void AddQueue(int p) {
               
        try {
            if((Done[p] & Queued) > 0) {
                return;
            }
        }
        catch(Exception ex) {
            return;
        }
        
        Done[p] |= Queued;
        Queue[QueueHead++] = p;
        
        if(QueueHead == QueueSize) {
            QueueHead = 0;
        }
    
    }
    
     private void ScanFractalNormal(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
                
        double center = LoadFractalNormal(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;

 
        boolean l = ll && LoadFractalNormal(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadFractalNormal(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadFractalNormal(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFractalNormal(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
    }
    
    private void ScanFractalNormalAntialiased(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadFractalNormalAntialiased(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadFractalNormalAntialiased(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadFractalNormalAntialiased(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadFractalNormalAntialiased(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFractalNormalAntialiased(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
    }
    
    private void ScanFractalSmooth(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadFractalSmooth(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadFractalSmooth(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadFractalSmooth(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadFractalSmooth(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFractalSmooth(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
    }
    
    private void ScanFractalSmoothAntialiased(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadFractalSmoothAntialiased(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadFractalSmoothAntialiased(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadFractalSmoothAntialiased(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadFractalSmoothAntialiased(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFractalSmoothAntialiased(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
    }
    
    private void ScanJuliaNormal(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadJuliaNormal(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadJuliaNormal(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadJuliaNormal(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadJuliaNormal(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadJuliaNormal(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
     }
    
     private void ScanJuliaNormalAntialiased(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadJuliaNormalAntialiased(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadJuliaNormalAntialiased(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadJuliaNormalAntialiased(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadJuliaNormalAntialiased(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadJuliaNormalAntialiased(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
     }
    
     private void ScanFastJuliaNormal(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadFastJuliaNormal(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadFastJuliaNormal(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadFastJuliaNormal(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadFastJuliaNormal(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFastJuliaNormal(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
     }
     
     private void ScanFastJuliaNormalAntialiased(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadFastJuliaNormalAntialiased(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadFastJuliaNormalAntialiased(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadFastJuliaNormalAntialiased(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadFastJuliaNormalAntialiased(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFastJuliaNormalAntialiased(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
     }
    
     private void ScanJuliaSmooth(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadJuliaSmooth(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadJuliaSmooth(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadJuliaSmooth(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadJuliaSmooth(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadJuliaSmooth(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
     }
     
     private void ScanJuliaSmoothAntialiased(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadJuliaSmoothAntialiased(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadJuliaSmoothAntialiased(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadJuliaSmoothAntialiased(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadJuliaSmoothAntialiased(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadJuliaSmoothAntialiased(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
     }
     
     private void ScanFastJuliaSmooth(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadFastJuliaSmooth(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadFastJuliaSmooth(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadFastJuliaSmooth(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadFastJuliaSmooth(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFastJuliaSmooth(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
     }
     
     private void ScanFastJuliaSmoothAntialiased(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yreal;
        
        int loc = y2 * image_size + x2;
        
        double center = LoadFastJuliaSmoothAntialiased(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yreal = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;
 
        boolean l = ll && LoadFastJuliaSmoothAntialiased(p-1, xreal - temp_size_image_size, yreal, loc - 1) != center;
        boolean r = rr && LoadFastJuliaSmoothAntialiased(p+1, xreal + temp_size_image_size, yreal, loc + 1) != center;
        boolean u = uu && LoadFastJuliaSmoothAntialiased(p-width, xreal, yreal - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFastJuliaSmoothAntialiased(p+width, xreal, yreal + temp_size_image_size, loc + image_size) != center;
        if(l) AddQueue(p-1);
        if(r) AddQueue(p+1);
        if(u) AddQueue(p-width);
        if(d) AddQueue(p+width);
        /* The corner pixels (nw,ne,sw,se) are also neighbors */
        if((uu&&ll)&&(l||u)) AddQueue(p-width-1);
        if((uu&&rr)&&(r||u)) AddQueue(p-width+1);
        if((dd&&ll)&&(l||d)) AddQueue(p+width-1);
        if((dd&&rr)&&(r||d)) AddQueue(p+width+1);
        
     }

     private double LoadFractalNormal(int p, double xreal, double yreal, int loc) {  
     
        //int x = p % width + FROMx, y = p / width + FROMy;
        
        //int loc = y * image_size + x;
        
        if((Done[p] & Loaded) > 0) {
            return image_iterations[loc];
        }
        
        double result = image_iterations[loc] = fractal.calculateFractal(new Complex(xreal, yreal));

        //image.setRGB(loc % image.getHeight(), loc / image.getHeight(), result == max_iterations ? fractal_color.getRGB() : getDrawingColor(result + color_cycling_location).getRGB());//demo
        //ptr.getMainPanel().repaint();//demo

        rgbs[loc] = result == max_iterations ? fractal_color.getRGB() : getDrawingColor(result + color_cycling_location).getRGB();
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return image_iterations[loc];
        
    }
     
    private double LoadFractalNormalAntialiased(int p, double xreal, double yreal, int loc) { 
        
        if((Done[p] & Loaded) > 0) {
            return AntialiasingData[p];
        }
        
   
        double result = image_iterations[loc] = fractal.calculateFractal(new Complex(xreal, yreal));
        
        double x_anti1, x_anti2, y_anti1, y_anti2;
        
        Color c0 = result == max_iterations ? fractal_color : getDrawingColor(result + color_cycling_location);
 
        double result1 = fractal.calculateFractal(new Complex(x_anti1 = (xreal - antialiasing_size), y_anti1 = (yreal - antialiasing_size)));
        double result2 = fractal.calculateFractal(new Complex(x_anti2 = (xreal + antialiasing_size), y_anti1));
        double result3 = fractal.calculateFractal(new Complex(x_anti2, y_anti2 = (yreal + antialiasing_size)));
        double result4 = fractal.calculateFractal(new Complex(x_anti1, y_anti2));

        Color c1 = result1 == max_iterations ? fractal_color : getDrawingColor(result1 + color_cycling_location);
        Color c2 = result2 == max_iterations ? fractal_color : getDrawingColor(result2 + color_cycling_location);
        Color c3 = result3 == max_iterations ? fractal_color : getDrawingColor(result3 + color_cycling_location);
        Color c4 = result4 == max_iterations ? fractal_color : getDrawingColor(result4 + color_cycling_location);
        
       
        // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
        int red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
        int green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
        int blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;

        
        rgbs[loc] = new Color(red, green, blue).getRGB();
        
        //image.setRGB(x, y, rgbs[loc]);//demo
        //ptr.getMainPanel().repaint();//demo
        
        AntialiasingData[p] = result == max_iterations ? (rgbs[loc] != fractal_color.getRGB() ? result + 101000 : result) : (rgbs[loc] != c0.getRGB() ? result + 101000 : result);
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return AntialiasingData[p];
        
    }
      
    private double LoadFractalSmooth(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return image_iterations[loc];
        }
        
        double result = image_iterations[loc] = fractal.calculateFractal(new Complex(xreal, yreal));

        //image.setRGB(x, y, result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(result + color_cycling_location).getRGB());//demo
        //ptr.getMainPanel().repaint();//demo
        
        rgbs[loc] = result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(result + color_cycling_location).getRGB();
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return image_iterations[loc];
        
    }
    
    private double LoadFractalSmoothAntialiased(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return AntialiasingData[p];
        }
   
        double result = image_iterations[loc] = fractal.calculateFractal(new Complex(xreal, yreal));
        
        double x_anti1, x_anti2, y_anti1, y_anti2;
        
        Color c0 = result == max_iterations ? fractal_color : getDrawingColorSmooth(result + color_cycling_location);

        double result1 = fractal.calculateFractal(new Complex(x_anti1 = (xreal - antialiasing_size), y_anti1 = (yreal - antialiasing_size)));
        double result2 = fractal.calculateFractal(new Complex(x_anti2 = (xreal + antialiasing_size), y_anti1));
        double result3 = fractal.calculateFractal(new Complex(x_anti2, y_anti2 = (yreal + antialiasing_size)));
        double result4 = fractal.calculateFractal(new Complex(x_anti1, y_anti2));

        Color c1 = result1 == max_iterations ? fractal_color : getDrawingColorSmooth(result1 + color_cycling_location);
        Color c2 = result2 == max_iterations ? fractal_color : getDrawingColorSmooth(result2 + color_cycling_location);
        Color c3 = result3 == max_iterations ? fractal_color : getDrawingColorSmooth(result3 + color_cycling_location);
        Color c4 = result4 == max_iterations ? fractal_color : getDrawingColorSmooth(result4 + color_cycling_location);
 
        // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
        int red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
        int green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
        int blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;

        rgbs[loc] = new Color(red, green, blue).getRGB();

        AntialiasingData[p] = result == max_iterations ? (rgbs[loc] != fractal_color.getRGB() ? result + 101000 : result) : (rgbs[loc] != c0.getRGB() ? result + 101000 : result);
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return AntialiasingData[p];
        
    }
    
    private double LoadJuliaNormal(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return image_iterations[loc];
        }
        
        double result = image_iterations[loc] = fractal.calculateJulia(new Complex(xreal, yreal));

        rgbs[loc] = result == max_iterations ? fractal_color.getRGB() : getDrawingColor(result + color_cycling_location).getRGB();
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return image_iterations[loc];
        
    }
    
    private double LoadJuliaNormalAntialiased(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return AntialiasingData[p];
        }
   
        double result = image_iterations[loc] = fractal.calculateJulia(new Complex(xreal, yreal));
        
        double x_anti1, x_anti2, y_anti1, y_anti2;
        
        Color c0 = result == max_iterations ? fractal_color : getDrawingColor(result + color_cycling_location);

        double result1 = fractal.calculateJulia(new Complex(x_anti1 = (xreal - antialiasing_size), y_anti1 = (yreal - antialiasing_size)));
        double result2 = fractal.calculateJulia(new Complex(x_anti2 = (xreal + antialiasing_size), y_anti1));
        double result3 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (yreal + antialiasing_size)));
        double result4 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));

        Color c1 = result1 == max_iterations ? fractal_color : getDrawingColor(result1 + color_cycling_location);
        Color c2 = result2 == max_iterations ? fractal_color : getDrawingColor(result2 + color_cycling_location);
        Color c3 = result3 == max_iterations ? fractal_color : getDrawingColor(result3 + color_cycling_location);
        Color c4 = result4 == max_iterations ? fractal_color : getDrawingColor(result4 + color_cycling_location);

        int red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
        int green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
        int blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;

        rgbs[loc] = new Color(red, green, blue).getRGB();

        AntialiasingData[p] = result == max_iterations ? (rgbs[loc] != fractal_color.getRGB() ? result + 101000 : result) : (rgbs[loc] != c0.getRGB() ? result + 101000 : result);
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return AntialiasingData[p];
        
    }
    
    private double LoadFastJuliaNormal(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return FastJuliaData[p];
        }

        double result = fractal.calculateJulia(new Complex(xreal, yreal));

        rgbs[loc] = result == max_iterations ? fractal_color.getRGB() : getDrawingColor(result + color_cycling_location).getRGB();

        Done[p] |= Loaded;
        
        return FastJuliaData[p] = result;
        
    }
    
    private double LoadFastJuliaNormalAntialiased(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return FastJuliaData[p];
        }
          
        double result = fractal.calculateJulia(new Complex(xreal, yreal));
        
        double x_anti1, x_anti2, y_anti1, y_anti2;
        
 
        Color c0 = result == max_iterations ? fractal_color : getDrawingColor(result + color_cycling_location);

        double result1 = fractal.calculateJulia(new Complex(x_anti1 = (xreal - antialiasing_size), y_anti1 = (yreal - antialiasing_size)));
        double result2 = fractal.calculateJulia(new Complex(x_anti2 = (xreal + antialiasing_size), y_anti1));
        double result3 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (yreal + antialiasing_size)));
        double result4 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));

        Color c1 = result1 == max_iterations ? fractal_color : getDrawingColor(result1 + color_cycling_location);
        Color c2 = result2 == max_iterations ? fractal_color : getDrawingColor(result2 + color_cycling_location);
        Color c3 = result3 == max_iterations ? fractal_color : getDrawingColor(result3 + color_cycling_location);
        Color c4 = result4 == max_iterations ? fractal_color : getDrawingColor(result4 + color_cycling_location);

        int red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
        int green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
        int blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;

        rgbs[loc] = new Color(red, green, blue).getRGB();
        
        FastJuliaData[p] = result == max_iterations ? (rgbs[loc] != fractal_color.getRGB() ? result + 101000 : result) : (rgbs[loc] != c0.getRGB() ? result + 101000 : result);

        Done[p] |= Loaded;
        
        return FastJuliaData[p];
        
    }
    
    
    private double LoadJuliaSmooth(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return image_iterations[loc];
        }
        
        double result = image_iterations[loc] = fractal.calculateJulia(new Complex(xreal, yreal));

        rgbs[loc] = result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(result + color_cycling_location).getRGB();
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return image_iterations[loc];
        
    }
    
    private double LoadJuliaSmoothAntialiased(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return AntialiasingData[p];
        }
   
        double result  = image_iterations[loc] = fractal.calculateJulia(new Complex(xreal, yreal));
        
        double x_anti1, x_anti2, y_anti1, y_anti2;
        
        Color c0 = result == max_iterations ? fractal_color : getDrawingColorSmooth(result + color_cycling_location);

        double result1 = fractal.calculateJulia(new Complex(x_anti1 = (xreal - antialiasing_size), y_anti1 = (yreal - antialiasing_size)));
        double result2 = fractal.calculateJulia(new Complex(x_anti2 = (xreal + antialiasing_size), y_anti1));
        double result3 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (yreal + antialiasing_size)));
        double result4 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));

        Color c1 = result1 == max_iterations ? fractal_color : getDrawingColorSmooth(result1 + color_cycling_location);
        Color c2 = result2 == max_iterations ? fractal_color : getDrawingColorSmooth(result2 + color_cycling_location);
        Color c3 = result3 == max_iterations ? fractal_color : getDrawingColorSmooth(result3 + color_cycling_location);
        Color c4 = result4 == max_iterations ? fractal_color : getDrawingColorSmooth(result4 + color_cycling_location);

        int red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
        int green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
        int blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;

        rgbs[loc] = new Color(red, green, blue).getRGB();
 
        AntialiasingData[p] = result == max_iterations ? (rgbs[loc] != fractal_color.getRGB() ? result + 101000 : result) : (rgbs[loc] != c0.getRGB() ? result + 101000 : result);
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return AntialiasingData[p];
        
    }
    
    private double LoadFastJuliaSmooth(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return FastJuliaData[p];
        }
        
        double result = fractal.calculateJulia(new Complex(xreal, yreal));

        rgbs[loc] = result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(result + color_cycling_location).getRGB();
        
        Done[p] |= Loaded;
        
        return FastJuliaData[p] = result;
        
    }
    
    private double LoadFastJuliaSmoothAntialiased(int p, double xreal, double yreal, int loc) {  
        
        if((Done[p] & Loaded) > 0) {
            return FastJuliaData[p];
        }
   
        double result = fractal.calculateJulia(new Complex(xreal, yreal));
        
        double x_anti1, x_anti2, y_anti1, y_anti2;

        Color c0 = result == max_iterations ? fractal_color : getDrawingColorSmooth(result + color_cycling_location);

        double result1 = fractal.calculateJulia(new Complex(x_anti1 = (xreal - antialiasing_size), y_anti1 = (yreal - antialiasing_size)));
        double result2 = fractal.calculateJulia(new Complex(x_anti2 = (xreal + antialiasing_size), y_anti1));
        double result3 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (yreal + antialiasing_size)));
        double result4 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));

        Color c1 = result1 == max_iterations ? fractal_color : getDrawingColorSmooth(result1 + color_cycling_location);
        Color c2 = result2 == max_iterations ? fractal_color : getDrawingColorSmooth(result2 + color_cycling_location);
        Color c3 = result3 == max_iterations ? fractal_color : getDrawingColorSmooth(result3 + color_cycling_location);
        Color c4 = result4 == max_iterations ? fractal_color : getDrawingColorSmooth(result4 + color_cycling_location);

        int red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
        int green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
        int blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;

        rgbs[loc] = new Color(red, green, blue).getRGB();
        
        FastJuliaData[p] = result == max_iterations ? (rgbs[loc] != fractal_color.getRGB() ? result + 101000 : result) : (rgbs[loc] != c0.getRGB() ? result + 101000 : result);
        
        Done[p] |= Loaded;
        
        return FastJuliaData[p];
        
    }
    
    private void drawFractalNormal(int image_size) {
 
         //ptr.setWholeImageDone(true); // demo
           
         double size = fractal.getSize();
        
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
                  
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                     temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
  

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             }
             
             calculated += drawing_done;
         }
         else {      
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int counter = 0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFractalNormal(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);
                
               // try {
                 //   Thread.sleep(1); //demo
                //}
               //catch (InterruptedException ex) {}

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             calculated += drawing_done;
             

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     //try {
                         if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                            rgbs[loc2 + 1] = rgbs[loc2];
                            image_iterations[loc2 + 1] = image_iterations[loc2];
                            Done[loc + 1] |= Loaded; 
                            drawing_done++;
                                //image.setRGB(x + FROMx + 1, (y + FROMy), rgbs[loc2]);//demo
                                //ptr.getMainPanel().repaint();//demo
                            
                         }
                     //}
                     //catch(Exception ex) {}
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }
             }
             
             
         }
 
         //OLD ALGORITHM
       
        /*int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         int total_drawing;
         
         double temp_starting_pixel_cicle;
         int temp_starting_pixel_color;
         
         int thread_size_width = TOx - FROMx;
         int thread_size_height = TOy - FROMy;              
                        
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
         double temp_result;
         int loc;
     
         for(int i = 0; i < thread_slices; i++) {
             slice_FROMy = FROMy + i * thread_size_height / thread_slices;
             slice_TOy = FROMy + (i + 1) * (thread_size_height) / thread_slices;
             for(int j = 0; j < thread_slices; j++) {
                 slice_FROMx =  FROMx + j * (thread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (thread_size_width) / thread_slices;  
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     x = slice_FROMx + step;
                     temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
                     
                     loc = y * image_size + x;
                             
                     temp_starting_pixel_cicle =  temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                     
                     rgbs[loc] = temp_starting_pixel_color = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                    
                     image.setRGB(x, y, rgbs[loc]);//demo
                     ptr.getMainPanel().repaint();//demo
                     try {
                         Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}
                     
                     drawing_done++;
                     total_drawing++;

                     for(x++, loc++; x < slice_TOx - step; x++, loc++) {
                         temp_x0 += temp_size_image_size;
                         
                         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                                           
                         if(temp_result == temp_starting_pixel_cicle) {
                             rgbs[loc] = temp_starting_pixel_color;
                             image.setRGB(x, y, rgbs[loc]);//demo
                             ptr.getMainPanel().repaint();//demo
                         }
                         else {
                             rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                             image.setRGB(x, y, rgbs[loc]);//demo
                             ptr.getMainPanel().repaint();//demo
                             whole_area = false;
                         }
                         
                         try {
                             Thread.sleep(1); //demo
                         }
                         catch (InterruptedException ex) {}
                 

                         drawing_done++;
                         total_drawing++;
                                        
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 += temp_size_image_size;//= temp_ycenter_size + temp_size_image_size * y;
                         loc = y * image_size + x;
                         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(temp_result == temp_starting_pixel_cicle) {
                             rgbs[loc] = temp_starting_pixel_color;
                             image.setRGB(x, y, rgbs[loc]);//demo
                             ptr.getMainPanel().repaint();//demo
                         }
                         else {
                             rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                             image.setRGB(x, y, rgbs[loc]);//demo
                             ptr.getMainPanel().repaint();//demo
                             whole_area = false;
                         }
                         
                         try {
                             Thread.sleep(1); //demo
                         }
                         catch (InterruptedException ex) {}
                 
                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
                         temp_x0 -= temp_size_image_size;//= temp_xcenter_size + temp_size_image_size * x;
                         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                

                         if(temp_result == temp_starting_pixel_cicle) {
                             rgbs[loc] = temp_starting_pixel_color;
                             image.setRGB(x, y, rgbs[loc]);//demo
                             ptr.getMainPanel().repaint();//demo
                         }
                         else {
                             rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                             image.setRGB(x, y, rgbs[loc]);//demo
                             ptr.getMainPanel().repaint();//demo
                             whole_area = false;
                         }
                         
                         try {
                             Thread.sleep(1); //demo
                         }
                         catch (InterruptedException ex) {}
                 
                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 -= temp_size_image_size;//= temp_ycenter_size + temp_size_image_size * y;
                         loc = y * image_size + x;
                         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(temp_result == temp_starting_pixel_cicle) {
                             rgbs[loc] = temp_starting_pixel_color;
                             image.setRGB(x, y, rgbs[loc]);//demo
                             ptr.getMainPanel().repaint();//demo
                         }
                         else {
                             rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                             image.setRGB(x, y, rgbs[loc]);//demo
                             ptr.getMainPanel().repaint();//demo
                             whole_area = false;
                         }
                 
                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
                     x++;


                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         calculated += drawing_done;
                         drawing_done = 0;
                     }
                     
                     
             
                     if(whole_area) {
                         int temp6 = step + 1;
                         int temp1 = slice_TOx - temp6;
                         int temp2 = slice_TOy - temp6;
  
                         int temp3;
                         int temp4; 
                         for(int k = y; k < temp2; k++) {
                             temp3 = k * image_size + x;
                             temp4 = k * image_size + temp1;
                             Arrays.fill(image_iterations, temp3, temp4, temp_starting_pixel_cicle);
                             Arrays.fill(rgbs, temp3, temp4, temp_starting_pixel_color);
                         }
   
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }
         
         calculated += drawing_done; */

    }
    
     private void drawFractalNormalAntialiased(int image_size) {
         
         //ptr.setWholeImageDone(true); // demo
         double size = fractal.getSize();
        
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         
                  
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             Color c0, c1, c2, c3, c4;
             
             int red, green, blue;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                     temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                     c0 = temp_result == max_iterations ? fractal_color : getDrawingColor(temp_result + color_cycling_location);
        
                     temp_result2 = fractal.calculateFractal(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                     temp_result3 = fractal.calculateFractal(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                     temp_result4 = fractal.calculateFractal(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                     temp_result5 = fractal.calculateFractal(new Complex(x_anti1, y_anti2));
                     
                     c1 = temp_result2 == max_iterations ? fractal_color : getDrawingColor(temp_result2 + color_cycling_location);
                     c2 = temp_result3 == max_iterations ? fractal_color : getDrawingColor(temp_result3 + color_cycling_location);
                     c3 = temp_result4 == max_iterations ? fractal_color : getDrawingColor(temp_result4 + color_cycling_location);
                     c4 = temp_result5 == max_iterations ? fractal_color : getDrawingColor(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                     red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
                     green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
                     blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;
    
                     rgbs[loc] = new Color(red, green, blue).getRGB();

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             }
             calculated += drawing_done;
         }
         else {      
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int counter = 0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFractalNormalAntialiased(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);
                
                //try {
                  //  Thread.sleep(1); //demo
                //}
               //catch (InterruptedException ex) {}

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             calculated += drawing_done;
             

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                        rgbs[loc2 + 1] = rgbs[loc2];
                        image_iterations[loc2 + 1] = image_iterations[loc2];
                        Done[loc + 1] |= Loaded; 
                        drawing_done++; 
                     }
                     
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }
             }
             
             
         }  

    }
   

    private void drawFractalSmooth(int image_size) {
         
         //ptr.setWholeImageDone(true);
         
         double size = fractal.getSize();
         
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {

                     temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
      
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             }
             calculated += drawing_done;
         }
         else {
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int counter = 0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFractalSmooth(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);
                
                // try {
                //     Thread.sleep(1); //demo
               // }
               // catch (InterruptedException ex) {}

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             calculated += drawing_done;

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                        rgbs[loc2 + 1] = rgbs[loc2];
                        image_iterations[loc2 + 1] = image_iterations[loc2];
                        Done[loc + 1] |= Loaded; 
                        drawing_done++; 
                     }
                     
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }
             }
         }
                
         /*int x = 0;
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
         double temp_result;
         int loc;
         
         
         for(int i = 0; i < thread_slices; i++) {
             slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
             slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
             for(int j = 0; j < thread_slices; j++) {
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;

                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     double temp_size_image_size2 = 0;
                     for(x = slice_FROMx + step, temp_x0 = temp_xcenter_size + temp_size_image_size * x, loc = y * image_size + x; x < slice_TOx - step; x++, loc++) {

                         temp_x0 += temp_size_image_size2;
                         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                         
                         temp_size_image_size2 = temp_size_image_size;
 
                         if(temp_result == max_iterations) {
                             rgbs[loc] = fractal_color.getRGB();
                         }
                         else {
                             rgbs[loc] = getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                             whole_area = false;
                         }
                 

                         drawing_done++;
                         total_drawing++;
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 += temp_size_image_size;
                         loc = y * image_size + x;
                         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(temp_result == max_iterations) {
                             rgbs[loc] = fractal_color.getRGB();
                         }
                         else {
                             rgbs[loc] = getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                             whole_area = false;
                         }
                 
                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
                         temp_x0 -= temp_size_image_size;
                         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                

                         if(temp_result == max_iterations) {
                             rgbs[loc] = fractal_color.getRGB();
                         }
                         else {
                             rgbs[loc] = getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                             whole_area = false;
                         }
                 
                         drawing_done++;
                         total_drawing++;
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 -= temp_size_image_size;
                         loc = y * image_size + x;
                         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 

                         if(temp_result == max_iterations) {
                             rgbs[loc] = fractal_color.getRGB();
                         }
                         else {
                             rgbs[loc] = getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                             whole_area = false;
                         }
                 
                         drawing_done++;
                         total_drawing++;
                     }
                     y++;
                     x++;
             

                     if(drawing_done / pixel_percent >= 1) {
                         progress.update(drawing_done);
                         drawing_done = 0;
                     }
             
                     if(whole_area) {  
                         int temp3;
                         int temp4;
                         for(int k = y; k < slice_TOy - step - 1; k++) {
                             temp3 = k * image_size + x;
                             temp4 = k * image_size + slice_TOx - step - 1;
                             Arrays.fill(image_iterations, temp3, temp4, max_iterations);
                             Arrays.fill(rgbs, temp3, temp4, fractal_color.getRGB());
                         }
                         progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                         break;
                     }
                 }
             }
         }*/

    }
    
    private void drawFractalSmoothAntialiased(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

            double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             Color c0, c1, c2, c3, c4;
             
             int red, green, blue;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                     temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                     c0 = temp_result == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result + color_cycling_location);
        
                     temp_result2 = fractal.calculateFractal(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                     temp_result3 = fractal.calculateFractal(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                     temp_result4 = fractal.calculateFractal(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                     temp_result5 = fractal.calculateFractal(new Complex(x_anti1, y_anti2));
                     
                     c1 = temp_result2 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result2 + color_cycling_location);
                     c2 = temp_result3 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result3 + color_cycling_location);
                     c3 = temp_result4 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result4 + color_cycling_location);
                     c4 = temp_result5 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                     red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
                     green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
                     blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;
    
                     rgbs[loc] = new Color(red, green, blue).getRGB();

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             }
             calculated += drawing_done;
         }
         else {
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int counter = 0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFractalSmoothAntialiased(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             calculated += drawing_done;

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                        rgbs[loc2 + 1] = rgbs[loc2];
                        image_iterations[loc2 + 1] = image_iterations[loc2];
                        Done[loc + 1] |= Loaded; 
                        drawing_done++; 
                     }
                     
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }
             }
         }

    }


    private void drawJuliaNormal(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {

                     temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             } 
             calculated += drawing_done;
         }
         else {        
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int counter = 0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanJuliaNormal(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             calculated += drawing_done;

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                        rgbs[loc2 + 1] = rgbs[loc2];
                        image_iterations[loc2 + 1] = image_iterations[loc2];
                        Done[loc + 1] |= Loaded; 
                        drawing_done++; 
                     }
                     
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }
             }
         }

    }
    
    private void drawJuliaNormalAntialiased(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             Color c0, c1, c2, c3, c4;
             
             int red, green, blue;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                     temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     c0 = temp_result == max_iterations ? fractal_color : getDrawingColor(temp_result + color_cycling_location);
        
                     temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                     temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                     temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                     temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                     c1 = temp_result2 == max_iterations ? fractal_color : getDrawingColor(temp_result2 + color_cycling_location);
                     c2 = temp_result3 == max_iterations ? fractal_color : getDrawingColor(temp_result3 + color_cycling_location);
                     c3 = temp_result4 == max_iterations ? fractal_color : getDrawingColor(temp_result4 + color_cycling_location);
                     c4 = temp_result5 == max_iterations ? fractal_color : getDrawingColor(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                     red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
                     green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
                     blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;
    
                     rgbs[loc] = new Color(red, green, blue).getRGB();

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             }   
             calculated += drawing_done;
         }
         else {        
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int counter = 0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanJuliaNormalAntialiased(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             calculated += drawing_done;

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                        rgbs[loc2 + 1] = rgbs[loc2];
                        image_iterations[loc2 + 1] = image_iterations[loc2];
                        Done[loc + 1] |= Loaded; 
                        drawing_done++; 
                     }
                     
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }
             }
         }

    }


    private void drawJuliaSmooth(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {

                     temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             } 
             calculated += drawing_done;
         }
         else {       
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int counter = 0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanJuliaSmooth(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             calculated += drawing_done;

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                        rgbs[loc2 + 1] = rgbs[loc2];
                        image_iterations[loc2 + 1] = image_iterations[loc2];
                        Done[loc + 1] |= Loaded; 
                        drawing_done++; 
                     }
                     
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }
             }
         }

    }
    
    private void drawJuliaSmoothAntialiased(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             Color c0, c1, c2, c3, c4;
             
             int red, green, blue;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                     temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     c0 = temp_result == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result + color_cycling_location);
        
                     temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                     temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                     temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                     temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                     c1 = temp_result2 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result2 + color_cycling_location);
                     c2 = temp_result3 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result3 + color_cycling_location);
                     c3 = temp_result4 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result4 + color_cycling_location);
                     c4 = temp_result5 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                     red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
                     green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
                     blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;
    
                     rgbs[loc] = new Color(red, green, blue).getRGB();

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             }
             calculated += drawing_done;
         }
         else {       
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int counter = 0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanJuliaSmoothAntialiased(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             calculated += drawing_done;

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                        rgbs[loc2 + 1] = rgbs[loc2];
                        image_iterations[loc2 + 1] = image_iterations[loc2];
                        Done[loc + 1] |= Loaded; 
                        drawing_done++; 
                     }
                     
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }
             }
         }

    }

    private void fastJuliaDraw() {

        int image_size = image.getHeight();

        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            if(fast_julia_filters && filters[0]) {
                drawFastJuliaSmoothAntialiased(image_size);
            }
            else {
                drawFastJuliaSmooth(image_size);
            } 
        }
        else {
            if(fast_julia_filters && filters[0]) {
                drawFastJuliaNormalAntialiased(image_size);
            }
            else {
                drawFastJuliaNormal(image_size);
            }
        }

        boolean whole_done_temp = false;

        synchronized(this) {

             first_part_done = true;

             whole_done_temp = ptr.isDrawingDone();

       }

       if(whole_done_temp) {
           
           if(fast_julia_filters) {
               
                 if(filters[5]) {
                     filterInvertColors();
                 }
                 
                 if(filters[1] && filters[6]) {
                     filterEdgeDetection();
                 }
                 else {
                     if(filters[1]) {
                         filterEdgeDetection();
                     }
                     else if(filters[6]) {
                         filterEdgeDetection2();
                     } 
                 }

                 if(filters[3]) {
                     filterSharpness();
                 }

                 if(filters[4] && filters[2]) {
                     filterEmbossColored();
                 }
                 else {
                     if(filters[2]) {
                         filterEmboss();
                     }
                     else if(filters[4]) {
                         filterEmbossColored();
                     } 
                 }

                 //if(filters[0]) {
                     //filterAntiAliasing();
                 //}
           }
           
           Graphics2D graphics = image.createGraphics();
           graphics.setColor(Color.BLACK);
           graphics.drawRect(0, 0, image_size - 2, image_size - 2);
           ptr.getMainPanel().getGraphics().drawImage(image, ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);
           
       }

       try {
            finalize();
       }
       catch (Throwable ex) {}


    }

    private void drawFastJuliaNormal(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                 }
                 temp_x0 = temp;
             } 
         }
         else {      
         
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFastJuliaNormal(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

             }


             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                         rgbs[loc2 + 1] = rgbs[loc2];
                         Done[loc + 1] |= Loaded;                        
                     }
                 }
             }   
         }
                 
         /*int x = 0;
         int y = 0;
         boolean whole_area;
         int step;
         double temp_x0 = 0;
         double temp_y0;
         
         double temp_starting_pixel_cicle;
         int temp_starting_pixel_color;
         
         int tread_size_width = TOx - FROMx;
         int tread_size_height = TOy - FROMy;
                               
         int slice_FROMx;
         int slice_FROMy;
         int slice_TOx;
         int slice_TOy;
         
         double temp_iteration = 0;
         
         
                 
         
         for(int i = 0; i < thread_slices; i++) {
             slice_FROMy = FROMy + i * (tread_size_height) / thread_slices;
             slice_TOy = FROMy + (i + 1) * (tread_size_height) / thread_slices;
             for(int j = 0; j < thread_slices; j++) {
                 slice_FROMx =  FROMx + j * (tread_size_width) / thread_slices;
                 slice_TOx = FROMx + (j + 1) * (tread_size_width) / thread_slices;
                 
                 
                 double temp = (slice_TOy - slice_FROMy + 1) / 2;
         
                 for(y = slice_FROMy, whole_area = true, step = 0; step < temp; step++, whole_area = true) {

                     temp_y0 = temp_ycenter_size + temp_size_image_size * y;           
             
                     x = slice_FROMx + step;
                     temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
                     
                     temp_starting_pixel_cicle = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     
                     rgbs[y * image_size + x] = temp_starting_pixel_color = temp_starting_pixel_cicle == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_starting_pixel_cicle + color_cycling_location).getRGB();
                     
       
                     for(x++; x < slice_TOx - step; x++) {

                         temp_x0 += temp_size_image_size;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));                        
                 
                         if(temp_iteration == temp_starting_pixel_cicle) {
                             rgbs[y * image_size + x] = temp_starting_pixel_color;
                         }
                         else {
                             rgbs[y * image_size + x] = temp_iteration == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_iteration + color_cycling_location).getRGB();
                             whole_area = false;
                         }               
                
                     }
             
                     for(x--, y++; y < slice_TOy - step; y++) {
                         temp_y0 += temp_size_image_size;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(temp_iteration == temp_starting_pixel_cicle) {
                             rgbs[y * image_size + x] = temp_starting_pixel_color;
                         }
                         else {
                             rgbs[y * image_size + x] = temp_iteration == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_iteration + color_cycling_location).getRGB();
                             whole_area = false;
                         }
                                          
                     }
             
                     for(y--, x--; x >= slice_FROMx + step; x--) {
                         temp_x0 -= temp_size_image_size;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                

                         if(temp_iteration == temp_starting_pixel_cicle) {
                             rgbs[y * image_size + x] = temp_starting_pixel_color;
                         }
                         else {
                             rgbs[y * image_size + x] = temp_iteration == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_iteration + color_cycling_location).getRGB();
                             whole_area = false;
                         }                 
  
                     }
             
                     for(x++, y--; y > slice_FROMy + step; y--) {
                         temp_y0 -= temp_size_image_size;
                         temp_iteration = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 

                         if(temp_iteration == temp_starting_pixel_cicle) {
                             rgbs[y * image_size + x] = temp_starting_pixel_color;
                         }
                         else {
                             rgbs[y * image_size + x] = temp_iteration == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_iteration + color_cycling_location).getRGB();
                             whole_area = false;
                         }                
                         
                     }
                     y++;
                     x++;
             
             
                     if(whole_area) {
                         int temp3 = step + 1;
                         int temp1 = slice_TOx - temp3;
                         int temp2 = slice_TOy - temp3;
                                                  
                         //full_image_g.setColor(new Color(temp_starting_pixel_color));
                         //full_image_g.fillRect(x, y, temp1 - x, temp2 - y);
                         

                         //int temp3 = x;
                         //int temp4 = temp1 - x;
                         //int temp5 = temp2 - y;
                                                  
                         //full_image_g.setColor(new Color(temp_starting_pixel_color));
                         //full_image_g.fillRect(temp3, y, temp4, temp5);
                         for(int k = y; k < temp2; k++) {
                             for(int l = x; l < temp1; l++) {
                                 rgbs[k * image_size + l] = temp_starting_pixel_color;
                             }
                         }
                         
                         break;
                     }
                 }
             }
         }*/

    }
    
    private void drawFastJuliaNormalAntialiased(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             Color c0, c1, c2, c3, c4;
             
             int red, green, blue;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                     temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     c0 = temp_result == max_iterations ? fractal_color : getDrawingColor(temp_result + color_cycling_location);
        
                     temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                     temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                     temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                     temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                     c1 = temp_result2 == max_iterations ? fractal_color : getDrawingColor(temp_result2 + color_cycling_location);
                     c2 = temp_result3 == max_iterations ? fractal_color : getDrawingColor(temp_result3 + color_cycling_location);
                     c3 = temp_result4 == max_iterations ? fractal_color : getDrawingColor(temp_result4 + color_cycling_location);
                     c4 = temp_result5 == max_iterations ? fractal_color : getDrawingColor(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                     red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
                     green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
                     blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;
    
                     rgbs[loc] = new Color(red, green, blue).getRGB();
                 }
                 temp_x0 = temp;
             } 
         }
         else {      
         
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFastJuliaNormalAntialiased(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

             }


             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                         rgbs[loc2 + 1] = rgbs[loc2];
                         Done[loc + 1] |= Loaded;                        
                     }
                 }
             } 
         }
    }

    private void drawFastJuliaSmooth(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                 }
                 temp_x0 = temp;
             } 
         }
         else {
               
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFastJuliaSmooth(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

             }

             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                         rgbs[loc2 + 1] = rgbs[loc2];
                         Done[loc + 1] |= Loaded;                        
                     }
                 }
             } 
          }
         
    }
    
    private void drawFastJuliaSmoothAntialiased(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;
         
         if(!boundary_tracing) {
             double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             Color c0, c1, c2, c3, c4;
             
             int red, green, blue;
             
             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                     temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                     c0 = temp_result == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result + color_cycling_location);
        
                     temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                     temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                     temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                     temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                     c1 = temp_result2 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result2 + color_cycling_location);
                     c2 = temp_result3 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result3 + color_cycling_location);
                     c3 = temp_result4 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result4 + color_cycling_location);
                     c4 = temp_result5 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                     red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
                     green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
                     blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;
    
                     rgbs[loc] = new Color(red, green, blue).getRGB();
                 }
                 temp_x0 = temp;
             } 
         }
         else {      
         
             int width = TOx - FROMx;
             int height = TOy - FROMy;

             for(int y = 0; y < width; y++) {
                AddQueue(y * width);
                AddQueue(y * width + (width - 1));
             }
             for(int x = 1; x < height - 1; x++) {
                AddQueue(x);
                AddQueue((height - 1) * width + x);
             }

             int flag=0;
             int p;
             while(QueueTail != QueueHead) {
                if(QueueHead <= QueueTail || (++flag & 3) > 0) {
                    p = Queue[QueueTail++];
                    if(QueueTail == QueueSize) {
                        QueueTail = 0;
                    }
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFastJuliaSmoothAntialiased(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);

             }


             for(int y = 0; y < width - 1; y++) {
                 for(int x = 0, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     if((Done[loc] & Loaded) > 0 && Done[loc + 1] == 0) {
                         rgbs[loc2 + 1] = rgbs[loc2];
                         Done[loc + 1] |= Loaded;                        
                     }
                 }
             } 
         }
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

         first_part_done = false;
         ptr.setWholeImageDone(false);

         int image_size = image.getHeight();
         
         color_cycling_location++;
        
         color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;
             
         double temp_result;
         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                 temp_result = image_iterations[loc];
                 rgbs[loc] =  temp_result == max_iterations? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
             }
         }
 
         first_part_done = true;
         ptr.setWholeImageDone(true);

         ptr.getMainPanel().repaint();

         try {
             Thread.sleep(200);
         }
         catch (InterruptedException ex) {}

         ColorCyclingNormal();

    }

    private void ColorCyclingSmooth() {

        if(!color_cycling) {

             return;

         }

         first_part_done = false;
         ptr.setWholeImageDone(false);
         
         int image_size = image.getHeight();

         color_cycling_location++;

         color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;

         double temp_result;
         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                 temp_result = image_iterations[loc];
                 rgbs[loc] =  temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
             }
         }


         first_part_done = true;
         ptr.setWholeImageDone(true);

         ptr.getMainPanel().repaint();

         try {
             Thread.sleep(140);
         }
         catch (InterruptedException ex) {}

         ColorCyclingSmooth();
        
    }
    
    private void applyPaletteAndFilter() {
        
         int image_size = image.getHeight();

          
         if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
             applyPaletteAndFilterSmooth(image_size);
         }
         else {
             applyPaletteAndFilterNormal(image_size);     
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
             
             if(filters[5]) {
                 filterInvertColors();
             }

             if(filters[1] && filters[6]) {
                 filterEdgeDetection();
             }
             else {
                 if(filters[1]) {
                     filterEdgeDetection();
                 }
                 else if(filters[6]) {
                     filterEdgeDetection2();
                 } 
             }
             
             if(filters[3]) {
                 filterSharpness();
             }
             
             if(filters[4] && filters[2]) {
                 filterEmbossColored();
             }
             else {
                 if(filters[2]) {
                     filterEmboss();
                 }
                 else if(filters[4]) {
                     filterEmbossColored();
                 } 
             }

             //if(filters[0]) {
                 //filterAntiAliasing();
             //}

             ptr.setOptions(true);
             ptr.setWholeImageDone(true);
             ptr.getMainPanel().repaint();
             ptr.getProgressBar().setValue((image_size * image_size) + (image_size *  image_size / 100));
             ptr.getProgressBar().setToolTipText(System.currentTimeMillis() - ptr.getCalculationTime() + " ms.");
         }


         try {
             finalize();
         }
         catch (Throwable ex) {}
        
    }


    private void applyPaletteAndFilterNormal(int image_size) {

         int pixel_percent = image_size *  image_size / 100;
         
         double temp_result;
         if(ptr.getJuliaMap()) {
             for(int y = FROMy; y < TOy; y++) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                     temp_result = image_iterations[loc];
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();

                     drawing_done++;
                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }

             }    
         }
         else {
             int x = 0;
             int y = 0;
             boolean whole_area;
             int step;
             int total_drawing;

             double temp_starting_pixel_cicle;
             int temp_starting_pixel_color;

             int thread_size_width = TOx - FROMx;
             int thread_size_height = TOy - FROMy;              

             int slice_FROMx;
             int slice_FROMy;
             int slice_TOx;
             int slice_TOy;
             
             int loc;

             for(int i = 0; i < thread_slices; i++) {
                 slice_FROMy = FROMy + i * (thread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (thread_size_height) / thread_slices;
                 for(int j = 0; j < thread_slices; j++) {
                     slice_FROMx =  FROMx + j * (thread_size_width) / thread_slices;
                     slice_TOx = FROMx + (j + 1) * (thread_size_width) / thread_slices;


                     double temp = (slice_TOy - slice_FROMy + 1) * 0.5;

                     for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {           

                         x = slice_FROMx + step;                        

                         loc = y * image_size + x;
                         temp_result = temp_starting_pixel_cicle = image_iterations[loc];

                         rgbs[loc] = temp_starting_pixel_color = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();

                         drawing_done++;
                         total_drawing++;

                         for(x++, loc = y * image_size + x; x < slice_TOx - step; x++, loc++) {
                             temp_result = image_iterations[loc];
                             if(temp_result == temp_starting_pixel_cicle) {
                                 rgbs[loc] = temp_starting_pixel_color;
                             }
                             else {
                                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                                 whole_area = false;
                             }

                             drawing_done++;
                             total_drawing++;

                         }

                         for(x--, y++; y < slice_TOy - step; y++) {
                             loc = y * image_size + x;
                             temp_result = image_iterations[loc];
                             if(temp_result == temp_starting_pixel_cicle) {
                                 rgbs[loc] = temp_starting_pixel_color;
                             }
                             else {
                                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                                 whole_area = false;
                             }

                             drawing_done++;
                             total_drawing++;
                         }

                         for(y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
                             temp_result = image_iterations[loc];
                             if(temp_result == temp_starting_pixel_cicle) {
                                 rgbs[loc] = temp_starting_pixel_color;
                             }
                             else {
                                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                                 whole_area = false;
                             }


                             drawing_done++;
                             total_drawing++;
                         }

                         for(x++, y--; y > slice_FROMy + step; y--) {
                             loc = y * image_size + x;
                             temp_result = image_iterations[loc];
                             if(temp_result == temp_starting_pixel_cicle) {
                                 rgbs[loc] = temp_starting_pixel_color;
                             }
                             else {
                                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();
                                 whole_area = false;
                             }

                             drawing_done++;
                             total_drawing++;
                         }
                         y++;
                         x++;


                         if(drawing_done / pixel_percent >= 1) {
                             progress.update(drawing_done);
                             drawing_done = 0;
                         }

                         if(whole_area) {                             
                             int temp6 = step + 1;
                             int temp1 = slice_TOx - temp6;
                             int temp2 = slice_TOy - temp6;
                      
                             for(int k = y; k < temp2; k++) {
                                 Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, temp_starting_pixel_color);
                             }

                             progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                             break;
                         }
                     }
                 }
             }
             
         }
                 
    }


    private void applyPaletteAndFilterSmooth(int image_size) {

         int pixel_percent = image_size *  image_size / 100;
         

         double temp_result;                    
         if(ptr.getJuliaMap()) {
             for(int y = FROMy; y < TOy; y++) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                     temp_result = image_iterations[loc];
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }

             }  
         }
         else {
             int x = 0;
             int y = 0;
             boolean whole_area;
             int step;
             int total_drawing;

             int thread_size_width = TOx - FROMx;
             int thread_size_height = TOy - FROMy;


             int slice_FROMx;
             int slice_FROMy;
             int slice_TOx;
             int slice_TOy;

             int loc;

             for(int i = 0; i < thread_slices; i++) {
                 slice_FROMy = FROMy + i * (thread_size_height) / thread_slices;
                 slice_TOy = FROMy + (i + 1) * (thread_size_height) / thread_slices;
                 for(int j = 0; j < thread_slices; j++) {
                     slice_FROMx =  FROMx + j * (thread_size_width) / thread_slices;
                     slice_TOx = FROMx + (j + 1) * (thread_size_width) / thread_slices;


                     double temp = (slice_TOy - slice_FROMy + 1) * 0.5;

                     for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {    

                         for(x = slice_FROMx + step, loc = y * image_size + x; x < slice_TOx - step; x++, loc++) {
                             temp_result = image_iterations[loc];
                             if(temp_result == max_iterations) {
                                 rgbs[loc] = fractal_color.getRGB();
                             }
                             else {
                                 rgbs[loc] = getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                                 whole_area = false;
                             }

                             drawing_done++;
                             total_drawing++;

                         }

                         for(x--, y++; y < slice_TOy - step; y++) {
                             loc = y * image_size + x;
                             temp_result = image_iterations[loc];
                             if(temp_result == max_iterations) {
                                 rgbs[loc] = fractal_color.getRGB();
                             }
                             else {
                                 rgbs[loc] = getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                                 whole_area = false;
                             }

                             drawing_done++;
                             total_drawing++;
                         }

                         for(y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
                             temp_result = image_iterations[loc];
                             if(temp_result == max_iterations) {
                                 rgbs[loc] = fractal_color.getRGB();
                             }
                             else {
                                 rgbs[loc] = getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                                 whole_area = false;
                             }

                             drawing_done++;
                             total_drawing++;
                         }

                         for(x++, y--; y > slice_FROMy + step; y--) {
                             loc = y * image_size + x;
                             temp_result = image_iterations[loc];
                             if(temp_result == max_iterations) {
                                 rgbs[loc] = fractal_color.getRGB();
                             }
                             else {
                                 rgbs[loc] = getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();
                                 whole_area = false;
                             }

                             drawing_done++;
                             total_drawing++;
                         }
                         y++;
                         x++;


                         if(drawing_done / pixel_percent >= 1) {
                             progress.update(drawing_done);
                             drawing_done = 0;
                         }

                         if(whole_area) {
                             int temp6 = step + 1;
                             int temp1 = slice_TOx - temp6;
                             int temp2 = slice_TOy - temp6;
                      
                             for(int k = y; k < temp2; k++) {
                                 Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, fractal_color.getRGB());
                             }

                             progress.update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                             break;
                         }
                     }
                 }
             }  
         }

    }
    
    private void juliaMap() {

        int image_size = image.getHeight();
         
        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            if(filters[0]) {
                juliaMapSmoothAntialiased(image_size);
            }
            else {
                juliaMapSmooth(image_size);
            } 
        }
        else {
            if(filters[0]) {
                juliaMapNormalAntialiased(image_size);
            }
            else {
                juliaMapNormal(image_size);
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
             
             if(filters[5]) {
                 filterInvertColors();
             }
             
             if(filters[1] && filters[6]) {
                 filterEdgeDetection();
             }
             else {
                 if(filters[1]) {
                     filterEdgeDetection();
                 }
                 else if(filters[6]) {
                     filterEdgeDetection2();
                 } 
             }
             
             if(filters[3]) {
                 filterSharpness();
             }
             
             if(filters[4] && filters[2]) {
                 filterEmbossColored();
             }
             else {
                 if(filters[2]) {
                     filterEmboss();
                 }
                 else if(filters[4]) {
                     filterEmbossColored();
                 } 
             }

             //if(filters[0]) {
                 //filterAntiAliasing();
             //}
           
                                     
             ptr.setOptions(true);
             ptr.setWholeImageDone(true);
             ptr.getMainPanel().repaint();
             ptr.getProgressBar().setValue((image_size * image_size) + (image_size *  image_size / 100));
             ptr.getProgressBar().setToolTipText(System.currentTimeMillis() - ptr.getCalculationTime() + " ms.");
         }

         try {
             finalize();
         }
         catch (Throwable ex) {}
        
    }
    
 
    private void juliaMapNormal(int image_size) {

         double size = fractal.getSize();
         
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / (TOx - FROMx);

         int pixel_percent = image_size *  image_size / 100;

         double temp_y0 = temp_ycenter_size;
         double temp_x0 = temp_xcenter_size;
         
         double temp_result;
         
         for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
             for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {

                 temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColor(temp_result + color_cycling_location).getRGB();

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }
             
             temp_x0 = temp_xcenter_size;

         }
        
    }
    
    private void juliaMapNormalAntialiased(int image_size) {

         double size = fractal.getSize();
         
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / (TOx - FROMx);

         int pixel_percent = image_size *  image_size / 100;

         double temp_y0 = temp_ycenter_size;
         double temp_x0 = temp_xcenter_size;
         
         double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
         double x_anti1, x_anti2;
         double y_anti1, y_anti2;
         Color c0, c1, c2, c3, c4;
             
         int red, green, blue;
             
         for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
             for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                 temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 c0 = temp_result == max_iterations ? fractal_color : getDrawingColor(temp_result + color_cycling_location);
        
                 temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                 temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                 temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                 temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                 c1 = temp_result2 == max_iterations ? fractal_color : getDrawingColor(temp_result2 + color_cycling_location);
                 c2 = temp_result3 == max_iterations ? fractal_color : getDrawingColor(temp_result3 + color_cycling_location);
                 c3 = temp_result4 == max_iterations ? fractal_color : getDrawingColor(temp_result4 + color_cycling_location);
                 c4 = temp_result5 == max_iterations ? fractal_color : getDrawingColor(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                 red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
                 green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
                 blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;
    
                 rgbs[loc] = new Color(red, green, blue).getRGB();

                 drawing_done++;
             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }
             
             temp_x0 = temp_xcenter_size;

         }
        
    }
    

    private void juliaMapSmooth(int image_size) {
        
         double size = fractal.getSize();
         
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / (TOx - FROMx);

         int pixel_percent = image_size *  image_size / 100;

         double temp_y0 = temp_ycenter_size;
         double temp_x0 = temp_xcenter_size;
         
         double temp_result;
         
         for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
             for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {

                 temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : getDrawingColorSmooth(temp_result + color_cycling_location).getRGB();

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }
             
             temp_x0 = temp_xcenter_size;

         }
        
    }
    
    private void juliaMapSmoothAntialiased(int image_size) {

         double size = fractal.getSize();
         
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / (TOx - FROMx);

         int pixel_percent = image_size *  image_size / 100;

         double temp_y0 = temp_ycenter_size;
         double temp_x0 = temp_xcenter_size;
         
         double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
         double x_anti1, x_anti2;
         double y_anti1, y_anti2;
         Color c0, c1, c2, c3, c4;
             
         int red, green, blue;
             
         for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
             for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                 temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 c0 = temp_result == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result + color_cycling_location);
        
                 temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                 temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                 temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                 temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                 c1 = temp_result2 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result2 + color_cycling_location);
                 c2 = temp_result3 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result3 + color_cycling_location);
                 c3 = temp_result4 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result4 + color_cycling_location);
                 c4 = temp_result5 == max_iterations ? fractal_color : getDrawingColorSmooth(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                 red = (c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 5;
                 green = (c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 5;
                 blue = (c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 5;
    
                 rgbs[loc] = new Color(red, green, blue).getRGB();

                 drawing_done++;
             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }
             
             temp_x0 = temp_xcenter_size;

         }
        
    }

    private void filterEmboss() {

       int image_size = image.getHeight();

       BufferedImage newSource = new BufferedImage (image_size, image_size, BufferedImage.TYPE_INT_RGB);
       
       int[] rgbs = new int[image_size * image_size];
       
       int[] raster = ((DataBufferInt)newSource.getRaster().getDataBuffer()).getData();

       for (int i = 0; i < image_size; i++) {
           for (int j = 0; j < image_size; j++) {
               int current = image.getRGB(j, i);

               int upperLeft = 0;
               if(i > 0 && j > 0) {
                   upperLeft = image.getRGB(j - 1, i - 1);
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
               rgbs[i * image_size + j] = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
               
           } 
       }

       System.arraycopy(rgbs, 0, raster, 0, rgbs.length);

       image.getGraphics().drawImage(newSource, 0, 0, image_size , image_size, null);

       newSource = null;
   

    }

    private void filterEdgeDetection() {

       /*float[] EDGES = {1.0f,   1.0f,  1.0f,
                         1.0f, -8.0f,  1.0f,
                         1.0f,   1.0f,  1.0f};*/
        
        /*float[] EDGES = {-7.0f,   -7.0f,  -7.0f,
                         -7.0f, 56.0f,  -7.0f,
                         -7.0f,   -7.0f,  -7.0f};*/

       /*float[] EDGES = {-1.0f, -1.0f, -2.0f, -1.0f, -1.0f,
                         -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
                         -2.0f, -4.0f, 44.0f, -4.0f, -2.0f,
                         -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
                         -1.0f, -1.0f, -2.0f, -1.0f, -1.0f};*/
        
       float[] EDGES = {-1.0f, -1.0f, -1.0f, -1.0f, -1.0f,
                         -1.0f, -2.0f, -2.0f, -2.0f, -1.0f,
                         -1.0f, -2.0f, 32.0f, -2.0f, -1.0f,
                         -1.0f, -2.0f, -2.0f, -2.0f, -1.0f,
                         -1.0f, -1.0f, -1.0f, -1.0f, -1.0f};
        
        
        
 
        int image_size = image.getHeight();

        Kernel kernel = new Kernel(5, 5, EDGES);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        BufferedImage newSource = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        BufferedImage newSource2 = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, 0, 0, image_size , image_size, null);
        
        Graphics2D graphics2 = newSource2.createGraphics();
        graphics2.drawImage(image, 0, 0, image_size , image_size, null);

        cop.filter(newSource, newSource2);
        
        int black = Color.BLACK.getRGB();
        
        /*for(int y = 0; y < image_size; y++) {
            for(int x = 0; x < image_size; x++) {
                if(newSource2.getRGB(x, y) != black) {
                    image.setRGB(x, y, newSource.getRGB(x, y));
                }
                else {
                    image.setRGB(x, y, black);
                }
            }
        }*/
        
        int[] raster = ((DataBufferInt)newSource2.getRaster().getDataBuffer()).getData();    
        
        for(int p = 0; p < image_size * image_size; p++) {
            if(convert_RGB_to_ARGB(raster[p]) == black) {
                rgbs[p] = black;
            }        
        }
        

        graphics.dispose();
        graphics = null;
        graphics2.dispose();
        graphics2 = null;
        EDGES = null;
        kernel = null;
        cop = null;
        newSource = null;
        newSource2 = null;

    }
    
    private void filterEdgeDetection2() {

       float[] EDGES = {-1.0f,   -1.0f,  -1.0f,
                         -1.0f, 8.0f,  -1.0f,
                         -1.0f,   -1.0f,  -1.0f};
        
           
 
        int image_size = image.getHeight();

        Kernel kernel = new Kernel(3, 3, EDGES);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        BufferedImage newSource = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        BufferedImage newSource2 = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, 0, 0, image_size , image_size, null);
        
        Graphics2D graphics2 = newSource2.createGraphics();
        graphics2.drawImage(image, 0, 0, image_size , image_size, null);

        cop.filter(newSource, newSource2);
        
        int black = Color.BLACK.getRGB();

        int[] raster = ((DataBufferInt)newSource2.getRaster().getDataBuffer()).getData();    
        
        for(int p = 0; p < image_size * image_size; p++) {
            if(convert_RGB_to_ARGB(raster[p]) == black) {
                rgbs[p] = black;
            }        
        }

        graphics.dispose();
        graphics = null;
        graphics2.dispose();
        graphics2 = null;
        EDGES = null;
        kernel = null;
        cop = null;
        newSource = null;
        newSource2 = null;

    }
    
    private void filterSharpness() {

       
        
         float[] SHARPNESS = {
            -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f,  3.4f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
         };


        int image_size = image.getHeight();
        
        int kernelWidth = (int)Math.sqrt((double)SHARPNESS.length);
        int kernelHeight = kernelWidth;
        int xOffset = (kernelWidth - 1) / 2;
        int yOffset = xOffset;

        BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, xOffset, yOffset, null);


        Kernel kernel = new Kernel(kernelWidth, kernelHeight, SHARPNESS);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(newSource, image);

        graphics.dispose();
        graphics = null;
        SHARPNESS = null;
        kernel = null;
        cop = null;
        newSource = null;

    }
    
    private void filterEmbossColored() {

       
        
        float[] EMBOSS = {
         1.0f,  0.0f,  0.0f,
         0.0f,  1.0f,  0.0f,
         0.0f,  0.0f,  -1.0f,
        };
         
         
        int image_size = image.getHeight();
        
        int kernelWidth = (int)Math.sqrt((double)EMBOSS.length);
        int kernelHeight = kernelWidth;
        int xOffset = (kernelWidth - 1) / 2;
        int yOffset = xOffset;

        BufferedImage newSource = new BufferedImage(image_size + kernelWidth - 1, image_size + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, xOffset, yOffset, null);


        Kernel kernel = new Kernel(kernelWidth, kernelHeight, EMBOSS);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(newSource, image);

        graphics.dispose();
        graphics = null;
        EMBOSS = null;
        kernel = null;
        cop = null;
        newSource = null;

    }

    private void  filterAntiAliasing() { //OLD antialiasing method (blurring)

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





        float e = 1.0f / 37184.0f;
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
                       e, e, e, e, e, e, e, e, e};
        
    

        /*float h = 1.0f / 64260344.0f;
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
                       h, h, h, h, h, h, h, h, h, h, h, h, h, h, h};*/


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
    
    private void filterInvertColors() {
        
        int image_size = image.getHeight();
        
        BufferedImage newSource = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D graphics = newSource.createGraphics();
        graphics.drawImage(image, 0, 0, image_size , image_size, null);
        
        int[] raster = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        int[] raster2 = ((DataBufferInt)newSource.getRaster().getDataBuffer()).getData();
        
       
        for(int p = 0; p < image_size * image_size; p++) {
            raster[p] = ~convert_ARGB_to_RGB(raster2[p]);
        }
  
        
        graphics.dispose();
        graphics = null;
        newSource = null;
  
    }

    public void setColorCycling(boolean temp) {

        color_cycling = temp;

    }
   

    public int getColorCyclingLocation() {

        return color_cycling_location;

    }
 

    public boolean isFirstPartDone() {

        return first_part_done;
        
    }
    
    private int convert_RGB_to_ARGB(int rgb) {

        int r = (rgb >> 16) & 0xFF;

        int g = (rgb >> 8) & 0xFF;

        int b = rgb & 0xFF;

        return 0xff000000 | (r << 16) | (g << 8) | b;

    }
    
   private int convert_ARGB_to_RGB(int argb) {

        //int a = (argb >> 24) & 0xFF;

        int r = (argb >> 16) & 0xFF;

        int g = (argb >> 8) & 0xFF;

        int b = argb & 0xFF;

        return (r << 16) | (g << 8) | b;

    }
    
    public static void setArrays(int image_size) {
 
        image_iterations = new double[image_size * image_size];
        
    }
  
}
