/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.core;

import fractalzoomer.core.blending.AdditionBlending;
import fractalzoomer.core.blending.Blending;
import fractalzoomer.core.blending.BurnBlending;
import fractalzoomer.core.blending.ColorBlending;
import fractalzoomer.core.blending.DarkenOnlyBlending;
import fractalzoomer.core.blending.DifferenceBlending;
import fractalzoomer.core.blending.DivideBlending;
import fractalzoomer.core.blending.DodgeBlending;
import fractalzoomer.core.blending.GrainExtractBlending;
import fractalzoomer.core.blending.GrainMergeBlending;
import fractalzoomer.core.blending.HardLightBlending;
import fractalzoomer.core.blending.LightenOnlyBlending;
import fractalzoomer.core.blending.MultiplyBlending;
import fractalzoomer.core.blending.NormalBlending;
import fractalzoomer.core.blending.OverlayBlending;
import fractalzoomer.core.blending.SaturationBlending;
import fractalzoomer.core.blending.ScreenBlending;
import fractalzoomer.core.blending.SubtractionBlending;
import fractalzoomer.core.blending.ValueBlending;
import fractalzoomer.core.iteration_algorithm.FractalIterationAlgorithm;
import fractalzoomer.core.iteration_algorithm.IterationAlgorithm;
import fractalzoomer.core.iteration_algorithm.JuliaIterationAlgorithm;
import fractalzoomer.core.domain_coloring.BlackCirclesLog2DomainColoring;
import fractalzoomer.core.domain_coloring.BlackGridBrightContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.BlackGridDomainColoring;
import fractalzoomer.core.domain_coloring.BlackGridWhiteCirclesLog2DomainColoring;
import fractalzoomer.core.domain_coloring.BrightContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.DarkContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.DomainColoring;
import fractalzoomer.core.domain_coloring.NormBlackCirclesLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormBlackGridBrightContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormBlackGridDomainColoring;
import fractalzoomer.core.domain_coloring.NormBlackGridWhiteCirclesLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormBrightContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormDarkContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormWhiteCirclesLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormWhiteGridBlackCirclesLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormWhiteGridDarkContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormWhiteGridDomainColoring;
import fractalzoomer.core.domain_coloring.BlackGridContoursLog2IsoLinesDomainColoring;
import fractalzoomer.core.domain_coloring.BlackGridIsoContoursDomainColoring;
import fractalzoomer.core.domain_coloring.CustomDomainColoring;
import fractalzoomer.core.domain_coloring.GridContoursIsoLinesDomainColoring;
import fractalzoomer.core.domain_coloring.IsoContoursContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.NormBlackGridContoursLog2IsoLinesDomainColoring;
import fractalzoomer.core.domain_coloring.NormBlackGridIsoContoursDomainColoring;
import fractalzoomer.core.domain_coloring.NormGridContoursIsoLinesDomainColoring;
import fractalzoomer.core.domain_coloring.NormIsoContoursContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.WhiteCirclesLog2DomainColoring;
import fractalzoomer.core.domain_coloring.WhiteGridBlackCirclesLog2DomainColoring;
import fractalzoomer.core.domain_coloring.WhiteGridDarkContoursLog2DomainColoring;
import fractalzoomer.core.domain_coloring.WhiteGridDomainColoring;
import fractalzoomer.fractal_options.orbit_traps.OrbitTrap;
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
import fractalzoomer.functions.formulas.coupled.CoupledMandelbrot;
import fractalzoomer.functions.formulas.coupled.CoupledMandelbrotBurningShip;
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
import fractalzoomer.functions.formulas.m_like_generalization.Formula28;
import fractalzoomer.functions.formulas.m_like_generalization.Formula29;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula27;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula3;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula4;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula5;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula6;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula7;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula8;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.Formula9;
import fractalzoomer.functions.general.FrothyBasin;
import fractalzoomer.functions.root_finding_methods.halley.HalleySin;
import fractalzoomer.functions.root_finding_methods.halley.Halley4;
import fractalzoomer.functions.root_finding_methods.halley.HalleyPoly;
import fractalzoomer.functions.root_finding_methods.halley.HalleyGeneralized8;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderGeneralized8;
import fractalzoomer.functions.root_finding_methods.halley.HalleyCos;
import fractalzoomer.functions.root_finding_methods.halley.HalleyGeneralized3;
import fractalzoomer.functions.root_finding_methods.halley.Halley3;
import fractalzoomer.functions.root_finding_methods.householder.Householder3;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderSin;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderCos;
import fractalzoomer.functions.root_finding_methods.householder.Householder4;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderPoly;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderGeneralized3;
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
import fractalzoomer.functions.root_finding_methods.newton.NewtonCos;
import fractalzoomer.functions.root_finding_methods.newton.NewtonGeneralized3;
import fractalzoomer.functions.root_finding_methods.newton.Newton3;
import fractalzoomer.functions.root_finding_methods.newton.NewtonGeneralized8;
import fractalzoomer.functions.root_finding_methods.newton.NewtonPoly;
import fractalzoomer.functions.general.Phoenix;
import fractalzoomer.functions.root_finding_methods.newton.NewtonSin;
import fractalzoomer.functions.root_finding_methods.newton.Newton4;
import fractalzoomer.functions.general.Spider;
import fractalzoomer.functions.root_finding_methods.schroder.Schroder4;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderGeneralized3;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderSin;
import fractalzoomer.functions.root_finding_methods.schroder.Schroder3;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderPoly;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderCos;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderGeneralized8;
import fractalzoomer.functions.general.SierpinskiGasket;
import fractalzoomer.functions.mandelbrot.MandelbrotWth;
import fractalzoomer.functions.math.Sinh;
import fractalzoomer.functions.math.Sin;
import fractalzoomer.functions.math.Tan;
import fractalzoomer.functions.math.Tanh;
import fractalzoomer.functions.general.Nova;
import fractalzoomer.functions.formulas.general.mathtype.Formula31;
import fractalzoomer.functions.formulas.general.mathtype.Formula30;
import fractalzoomer.functions.formulas.general.mathtype.Formula32;
import fractalzoomer.functions.formulas.general.mathtype.Formula33;
import fractalzoomer.functions.formulas.general.mathtype.Formula34;
import fractalzoomer.functions.formulas.general.mathtype.Formula35;
import fractalzoomer.functions.formulas.general.mathtype.Formula36;
import fractalzoomer.functions.formulas.general.mathtype.Formula37;
import fractalzoomer.functions.formulas.general.newtonvariant.Formula42;
import fractalzoomer.functions.formulas.general.newtonvariant.Formula43;
import fractalzoomer.functions.formulas.general.newtonvariant.Formula44;
import fractalzoomer.functions.formulas.general.newtonvariant.Formula45;
import fractalzoomer.functions.formulas.m_like_generalization.Formula38;
import fractalzoomer.functions.formulas.m_like_generalization.Formula39;
import fractalzoomer.functions.formulas.m_like_generalization.Formula46;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.Formula40;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.Formula41;
import fractalzoomer.functions.root_finding_methods.halley.HalleyFormula;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderFormula;
import fractalzoomer.functions.root_finding_methods.laguerre.Laguerre3;
import fractalzoomer.functions.root_finding_methods.laguerre.Laguerre4;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreCos;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreFormula;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreGeneralized3;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreGeneralized8;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerrePoly;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreSin;
import fractalzoomer.functions.root_finding_methods.muller.Muller3;
import fractalzoomer.functions.root_finding_methods.muller.Muller4;
import fractalzoomer.functions.root_finding_methods.muller.MullerCos;
import fractalzoomer.functions.root_finding_methods.muller.MullerFormula;
import fractalzoomer.functions.root_finding_methods.muller.MullerGeneralized3;
import fractalzoomer.functions.root_finding_methods.muller.MullerGeneralized8;
import fractalzoomer.functions.root_finding_methods.muller.MullerPoly;
import fractalzoomer.functions.root_finding_methods.muller.MullerSin;
import fractalzoomer.functions.root_finding_methods.newton.NewtonFormula;
import fractalzoomer.functions.root_finding_methods.parhalley.Parhalley3;
import fractalzoomer.functions.root_finding_methods.parhalley.Parhalley4;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyCos;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyFormula;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyGeneralized3;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyGeneralized8;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyPoly;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleySin;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderFormula;
import fractalzoomer.functions.user_formulas.UserFormulaConverging;
import fractalzoomer.functions.user_formulas.UserFormulaEscaping;
import fractalzoomer.functions.user_formulas.UserFormulaIterationBasedConverging;
import fractalzoomer.functions.user_formulas.UserFormulaIterationBasedEscaping;
import fractalzoomer.functions.root_finding_methods.secant.Secant3;
import fractalzoomer.functions.root_finding_methods.secant.Secant4;
import fractalzoomer.functions.root_finding_methods.secant.SecantCos;
import fractalzoomer.functions.root_finding_methods.secant.SecantFormula;
import fractalzoomer.functions.root_finding_methods.secant.SecantGeneralized3;
import fractalzoomer.functions.root_finding_methods.secant.SecantGeneralized8;
import fractalzoomer.functions.root_finding_methods.secant.SecantPoly;
import fractalzoomer.functions.root_finding_methods.steffensen.Steffensen3;
import fractalzoomer.functions.root_finding_methods.steffensen.Steffensen4;
import fractalzoomer.functions.root_finding_methods.steffensen.SteffensenFormula;
import fractalzoomer.functions.root_finding_methods.steffensen.SteffensenGeneralized3;
import fractalzoomer.functions.root_finding_methods.steffensen.SteffensenPoly;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly1;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly2;
import fractalzoomer.functions.user_formulas.UserFormulaConditionalConverging;
import fractalzoomer.functions.user_formulas.UserFormulaConditionalEscaping;
import fractalzoomer.functions.user_formulas.UserFormulaCoupledConverging;
import fractalzoomer.functions.user_formulas.UserFormulaCoupledEscaping;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.app_settings.BumpMapSettings;
import fractalzoomer.main.app_settings.D3Settings;
import fractalzoomer.main.app_settings.DomainColoringSettings;
import fractalzoomer.main.app_settings.EntropyColoringSettings;
import fractalzoomer.main.app_settings.FakeDistanceEstimationSettings;
import fractalzoomer.main.app_settings.FiltersSettings;
import fractalzoomer.main.app_settings.GreyscaleColoringSettings;
import fractalzoomer.main.app_settings.OffsetColoringSettings;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.RainbowPaletteSettings;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;
import fractalzoomer.palettes.Palette;
import fractalzoomer.palettes.transfer_functions.AtanTransferFunction;
import fractalzoomer.palettes.transfer_functions.CbrtTransferFunction;
import fractalzoomer.palettes.transfer_functions.ForthrtTransferFunction;
import fractalzoomer.palettes.transfer_functions.LinearTransferFunction;
import fractalzoomer.palettes.transfer_functions.LogLogTransferFunction;
import fractalzoomer.palettes.transfer_functions.LogarithmTransferFunction;
import fractalzoomer.palettes.transfer_functions.SqrtTransferFunction;
import fractalzoomer.palettes.transfer_functions.TransferFunction;
import fractalzoomer.utils.ColorAlgorithm;
import fractalzoomer.utils.ColorGenerator;
import fractalzoomer.utils.MathUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author hrkalona
 */
public abstract class ThreadDraw extends Thread {

    /**
     * ** MODES ***
     */
    public static final int NORMAL = 0;
    public static final int FAST_JULIA = 1;
    public static final int COLOR_CYCLING = 2;
    public static final int APPLY_PALETTE_AND_FILTER = 3;
    public static final int JULIA_MAP = 4;
    public static final int ROTATE_3D_MODEL = 5;
    public static final int POLAR = 6;
    public static final int FAST_JULIA_POLAR = 7;
    public static final int JULIA_MAP_POLAR = 8;
    public static final int APPLY_PALETTE_AND_FILTER_3D_MODEL = 9;
    public static final int DOMAIN = 10;
    public static final int DOMAIN_POLAR = 11;
    public static final int NORMAL_EXPANDER = 12;
    public static final int POLAR_EXPANDER = 13;
    public static final int DOMAIN_EXPANDER = 14;
    public static final int DOMAIN_POLAR_EXPANDER = 15;
    /**
     * ************
     */

    /**
     * ** Thread Related / Synchronization ***
     */
    protected int FROMx;
    protected int TOx;
    protected int FROMy;
    protected int TOy;

    protected static final int THREAD_CHUNK_SIZE = 5000;
    protected static final int QUICKDRAW_THREAD_CHUNK_SIZE = 50;

    protected static int randomNumber;

    protected int drawing_done;
    protected int thread_calculated;

    protected static int[] algorithm_colors;

    protected static AtomicInteger finalize_sync;
    protected static CyclicBarrier initialize_jobs_sync;
    protected static CyclicBarrier post_processing_sync;
    protected static CyclicBarrier calculate_vectors_sync;
    protected static AtomicInteger painting_sync;
    protected static AtomicInteger height_scaling_sync;
    protected static CyclicBarrier height_scaling_sync2;
    protected static AtomicInteger gaussian_scaling_sync;
    protected static CyclicBarrier gaussian_scaling_sync2;
    protected static AtomicInteger shade_color_height_sync;
    protected static CyclicBarrier shade_color_height_sync2;
    protected static AtomicInteger total_calculated;
    protected static AtomicInteger normal_drawing_algorithm_pixel;
    protected static CyclicBarrier color_cycling_filters_sync;
    protected static CyclicBarrier color_cycling_restart_sync;
    /**
     * ****************************
     */

    /**
     * ** 3D ***
     */
    protected boolean d3;
    protected int detail;
    protected double fiX, fiY, scale, m20, m21, m22;
    protected double d3_height_scale;
    protected int max_range;
    protected int min_range;
    protected int max_scaling;
    protected int height_algorithm;
    protected boolean gaussian_scaling;
    protected double gaussian_weight;
    protected int gaussian_kernel_size;
    protected static float vert[][][];
    protected static float vert1[][][];
    protected static float Norm1z[][][];
    protected static float[] gaussian_kernel;
    protected static float[][] temp_array;
    protected double color_3d_blending;
    protected boolean shade_height;
    protected int shade_choice;
    protected int shade_algorithm;
    protected boolean shade_invert;
    protected static double max;
    protected static double min;

    /**
     * *********
     */
    /**
     * ** Filters ***
     */
    protected boolean[] filters;
    protected int[] filters_options_vals;
    protected int[][] filters_options_extra_vals;
    protected boolean fast_julia_filters;
    protected Color[] filters_colors;
    protected Color[][] filters_extra_colors;
    protected int[] filters_order;
    /**
     * **************
     */

    /**
     * ** Image Related ***
     */
    protected BufferedImage image;
    protected int[] rgbs;
    protected static double[] image_iterations;
    protected static double[] image_iterations_fast_julia;
    public static int IMAGE_SIZE;
    /**
     * ********************
     */

    /**
     * ** Post Processing ***
     */
    private static final int MAX_BUMP_MAPPING_DEPTH = 100;
    private static final int DEFAULT_BUMP_MAPPING_STRENGTH = 50;
    protected boolean bump_map;
    protected double bumpMappingStrength;
    protected double bumpMappingDepth;
    protected double lightDirectionDegrees;
    protected int bump_transfer_function;
    protected double bump_transfer_factor;
    protected int bumpProcessing;
    protected double bump_blending;
    protected boolean fake_de;
    protected double fake_de_factor;
    protected int dem_color;
    protected boolean rainbow_palette;
    protected double rainbow_palette_factor;
    protected boolean inverse_fake_dem;
    protected boolean entropy_coloring;
    protected int entropy_algorithm;
    protected boolean offset_coloring;
    protected int post_process_offset;
    protected double entropy_palette_factor;
    protected double bm_noise_reducing_factor;
    protected double rp_noise_reducing_factor;
    protected double en_noise_reducing_factor;
    protected double of_noise_reducing_factor;
    protected double en_blending;
    protected double rp_blending;
    protected double of_blending;
    protected int entropy_offset;
    protected int rainbow_offset;
    protected int rainbow_algorithm;
    protected boolean greyscale_coloring;
    protected double gs_noise_reducing_factor;
    /**
     * **********************
     */

