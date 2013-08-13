package fractalzoomer.core;

import fractalzoomer.main.MainWindow;
import fractalzoomer.functions.barnsley.Barnsley1;
import fractalzoomer.functions.barnsley.Barnsley3;
import fractalzoomer.functions.barnsley.Barnsley2;
import fractalzoomer.functions.math.Cot;
import fractalzoomer.functions.math.Cosh;
import fractalzoomer.functions.math.Cos;
import fractalzoomer.functions.math.Coth;
import fractalzoomer.functions.math.Exp;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula10;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula11;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula12;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula13;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula15;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula16;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula17;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula18;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.Formula14;
import fractalzoomer.functions.formulas.kaliset.Formula19;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula2;
import fractalzoomer.functions.formulas.kaliset.Formula20;
import fractalzoomer.functions.formulas.kaliset.Formula21;
import fractalzoomer.functions.formulas.kaliset.Formula22;
import fractalzoomer.functions.formulas.kaliset.Formula24;
import fractalzoomer.functions.formulas.kaliset.Formula25;
import fractalzoomer.functions.formulas.kaliset.Formula26;
import fractalzoomer.functions.formulas.kaliset.Formula23;
import fractalzoomer.functions.formulas.m_like_generalization.Formula1;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula3;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula4;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula5;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula6;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula7;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula8;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula9;
import fractalzoomer.functions.general.FrothyBasin;
import fractalzoomer.functions.halley.HalleySin;
import fractalzoomer.functions.halley.Halley4;
import fractalzoomer.functions.halley.HalleyPoly;
import fractalzoomer.functions.halley.HalleyGeneralized8;
import fractalzoomer.functions.householder.HouseholderGeneralized8;
import fractalzoomer.functions.halley.HalleyCos;
import fractalzoomer.functions.halley.HalleyGeneralized3;
import fractalzoomer.functions.halley.Halley3;
import fractalzoomer.functions.householder.Householder3;
import fractalzoomer.functions.householder.HouseholderSin;
import fractalzoomer.functions.householder.HouseholderCos;
import fractalzoomer.functions.householder.Householder4;
import fractalzoomer.functions.householder.HouseholderPoly;
import fractalzoomer.functions.householder.HouseholderGeneralized3;
import fractalzoomer.functions.general.Lambda;
import fractalzoomer.functions.math.Log;
import fractalzoomer.functions.magnet.Magnet1;
import fractalzoomer.functions.magnet.Magnet2;
import fractalzoomer.functions.mandelbrot.MandelbrotFifth;
import fractalzoomer.functions.mandelbrot.MandelbrotNth;
import fractalzoomer.functions.general.Manowar;
import fractalzoomer.functions.mandelbrot.MandelbrotSeventh;
import fractalzoomer.functions.mandelbrot.MandelbrotFourth;
import fractalzoomer.functions.mandelbrot.Mandelbar;
import fractalzoomer.functions.mandelbrot.MandelbrotNinth;
import fractalzoomer.functions.mandelbrot.MandelbrotPoly;
import fractalzoomer.functions.mandelbrot.MandelbrotEighth;
import fractalzoomer.functions.mandelbrot.MandelbrotSixth;
import fractalzoomer.functions.mandelbrot.Mandelbrot;
import fractalzoomer.functions.mandelbrot.MandelbrotTenth;
import fractalzoomer.functions.mandelbrot.MandelbrotCubed;
import fractalzoomer.functions.newton.NewtonCos;
import fractalzoomer.functions.newton.NewtonGeneralized3;
import fractalzoomer.functions.newton.Newton3;
import fractalzoomer.functions.newton.NewtonGeneralized8;
import fractalzoomer.functions.newton.NewtonPoly;
import fractalzoomer.functions.general.Phoenix;
import fractalzoomer.functions.newton.NewtonSin;
import fractalzoomer.functions.newton.Newton4;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.functions.general.Spider;
import fractalzoomer.functions.schroder.Schroder4;
import fractalzoomer.functions.schroder.SchroderGeneralized3;
import fractalzoomer.functions.schroder.SchroderSin;
import fractalzoomer.functions.schroder.Schroder3;
import fractalzoomer.functions.schroder.SchroderPoly;
import fractalzoomer.functions.schroder.SchroderCos;
import fractalzoomer.functions.schroder.SchroderGeneralized8;
import fractalzoomer.functions.general.SierpinskiGasket;
import fractalzoomer.functions.mandelbrot.MandelbrotWth;
import fractalzoomer.functions.math.Sinh;
import fractalzoomer.functions.math.Sin;
import fractalzoomer.functions.math.Tan;
import fractalzoomer.functions.math.Tanh;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly1;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferInt;
import java.awt.image.Kernel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class ThreadDraw extends Thread {
  public static final int NORMAL = 0;
  public static final int FAST_JULIA = 1;
  public static final int COLOR_CYCLING = 2;
  public static final int APPLY_PALETTE_AND_FILTER = 3;
  public static final int JULIA_MAP = 4;
  protected static double[] image_iterations;
  protected static AtomicInteger synchronization;
  protected static AtomicInteger total_calculated;
  protected static AtomicInteger normal_drawing_algorithm_pixel;
  protected int [] rgbs;
  protected Fractal fractal;
  protected PaletteColor palette_color;
  protected int FROMx;
  protected int TOx;
  protected int FROMy;
  protected int TOy;
  protected boolean[] filters;
  protected int out_coloring_algorithm;
  protected boolean julia;
  protected boolean fast_julia_filters;
  protected boolean boundary_tracing;
  protected MainWindow ptr;
  protected BufferedImage image;
  protected int drawing_done;
  protected int thread_calculated;
  protected Color fractal_color;
  protected ProgressChecker progress;
  protected int max_iterations;
  protected boolean color_cycling;
  protected int color_cycling_location;
  protected int action;
  protected int thread_slices;
  protected double antialiasing_size;
  /*protected double[] AntialiasingData;
  protected double[] FastJuliaData;
  protected int[] Done;
  protected int QueueSize;
  protected int[] Queue;
  protected int QueueHead;
  protected int QueueTail;
  public static final int Loaded = 1, Queued = 2;*/
  
  
  
    static {

       synchronization = new AtomicInteger(0);
       total_calculated = new AtomicInteger(0);
       normal_drawing_algorithm_pixel = new AtomicInteger(0);
       image_iterations = new double[MainWindow.image_size * MainWindow.image_size];
               
    }

    //Fractal
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, int bailout_test_algorithm, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, double[] coefficients) {

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
        
        /*if(boundary_tracing) {
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
        }*/
        
        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship, z_exponent);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship, z_exponent_complex);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, burning_ship, coefficients);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.NEWTON3:
                fractal = new Newton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTON4:
                fractal = new Newton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                fractal = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                fractal = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTONSIN:
                fractal = new NewtonSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTONCOS:
                fractal = new NewtonCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.NEWTONPOLY:
                fractal = new NewtonPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals, coefficients);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.SIERPINSKI_GASKET:
                fractal = new SierpinskiGasket(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEY3:
                fractal = new Halley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEY4:
                fractal = new Halley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEYGENERALIZED3:
                fractal = new HalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEYGENERALIZED8:
                fractal = new HalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEYSIN:
                fractal = new HalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEYCOS:
                fractal = new HalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HALLEYPOLY:
                fractal = new HalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals, coefficients);
                break;
            case MainWindow.SCHRODER3:
                fractal = new Schroder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODER4:
                fractal = new Schroder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODERGENERALIZED3:
                fractal = new SchroderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODERGENERALIZED8:
                fractal = new SchroderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODERSIN:
                fractal = new SchroderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODERCOS:
                fractal = new SchroderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.SCHRODERPOLY:
                fractal = new SchroderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals, coefficients);
                break;
            case MainWindow.HOUSEHOLDER3:
                fractal = new Householder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDER4:
                fractal = new Householder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED3:
                fractal = new HouseholderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED8:
                fractal = new HouseholderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDERSIN:
                fractal = new HouseholderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDERCOS:
                fractal = new HouseholderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals);
                break;
            case MainWindow.HOUSEHOLDERPOLY:
                fractal = new HouseholderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, in_coloring_algorithm, plane_type, rotation_vals, coefficients);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals);
                break;

        }

        drawing_done = 0;
        thread_calculated = 0;
        
        progress = new ProgressChecker(ptr);

    }

    //Julia
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

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
 
        
        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent_complex, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, coefficients, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
             case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
                
        }
                        
        drawing_done = 0;
        thread_calculated = 0;

        progress = new ProgressChecker(ptr);
       
    }
    
    //Julia Map
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] coefficients) {
        
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
                fractal = new Mandelbrot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent_complex, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, coefficients, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(0.5, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
                
        }
        
        drawing_done = 0;

        progress = new ProgressChecker(ptr);
        
    }

    //Julia Preview
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, int out_coloring_algorithm, int in_coloring_algorithm, boolean[] filters, boolean burning_ship, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

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
        
        
        switch (function) {
            case 0:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 1:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 2:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 3:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 4:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 5:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 6:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 7:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case 8:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, z_exponent_complex, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, burning_ship, coefficients, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type, rotation_vals, xJuliaCenter, yJuliaCenter);
                break;
                
        }

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
                drawJuliaMap();
                break;
                
        }
                      
    }
  

    private void draw() {

         int image_size = image.getHeight();

         if(julia) {
             if(filters[0]) {
                  drawJuliaAntialiased(image_size);
             }
             else {
                  drawJulia(image_size);
             }  
         }
         else {
             if(filters[0]) {
                 drawFractalAntialiased(image_size);
             }
             else {
                 drawFractal(image_size);
             }                
         }
         

         if(drawing_done != 0) {
             progress.update(drawing_done);
         }
         
         int done = synchronization.incrementAndGet();
         
         total_calculated.addAndGet(thread_calculated);

         if(done == ptr.getNumberOfThreads()) {     
             
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
             ptr.getProgressBar().setToolTipText(System.currentTimeMillis() - ptr.getCalculationTime() + " ms  # " + String.format("%6.2f", ((double)total_calculated.get()) / (image_size * image_size) * 100) + "% Calculated.");
         }
    }
    
   /* private void AddQueue(int p) {
               
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
        
        QueueHead %= QueueSize;

    }
    
     private void ScanFractal(int p, int image_size, int width, int height, double temp_size_image_size, double temp_xcenter_size, double temp_ycenter_size) {
        
        int x = p % width, y = p / width;
        int x2 = x + FROMx;
        int y2 = y + FROMy;
        
        double xreal;
        double yimag;
        
        int loc = y2 * image_size + x2;
                
        double center = LoadFractal(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yimag = temp_ycenter_size + y2 * temp_size_image_size, loc);
        boolean ll = x >= 1, rr = x < width - 1;
        boolean uu = y >= 1, dd = y < height - 1;

        int p1 = p - 1;
        int p2 = p + 1;
        int p3 = p - width;
        int p4 = p + width;
        
        boolean l = ll && LoadFractal(p1, xreal - temp_size_image_size, yimag, loc - 1) != center;
        boolean r = rr && LoadFractal(p2, xreal + temp_size_image_size, yimag, loc + 1) != center;
        boolean u = uu && LoadFractal(p3, xreal, yimag - temp_size_image_size, loc - image_size) != center;
        boolean d = dd && LoadFractal(p4, xreal, yimag + temp_size_image_size, loc + image_size) != center;
        
        if(l) AddQueue(p1);
        if(r) AddQueue(p2);
        if(u) AddQueue(p3);
        if(d) AddQueue(p4);
        
        // The corner pixels (nw,ne,sw,se) are also neighbors 
        if((uu&&ll)&&(l||u)) AddQueue(p3 - 1);
        if((uu&&rr)&&(r||u)) AddQueue(p3 + 1);
        if((dd&&ll)&&(l||d)) AddQueue(p4 - 1);
        if((dd&&rr)&&(r||d)) AddQueue(p4 + 1);
        
    }
    
         
     private double LoadFractal(int p, double xreal, double yimag, int loc) {  
     
        //int x = p % width + FROMx, y = p / width + FROMy;
        
        //int loc = y * image_size + x;
        
        if((Done[p] & Loaded) > 0) {
            return image_iterations[loc];
        }
        
        double result = image_iterations[loc] = fractal.calculateFractal(new Complex(xreal, yimag));

        //image.setRGB(loc % image.getHeight(), loc / image.getHeight(), result == max_iterations ? fractal_color.getRGB() : getPaletteColor(result + color_cycling_location).getRGB());//demo
        //ptr.getMainPanel().repaint();//demo

        rgbs[loc] = result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(result + color_cycling_location).getRGB();
        
        drawing_done++;
        
        Done[p] |= Loaded;
        
        return image_iterations[loc];
        
    }
     
    */
        
    private void drawFractal(int image_size) {
 
        // ptr.setWholeImageDone(true); // demo

         double size = fractal.getSize();
        
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
    
         
         
        if(!boundary_tracing) {
            
             //Better brute force
             double temp_result;
             
             int x, y, loc, counter = 0;
             
             int condition = image_size * image_size;
             
             do {
                
                 loc = normal_drawing_algorithm_pixel.getAndIncrement();

                 if(loc >= condition) {
                     break;
                 }      

                 x = loc % image_size;
                 y = loc / image_size;

                 temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_xcenter_size + temp_size_image_size * x, temp_ycenter_size + temp_size_image_size * y));
                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
  

                 drawing_done++;
                 counter++;
                 
                 if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     thread_calculated += drawing_done;
                     drawing_done = 0;
                 }
    
             } while(true);
             
             /*
              int tile = 6;
              int image_size_tile = image_size / tile;
              condition = (image_size_tile) * (image_size_tile);
              
              int color, loc2;
             
              do {
                
                 loc = normal_drawing_algorithm_pixel.getAndIncrement();

                 if(loc >= condition) {
                     break;
                 }      

                 x = (loc % image_size_tile) * tile;
                 y = (loc / image_size_tile) * tile;

                 loc2 = y * image_size + x;
                 temp_result = image_iterations[loc2] = fractal.calculateFractal(new Complex(temp_xcenter_size + temp_size_image_size * x, temp_ycenter_size + temp_size_image_size * y));
                 color = rgbs[loc2] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
                 
                 for(int i = y; i < y + tile; i++) {
                     for(int j = x, loc3 = i * image_size + j; j < x + tile; j++, loc3++) {
                         image_iterations[loc3] = temp_result;
                         rgbs[loc3] = color;
                          
                     }
                 }
                 
                
    
             } while(true);*/
             
             thread_calculated += drawing_done;
             
             
             //Brute force
             /*double temp = temp_xcenter_size + temp_size_image_size * FROMx;
             double temp_y0 = temp_ycenter_size + temp_size_image_size * FROMy;
             double temp_x0 = temp;

             for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
                     
                     temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
  

                     drawing_done++;

                 }

                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     thread_calculated += drawing_done;
                     drawing_done = 0;
                 }

                 temp_x0 = temp;

             }
             
             thread_calculated += drawing_done;*/
  
         }
         else {
             double temp_result;
      
             final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;
          
             double temp_x0, temp_y0;
             //double delX[] = {temp_size_image_size, 0., -temp_size_image_size, 0.};
             //double delY[] = {0., temp_size_image_size, 0., -temp_size_image_size};
             
             
             int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
             int delPix[] = {1 , image_size, -1, -image_size};
             //double curX, curY; 
             double nextX, nextY, previous_value;
          
             int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
             int intX[] = {1, 0, -1, 0};
             int intY[] = {0, 1, 0, -1};
             
            
             
             for(y = FROMy;  y < TOy; y++) {
                 temp_y0 = temp_ycenter_size + y * temp_size_image_size;
                 for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {
          
                     if(rgbs[pix] == culcColor) {
                         temp_x0 = temp_xcenter_size + x * temp_size_image_size;
                         //curX = temp_x0;
                         //curY = temp_y0;
                         curPix = startPix = pix;     
                         curDir = dirRight;
                         ix = x;
                         iy = y;
                    
                         temp_result = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                         startColor = rgbs[pix] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
                         drawing_done++;
                         thread_calculated++;
                         /*ptr.getMainPanel().repaint();
                         try {
                            Thread.sleep(1); //demo
                         }
                         catch (InterruptedException ex) {}*/
                         
                         while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor){   // looking for boundary
                             curPix = startPix = startPix - image_size;
                             iy--;
                             //curY = temp_ycenter_size + iy * temp_size_image_size;
                         }
                    
                         temp_ix = ix;
                         temp_iy = iy;
                         
                         do {                                            // tracing cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                            
                                 //nextX = curX + delX[dir]; 
                                 //nextY = curY + delY[dir];
                                 
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
                                 
                                 nextX = temp_xcenter_size + next_ix * temp_size_image_size;
                                 nextY = temp_ycenter_size + next_iy * temp_size_image_size;
                                                             
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }
                                
                                 if((nextColor = rgbs[nextPix]) == culcColor)  {
                                     temp_result = image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                     nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
                                     drawing_done++;
                                     thread_calculated++;
                                     /*ptr.getMainPanel().repaint();
                                     try {
                                        Thread.sleep(1); //demo
                                     }
                                     catch (InterruptedException ex) {}*/
                                 }
                              
                            
                                 if(nextColor == startColor ) {
                                     curDir = dir;    
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     //curX = nextX;  
                                     //curY = nextY;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);

                    
                         curDir = dirRight;
        
                         
                         do {                                                 // 2nd cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                                 
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
       
                                 
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }

                                 if(rgbs[nextPix] == startColor) {           // flooding  
                                     curDir = dir;
                                     if(dir == dirUP) {
                                         floodPix = curPix;
                                         flood_ix = temp_ix;                                       
                                         
                                         while(true) {
                                             flood_ix++;
                                                                                   
                                             if(flood_ix >= TOx) {
                                                 break;
                                             }
                                             
                                             previous_value = image_iterations[floodPix];
                                             floodPix++;
                                             
                                             if((floodColor = rgbs[floodPix]) == culcColor) {
                                                 drawing_done++;
                                                 rgbs[floodPix] = startColor;
                                                 image_iterations[floodPix] = previous_value;
                                                 /*ptr.getMainPanel().repaint();
                                                 try {
                                                     Thread.sleep(1); //demo
                                                 }
                                                 catch (InterruptedException ex) {}*/
                                             }
                                             else if(floodColor != startColor) {
                                                 break;
                                             }
                   
                                         }
                                         
                                         if(drawing_done / pixel_percent >= 1) {
                                             progress.update(drawing_done);
                                             drawing_done = 0;
                                         }
                                     }
                                
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);
 
                     }
                 }
             
                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }     
             }
          
   
            
             //Boundary Tracing Version 1
             /*
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
                    QueueTail %= QueueSize;
                } 
                else {
                    p = Queue[--QueueHead];
                }

                ScanFractal(p, image_size, width, height, temp_size_image_size, temp_xcenter_size, temp_ycenter_size);
                
               // try {
                 //   Thread.sleep(1); //demo
                //}
               //catch (InterruptedException ex) {}

                counter++;

                if(counter % height == 0 && drawing_done / pixel_percent >= 1) {
                    progress.update(drawing_done);
                    thread_calculated += drawing_done;
                    drawing_done = 0;
                }

             }
             
             thread_calculated += drawing_done;
             

             for(int y = 1; y < width - 1; y++) {
                 for(int x = 1, loc = y * width + x, loc2 = (y + FROMy) * image_size + x + FROMx; x < height - 1; x++, loc++, loc2++) {
                     //try {
                         if(Done[loc + 1] == 0 && (Done[loc] & Loaded) > 0) {
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
             }*/
             
             
         }
 
         //Solid Guessing algorithm
       
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
                     
                     rgbs[loc] = temp_starting_pixel_color = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
                    
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
                             rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
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
                             rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
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
                             rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
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
                             rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
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
                         thread_calculated += drawing_done;
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
         
         thread_calculated += drawing_done; */

    }
    
     private void drawFractalAntialiased(int image_size) {
         
         //ptr.setWholeImageDone(true); // demo
         double size = fractal.getSize();
        
         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;

         
                  
         if(!boundary_tracing) {
             
             //better Brute force with antialiasing
             int x, y, loc, counter = 0;
             Color c0, c1, c2, c3, c4;
             
             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double temp_x0, temp_y0;
             
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             
             int red, green, blue;
             
             int condition = image_size * image_size;
             
             do {
                
                 loc = normal_drawing_algorithm_pixel.getAndIncrement();

                 if(loc >= condition) {
                     break;
                 }

                 x = loc % image_size;
                 y = loc / image_size;

                 temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                 temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                 
                 temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                 c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
        
                 temp_result2 = fractal.calculateFractal(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                 temp_result3 = fractal.calculateFractal(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                 temp_result4 = fractal.calculateFractal(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                 temp_result5 = fractal.calculateFractal(new Complex(x_anti1, y_anti2));
                     
                 c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                 c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                 c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                 c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                 red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                 green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                 blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);
    
                 rgbs[loc] = new Color(red, green, blue).getRGB();
  
                 drawing_done++;
                 counter++;
                 
                 if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     thread_calculated += drawing_done;
                     drawing_done = 0;
                 }
    
             } while(true);
             
             thread_calculated += drawing_done;
        
         }
         else {    
             
             Color c0, c1, c2, c3, c4;
             
             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             
             int red, green, blue;
      
             final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;
          
             double temp_x0, temp_y0;           
             
             int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
             int delPix[] = {1 , image_size, -1, -image_size};
             double nextX, nextY, previous_value;
          
             int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
             int intX[] = {1, 0, -1, 0};
             int intY[] = {0, 1, 0, -1};
         
     
          
             for(y = FROMy; y < TOy; y++) {
                 temp_y0 = temp_ycenter_size + y * temp_size_image_size;
                 for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {
          
                     if(rgbs[pix] == culcColor) {
                         temp_x0 = temp_xcenter_size + x * temp_size_image_size;
                         curPix = startPix = pix;     
                         curDir = dirRight;
                         ix = x;
                         iy = y;
                    
                         temp_result = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                         c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                         
                         temp_result2 = fractal.calculateFractal(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                         temp_result3 = fractal.calculateFractal(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                         temp_result4 = fractal.calculateFractal(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                         temp_result5 = fractal.calculateFractal(new Complex(x_anti1, y_anti2));

                         c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                         c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                         c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                         c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                              // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                         red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                         green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                         blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);
    
                         startColor = rgbs[pix] = new Color(red, green, blue).getRGB();
                         
                         drawing_done++;
                         thread_calculated++;
                         /*ptr.getMainPanel().repaint();
                         try {
                            Thread.sleep(1); //demo
                         }
                         catch (InterruptedException ex) {}*/
                         
                         while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor){   // looking for boundary
                             curPix = startPix = startPix - image_size;
                             iy--;
                         }
                    
                         temp_ix = ix;
                         temp_iy = iy;
                         
                         do {                                            // tracing cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                             
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
                                 
                                 nextX = temp_xcenter_size + next_ix * temp_size_image_size;
                                 nextY = temp_ycenter_size + next_iy * temp_size_image_size;
                                                             
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }
                                
                                 if((nextColor = rgbs[nextPix]) == culcColor)  {
                                     temp_result = image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                     c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                     temp_result2 = fractal.calculateFractal(new Complex(x_anti1 = (nextX - antialiasing_size), y_anti1 = (nextY - antialiasing_size)));
                                     temp_result3 = fractal.calculateFractal(new Complex(x_anti2 = (nextX + antialiasing_size), y_anti1));
                                     temp_result4 = fractal.calculateFractal(new Complex(x_anti2, y_anti2 = (nextY + antialiasing_size)));
                                     temp_result5 = fractal.calculateFractal(new Complex(x_anti1, y_anti2));

                                     c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                                     c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                                     c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                                     c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                                          // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                                     red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                                     green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                                     blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);

                                     nextColor = rgbs[nextPix] = new Color(red, green, blue).getRGB();
                                     
                                     drawing_done++;
                                     thread_calculated++;
                                     /*ptr.getMainPanel().repaint();
                                     try {
                                        Thread.sleep(1); //demo
                                     }
                                     catch (InterruptedException ex) {}*/
                                 }
                              
                            
                                 if(nextColor == startColor ) {
                                     curDir = dir;    
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);

                    
                         curDir = dirRight;
        
                         
                         do {                                                 // 2nd cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                                 
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
       
                                 
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }

                                 if(rgbs[nextPix] == startColor) {           // flooding
                                     curDir = dir;
                                     if(dir == dirUP) {
                                         floodPix = curPix;
                                         flood_ix = temp_ix;
                                         
                                         while(true) {
                                             flood_ix++;
                                                                                   
                                             if(flood_ix >= TOx) {
                                                 break;
                                             }
                                             
                                             previous_value = image_iterations[floodPix];
                                             floodPix++;
                                             
                                             if((floodColor = rgbs[floodPix]) == culcColor) {
                                                 drawing_done++;
                                                 rgbs[floodPix] = startColor;
                                                 image_iterations[floodPix] = previous_value;
                                                 /*ptr.getMainPanel().repaint();
                                                 try {
                                                     Thread.sleep(1); //demo
                                                 }
                                                 catch (InterruptedException ex) {}*/
                                             }
                                             else if(floodColor != startColor) {
                                                 break;
                                             }
                   
                                         }
                                         
                                         if(drawing_done / pixel_percent >= 1) {
                                             progress.update(drawing_done);
                                             drawing_done = 0;
                                         }
                                     }
                                
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);
 
                     }
                 }
             
                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }     
             }
           
         }  

    }
   

                
    private void drawJulia(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         if(!boundary_tracing) {
             double temp_result;
             
             int x, y, loc, counter = 0;
             
             int condition = image_size * image_size;
             
             do {
                
                 loc = normal_drawing_algorithm_pixel.getAndIncrement();

                 if(loc >= condition) {
                     break;
                 }

                 x = loc % image_size;
                 y = loc / image_size;

                 temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size * x, temp_ycenter_size + temp_size_image_size * y));
                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
  
                 drawing_done++;
                 counter++;
                 
                 if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     thread_calculated += drawing_done;
                     drawing_done = 0;
                 }
    
             } while(true);
             
             thread_calculated += drawing_done;
             
         }
         else {        
             double temp_result;
      
             final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;
          
             double temp_x0, temp_y0;
          
             int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
             int delPix[] = {1 , image_size, -1, -image_size};

             double nextX, nextY, previous_value;
          
             int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
             int intX[] = {1, 0, -1, 0};
             int intY[] = {0, 1, 0, -1};

             
             for(y = FROMy;  y < TOy; y++) {
                 temp_y0 = temp_ycenter_size + y * temp_size_image_size;
                 for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {
          
                     if(rgbs[pix] == culcColor) {
                         temp_x0 = temp_xcenter_size + x * temp_size_image_size;

                         curPix = startPix = pix;     
                         curDir = dirRight;
                         ix = x;
                         iy = y;
                    
                         temp_result = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                         startColor = rgbs[pix] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
                         drawing_done++;
                         thread_calculated++;
                         
                         while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor){   // looking for boundary
                             curPix = startPix = startPix - image_size;
                             iy--;
                         }
                    
                         temp_ix = ix;
                         temp_iy = iy;
                         
                         do {                                            // tracing cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                                 
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
                                 
                                 nextX = temp_xcenter_size + next_ix * temp_size_image_size;
                                 nextY = temp_ycenter_size + next_iy * temp_size_image_size;
                                                             
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }
                                
                                 if((nextColor = rgbs[nextPix]) == culcColor)  {
                                     temp_result = image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                     nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
                                     drawing_done++;
                                     thread_calculated++;
                                 }
                              
                            
                                 if(nextColor == startColor ) {
                                     curDir = dir;    
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);

                    
                         curDir = dirRight;
        
                         
                         do {                                                 // 2nd cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                                 
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
       
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }

                                 if(rgbs[nextPix] == startColor) {           // flooding  
                                     curDir = dir;
                                     if(dir == dirUP) {
                                         floodPix = curPix;
                                         flood_ix = temp_ix;                                       
                                         
                                         while(true) {
                                             flood_ix++;
                                                                                   
                                             if(flood_ix >= TOx) {
                                                 break;
                                             }
                                             
                                             previous_value = image_iterations[floodPix];
                                             floodPix++;
                                             
                                             if((floodColor = rgbs[floodPix]) == culcColor) {
                                                 drawing_done++;
                                                 rgbs[floodPix] = startColor;
                                                 image_iterations[floodPix] = previous_value;
                                             }
                                             else if(floodColor != startColor) {
                                                 break;
                                             }
                   
                                         }
                                         
                                         if(drawing_done / pixel_percent >= 1) {
                                             progress.update(drawing_done);
                                             drawing_done = 0;
                                         }
                                     }
                                
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);
 
                     }
                 }
             
                 if(drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     drawing_done = 0;
                 }     
             }
         }

    }
    
    private void drawJuliaAntialiased(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;

         int pixel_percent = image_size *  image_size / 100;
         
         if(!boundary_tracing) {
             int x, y, loc, counter = 0;
             Color c0, c1, c2, c3, c4;
             
             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double temp_x0, temp_y0;
             
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             
             int red, green, blue;
             
             int condition = image_size * image_size;
             
             do {
                
                 loc = normal_drawing_algorithm_pixel.getAndIncrement();

                 if(loc >= condition) {
                     break;
                 }

                 x = loc % image_size;
                 y = loc / image_size;
   
                 temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                 temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                 
                 temp_result = image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
        
                 temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                 temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                 temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                 temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                 c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                 c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                 c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                 c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                 red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                 green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                 blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);
    
                 rgbs[loc] = new Color(red, green, blue).getRGB();
  
                 drawing_done++;
                 counter++;
                 
                 if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                     progress.update(drawing_done);
                     thread_calculated += drawing_done;
                     drawing_done = 0;
                 }
    
             } while(true);
             
             thread_calculated += drawing_done;
             
         }
         else {        
             Color c0, c1, c2, c3, c4;
             
             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             
             int red, green, blue;
      
             final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;
          
             double temp_x0, temp_y0;           
             
             int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
             int delPix[] = {1 , image_size, -1, -image_size};
             double nextX, nextY, previous_value;
          
             int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
             int intX[] = {1, 0, -1, 0};
             int intY[] = {0, 1, 0, -1};
         
          
             for(y = FROMy; y < TOy; y++) {
                 temp_y0 = temp_ycenter_size + y * temp_size_image_size;
                 for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {
          
                     if(rgbs[pix] == culcColor) {
                         temp_x0 = temp_xcenter_size + x * temp_size_image_size;
                         curPix = startPix = pix;     
                         curDir = dirRight;
                         ix = x;
                         iy = y;
                    
                         temp_result = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                         c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                         
                         temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                         temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                         temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                         temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));

                         c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                         c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                         c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                         c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                              // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                         red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                         green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                         blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);
    
                         startColor = rgbs[pix] = new Color(red, green, blue).getRGB();
                         
                         drawing_done++;
                         thread_calculated++;

                         
                         while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor){   // looking for boundary
                             curPix = startPix = startPix - image_size;
                             iy--;
                         }
                    
                         temp_ix = ix;
                         temp_iy = iy;
                         
                         do {                                            // tracing cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                             
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
                                 
                                 nextX = temp_xcenter_size + next_ix * temp_size_image_size;
                                 nextY = temp_ycenter_size + next_iy * temp_size_image_size;
                                                             
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }
                                
                                 if((nextColor = rgbs[nextPix]) == culcColor)  {
                                     temp_result = image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                     c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                     temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (nextX - antialiasing_size), y_anti1 = (nextY - antialiasing_size)));
                                     temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (nextX + antialiasing_size), y_anti1));
                                     temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (nextY + antialiasing_size)));
                                     temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));

                                     c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                                     c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                                     c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                                     c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                                          // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                                     red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                                     green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                                     blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);

                                     nextColor = rgbs[nextPix] = new Color(red, green, blue).getRGB();
                                     
                                     drawing_done++;
                                     thread_calculated++;
                                 }
                              
                            
                                 if(nextColor == startColor ) {
                                     curDir = dir;    
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);

                    
                         curDir = dirRight;
        
                         
                         do {                                                 // 2nd cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                                 
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
       
                                 
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }

                                 if(rgbs[nextPix] == startColor) {           // flooding
                                     curDir = dir;
                                     if(dir == dirUP) {
                                         floodPix = curPix;
                                         flood_ix = temp_ix;
                                         
                                         while(true) {
                                             flood_ix++;
                                                                                   
                                             if(flood_ix >= TOx) {
                                                 break;
                                             }
                                             
                                             previous_value = image_iterations[floodPix];
                                             floodPix++;
                                             
                                             if((floodColor = rgbs[floodPix]) == culcColor) {
                                                 drawing_done++;
                                                 rgbs[floodPix] = startColor;
                                                 image_iterations[floodPix] = previous_value;
                                             }
                                             else if(floodColor != startColor) {
                                                 break;
                                             }
                   
                                         }
                                         
                                         if(drawing_done / pixel_percent >= 1) {
                                             progress.update(drawing_done);
                                             drawing_done = 0;
                                         }
                                     }
                                
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);
 
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

        
        if(fast_julia_filters && filters[0]) {
            drawFastJuliaAntialiased(image_size);
        }
        else {
            drawFastJulia(image_size);
        }
        

        int done = synchronization.incrementAndGet();
         
 
        if(done == ptr.getNumberOfThreads()) {    
           
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
       
    }

    private void drawFastJulia(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;
         
         if(!boundary_tracing) {
             double temp_result;
             
             int x, y, loc;
             
             int condition = image_size * image_size;
             
             do {
                
                 loc = normal_drawing_algorithm_pixel.getAndIncrement();

                 if(loc >= condition) {
                     break;
                 }

                 x = loc % image_size;
                 y = loc / image_size;

                 temp_result = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size * x, temp_ycenter_size + temp_size_image_size * y));
                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
 
             } while(true);

         }
         else {
             double temp_result;
      
             final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;
          
             double temp_x0, temp_y0;
           
             int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
             int delPix[] = {1 , image_size, -1, -image_size};
             double nextX, nextY;
          
             int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
             int intX[] = {1, 0, -1, 0};
             int intY[] = {0, 1, 0, -1};
             
          
             for(y = FROMy; y < TOy; y++) {
                 temp_y0 = temp_ycenter_size + y * temp_size_image_size;
                 for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {
          
                     if(rgbs[pix] == culcColor) {
                         temp_x0 = temp_xcenter_size + x * temp_size_image_size;
                         curPix = startPix = pix;     
                         curDir = dirRight;
                         ix = x;
                         iy = y;
                    
                         temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                         startColor = rgbs[pix] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
                         
                         while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor){   // looking for boundary
                             curPix = startPix = startPix - image_size;
                             iy--;
                         }
                    
                         temp_ix = ix;
                         temp_iy = iy;
                         
                         do {                                            // tracing cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                               
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
                                 
                                 nextX = temp_xcenter_size + next_ix * temp_size_image_size;
                                 nextY = temp_ycenter_size + next_iy * temp_size_image_size;
                                 
                                                             
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }
                                
                                 if((nextColor = rgbs[nextPix]) == culcColor)  {
                                     temp_result = fractal.calculateJulia(new Complex(nextX, nextY));
                                     nextColor = rgbs[nextPix] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
                                 }
                              
                            
                                 if(nextColor == startColor ) {
                                     curDir = dir;    
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);

                    
                         curDir = dirRight;
        
                         
                         do {                                                 // 2nd cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                                 
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
       
                                 
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }

                                 if(rgbs[nextPix] == startColor) {           // flooding
                                     curDir = dir;
                                     if(dir == dirUP) {
                                         floodPix = curPix;
                                         flood_ix = temp_ix;
                                         
                                         while(true) {
                                             flood_ix++;
                                                                                   
                                             if(flood_ix >= TOx) {
                                                 break;
                                             }
                                             
                                             floodPix++;
                                             
                                             if((floodColor = rgbs[floodPix]) == culcColor) {
                                                 rgbs[floodPix] = startColor;
                                             }
                                             else if(floodColor != startColor) {
                                                 break;
                                             }
                   
                                         }

                                     }
                                
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);
 
                     }
                 }
       
             }
         }

    }
    
    private void drawFastJuliaAntialiased(int image_size) {

         double size = fractal.getSize();

         double size_2 = size * 0.5;
         double temp_xcenter_size = fractal.getXCenter() - size_2;
         double temp_ycenter_size = fractal.getYCenter() - size_2;
         double temp_size_image_size = size / image_size;
         
         if(!boundary_tracing) {
             int x, y, loc;
             Color c0, c1, c2, c3, c4;
             
             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             double temp_x0, temp_y0;
             
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             
             int red, green, blue;
             
             int condition = image_size * image_size;
             
             do {
                
                 loc = normal_drawing_algorithm_pixel.getAndIncrement();

                 if(loc >= condition) {
                     break;
                 }

                 x = loc % image_size;
                 y = loc / image_size;

                 temp_x0 = temp_xcenter_size + temp_size_image_size * x;
                 temp_y0 = temp_ycenter_size + temp_size_image_size * y;
                 
                 temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                 c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
        
                 temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                 temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                 temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                 temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                 c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                 c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                 c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                 c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                 red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                 green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                 blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);
    
                 rgbs[loc] = new Color(red, green, blue).getRGB();

             } while(true);

         }
         else {      
             Color c0, c1, c2, c3, c4;
             
             double temp_result, temp_result2, temp_result3, temp_result4, temp_result5;
             
             double x_anti1, x_anti2;
             double y_anti1, y_anti2;
             
             int red, green, blue;
      
             final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;
          
             double temp_x0, temp_y0;

             int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
             int delPix[] = {1 , image_size, -1, -image_size};
             double nextX, nextY;
          
             int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
             int intX[] = {1, 0, -1, 0};
             int intY[] = {0, 1, 0, -1};
         
     
          
             for(y = FROMy; y < TOy; y++) {
                 temp_y0 = temp_ycenter_size + y * temp_size_image_size;
                 for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {
          
                     if(rgbs[pix] == culcColor) {
                         temp_x0 = temp_xcenter_size + x * temp_size_image_size;
                         curPix = startPix = pix;     
                         curDir = dirRight;
                         ix = x;
                         iy = y;
                    
                         temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                         c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
                         
                         temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                         temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                         temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                         temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));

                         c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                         c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                         c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                         c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                              // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                         red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                         green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                         blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);
    
                         startColor = rgbs[pix] = new Color(red, green, blue).getRGB();                     
                         
                         while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor){   // looking for boundary
                             curPix = startPix = startPix - image_size;
                             iy--;
                         }
                    
                         temp_ix = ix;
                         temp_iy = iy;
                         
                         do {                                            // tracing cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];

                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
                                 
                                 nextX = temp_xcenter_size + next_ix * temp_size_image_size;
                                 nextY = temp_ycenter_size + next_iy * temp_size_image_size;
                                                             
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }
                                
                                 if((nextColor = rgbs[nextPix]) == culcColor)  {
                                     temp_result = fractal.calculateJulia(new Complex(nextX, nextY));
                                     c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);

                                     temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (nextX - antialiasing_size), y_anti1 = (nextY - antialiasing_size)));
                                     temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (nextX + antialiasing_size), y_anti1));
                                     temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (nextY + antialiasing_size)));
                                     temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));

                                     c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                                     c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                                     c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                                     c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                                          // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                                     red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                                     green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                                     blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);

                                     nextColor = rgbs[nextPix] = new Color(red, green, blue).getRGB();
 
                                 }
                              
                            
                                 if(nextColor == startColor ) {
                                     curDir = dir;    
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);

                    
                         curDir = dirRight;
        
                         
                         do {                                                 // 2nd cycle
                             for(Dir = curDir + 3; Dir < curDir + 7; Dir++) {
                                 dir = Dir & maskDir;
                                 nextPix = curPix + delPix[dir];
                                 
                                 next_ix = temp_ix + intX[dir];
                                 next_iy = temp_iy + intY[dir];
       
                                 
                                 if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                     continue;
                                 }

                                 if(rgbs[nextPix] == startColor) {           // flooding
                                     curDir = dir;
                                     if(dir == dirUP) {
                                         floodPix = curPix;
                                         flood_ix = temp_ix;
                                         
                                         while(true) {
                                             flood_ix++;
                                                                                   
                                             if(flood_ix >= TOx) {
                                                 break;
                                             }
                                             
                                             floodPix++;
                                             
                                             if((floodColor = rgbs[floodPix]) == culcColor) {
                                                 rgbs[floodPix] = startColor;
                                             }
                                             else if(floodColor != startColor) {
                                                 break;
                                             }
                   
                                         }
                                     }
                                
                                     curPix = nextPix;
                                     temp_ix = next_ix;
                                     temp_iy = next_iy;
                                     break; 
                                 }
                             }
                         } while(curPix != startPix);
 
                     }
                 }   
             }
         }
    }

   
    private void colorCycling() {

         if(!color_cycling) {

             return;

         }

         ptr.setWholeImageDone(false);

         int image_size = image.getHeight();
         
         color_cycling_location++;
        
         color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;
             
         double temp_result;
         for(int y = FROMy; y < TOy; y++) {
             for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                 temp_result = image_iterations[loc];
                 rgbs[loc] =  temp_result == max_iterations? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
             }
         }
 
         ptr.setWholeImageDone(true);

         ptr.getMainPanel().repaint();

         try {
             Thread.sleep(200);
         }
         catch (InterruptedException ex) {}

         colorCycling();

    }

    
    private void applyPaletteAndFilter() {
        
         int image_size = image.getHeight();

          
        
         changePalette(image_size);     
         
                  
         if(drawing_done != 0) {
             progress.update(drawing_done);
         }

         int done = synchronization.incrementAndGet();
         
  
         if(done == ptr.getNumberOfThreads()) {    
             
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
        
    }


    private void changePalette(int image_size) {

         int pixel_percent = image_size *  image_size / 100;
         
         double temp_result;
         if(ptr.getJuliaMap()) {
             for(int y = FROMy; y < TOy; y++) {
                 for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                     temp_result = image_iterations[loc];
                     rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();

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

                         rgbs[loc] = temp_starting_pixel_color = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();

                         drawing_done++;
                         total_drawing++;

                         for(x++, loc = y * image_size + x; x < slice_TOx - step; x++, loc++) {
                             temp_result = image_iterations[loc];
                             if(temp_result == temp_starting_pixel_cicle) {
                                 rgbs[loc] = temp_starting_pixel_color;
                             }
                             else {
                                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
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
                                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
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
                                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
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
                                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();
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


    
    private void drawJuliaMap() {

        int image_size = image.getHeight();
         
        
        if(filters[0]) {
            juliaMapAntialiased(image_size);
        }
        else {
            juliaMap(image_size);
        }  
        
        
        if(drawing_done != 0) {
             progress.update(drawing_done);
         }

         int done = synchronization.incrementAndGet();
                  
         if(done == ptr.getJuliaMapSlices()) {              
             
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
        
    }
    
 
    private void juliaMap(int image_size) {

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
                 rgbs[loc] = temp_result == max_iterations ? fractal_color.getRGB() : palette_color.getPaletteColor(temp_result + color_cycling_location).getRGB();

                 drawing_done++;

             }

             if(drawing_done / pixel_percent >= 1) {
                 progress.update(drawing_done);
                 drawing_done = 0;
             }
             
             temp_x0 = temp_xcenter_size;

         }
        
    }
    
    private void juliaMapAntialiased(int image_size) {

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
                 c0 = temp_result == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result + color_cycling_location);
        
                 temp_result2 = fractal.calculateJulia(new Complex(x_anti1 = (temp_x0 - antialiasing_size), y_anti1 = (temp_y0 - antialiasing_size)));
                 temp_result3 = fractal.calculateJulia(new Complex(x_anti2 = (temp_x0 + antialiasing_size), y_anti1));
                 temp_result4 = fractal.calculateJulia(new Complex(x_anti2, y_anti2 = (temp_y0 + antialiasing_size)));
                 temp_result5 = fractal.calculateJulia(new Complex(x_anti1, y_anti2));
                     
                 c1 = temp_result2 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result2 + color_cycling_location);
                 c2 = temp_result3 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result3 + color_cycling_location);
                 c3 = temp_result4 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result4 + color_cycling_location);
                 c4 = temp_result5 == max_iterations ? fractal_color : palette_color.getPaletteColor(temp_result5 + color_cycling_location);
                      // resulting color; each component of color is an avarage of 5 values ( central point and 4 corners )
                 red = (int)((c0.getRed() + c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed() + 2.5) / 5);
                 green = (int)((c0.getGreen() + c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen() + 2.5) / 5);
                 blue = (int)((c0.getBlue() + c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue() + 2.5) / 5);
    
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
    
    public static void resetAtomics() {
        
        synchronization = new AtomicInteger(0);
        total_calculated = new AtomicInteger(0);
        normal_drawing_algorithm_pixel = new AtomicInteger(0);
        
    }

}
