/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
import fractalzoomer.palettes.PaletteColor;
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
import fractalzoomer.functions.root_finding_methods.muller.Muller3;
import fractalzoomer.functions.root_finding_methods.muller.Muller4;
import fractalzoomer.functions.root_finding_methods.muller.MullerCos;
import fractalzoomer.functions.root_finding_methods.muller.MullerFormula;
import fractalzoomer.functions.root_finding_methods.muller.MullerGeneralized3;
import fractalzoomer.functions.root_finding_methods.muller.MullerGeneralized8;
import fractalzoomer.functions.root_finding_methods.muller.MullerPoly;
import fractalzoomer.functions.root_finding_methods.muller.MullerSin;
import fractalzoomer.functions.root_finding_methods.newton.NewtonFormula;
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
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;
import fractalzoomer.utils.MathUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

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

    protected int thread_slices;

    protected int drawing_done;
    protected int thread_calculated;

    protected static AtomicInteger image_filters_sync;
    protected static CyclicBarrier post_processing_sync;
    protected static CyclicBarrier calculate_vectors_sync;
    protected static AtomicInteger painting_sync;
    protected static AtomicInteger height_scaling_sync;
    protected static CyclicBarrier gaussian_scaling_sync;
    protected static AtomicInteger shade_color_height_sync;
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
    public static final int[] colors_3d = {-16777216, -16711423, -16645630, -16579837, -16514044, -16448251, -16382458, -16316665, -16250872, -16185079, -16119286, -16053493, -15987700, -15921907, -15856114, -15790321, -15724528, -15658735, -15592942, -15527149, -15461356, -15395563, -15329770, -15263977, -15198184, -15132391, -15066598, -15000805, -14935012, -14869219, -14803426, -14737633, -14671840, -14606047, -14540254, -14474461, -14408668, -14342875, -14277082, -14211289, -14145496, -14079703, -14013910, -13948117, -13882324, -13816531, -13750738, -13684945, -13619152, -13553359, -13487566, -13421773, -13355980, -13290187, -13224394, -13158601, -13092808, -13027015, -12961222, -12895429, -12829636, -12763843, -12698050, -12632257, -12566464, -12500671, -12434878, -12369085, -12303292, -12237499, -12171706, -12105913, -12040120, -11974327, -11908534, -11842741, -11776948, -11711155, -11645362, -11579569, -11513776, -11447983, -11382190, -11316397, -11250604, -11184811, -11119018, -11053225, -10987432, -10921639, -10855846, -10790053, -10724260, -10658467, -10592674, -10526881, -10461088, -10395295, -10329502, -10263709, -10197916, -10132123, -10066330, -10000537, -9934744, -9868951, -9803158, -9737365, -9671572, -9605779, -9539986, -9474193, -9408400, -9342607, -9276814, -9211021, -9145228, -9079435, -9013642, -8947849, -8882056, -8816263, -8750470, -8684677, -8618884, -8553091, -8487298, -8421505, -8355712, -8289919, -8224126, -8158333, -8092540, -8026747, -7960954, -7895161, -7829368, -7763575, -7697782, -7631989, -7566196, -7500403, -7434610, -7368817, -7303024, -7237231, -7171438, -7105645, -7039852, -6974059, -6908266, -6842473, -6776680, -6710887, -6645094, -6579301, -6513508, -6447715, -6381922, -6316129, -6250336, -6184543, -6118750, -6052957, -5987164, -5921371, -5855578, -5789785, -5723992, -5658199, -5592406, -5526613, -5460820, -5395027, -5329234, -5263441, -5197648, -5131855, -5066062, -5000269, -4934476, -4868683, -4802890, -4737097, -4671304, -4605511, -4539718, -4473925, -4408132, -4342339, -4276546, -4210753, -4144960, -4079167, -4013374, -3947581, -3881788, -3815995, -3750202, -3684409, -3618616, -3552823, -3487030, -3421237, -3355444, -3289651, -3223858, -3158065, -3092272, -3026479, -2960686, -2894893, -2829100, -2763307, -2697514, -2631721, -2565928, -2500135, -2434342, -2368549, -2302756, -2236963, -2171170, -2105377, -2039584, -1973791, -1907998, -1842205, -1776412, -1710619, -1644826, -1579033, -1513240, -1447447, -1381654, -1315861, -1250068, -1184275, -1118482, -1052689, -986896, -921103, -855310, -789517, -723724, -657931, -592138, -526345, -460552, -394759, -328966, -263173, -197380, -131587, -65794, -1};
    protected int detail;
    protected double fiX, fiY, scale, m20, m21, m22;
    protected double d3_height_scale;
    protected int min_to_max_scaling;
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
    protected boolean fake_de;
    protected double fake_de_factor;
    protected int dem_color;
    protected boolean rainbow_palette;
    protected double rainbow_palette_factor;
    protected boolean inverse_fake_dem;
    protected double bm_noise_reducing_factor;
    protected double rp_noise_reducing_factor;
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
    protected boolean julia;
    protected boolean domain_coloring;
    protected int domain_coloring_alg;
    protected boolean use_palette_domain_coloring;
    protected double height_ratio;
    protected boolean polar_projection;
    protected double circle_period;
    protected int fractal_color;
    protected int max_iterations;
    protected PaletteColor palette_color;
    protected boolean boundary_tracing;
    protected MainWindow ptr;
    protected int action;
    protected double x_antialiasing_size;
    protected double x_antialiasing_size_x2;
    protected double y_antialiasing_size;
    protected double y_antialiasing_size_x2;
    protected boolean quickDraw;
    protected int tile;
    private static String default_init_val;
    private static int TILE_SIZE = 5;
    private static int QUICK_DRAW_DELAY = 1200; //msec


    /*protected double[] AntialiasingData;
     protected double[] FastJuliaData;
     protected int[] Done;
     protected int QueueSize;
     protected int[] Queue;
     protected int QueueHead;
     protected int QueueTail;
     public static final int Loaded = 1, Queued = 2;*/
    static {

        default_init_val = "c";
        image_iterations_fast_julia = new double[MainWindow.FAST_JULIA_IMAGE_SIZE * MainWindow.FAST_JULIA_IMAGE_SIZE];

    }

    //Fractal
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean d3, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean gaussian_scaling, double gaussian_weight, int gaussian_kernel_size, int min_to_max_scaling, int max_scaling, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double d3_height_scale, int height_algorithm, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean domain_coloring, int domain_coloring_alg, boolean use_palette_domain_coloring, boolean shade_height, int shade_choice, int shade_algorithm, boolean shade_invert, boolean inverse_dem, boolean inverse_fake_dem, boolean quickDraw, double bm_noise_reducing_factor, double rp_noise_reducing_factor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = filters_options_vals;
        this.filters_options_extra_vals = filters_options_extra_vals;
        this.filters_colors = filters_colors;
        this.filters_extra_colors = filters_extra_colors;
        this.filters_order = filters_order;
        this.d3 = d3;
        //this.d3_draw_method = d3_draw_method;
        this.detail = detail;
        this.fiX = fiX;
        this.fiY = fiY;
        this.height_ratio = height_ratio;
        this.d3_height_scale = d3_height_scale;
        this.height_algorithm = height_algorithm;
        scale = d3_size_scale;
        this.color_3d_blending = color_3d_blending;
        this.gaussian_scaling = gaussian_scaling;
        this.gaussian_weight = gaussian_weight;
        this.gaussian_kernel_size = gaussian_kernel_size;
        this.min_to_max_scaling = min_to_max_scaling;
        this.max_scaling = max_scaling;
        this.shade_height = shade_height;
        this.shade_choice = shade_choice;
        this.shade_algorithm = shade_algorithm;
        this.shade_invert = shade_invert;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;
        this.inverse_fake_dem = inverse_fake_dem;

        this.rainbow_palette = rainbow_palette;
        this.rainbow_palette_factor = rainbow_palette_factor;
        
        this.bm_noise_reducing_factor = bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rp_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = domain_coloring;
        this.domain_coloring_alg = domain_coloring_alg;
        this.use_palette_domain_coloring = use_palette_domain_coloring;

        if(filters[MainWindow.ANTIALIASING]) {
            if(d3 && polar_projection) {
                int n1 = detail - 1;

                y_antialiasing_size = ((2 * circle_period * Math.PI) / n1) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else if(polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else if(d3) {
                int n1 = detail - 1;

                x_antialiasing_size = (size / n1) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / n1) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }
        // thread_slices = 10;

        if(domain_coloring) {
            if(polar_projection) {
                action = DOMAIN_POLAR;
            }
            else {
                action = DOMAIN;
            }
        }
        else {
            if(polar_projection) {
                action = POLAR;
            }
            else {
                action = NORMAL;
            }
        }

        julia = false;

        this.quickDraw = quickDraw;
        tile = TILE_SIZE;

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
        
         if(filters[MainWindow.ANTIALIASING]) {
         AntialiasingData = new double[width * height];
         }
         }*/
        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTON3:
                fractal = new Newton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTON4:
                fractal = new Newton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                fractal = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                fractal = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONSIN:
                fractal = new NewtonSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONCOS:
                fractal = new NewtonCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONPOLY:
                fractal = new NewtonPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.NEWTONFORMULA:
                fractal = new NewtonFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SIERPINSKI_GASKET:
                fractal = new SierpinskiGasket(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.HALLEY3:
                fractal = new Halley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEY4:
                fractal = new Halley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYGENERALIZED3:
                fractal = new HalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYGENERALIZED8:
                fractal = new HalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYSIN:
                fractal = new HalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYCOS:
                fractal = new HalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYPOLY:
                fractal = new HalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HALLEYFORMULA:
                fractal = new HalleyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula);
                break;
            case MainWindow.SCHRODER3:
                fractal = new Schroder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODER4:
                fractal = new Schroder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERGENERALIZED3:
                fractal = new SchroderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERGENERALIZED8:
                fractal = new SchroderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERSIN:
                fractal = new SchroderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERCOS:
                fractal = new SchroderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERPOLY:
                fractal = new SchroderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SCHRODERFORMULA:
                fractal = new SchroderFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula);
                break;
            case MainWindow.HOUSEHOLDER3:
                fractal = new Householder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDER4:
                fractal = new Householder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED3:
                fractal = new HouseholderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED8:
                fractal = new HouseholderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERSIN:
                fractal = new HouseholderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERCOS:
                fractal = new HouseholderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERPOLY:
                fractal = new HouseholderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.HOUSEHOLDERFORMULA:
                fractal = new HouseholderFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula);
                break;
            case MainWindow.SECANT3:
                fractal = new Secant3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANT4:
                fractal = new Secant4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTGENERALIZED3:
                fractal = new SecantGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTGENERALIZED8:
                fractal = new SecantGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTCOS:
                fractal = new SecantCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTPOLY:
                fractal = new SecantPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.SECANTFORMULA:
                fractal = new SecantFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula);
                break;
            case MainWindow.STEFFENSEN3:
                fractal = new Steffensen3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.STEFFENSEN4:
                fractal = new Steffensen4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.STEFFENSENGENERALIZED3:
                fractal = new SteffensenGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.STEFFENSENPOLY:
                fractal = new SteffensenPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.STEFFENSENFORMULA:
                fractal = new SteffensenFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                }
                else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                }
                else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                }
                else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed);
                }
                else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm);
                break;
            case MainWindow.MULLER3:
                fractal = new Muller3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.MULLER4:
                fractal = new Muller4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.MULLERGENERALIZED3:
                fractal = new MullerGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.MULLERGENERALIZED8:
                fractal = new MullerGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.MULLERSIN:
                fractal = new MullerSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.MULLERCOS:
                fractal = new MullerCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.MULLERPOLY:
                fractal = new MullerPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm);
                break;
            case MainWindow.MULLERFORMULA:
                fractal = new MullerFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula);
                break;

        }

        try {
            default_init_val = fractal.getInitialValue().toString();
        }
        catch(Exception ex) {
            default_init_val = "c";
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean d3, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean gaussian_scaling, double gaussian_weight, int gaussian_kernel_size, int min_to_max_scaling, int max_scaling, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double d3_height_scale, int height_algorithm, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean domain_coloring, int domain_coloring_alg, boolean use_palette_domain_coloring, boolean shade_height, int shade_choice, int shade_algorithm, boolean shade_invert, boolean inverse_dem, boolean inverse_fake_dem, boolean quickDraw, double bm_noise_reducing_factor, double rp_noise_reducing_factor, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = filters_options_vals;
        this.filters_options_extra_vals = filters_options_extra_vals;
        this.filters_colors = filters_colors;
        this.filters_extra_colors = filters_extra_colors;
        this.filters_order = filters_order;
        this.d3 = d3;
        //this.d3_draw_method = d3_draw_method;
        this.detail = detail;
        this.fiX = fiX;
        this.fiY = fiY;
        this.height_ratio = height_ratio;
        this.d3_height_scale = d3_height_scale;
        this.height_algorithm = height_algorithm;
        scale = d3_size_scale;
        this.color_3d_blending = color_3d_blending;
        this.gaussian_scaling = gaussian_scaling;
        this.gaussian_weight = gaussian_weight;
        this.gaussian_kernel_size = gaussian_kernel_size;
        this.min_to_max_scaling = min_to_max_scaling;
        this.max_scaling = max_scaling;
        this.shade_height = shade_height;
        this.shade_choice = shade_choice;
        this.shade_algorithm = shade_algorithm;
        this.shade_invert = shade_invert;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;
        this.inverse_fake_dem = inverse_fake_dem;

        this.rainbow_palette = rainbow_palette;
        this.rainbow_palette_factor = rainbow_palette_factor;
        
        this.bm_noise_reducing_factor = bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rp_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = domain_coloring;
        this.domain_coloring_alg = domain_coloring_alg;
        this.use_palette_domain_coloring = use_palette_domain_coloring;

        if(filters[MainWindow.ANTIALIASING]) {
            if(d3 && polar_projection) {
                int n1 = detail - 1;

                y_antialiasing_size = ((2 * circle_period * Math.PI) / n1) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else if(polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else if(d3) {
                int n1 = detail - 1;

                x_antialiasing_size = (size / n1) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / n1) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }
        //thread_slices = 10;

        if(domain_coloring) {
            if(polar_projection) {
                action = DOMAIN_POLAR;
            }
            else {
                action = DOMAIN;
            }
        }
        else {
            if(polar_projection) {
                action = POLAR;
            }
            else {
                action = NORMAL;
            }
        }

        julia = true;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        this.boundary_tracing = boundary_tracing;

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;

        }

        this.quickDraw = quickDraw;
        tile = TILE_SIZE;

        default_init_val = "c";

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia Map
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, boolean inverse_fake_dem, double bm_noise_reducing_factor, double rp_noise_reducing_factor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.filters = filters;
        this.filters_colors = filters_colors;
        this.filters_extra_colors = filters_extra_colors;
        this.filters_order = filters_order;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = filters_options_vals;
        this.filters_options_extra_vals = filters_options_extra_vals;
        this.height_ratio = height_ratio;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;
        this.inverse_fake_dem = inverse_fake_dem;

        this.rainbow_palette = rainbow_palette;
        this.rainbow_palette_factor = rainbow_palette_factor;
        
        this.bm_noise_reducing_factor = bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rp_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        if(filters[MainWindow.ANTIALIASING]) {
            if(polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / (TOx - FROMx)) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else {
                x_antialiasing_size = (size / (TOx - FROMx)) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / (TOx - FROMx)) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }

        if(polar_projection) {
            action = JULIA_MAP_POLAR;
        }
        else {
            action = JULIA_MAP;
        }

        double xJuliaCenter, yJuliaCenter;

        if(polar_projection) {
            double start;
            double center = Math.log(size);

            double f, sf, cf, r;
            double muly = (2 * circle_period * Math.PI) / image.getHeight();

            double mulx = muly * height_ratio;

            start = center - mulx * image.getHeight() / 2.0;

            f = ((TOy + FROMy) * 0.5) * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(((TOx + FROMx) * 0.5) * mulx + start);

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter + r * cf, yCenter + r * sf, rotation_vals, rotation_center);

            xJuliaCenter = p.x;
            yJuliaCenter = p.y;
        }
        else {
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

        julia = true;

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(0.5, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(-2, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    fractal = new UserFormulaEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaIterationBasedConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConditionalConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaCoupledConverging(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(0, 0, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;

        }

        default_init_val = "c";

        drawing_done = 0;

    }

    //Julia Preview
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, boolean inverse_fake_dem, double bm_noise_reducing_factor, double rp_noise_reducing_factor, double xJuliaCenter, double yJuliaCenter) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.max_iterations = max_iterations;
        this.ptr = ptr;
        this.fast_julia_filters = fast_julia_filters;
        this.filters = filters;
        this.filters_colors = filters_colors;
        this.filters_extra_colors = filters_extra_colors;
        this.filters_order = filters_order;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location = color_cycling_location;
        this.filters_options_vals = filters_options_vals;
        this.filters_options_extra_vals = filters_options_extra_vals;
        this.height_ratio = height_ratio;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;
        this.inverse_fake_dem = inverse_fake_dem;

        this.rainbow_palette = rainbow_palette;
        this.rainbow_palette_factor = rainbow_palette_factor;
        
        this.bm_noise_reducing_factor = bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rp_noise_reducing_factor;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        if(filters[MainWindow.ANTIALIASING]) {
            if(polar_projection) {
                y_antialiasing_size = ((2 * circle_period * Math.PI) / image.getHeight()) * 0.25;
                x_antialiasing_size = y_antialiasing_size * height_ratio;

                x_antialiasing_size_x2 = 2 * x_antialiasing_size;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
            else {
                x_antialiasing_size = (size / image.getHeight()) * 0.25;
                x_antialiasing_size_x2 = 2 * x_antialiasing_size;

                y_antialiasing_size = ((size * height_ratio) / image.getHeight()) * 0.25;
                y_antialiasing_size_x2 = 2 * y_antialiasing_size;
            }
        }
        //thread_slices = 10;

        if(polar_projection) {
            action = FAST_JULIA_POLAR;
        }
        else {
            action = FAST_JULIA;
        }

        julia = true;

        this.boundary_tracing = boundary_tracing;

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if(bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                }
                else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, xJuliaCenter, yJuliaCenter);
                break;

        }

    }

    //Color Cycling
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, int color_cycling_speed, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean inverse_fake_dem, double bm_noise_reducing_factor, double rp_noise_reducing_factor) {

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
        this.filters = filters;
        this.filters_colors = filters_colors;
        this.filters_extra_colors = filters_extra_colors;
        this.filters_order = filters_order;
        this.filters_options_vals = filters_options_vals;
        this.filters_options_extra_vals = filters_options_extra_vals;
        action = COLOR_CYCLING;
        color_cycling = true;

        this.color_cycling_speed = color_cycling_speed;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;
        this.inverse_fake_dem = inverse_fake_dem;

        this.rainbow_palette = rainbow_palette;
        this.rainbow_palette_factor = rainbow_palette_factor;
        
        this.bm_noise_reducing_factor = bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rp_noise_reducing_factor;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    }

    //Apply Filter
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean inverse_fake_dem, double bm_noise_reducing_factor, double rp_noise_reducing_factor) {

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
        this.filters = filters;
        this.filters_colors = filters_colors;
        this.filters_extra_colors = filters_extra_colors;
        this.filters_order = filters_order;
        this.filters_options_vals = filters_options_vals;
        this.filters_options_extra_vals = filters_options_extra_vals;
        thread_slices = 10;
        action = APPLY_PALETTE_AND_FILTER;

        this.bump_map = bump_map;
        this.bumpMappingStrength = bumpMappingStrength;
        this.bumpMappingDepth = bumpMappingDepth;
        this.lightDirectionDegrees = lightDirectionDegrees;

        this.fake_de = fake_de;
        this.fake_de_factor = fake_de_factor;
        this.inverse_fake_dem = inverse_fake_dem;

        this.rainbow_palette = rainbow_palette;
        this.rainbow_palette_factor = rainbow_palette_factor;
        
        this.bm_noise_reducing_factor = bm_noise_reducing_factor;
        this.rp_noise_reducing_factor = rp_noise_reducing_factor;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        drawing_done = 0;

    }

    //Rotate 3d model
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean draw_action, MainWindow ptr, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.image = image;
        this.filters = filters;
        this.filters_colors = filters_colors;
        this.filters_extra_colors = filters_extra_colors;
        this.filters_order = filters_order;
        this.filters_options_vals = filters_options_vals;
        this.filters_options_extra_vals = filters_options_extra_vals;
        this.detail = detail;
        this.fiX = fiX;
        this.fiY = fiY;
        //this.d3_draw_method = d3_draw_method;
        this.color_3d_blending = color_3d_blending;

        rgbs = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        scale = d3_size_scale;

        if(draw_action) {
            action = ROTATE_3D_MODEL;
        }
        else {
            action = APPLY_PALETTE_AND_FILTER_3D_MODEL;
            drawing_done = 0;
        }

    }

    @Override
    public void run() {

        switch (action) {

            case NORMAL:
                if(quickDraw) {
                    quickDraw();
                }
                else {
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
                if(quickDraw) {
                    quickDrawPolar();
                }
                else {
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
                if(quickDraw) {
                    quickDrawDomain();
                }
                else {
                    drawDomain();
                }
                break;
            case DOMAIN_POLAR:
                if(quickDraw) {
                    quickDrawDomainPolar();
                }
                else {
                    drawDomainPolar();
                }
                break;

        }

    }

    private void draw() {

        int image_size = image.getHeight();

        if(julia) {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJulia3DAntialiased(image_size);
                }
                else {
                    drawJulia3D(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaAntialiased(image_size);
                }
                else {
                    drawJulia(image_size);
                }
            }
        }
        else {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractal3DAntialiased(image_size);
                }
                else {
                    drawFractal3D(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalAntialiased(image_size);
                }
                else {
                    drawFractal(image_size);
                }
            }
        }

        if(drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

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
                ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            }
            else {
                ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
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
     ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
     }
     else {
     ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
     }

     int temp2 = image_size * image_size;
     ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
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
     int x, y, loc, counter = 0;
        
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
     //counter++;

        

     } while(true);

     //thread_calculated += drawing_done;
     }*/

    private void quickDraw() {

        int image_size = image.getHeight();

        if(julia) {
            quickDrawJulia(image_size);
        }
        else {
            quickDrawFractal(image_size);
        }

        total_calculated.addAndGet(thread_calculated);

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            if(julia) {
                ptr.updateValues("Julia mode");
            }
            else {
                ptr.updateValues("Normal mode");
            }

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void quickDrawPolar() {

        int image_size = image.getHeight();

        if(julia) {
            quickDrawJuliaPolar(image_size);
        }
        else {
            quickDrawFractalPolar(image_size);
        }

        total_calculated.addAndGet(thread_calculated);

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            if(julia) {
                ptr.updateValues("Julia mode");
            }
            else {
                ptr.updateValues("Normal mode");
            }

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void drawDomain() {

        int image_size = image.getHeight();

        if(julia) {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaDomain3DAntialiased(image_size);
                }
                else {
                    drawJuliaDomain3D(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaDomainAntialiased(image_size);
                }
                else {
                    drawJuliaDomain(image_size);
                }
            }
        }
        else {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalDomain3DAntialiased(image_size);
                }
                else {
                    drawFractalDomain3D(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalDomainAntialiased(image_size);
                }
                else {
                    drawFractalDomain(image_size);
                }
            }

        }

        if(drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            if(d3) {
                ptr.updateValues("3D mode");
            }
            else {
                ptr.updateValues("Domain C. mode");
            }

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            if(d3) {
                ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            }
            else {
                ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }
    }

    private void quickDrawDomain() {

        int image_size = image.getHeight();

        if(julia) {
            quickDrawJuliaDomain(image_size);
        }
        else {
            quickDrawFractalDomain(image_size);
        }

        total_calculated.addAndGet(thread_calculated);

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            ptr.updateValues("Domain C. mode");

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void quickDrawDomainPolar() {

        int image_size = image.getHeight();

        if(julia) {
            quickDrawJuliaDomainPolar(image_size);
        }
        else {
            quickDrawFractalDomainPolar(image_size);
        }

        total_calculated.addAndGet(thread_calculated);

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            ptr.updateValues("Domain C. mode");

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void drawPolar() {
        int image_size = image.getHeight();

        if(julia) {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaPolar3DAntialiased(image_size);
                }
                else {
                    drawJuliaPolar3D(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaPolarAntialiased(image_size);
                }
                else {
                    drawJuliaPolar(image_size);
                }
            }
        }
        else {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalPolar3DAntialiased(image_size);
                }
                else {
                    drawFractalPolar3D(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalPolarAntialiased(image_size);
                }
                else {
                    drawFractalPolar(image_size);
                }
            }
        }

        if(drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

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
                ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            }
            else {
                ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }
    }

    private void drawDomainPolar() {
        int image_size = image.getHeight();

        if(julia) {

            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaDomainPolar3DAntialiased(image_size);
                }
                else {
                    drawJuliaDomainPolar3D(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawJuliaDomainPolarAntialiased(image_size);
                }
                else {
                    drawJuliaDomainPolar(image_size);
                }
            }
        }
        else {
            if(d3) {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalDomainPolar3DAntialiased(image_size);
                }
                else {
                    drawFractalDomainPolar3D(image_size);
                }
            }
            else {
                if(filters[MainWindow.ANTIALIASING]) {
                    drawFractalDomainPolarAntialiased(image_size);
                }
                else {
                    drawFractalDomainPolar(image_size);
                }
            }
        }

        if(drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            if(d3) {
                ptr.updateValues("3D mode");
            }
            else {
                ptr.updateValues("Domain C. mode");
            }

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            if(d3) {
                ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            }
            else {
                ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            }

            int temp2 = image_size * image_size;
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double)total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
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
    
     double center = LoadFractal(p, xreal = temp_xcenter_size + x2 * temp_size_image_size, yimag = temp_ycenter_size - y2 * temp_size_image_size, loc);
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
    
     rgbs[loc] = getColor(result);
    
     drawing_done++;
    
     Done[p] |= Loaded;
    
     return image_iterations[loc];
    
     }
    
     */
    private void drawFractal(int image_size) {
        // ptr.setWholeImageDone(true); // demo

        if(!boundary_tracing) {
            drawFractalBruteForce(image_size);
        }
        else {
            drawFractalBoundaryTracing(image_size);
        }
    }

    private int getColor(double result) {

        if(result == max_iterations) {
            return fractal_color;
        }
        else if(result == -max_iterations) {
            return dem_color;
        }
        else {
            return palette_color.getPaletteColor(result + color_cycling_location);
        }

    }

    private void quickDrawFractalDomain(int image_size) {

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

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            tempx = loc % image_size_tile;
            tempy = loc / image_size_tile;

            x = tempx * tile;
            y = tempy * tile;

            loc2 = y * image_size + x;
            image_iterations[loc2] = color = rgbs[loc2] = getDomainColor(fractal.calculateFractalDomain(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y)));

            thread_calculated++;

            tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
            tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

            for(int i = y; i < tempy; i++) {
                for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                    rgbs[loc3] = color;
                }
            }

        } while(true);

    }

    private void quickDrawFractal(int image_size) {

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

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            tempx = loc % image_size_tile;
            tempy = loc / image_size_tile;

            x = tempx * tile;
            y = tempy * tile;

            loc2 = y * image_size + x;
            image_iterations[loc2] = fractal.calculateFractal(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
            color = rgbs[loc2] = getColor(image_iterations[loc2]);

            thread_calculated++;

            tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
            tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

            for(int i = y; i < tempy; i++) {
                for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                    rgbs[loc3] = color;
                }
            }

        } while(true);

    }

    private void quickDrawJuliaDomain(int image_size) {

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

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            tempx = loc % image_size_tile;
            tempy = loc / image_size_tile;

            x = tempx * tile;
            y = tempy * tile;

            loc2 = y * image_size + x;
            image_iterations[loc2] = color = rgbs[loc2] = getDomainColor(fractal.calculateJuliaDomain(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y)));

            thread_calculated++;

            tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
            tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

            for(int i = y; i < tempy; i++) {
                for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                    rgbs[loc3] = color;
                }
            }

        } while(true);

    }

    private void quickDrawJulia(int image_size) {

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

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            tempx = loc % image_size_tile;
            tempy = loc / image_size_tile;

            x = tempx * tile;
            y = tempy * tile;

            loc2 = y * image_size + x;
            image_iterations[loc2] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
            color = rgbs[loc2] = getColor(image_iterations[loc2]);

            thread_calculated++;

            tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
            tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

            for(int i = y; i < tempy; i++) {
                for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                    rgbs[loc3] = color;
                }
            }

        } while(true);

    }

    private void quickDrawFractalPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int image_size_tile = image_size / tile, tempx, tempy;
        int condition = (image_size_tile) * (image_size_tile);

        int color, loc2, loc, x, y;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            tempx = loc % image_size_tile;
            tempy = loc / image_size_tile;

            x = tempx * tile;
            y = tempy * tile;

            loc2 = y * image_size + x;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc2] = fractal.calculateFractal(new Complex(xcenter + r * cf, ycenter + r * sf));
            color = rgbs[loc2] = getColor(image_iterations[loc2]);

            thread_calculated++;

            tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
            tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

            for(int i = y; i < tempy; i++) {
                for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                    rgbs[loc3] = color;
                }
            }

        } while(true);

    }

    private void quickDrawFractalDomainPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int image_size_tile = image_size / tile, tempx, tempy;
        int condition = (image_size_tile) * (image_size_tile);

        int color, loc2, loc, x, y;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            tempx = loc % image_size_tile;
            tempy = loc / image_size_tile;

            x = tempx * tile;
            y = tempy * tile;

            loc2 = y * image_size + x;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc2] = color = rgbs[loc2] = getDomainColor(fractal.calculateFractalDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

            thread_calculated++;

            tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
            tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

            for(int i = y; i < tempy; i++) {
                for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                    rgbs[loc3] = color;
                }
            }

        } while(true);

    }

    private void quickDrawJuliaPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int image_size_tile = image_size / tile, tempx, tempy;
        int condition = (image_size_tile) * (image_size_tile);

        int color, loc2, loc, x, y;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            tempx = loc % image_size_tile;
            tempy = loc / image_size_tile;

            x = tempx * tile;
            y = tempy * tile;

            loc2 = y * image_size + x;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc2] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            color = rgbs[loc2] = getColor(image_iterations[loc2]);

            thread_calculated++;

            tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
            tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

            for(int i = y; i < tempy; i++) {
                for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                    rgbs[loc3] = color;
                }
            }

        } while(true);

    }

    private void quickDrawJuliaDomainPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int image_size_tile = image_size / tile, tempx, tempy;
        int condition = (image_size_tile) * (image_size_tile);

        int color, loc2, loc, x, y;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            tempx = loc % image_size_tile;
            tempy = loc / image_size_tile;

            x = tempx * tile;
            y = tempy * tile;

            loc2 = y * image_size + x;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc2] = color = rgbs[loc2] = getDomainColor(fractal.calculateJuliaDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

            thread_calculated++;

            tempx = tempx == image_size_tile - 1 ? image_size : x + tile;
            tempy = tempy == image_size_tile - 1 ? image_size : y + tile;

            for(int i = y; i < tempy; i++) {
                for(int j = x, loc3 = i * image_size + j; j < tempx; j++, loc3++) {
                    rgbs[loc3] = color;
                }
            }

        } while(true);

    }

    private void drawFractalBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        //Better brute force
        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            image_iterations[loc] = fractal.calculateFractal(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
            rgbs[loc] = getColor(image_iterations[loc]);

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        //Brute force
             /*double temp = temp_xcenter_size + temp_size_image_size * FROMx;
         double temp_y0 = temp_ycenter_size - temp_size_image_size * FROMy;
         double temp_x0 = temp;
        
         for(int y = FROMy; y < TOy; y++, temp_y0 += temp_size_image_size) {
         for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size, loc++) {
        
         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
         rgbs[loc] = getColor(temp_result);
        
        
         drawing_done++;
        
         }
        
         if(drawing_done / pixel_percent >= 1) {
         update(drawing_done);
         thread_calculated += drawing_done;
         drawing_done = 0;
         }
        
         temp_x0 = temp;
        
         }
        
         thread_calculated += drawing_done;*/
        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);

        }
    }

    private void drawFractalDomain(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        //Better brute force
        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            image_iterations[loc] = rgbs[loc] = getDomainColor(fractal.calculateFractalDomain(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y)));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawFractalBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;
        //double delX[] = {temp_size_image_size, 0., -temp_size_image_size, 0.};
        //double delY[] = {0., temp_size_image_size, 0., -temp_size_image_size};

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        //double curX, curY; 
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    //curX = temp_x0;
                    //curY = temp_y0;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = getColor(start_val);
                    drawing_done++;
                    thread_calculated++;
                    /*ptr.getMainPanel().repaint();
                     try {
                     Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}*/

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = getColor(image_iterations[nextPix]);
                                drawing_done++;
                                thread_calculated++;
                                /*ptr.getMainPanel().repaint();
                                 try {
                                 Thread.sleep(1); //demo
                                 }
                                 catch (InterruptedException ex) {}*/
                            }

                            if(nextColor == startColor) {
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

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
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
                                        update(drawing_done);
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
                update(drawing_done);
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
         update(drawing_done);
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
         update(drawing_done);
         drawing_done = 0;
         }
         }*/
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
        
         temp_y0 = temp_ycenter_size - temp_size_image_size * y;           
        
         x = slice_FROMx + step;
         temp_x0 = temp_xcenter_size + temp_size_image_size * x;                        
        
         loc = y * image_size + x;
        
         temp_starting_pixel_cicle =  temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        
         rgbs[loc] = temp_starting_pixel_color = getColor(temp_result);
        
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
         rgbs[loc] = getColor(temp_result);
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
         temp_y0 += temp_size_image_size;//= temp_ycenter_size - temp_size_image_size * y;
         loc = y * image_size + x;
         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        
        
         if(temp_result == temp_starting_pixel_cicle) {
         rgbs[loc] = temp_starting_pixel_color;
         image.setRGB(x, y, rgbs[loc]);//demo
         ptr.getMainPanel().repaint();//demo
         }
         else {
         rgbs[loc] = getColor(temp_result);
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
         rgbs[loc] = getColor(temp_result);
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
         temp_y0 -= temp_size_image_size;//= temp_ycenter_size - temp_size_image_size * y;
         loc = y * image_size + x;
         temp_result = image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
        
        
         if(temp_result == temp_starting_pixel_cicle) {
         rgbs[loc] = temp_starting_pixel_color;
         image.setRGB(x, y, rgbs[loc]);//demo
         ptr.getMainPanel().repaint();//demo
         }
         else {
         rgbs[loc] = getColor(temp_result);
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
         update(drawing_done);
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
        
         update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
         break;
         }
         }
         }
         }
        
         thread_calculated += drawing_done; */
        if(bump_map || fake_de || rainbow_palette) {

            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }
    }

    private void drawFractalPolar(int image_size) {

        if(!boundary_tracing) {
            drawFractalPolarBruteForce(image_size);
        }
        else {
            drawFractalPolarBoundaryTracing(image_size);
        }

    }

    private void drawFractalPolarAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawFractalPolarAntialiasedBruteForce(image_size);
        }
        else {
            drawFractalPolarAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawJuliaPolarAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawJuliaPolarAntialiasedBruteForce(image_size);
        }
        else {
            drawJuliaPolarAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawJuliaPolar(int image_size) {

        if(!boundary_tracing) {
            drawJuliaPolarBruteForce(image_size);
        }
        else {
            drawJuliaPolarBoundaryTracing(image_size);
        }

    }

    private void drawFractalPolarBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int pixel_percent = image_size * image_size / 100;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;
        //double delX[] = {temp_size_image_size, 0., -temp_size_image_size, 0.};
        //double delY[] = {0., temp_size_image_size, 0., -temp_size_image_size};

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        //double curX, curY; 
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;
                    //curX = temp_x0;
                    //curY = temp_y0;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = getColor(start_val);
                    drawing_done++;
                    thread_calculated++;
                    /*ptr.getMainPanel().repaint();
                     try {
                     Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}*/

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
                        curPix = startPix = startPix - image_size;
                        iy--;
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

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = getColor(image_iterations[nextPix]);
                                drawing_done++;
                                thread_calculated++;
                                /*ptr.getMainPanel().repaint();
                                 try {
                                 Thread.sleep(1); //demo
                                 }
                                 catch (InterruptedException ex) {}*/
                            }

                            if(nextColor == startColor) {
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

                                        floodPix++;

                                        if((floodColor = rgbs[floodPix]) == culcColor) {
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
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
                                        update(drawing_done);
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
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }
    }

    private void drawFractalPolarAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        double sf3, cf3, r3;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                    color = getColor(start_val);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < supersampling_num; i++) {

                        sf3 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf3 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r3 = r * antialiasing_x[i];

                        temp_x0 = xcenter + r3 * cf3;
                        temp_y0 = ycenter + r3 * sf3;

                        temp_result = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                        color = getColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                color = getColor(image_iterations[nextPix]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < supersampling_num; i++) {

                                    sf3 = sf2 * antialiasing_y_cos[i] + cf2 * antialiasing_y_sin[i];
                                    cf3 = cf2 * antialiasing_y_cos[i] - sf2 * antialiasing_y_sin[i];

                                    r3 = r2 * antialiasing_x[i];

                                    nextX = xcenter + r3 * cf3;
                                    nextY = ycenter + r3 * sf3;

                                    temp_result = fractal.calculateFractal(new Complex(nextX, nextY));
                                    color = getColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;

                            }

                            if(nextColor == startColor) {
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
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
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
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }
    }

    private void drawJuliaPolarAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        double sf3, cf3, r3;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    color = getColor(start_val);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < supersampling_num; i++) {

                        sf3 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf3 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r3 = r * antialiasing_x[i];

                        temp_x0 = xcenter + r3 * cf3;
                        temp_y0 = ycenter + r3 * sf3;

                        temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                        color = getColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                color = getColor(image_iterations[nextPix]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < supersampling_num; i++) {

                                    sf3 = sf2 * antialiasing_y_cos[i] + cf2 * antialiasing_y_sin[i];
                                    cf3 = cf2 * antialiasing_y_cos[i] - sf2 * antialiasing_y_sin[i];

                                    r3 = r2 * antialiasing_x[i];

                                    nextX = xcenter + r3 * cf3;
                                    nextY = ycenter + r3 * sf3;

                                    temp_result = fractal.calculateJulia(new Complex(nextX, nextY));
                                    color = getColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;

                            }

                            if(nextColor == startColor) {
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
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
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
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }
    }

    private void drawJuliaPolarBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int pixel_percent = image_size * image_size / 100;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = getColor(start_val);
                    drawing_done++;
                    thread_calculated++;

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = getColor(image_iterations[nextPix]);
                                drawing_done++;
                                thread_calculated++;

                            }

                            if(nextColor == startColor) {
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
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;

                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
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
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }
    }

    private void drawFractalPolarBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        /*double start;
         double end = Math.log(size);

         int pixel_percent = image_size * image_size / 100;

         double f, sf, cf, r;
         double muly = (2 * circle_period * Math.PI) / image_size;

         double mulx = muly * height_ratio;

         start = -mulx * image_size + end;*/
        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc] = fractal.calculateFractal(new Complex(xcenter + r * cf, ycenter + r * sf));
            rgbs[loc] = getColor(image_iterations[loc]);

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void drawFractalDomainPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc] = rgbs[loc] = getDomainColor(fractal.calculateFractalDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawFractalPolarAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc] = fractal.calculateFractal(new Complex(xcenter + r * cf, ycenter + r * sf));
            color = getColor(image_iterations[loc]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {

                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                r2 = r * antialiasing_x[i];

                temp_result = fractal.calculateFractal(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                color = getColor(temp_result);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void drawFractalDomainPolarAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc] = color = getDomainColor(fractal.calculateFractalDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {

                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                r2 = r * antialiasing_x[i];

                color = getDomainColor(fractal.calculateFractalDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2)));

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawJuliaPolarAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            color = getColor(image_iterations[loc]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {

                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                r2 = r * antialiasing_x[i];

                temp_result = fractal.calculateJulia(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                color = getColor(temp_result);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void drawJuliaDomainPolarAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc] = color = getDomainColor(fractal.calculateJuliaDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {

                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                r2 = r * antialiasing_x[i];

                color = getDomainColor(fractal.calculateJuliaDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2)));

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawJuliaPolarBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            rgbs[loc] = getColor(image_iterations[loc]);

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void drawJuliaDomainPolar(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations[loc] = rgbs[loc] = getDomainColor(fractal.calculateJuliaDomain(new Complex(xcenter + r * cf, ycenter + r * sf)));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void draw3D(int image_size, boolean update_progress) {

        int x, y;

        int w2 = image_size / 2;

        int n1 = detail - 1;

        double dx = image_size / (double)n1;

        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        double mod;
        double norm_0_0, norm_0_1, norm_0_2, norm_1_0, norm_1_1, norm_1_2;

        int pixel_percent = detail * detail / 100;

        for(x = FROMx; x < TOx; x++) {
            double c1 = dx * x - w2;
            for(y = FROMy; y < TOy; y++) {

                if(y < n1 && x < n1) {
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

                    Norm1z[x][y][0] = (float)(m20 * norm_0_0 + m21 * norm_0_1 + m22 * norm_0_2);
                    Norm1z[x][y][1] = (float)(m20 * norm_1_0 + m21 * norm_1_1 + m22 * norm_1_2);
                }

                double c2 = dx * y - w2;
                vert1[x][y][0] = (float)(m00 * c1 + m02 * c2);
                vert1[x][y][1] = (float)(m10 * c1 + m11 * vert[x][y][1] + m12 * c2);

                if(update_progress) {
                    drawing_done++;
                }
            }

            if(update_progress && (drawing_done / pixel_percent >= 1)) {
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

        }

    }

    private void drawFractal3D(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            temp = fractal.calculateFractal3D(new Complex(temp_xcenter_size + dr_x * x, temp_ycenter_size - dr_y * y));
            vert[x][y][1] = (float)(temp[0]);
            vert[x][y][0] = getColor(temp[1]);
            vert[x][y][2] = (float)(temp[1]);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing3D(detail);
        }

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalDomain3D(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        int n1 = detail - 1;

        int w2 = image_size / 2;
        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            Complex a = fractal.calculateFractalDomain(new Complex(temp_xcenter_size + dr_x * x, temp_ycenter_size - dr_y * y));

            float res = (float)a.norm();

            vert[x][y][1] = res > 50 ? 50 : res;
            vert[x][y][0] = getDomainColor(a);
            vert[x][y][2] = vert[x][y][1];

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalPolar3D(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            temp = fractal.calculateFractal3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
            vert[x][y][1] = (float)(temp[0]);
            vert[x][y][0] = getColor(temp[1]);
            vert[x][y][2] = (float)(temp[1]);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing3D(detail);
        }

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalDomainPolar3D(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            Complex a = fractal.calculateFractalDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));

            float res = (float)a.norm();

            vert[x][y][1] = res > 50 ? 50 : res;
            vert[x][y][0] = getDomainColor(a);
            vert[x][y][2] = vert[x][y][1];

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaPolar3D(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            temp = fractal.calculateJulia3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
            vert[x][y][1] = (float)(temp[0]);
            vert[x][y][0] = getColor(temp[1]);
            vert[x][y][2] = (float)(temp[1]);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing3D(detail);
        }

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaDomainPolar3D(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            Complex a = fractal.calculateJuliaDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));

            float res = (float)a.norm();

            vert[x][y][1] = res > 50 ? 50 : res;
            vert[x][y][0] = getDomainColor(a);
            vert[x][y][2] = vert[x][y][1];

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalPolar3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2, sf3, cf3, r3;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int red, green, blue, color;

        double height;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

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

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            temp = fractal.calculateFractal3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
            height = temp[0];
            color = getColor(temp[1]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < supersampling_num; k++) {

                sf3 = sf2 * antialiasing_y_cos[k] + cf2 * antialiasing_y_sin[k];
                cf3 = cf2 * antialiasing_y_cos[k] - sf2 * antialiasing_y_sin[k];

                r3 = r2 * antialiasing_x[k];

                temp = fractal.calculateFractal3D(new Complex(xcenter + r3 * cf3, ycenter + r3 * sf3));
                color = getColor(temp[1]);

                height += temp[0];
                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][0] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            vert[x][y][2] = (float)(temp[1]);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing3D(detail);
        }

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalDomainPolar3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2, sf3, cf3, r3;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int red, green, blue, color;

        double height;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

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

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            Complex a = fractal.calculateFractalDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));

            float res = (float)a.norm();

            height = res > 50 ? 50 : res;
            color = getDomainColor(a);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < supersampling_num; k++) {

                sf3 = sf2 * antialiasing_y_cos[k] + cf2 * antialiasing_y_sin[k];
                cf3 = cf2 * antialiasing_y_cos[k] - sf2 * antialiasing_y_sin[k];

                r3 = r2 * antialiasing_x[k];

                a = fractal.calculateFractalDomain(new Complex(xcenter + r3 * cf3, ycenter + r3 * sf3));

                res = (float)a.norm();

                height += res > 50 ? 50 : res;
                color = getDomainColor(a);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][0] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            vert[x][y][2] = vert[x][y][1];

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaPolar3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2, sf3, cf3, r3;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int red, green, blue, color;

        double height;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

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

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            temp = fractal.calculateJulia3D(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
            height = temp[0];
            color = getColor(temp[1]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < supersampling_num; k++) {

                sf3 = sf2 * antialiasing_y_cos[k] + cf2 * antialiasing_y_sin[k];
                cf3 = cf2 * antialiasing_y_cos[k] - sf2 * antialiasing_y_sin[k];

                r3 = r2 * antialiasing_x[k];

                temp = fractal.calculateJulia3D(new Complex(xcenter + r3 * cf3, ycenter + r3 * sf3));
                color = getColor(temp[1]);

                height += temp[0];
                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][0] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            vert[x][y][2] = (float)(temp[1]);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing3D(detail);
        }

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaDomainPolar3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        int pixel_percent = detail * detail / 100;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1;

        double start;
        double center = Math.log(size);

        double f2, sf2, cf2, r2, sf3, cf3, r3;
        double muly = (2 * circle_period * Math.PI) / n1;

        double mulx = muly * height_ratio;

        start = center - mulx * n1 / 2.0;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int red, green, blue, color;

        double height;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

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

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            f2 = y * muly;
            sf2 = Math.sin(f2);
            cf2 = Math.cos(f2);

            r2 = Math.exp(x * mulx + start);

            Complex a = fractal.calculateJuliaDomain(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));

            float res = (float)a.norm();

            height = res > 50 ? 50 : res;
            color = getDomainColor(a);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < supersampling_num; k++) {

                sf3 = sf2 * antialiasing_y_cos[k] + cf2 * antialiasing_y_sin[k];
                cf3 = cf2 * antialiasing_y_cos[k] - sf2 * antialiasing_y_sin[k];

                r3 = r2 * antialiasing_x[k];

                a = fractal.calculateJuliaDomain(new Complex(xcenter + r3 * cf3, ycenter + r3 * sf3));

                res = (float)a.norm();

                height += res > 50 ? 50 : res;
                color = getDomainColor(a);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][0] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            vert[x][y][2] = vert[x][y][1];

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractal3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        double temp_x0, temp_y0, height;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            temp = fractal.calculateFractal3D(new Complex(temp_x0 = temp_xcenter_size + dr_x * x, temp_y0 = temp_ycenter_size - dr_y * y));
            height = temp[0];
            color = getColor(temp[1]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < supersampling_num; k++) {
                temp = fractal.calculateFractal3D(new Complex(temp_x0 + antialiasing_x[k], temp_y0 + antialiasing_y[k]));
                color = getColor(temp[1]);

                height += temp[0];
                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][0] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            vert[x][y][2] = (float)(temp[1]);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing3D(detail);
        }

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);;

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalDomain3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        double temp_x0, temp_y0, height;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            Complex a = fractal.calculateFractalDomain(new Complex(temp_x0 = temp_xcenter_size + dr_x * x, temp_y0 = temp_ycenter_size - dr_y * y));

            float res = (float)a.norm();

            height = res > 50 ? 50 : res;
            color = getDomainColor(a);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < supersampling_num; k++) {
                a = fractal.calculateFractalDomain(new Complex(temp_x0 + antialiasing_x[k], temp_y0 + antialiasing_y[k]));

                res = (float)a.norm();

                color = getDomainColor(a);
                height += res > 50 ? 50 : res;

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][0] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            vert[x][y][2] = vert[x][y][1];

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);;

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawFractalAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        //better Brute force with antialiasing
        int x, y, loc, counter = 0;
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

            image_iterations[loc] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
            color = getColor(image_iterations[loc]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {
                temp_result = fractal.calculateFractal(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                color = getColor(temp_result);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }
    }

    private void drawFractalDomainAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        //better Brute force with antialiasing
        int x, y, loc, counter = 0;
        int color;

        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

            image_iterations[loc] = color = getDomainColor(fractal.calculateFractalDomain(new Complex(temp_x0, temp_y0)));

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {
                color = getDomainColor(fractal.calculateFractalDomain(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i])));

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawFractalAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int color;

        double temp_result;

        int red, green, blue;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateFractal(new Complex(temp_x0, temp_y0));
                    color = getColor(start_val);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < supersampling_num; i++) {
                        temp_result = fractal.calculateFractal(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;
                    /*ptr.getMainPanel().repaint();
                     try {
                     Thread.sleep(1); //demo
                     }
                     catch (InterruptedException ex) {}*/

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations[nextPix] = fractal.calculateFractal(new Complex(nextX, nextY));
                                color = getColor(image_iterations[nextPix]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < supersampling_num; i++) {
                                    temp_result = fractal.calculateFractal(new Complex(nextX + antialiasing_x[i], nextY + antialiasing_y[i]));
                                    color = getColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;
                                /*ptr.getMainPanel().repaint();
                                 try {
                                 Thread.sleep(1); //demo
                                 }
                                 catch (InterruptedException ex) {}*/
                            }

                            if(nextColor == startColor) {
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
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
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
                                        update(drawing_done);
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
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void drawFractalAntialiased(int image_size) {

        //ptr.setWholeImageDone(true); // demo
        if(!boundary_tracing) {
            drawFractalAntialiasedBruteForce(image_size);
        }
        else {
            drawFractalAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawJuliaBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            image_iterations[loc] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
            rgbs[loc] = getColor(image_iterations[loc]);

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }
    }

    private void drawJuliaDomain(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int x, y, loc, counter = 0;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            image_iterations[loc] = rgbs[loc] = getDomainColor(fractal.calculateJuliaDomain(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y)));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawJuliaBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = getColor(start_val);
                    drawing_done++;
                    thread_calculated++;

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = getColor(image_iterations[nextPix]);
                                drawing_done++;
                                thread_calculated++;
                            }

                            if(nextColor == startColor) {
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
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
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
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void drawJulia(int image_size) {

        if(!boundary_tracing) {
            drawJuliaBruteForce(image_size);
        }
        else {
            drawJuliaBoundaryTracing(image_size);
        }

    }

    private void drawJulia3D(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            temp = fractal.calculateJulia3D(new Complex(temp_xcenter_size + dr_x * x, temp_ycenter_size - dr_y * y));
            vert[x][y][1] = (float)(temp[0]);
            vert[x][y][0] = getColor(temp[1]);
            vert[x][y][2] = (float)(temp[1]);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing3D(detail);
        }

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaDomain3D(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            Complex a = fractal.calculateJuliaDomain(new Complex(temp_xcenter_size + dr_x * x, temp_ycenter_size - dr_y * y));

            float res = (float)a.norm();

            vert[x][y][1] = res > 50 ? 50 : res;
            vert[x][y][0] = getDomainColor(a);
            vert[x][y][2] = vert[x][y][1];

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJulia3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        double temp_x0, temp_y0, height;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            temp = fractal.calculateJulia3D(new Complex(temp_x0 = temp_xcenter_size + dr_x * x, temp_y0 = temp_ycenter_size - dr_y * y));
            height = temp[0];
            color = getColor(temp[1]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < supersampling_num; k++) {
                temp = fractal.calculateJulia3D(new Complex(temp_x0 + antialiasing_x[k], temp_y0 + antialiasing_y[k]));
                color = getColor(temp[1]);

                height += temp[0];
                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][0] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            vert[x][y][2] = (float)(temp[1]);

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing3D(detail);
        }

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaDomain3DAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = detail * detail / 100;

        int n1 = detail - 1;

        int w2 = image_size / 2;

        double dx = image_size / (double)n1, dr_x = size / n1, dr_y = (size * height_ratio) / n1;

        int x, y, loc, counter = 0;

        int condition = detail * detail;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        double temp_x0, temp_y0, height;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % detail;
            y = loc / detail;

            Complex a = fractal.calculateJuliaDomain(new Complex(temp_x0 = temp_xcenter_size + dr_x * x, temp_y0 = temp_ycenter_size - dr_y * y));

            float res = (float)a.norm();

            height = res > 50 ? 50 : res;
            color = getDomainColor(a);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int k = 0; k < supersampling_num; k++) {
                a = fractal.calculateJuliaDomain(new Complex(temp_x0 + antialiasing_x[k], temp_y0 + antialiasing_y[k]));

                res = (float)a.norm();

                color = getDomainColor(a);
                height += res > 50 ? 50 : res;

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            vert[x][y][1] = (float)(height / temp_samples);
            vert[x][y][0] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            vert[x][y][2] = vert[x][y][1];

            drawing_done++;
            counter++;

            if(counter % detail == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        } while(true);

        if(height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyHeightScaling();

            if(gaussian_scaling) {
                gaussianHeightScalingInit();
            }

        }

        if(gaussian_scaling) {
            try {
                gaussian_scaling_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            gaussianHeightScaling();
        }

        if(shade_height) {
            if(shade_color_height_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                shadeColorBasedOnHeight();

            }
        }

        try {
            calculate_vectors_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        if(gaussian_scaling) {
            gaussianHeightScalingEnd();
        }

        calculate3DVectors(n1, dx, w2);

        if(painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(n1, w2);

            thread_calculated = image_size * image_size;
        }

    }

    private void drawJuliaAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int x, y, loc, counter = 0;
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

            image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
            color = getColor(image_iterations[loc]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {
                temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                color = getColor(temp_result);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }
    }

    private void drawJuliaDomainAntialiased(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int x, y, loc, counter = 0;
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

            image_iterations[loc] = color = getDomainColor(fractal.calculateJuliaDomain(new Complex(temp_x0, temp_y0)));

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {
                color = getDomainColor(fractal.calculateJuliaDomain(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i])));

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

            drawing_done++;
            counter++;

            if(counter % image_size == 0 && drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while(true);

        thread_calculated += drawing_done;

    }

    private void drawJuliaAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int color;

        double temp_result;

        int red, green, blue;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY, start_val;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    color = getColor(start_val);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < supersampling_num; i++) {
                        temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                color = getColor(image_iterations[nextPix]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < supersampling_num; i++) {
                                    temp_result = fractal.calculateJulia(new Complex(nextX + antialiasing_x[i], nextY + antialiasing_y[i]));
                                    color = getColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;
                            }

                            if(nextColor == startColor) {
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
                                            drawing_done++;
                                            rgbs[floodPix] = startColor;
                                            image_iterations[floodPix] = start_val;
                                        }
                                        else if(floodColor != startColor) {
                                            break;
                                        }

                                    }

                                    if(drawing_done / pixel_percent >= 1) {
                                        update(drawing_done);
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
                update(drawing_done);
                drawing_done = 0;
            }
        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void drawJuliaAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawJuliaAntialiasedBruteForce(image_size);
        }
        else {
            drawJuliaAntialiasedBoundaryTracing(image_size);
        }

    }

    private void fastJuliaDraw() {

        int image_size = image.getHeight();

        if(fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
            drawFastJuliaAntialiased(image_size);
        }
        else {
            drawFastJulia(image_size);
        }

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            if(fast_julia_filters) {
                applyFilters();
            }

            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, 0, image_size - 1, image_size - 1);
            ptr.getMainPanel().getGraphics().drawImage(image, ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);

        }

    }

    private void fastJuliaDrawPolar() {

        int image_size = image.getHeight();

        if(fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
            drawFastJuliaPolarAntialiased(image_size);
        }
        else {
            drawFastJuliaPolar(image_size);
        }

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            if(fast_julia_filters) {
                applyFilters();
            }

            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, 0, image_size - 1, image_size - 1);
            ptr.getMainPanel().getGraphics().drawImage(image, ptr.getScrollPane().getHorizontalScrollBar().getValue(), ptr.getScrollPane().getVerticalScrollBar().getValue(), null);

        }

    }

    private void drawFastJuliaBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int x, y, loc;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            image_iterations_fast_julia[loc] = fractal.calculateJulia(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
            rgbs[loc] = getColor(image_iterations_fast_julia[loc]);

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessingFastJulia(image_size);
        }

    }

    private int paletteRainbow(double[] image_iterations, int i, int j, int image_size) {

        int k0 = image_size * i + j;
        int kx = k0 + image_size;
        int sx = 1;
        if(i == image_size - 1) {
            kx -= 2 * image_size;
            sx = -1;
        }

        int ky = k0 + 1;
        int sy = 1;
        if(j == image_size - 1) {
            ky -= 2;
            sy = -1;
        }

        double n0 = Math.abs(image_iterations[k0]);
        double nx = Math.abs(image_iterations[kx]);
        double ny = Math.abs(image_iterations[ky]);
        double zx = sx * (nx - n0);
        double zy = sy * (ny - n0);

        double zz = 1.0;

        double z = Math.sqrt(zx * zx + zy * zy + zz * zz);
        //zz /= z;
        zx /= z;
        zy /= z;

        double hue = Math.atan2(zy, zx) / (2 * Math.PI);

        if(image_iterations[k0] < 0) {
            return getColor(image_iterations[k0] - hue * palette_color.getPaletteLength() * rainbow_palette_factor);
        }
        else {
            return getColor(image_iterations[k0] + hue * palette_color.getPaletteLength() * rainbow_palette_factor);
        }

    }

    private int paletteRainbow3D(float[][][] image_iterations, int i, int j, int image_size) {

        int i1 = i + 1;
        int sx = 1;
        if(i == image_size - 1) {
            i1 -= 2;
            sx = -1;
        }

        int j1 = j + 1;
        int sy = 1;
        if(j == image_size - 1) {
            j1 -= 2;
            sy = -1;
        }

        double n0 = Math.abs(image_iterations[i][j][1]);
        double nx = Math.abs(image_iterations[i1][j][1]);
        double ny = Math.abs(image_iterations[i][j1][1]);
        double zx = sx * (nx - n0);
        double zy = sy * (ny - n0);

        double zz = 1.0;

        double z = Math.sqrt(zx * zx + zy * zy + zz * zz);
        //zz /= z;
        zx /= z;
        zy /= z;

        double hue = Math.atan2(zx, zy) / (2 * Math.PI); // zx , zy instead of zy , zx

        if(image_iterations[i][j][2] < 0) {
            return getColor(image_iterations[i][j][2] - hue * palette_color.getPaletteLength() * rainbow_palette_factor);
        }
        else {
            return getColor(image_iterations[i][j][2] + hue * palette_color.getPaletteLength() * rainbow_palette_factor);
        }

    }

    private int postProcessingSmoothing3D(int new_color, float[][][] image_iterations, int color, int i, int j, int image_size, double factor) {

        int i1 = i + 1;
        int sx = 1;
        if(i == image_size - 1) {
            i1 -= 2;
            sx = -1;
        }

        int j1 = j + 1;
        int sy = 1;
        if(j == image_size - 1) {
            j1 -= 2;
            sy = -1;
        }

        double n0 = Math.abs(image_iterations[i][j][1]);
        double nx = Math.abs(image_iterations[i1][j][1]);
        double ny = Math.abs(image_iterations[i][j1][1]);
        double zx = sx * (nx - n0);
        double zy = sy * (ny - n0);

        double zz = 1 / factor;

        double z = Math.sqrt(zx * zx + zy * zy + zz * zz);
        zz /= z;
        double coef = zz;

        int r = color & 0xFF0000;
        int g = color & 0x00FF00;
        int b = color & 0x0000FF;

        int fc_red = new_color & 0xFF0000;
        int fc_green = new_color & 0x00FF00;
        int fc_blue = new_color & 0x0000FF;

        double inv_coef;

        inv_coef = coef;
        coef = 1 - coef;

        final int ret = 0xff000000 | (int)(r * coef + fc_red * inv_coef + 0.5) & 0xFF0000 | (int)(g * coef + fc_green * inv_coef + 0.5) & 0x00FF00 | (int)(b * coef + fc_blue * inv_coef + 0.5);

        return ret;

    }

    private int postProcessingSmoothing(int new_color, double[] image_iterations, int color, int i, int j, int image_size, double factor) {

        int k0 = image_size * i + j;
        int kx = k0 + image_size;
        int sx = 1;
        if(i == image_size - 1) {
            kx -= 2 * image_size;
            sx = -1;
        }

        int ky = k0 + 1;
        int sy = 1;
        if(j == image_size - 1) {
            ky -= 2;
            sy = -1;
        }

        double n0 = Math.abs(image_iterations[k0]);
        double nx = Math.abs(image_iterations[kx]);
        double ny = Math.abs(image_iterations[ky]);
        double zx = sx * (nx - n0);
        double zy = sy * (ny - n0);

        double zz = 1 / factor;

        double z = Math.sqrt(zx * zx + zy * zy + zz * zz);

        zz /= z;

        double coef = zz;

        int r = color & 0xFF0000;
        int g = color & 0x00FF00;
        int b = color & 0x0000FF;

        int fc_red = new_color & 0xFF0000;
        int fc_green = new_color & 0x00FF00;
        int fc_blue = new_color & 0x0000FF;

        double inv_coef;

        inv_coef = coef;
        coef = 1 - coef;

        final int ret = 0xff000000 | (int)(r * coef + fc_red * inv_coef + 0.5) & 0xFF0000 | (int)(g * coef + fc_green * inv_coef + 0.5) & 0x00FF00 | (int)(b * coef + fc_blue * inv_coef + 0.5);

        return ret;

    }

    private int pseudoDistanceEstimation(double[] image_iterations, int color, int i, int j, int image_size) {

        int k0 = image_size * i + j;
        int kx = k0 + image_size;
        int sx = 1;
        if(i == image_size - 1) {
            kx -= 2 * image_size;
            sx = -1;
        }

        int ky = k0 + 1;
        int sy = 1;
        if(j == image_size - 1) {
            ky -= 2;
            sy = -1;
        }

        double n0 = Math.abs(image_iterations[k0]);
        double nx = Math.abs(image_iterations[kx]);
        double ny = Math.abs(image_iterations[ky]);
        double zx = sx * (nx - n0);
        double zy = sy * (ny - n0);

        double zz = 1 / fake_de_factor;

        double z = Math.sqrt(zx * zx + zy * zy + zz * zz);

        zz /= z;

        double coef = zz;

        int r = color & 0xFF0000;
        int g = color & 0x00FF00;
        int b = color & 0x0000FF;

        int fc_red = dem_color & 0xFF0000;
        int fc_green = dem_color & 0x00FF00;
        int fc_blue = dem_color & 0x0000FF;

        double inv_coef;

        if(inverse_fake_dem) {
            if(n0 == max_iterations) {
                return color;
            }

            inv_coef = coef;
            coef = 1 - coef;
        }
        else {
            inv_coef = 1 - coef;
        }

        final int ret = 0xff000000 | (int)(r * coef + fc_red * inv_coef + 0.5) & 0xFF0000 | (int)(g * coef + fc_green * inv_coef + 0.5) & 0x00FF00 | (int)(b * coef + fc_blue * inv_coef + 0.5);

        return ret;

    }

    private int pseudoDistanceEstimation3D(float[][][] image_iterations, int color, int i, int j, int image_size) {

        int i1 = i + 1;
        int sx = 1;
        if(i == image_size - 1) {
            i1 -= 2;
            sx = -1;
        }

        int j1 = j + 1;
        int sy = 1;
        if(j == image_size - 1) {
            j1 -= 2;
            sy = -1;
        }

        double n0 = Math.abs(image_iterations[i][j][1]);
        double nx = Math.abs(image_iterations[i1][j][1]);
        double ny = Math.abs(image_iterations[i][j1][1]);
        double zx = sx * (nx - n0);
        double zy = sy * (ny - n0);

        double zz = 1 / fake_de_factor;

        double z = Math.sqrt(zx * zx + zy * zy + zz * zz);
        zz /= z;
        double coef = zz;

        int r = color & 0xFF0000;
        int g = color & 0x00FF00;
        int b = color & 0x0000FF;

        int fc_red = dem_color & 0xFF0000;
        int fc_green = dem_color & 0x00FF00;
        int fc_blue = dem_color & 0x0000FF;

        double inv_coef;

        if(inverse_fake_dem) {
            if(n0 == max_iterations) {
                return color;
            }

            inv_coef = coef;
            coef = 1 - coef;
        }
        else {
            inv_coef = 1 - coef;
        }

        final int ret = 0xff000000 | (int)(r * coef + fc_red * inv_coef + 0.5) & 0xFF0000 | (int)(g * coef + fc_green * inv_coef + 0.5) & 0x00FF00 | (int)(b * coef + fc_blue * inv_coef + 0.5);

        return ret;

    }

    private void applyPostProcessing(int image_size) {

        double gradCorr, sizeCorr = 0, lightAngleRadians, lightx = 0, lighty = 0;

        if(bump_map) {
            gradCorr = Math.pow(2, (bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = image_size / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bumpMappingDepth) * 0.16);
            lightAngleRadians = Math.toRadians(lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }

        double gradx, grady, dotp, gradAbs, cosAngle, smoothGrad;
        int modified;

        for(int y = FROMy; y < TOy; y++) {
            for(int x = FROMx; x < TOx; x++) {
                int index = y * image_size + x;

                modified = rgbs[index];
                
                if(rainbow_palette) {
                    int original_color = modified;
                    modified = paletteRainbow(image_iterations, y, x, image_size);
                    modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, rp_noise_reducing_factor);
                }

                if(bump_map) {
                    gradx = getGradientX(image_iterations, index, image_size);
                    grady = getGradientY(image_iterations, index, image_size);

                    dotp = gradx * lightx + grady * lighty;
                    if(dotp != 0.0) {

                        gradAbs = Math.sqrt(gradx * gradx + grady * grady);
                        cosAngle = dotp / gradAbs;
                        smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;
                        //final double smoothGrad = Math.atan(gradAbs * sizeCorr);
                        int original_color = modified;
                        modified = changeBrightnessOfColor(modified, cosAngle * smoothGrad);
                        modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, bm_noise_reducing_factor);
                    }
                }

                if(fake_de) {
                    modified = pseudoDistanceEstimation(image_iterations, modified, y, x, image_size);
                }

                rgbs[index] = modified;
            }
        }
    }

    private void applyPostProcessingFastJulia(int image_size) {

        double gradCorr, sizeCorr = 0, lightAngleRadians, lightx = 0, lighty = 0;

        if(bump_map) {
            gradCorr = Math.pow(2, (bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = image_size / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bumpMappingDepth) * 0.16);
            lightAngleRadians = Math.toRadians(lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }

        double gradx, grady, dotp, gradAbs, cosAngle, smoothGrad;
        int modified;

        for(int y = FROMy; y < TOy; y++) {
            for(int x = FROMx; x < TOx; x++) {
                int index = y * image_size + x;

                modified = rgbs[index];
                
                if(rainbow_palette) {
                    int original_color = modified;
                    modified = paletteRainbow(image_iterations_fast_julia, y, x, image_size);
                    modified = postProcessingSmoothing(modified, image_iterations_fast_julia, original_color, y, x, image_size, rp_noise_reducing_factor);
                }

                if(bump_map) {
                    gradx = getGradientX(image_iterations_fast_julia, index, image_size);
                    grady = getGradientY(image_iterations_fast_julia, index, image_size);

                    dotp = gradx * lightx + grady * lighty;
                    if(dotp != 0.0) {

                        gradAbs = Math.sqrt(gradx * gradx + grady * grady);
                        cosAngle = dotp / gradAbs;
                        smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;
                        //final double smoothGrad = Math.atan(gradAbs * sizeCorr);
                        int original_color = modified;
                        modified = changeBrightnessOfColor(modified, cosAngle * smoothGrad);
                        modified = postProcessingSmoothing(modified, image_iterations_fast_julia, original_color, y, x, image_size, bm_noise_reducing_factor);
                    }
                }

                if(fake_de) {
                    modified = pseudoDistanceEstimation(image_iterations_fast_julia, modified, y, x, image_size);
                }

                rgbs[index] = modified;
            }
        }
    }

    private void drawFastJuliaBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double start_val;

        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations_fast_julia[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = getColor(start_val);

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations_fast_julia[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = getColor(image_iterations_fast_julia[nextPix]);
                            }

                            if(nextColor == startColor) {
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
                                            image_iterations_fast_julia[floodPix] = start_val;
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

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessingFastJulia(image_size);
        }
    }

    private void drawFastJulia(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaBruteForce(image_size);
        }
        else {
            drawFastJuliaBoundaryTracing(image_size);
        }

    }

    private void drawFastJuliaPolar(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaPolarBruteForce(image_size);
        }
        else {
            drawFastJuliaPolarBoundaryTracing(image_size);
        }

    }

    private void drawFastJuliaPolarBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        int x, y, loc;

        int condition = image_size * image_size;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations_fast_julia[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            rgbs[loc] = getColor(image_iterations_fast_julia[loc]);

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessingFastJulia(image_size);
        }

    }

    private void drawFastJuliaPolarBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double start_val;

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations_fast_julia[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    startColor = rgbs[pix] = getColor(start_val);

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations_fast_julia[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                nextColor = rgbs[nextPix] = getColor(image_iterations_fast_julia[nextPix]);
                            }

                            if(nextColor == startColor) {
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
                                            image_iterations_fast_julia[floodPix] = start_val;
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

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessingFastJulia(image_size);
        }
    }

    private void drawFastJuliaAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int x, y, loc;
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        int condition = image_size * image_size;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

            image_iterations_fast_julia[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
            color = getColor(image_iterations_fast_julia[loc]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {
                temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                color = getColor(temp_result);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessingFastJulia(image_size);
        }
    }

    private void drawFastJuliaAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int color;

        double temp_result;

        int red, green, blue;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};
        double nextX, nextY;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

        double start_val;

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(y = FROMy; y < TOy; y++) {
            temp_y0 = temp_ycenter_size - y * temp_size_image_size_y;
            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {
                    temp_x0 = temp_xcenter_size + x * temp_size_image_size_x;
                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations_fast_julia[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    color = getColor(start_val);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < supersampling_num; i++) {
                        temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            nextX = temp_xcenter_size + next_ix * temp_size_image_size_x;
                            nextY = temp_ycenter_size - next_iy * temp_size_image_size_y;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations_fast_julia[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                color = getColor(image_iterations_fast_julia[nextPix]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < supersampling_num; i++) {
                                    temp_result = fractal.calculateJulia(new Complex(nextX + antialiasing_x[i], nextY + antialiasing_y[i]));
                                    color = getColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                            }

                            if(nextColor == startColor) {
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
                                            image_iterations_fast_julia[floodPix] = start_val;
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

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessingFastJulia(image_size);
        }
    }

    private void drawFastJuliaAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaAntialiasedBruteForce(image_size);
        }
        else {
            drawFastJuliaAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawFastJuliaPolarAntialiased(int image_size) {

        if(!boundary_tracing) {
            drawFastJuliaPolarAntialiasedBruteForce(image_size);
        }
        else {
            drawFastJuliaPolarAntialiasedBoundaryTracing(image_size);
        }

    }

    private void drawFastJuliaPolarAntialiasedBruteForce(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        int x, y, loc;

        int condition = image_size * image_size;

        int color;

        int red, green, blue;

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        do {

            loc = normal_drawing_algorithm_pixel.getAndIncrement();

            if(loc >= condition) {
                break;
            }

            x = loc % image_size;
            y = loc / image_size;

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x * mulx + start);

            image_iterations_fast_julia[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
            color = getColor(image_iterations_fast_julia[loc]);

            red = (color >> 16) & 0xff;
            green = (color >> 8) & 0xff;
            blue = color & 0xff;

            //Supersampling
            for(int i = 0; i < supersampling_num; i++) {

                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                r2 = r * antialiasing_x[i];

                temp_result = fractal.calculateJulia(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                color = getColor(temp_result);

                red += (color >> 16) & 0xff;
                green += (color >> 8) & 0xff;
                blue += color & 0xff;
            }

            rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

        } while(true);

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessingFastJulia(image_size);
        }
    }

    private void drawFastJuliaPolarAntialiasedBoundaryTracing(int image_size) {

        double size = fractal.getSize();

        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        double f, sf, cf, r, r2, f2, sf2, cf2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size / 2.0;

        double temp_result;

        final int dirRight = 0, dirUP = 3, maskDir = 3, culcColor = 0;// borderColor = 1;

        double temp_x0, temp_y0;

        int pix, y, x, curDir, curPix, startPix, startColor, nextColor, dir, Dir, nextPix, floodPix, floodColor;
        int delPix[] = {1, image_size, -1, -image_size};

        double nextX, nextY, start_val;

        double sf3, cf3, r3;

        int ix, iy, next_ix, next_iy, temp_ix, temp_iy, flood_ix;
        int intX[] = {1, 0, -1, 0};
        int intY[] = {0, 1, 0, -1};

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        for(y = FROMy; y < TOy; y++) {

            f = y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            for(x = FROMx, pix = y * image_size + x; x < TOx; x++, pix++) {

                if(rgbs[pix] == culcColor) {

                    r = Math.exp(x * mulx + start);

                    temp_x0 = xcenter + r * cf;
                    temp_y0 = ycenter + r * sf;

                    curPix = startPix = pix;
                    curDir = dirRight;
                    ix = x;
                    iy = y;

                    start_val = image_iterations_fast_julia[pix] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                    color = getColor(start_val);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for(int i = 0; i < supersampling_num; i++) {

                        sf3 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf3 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r3 = r * antialiasing_x[i];

                        temp_x0 = xcenter + r3 * cf3;
                        temp_y0 = ycenter + r3 * sf3;

                        temp_result = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                        color = getColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    startColor = rgbs[pix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                    while(iy - 1 >= FROMy && rgbs[startPix - image_size] == startColor) {   // looking for boundary
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

                            f2 = next_iy * muly;
                            sf2 = Math.sin(f2);
                            cf2 = Math.cos(f2);

                            r2 = Math.exp(next_ix * mulx + start);

                            nextX = xcenter + r2 * cf2;
                            nextY = ycenter + r2 * sf2;

                            if(!(next_ix >= FROMx && next_ix < TOx && next_iy >= FROMy && next_iy < TOy)) {
                                continue;
                            }

                            if((nextColor = rgbs[nextPix]) == culcColor) {
                                image_iterations_fast_julia[nextPix] = fractal.calculateJulia(new Complex(nextX, nextY));
                                color = getColor(image_iterations_fast_julia[nextPix]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for(int i = 0; i < supersampling_num; i++) {

                                    sf3 = sf2 * antialiasing_y_cos[i] + cf2 * antialiasing_y_sin[i];
                                    cf3 = cf2 * antialiasing_y_cos[i] - sf2 * antialiasing_y_sin[i];

                                    r3 = r2 * antialiasing_x[i];

                                    nextX = xcenter + r3 * cf3;
                                    nextY = ycenter + r3 * sf3;

                                    temp_result = fractal.calculateJulia(new Complex(nextX, nextY));
                                    color = getColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                nextColor = rgbs[nextPix] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
                            }

                            if(nextColor == startColor) {
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
                                            image_iterations_fast_julia[floodPix] = start_val;
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

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessingFastJulia(image_size);
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

        for(int y = FROMy; y < TOy; y++) {
            for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                rgbs[loc] = getColor(image_iterations[loc]);
            }
        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

        try {
            if(color_cycling_filters_sync.await() == 0) {
                applyFilters();

                ptr.setWholeImageDone(true);

                ptr.getMainPanel().repaint();

                ptr.updatePalettePreview(color_cycling_location);
            }
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        try {
            Thread.sleep(color_cycling_speed + 35);
        }
        catch(InterruptedException ex) {
        }

        try {
            color_cycling_restart_sync.await();
        }
        catch(InterruptedException ex) {

        }
        catch(BrokenBarrierException ex) {

        }

        colorCycling();

    }

    private void rotate3DModel() {

        int image_size = image.getHeight();

        draw3D(image_size, false);

        if(drawing_done != 0) {
            update(drawing_done);
        }

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void applyPaletteAndFilter3DModel() {

        int image_size = image.getHeight();

        draw3D(image_size, true);

        if(drawing_done != 0) {
            update(drawing_done);
        }

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((detail * detail) + (detail * detail / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void applyPaletteAndFilter() {

        int image_size = image.getHeight();

        changePalette(image_size);

        if(drawing_done != 0) {
            update(drawing_done);
        }

        if(image_filters_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void changePalette(int image_size) {

        int pixel_percent = image_size * image_size / 100;

        double temp_result;

        if(ptr.getDomainColoring()) {
            for(int y = FROMy; y < TOy; y++) {
                for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                    rgbs[loc] = (int)image_iterations[loc];

                    drawing_done++;
                }

                if(drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }

            }
        }
        else if(ptr.getJuliaMap()) {
            for(int y = FROMy; y < TOy; y++) {
                for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                    rgbs[loc] = getColor(image_iterations[loc]);

                    drawing_done++;
                }

                if(drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
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
                    slice_FROMx = FROMx + j * (thread_size_width) / thread_slices;
                    slice_TOx = FROMx + (j + 1) * (thread_size_width) / thread_slices;

                    double temp = (slice_TOy - slice_FROMy + 1) * 0.5;

                    for(y = slice_FROMy, whole_area = true, step = 0, total_drawing = 0; step < temp; step++, whole_area = true) {

                        x = slice_FROMx + step;

                        loc = y * image_size + x;
                        temp_starting_pixel_cicle = image_iterations[loc];

                        rgbs[loc] = temp_starting_pixel_color = getColor(image_iterations[loc]);

                        drawing_done++;
                        total_drawing++;

                        for(x++, loc = y * image_size + x; x < slice_TOx - step; x++, loc++) {
                            temp_result = image_iterations[loc];
                            if(temp_result == temp_starting_pixel_cicle) {
                                rgbs[loc] = temp_starting_pixel_color;
                            }
                            else {
                                rgbs[loc] = getColor(temp_result);
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
                                rgbs[loc] = getColor(temp_result);
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
                                rgbs[loc] = getColor(temp_result);
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
                                rgbs[loc] = getColor(temp_result);
                                whole_area = false;
                            }

                            drawing_done++;
                            total_drawing++;
                        }
                        y++;
                        x++;

                        if(drawing_done / pixel_percent >= 1) {
                            update(drawing_done);
                            drawing_done = 0;
                        }

                        if(whole_area) {
                            int temp6 = step + 1;
                            int temp1 = slice_TOx - temp6;
                            int temp2 = slice_TOy - temp6;

                            for(int k = y; k < temp2; k++) {
                                Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, temp_starting_pixel_color);
                            }

                            update((slice_TOx - slice_FROMx) * (slice_TOy - slice_FROMy) - total_drawing);
                            break;
                        }
                    }
                }
            }

        }

        if((bump_map || fake_de || rainbow_palette) && !ptr.getDomainColoring()) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void drawJuliaMap() {

        int image_size = image.getHeight();

        if(filters[MainWindow.ANTIALIASING]) {
            juliaMapAntialiased(image_size);
        }
        else {
            juliaMap(image_size);
        }

        if(drawing_done != 0) {
            update(drawing_done);
        }

        if(image_filters_sync.incrementAndGet() == ptr.getJuliaMapSlices()) {

            applyFilters();

            ptr.updateValues("Julia Map mode");
            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getJuliaMapSlices() + "</html>");
        }

    }

    private void drawJuliaMapPolar() {

        int image_size = image.getHeight();

        if(filters[MainWindow.ANTIALIASING]) {
            juliaMapPolarAntialiased(image_size);
        }
        else {
            juliaMapPolar(image_size);
        }

        if(drawing_done != 0) {
            update(drawing_done);
        }

        if(image_filters_sync.incrementAndGet() == ptr.getJuliaMapSlices()) {

            applyFilters();

            ptr.updateValues("Julia Map mode");
            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            ptr.getProgressBar().setValue((image_size * image_size) + (image_size * image_size / 100));
            ptr.getProgressBar().setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getJuliaMapSlices() + "</html>");
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

        for(int y = FROMy; y < TOy; y++, temp_y0 -= temp_size_image_size_y) {
            for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size_x, loc++) {

                image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                rgbs[loc] = getColor(image_iterations[loc]);

                drawing_done++;

            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

            temp_x0 = temp_xcenter_size;

        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

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

        start = center - mulx * (TOx - FROMx) / 2.0;

        for(int y = FROMy, y1 = 0; y < TOy; y++, y1++) {
            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);
            for(int x = FROMx, x1 = 0, loc = y * image_size + x; x < TOx; x++, loc++, x1++) {

                r = Math.exp(x1 * mulx + start);

                image_iterations[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getColor(image_iterations[loc]);

                drawing_done++;

            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

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

        double antialiasing_x[] = {-x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, -x_antialiasing_size,
            -x_antialiasing_size, x_antialiasing_size, 0, 0,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size_x2, 0, 0, x_antialiasing_size_x2, x_antialiasing_size_x2, x_antialiasing_size_x2,
            -x_antialiasing_size_x2, -x_antialiasing_size_x2, -x_antialiasing_size, -x_antialiasing_size, x_antialiasing_size, x_antialiasing_size, x_antialiasing_size_x2, x_antialiasing_size_x2};
        double antialiasing_y[] = {-y_antialiasing_size, -y_antialiasing_size, y_antialiasing_size, y_antialiasing_size,
            0, 0, -y_antialiasing_size, y_antialiasing_size,
            -y_antialiasing_size_x2, 0, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, 0, y_antialiasing_size_x2,
            -y_antialiasing_size, y_antialiasing_size, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size_x2, y_antialiasing_size_x2, -y_antialiasing_size, y_antialiasing_size};

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(int y = FROMy; y < TOy; y++, temp_y0 -= temp_size_image_size_y) {
            for(int x = FROMx, loc = y * image_size + x; x < TOx; x++, temp_x0 += temp_size_image_size_x, loc++) {

                image_iterations[loc] = fractal.calculateJulia(new Complex(temp_x0, temp_y0));
                color = getColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {
                    temp_result = fractal.calculateJulia(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                drawing_done++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

            temp_x0 = temp_xcenter_size;

        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

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

        start = center - mulx * (TOx - FROMx) / 2.0;

        double temp_result;

        int color;

        int red, green, blue;

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

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(int y = FROMy, y1 = 0; y < TOy; y++, y1++) {
            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);
            for(int x = FROMx, x1 = 0, loc = y * image_size + x; x < TOx; x++, loc++, x1++) {
                r = Math.exp(x1 * mulx + start);

                image_iterations[loc] = fractal.calculateJulia(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {
                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = fractal.calculateJulia(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                drawing_done++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        if(bump_map || fake_de || rainbow_palette) {
            try {
                post_processing_sync.await();
            }
            catch(InterruptedException ex) {

            }
            catch(BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size);
        }

    }

    private void shadeColorBasedOnHeight() {

        double min = Double.MAX_VALUE, max = -Double.MIN_VALUE;

        for(int x = 0; x < detail; x++) {
            for(int y = 0; y < detail; y++) {
                if(vert[x][y][1] < min) {
                    min = vert[x][y][1];
                }

                if(vert[x][y][1] > max) {
                    max = vert[x][y][1];
                }
            }
        }

        max -= min;

        for(int x = 0; x < detail; x++) {
            for(int y = 0; y < detail; y++) {
                int r = ((int)vert[x][y][0] >> 16) & 0xff;
                int g = ((int)vert[x][y][0] >> 8) & 0xff;
                int b = (int)vert[x][y][0] & 0xff;

                double coef = 0;

                switch (shade_algorithm) {
                    case 0: //lerp
                        coef = ((vert[x][y][1] - min) / max - 0.5) * 2;
                        break;
                    case 1://cos lerp
                        coef = -Math.cos((vert[x][y][1] - min) / max * Math.PI);
                        break;
                    case 2:
                        double lim = 0.1;
                        if((vert[x][y][1] - min) / max <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / max * (1 / lim));
                        }
                        else if((vert[x][y][1] - min) / max >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / max) * (1 / lim);
                        }
                        else {
                            coef = 0;
                        }
                        break;
                    case 3:
                        lim = 0.2;
                        if((vert[x][y][1] - min) / max <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / max * (1 / lim));
                        }
                        else if((vert[x][y][1] - min) / max >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / max) * (1 / lim);
                        }
                        else {
                            coef = 0;
                        }
                        break;
                    case 4:
                        lim = 0.3;
                        if((vert[x][y][1] - min) / max <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / max * (1 / lim));
                        }
                        else if((vert[x][y][1] - min) / max >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / max) * (1 / lim);
                        }
                        else {
                            coef = 0;
                        }
                        break;
                    case 5:
                        lim = 0.4;
                        if((vert[x][y][1] - min) / max <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / max * (1 / lim));
                        }
                        else if((vert[x][y][1] - min) / max >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / max) * (1 / lim);
                        }
                        else {
                            coef = 0;
                        }
                        break;
                }

                if(shade_invert) {
                    coef *= -1;
                }

                if(shade_choice == 2) { //-1 to 0 only
                    if(coef > 0) {
                        coef = 0;
                    }
                }
                else if(shade_choice == 1) { //0 to 1 only
                    if(coef < 0) {
                        coef = 0;
                    }
                }

                int col = 0;
                int col2 = 255 - col;

                if(coef < 0) {
                    r = (int)(col * Math.abs(coef) + r * (1 - Math.abs(coef)));
                    g = (int)(col * Math.abs(coef) + g * (1 - Math.abs(coef)));
                    b = (int)(col * Math.abs(coef) + b * (1 - Math.abs(coef)));
                }
                else {
                    r = (int)(col2 * coef + r * (1 - coef));
                    g = (int)(col2 * coef + g * (1 - coef));
                    b = (int)(col2 * coef + b * (1 - coef));
                }

                vert[x][y][0] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
    }

    private void update(int new_percent) {

        ptr.getProgressBar().setValue(ptr.getProgressBar().getValue() + new_percent);

    }

    private double getGradientX(double[] image_iterations, int index, int size) {

        int x = index % size;
        double it = Math.abs(image_iterations[index]);

        if(x == 0) {
            return (Math.abs(image_iterations[index + 1]) - it) * 2;
        }
        else if(x == size - 1) {
            return (it - Math.abs(image_iterations[index - 1])) * 2;
        }
        else {
            double diffL = it - Math.abs(image_iterations[index - 1]);
            double diffR = it - Math.abs(image_iterations[index + 1]);
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }

    }

    private double getGradientX3D(float[][][] image_iterations, int x, int y, int size) {

        double it = Math.abs(image_iterations[x][y][1]);

        if(x == 0) {
            return (Math.abs(image_iterations[x + 1][y][1]) - it) * 2;
        }
        else if(x == size - 1) {
            return (it - Math.abs(image_iterations[x - 1][y][1])) * 2;
        }
        else {
            double diffL = it - Math.abs(image_iterations[x - 1][y][1]);
            double diffR = it - Math.abs(image_iterations[x + 1][y][1]);
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }

    }

    private double getGradientY(double[] image_iterations, int index, int size) {

        int y = index / size;
        double it = Math.abs(image_iterations[index]);

        if(y == 0) {
            return (it - Math.abs(image_iterations[index + size])) * 2;
        }
        else if(y == size - 1) {
            return (Math.abs(image_iterations[index - size]) - it) * 2;
        }
        else {
            double diffU = it - Math.abs(image_iterations[index - size]);
            double diffD = it - Math.abs(image_iterations[index + size]);
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }

    }

    private double getGradientY3D(float[][][] image_iterations, int x, int y, int size) {

        double it = Math.abs(image_iterations[x][y][1]);

        if(y == 0) {
            return (it - Math.abs(image_iterations[x][y + 1][1])) * 2;
        }
        else if(y == size - 1) {
            return (Math.abs(image_iterations[x][y - 1][1]) - it) * 2;
        }
        else {
            double diffU = it - Math.abs(image_iterations[x][y - 1][1]);
            double diffD = it - Math.abs(image_iterations[x][y + 1][1]);
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }

    }

    private int changeBrightnessOfColor(int rgb, double delta) {
        int new_color = 0;
        
        if(delta > 0) {
            rgb ^= 0xFFFFFF;
            int r = rgb & 0xFF0000;
            int g = rgb & 0x00FF00;
            int b = rgb & 0x0000FF;
            double mul = (1.5 / (delta + 1.5));
            int ret = (int)(r * mul + 0.5) & 0xFF0000 | (int)(g * mul + 0.5) & 0x00FF00 | (int)(b * mul + 0.5);

            new_color = 0xff000000 | (ret ^ 0xFFFFFF);
        }
        else {
            int r = rgb & 0xFF0000;
            int g = rgb & 0x00FF00;
            int b = rgb & 0x0000FF;
            double mul = (1.5 / (-delta + 1.5));
            //double mul = (Math.atan(-Math.abs(delta))*0.63662+1);
            //double mul = Math.pow(2, -Math.abs(delta));
            new_color = 0xff000000 | (int)(r * mul + 0.5) & 0xFF0000 | (int)(g * mul + 0.5) & 0x00FF00 | (int)(b * mul + 0.5);
        }
        return new_color;  
    }

    public void terminateColorCycling() {

        color_cycling = false;

    }

    public int getColorCyclingLocation() {

        return color_cycling_location;

    }

    private void applyFilters() {

        ImageFilters.filter(image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order);

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
                return 40 * Math.sin(x);
            case 5:
                return 40 * Math.cos(x);
            case 6:
                return 150 / (1 + Math.exp(-3 * x + 3));

        }

        return 0;
    }

    private void applyHeightScaling() {

        double min = Double.MAX_VALUE, max = -Double.MIN_VALUE;

        for(int x = 0; x < detail; x++) {
            for(int y = 0; y < detail; y++) {
                if(vert[x][y][1] < min) {
                    min = vert[x][y][1];
                }

                if(vert[x][y][1] > max) {
                    max = vert[x][y][1];
                }
            }
        }

        min = min + (min_to_max_scaling / 100.0) * 0.1 * max;
        min = min > max ? max : min;

        max -= min;

        for(int x = 0; x < detail; x++) {
            for(int y = 0; y < detail; y++) {
                vert[x][y][1] -= min;
                vert[x][y][1] = vert[x][y][1] < 0 ? 0 : vert[x][y][1];
                vert[x][y][1] *= (200 - max_scaling) / max;

                vert[x][y][1] = (float)(d3_height_scale * calculateHeight(vert[x][y][1]) - 100);
            }
        }
    }

    private void gaussianHeightScalingInit() {
        temp_array = new float[detail][detail];

        for(int x = 0; x < detail; x++) {
            for(int y = 0; y < detail; y++) {
                temp_array[x][y] = vert[x][y][1];
            }
        }

        createGaussianKernel(gaussian_kernel_size * 2 + 3, gaussian_weight);
    }

    private void gaussianHeightScalingEnd() {
        temp_array = null;
    }

    private void gaussianHeightScaling() {

        int kernel_size = (int)(Math.sqrt(gaussian_kernel.length));
        int kernel_size2 = kernel_size / 2;

        int startx = FROMx == 0 ? kernel_size2 : FROMx;
        int starty = FROMy == 0 ? kernel_size2 : FROMy;
        int endx = TOx == detail ? detail - kernel_size2 : TOx;
        int endy = TOy == detail ? detail - kernel_size2 : TOy;

        for(int x = startx; x < endx; x++) {
            for(int y = starty; y < endy; y++) {
                double sum = 0;

                for(int k = x - kernel_size2, p = 0; p < kernel_size; k++, p++) {
                    for(int l = y - kernel_size2, t = 0; t < kernel_size; l++, t++) {
                        sum += temp_array[k][l] * gaussian_kernel[p * kernel_size + t];
                    }
                }

                vert[x][y][1] = (float)sum;
            }
        }
    }

    private void applyPostProcessing3D(int image_size) {

        double gradCorr, sizeCorr = 0, lightAngleRadians, lightx = 0, lighty = 0;

        if(bump_map) {
            gradCorr = Math.pow(2, (bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = image_size / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bumpMappingDepth) * 0.16);
            lightAngleRadians = Math.toRadians(lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }

        double gradx, grady, dotp, gradAbs, cosAngle, smoothGrad;
        int modified;

        for(int y = FROMy; y < TOy; y++) {
            for(int x = FROMx; x < TOx; x++) {

                modified = (int)vert[x][y][0];
                
                if(rainbow_palette) {
                    int original_color = modified;
                    modified = paletteRainbow3D(vert, x, y, image_size);
                    modified = postProcessingSmoothing3D(modified, vert, original_color, x, y, image_size, rp_noise_reducing_factor);
                }

                if(bump_map) {
                    gradx = getGradientX3D(vert, x, y, image_size);
                    grady = getGradientY3D(vert, x, y, image_size);

                    dotp = gradx * lightx + grady * lighty;
                    if(dotp != 0.0) {

                        gradAbs = Math.sqrt(gradx * gradx + grady * grady);
                        cosAngle = dotp / gradAbs;
                        smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;
                        //final double smoothGrad = Math.atan(gradAbs * sizeCorr);
                        int original_color = modified;
                        modified = changeBrightnessOfColor(modified, cosAngle * smoothGrad);
                        modified = postProcessingSmoothing3D(modified, vert, original_color, x, y, image_size, bm_noise_reducing_factor);
                    }
                }

                if(fake_de) {
                    modified = pseudoDistanceEstimation3D(vert, modified, x, y, image_size);
                }

                vert[x][y][0] = modified;
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
        for(int filterY = -kernelRadius; filterY <= kernelRadius; filterY++) {
            for(int filterX = -kernelRadius; filterX <= kernelRadius; filterX++) {
                distance = ((filterX * filterX) + (filterY * filterY)) / (2 * (weight * weight));
                temp = gaussian_kernel[(filterY + kernelRadius) * length + filterX + kernelRadius] = (float)(calculatedEuler * Math.exp(-distance));
                sumTotal += temp;
            }
        }

        for(int y = 0; y < length; y++) {
            for(int x = 0; x < length; x++) {
                gaussian_kernel[y * length + x] = (float)(gaussian_kernel[y * length + x] * (1.0 / sumTotal));
            }
        }

    }

    private void calculate3DVectors(int n1, double dx, double w2) {

        double mod;
        double ct = Math.cos(fiX), cf = Math.cos(fiY), st = Math.sin(fiX), sf = Math.sin(fiY);
        double m00 = scale * cf, m02 = scale * sf, m10 = scale * st * sf, m11 = scale * ct, m12 = -scale * st * cf;
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        double norm_0_0, norm_0_1, norm_0_2, norm_1_0, norm_1_1, norm_1_2;

        for(int x = FROMx; x < TOx; x++) {

            double c1 = dx * x - w2;

            for(int y = FROMy; y < TOy; y++) {
                if(x < n1 && y < n1) {

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

                    Norm1z[x][y][0] = (float)(m20 * norm_0_0 + m21 * norm_0_1 + m22 * norm_0_2);
                    Norm1z[x][y][1] = (float)(m20 * norm_1_0 + m21 * norm_1_1 + m22 * norm_1_2);
                }

                double c2 = dx * y - w2;
                vert1[x][y][0] = (float)(m00 * c1 + m02 * c2);
                vert1[x][y][1] = (float)(m10 * c1 + m11 * vert[x][y][1] + m12 * c2);
            }
        }
    }

    private void paint3D(int n1, int w2) {
        int[] xPol = new int[3];
        int[] yPol = new int[3];

        Graphics2D g = image.createGraphics();

        int ib = 0, ie = n1, sti = 1, jb = 0, je = n1, stj = 1;

        if(m20 < 0) {
            ib = n1;
            ie = -1;
            sti = -1;
        }

        if(m22 < 0) {
            jb = n1;
            je = -1;
            stj = -1;
        }

        double second_color = 1 - color_3d_blending;

        double red, green, blue;

        for(int i = ib; i != ie; i += sti) {
            for(int j = jb; j != je; j += stj) {

                red = ((((int)vert[i][j][0]) >> 16) & 0xff);
                green = ((((int)vert[i][j][0]) >> 8) & 0xff);
                blue = (((int)vert[i][j][0]) & 0xff);

                if(Norm1z[i][j][0] > 0) {
                    xPol[0] = w2 + (int)vert1[i][j][0];
                    xPol[1] = w2 + (int)vert1[i + 1][j][0];
                    xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                    yPol[0] = w2 - (int)vert1[i][j][1];
                    yPol[1] = w2 - (int)vert1[i + 1][j][1];
                    yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                    g.setColor(new Color((int)(red * color_3d_blending + ((colors_3d[(int)Norm1z[i][j][0]] >> 16) & 0xff) * second_color + 0.5), (int)(green * color_3d_blending + ((colors_3d[(int)Norm1z[i][j][0]] >> 8) & 0xff) * second_color + 0.5), (int)(blue * color_3d_blending + (colors_3d[(int)Norm1z[i][j][0]] & 0xff) * second_color + 0.5)));

                    if(filters[MainWindow.ANTIALIASING]) {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                        g.fillPolygon(xPol, yPol, 3);
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.fillPolygon(xPol, yPol, 3);
                    }
                    else {
                        g.fillPolygon(xPol, yPol, 3);
                    }

                }

                if(Norm1z[i][j][1] > 0) {
                    xPol[0] = w2 + (int)vert1[i][j][0];
                    xPol[1] = w2 + (int)vert1[i][j + 1][0];
                    xPol[2] = w2 + (int)vert1[i + 1][j + 1][0];
                    yPol[0] = w2 - (int)vert1[i][j][1];
                    yPol[1] = w2 - (int)vert1[i][j + 1][1];
                    yPol[2] = w2 - (int)vert1[i + 1][j + 1][1];

                    g.setColor(new Color((int)(red * color_3d_blending + ((colors_3d[(int)Norm1z[i][j][1]] >> 16) & 0xff) * second_color + 0.5), (int)(green * color_3d_blending + ((colors_3d[(int)Norm1z[i][j][1]] >> 8) & 0xff) * second_color + 0.5), (int)(blue * color_3d_blending + (colors_3d[(int)Norm1z[i][j][1]] & 0xff) * second_color + 0.5)));

                    if(filters[MainWindow.ANTIALIASING]) {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                        g.fillPolygon(xPol, yPol, 3);
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.fillPolygon(xPol, yPol, 3);
                    }
                    else {
                        g.fillPolygon(xPol, yPol, 3);
                    }

                }
            }
        }

    }

    private int getDomainColor(Complex res) {

        double max_norm = 20.0;

        if(use_palette_domain_coloring) {

            switch (domain_coloring_alg) {
                case 0:
                    float h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    Color col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    float[] temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    float s = (float)Math.abs(Math.sin(Math.log(res.norm()) / Math.log(2) * Math.PI * 2));
                    float b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    Color col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    int cola = 255;
                    int cola2 = 255 - cola;

                    int r = col2.getRed(),
                     g = col2.getGreen(),
                     bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    double sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 1:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = (float)Math.abs(Math.sin(Math.log(res.norm()) / Math.log(2) * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 0;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 2:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = (float)Math.abs(Math.sin(res.norm() * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 255;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 3:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = (float)Math.abs(Math.sin(res.norm() * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 0;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 4:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 255;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    return new Color(r, g, bl).getRGB();
                case 5:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 0;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    return new Color(r, g, bl).getRGB();
                case 6:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = 1.0f - (float)((((int)(Math.abs(Math.log(res.norm()) / Math.log(2)))) * 0.12) % 0.9);
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 255;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 7:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = 1.0f - (float)((((int)(Math.abs(Math.log(res.norm()) / Math.log(2)))) * 0.12) % 0.9);
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 0;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 8:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = 1.0f - (float)((((int)(res.norm())) * 0.12) % 0.9);
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 255;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 9:
                    h = (float)((res.arg() + 2 * Math.PI) / (2 * Math.PI));

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = 1.0f - (float)((((int)(res.norm())) * 0.12) % 0.9);
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 0;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 10:
                    h = (float)(res.norm() / max_norm);

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = (float)Math.abs(Math.sin(Math.log(res.norm()) / Math.log(2) * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 255;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 11:
                    h = (float)(res.norm() / max_norm);

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = (float)Math.abs(Math.sin(Math.log(res.norm()) / Math.log(2) * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 0;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 12:
                    h = (float)(res.norm() / max_norm);

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = (float)Math.abs(Math.sin(res.norm() * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 255;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 13:
                    h = (float)(res.norm() / max_norm);

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    s = (float)Math.abs(Math.sin(res.norm() * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 0;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    sqrts = Math.sqrt(s);

                    r = (int)(cola * (1 - sqrts) + r * sqrts);
                    g = (int)(cola * (1 - sqrts) + g * sqrts);
                    bl = (int)(cola * (1 - sqrts) + bl * sqrts);

                    return new Color(r, g, bl).getRGB();
                case 14:
                    h = (float)(res.norm() / max_norm);

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 255;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    return new Color(r, g, bl).getRGB();
                case 15:
                    h = (float)(res.norm() / max_norm);

                    col = new Color(getColor(OutColorAlgorithm.transformResultToColor(h * palette_color.getPaletteLength())));
                    temp = new float[3];

                    Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), temp);

                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    col2 = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));

                    cola = 0;
                    cola2 = 255 - cola;

                    r = col2.getRed();
                    g = col2.getGreen();
                    bl = col2.getBlue();

                    r = (int)(cola2 * (1 - b) + r * b);
                    g = (int)(cola2 * (1 - b) + g * b);
                    bl = (int)(cola2 * (1 - b) + bl * b);

                    return new Color(r, g, bl).getRGB();

            }

        }
        else {
            switch (domain_coloring_alg) {
                case 0:
                    float h = (float)(res.arg() / Math.PI * 0.5);

                    float s = (float)Math.abs(Math.sin(Math.log(res.norm()) / Math.log(2) * Math.PI * 2));
                    float b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    float b2 = Math.max(1 - s, b);

                    return Color.HSBtoRGB(h, (float)Math.sqrt(s), b2);
                case 1:
                    h = (float)(res.arg() / Math.PI * 0.5);
                    s = (float)Math.abs(Math.sin(Math.log(res.norm()) / Math.log(2) * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    b2 = Math.max(1 - s, b);

                    return Color.HSBtoRGB(h, b2, (float)Math.sqrt(s));
                case 2:
                    h = (float)(res.arg() / Math.PI * 0.5);
                    s = (float)Math.abs(Math.sin(res.norm() * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    b2 = Math.max(1 - s, b);

                    return Color.HSBtoRGB(h, (float)Math.sqrt(s), b2);
                case 3:
                    h = (float)(res.arg() / Math.PI * 0.5);
                    s = (float)Math.abs(Math.sin(res.norm() * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    b2 = Math.max(1 - s, b);

                    return Color.HSBtoRGB(h, b2, (float)Math.sqrt(s));
                case 4:
                    h = (float)(res.arg() / Math.PI * 0.5);

                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    return Color.HSBtoRGB(h, 1.0f, b);
                case 5:
                    h = (float)(res.arg() / Math.PI * 0.5);

                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    return Color.HSBtoRGB(h, b, 1.0f);
                case 6:
                    h = (float)(res.arg() / Math.PI * 0.5);
                    s = 1.0f - (float)((((int)(Math.abs(Math.log(res.norm()) / Math.log(2)))) * 0.12) % 0.9);
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    return Color.HSBtoRGB(h, s, b);
                case 7:
                    h = (float)(res.arg() / Math.PI * 0.5);
                    s = 1.0f - (float)((((int)(Math.abs(Math.log(res.norm()) / Math.log(2)))) * 0.12) % 0.9);
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    return Color.HSBtoRGB(h, b, s);
                case 8:
                    h = (float)(res.arg() / Math.PI * 0.5);
                    s = 1.0f - (float)((((int)(res.norm())) * 0.12) % 0.9);
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    return Color.HSBtoRGB(h, s, b);
                case 9:
                    h = (float)(res.arg() / Math.PI * 0.5);
                    s = 1.0f - (float)((((int)(res.norm())) * 0.12) % 0.9);
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    return Color.HSBtoRGB(h, b, s);
                case 10:
                    h = (float)(res.norm() / max_norm);
                    s = (float)Math.abs(Math.sin(Math.log(res.norm()) / Math.log(2) * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    b2 = Math.max(1 - s, b);

                    return Color.HSBtoRGB(h, (float)Math.sqrt(s), b2);
                case 11:
                    h = (float)(res.norm() / max_norm);
                    s = (float)Math.abs(Math.sin(Math.log(res.norm()) / Math.log(2) * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    b2 = Math.max(1 - s, b);

                    return Color.HSBtoRGB(h, b2, (float)Math.sqrt(s));
                case 12:
                    h = (float)(res.norm() / max_norm);
                    s = (float)Math.abs(Math.sin(res.norm() * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    b2 = Math.max(1 - s, b);

                    return Color.HSBtoRGB(h, (float)Math.sqrt(s), b2);
                case 13:
                    h = (float)(res.norm() / max_norm);
                    s = (float)Math.abs(Math.sin(res.norm() * Math.PI * 2));
                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    b2 = Math.max(1 - s, b);

                    return Color.HSBtoRGB(h, b2, (float)Math.sqrt(s));
                case 14:
                    h = (float)(res.norm() / max_norm);

                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    return Color.HSBtoRGB(h, 1.0f, b);
                case 15:
                    h = (float)(res.norm() / max_norm);

                    b = (float)Math.sqrt(Math.sqrt(Math.abs(Math.sin(res.getIm() * Math.PI * 2) * Math.sin(res.getRe() * Math.PI * 2))));

                    return Color.HSBtoRGB(h, b, 1.0f);

            }
        }

        return 0;
    }

    public static String getDefaultInitialValue() {

        return default_init_val;

    }

    public static void setArrays(int image_size) {

        vert = null;
        vert1 = null;
        Norm1z = null;

        System.gc();

        image_iterations = new double[image_size * image_size];
        image_iterations_fast_julia = new double[MainWindow.FAST_JULIA_IMAGE_SIZE * MainWindow.FAST_JULIA_IMAGE_SIZE];

    }

    public static void set3DArrays(int detail) {

        image_iterations = null;
        image_iterations_fast_julia = null;

        System.gc();

        vert = new float[detail][detail][3];
        vert1 = new float[detail][detail][2];
        Norm1z = new float[detail][detail][2];

    }

    public static void resetAtomics(int num_threads) {

        image_filters_sync = new AtomicInteger(0);
        total_calculated = new AtomicInteger(0);
        post_processing_sync = new CyclicBarrier(num_threads);
        calculate_vectors_sync = new CyclicBarrier(num_threads);
        painting_sync = new AtomicInteger(0);
        height_scaling_sync = new AtomicInteger(0);
        gaussian_scaling_sync = new CyclicBarrier(num_threads);
        normal_drawing_algorithm_pixel = new AtomicInteger(0);
        color_cycling_filters_sync = new CyclicBarrier(num_threads);
        color_cycling_restart_sync = new CyclicBarrier(num_threads);
        shade_color_height_sync = new AtomicInteger(0);

    }

    public static void setQuickDrawTileSize(int size) {

        TILE_SIZE = size;

    }

    public static int getQuickDrawTileSize() {

        return TILE_SIZE;

    }

}