    /**
     * ** Traps ***
     */
    protected boolean useTraps;
    protected double trapMaxDistance;
    protected double trapBlending;
    protected boolean trapUseSpecialColor;
    /**
     * **********************
     */

    /**
     * ** Color Cycling ***
     */
    protected boolean color_cycling;
    protected int color_cycling_location;
    protected int color_cycling_speed;
    /**
     * ********************
     */

    protected Fractal fractal;
    protected IterationAlgorithm iteration_algorithm;
    protected boolean domain_coloring;
    protected double height_ratio;
    protected boolean polar_projection;
    protected double circle_period;
    protected int fractal_color;
    protected int max_iterations;
    public static Palette palette;
    protected TransferFunction color_transfer;
    protected Blending blending;
    protected int color_blending;
    protected DomainColoring domain_color;
    protected MainWindow ptr;
    protected ImageExpanderWindow ptrExpander;
    protected JProgressBar progress;
    protected int action;
    protected double x_antialiasing_size;
    protected double x_antialiasing_size_x2;
    protected double y_antialiasing_size;
    protected double y_antialiasing_size_x2;
    protected boolean quickDraw;
    protected int tile;
    private static String default_init_val;
    private static double convergent_bailout;
    public static int TILE_SIZE = 5;
    private static int QUICK_DRAW_DELAY = 1200; //msec
    public static int SKIPPED_PIXELS_ALG = 0;
    public static int SKIPPED_PIXELS_COLOR = 0xFFFFFFFF;
    public static int[] gradient;

    static {

        default_init_val = "c";
        convergent_bailout = 0;
        List<Color> colors = ColorGenerator.generate(600, 0, 0);
        algorithm_colors = new int[colors.size()];

        for (int i = 0; i < algorithm_colors.length; i++) {
            algorithm_colors[i] = colors.get(i).getRGB();
        }
    }

    //Fractal
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, double[] laguerre_deg, int color_blending, OrbitTrapSettings ots) {

        if (quickDraw && max_iterations > 1000) {
            max_iterations = 1000;
        }

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = fs.filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.height_ratio = height_ratio;
        this.d3 = d3s.d3;
        //this.d3_draw_method = d3_draw_method;
        this.detail = d3s.detail;
        this.fiX = d3s.fiX;
        this.fiY = d3s.fiY;
        this.d3_height_scale = d3s.d3_height_scale;
        this.height_algorithm = d3s.height_algorithm;
        scale = d3s.d3_size_scale;
        this.color_3d_blending = d3s.color_3d_blending;
        this.gaussian_scaling = d3s.gaussian_scaling;
        this.gaussian_weight = d3s.gaussian_weight;
        this.gaussian_kernel_size = d3s.gaussian_kernel;
        this.max_range = d3s.max_range;
        this.min_range = d3s.min_range;
        this.max_scaling = d3s.max_scaling;
        this.shade_height = d3s.shade_height;
        this.shade_choice = d3s.shade_choice;
        this.shade_algorithm = d3s.shade_algorithm;
        this.shade_invert = d3s.shade_invert;

        this.color_blending = color_blending;

        this.bump_map = bms.bump_map;
        this.bumpMappingStrength = bms.bumpMappingStrength;
        this.bumpMappingDepth = bms.bumpMappingDepth;
        this.lightDirectionDegrees = bms.lightDirectionDegrees;
        this.bump_transfer_function = bms.bump_transfer_function;
        this.bump_transfer_factor = bms.bump_transfer_factor;
        this.bumpProcessing = bms.bumpProcessing;
        this.bump_blending = bms.bump_blending;

        this.fake_de = fdes.fake_de;
        this.fake_de_factor = fdes.fake_de_factor;
        this.inverse_fake_dem = fdes.inverse_fake_dem;

        this.rainbow_palette = rps.rainbow_palette;
        this.rainbow_palette_factor = rps.rainbow_palette_factor;
        this.rainbow_algorithm = rps.rainbow_algorithm;

        this.bm_noise_reducing_factor = bms.bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rps.rp_noise_reducing_factor;

        this.entropy_coloring = ens.entropy_coloring;
        this.entropy_palette_factor = ens.entropy_palette_factor;
        this.en_noise_reducing_factor = ens.en_noise_reducing_factor;
        this.entropy_algorithm = ens.entropy_algorithm;

        this.offset_coloring = ofs.offset_coloring;
        this.post_process_offset = ofs.post_process_offset;
        this.of_noise_reducing_factor = ofs.of_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;

        this.en_blending = ens.en_blending;
        this.rp_blending = rps.rp_blending;
        this.of_blending = ofs.of_blending;

        this.entropy_offset = ens.entropy_offset;
        this.rainbow_offset = rps.rainbow_offset;

        this.greyscale_coloring = gss.greyscale_coloring;
        this.gs_noise_reducing_factor = gss.gs_noise_reducing_factor;

        useTraps = ots.useTraps;
        trapMaxDistance = ots.trapMaxDistance;
        trapBlending = ots.trapBlending;
        trapUseSpecialColor = ots.trapUseSpecialColor;

        progress = ptr.getProgressBar();

        if (filters[MainWindow.ANTIALIASING]) {
            if (d3 && polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / detail) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else if (polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else if (d3) {
                x_antialiasing_size = (size / detail) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / detail) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }

        if (domain_coloring) {
            if (polar_projection) {
                action = DOMAIN_POLAR;
            } else {
                action = DOMAIN;
            }
        } else if (polar_projection) {
            action = POLAR;
        } else {
            action = NORMAL;
        }

        switch (transfer_function) {

            case MainWindow.LINEAR:
                color_transfer = new LinearTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer = new SqrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer = new CbrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer = new ForthrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOGARITHM:
                color_transfer = new LogarithmTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOG_LOG:
                color_transfer = new LogLogTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.ATAN:
                color_transfer = new AtanTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
        }

        this.quickDraw = quickDraw;
        tile = TILE_SIZE;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTON3:
                fractal = new Newton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTON4:
                fractal = new Newton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                fractal = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                fractal = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONSIN:
                fractal = new NewtonSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONCOS:
                fractal = new NewtonCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONPOLY:
                fractal = new NewtonPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONFORMULA:
                fractal = new NewtonFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SIERPINSKI_GASKET:
                fractal = new SierpinskiGasket(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEY3:
                fractal = new Halley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEY4:
                fractal = new Halley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYGENERALIZED3:
                fractal = new HalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYGENERALIZED8:
                fractal = new HalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYSIN:
                fractal = new HalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYCOS:
                fractal = new HalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYPOLY:
                fractal = new HalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYFORMULA:
                fractal = new HalleyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots);
                break;
            case MainWindow.SCHRODER3:
                fractal = new Schroder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODER4:
                fractal = new Schroder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERGENERALIZED3:
                fractal = new SchroderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERGENERALIZED8:
                fractal = new SchroderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERSIN:
                fractal = new SchroderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERCOS:
                fractal = new SchroderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERPOLY:
                fractal = new SchroderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERFORMULA:
                fractal = new SchroderFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots);
                break;
            case MainWindow.HOUSEHOLDER3:
                fractal = new Householder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDER4:
                fractal = new Householder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED3:
                fractal = new HouseholderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED8:
                fractal = new HouseholderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERSIN:
                fractal = new HouseholderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERCOS:
                fractal = new HouseholderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERPOLY:
                fractal = new HouseholderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERFORMULA:
                fractal = new HouseholderFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots);
                break;
            case MainWindow.SECANT3:
                fractal = new Secant3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANT4:
                fractal = new Secant4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTGENERALIZED3:
                fractal = new SecantGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTGENERALIZED8:
                fractal = new SecantGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTCOS:
                fractal = new SecantCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTPOLY:
                fractal = new SecantPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTFORMULA:
                fractal = new SecantFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots);
                break;
            case MainWindow.STEFFENSEN3:
                fractal = new Steffensen3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.STEFFENSEN4:
                fractal = new Steffensen4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.STEFFENSENGENERALIZED3:
                fractal = new SteffensenGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.STEFFENSENPOLY:
                fractal = new SteffensenPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.STEFFENSENFORMULA:
                fractal = new SteffensenFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.USER_FORMULA:
                if (bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                } else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                } else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if (bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                } else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots);
                } else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MULLER3:
                fractal = new Muller3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLER4:
                fractal = new Muller4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERGENERALIZED3:
                fractal = new MullerGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERGENERALIZED8:
                fractal = new MullerGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERSIN:
                fractal = new MullerSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERCOS:
                fractal = new MullerCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERPOLY:
                fractal = new MullerPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERFORMULA:
                fractal = new MullerFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots);
                break;
            case MainWindow.PARHALLEY3:
                fractal = new Parhalley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEY4:
                fractal = new Parhalley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYGENERALIZED3:
                fractal = new ParhalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYGENERALIZED8:
                fractal = new ParhalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYSIN:
                fractal = new ParhalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYCOS:
                fractal = new ParhalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYPOLY:
                fractal = new ParhalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYFORMULA:
                fractal = new ParhalleyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots);
                break;
            case MainWindow.LAGUERRE3:
                fractal = new Laguerre3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERRE4:
                fractal = new Laguerre4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERREGENERALIZED3:
                fractal = new LaguerreGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERREGENERALIZED8:
                fractal = new LaguerreGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERRESIN:
                fractal = new LaguerreSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERRECOS:
                fractal = new LaguerreCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERREPOLY:
                fractal = new LaguerrePoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERREFORMULA:
                fractal = new LaguerreFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, laguerre_deg, ots);
                break;

        }

        blendingFactory();

        iteration_algorithm = new FractalIterationAlgorithm(fractal);

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds);
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Fractal-Expander
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, double[] laguerre_deg, int color_blending, OrbitTrapSettings ots) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptrExpander = ptr;
        this.filters = fs.filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.height_ratio = height_ratio;

        this.color_blending = color_blending;

        this.bump_map = bms.bump_map;
        this.bumpMappingStrength = bms.bumpMappingStrength;
        this.bumpMappingDepth = bms.bumpMappingDepth;
        this.lightDirectionDegrees = bms.lightDirectionDegrees;
        this.bump_transfer_function = bms.bump_transfer_function;
        this.bump_transfer_factor = bms.bump_transfer_factor;
        this.bumpProcessing = bms.bumpProcessing;
        this.bump_blending = bms.bump_blending;

        this.fake_de = fdes.fake_de;
        this.fake_de_factor = fdes.fake_de_factor;
        this.inverse_fake_dem = fdes.inverse_fake_dem;

        this.rainbow_palette = rps.rainbow_palette;
        this.rainbow_palette_factor = rps.rainbow_palette_factor;
        this.rainbow_algorithm = rps.rainbow_algorithm;

        this.bm_noise_reducing_factor = bms.bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rps.rp_noise_reducing_factor;

        this.entropy_coloring = ens.entropy_coloring;
        this.entropy_palette_factor = ens.entropy_palette_factor;
        this.en_noise_reducing_factor = ens.en_noise_reducing_factor;
        this.entropy_algorithm = ens.entropy_algorithm;

        this.offset_coloring = ofs.offset_coloring;
        this.post_process_offset = ofs.post_process_offset;
        this.of_noise_reducing_factor = ofs.of_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;

        this.en_blending = ens.en_blending;
        this.rp_blending = rps.rp_blending;
        this.of_blending = ofs.of_blending;

        this.entropy_offset = ens.entropy_offset;
        this.rainbow_offset = rps.rainbow_offset;

        this.greyscale_coloring = gss.greyscale_coloring;
        this.gs_noise_reducing_factor = gss.gs_noise_reducing_factor;

        useTraps = ots.useTraps;
        trapMaxDistance = ots.trapMaxDistance;
        trapBlending = ots.trapBlending;
        trapUseSpecialColor = ots.trapUseSpecialColor;

        progress = ptrExpander.getProgressBar();

        if (filters[MainWindow.ANTIALIASING]) {
            if (polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }

        if (domain_coloring) {
            if (polar_projection) {
                action = DOMAIN_POLAR_EXPANDER;
            } else {
                action = DOMAIN_EXPANDER;
            }
        } else if (polar_projection) {
            action = POLAR_EXPANDER;
        } else {
            action = NORMAL_EXPANDER;
        }

        switch (transfer_function) {

            case MainWindow.LINEAR:
                color_transfer = new LinearTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer = new SqrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer = new CbrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer = new ForthrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOGARITHM:
                color_transfer = new LogarithmTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOG_LOG:
                color_transfer = new LogLogTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.ATAN:
                color_transfer = new AtanTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
        }

        tile = TILE_SIZE;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTON3:
                fractal = new Newton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTON4:
                fractal = new Newton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                fractal = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                fractal = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONSIN:
                fractal = new NewtonSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONCOS:
                fractal = new NewtonCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONPOLY:
                fractal = new NewtonPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.NEWTONFORMULA:
                fractal = new NewtonFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SIERPINSKI_GASKET:
                fractal = new SierpinskiGasket(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEY3:
                fractal = new Halley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEY4:
                fractal = new Halley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYGENERALIZED3:
                fractal = new HalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYGENERALIZED8:
                fractal = new HalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYSIN:
                fractal = new HalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYCOS:
                fractal = new HalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYPOLY:
                fractal = new HalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HALLEYFORMULA:
                fractal = new HalleyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots);
                break;
            case MainWindow.SCHRODER3:
                fractal = new Schroder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODER4:
                fractal = new Schroder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERGENERALIZED3:
                fractal = new SchroderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERGENERALIZED8:
                fractal = new SchroderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERSIN:
                fractal = new SchroderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERCOS:
                fractal = new SchroderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERPOLY:
                fractal = new SchroderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SCHRODERFORMULA:
                fractal = new SchroderFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots);
                break;
            case MainWindow.HOUSEHOLDER3:
                fractal = new Householder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDER4:
                fractal = new Householder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED3:
                fractal = new HouseholderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED8:
                fractal = new HouseholderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERSIN:
                fractal = new HouseholderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERCOS:
                fractal = new HouseholderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERPOLY:
                fractal = new HouseholderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.HOUSEHOLDERFORMULA:
                fractal = new HouseholderFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots);
                break;
            case MainWindow.SECANT3:
                fractal = new Secant3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANT4:
                fractal = new Secant4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTGENERALIZED3:
                fractal = new SecantGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTGENERALIZED8:
                fractal = new SecantGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTCOS:
                fractal = new SecantCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTPOLY:
                fractal = new SecantPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.SECANTFORMULA:
                fractal = new SecantFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots);
                break;
            case MainWindow.STEFFENSEN3:
                fractal = new Steffensen3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.STEFFENSEN4:
                fractal = new Steffensen4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.STEFFENSENGENERALIZED3:
                fractal = new SteffensenGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.STEFFENSENPOLY:
                fractal = new SteffensenPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.STEFFENSENFORMULA:
                fractal = new SteffensenFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.USER_FORMULA:
                if (bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                } else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                } else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if (bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                } else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots);
                } else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots);
                break;
            case MainWindow.MULLER3:
                fractal = new Muller3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLER4:
                fractal = new Muller4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERGENERALIZED3:
                fractal = new MullerGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERGENERALIZED8:
                fractal = new MullerGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERSIN:
                fractal = new MullerSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERCOS:
                fractal = new MullerCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERPOLY:
                fractal = new MullerPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.MULLERFORMULA:
                fractal = new MullerFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots);
                break;
            case MainWindow.PARHALLEY3:
                fractal = new Parhalley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEY4:
                fractal = new Parhalley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYGENERALIZED3:
                fractal = new ParhalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYGENERALIZED8:
                fractal = new ParhalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYSIN:
                fractal = new ParhalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYCOS:
                fractal = new ParhalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYPOLY:
                fractal = new ParhalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.PARHALLEYFORMULA:
                fractal = new ParhalleyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots);
                break;
            case MainWindow.LAGUERRE3:
                fractal = new Laguerre3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERRE4:
                fractal = new Laguerre4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERREGENERALIZED3:
                fractal = new LaguerreGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERREGENERALIZED8:
                fractal = new LaguerreGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERRESIN:
                fractal = new LaguerreSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERRECOS:
                fractal = new LaguerreCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERREPOLY:
                fractal = new LaguerrePoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots);
                break;
            case MainWindow.LAGUERREFORMULA:
                fractal = new LaguerreFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, laguerre_deg, ots);
                break;

        }

        blendingFactory();

        iteration_algorithm = new FractalIterationAlgorithm(fractal);

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds);
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        if (quickDraw && max_iterations > 1000) {
            max_iterations = 1000;
        }

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = fs.filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.d3 = d3s.d3;
        //this.d3_draw_method = d3_draw_method;
        this.detail = d3s.detail;
        this.fiX = d3s.fiX;
        this.fiY = d3s.fiY;
        this.height_ratio = height_ratio;
        this.d3_height_scale = d3s.d3_height_scale;
        this.height_algorithm = d3s.height_algorithm;
        scale = d3s.d3_size_scale;
        this.color_3d_blending = d3s.color_3d_blending;
        this.gaussian_scaling = d3s.gaussian_scaling;
        this.gaussian_weight = d3s.gaussian_weight;
        this.gaussian_kernel_size = d3s.gaussian_kernel;
        this.max_range = d3s.max_range;
        this.min_range = d3s.min_range;
        this.max_scaling = d3s.max_scaling;
        this.shade_height = d3s.shade_height;
        this.shade_choice = d3s.shade_choice;
        this.shade_algorithm = d3s.shade_algorithm;
        this.shade_invert = d3s.shade_invert;

        this.color_blending = color_blending;

        this.bump_map = bms.bump_map;
        this.bumpMappingStrength = bms.bumpMappingStrength;
        this.bumpMappingDepth = bms.bumpMappingDepth;
        this.lightDirectionDegrees = bms.lightDirectionDegrees;
        this.bump_transfer_function = bms.bump_transfer_function;
        this.bump_transfer_factor = bms.bump_transfer_factor;
        this.bumpProcessing = bms.bumpProcessing;
        this.bump_blending = bms.bump_blending;

        this.fake_de = fdes.fake_de;
        this.fake_de_factor = fdes.fake_de_factor;
        this.inverse_fake_dem = fdes.inverse_fake_dem;

        this.rainbow_palette = rps.rainbow_palette;
        this.rainbow_palette_factor = rps.rainbow_palette_factor;
        this.rainbow_algorithm = rps.rainbow_algorithm;

        this.bm_noise_reducing_factor = bms.bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rps.rp_noise_reducing_factor;

        this.entropy_coloring = ens.entropy_coloring;
        this.entropy_palette_factor = ens.entropy_palette_factor;
        this.en_noise_reducing_factor = ens.en_noise_reducing_factor;
        this.entropy_algorithm = ens.entropy_algorithm;

        this.offset_coloring = ofs.offset_coloring;
        this.post_process_offset = ofs.post_process_offset;
        this.of_noise_reducing_factor = ofs.of_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;

        this.en_blending = ens.en_blending;
        this.rp_blending = rps.rp_blending;
        this.of_blending = ofs.of_blending;

        this.entropy_offset = ens.entropy_offset;
        this.rainbow_offset = rps.rainbow_offset;

        this.greyscale_coloring = gss.greyscale_coloring;
        this.gs_noise_reducing_factor = gss.gs_noise_reducing_factor;

        useTraps = ots.useTraps;
        trapMaxDistance = ots.trapMaxDistance;
        trapBlending = ots.trapBlending;
        trapUseSpecialColor = ots.trapUseSpecialColor;

        progress = ptr.getProgressBar();

        if (filters[MainWindow.ANTIALIASING]) {
            if (d3 && polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / detail) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else if (polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else if (d3) {
                x_antialiasing_size = (size / detail) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / detail) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }

        if (domain_coloring) {
            if (polar_projection) {
                action = DOMAIN_POLAR;
            } else {
                action = DOMAIN;
            }
        } else if (polar_projection) {
            action = POLAR;
        } else {
            action = NORMAL;
        }

        switch (transfer_function) {

            case MainWindow.LINEAR:
                color_transfer = new LinearTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer = new SqrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer = new CbrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer = new ForthrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOGARITHM:
                color_transfer = new LogarithmTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOG_LOG:
                color_transfer = new LogLogTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.ATAN:
                color_transfer = new AtanTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
        }

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if (bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if (bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;

        }

        blendingFactory();

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        this.quickDraw = quickDraw;
        tile = TILE_SIZE;

        default_init_val = "c";

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds);
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia-Expander
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptrExpander = ptr;
        this.filters = fs.filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.height_ratio = height_ratio;

        this.color_blending = color_blending;

        this.bump_map = bms.bump_map;
        this.bumpMappingStrength = bms.bumpMappingStrength;
        this.bumpMappingDepth = bms.bumpMappingDepth;
        this.lightDirectionDegrees = bms.lightDirectionDegrees;
        this.bump_transfer_function = bms.bump_transfer_function;
        this.bump_transfer_factor = bms.bump_transfer_factor;
        this.bumpProcessing = bms.bumpProcessing;
        this.bump_blending = bms.bump_blending;

        this.fake_de = fdes.fake_de;
        this.fake_de_factor = fdes.fake_de_factor;
        this.inverse_fake_dem = fdes.inverse_fake_dem;

        this.rainbow_palette = rps.rainbow_palette;
        this.rainbow_palette_factor = rps.rainbow_palette_factor;
        this.rainbow_algorithm = rps.rainbow_algorithm;

        this.bm_noise_reducing_factor = bms.bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rps.rp_noise_reducing_factor;

        this.entropy_coloring = ens.entropy_coloring;
        this.entropy_palette_factor = ens.entropy_palette_factor;
        this.en_noise_reducing_factor = ens.en_noise_reducing_factor;
        this.entropy_algorithm = ens.entropy_algorithm;

        this.offset_coloring = ofs.offset_coloring;
        this.post_process_offset = ofs.post_process_offset;
        this.of_noise_reducing_factor = ofs.of_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;

        this.en_blending = ens.en_blending;
        this.rp_blending = rps.rp_blending;
        this.of_blending = ofs.of_blending;

        this.entropy_offset = ens.entropy_offset;
        this.rainbow_offset = rps.rainbow_offset;

        this.greyscale_coloring = gss.greyscale_coloring;
        this.gs_noise_reducing_factor = gss.gs_noise_reducing_factor;

        useTraps = ots.useTraps;
        trapMaxDistance = ots.trapMaxDistance;
        trapBlending = ots.trapBlending;
        trapUseSpecialColor = ots.trapUseSpecialColor;

        progress = ptrExpander.getProgressBar();

        if (filters[MainWindow.ANTIALIASING]) {
            if (polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }

        if (domain_coloring) {
            if (polar_projection) {
                action = DOMAIN_POLAR_EXPANDER;
            } else {
                action = DOMAIN_EXPANDER;
            }
        } else if (polar_projection) {
            action = POLAR_EXPANDER;
        } else {
            action = NORMAL_EXPANDER;
        }

        switch (transfer_function) {

            case MainWindow.LINEAR:
                color_transfer = new LinearTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer = new SqrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer = new CbrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer = new ForthrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOGARITHM:
                color_transfer = new LogarithmTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOG_LOG:
                color_transfer = new LogLogTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.ATAN:
                color_transfer = new AtanTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
        }

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if (bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if (bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;

        }

        blendingFactory();

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        tile = TILE_SIZE;

        default_init_val = "c";

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds);
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia Map
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.height_ratio = height_ratio;

        this.color_blending = color_blending;

        this.bump_map = bms.bump_map;
        this.bumpMappingStrength = bms.bumpMappingStrength;
        this.bumpMappingDepth = bms.bumpMappingDepth;
        this.lightDirectionDegrees = bms.lightDirectionDegrees;
        this.bump_transfer_function = bms.bump_transfer_function;
        this.bump_transfer_factor = bms.bump_transfer_factor;
        this.bumpProcessing = bms.bumpProcessing;
        this.bump_blending = bms.bump_blending;

        this.fake_de = fdes.fake_de;
        this.fake_de_factor = fdes.fake_de_factor;
        this.inverse_fake_dem = fdes.inverse_fake_dem;

        this.rainbow_palette = rps.rainbow_palette;
        this.rainbow_palette_factor = rps.rainbow_palette_factor;
        this.rainbow_algorithm = rps.rainbow_algorithm;

        this.bm_noise_reducing_factor = bms.bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rps.rp_noise_reducing_factor;

        this.entropy_coloring = ens.entropy_coloring;
        this.entropy_palette_factor = ens.entropy_palette_factor;
        this.en_noise_reducing_factor = ens.en_noise_reducing_factor;
        this.entropy_algorithm = ens.entropy_algorithm;

        this.offset_coloring = ofs.offset_coloring;
        this.post_process_offset = ofs.post_process_offset;
        this.of_noise_reducing_factor = ofs.of_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.en_blending = ens.en_blending;
        this.rp_blending = rps.rp_blending;
        this.of_blending = ofs.of_blending;

        this.entropy_offset = ens.entropy_offset;
        this.rainbow_offset = rps.rainbow_offset;

        this.greyscale_coloring = gss.greyscale_coloring;
        this.gs_noise_reducing_factor = gss.gs_noise_reducing_factor;

        useTraps = ots.useTraps;
        trapMaxDistance = ots.trapMaxDistance;
        trapBlending = ots.trapBlending;
        trapUseSpecialColor = ots.trapUseSpecialColor;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        progress = ptr.getProgressBar();

        if (filters[MainWindow.ANTIALIASING]) {
            if (polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / (TOx - FROMx)) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else {
                x_antialiasing_size = (size / (TOx - FROMx)) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / (TOx - FROMx)) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }

        switch (transfer_function) {

            case MainWindow.LINEAR:
                color_transfer = new LinearTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer = new SqrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer = new CbrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer = new ForthrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOGARITHM:
                color_transfer = new LogarithmTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOG_LOG:
                color_transfer = new LogLogTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.ATAN:
                color_transfer = new AtanTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
        }

        if (polar_projection) {
            action = JULIA_MAP_POLAR;
        } else {
            action = JULIA_MAP;
        }

        double xJuliaCenter, yJuliaCenter;

        if (polar_projection) {
            double start;
            double center = Math.log(size);

            double f, sf, cf, r;
            double muly = (2 * circle_period * Math.PI) / image.getHeight();

            double mulx = muly * height_ratio;

            start = center - mulx * image.getHeight() * 0.5;

            f = ((TOy + FROMy) * 0.5) * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(((TOx + FROMx) * 0.5) * mulx + start);

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter + r * cf, yCenter + r * sf, rotation_vals, rotation_center);

            xJuliaCenter = p.x;
            yJuliaCenter = p.y;
        } else {
            double size_2_x = size * 0.5;
            double size_2_y = (size * height_ratio) * 0.5;

            double temp_xcenter_size = xCenter - size_2_x;
            double temp_ycenter_size = yCenter + size_2_y;
            double temp_size_image_size_x = size / image.getHeight();
            double temp_size_image_size_y = (size * height_ratio) / image.getHeight();

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * ((TOx + FROMx) * 0.5), temp_ycenter_size - temp_size_image_size_y * ((TOy + FROMy) * 0.5), rotation_vals, rotation_center);

            xJuliaCenter = p.x;
            yJuliaCenter = p.y;
        }

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(0.5, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(-2, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if (bail_technique == 0) {
                    fractal = new UserFormulaEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaIterationBasedConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if (bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConditionalConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaCoupledConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;

        }

        blendingFactory();

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        default_init_val = "c";

        convergent_bailout = fractal.getConvergentBailout();

        drawing_done = 0;

    }

    //Julia Preview
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, FiltersSettings fs, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.fast_julia_filters = fast_julia_filters;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.height_ratio = height_ratio;

        this.color_blending = color_blending;

        this.bump_map = bms.bump_map;
        this.bumpMappingStrength = bms.bumpMappingStrength;
        this.bumpMappingDepth = bms.bumpMappingDepth;
        this.lightDirectionDegrees = bms.lightDirectionDegrees;
        this.bump_transfer_function = bms.bump_transfer_function;
        this.bump_transfer_factor = bms.bump_transfer_factor;
        this.bumpProcessing = bms.bumpProcessing;
        this.bump_blending = bms.bump_blending;

        this.fake_de = fdes.fake_de;
        this.fake_de_factor = fdes.fake_de_factor;
        this.inverse_fake_dem = fdes.inverse_fake_dem;

        this.rainbow_palette = rps.rainbow_palette;
        this.rainbow_palette_factor = rps.rainbow_palette_factor;
        this.rainbow_algorithm = rps.rainbow_algorithm;

        this.bm_noise_reducing_factor = bms.bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rps.rp_noise_reducing_factor;

        this.entropy_coloring = ens.entropy_coloring;
        this.entropy_palette_factor = ens.entropy_palette_factor;
        this.en_noise_reducing_factor = ens.en_noise_reducing_factor;
        this.entropy_algorithm = ens.entropy_algorithm;

        this.offset_coloring = ofs.offset_coloring;
        this.post_process_offset = ofs.post_process_offset;
        this.of_noise_reducing_factor = ofs.of_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.en_blending = ens.en_blending;
        this.rp_blending = rps.rp_blending;
        this.of_blending = ofs.of_blending;

        this.entropy_offset = ens.entropy_offset;
        this.rainbow_offset = rps.rainbow_offset;

        this.greyscale_coloring = gss.greyscale_coloring;
        this.gs_noise_reducing_factor = gss.gs_noise_reducing_factor;

        useTraps = ots.useTraps;
        trapMaxDistance = ots.trapMaxDistance;
        trapBlending = ots.trapBlending;
        trapUseSpecialColor = ots.trapUseSpecialColor;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        progress = ptr.getProgressBar();

        if (filters[MainWindow.ANTIALIASING]) {
            if (polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            } else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }

        if (polar_projection) {
            action = FAST_JULIA_POLAR;
        } else {
            action = FAST_JULIA;
        }

        switch (transfer_function) {

            case MainWindow.LINEAR:
                color_transfer = new LinearTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer = new SqrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer = new CbrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer = new ForthrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOGARITHM:
                color_transfer = new LogarithmTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOG_LOG:
                color_transfer = new LogLogTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.ATAN:
                color_transfer = new AtanTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
        }

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if (bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if (bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, xJuliaCenter, yJuliaCenter);
                break;

        }

        blendingFactory();

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

    }

    //Color Cycling
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, BumpMapSettings bms, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.color_cycling_location = color_cycling_location;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        action = COLOR_CYCLING;
        color_cycling = true;

        this.color_cycling_speed = color_cycling_speed;

        this.color_blending = color_blending;

        this.bump_map = bms.bump_map;
        this.bumpMappingStrength = bms.bumpMappingStrength;
        this.bumpMappingDepth = bms.bumpMappingDepth;
        this.lightDirectionDegrees = bms.lightDirectionDegrees;
        this.bump_transfer_function = bms.bump_transfer_function;
        this.bump_transfer_factor = bms.bump_transfer_factor;
        this.bumpProcessing = bms.bumpProcessing;
        this.bump_blending = bms.bump_blending;

        this.fake_de = fdes.fake_de;
        this.fake_de_factor = fdes.fake_de_factor;
        this.inverse_fake_dem = fdes.inverse_fake_dem;

        this.rainbow_palette = rps.rainbow_palette;
        this.rainbow_palette_factor = rps.rainbow_palette_factor;
        this.rainbow_algorithm = rps.rainbow_algorithm;

        this.entropy_coloring = ens.entropy_coloring;
        this.entropy_palette_factor = ens.entropy_palette_factor;
        this.en_noise_reducing_factor = ens.en_noise_reducing_factor;
        this.entropy_algorithm = ens.entropy_algorithm;

        this.offset_coloring = ofs.offset_coloring;
        this.post_process_offset = ofs.post_process_offset;
        this.of_noise_reducing_factor = ofs.of_noise_reducing_factor;

        this.bm_noise_reducing_factor = bms.bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rps.rp_noise_reducing_factor;

        this.en_blending = ens.en_blending;
        this.rp_blending = rps.rp_blending;
        this.of_blending = ofs.of_blending;

        this.entropy_offset = ens.entropy_offset;
        this.rainbow_offset = rps.rainbow_offset;

        this.greyscale_coloring = gss.greyscale_coloring;
        this.gs_noise_reducing_factor = gss.gs_noise_reducing_factor;

        progress = ptr.getProgressBar();

        switch (transfer_function) {

            case MainWindow.LINEAR:
                color_transfer = new LinearTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer = new SqrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer = new CbrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer = new ForthrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOGARITHM:
                color_transfer = new LogarithmTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOG_LOG:
                color_transfer = new LogLogTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.ATAN:
                color_transfer = new AtanTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
        }

        blendingFactory();

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    }

    //Apply Filter
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, FiltersSettings fs, BumpMapSettings bms, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double color_intensity, int transfer_function, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        action = APPLY_PALETTE_AND_FILTER;

        this.color_blending = color_blending;

        this.bump_map = bms.bump_map;
        this.bumpMappingStrength = bms.bumpMappingStrength;
        this.bumpMappingDepth = bms.bumpMappingDepth;
        this.lightDirectionDegrees = bms.lightDirectionDegrees;
        this.bump_transfer_function = bms.bump_transfer_function;
        this.bump_transfer_factor = bms.bump_transfer_factor;
        this.bumpProcessing = bms.bumpProcessing;
        this.bump_blending = bms.bump_blending;

        this.fake_de = fdes.fake_de;
        this.fake_de_factor = fdes.fake_de_factor;
        this.inverse_fake_dem = fdes.inverse_fake_dem;

        this.rainbow_palette = rps.rainbow_palette;
        this.rainbow_palette_factor = rps.rainbow_palette_factor;
        this.rainbow_algorithm = rps.rainbow_algorithm;

        this.entropy_coloring = ens.entropy_coloring;
        this.entropy_palette_factor = ens.entropy_palette_factor;
        this.en_noise_reducing_factor = ens.en_noise_reducing_factor;
        this.entropy_algorithm = ens.entropy_algorithm;

        this.offset_coloring = ofs.offset_coloring;
        this.post_process_offset = ofs.post_process_offset;
        this.of_noise_reducing_factor = ofs.of_noise_reducing_factor;

        this.bm_noise_reducing_factor = bms.bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rps.rp_noise_reducing_factor;

        this.en_blending = ens.en_blending;
        this.rp_blending = rps.rp_blending;
        this.of_blending = ofs.of_blending;

        this.entropy_offset = ens.entropy_offset;
        this.rainbow_offset = rps.rainbow_offset;

        this.greyscale_coloring = gss.greyscale_coloring;
        this.gs_noise_reducing_factor = gss.gs_noise_reducing_factor;

        progress = ptr.getProgressBar();

        switch (transfer_function) {

            case MainWindow.LINEAR:
                color_transfer = new LinearTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer = new SqrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer = new CbrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer = new ForthrtTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOGARITHM:
                color_transfer = new LogarithmTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.LOG_LOG:
                color_transfer = new LogLogTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
            case MainWindow.ATAN:
                color_transfer = new AtanTransferFunction(palette.getPaletteLength(), color_intensity);
                break;
        }

        blendingFactory();

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        drawing_done = 0;

    }

    //Rotate 3d model
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs, int color_blending) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.image = image;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.detail = d3s.detail;
        this.fiX = d3s.fiX;
        this.fiY = d3s.fiY;
        //this.d3_draw_method = d3_draw_method;
        this.color_3d_blending = d3s.color_3d_blending;

        this.color_blending = color_blending;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        scale = d3s.d3_size_scale;

        progress = ptr.getProgressBar();

        blendingFactory();

        if (draw_action) {
            action = ROTATE_3D_MODEL;
        } else {
            action = APPLY_PALETTE_AND_FILTER_3D_MODEL;
            drawing_done = 0;
        }

    }

    @Override
    public void run() {

        try {
            switch (action) {

                case NORMAL:
                    if (quickDraw) {
                        quickDraw();
                    } else {
                        draw();
                    }
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
                case ROTATE_3D_MODEL:
                    rotate3DModel();
                    break;
                case APPLY_PALETTE_AND_FILTER_3D_MODEL:
                    applyPaletteAndFilter3DModel();
                    break;
                case POLAR:
                    if (quickDraw) {
                        quickDrawPolar();
                    } else {
                        drawPolar();
                    }
                    break;
                case FAST_JULIA_POLAR:
                    fastJuliaDrawPolar();
                    break;
                case JULIA_MAP_POLAR:
                    drawJuliaMapPolar();
                    break;
                case DOMAIN:
                    if (quickDraw) {
                        quickDrawDomain();
                    } else {
                        drawDomain();
                    }
                    break;
                case DOMAIN_POLAR:
                    if (quickDraw) {
                        quickDrawDomainPolar();
                    } else {
                        drawDomainPolar();
                    }
                    break;
                case NORMAL_EXPANDER:
                    drawExpander();
                    break;
                case POLAR_EXPANDER:
                    drawPolarExpander();
                    break;
                case DOMAIN_EXPANDER:
                    drawDomainExpander();
                    break;
                case DOMAIN_POLAR_EXPANDER:
                    drawDomainPolarExpander();
                    break;

            }
        } catch (OutOfMemoryError e) {
            if (ptrExpander != null) {
                JOptionPane.showMessageDialog(ptrExpander, "Maximum Heap size was reached.\nPlease set the maximum Heap size to a higher value.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                ptrExpander.savePreferences();
            } else {
                JOptionPane.showMessageDialog(ptr, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                ptr.savePreferences();
            }
            System.exit(-1);
        }

    }

    private void drawDomainExpander() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsDomainAntialiased(image_size);
        } else {
            drawIterationsDomain(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptrExpander.getNumberOfThreads()) {

            image_iterations = null;
            System.gc();

            applyFilters();

            ptrExpander.writeImageToDisk();

            ptrExpander.setOptions(true);

            progress.setValue((image_size * image_size) + (image_size * image_size / 100));

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptrExpander.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptrExpander.getNumberOfThreads() + "</html>");

        }
    }

    private void drawDomainPolarExpander() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsDomainPolarAntialiased(image_size);
        } else {
            drawIterationsDomainPolar(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptrExpander.getNumberOfThreads()) {

            image_iterations = null;
            System.gc();

            applyFilters();

            ptrExpander.writeImageToDisk();

            ptrExpander.setOptions(true);

            progress.setValue((image_size * image_size) + (image_size * image_size / 100));

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptrExpander.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptrExpander.getNumberOfThreads() + "</html>");

        }
    }

    private void drawPolarExpander() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsPolarAntialiased(image_size);
        } else {
            drawIterationsPolar(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptrExpander.getNumberOfThreads()) {

            image_iterations = null;
            System.gc();

            applyFilters();

            ptrExpander.writeImageToDisk();

            ptrExpander.setOptions(true);

            progress.setValue((image_size * image_size) + (image_size * image_size / 100));

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptrExpander.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptrExpander.getNumberOfThreads() + "</html>");

        }
    }

    private void drawExpander() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsAntialiased(image_size);
        } else {
            drawIterations(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptrExpander.getNumberOfThreads()) {

            image_iterations = null;
            System.gc();

            applyFilters();

            ptrExpander.writeImageToDisk();

            ptrExpander.setOptions(true);

            progress.setValue((image_size * image_size) + (image_size * image_size / 100));

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptrExpander.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptrExpander.getNumberOfThreads() + "</html>");

        }
    }

    private void draw() {

        int image_size = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                drawIterations3DAntialiased(image_size);
            } else {
                drawIterations3D(image_size);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsAntialiased(image_size);
        } else {
            drawIterations(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            if (d3) {
                ptr.updateValues("3D mode");
            } else if (iteration_algorithm instanceof JuliaIterationAlgorithm) {
                ptr.updateValues("Julia mode");
            } else {
                ptr.updateValues("Normal mode");
            }

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            if (d3) {
                progress.setValue((detail * detail) + (detail * detail / 100));
            } else {
                progress.setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }
    }

    /*  
     private void drawRandomOrbits() {
     int image_size = image.getHeight();
        
     drawFractalOrbits(image_size);
        
     if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

     applyFilters();

     if(d3) {
     ptr.updateValues("3D mode");
     }
     else if(julia) {
     ptr.updateValues("Julia mode");
     }
     else {
     ptr.updateValues("Normal mode");
     }

     ptr.setOptions(true);
     ptr.setWholeImageDone(true);
     ptr.reloadTitle();
     ptr.getMainPanel().repaint();
     if(d3) {
     progress.setValue((detail * detail) + (detail * detail / 100));
     }
     else {
     progress.setValue((image_size * image_size) + (image_size * image_size / 100));
     }

     int temp2 = image_size * image_size;
     progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
     }
        
     }
    
     private void drawFractalOrbits(int image_size) {
     double size = fractal.getSize();

     double size_2_x = size * 0.5;
     double size_2_y = (size * height_ratio) * 0.5;
     double temp_size_image_size_x = size / image_size;
     double temp_size_image_size_y = (size * height_ratio) / image_size;

     double temp_xcenter_size = fractal.getXCenter() - size_2_x;
     double temp_ycenter_size = fractal.getYCenter() + size_2_y;

     //int pixel_percent = image_size * image_size / 100;

     //Better brute force
     int x, y, loc;
        
     for(int k = 0; k < rgbs.length; k++) {
     rgbs[k] = 0xff000000;
     }

     int condition = image_size * image_size;
     int x0, y0;
     do {

     loc = normal_drawing_algorithm_pixel.getAndIncrement();
            
            
            

     if(loc >= condition) {
     break;
     }
            
     if(Math.random() > 0.4) {
     continue;
     }

     x = loc % image_size;
     y = loc / image_size;

     ArrayList<Complex> orbits = fractal.calculateFractalOrbit2(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
     boolean escaped = false;
     if(orbits.get(orbits.size() - 1).norm_squared() >= 4) {
     escaped = true;
     }
     for(int i = 0; i < orbits.size(); i++) {
     x0 = (int)((orbits.get(i).getRe() - temp_xcenter_size) / temp_size_image_size_x + 0.5);
     y0 = (int)((-orbits.get(i).getIm() + temp_ycenter_size) / temp_size_image_size_y + 0.5);
                
     if(x0 < 0 || x0 >= image_size || y0 < 0 || y0 >= image_size) {
     continue;
     }

     if(escaped) {
     Color a = new Color(rgbs[y0 * image_size + x0]);
     int red = PixelUtils.clamp((int)(a.getRed() + 255 * 0.07));
     int green = PixelUtils.clamp((int)(a.getGreen() + 127 * 0.07));
     int blue = PixelUtils.clamp((int)(a.getBlue() + 39 * 0.07));
     rgbs[y0 * image_size + x0] = new Color(red, green, blue).getRGB();
     }
     else {
     Color a = new Color(rgbs[y0 * image_size + x0]);
     int red = PixelUtils.clamp((int)(a.getRed() + 127 * 0.01));
     int green = PixelUtils.clamp((int)(a.getGreen() + 255 * 0.01));
     int blue = PixelUtils.clamp((int)(a.getBlue() + 39 * 0.01));
     rgbs[y0 * image_size + x0] = new Color(red, green, blue).getRGB();
     }
     //}
     }
     //rgbs[loc] = getColor(image_iterations[loc]);

     //drawing_done++;
     //

        

     } while(true);

     //thread_calculated += drawing_done;
     }*/
    private void quickDraw() {

        int image_size = image.getHeight();

        quickDrawIterations(image_size);

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            if (iteration_algorithm instanceof JuliaIterationAlgorithm) {
                ptr.updateValues("Julia mode");
            } else {
                ptr.updateValues("Normal mode");
            }

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void quickDrawPolar() {

        int image_size = image.getHeight();

        quickDrawIterationsPolar(image_size);

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            if (iteration_algorithm instanceof JuliaIterationAlgorithm) {
                ptr.updateValues("Julia mode");
            } else {
                ptr.updateValues("Normal mode");
            }

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void drawDomain() {

        int image_size = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                drawIterationsDomain3DAntialiased(image_size);
            } else {
                drawIterationsDomain3D(image_size);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsDomainAntialiased(image_size);
        } else {
            drawIterationsDomain(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            if (d3) {
                ptr.updateValues("3D mode");
            } else {
                ptr.updateValues("Domain C. mode");
            }

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            if (d3) {
                progress.setValue((detail * detail) + (detail * detail / 100));
            } else {
                progress.setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }
    }

    private void quickDrawDomain() {

        int image_size = image.getHeight();

        quickDrawIterationsDomain(image_size);

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            ptr.updateValues("Domain C. mode");

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void quickDrawDomainPolar() {

        int image_size = image.getHeight();

        quickDrawIterationsDomainPolar(image_size);

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            ptr.updateValues("Domain C. mode");

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void drawPolar() {
        int image_size = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                drawIterationsPolar3DAntialiased(image_size);
            } else {
                drawIterationsPolar3D(image_size);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsPolarAntialiased(image_size);
        } else {
            drawIterationsPolar(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            if (d3) {
                ptr.updateValues("3D mode");
            } else if (iteration_algorithm instanceof JuliaIterationAlgorithm) {
                ptr.updateValues("Julia mode");
            } else {
                ptr.updateValues("Normal mode");
            }

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            if (d3) {
                progress.setValue((detail * detail) + (detail * detail / 100));
            } else {
                progress.setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }
    }

    private void drawDomainPolar() {
        int image_size = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                drawIterationsDomainPolar3DAntialiased(image_size);
            } else {
                drawIterationsDomainPolar3D(image_size);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsDomainPolarAntialiased(image_size);
        } else {
            drawIterationsDomainPolar(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            if (d3) {
                ptr.updateValues("3D mode");
            } else {
                ptr.updateValues("Domain C. mode");
            }

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            if (d3) {
                progress.setValue((detail * detail) + (detail * detail / 100));
            } else {
                progress.setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }
    }

    protected abstract void drawIterations(int image_size);

    protected int getFinalColor(double result) {

        int color = trapUseSpecialColor ? palette.getSpecialColor() : getPaletteColor(result);
        return useTraps ? getTrapColor(color) : color;

    }
    
    protected int getPaletteColor(double result) {
        
        int colorA;
        if (result == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            colorA = fractal_color;
        } else if (result == -ColorAlgorithm.MAXIMUM_ITERATIONS) {
            colorA = dem_color;
        } else {
            result = color_transfer.transfer(result);
            colorA = result < 0 ? palette.getPaletteColor(result - color_cycling_location) : palette.getPaletteColor(result + color_cycling_location);
        }
        
        return colorA;
        
    }

    private int getTrapColor(int originalColor) {

        OrbitTrap trap = fractal.getOrbitTrap();

        double distance = trap.getDistance();

        if (distance > trapMaxDistance) {
            return originalColor;
        }
        
        distance /= trapMaxDistance;

        int index = (int) (distance * (gradient.length - 1) + 0.5);
        index = gradient.length - 1 - index;

        index = index > gradient.length - 1 ? gradient.length - 1 : index;

        int final_redA = (gradient[index] >> 16) & 0xff;;
        int final_greenA = (gradient[index] >> 8) & 0xff;
        int final_blueA = gradient[index] & 0xff;

        if(trapUseSpecialColor) {
            return 0xff000000 | final_redA << 16 | final_greenA << 8 | final_blueA;
        }
        else {
            return blending.blend(final_redA, final_greenA, final_blueA, (originalColor >> 16) & 0xFF, (originalColor >> 8) & 0xFF, originalColor & 0xFF, 1 - trapBlending);
        }
    }

    private void quickDrawIterationsDomain(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int image_size_tile = image_size / tile, tempx, tempy;
        int condition = (image_size_tile) * (image_size_tile);

        int color, loc2, loc, x, y;

        do {

            loc = QUICKDRAW_THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < QUICKDRAW_THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                tempx = loc % image_size_tile;
                tempy = loc / image_size_tile;

                x = tempx * tile;
                y = tempy * tile;

                loc2 = y * image_size + x;
                image_iterations[loc2] = color = rgbs[loc2] = domain_color.getDomainColor(iteration_algorithm.calculateDomain(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y)));

                thread_calculated++;

                tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
                tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

                for (int i = y; i < tempy; i++) {
                    for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                        rgbs[loc3] = color;
                    }
                }
            }

        } while (true);

    }

    private void quickDrawIterations(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int image_size_tile = image_size / tile, tempx, tempy;
        int condition = (image_size_tile) * (image_size_tile);

        int color, loc2, loc, x, y;

        do {

            loc = QUICKDRAW_THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < QUICKDRAW_THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                tempx = loc % image_size_tile;
                tempy = loc / image_size_tile;

                x = tempx * tile;
                y = tempy * tile;

                loc2 = y * image_size + x;
                image_iterations[loc2] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                color = rgbs[loc2] = getFinalColor(image_iterations[loc2]);

                thread_calculated++;

                tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
                tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

                for (int i = y; i < tempy; i++) {
                    for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                        rgbs[loc3] = color;
                    }
                }
            }

        } while (true);

    }

    private void quickDrawIterationsPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int image_size_tile = image_size / tile, tempx, tempy;
        int condition = (image_size_tile) * (image_size_tile);

        int color, loc2, loc, x, y;

        do {

            loc = QUICKDRAW_THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < QUICKDRAW_THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                tempx = loc % image_size_tile;
                tempy = loc / image_size_tile;

                x = tempx * tile;
                y = tempy * tile;

                loc2 = y * image_size + x;

                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc2] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = rgbs[loc2] = getFinalColor(image_iterations[loc2]);

                thread_calculated++;

                tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
                tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

                for (int i = y; i < tempy; i++) {
                    for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                        rgbs[loc3] = color;
                    }
                }
            }

        } while (true);

    }

    private void quickDrawIterationsDomainPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int image_size_tile = image_size / tile, tempx, tempy;
        int condition = (image_size_tile) * (image_size_tile);

        int color, loc2, loc, x, y;

        do {

            loc = QUICKDRAW_THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < QUICKDRAW_THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                tempx = loc % image_size_tile;
                tempy = loc / image_size_tile;

                x = tempx * tile;
                y = tempy * tile;

                loc2 = y * image_size + x;

                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc2] = color = rgbs[loc2] = domain_color.getDomainColor(iteration_algorithm.calculateDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

                thread_calculated++;

                tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
                tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

                for (int i = y; i < tempy; i++) {
                    for (int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                        rgbs[loc3] = color;
                    }
                }
            }

        } while (true);

    }

    private void drawIterationsDomain(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        //Better brute force
        int x, y, loc;

        int condition = image_size * image_size;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % image_size;
                y = loc / image_size;

                image_iterations[loc] = rgbs[loc] = domain_color.getDomainColor(iteration_algorithm.calculateDomain(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y)));

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while (true);

        thread_calculated += drawing_done;

    }

    protected abstract void drawIterationsPolar(int image_size);

    protected abstract void drawIterationsPolarAntialiased(int image_size);

    private void drawIterationsDomainPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int x, y, loc;

        int condition = image_size * image_size;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % image_size;
                y = loc / image_size;

                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = rgbs[loc] = domain_color.getDomainColor(iteration_algorithm.calculateDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while (true);

        thread_calculated += drawing_done;

    }

    private void drawIterationsDomainPolarAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int x, y, loc;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y_sin = (double[]) (ret[1]);
        double[] antialiasing_y_cos = (double[]) (ret[2]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);

        double temp_samples = supersampling_num + 1;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % image_size;
                y = loc / image_size;

                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = color = domain_color.getDomainColor(iteration_algorithm.calculateDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    color = domain_color.getDomainColor(iteration_algorithm.calculateDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2)));

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while (true);

        thread_calculated += drawing_done;

    }

    private void draw3D(int image_size, boolean update_progress) {

        int x, y;

        int w2 = image_size / 2;

        int n1 = detail - 1;

        double dx = image_size / (double) detail;

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        double mod;
        double norm_0_0, norm_0_1, norm_0_2, norm_1_0, norm_1_1, norm_1_2;

        int pixel_percent = detail * detail / 100;

        for (x = FROMx; x < TOx; x++) {
            double c1 = dx * x - w2;
            for (y = FROMy; y < TOy; y++) {

                if (y < n1 && x < n1) {
                    norm_0_0 = vert[x][y][1] - vert[x + 1][y][1];
                    norm_0_1 = dx;
                    norm_0_2 = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(norm_0_0 * norm_0_0 + norm_0_1 * norm_0_1 + norm_0_2 * norm_0_2) / 255.5;
                    norm_0_0 /= mod;
                    norm_0_1 /= mod;
                    norm_0_2 /= mod;

                    norm_1_0 = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    norm_1_1 = dx;
                    norm_1_2 = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(norm_1_0 * norm_1_0 + norm_1_1 * norm_1_1 + norm_1_2 * norm_1_2) / 255.5;
                    norm_1_0 /= mod;
                    norm_1_1 /= mod;
                    norm_1_2 /= mod;

                    Norm1z[x][y][0] = (float) (m20 * norm_0_0 + m21 * norm_0_1 + m22 * norm_0_2);
                    Norm1z[x][y][1] = (float) (m20 * norm_1_0 + m21 * norm_1_1 + m22 * norm_1_2);
                }

                double c2 = dx * y - w2;
                vert1[x][y][0] = (float) (m00 * c1 + m02 * c2);
                vert1[x][y][1] = (float) (m10 * c1 + m11 * vert[x][y][1] + m12 * c2);

                if (update_progress) {
                    drawing_done++;
                }
            }

            if (update_progress && (drawing_done / pixel_percent >= 1)) {
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, update_progress);

        }

    }

    private void drawIterations3D(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail, dr_x = size / detail, dr_y = (size * height_ratio) / detail;

        int x, y, loc;

        int condition = detail * detail;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                temp = iteration_algorithm.calculate3D(new Complex(temp_xcenter_size + dr_x * x, temp_ycenter_size - dr_y * y));
                vert[x][y][1] = (float) (temp[0]);
                vert[x][y][0] = getFinalColor(temp[1]);
                image_iterations[y * detail + x] = temp[1];

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        postProcess(detail);

        heightProcessing();

        calculate3DVectors(dx, w2);

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, true);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawIterationsDomain3D(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        int w2 = image_size / 2;
        double dx = image_size / (double) detail, dr_x = size / detail, dr_y = (size * height_ratio) / detail;

        int x, y, loc;

        int condition = detail * detail;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                Complex a = iteration_algorithm.calculateDomain(new Complex(temp_xcenter_size + dr_x * x, temp_ycenter_size - dr_y * y));

                vert[x][y][1] = calaculateDomainColoringHeight(a);
                vert[x][y][0] = domain_color.getDomainColor(a);
                image_iterations[y * detail + x] = vert[x][y][1];

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        heightProcessing();

        calculate3DVectors(dx, w2);

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, true);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawIterationsPolar3D(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / detail;

        double mulx = muly * height_ratio;

        start = center - mulx * detail * 0.5;

        int x, y, loc;

        int condition = detail * detail;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                f2 = y * muly;
                sf2 = Math.sin(f2);
                cf2 = Math.cos(f2);

                r2 = Math.exp(x * mulx + start);

                temp = iteration_algorithm.calculate3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                vert[x][y][1] = (float) (temp[0]);
                vert[x][y][0] = getFinalColor(temp[1]);
                image_iterations[y * detail + x] = temp[1];

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        postProcess(detail);

        heightProcessing();

        calculate3DVectors(dx, w2);

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, true);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawIterationsDomainPolar3D(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / detail;

        double mulx = muly * height_ratio;

        start = center - mulx * detail * 0.5;

        int x, y, loc;

        int condition = detail * detail;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                f2 = y * muly;
                sf2 = Math.sin(f2);
                cf2 = Math.cos(f2);

                r2 = Math.exp(x * mulx + start);

                Complex a = iteration_algorithm.calculateDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));

                vert[x][y][1] = calaculateDomainColoringHeight(a);
                vert[x][y][0] = domain_color.getDomainColor(a);
                image_iterations[y * detail + x] = vert[x][y][1];

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        heightProcessing();

        calculate3DVectors(dx, w2);

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, true);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawIterationsPolar3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2, sf3, cf3, r3;
        double muly = (2 * circle_period * Math.PI) / detail;

        double mulx = muly * height_ratio;

        start = center - mulx * detail * 0.5;

        int x, y, loc;

        int condition = detail * detail;

        int red, green, blue, color;

        double height;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y_sin = (double[]) (ret[1]);
        double[] antialiasing_y_cos = (double[]) (ret[2]);

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                f2 = y * muly;
                sf2 = Math.sin(f2);
                cf2 = Math.cos(f2);

                r2 = Math.exp(x * mulx + start);

                temp = iteration_algorithm.calculate3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                height = temp[0];
                color = getFinalColor(temp[1]);
                image_iterations[y * detail + x] = temp[1];

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int k = 0; k < supersampling_num; k++) {

                    sf3 = sf2 * antialiasing_y_cos[k] + cf2 * antialiasing_y_sin[k];
                    cf3 = cf2 * antialiasing_y_cos[k] - sf2 * antialiasing_y_sin[k];

                    r3 = r2 * antialiasing_x[k];

                    temp = iteration_algorithm.calculate3D(new Complex(xcenter + r3 * cf3, ycenter + r3 * sf3));
                    color = getFinalColor(temp[1]);

                    height += temp[0];
                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                vert[x][y][1] = (float) (height / temp_samples);
                vert[x][y][0] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        postProcess(detail);

        heightProcessing();

        calculate3DVectors(dx, w2);

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, true);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawIterationsDomainPolar3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2, sf3, cf3, r3;
        double muly = (2 * circle_period * Math.PI) / detail;

        double mulx = muly * height_ratio;

        start = center - mulx * detail * 0.5;

        int x, y, loc;

        int condition = detail * detail;

        int red, green, blue, color;

        double height;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y_sin = (double[]) (ret[1]);
        double[] antialiasing_y_cos = (double[]) (ret[2]);

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                f2 = y * muly;
                sf2 = Math.sin(f2);
                cf2 = Math.cos(f2);

                r2 = Math.exp(x * mulx + start);

                Complex a = iteration_algorithm.calculateDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));

                height = calaculateDomainColoringHeight(a);
                color = domain_color.getDomainColor(a);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int k = 0; k < supersampling_num; k++) {

                    sf3 = sf2 * antialiasing_y_cos[k] + cf2 * antialiasing_y_sin[k];
                    cf3 = cf2 * antialiasing_y_cos[k] - sf2 * antialiasing_y_sin[k];

                    r3 = r2 * antialiasing_x[k];

                    a = iteration_algorithm.calculateDomain(new Complex(xcenter + r3 * cf3, ycenter + r3 * sf3));

                    height += calaculateDomainColoringHeight(a);
                    color = domain_color.getDomainColor(a);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                vert[x][y][1] = (float) (height / temp_samples);
                vert[x][y][0] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                image_iterations[y * detail + x] = vert[x][y][1];

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        heightProcessing();

        calculate3DVectors(dx, w2);

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, true);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawIterations3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail, dr_x = size / detail, dr_y = (size * height_ratio) / detail;

        int x, y, loc;

        int condition = detail * detail;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        double temp_x0, temp_y0, height;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y = (double[]) (ret[1]);

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                temp = iteration_algorithm.calculate3D(new Complex(temp_x0 = temp_xcenter_size + dr_x * x, temp_y0 = temp_ycenter_size - dr_y * y));
                height = temp[0];
                color = getFinalColor(temp[1]);
                image_iterations[y * detail + x] = temp[1];

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int k = 0; k < supersampling_num; k++) {
                    temp = iteration_algorithm.calculate3D(new Complex(temp_x0 + antialiasing_x[k], temp_y0 + antialiasing_y[k]));
                    color = getFinalColor(temp[1]);

                    height += temp[0];
                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                vert[x][y][1] = (float) (height / temp_samples);
                vert[x][y][0] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        postProcess(detail);

        heightProcessing();

        calculate3DVectors(dx, w2);

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, true);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawIterationsDomain3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail, dr_x = size / detail, dr_y = (size * height_ratio) / detail;

        int x, y, loc;

        int condition = detail * detail;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        double temp_x0, temp_y0, height;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y = (double[]) (ret[1]);

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                Complex a = iteration_algorithm.calculateDomain(new Complex(temp_x0 = temp_xcenter_size + dr_x * x, temp_y0 = temp_ycenter_size - dr_y * y));

                height = calaculateDomainColoringHeight(a);
                color = domain_color.getDomainColor(a);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int k = 0; k < supersampling_num; k++) {
                    a = iteration_algorithm.calculateDomain(new Complex(temp_x0 + antialiasing_x[k], temp_y0 + antialiasing_y[k]));

                    color = domain_color.getDomainColor(a);
                    height += calaculateDomainColoringHeight(a);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                vert[x][y][1] = (float) (height / temp_samples);
                vert[x][y][0] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                image_iterations[y * detail + x] = vert[x][y][1];

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while (true);

        heightProcessing();

        calculate3DVectors(dx, w2);

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(w2, true);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawIterationsDomainAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        //better Brute force with antialiasing
        int x, y, loc;
        int color;

        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y = (double[]) (ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % image_size;
                y = loc / image_size;

                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations[loc] = color = domain_color.getDomainColor(iteration_algorithm.calculateDomain(new Complex(temp_x0, temp_y0)));

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    color = domain_color.getDomainColor(iteration_algorithm.calculateDomain(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i])));

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while (true);

        thread_calculated += drawing_done;

    }

    protected abstract void drawIterationsAntialiased(int image_size);

    private void fastJuliaDraw() {

        int image_size = image.getHeight();

        if (fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
            drawFastJuliaAntialiased(image_size);
        } else {
            drawFastJulia(image_size);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            if (fast_julia_filters) {
                applyFiltersNoProgress();
            }

            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, 0, image_size - 1, image_size - 1);
            ptr.getMainPanel().getGraphics().drawImage(image, ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);

        }

    }

    private void fastJuliaDrawPolar() {

        int image_size = image.getHeight();

        if (fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
            drawFastJuliaPolarAntialiased(image_size);
        } else {
            drawFastJuliaPolar(image_size);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            if (fast_julia_filters) {
                applyFilters();
            }

            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, 0, image_size - 1, image_size - 1);
            ptr.getMainPanel().getGraphics().drawImage(image, ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);

        }

    }

    private int offsetColoring(double[] image_iterations, int i, int j, int image_size, int color) {

        if (!useTraps && Math.abs(image_iterations[i * image_size + j]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getPaletteColor(image_iterations[i * image_size + j]);
        }

        double coef = 1 - of_blending;

        double res = OutColorAlgorithm.transformResultToHeight(image_iterations[i * image_size + j], max_iterations);

        int color2 = getPaletteColor(res + post_process_offset);

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int fc_red = (color2 >> 16) & 0xFF;
        int fc_green = (color2 >> 8) & 0xFF;
        int fc_blue = color2 & 0xFF;

        return blending.blend(r, g, b, fc_red, fc_green, fc_blue, coef);
    }

    private int greyscaleColoring(double[] image_iterations, int i, int j, int image_size, int color) {

        if (!useTraps && Math.abs(image_iterations[i * image_size + j]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getPaletteColor(image_iterations[i * image_size + j]);
        }

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int greyscale = (int) ((r + g + b) / 3.0 + 0.5);

        return 0xff000000 | (greyscale << 16) | (greyscale << 8) | greyscale;

    }

    private int entropyColoring(double[] image_iterations, int i, int j, int image_size, int color) {

        if (!useTraps && Math.abs(image_iterations[i * image_size + j]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getPaletteColor(image_iterations[i * image_size + j]);
        }

        int kernel_size = 3;
        int kernel_size2 = kernel_size / 2;

        double min_value = Double.MAX_VALUE;

        double[] values = new double[kernel_size * kernel_size];
        int length = 0;

        for (int k = i - kernel_size2, p = 0; p < kernel_size; k++, p++) {
            for (int l = j - kernel_size2, t = 0; t < kernel_size; l++, t++) {

                if (k >= 0 && k < image_size && l >= 0 && l < image_size) {

                    double temp = OutColorAlgorithm.transformResultToHeight(image_iterations[k * image_size + l], max_iterations);

                    values[p * kernel_size + t] = temp;

                    if (temp < min_value) {
                        min_value = temp;
                    }
                    length++;

                }

            }
        }

        double sum = 0;
        for (int k = 0; k < length; k++) {
            values[k] -= min_value;
            sum += values[k];
        }

        double sum2 = 0;
        for (int k = 0; k < length; k++) {
            values[k] /= sum;

            if (values[k] > 0) {
                sum2 += values[k] * Math.log(values[k]);
            }

        }
        sum2 /= length;
        sum2 *= 10;

        double coef = 1 - en_blending;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int temp_red = 0, temp_green = 0, temp_blue = 0;

        if (entropy_algorithm == 0) {
            double res = OutColorAlgorithm.transformResultToHeight(image_iterations[i * image_size + j], max_iterations);

            int color2 = getPaletteColor(entropy_offset + res + entropy_palette_factor * Math.abs(sum2));

            temp_red = (color2 >> 16) & 0xFF;
            temp_green = (color2 >> 8) & 0xFF;
            temp_blue = color2 & 0xFF;
        } else {
            double temp = Math.abs(sum2) * entropy_palette_factor;

            if (temp > 1) {
                temp = (int) temp % 2 == 1 ? 1 - (temp - (int) temp) : (temp - (int) temp);
            }

            int index = (int) (temp * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            temp_red = (gradient[index] >> 16) & 0xff;
            temp_green = (gradient[index] >> 8) & 0xff;
            temp_blue = gradient[index] & 0xff;
        }

        return blending.blend(r, g, b, temp_red, temp_green, temp_blue, coef);
    }

    private int paletteRainbow(double[] image_iterations, int i, int j, int image_size, int color) {

        int k0 = image_size * i + j;
        int kx = k0 + image_size;
        int sx = 1;

        int kx2 = k0 - image_size;
        int sx2 = -1;

        int ky = k0 + 1;
        int sy = 1;

        int ky2 = k0 - 1;
        int sy2 = -1;

        double zx = 0;
        double zy = 0;
        double zx2 = 0;
        double zy2 = 0;

        if (!useTraps && Math.abs(image_iterations[k0]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getPaletteColor(image_iterations[k0]);
        }

        double n0 = OutColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

        if (i < image_size - 1) {
            double nx = OutColorAlgorithm.transformResultToHeight(image_iterations[kx], max_iterations);
            zx = sx * (nx - n0);
        }

        if (j < image_size - 1) {
            double ny = OutColorAlgorithm.transformResultToHeight(image_iterations[ky], max_iterations);
            zy = sy * (ny - n0);
        }

        if (i > 0) {
            double nx2 = OutColorAlgorithm.transformResultToHeight(image_iterations[kx2], max_iterations);
            zx2 = sx2 * (nx2 - n0);
        }

        if (j > 0) {
            double ny2 = OutColorAlgorithm.transformResultToHeight(image_iterations[ky2], max_iterations);
            zy2 = sy2 * (ny2 - n0);
        }

        double zz = 1.0;

        double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);
        //zz /= z;
        zx /= z;
        zy /= z;

        double hue = Math.atan2(zy, zx) / Math.PI * 0.5;

        hue = hue < 0 ? hue + 1 : hue;

        double coef = 1 - rp_blending;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int temp_red = 0, temp_green = 0, temp_blue = 0;

        if (rainbow_algorithm == 0) {
            int color2 = getPaletteColor(rainbow_offset + hue * palette.getPaletteLength() * rainbow_palette_factor);

            temp_red = (color2 >> 16) & 0xFF;
            temp_green = (color2 >> 8) & 0xFF;
            temp_blue = color2 & 0xFF;
        } else {
            hue *= 2 * rainbow_palette_factor;

            hue = hue % 2.0;

            int index = hue < 1 ? (int) (hue * (gradient.length - 1) + 0.5) : (int) ((1 - (hue - 1)) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            temp_red = (gradient[index] >> 16) & 0xff;
            temp_green = (gradient[index] >> 8) & 0xff;
            temp_blue = gradient[index] & 0xff;
        }

        return blending.blend(r, g, b, temp_red, temp_green, temp_blue, coef);

    }

    private int postProcessingSmoothing(int new_color, double[] image_iterations, int color, int i, int j, int image_size, double factor) {

        int k0 = image_size * i + j;
        int kx = k0 + image_size;
        int sx = 1;

        int kx2 = k0 - image_size;
        int sx2 = -1;

        int ky = k0 + 1;
        int sy = 1;

        int ky2 = k0 - 1;
        int sy2 = -1;

        double zx = 0;
        double zy = 0;
        double zx2 = 0;
        double zy2 = 0;

        double n0 = OutColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

        if (i < image_size - 1) {
            double nx = OutColorAlgorithm.transformResultToHeight(image_iterations[kx], max_iterations);
            zx = sx * (nx - n0);
        }

        if (j < image_size - 1) {
            double ny = OutColorAlgorithm.transformResultToHeight(image_iterations[ky], max_iterations);
            zy = sy * (ny - n0);
        }

        if (i > 0) {
            double nx2 = OutColorAlgorithm.transformResultToHeight(image_iterations[kx2], max_iterations);
            zx2 = sx2 * (nx2 - n0);
        }

        if (j > 0) {
            double ny2 = OutColorAlgorithm.transformResultToHeight(image_iterations[ky2], max_iterations);
            zy2 = sy2 * (ny2 - n0);
        }

        double zz = 1 / factor;

        double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);

        zz /= z;

        double coef = zz;
        coef = 1 - coef;

        int r = color & 0xFF0000;
        int g = color & 0x00FF00;
        int b = color & 0x0000FF;

        int fc_red = new_color & 0xFF0000;
        int fc_green = new_color & 0x00FF00;
        int fc_blue = new_color & 0x0000FF;

        final int ret = 0xff000000 | (int) (fc_red + (r - fc_red) * coef + 0.5) & 0xFF0000 | (int) (fc_green + (g - fc_green) * coef + 0.5) & 0x00FF00 | (int) (fc_blue + (b - fc_blue) * coef + 0.5);

        return ret;

    }

    private int pseudoDistanceEstimation(double[] image_iterations, int color, int i, int j, int image_size) {

        int k0 = image_size * i + j;
        int kx = k0 + image_size;
        int sx = 1;

        int kx2 = k0 - image_size;
        int sx2 = -1;

        int ky = k0 + 1;
        int sy = 1;

        int ky2 = k0 - 1;
        int sy2 = -1;

        double zx = 0;
        double zy = 0;
        double zx2 = 0;
        double zy2 = 0;

        double n0 = OutColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

        if (i < image_size - 1) {
            double nx = OutColorAlgorithm.transformResultToHeight(image_iterations[kx], max_iterations);
            zx = sx * (nx - n0);
        }

        if (j < image_size - 1) {
            double ny = OutColorAlgorithm.transformResultToHeight(image_iterations[ky], max_iterations);
            zy = sy * (ny - n0);
        }

        if (i > 0) {
            double nx2 = OutColorAlgorithm.transformResultToHeight(image_iterations[kx2], max_iterations);
            zx2 = sx2 * (nx2 - n0);
        }

        if (j > 0) {
            double ny2 = OutColorAlgorithm.transformResultToHeight(image_iterations[ky2], max_iterations);
            zy2 = sy2 * (ny2 - n0);
        }

        double zz = 1 / fake_de_factor;

        double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);

        zz /= z;

        double coef = zz;

        int r = color & 0xFF0000;
        int g = color & 0x00FF00;
        int b = color & 0x0000FF;

        int fc_red = dem_color & 0xFF0000;
        int fc_green = dem_color & 0x00FF00;
        int fc_blue = dem_color & 0x0000FF;

        if (inverse_fake_dem) {
            coef = 1 - coef;
        }

        final int ret = 0xff000000 | (int) (fc_red + (r - fc_red) * coef + 0.5) & 0xFF0000 | (int) (fc_green + (g - fc_green) * coef + 0.5) & 0x00FF00 | (int) (fc_blue + (b - fc_blue) * coef + 0.5);

        return ret;

    }

    protected void applyPostProcessing(int image_size, double[] image_iterations) {

        double gradCorr, sizeCorr = 0, lightAngleRadians, lightx = 0, lighty = 0;

        if (bump_map) {
            gradCorr = Math.pow(2, (bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = image_size / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bumpMappingDepth) * 0.16);
            lightAngleRadians = Math.toRadians(lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }

        double gradx, grady, dotp, gradAbs, cosAngle, smoothGrad;
        int modified;

        for (int y = FROMy; y < TOy; y++) {
            for (int x = FROMx; x < TOx; x++) {
                int index = y * image_size + x;

                if (d3) {
                    modified = (int) vert[x][y][0];
                } else {
                    modified = rgbs[index];
                }

                if (offset_coloring) {
                    int original_color = modified;
                    modified = offsetColoring(image_iterations, y, x, image_size, original_color);
                    modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, of_noise_reducing_factor);
                }

                if (entropy_coloring) {
                    int original_color = modified;
                    modified = entropyColoring(image_iterations, y, x, image_size, original_color);
                    modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, en_noise_reducing_factor);
                }

                if (rainbow_palette) {
                    int original_color = modified;
                    modified = paletteRainbow(image_iterations, y, x, image_size, original_color);
                    modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, rp_noise_reducing_factor);
                }

                if (greyscale_coloring) {
                    int original_color = modified;
                    modified = greyscaleColoring(image_iterations, y, x, image_size, original_color);
                    modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, gs_noise_reducing_factor);
                }

                if (bump_map) {
                    gradx = getGradientX(image_iterations, index, image_size);
                    grady = getGradientY(image_iterations, index, image_size);

                    dotp = gradx * lightx + grady * lighty;

                    int original_color = modified;

                    if (bumpProcessing == 0) {
                        if (dotp != 0) {
                            gradAbs = Math.sqrt(gradx * gradx + grady * grady);
                            cosAngle = dotp / gradAbs;
                            smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;
                            //smoothGrad = Math.atan(gradAbs * sizeCorr);
                            modified = changeBrightnessOfColorScaling(modified, cosAngle * smoothGrad);
                        }
                    } else if (dotp != 0 || (dotp == 0 && !isInt(image_iterations[index]))) {
                        gradAbs = Math.sqrt(gradx * gradx + grady * grady);
                        cosAngle = dotp / gradAbs;
                        smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;
                        //smoothGrad = Math.atan(gradAbs * sizeCorr);
                        modified = changeBrightnessOfColorBlending(modified, cosAngle * smoothGrad);
                    }

                    modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, bm_noise_reducing_factor);
                }

                if (fake_de) {
                    modified = pseudoDistanceEstimation(image_iterations, modified, y, x, image_size);
                }

                if (d3) {
                    vert[x][y][0] = modified;
                } else {
                    rgbs[index] = modified;
                }
            }
        }
    }

    private boolean isInt(double val) {

        return (val - (int) val) == 0;

    }

    protected abstract void drawFastJulia(int image_size);

    protected abstract void drawFastJuliaPolar(int image_size);

    protected abstract void drawFastJuliaAntialiased(int image_size);

    protected abstract void drawFastJuliaPolarAntialiased(int image_size);

    private void colorCycling() {

        if (!color_cycling) {
            return;
        }

        ptr.setWholeImageDone(false);

        int image_size = image.getHeight();

        color_cycling_location++;

        color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;

        for (int y = FROMy; y < TOy; y++) {
            for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                rgbs[loc] = getPaletteColor(image_iterations[loc]);
            }
        }

        postProcess(image_size);

        try {
            if (color_cycling_filters_sync.await() == 0) {
                applyFiltersNoProgress();

                ptr.setWholeImageDone(true);

                ptr.getMainPanel().repaint();

                ptr.updatePalettePreview(color_cycling_location);

                //progress.setForeground(new Color(palette.getPaletteColor(color_cycling_location)));
            }
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        try {
            Thread.sleep(color_cycling_speed + 35);
        } catch (InterruptedException ex) {
        }

        try {
            color_cycling_restart_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        colorCycling();

    }

    private void rotate3DModel() {

        int image_size = image.getHeight();

        draw3D(image_size, false);

        if (drawing_done != 0) {
            update(drawing_done);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFiltersNoProgress();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            progress.setValue((detail * detail) + (detail * detail / 100));
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void applyPaletteAndFilter3DModel() {

        int image_size = image.getHeight();

        draw3D(image_size, true);

        if (drawing_done != 0) {
            update(drawing_done);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            progress.setValue((detail * detail) + (detail * detail / 100));
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void applyPaletteAndFilter() {

        int image_size = image.getHeight();

        changePalette(image_size);

        if (drawing_done != 0) {
            update(drawing_done);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            progress.setValue((image_size * image_size) + (image_size * image_size / 100));
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void changePalette(int image_size) {

        int pixel_percent = image_size * image_size / 100;

        if (ptr.getDomainColoring()) {
            for (int y = FROMy; y < TOy; y++) {
                for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                    rgbs[loc] = (int) image_iterations[loc];

                    drawing_done++;
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            }
        } else {
            for (int y = FROMy; y < TOy; y++) {
                for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                    rgbs[loc] = getPaletteColor(image_iterations[loc]);

                    drawing_done++;
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            }
        }

        if ((bump_map || fake_de || rainbow_palette || entropy_coloring || offset_coloring || greyscale_coloring) && !ptr.getDomainColoring()) {
            try {
                post_processing_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size, image_iterations);
        }

    }

    private void drawJuliaMap() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            juliaMapAntialiased(image_size);
        } else {
            juliaMap(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        if (finalize_sync.incrementAndGet() == ptr.getJuliaMapSlices()) {

            applyFilters();

            ptr.updateValues("Julia Map mode");
            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            progress.setValue((image_size * image_size) + (image_size * image_size / 100));
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getJuliaMapSlices() + "</html>");
        }

    }

    private void drawJuliaMapPolar() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            juliaMapPolarAntialiased(image_size);
        } else {
            juliaMapPolar(image_size);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        if (finalize_sync.incrementAndGet() == ptr.getJuliaMapSlices()) {

            applyFilters();

            ptr.updateValues("Julia Map mode");
            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            progress.setValue((image_size * image_size) + (image_size * image_size / 100));
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getJuliaMapSlices() + "</html>");
        }

    }

    private void juliaMap(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / (TOx - FROMx);
        double temp_size_image_size_y = (size * height_ratio) / (TOx - FROMx);

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        double temp_y0 = temp_ycenter_size;
        double temp_x0 = temp_xcenter_size;

        for (int y = FROMy; y < TOy; y++, temp_y0 -= temp_size_image_size_y) {
            for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size_x, loc++) {

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                rgbs[loc] = getFinalColor(image_iterations[loc]);

                drawing_done++;

            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

            temp_x0 = temp_xcenter_size;

        }

        postProcess(image_size);

    }

    private void juliaMapPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / (TOx - FROMx);

        double mulx = muly * height_ratio;

        start = center - mulx * (TOx - FROMx) * 0.5;

        for (int y = FROMy, y1 = 0; y < TOy; y++, y1++) {
            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);
            for (int x = FROMx, x1 = 0, loc = y * image_size + x; x < TOx; x++, loc++, x1++) {

                r = Math.exp(x1 * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations[loc]);

                drawing_done++;

            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        postProcess(image_size);

    }

    private void juliaMapAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / (TOx - FROMx);
        double temp_size_image_size_y = (size * height_ratio) / (TOx - FROMx);

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        double temp_y0 = temp_ycenter_size;
        double temp_x0 = temp_xcenter_size;

        double temp_result;

        int color;

        int red, green, blue;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y = (double[]) (ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for (int y = FROMy; y < TOy; y++, temp_y0 -= temp_size_image_size_y) {
            for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size_x, loc++) {

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

            temp_x0 = temp_xcenter_size;

        }

        postProcess(image_size);

    }

    private void juliaMapPolarAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, cf2, sf2, r2;
        double muly = (2 * circle_period * Math.PI) / (TOx - FROMx);

        double mulx = muly * height_ratio;

        start = center - mulx * (TOx - FROMx) * 0.5;

        double temp_result;

        int color;

        int red, green, blue;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[]) (ret[0]);
        double[] antialiasing_y_sin = (double[]) (ret[1]);
        double[] antialiasing_y_cos = (double[]) (ret[2]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for (int y = FROMy, y1 = 0; y < TOy; y++, y1++) {
            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);
            for (int x = FROMx, x1 = 0, loc = y * image_size + x; x < TOx; x++, loc++, x1++) {
                r = Math.exp(x1 * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        postProcess(image_size);

    }

    private void shadeColorBasedOnHeight() {

        double new_max = max - min;

        for (int x = FROMx; x < TOx; x++) {
            for (int y = FROMy; y < TOy; y++) {
                int r = ((int) vert[x][y][0] >> 16) & 0xff;
                int g = ((int) vert[x][y][0] >> 8) & 0xff;
                int b = (int) vert[x][y][0] & 0xff;

                double coef = 0;

                switch (shade_algorithm) {
                    case 0: //lerp
                        coef = ((vert[x][y][1] - min) / new_max - 0.5) * 2;
                        break;
                    case 1://cos lerp
                        coef = -Math.cos((vert[x][y][1] - min) / new_max * Math.PI);
                        break;
                    case 2:
                        double lim = 0.1;
                        if ((vert[x][y][1] - min) / new_max <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / new_max * (1 / lim));
                        } else if ((vert[x][y][1] - min) / new_max >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / new_max) * (1 / lim);
                        } else {
                            coef = 0;
                        }
                        break;
                    case 3:
                        lim = 0.2;
                        if ((vert[x][y][1] - min) / new_max <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / new_max * (1 / lim));
                        } else if ((vert[x][y][1] - min) / new_max >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / new_max) * (1 / lim);
                        } else {
                            coef = 0;
                        }
                        break;
                    case 4:
                        lim = 0.3;
                        if ((vert[x][y][1] - min) / new_max <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / new_max * (1 / lim));
                        } else if ((vert[x][y][1] - min) / new_max >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / new_max) * (1 / lim);
                        } else {
                            coef = 0;
                        }
                        break;
                    case 5:
                        lim = 0.4;
                        if ((vert[x][y][1] - min) / new_max <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / new_max * (1 / lim));
                        } else if ((vert[x][y][1] - min) / new_max >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / new_max) * (1 / lim);
                        } else {
                            coef = 0;
                        }
                        break;
                }

                if (shade_invert) {
                    coef *= -1;
                }

                if (shade_choice == 2) { //-1 to 0 only
                    if (coef > 0) {
                        coef = 0;
                    }
                } else if (shade_choice == 1) { //0 to 1 only
                    if (coef < 0) {
                        coef = 0;
                    }
                }

                int col = 0;
                int col2 = 255 - col;

                if (coef < 0) {
                    r = (int) (col * Math.abs(coef) + r * (1 - Math.abs(coef)));
                    g = (int) (col * Math.abs(coef) + g * (1 - Math.abs(coef)));
                    b = (int) (col * Math.abs(coef) + b * (1 - Math.abs(coef)));
                } else {
                    r = (int) (col2 * coef + r * (1 - coef));
                    g = (int) (col2 * coef + g * (1 - coef));
                    b = (int) (col2 * coef + b * (1 - coef));
                }

                vert[x][y][0] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
    }

    protected void update(int new_percent) {

        progress.setValue(progress.getValue() + new_percent);

    }

    private double getGradientX(double[] image_iterations, int index, int size) {

        int x = index % size;
        double it = OutColorAlgorithm.transformResultToHeight(image_iterations[index], max_iterations);

        if (x == 0) {
            return (OutColorAlgorithm.transformResultToHeight(image_iterations[index + 1], max_iterations) - it) * 2;
        } else if (x == size - 1) {
            return (it - OutColorAlgorithm.transformResultToHeight(image_iterations[index - 1], max_iterations)) * 2;
        } else {
            double diffL = it - OutColorAlgorithm.transformResultToHeight(image_iterations[index - 1], max_iterations);
            double diffR = it - OutColorAlgorithm.transformResultToHeight(image_iterations[index + 1], max_iterations);
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }

    }

    private double getGradientY(double[] image_iterations, int index, int size) {

        int y = index / size;
        double it = OutColorAlgorithm.transformResultToHeight(image_iterations[index], max_iterations);

        if (y == 0) {
            return (it - OutColorAlgorithm.transformResultToHeight(image_iterations[index + size], max_iterations)) * 2;
        } else if (y == size - 1) {
            return (OutColorAlgorithm.transformResultToHeight(image_iterations[index - size], max_iterations) - it) * 2;
        } else {
            double diffU = it - OutColorAlgorithm.transformResultToHeight(image_iterations[index - size], max_iterations);
            double diffD = it - OutColorAlgorithm.transformResultToHeight(image_iterations[index + size], max_iterations);
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }

    }

    private double getBumpCoef(double delta) {
        double mul = 0;

        switch (bump_transfer_function) {
            case 0:
                mul = (1.5 / (Math.abs(delta * bump_transfer_factor) + 1.5));
                break;
            case 1:
                mul = 1 / Math.sqrt(Math.abs(delta * bump_transfer_factor) + 1);
                break;
            case 2:
                mul = 1 / Math.cbrt(Math.abs(delta * bump_transfer_factor) + 1);
                break;
            case 3:
                mul = Math.pow(2, -Math.abs(delta * bump_transfer_factor));
                break;
            //case 4:
            //mul = (Math.atan(-Math.abs(delta * bump_transfer_factor))*0.63662+1);
            //break;
        }

        return mul;
    }

    private int changeBrightnessOfColorScaling(int rgb, double delta) {
        int new_color = 0;

        double mul = getBumpCoef(delta);

        if (delta > 0) {
            rgb ^= 0xFFFFFF;
            int r = rgb & 0xFF0000;
            int g = rgb & 0x00FF00;
            int b = rgb & 0x0000FF;
            int ret = (int) (r * mul + 0.5) & 0xFF0000 | (int) (g * mul + 0.5) & 0x00FF00 | (int) (b * mul + 0.5);
            new_color = 0xff000000 | (ret ^ 0xFFFFFF);
        } else {
            int r = rgb & 0xFF0000;
            int g = rgb & 0x00FF00;
            int b = rgb & 0x0000FF;
            new_color = 0xff000000 | (int) (r * mul + 0.5) & 0xFF0000 | (int) (g * mul + 0.5) & 0x00FF00 | (int) (b * mul + 0.5);
        }

        return new_color;

    }

    private int changeBrightnessOfColorBlending(int rgbIn, double delta) {

        double mul = getBumpCoef(delta);

        int temp_red = 0;
        int temp_green = 0;
        int temp_blue = 0;

        if (delta > 0) {
            int index = bumpProcessing == 1 ? (int) ((mul / 2) * (gradient.length - 1) + 0.5) : (int) ((1 - mul) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            temp_red = (gradient[index] >> 16) & 0xff;
            temp_green = (gradient[index] >> 8) & 0xff;
            temp_blue = gradient[index] & 0xff;
        } else {
            int index = bumpProcessing == 1 ? (int) (((2 - mul) / 2) * (gradient.length - 1) + 0.5) : (int) ((1 - mul) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            temp_red = (gradient[index] >> 16) & 0xff;
            temp_green = (gradient[index] >> 8) & 0xff;
            temp_blue = gradient[index] & 0xff;
        }

        int old_red = (rgbIn >> 16) & 0xFF;
        int old_green = (rgbIn >> 8) & 0xFF;
        int old_blue = rgbIn & 0xFF;

        return blending.blend(temp_red, temp_green, temp_blue, old_red, old_green, old_blue, 1 - bump_blending);

    }

    public void terminateColorCycling() {

        color_cycling = false;

    }

    public int getColorCyclingLocation() {

        return color_cycling_location;

    }

    private void applyFilters() {

        int active_filters_count = 0;
        for (int i = 0; i < filters.length; i++) {
            if (filters[i]) {
                active_filters_count++;
            }
        }

        int old_max = progress.getMaximum();
        int cur_val = progress.getValue();

        if (active_filters_count > 0) {
            progress.setMaximum(active_filters_count);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_filters_color);
            progress.setString("Image Filters: " + 0 + "/" + active_filters_count);
        }

        ImageFilters.filter(image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order, progress);

        if (active_filters_count > 0) {
            progress.setString(null);
            progress.setMaximum(old_max);
            progress.setValue(cur_val);
            progress.setForeground(MainWindow.progress_color);
        }

    }

    private void applyFiltersNoProgress() {
        ImageFilters.filter(image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order, null);
    }

    private double calculateHeight(double x) {

        switch (height_algorithm) {
            case 0:
                return 120 / (x + 1);
            case 1:
                return Math.exp(-x + 5);
            case 2:
                return 40 * Math.log(x + 1);
            case 3:
                return 150 - Math.exp(-x + 5);
            case 4:
                return 40 * Math.log(Math.log(x + 1) + 1);
            case 5:
                return 150 / (1 + Math.exp(-3 * x + 3));

        }

        return 0;
    }

    private void findMinMaxHeight() {

        min = Double.MAX_VALUE;
        max = -Double.MIN_VALUE;

        for (int x = 0; x < detail; x++) {
            for (int y = 0; y < detail; y++) {
                if (vert[x][y][1] < min) {
                    min = vert[x][y][1];
                }

                if (vert[x][y][1] > max) {
                    max = vert[x][y][1];
                }
            }
        }

    }

    private void applyHeightScaling() {

        double local_max = max * (max_range / 100.0);

        double local_min = min + local_max * (min_range / 100.0) * 0.05;
        local_min = local_min > local_max ? local_max : local_min;

        double new_max = local_max - local_min;

        for (int x = FROMx; x < TOx; x++) {
            for (int y = FROMy; y < TOy; y++) {
                if (vert[x][y][1] <= local_max && vert[x][y][1] >= local_min) {
                    vert[x][y][1] -= local_min;
                    vert[x][y][1] *= max_scaling / new_max;
                } else if (vert[x][y][1] > local_max) {
                    vert[x][y][1] = (float) (max_scaling);
                } else if (!Double.isNaN(vert[x][y][1]) && !Double.isInfinite(vert[x][y][1])) {
                    vert[x][y][1] = 0;
                }

                vert[x][y][1] = (float) (d3_height_scale * calculateHeight(vert[x][y][1]) - 100);
            }
        }
    }

    private void gaussianHeightScalingInit() {
        temp_array = new float[detail][detail];

        for (int x = 0; x < detail; x++) {
            for (int y = 0; y < detail; y++) {
                temp_array[x][y] = vert[x][y][1];
            }
        }

        createGaussianKernel(gaussian_kernel_size * 2 + 3, gaussian_weight);
    }

    private void gaussianHeightScalingEnd() {
        temp_array = null;
    }

    private void gaussianHeightScaling() {

        int kernel_size = (int) (Math.sqrt(gaussian_kernel.length));
        int kernel_size2 = kernel_size / 2;

        int startx = FROMx == 0 ? kernel_size2 : FROMx;
        int starty = FROMy == 0 ? kernel_size2 : FROMy;
        int endx = TOx == detail ? detail - kernel_size2 : TOx;
        int endy = TOy == detail ? detail - kernel_size2 : TOy;

        for (int x = startx; x < endx; x++) {
            for (int y = starty; y < endy; y++) {
                double sum = 0;

                for (int k = x - kernel_size2, p = 0; p < kernel_size; k++, p++) {
                    for (int l = y - kernel_size2, t = 0; t < kernel_size; l++, t++) {
                        sum += temp_array[k][l] * gaussian_kernel[p * kernel_size + t];
                    }
                }

                vert[x][y][1] = (float) sum;
            }
        }
    }

    private void createGaussianKernel(int length, double weight) {
        gaussian_kernel = new float[length * length];
        double sumTotal = 0;

        int kernelRadius = length / 2;
        double distance = 0;

        double calculatedEuler = 1.0 / (2.0 * Math.PI * weight * weight);

        float temp;
        for (int filterY = -kernelRadius; filterY <= kernelRadius; filterY++) {
            for (int filterX = -kernelRadius; filterX <= kernelRadius; filterX++) {
                distance = ((filterX * filterX) + (filterY * filterY)) / (2 * (weight * weight));
                temp = gaussian_kernel[(filterY + kernelRadius) * length + filterX + kernelRadius] = (float) (calculatedEuler * Math.exp(-distance));
                sumTotal += temp;
            }
        }

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                gaussian_kernel[y * length + x] = (float) (gaussian_kernel[y * length + x] * (1.0 / sumTotal));
            }
        }

    }

    private void heightProcessing() {

        if (gaussian_scaling) {

            if (gaussian_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                gaussianHeightScalingInit();

            }

            try {
                gaussian_scaling_sync2.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if (height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            findMinMaxHeight();

        }

        try {
            height_scaling_sync2.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        applyHeightScaling();

        if (shade_height) {
            if (shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                findMinMaxHeight();

            }

            try {
                shade_color_height_sync2.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            shadeColorBasedOnHeight();
        }

        try {
            calculate_vectors_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        if (gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

    }

    private void calculate3DVectors(double dx, double w2) {

        int n1 = detail - 1;

        double mod;
        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        double norm_0_0, norm_0_1, norm_0_2, norm_1_0, norm_1_1, norm_1_2;

        for (int x = FROMx; x < TOx; x++) {

            double c1 = dx * x - w2;

            for (int y = FROMy; y < TOy; y++) {
                if (x < n1 && y < n1) {

                    norm_0_0 = vert[x][y][1] - vert[x + 1][y][1];
                    norm_0_1 = dx;
                    norm_0_2 = vert[x + 1][y][1] - vert[x + 1][y + 1][1];
                    mod = Math.sqrt(norm_0_0 * norm_0_0 + norm_0_1 * norm_0_1 + norm_0_2 * norm_0_2) / 255.5;
                    norm_0_0 /= mod;
                    norm_0_1 /= mod;
                    norm_0_2 /= mod;

                    norm_1_0 = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    norm_1_1 = dx;
                    norm_1_2 = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(norm_1_0 * norm_1_0 + norm_1_1 * norm_1_1 + norm_1_2 * norm_1_2) / 255.5;
                    norm_1_0 /= mod;
                    norm_1_1 /= mod;
                    norm_1_2 /= mod;

                    Norm1z[x][y][0] = (float) (m20 * norm_0_0 + m21 * norm_0_1 + m22 * norm_0_2);
                    Norm1z[x][y][1] = (float) (m20 * norm_1_0 + m21 * norm_1_1 + m22 * norm_1_2);
                }

                double c2 = dx * y - w2;
                vert1[x][y][0] = (float) (m00 * c1 + m02 * c2);
                vert1[x][y][1] = (float) (m10 * c1 + m11 * vert[x][y][1] + m12 * c2);
            }
        }
    }

    private void paint3D(int w2, boolean updateProgress) {

        int n1 = detail - 1;

        int[] xPol = new int[3];
        int[] yPol = new int[3];

        Graphics2D g = image.createGraphics();

        int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

        if (m20 < 0) {
            ib = n1;
            ie = -1;
            sti = -1;
        }

        if (m22 < 0) {
            jb = n1;
            je = -1;
            stj = -1;
        }

        int old_max = progress.getMaximum();
        int cur_val = progress.getValue();

        if (updateProgress) {
            progress.setMaximum(Math.abs(ie - ib) * Math.abs(je - jb));
            progress.setValue(0);
            progress.setString("3D Render: " + 0 + "%");
            progress.setForeground(MainWindow.progress_d3_color);
        }

        int red, green, blue;

        color_3d_blending = 1 - color_3d_blending;

        int count = 0;
        for (int i = ib; i != ie; i += sti) {
            for (int j = jb; j != je; j += stj) {

                count++;

                red = ((((int) vert[i][j][0]) >> 16) & 0xff);
                green = ((((int) vert[i][j][0]) >> 8) & 0xff);
                blue = (((int) vert[i][j][0]) & 0xff);

                if (Norm1z[i][j][0] > 0) {
                    xPol[0] = w2 + (int) vert1[i][j][0];
                    xPol[1] = w2 + (int) vert1[i + 1][j][0];
                    xPol[2] = w2 + (int) vert1[i + 1][j + 1][0];
                    yPol[0] = w2 - (int) vert1[i][j][1];
                    yPol[1] = w2 - (int) vert1[i + 1][j][1];
                    yPol[2] = w2 - (int) vert1[i + 1][j + 1][1];

                    int index = (int) ((Norm1z[i][j][0] / 255.0) * (gradient.length - 1) + 0.5);

                    index = index >= gradient.length ? gradient.length - 1 : index;

                    int temp_red = (gradient[index] >> 16) & 0xff;
                    int temp_green = (gradient[index] >> 8) & 0xff;
                    int temp_blue = gradient[index] & 0xff;

                    g.setColor(new Color(blending.blend(temp_red, temp_green, temp_blue, red, green, blue, color_3d_blending)));

                    if (filters[MainWindow.ANTIALIASING]) {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                        g.fillPolygon(xPol, yPol, 3);
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.fillPolygon(xPol, yPol, 3);
                    } else {
                        g.fillPolygon(xPol, yPol, 3);
                    }

                }

                if (Norm1z[i][j][1] > 0) {
                    xPol[0] = w2 + (int) vert1[i][j][0];
                    xPol[1] = w2 + (int) vert1[i][j + 1][0];
                    xPol[2] = w2 + (int) vert1[i + 1][j + 1][0];
                    yPol[0] = w2 - (int) vert1[i][j][1];
                    yPol[1] = w2 - (int) vert1[i][j + 1][1];
                    yPol[2] = w2 - (int) vert1[i + 1][j + 1][1];

                    int index = (int) ((Norm1z[i][j][1] / 255.0) * (gradient.length - 1) + 0.5);

                    index = index >= gradient.length ? gradient.length - 1 : index;

                    int temp_red = (gradient[index] >> 16) & 0xff;
                    int temp_green = (gradient[index] >> 8) & 0xff;
                    int temp_blue = gradient[index] & 0xff;

                    g.setColor(new Color(blending.blend(temp_red, temp_green, temp_blue, red, green, blue, color_3d_blending)));

                    if (filters[MainWindow.ANTIALIASING]) {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                        g.fillPolygon(xPol, yPol, 3);
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.fillPolygon(xPol, yPol, 3);
                    } else {
                        g.fillPolygon(xPol, yPol, 3);
                    }

                }
            }

            if (updateProgress) {
                progress.setValue(count);
                progress.setString("3D Render: " + (int) ((double) count / progress.getMaximum() * 100) + "%");
            }
        }

        if (updateProgress) {
            progress.setString(null);
            progress.setMaximum(old_max);
            progress.setValue(cur_val);
            progress.setForeground(MainWindow.progress_color);
        }
    }

    protected void postProcessFastJulia(int image_size) {
        if (bump_map || fake_de || rainbow_palette || entropy_coloring || offset_coloring || greyscale_coloring) {
            try {
                post_processing_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size, image_iterations_fast_julia);
        }
    }

    protected void postProcess(int image_size) {
        if (bump_map || fake_de || rainbow_palette || entropy_coloring || offset_coloring || greyscale_coloring) {
            try {
                post_processing_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size, image_iterations);
        }
    }

    private void blendingFactory() {

        switch (color_blending) {
            case MainWindow.NORMAL_BLENDING:
                blending = new NormalBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.MULTIPLY_BLENDING:
                blending = new MultiplyBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.DIVIDE_BLENDING:
                blending = new DivideBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.ADDITION_BLENDING:
                blending = new AdditionBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.SUBTRACTION_BLENDING:
                blending = new SubtractionBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.DIFFERENCE_BLENDING:
                blending = new DifferenceBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.VALUE_BLENDING:
                blending = new ValueBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.OVERLAY_BLENDING:
                blending = new OverlayBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.SCREEN_BLENDING:
                blending = new ScreenBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.DODGE_BLENDING:
                blending = new DodgeBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.BURN_BLENDING:
                blending = new BurnBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.DARKEN_ONLY_BLENDING:
                blending = new DarkenOnlyBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.LIGHTEN_ONLY_BLENDING:
                blending = new LightenOnlyBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.HARD_LIGHT_BLENDING:
                blending = new HardLightBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.GRAIN_EXTRACT_BLENDING:
                blending = new GrainExtractBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.GRAIN_MERGE_BLENDING:
                blending = new GrainMergeBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.SATURATION_BLENDING:
                blending = new SaturationBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
            case MainWindow.COLOR_BLENDING:
                blending = new ColorBlending(MainWindow.INTERPOLATION_LINEAR);
                break;
        }
    }

    private void domainColoringFactory(DomainColoringSettings ds) {

        if (ds.customDomainColoring) {
            domain_color = new CustomDomainColoring(ds, palette, color_transfer, color_cycling_location, blending, gradient);
            return;
        }

        switch (ds.domain_coloring_alg) {
            case 0:
                domain_color = new BlackGridWhiteCirclesLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 1:
                domain_color = new WhiteGridBlackCirclesLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 2:
                domain_color = new BlackGridDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 3:
                domain_color = new WhiteGridDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 4:
                domain_color = new BlackGridBrightContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 5:
                domain_color = new WhiteGridDarkContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 6:
                domain_color = new NormBlackGridWhiteCirclesLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 7:
                domain_color = new NormWhiteGridBlackCirclesLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 8:
                domain_color = new NormBlackGridDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 9:
                domain_color = new NormWhiteGridDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 10:
                domain_color = new NormBlackGridBrightContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 11:
                domain_color = new NormWhiteGridDarkContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 12:
                domain_color = new WhiteCirclesLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 13:
                domain_color = new BlackCirclesLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 14:
                domain_color = new BrightContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 15:
                domain_color = new DarkContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 16:
                domain_color = new NormWhiteCirclesLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 17:
                domain_color = new NormBlackCirclesLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 18:
                domain_color = new NormBrightContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 19:
                domain_color = new NormDarkContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 20:
                domain_color = new BlackGridContoursLog2IsoLinesDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 21:
                domain_color = new NormBlackGridContoursLog2IsoLinesDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 22:
                domain_color = new BlackGridIsoContoursDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 23:
                domain_color = new NormBlackGridIsoContoursDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 24:
                domain_color = new IsoContoursContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 25:
                domain_color = new NormIsoContoursContoursLog2DomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 26:
                domain_color = new GridContoursIsoLinesDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
            case 27:
                domain_color = new NormGridContoursIsoLinesDomainColoring(ds.use_palette_domain_coloring, palette, color_transfer, color_cycling_location, blending);
                break;
        }

    }

    protected int getColorForSkippedPixels(int color, int divide_iteration) {

        switch (SKIPPED_PIXELS_ALG) {
            case 0:
                return color;
            case 1:
                return algorithm_colors[((int) getId()) % algorithm_colors.length];
            case 2:
                return SKIPPED_PIXELS_COLOR;
            case 3:
                return algorithm_colors[divide_iteration % algorithm_colors.length];
            case 4:
                return 0x00ffffff;
            default:
                return color;
        }

    }

    protected void drawSquares(int image_size) {

        int white = 0xffffffff;
        int grey = 0xffAAAAAA;

        int colA = white;
        int colB = grey;

        int length = 14;
        for (int y = FROMy; y < TOy; y++) {
            for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                if (rgbs[loc] == 0x00ffffff) {
                    if (x % length < length / 2) {
                        rgbs[loc] = colA;
                    } else {
                        rgbs[loc] = colB;
                    }

                }
            }

            if (y % length < length / 2) {
                colA = white;
                colB = grey;
            } else {
                colB = white;
                colA = grey;
            }
        }
    }

    private float calaculateDomainColoringHeight(Complex value) {

        double res = value.norm();
        return (float) (res > 50 ? 50 : res);

    }

    protected Object[] createAntialiasingSteps() {

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        return new Object[]{antialiasing_x, antialiasing_y};

    }

    protected Object[] createPolarAntialiasingSteps() {

        double exp_x_antialiasing_size = Math.exp(x_antialiasing_size);
        double exp_inv_x_antialiasing_size = 1 / exp_x_antialiasing_size;

        double exp_x_antialiasing_size_x2 = exp_x_antialiasing_size * exp_x_antialiasing_size;
        double exp_inv_x_antialiasing_size_x2 = 1 / exp_x_antialiasing_size_x2;

        double antialiasing_x[] = {exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_inv_x_antialiasing_size,
            exp_inv_x_antialiasing_size, exp_x_antialiasing_size, 1, 1,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, 1, 1, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2,
            exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size_x2, exp_inv_x_antialiasing_size, exp_inv_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size, exp_x_antialiasing_size_x2, exp_x_antialiasing_size_x2};

        double sin_y_antialiasing_size = Math.sin(y_antialiasing_size);
        double cos_y_antialiasing_size = Math.cos(y_antialiasing_size);

        double sin_inv_y_antialiasing_size = -sin_y_antialiasing_size;
        double cos_inv_y_antialiasing_size = cos_y_antialiasing_size;

        double sin_y_antialiasing_size_x2 = 2 * sin_y_antialiasing_size * cos_y_antialiasing_size;
        double cos_y_antialiasing_size_x2 = 2 * cos_y_antialiasing_size * cos_y_antialiasing_size - 1;

        double sin_inv_y_antialiasing_size_x2 = -sin_y_antialiasing_size_x2;
        double cos_inv_y_antialiasing_size_x2 = cos_y_antialiasing_size_x2;

        double antialiasing_y_sin[] = {sin_inv_y_antialiasing_size, sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_y_antialiasing_size,
            0, 0, sin_inv_y_antialiasing_size, sin_y_antialiasing_size,
            sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, 0, sin_y_antialiasing_size_x2,
            sin_inv_y_antialiasing_size, sin_y_antialiasing_size, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size_x2, sin_y_antialiasing_size_x2, sin_inv_y_antialiasing_size, sin_y_antialiasing_size};

        double antialiasing_y_cos[] = {cos_inv_y_antialiasing_size, cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_y_antialiasing_size,
            1, 1, cos_inv_y_antialiasing_size, cos_y_antialiasing_size,
            cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, 1, cos_y_antialiasing_size_x2,
            cos_inv_y_antialiasing_size, cos_y_antialiasing_size, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size_x2, cos_y_antialiasing_size_x2, cos_inv_y_antialiasing_size, cos_y_antialiasing_size};

        return new Object[]{antialiasing_x, antialiasing_y_sin, antialiasing_y_cos};
    }

    public static String getDefaultInitialValue() {

        return default_init_val;

    }

    public static double getConvergentBailout() {

        return convergent_bailout;

    }

    public static void setArrays(int image_size) {

        IMAGE_SIZE = image_size;

        vert = null;
        vert1 = null;
        Norm1z = null;
        image_iterations = null;

        System.gc();

        image_iterations = new double[image_size * image_size];
        image_iterations_fast_julia = new double[MainWindow.FAST_JULIA_IMAGE_SIZE * MainWindow.FAST_JULIA_IMAGE_SIZE];

    }

    public static void setArraysExpander(int image_size) {

        IMAGE_SIZE = image_size;

        image_iterations = null;

        System.gc();

        image_iterations = new double[image_size * image_size];

    }

    public static void set3DArrays(int detail) {

        IMAGE_SIZE = detail;

        image_iterations = null;
        image_iterations_fast_julia = null;

        System.gc();

        vert = new float[detail][detail][2];
        vert1 = new float[detail][detail][2];
        Norm1z = new float[detail][detail][2];
        image_iterations = new double[detail * detail];

    }

    public static void resetThreadData(int num_threads) {

        randomNumber = new Random().nextInt(100000);
        finalize_sync = new AtomicInteger(0);
        total_calculated = new AtomicInteger(0);
        post_processing_sync = new CyclicBarrier(num_threads);
        calculate_vectors_sync = new CyclicBarrier(num_threads);
        painting_sync = new AtomicInteger(0);
        height_scaling_sync = new AtomicInteger(0);
        height_scaling_sync2 = new CyclicBarrier(num_threads);
        gaussian_scaling_sync = new AtomicInteger(0);
        gaussian_scaling_sync2 = new CyclicBarrier(num_threads);
        normal_drawing_algorithm_pixel = new AtomicInteger(0);
        color_cycling_filters_sync = new CyclicBarrier(num_threads);
        color_cycling_restart_sync = new CyclicBarrier(num_threads);
        shade_color_height_sync = new AtomicInteger(0);
        shade_color_height_sync2 = new CyclicBarrier(num_threads);
        initialize_jobs_sync = new CyclicBarrier(num_threads);

    }

    /*public static void setQuickDrawTileSize(int size) {

        TILE_SIZE = size;

    }

    public static int getQuickDrawTileSize() {

        return TILE_SIZE;

    }

    public static void setSkippedPixelsAlgorithm(int algorithm) {

        SKIPPED_PIXELS_ALG = algorithm;

    }

    public static int getSkippedPixelsAlgorithm() {

        return SKIPPED_PIXELS_ALG;

    }

    public static void setSkippedPixelsColor(int color) {

        SKIPPED_PIXELS_COLOR = color;

    }

    public static int getSkippedPixelsColor() {

        return SKIPPED_PIXELS_COLOR;

    }*/
}
