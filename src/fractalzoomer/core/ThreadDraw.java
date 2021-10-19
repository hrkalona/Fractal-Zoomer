/* 
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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

import fractalzoomer.core.blending.*;
import fractalzoomer.core.domain_coloring.*;
import fractalzoomer.core.interpolation.*;
import fractalzoomer.core.iteration_algorithm.FractalIterationAlgorithm;
import fractalzoomer.core.iteration_algorithm.IterationAlgorithm;
import fractalzoomer.core.iteration_algorithm.JuliaIterationAlgorithm;
import fractalzoomer.fractal_options.iteration_statistics.Equicontinuity;
import fractalzoomer.fractal_options.iteration_statistics.GenericStatistic;
import fractalzoomer.fractal_options.orbit_traps.OrbitTrap;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.barnsley.Barnsley1;
import fractalzoomer.functions.barnsley.Barnsley2;
import fractalzoomer.functions.barnsley.Barnsley3;
import fractalzoomer.functions.formulas.coupled.CoupledMandelbrot;
import fractalzoomer.functions.formulas.coupled.CoupledMandelbrotBurningShip;
import fractalzoomer.functions.formulas.general.mathtype.*;
import fractalzoomer.functions.formulas.general.newtonvariant.*;
import fractalzoomer.functions.formulas.kaliset.*;
import fractalzoomer.functions.formulas.m_like_generalization.*;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.*;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.*;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.Formula40;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.Formula41;
import fractalzoomer.functions.general.*;
import fractalzoomer.functions.lambda.Lambda;
import fractalzoomer.functions.lambda.Lambda2;
import fractalzoomer.functions.lambda.Lambda3;
import fractalzoomer.functions.lambda.LambdaFnFn;
import fractalzoomer.functions.magnet.*;
import fractalzoomer.functions.mandelbrot.*;
import fractalzoomer.functions.math.*;
import fractalzoomer.functions.root_finding_methods.abbasbandy.*;
import fractalzoomer.functions.root_finding_methods.aberth_ehrlich.*;
import fractalzoomer.functions.root_finding_methods.bairstow.*;
import fractalzoomer.functions.root_finding_methods.durand_kerner.*;
import fractalzoomer.functions.root_finding_methods.halley.*;
import fractalzoomer.functions.root_finding_methods.householder.*;
import fractalzoomer.functions.root_finding_methods.householder3.*;
import fractalzoomer.functions.root_finding_methods.jaratt.*;
import fractalzoomer.functions.root_finding_methods.jaratt2.*;
import fractalzoomer.functions.root_finding_methods.laguerre.*;
import fractalzoomer.functions.root_finding_methods.midpoint.*;
import fractalzoomer.functions.root_finding_methods.muller.*;
import fractalzoomer.functions.root_finding_methods.newton.*;
import fractalzoomer.functions.root_finding_methods.newton_hines.*;
import fractalzoomer.functions.root_finding_methods.parhalley.*;
import fractalzoomer.functions.root_finding_methods.schroder.*;
import fractalzoomer.functions.root_finding_methods.secant.*;
import fractalzoomer.functions.root_finding_methods.steffensen.*;
import fractalzoomer.functions.root_finding_methods.stirling.*;
import fractalzoomer.functions.root_finding_methods.super_halley.*;
import fractalzoomer.functions.root_finding_methods.third_order_newton.*;
import fractalzoomer.functions.root_finding_methods.traub_ostrowski.*;
import fractalzoomer.functions.root_finding_methods.weerakoon_fernando.*;
import fractalzoomer.functions.root_finding_methods.whittaker.*;
import fractalzoomer.functions.root_finding_methods.whittaker_double_convex.*;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly1;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly2;
import fractalzoomer.functions.user_formulas.*;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.*;
import fractalzoomer.true_coloring_algorithms.TrueColorAlgorithm;
import fractalzoomer.utils.ColorAlgorithm;
import fractalzoomer.utils.ColorGenerator;
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.MathUtils;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    public static boolean USE_DIRECT_COLOR;
    public static int COLOR_SMOOTHING_METHOD;

    protected int drawing_done;
    protected int thread_calculated;

    protected static int[] algorithm_colors;
    protected static int[] algorithm_colors2;

    protected int threadId;
    private static AtomicInteger finalize_sync;
    private static CyclicBarrier normalize_find_ranges_sync;
    private static CyclicBarrier normalize_sync;
    private static CyclicBarrier normalize_sync2;
    private static CyclicBarrier normalize_find_ranges_sync_3d;
    private static CyclicBarrier normalize_sync_3d;
    private static CyclicBarrier normalize_sync2_3d;
    protected static CyclicBarrier initialize_jobs_sync;
    private static CyclicBarrier post_processing_sync;
    private static CyclicBarrier calculate_vectors_sync;
    private static AtomicInteger painting_sync;
    private static AtomicInteger height_scaling_sync;
    private static CyclicBarrier height_scaling_sync2;
    private static AtomicInteger height_scaling_sync3;
    private static CyclicBarrier height_scaling_sync4;
    private static CyclicBarrier height_function_sync;
    private static AtomicInteger gaussian_scaling_sync;
    private static CyclicBarrier gaussian_scaling_sync2;
    private static CyclicBarrier shade_color_height_sync;
    private static AtomicInteger total_calculated;
    protected static AtomicInteger normal_drawing_algorithm_pixel;
    private static CyclicBarrier color_cycling_filters_sync;
    private static CyclicBarrier color_cycling_restart_sync;
    private static ReentrantReadWriteLock color_cycling_toggle_lock;
    protected static AtomicInteger reference_calc_sync;
    protected static CyclicBarrier reference_sync;
    /**
     * ****************************
     */

    /**
     * ** 3D ***
     */
    private boolean d3;
    private int detail;
    private double fiX, fiY, scale, m20, m21, m22;
    private double d3_height_scale;
    private int max_range;
    private int min_range;
    private int max_scaling;
    private int height_algorithm;
    private boolean gaussian_scaling;
    private double gaussian_weight;
    private int gaussian_kernel_size;
    private static float vert[][][];
    private static float vert1[][][];
    private static float Norm1z[][][];
    private static float[] gaussian_kernel;
    private static float[][] temp_array;
    private double color_3d_blending;
    private boolean shade_height;
    private int shade_choice;
    private int shade_algorithm;
    private boolean shade_invert;
    private int d3_color_type;
    private static double max;
    private static double min;
    private static double maxIterations3d;
    private static double minIterations3d;
    private static double histogramDenominator = 1;
    private static int[] histogramCounts;
    private static int totalCounts;
    private boolean histogramHeight;
    private int histogram_granularity;
    private double histogram_density;

    /**
     * *********
     */
    /**
     * ** Histogram Coloring ***
     */
    private static double maxIterationEscaped;
    private static double maxIterationNotEscaped;
    private static double minIterationsNotEscaped;
    private static double minIterationsEscaped;
    private static int[] escapedCounts;
    private static int[] notEscapedCounts;
    private static int totalEscaped;
    private static int totalNotEscaped;
    private static double denominatorEscaped;
    private static double denominatorNotEscaped;

    /**
     * *********
     */
    /**
     * ** Filters ***
     */
    private boolean[] filters;
    protected int[] filters_options_vals;
    private int[][] filters_options_extra_vals;
    protected boolean fast_julia_filters;
    private Color[] filters_colors;
    private Color[][] filters_extra_colors;
    private int[] filters_order;
    /**
     * **************
     */

    /**
     * ** Image Related ***
     */
    protected BufferedImage image;
    protected int[] rgbs;
    protected static double[] image_iterations;
    protected static Complex[] domain_image_data;
    protected static double[] image_iterations_fast_julia;
    protected static boolean[] escaped_fast_julia;
    protected static boolean[] escaped;
    public static int IMAGE_SIZE;
    /**
     * ********************
     */

    /**
     * ** Post Processing ***
     */
    private static final int MAX_BUMP_MAPPING_DEPTH = 100;
    private static final int DEFAULT_BUMP_MAPPING_STRENGTH = 50;
    private int dem_color;
    private int[] post_processing_order;
    private boolean usePaletteForInColoring;
    private LightSettings ls;
    private BumpMapSettings bms;
    private EntropyColoringSettings ens;
    private RainbowPaletteSettings rps;
    private FakeDistanceEstimationSettings fdes;
    private ContourColoringSettings cns;
    private OffsetColoringSettings ofs;
    private GreyscaleColoringSettings gss;
    private PaletteGradientMergingSettings pbs;
    private HistogramColoringSettings hss;

    /**
     * **********************
     */
    /**
     * ** Traps ***
     */
    private OrbitTrapSettings ots;
    /**
     * **********************
     */

    /**
     * **********************
     */
    /**
     * ** Statistics ***
     */
    private StatisticsSettings sts;
    /**
     * **********************
     */


    /**
     * ** Color Cycling ***
     */
    private static boolean color_cycling;
    private int color_cycling_location_outcoloring;
    private int color_cycling_location_incoloring;
    private int gradient_offset;
    private int color_cycling_speed;
    private boolean cycle_colors;
    private boolean cycle_lights;
    private boolean cycle_gradient;
    /**
     * ********************
     */

    protected Apfloat xCenter;
    protected Apfloat yCenter;
    protected Apfloat size;
    protected Apfloat[] rotation_center;
    protected Apfloat[] rotation_vals;
    protected Fractal fractal;
    protected IterationAlgorithm iteration_algorithm;
    private boolean domain_coloring;
    private DomainColoringSettings ds;
    private boolean usesTrueColorIn;
    protected double height_ratio;
    protected boolean polar_projection;
    protected double circle_period;
    private int fractal_color;
    private int max_iterations;
    public static PaletteColor palette_outcoloring;
    public static PaletteColor palette_incoloring;
    private TransferFunction color_transfer_outcoloring;
    private TransferFunction color_transfer_incoloring;
    private Blending blending;
    private InterpolationMethod method;
    private int color_blending;
    private DomainColoring domain_color;
    private double contourFactor;
    protected MainWindow ptr;
    private ImageExpanderWindow ptrExpander;
    protected JProgressBar progress;
    private int action;
    private boolean quickDraw;
    protected int tile;
    private static String default_init_val;
    private static double convergent_bailout;
    public static int TILE_SIZE = 5;
    private static int QUICK_DRAW_DELAY = 1500; //msec
    public static int SKIPPED_PIXELS_ALG = 0;
    public static int SKIPPED_PIXELS_COLOR = 0xFFFFFFFF;
    public static int[] gradient;
    public static boolean PERTURBATION_THEORY = false;
    public static boolean SERIES_APPROXIMATION = true;
    public static boolean CALCULATE_FULL_REFERENCE = false;
    public static int SERIES_APPROXIMATION_TERMS = 5;
    public static boolean USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = false;
    public static long SERIES_APPROXIMATION_OOM_DIFFERENCE = 2;

    static {

        default_init_val = "c";
        convergent_bailout = 0;
        List<Color> colors = ColorGenerator.generate(600, 0, 0);
        algorithm_colors = new int[colors.size()];

        for (int i = 0; i < algorithm_colors.length; i++) {
            algorithm_colors[i] = colors.get(i).getRGB();
        }

        algorithm_colors2 = new int[200];

        Random random = new Random(5);
        for (int i = 0; i < algorithm_colors2.length; i++) {
            algorithm_colors2[i] = Color.HSBtoRGB(random.nextFloat(), 1, 1);
        }

    }

    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor) {
        this.contourFactor = contourFactor;
        settingsFractal(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, d3s, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, fns.smoothing, periodicity_checking, fns.plane_type, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.perturbation_user_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.initial_value_user_formula, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, fns.laguerre_deg, color_blending, ots, cns, fns.kleinianLine, fns.kleinianK, fns.kleinianM, post_processing_order, ls, pbs, sts, fns.gcs, fns.durand_kerner_init_val, fns.mps, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.gcps, fns.igs, fns.lfns, fns.newton_hines_k, fns.tcs, fns.lpns.lyapunovInitialValue, hss, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.root_initialization_method, fns.preffs, fns.postffs, fns.ips, fns.defaultNovaInitialValue);
    }

    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor) {
        this.contourFactor = contourFactor;
        settingsFractalExpander(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, fns.smoothing, periodicity_checking, fns.plane_type, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.perturbation_user_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.initial_value_user_formula, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, fns.laguerre_deg, color_blending, ots, cns, fns.kleinianLine, fns.kleinianK, fns.kleinianM, post_processing_order, ls, pbs, sts, fns.gcs, fns.durand_kerner_init_val, fns.mps, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.gcps, fns.igs, fns.lfns, fns.newton_hines_k, fns.tcs, fns.lpns.lyapunovInitialValue, hss, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.root_initialization_method, fns.preffs, fns.postffs, fns.ips, fns.defaultNovaInitialValue);
    }

    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, double xJuliaCenter, double yJuliaCenter) {
        this.contourFactor = contourFactor;
        settingsJulia(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, d3s, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, fns.smoothing, periodicity_checking, fns.plane_type, fns.apply_plane_on_julia, fns.apply_plane_on_julia_seed, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds, inverse_dem, quickDraw, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, fns.gcs, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.laguerre_deg, fns.gcps, fns.lfns, fns.newton_hines_k, fns.tcs, hss, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.preffs, fns.postffs, fns.ips, fns.juliter, fns.juliterIterations, fns.juliterIncludeInitialIterations, fns.defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
    }

    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, double xJuliaCenter, double yJuliaCenter) {
        this.contourFactor = contourFactor;
        settingsJuliaExpander(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, fns.smoothing, periodicity_checking, fns.plane_type, fns.apply_plane_on_julia, fns.apply_plane_on_julia_seed, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, fns.gcs, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.laguerre_deg, fns.gcps, fns.lfns, fns.newton_hines_k, fns.tcs, hss, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.preffs, fns.postffs, fns.ips, fns.juliter, fns.juliterIterations, fns.juliterIncludeInitialIterations, fns.defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
    }

    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor) {
        this.contourFactor = contourFactor;
        settingsJuliaMap(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, fns.smoothing, periodicity_checking, fns.plane_type, fns.apply_plane_on_julia, fns.apply_plane_on_julia_seed, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, fns.gcs, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.laguerre_deg, fns.gcps, fns.lfns, fns.newton_hines_k, fns.tcs, hss, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.preffs, fns.postffs, fns.ips, fns.juliter, fns.juliterIterations, fns.juliterIncludeInitialIterations, fns.defaultNovaInitialValue);
    }

    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, int gradient_offset, HistogramColoringSettings hss, double contourFactor, double xJuliaCenter, double yJuliaCenter) {
        this.contourFactor = contourFactor;
        settingsJuliaPreview(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fns.plane_type, fns.apply_plane_on_julia, fns.apply_plane_on_julia_seed, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, fns.smoothing, fs, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, inverse_dem, color_intensity, transfer_function, color_intensity2, transfer_function2, usePaletteForInColoring, ens, ofs, gss, color_blending, ots, cns, post_processing_order, ls, pbs, sts, fns.gcs, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.laguerre_deg, fns.gcps, fns.lfns, fns.newton_hines_k, fns.tcs, hss, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.preffs, fns.postffs, fns.ips, fns.juliter, fns.juliterIterations, fns.juliterIncludeInitialIterations, fns.defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
    }

    //Fractal
    private void settingsFractal(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, double[] laguerre_deg, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, double[] kleinianLine, double kleinianK, double kleinianM, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, GenericCaZbdZeSettings gcs, double[] durand_kernel_init_val, MagneticPendulumSettings mps, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, String lyapunovInitialValue, HistogramColoringSettings hss, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, int root_initialization_method, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean defaultNovaInitialValue) {

        if (quickDraw && size.compareTo(MyApfloat.MIN_DOUBLE_SIZE) > 0 && max_iterations > 3000) {
            max_iterations = 3000;
        }

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_center = rotation_center;
        this.rotation_vals = rotation_vals;

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
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
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
        this.d3_color_type = d3s.d3_color_type;
        this.histogramHeight = d3s.histogram_equalization;
        this.histogram_granularity = d3s.histogram_granularity;
        this.histogram_density = d3s.histogram_density;

        this.color_blending = color_blending;

        this.post_processing_order = post_processing_order;

        this.bms = bms;
        this.ls = ls;
        this.fdes = fdes;
        this.rps = rps;
        this.ens = ens;
        this.ofs = ofs;
        this.cns = cns;
        this.gss = gss;
        this.pbs = pbs;
        this.hss = hss;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;

        this.ots = ots;

        progress = ptr.getProgressBar();

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

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2);

        this.quickDraw = quickDraw;
        tile = TILE_SIZE;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        fractal = fractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kernel_init_val, mps, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue);

        this.sts = sts;
        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = fractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kernel_init_val, mps, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(false);
            }
        }

        setTrueColoringOptions(tcs);

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

        iteration_algorithm = new FractalIterationAlgorithm(fractal);

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD);
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Fractal-Expander
    private void settingsFractalExpander(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, double[] laguerre_deg, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, double[] kleinianLine, double kleinianK, double kleinianM, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, GenericCaZbdZeSettings gcs, double[] durand_kernel_init_val, MagneticPendulumSettings mps, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, String lyapunovInitialValue, HistogramColoringSettings hss, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, int root_initialization_method, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean defaultNovaInitialValue) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_center = rotation_center;
        this.rotation_vals = rotation_vals;

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
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.height_ratio = height_ratio;

        this.color_blending = color_blending;

        this.post_processing_order = post_processing_order;

        this.bms = bms;
        this.ls = ls;
        this.fdes = fdes;
        this.rps = rps;
        this.ens = ens;
        this.ofs = ofs;
        this.cns = cns;
        this.gss = gss;
        this.pbs = pbs;
        this.hss = hss;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;

        this.ots = ots;

        progress = ptrExpander.getProgressBar();

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

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2);

        tile = TILE_SIZE;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        fractal = fractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kernel_init_val, mps, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue);

        this.sts = sts;
        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = fractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kernel_init_val, mps, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(false);
            }
        }

        setTrueColoringOptions(tcs);

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

        iteration_algorithm = new FractalIterationAlgorithm(fractal);

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD);
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia
    private void settingsJulia(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, HistogramColoringSettings hss, String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, double xJuliaCenter, double yJuliaCenter) {

        if (quickDraw && size.compareTo(MyApfloat.MIN_DOUBLE_SIZE) > 0 && max_iterations > 3000) {
            max_iterations = 3000;
        }

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

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
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
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
        this.d3_color_type = d3s.d3_color_type;
        this.histogramHeight = d3s.histogram_equalization;
        this.histogram_granularity = d3s.histogram_granularity;
        this.histogram_density = d3s.histogram_density;

        this.color_blending = color_blending;

        this.post_processing_order = post_processing_order;

        this.bms = bms;
        this.ls = ls;
        this.fdes = fdes;
        this.rps = rps;
        this.ens = ens;
        this.ofs = ofs;
        this.cns = cns;
        this.gss = gss;
        this.pbs = pbs;
        this.hss = hss;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;

        this.ots = ots;

        progress = ptr.getProgressBar();

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

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2);

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        fractal = juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);

        this.sts = sts;
        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(true);
                ((Equicontinuity) fractal.getStatisticInstance()).setJuliIter(juliter);
            }
        }

        setTrueColoringOptions(tcs);

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        this.quickDraw = quickDraw;
        tile = TILE_SIZE;

        if(juliter) {
            default_init_val = fractal.getInitialValue();
        }
        else {
            default_init_val = "c";
        }

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD);
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia-Expander
    private void settingsJuliaExpander(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, HistogramColoringSettings hss, String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, double xJuliaCenter, double yJuliaCenter) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

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
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.height_ratio = height_ratio;

        this.color_blending = color_blending;

        this.post_processing_order = post_processing_order;

        this.bms = bms;
        this.ls = ls;
        this.fdes = fdes;
        this.rps = rps;
        this.ens = ens;
        this.ofs = ofs;
        this.cns = cns;
        this.gss = gss;
        this.pbs = pbs;
        this.hss = hss;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;

        this.ots = ots;

        progress = ptrExpander.getProgressBar();

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

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2);

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        fractal = juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);

        this.sts = sts;
        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(true);
                ((Equicontinuity) fractal.getStatisticInstance()).setJuliIter(juliter);
            }
        }

        setTrueColoringOptions(tcs);

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        tile = TILE_SIZE;

        if(juliter) {
            default_init_val = fractal.getInitialValue();
        }
        else {
            default_init_val = "c";
        }

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD);
        }

        drawing_done = 0;
        thread_calculated = 0;

    }

    //Julia Map
    private void settingsJuliaMap(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, HistogramColoringSettings hss, String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

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
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.height_ratio = height_ratio;

        this.color_blending = color_blending;

        this.post_processing_order = post_processing_order;

        this.bms = bms;
        this.ls = ls;
        this.fdes = fdes;
        this.rps = rps;
        this.ens = ens;
        this.ofs = ofs;
        this.gss = gss;
        this.cns = cns;
        this.pbs = pbs;
        this.hss = hss;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.ots = ots;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        progress = ptr.getProgressBar();

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2);

        if (polar_projection) {
            action = JULIA_MAP_POLAR;
        } else {
            action = JULIA_MAP;
        }

        double xJuliaCenter, yJuliaCenter;

        if (polar_projection) {

            PolarLocation location = new PolarLocation(xCenter, yCenter, size, height_ratio, image.getHeight(), circle_period);
            BigPoint p = location.getPoint((int)(FROMx + (TOx - FROMx) * 0.5), (int)(FROMy + (TOy - FROMy) * 0.5));
            p = MathUtils.rotatePointRelativeToPoint(p, rotation_vals, rotation_center);

            xJuliaCenter = p.x.doubleValue();
            yJuliaCenter = p.y.doubleValue();
        } else {
            CartesianLocation location = new CartesianLocation(xCenter, yCenter, size, height_ratio, image.getHeight());
            BigPoint p = location.getPoint((int)(FROMx + (TOx - FROMx) * 0.5), (int)(FROMy + (TOy - FROMy) * 0.5));

            p = MathUtils.rotatePointRelativeToPoint(p, rotation_vals, rotation_center);

            xJuliaCenter = p.x.doubleValue();
            yJuliaCenter = p.y.doubleValue();
        }

        double mapxCenter = 0;
        double mapyCenter = 0;

        if (function == MainWindow.FORMULA27) {
            mapxCenter = -2;
        }

        fractal = juliaFactory(function, mapxCenter, mapyCenter, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);

        this.sts = sts;
        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = juliaFactory(function, mapxCenter, mapyCenter, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(true);
                ((Equicontinuity) fractal.getStatisticInstance()).setJuliIter(juliter);
            }
        }

        setTrueColoringOptions(tcs);

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        if(juliter) {
            default_init_val = fractal.getInitialValue();
        }
        else {
            default_init_val = "c";
        }

        convergent_bailout = fractal.getConvergentBailout();

        drawing_done = 0;

    }

    //Julia Preview
    private void settingsJuliaPreview(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, FiltersSettings fs, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, StatisticsSettings sts, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, HistogramColoringSettings hss, String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, double xJuliaCenter, double yJuliaCenter) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

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
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.height_ratio = height_ratio;

        this.color_blending = color_blending;

        this.post_processing_order = post_processing_order;

        this.bms = bms;
        this.ls = ls;
        this.fdes = fdes;
        this.rps = rps;
        this.ens = ens;
        this.ofs = ofs;
        this.cns = cns;
        this.gss = gss;
        this.pbs = pbs;
        this.hss = hss;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.ots = ots;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        progress = ptr.getProgressBar();

        if (polar_projection) {
            action = FAST_JULIA_POLAR;
        } else {
            action = FAST_JULIA;
        }

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2);

        fractal = juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);

        this.sts = sts;
        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = fractal = juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(true);
                ((Equicontinuity) fractal.getStatisticInstance()).setJuliIter(juliter);
            }
        }

        setTrueColoringOptions(tcs);

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

    }

    //Color Cycling
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, int color_cycling_location2, BumpMapSettings bms, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, boolean cycle_colors, boolean cycle_lights, boolean cycle_gradient, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss, double contourFactor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.contourFactor = contourFactor;

        action = COLOR_CYCLING;

        this.color_cycling_speed = color_cycling_speed;
        this.cycle_colors = cycle_colors;
        this.cycle_lights = cycle_lights;
        this.cycle_gradient = cycle_gradient;

        this.color_blending = color_blending;

        this.post_processing_order = post_processing_order;

        this.bms = new BumpMapSettings(bms);
        this.ls = new LightSettings(ls);
        this.fdes = fdes;
        this.rps = rps;
        this.ens = ens;
        this.ofs = ofs;
        this.cns = cns;
        this.gss = gss;
        this.pbs = pbs;
        this.hss = hss;

        this.ots = ots;

        domain_coloring = ds.domain_coloring;

        progress = ptr.getProgressBar();

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2);

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD);
        }

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    }

    //Apply Filter
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, BumpMapSettings bms, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, double color_intensity, int transfer_function, double color_intensity2, int transfer_function2, boolean usePaletteForInColoring, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, ContourColoringSettings cns, int[] post_processing_order, LightSettings ls, PaletteGradientMergingSettings pbs, OrbitTrapSettings ots, DomainColoringSettings ds, int gradient_offset, HistogramColoringSettings hss, double contourFactor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.max_iterations = max_iterations;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.contourFactor = contourFactor;
        action = APPLY_PALETTE_AND_FILTER;

        this.color_blending = color_blending;

        this.post_processing_order = post_processing_order;

        this.bms = bms;
        this.ls = ls;
        this.fdes = fdes;
        this.rps = rps;
        this.ens = ens;
        this.ofs = ofs;
        this.cns = cns;
        this.gss = gss;
        this.pbs = pbs;
        this.hss = hss;

        this.ots = ots;

        domain_coloring = ds.domain_coloring;

        progress = ptr.getProgressBar();

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2);

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD);
        }

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        drawing_done = 0;

    }

    //Rotate 3d model
    public ThreadDraw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs, int color_blending, double contourFactor) {

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
        this.contourFactor = contourFactor;

        this.color_blending = color_blending;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        scale = d3s.d3_size_scale;

        d3_color_type = d3s.d3_color_type;

        progress = ptr.getProgressBar();

        blendingFactory(COLOR_SMOOTHING_METHOD);
        interpolationFactory(COLOR_SMOOTHING_METHOD);

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
            drawIterationsDomainAntialiased(image_size, false);
        } else {
            drawIterationsDomain(image_size, false);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptrExpander.getNumberOfThreads()) {

            image_iterations = null;
            escaped = null;
            System.gc();

            applyFilters();

            ptrExpander.writeImageToDisk();

            ptrExpander.setOptions(true);

            progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptrExpander.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptrExpander.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");

        }
    }

    private void drawDomainPolarExpander() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsDomainAntialiased(image_size, true);
        } else {
            drawIterationsDomain(image_size, true);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptrExpander.getNumberOfThreads()) {

            image_iterations = null;
            escaped = null;
            System.gc();

            applyFilters();

            ptrExpander.writeImageToDisk();

            ptrExpander.setOptions(true);

            progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptrExpander.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptrExpander.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");

        }
    }

    private void drawPolarExpander() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsAntialiased(image_size, true);
        } else {
            drawIterations(image_size, true);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptrExpander.getNumberOfThreads()) {

            image_iterations = null;
            escaped = null;
            System.gc();

            applyFilters();

            ptrExpander.writeImageToDisk();

            ptrExpander.setOptions(true);

            progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptrExpander.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptrExpander.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");

        }
    }

    private void drawExpander() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsAntialiased(image_size, false);
        } else {
            drawIterations(image_size, false);
        }

        if (drawing_done != 0) {
            update(drawing_done);
        }

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptrExpander.getNumberOfThreads()) {

            image_iterations = null;
            escaped = null;
            System.gc();

            applyFilters();

            ptrExpander.writeImageToDisk();

            ptrExpander.setOptions(true);

            progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptrExpander.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptrExpander.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");

        }
    }

    private void draw() {

        int image_size = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                drawIterations3DAntialiased(image_size, false);
            } else {
                drawIterations3D(image_size, false);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsAntialiased(image_size, false);
        } else {
            drawIterations(image_size, false);
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
                progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));
            }

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");
        }
    }

    private void quickDraw() {

        int image_size = image.getHeight();

        quickDrawIterations(image_size,false);

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
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void quickDrawPolar() {

        int image_size = image.getHeight();

        quickDrawIterations(image_size, true);

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
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void drawDomain() {

        int image_size = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                drawIterationsDomain3DAntialiased(image_size, false);
            } else {
                drawIterationsDomain3D(image_size, false);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsDomainAntialiased(image_size, false);
        } else {
            drawIterationsDomain(image_size, false);
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
                progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));
            }

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");
        }
    }

    private void quickDrawDomain() {

        int image_size = image.getHeight();

        quickDrawIterationsDomain(image_size, false);

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            ptr.updateValues("Domain C. mode");

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void quickDrawDomainPolar() {

        int image_size = image.getHeight();

        quickDrawIterationsDomain(image_size, true);

        total_calculated.addAndGet(thread_calculated);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            ptr.updateValues("Domain C. mode");

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");

            ptr.createCompleteImage(QUICK_DRAW_DELAY);
        }
    }

    private void drawPolar() {
        int image_size = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                drawIterations3DAntialiased(image_size, true);
            } else {
                drawIterations3D(image_size, true);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsAntialiased(image_size, true);
        } else {
            drawIterations(image_size, true);
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
                progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));
            }

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");
        }
    }

    private void drawDomainPolar() {
        int image_size = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                drawIterationsDomain3DAntialiased(image_size, true);
            } else {
                drawIterationsDomain3D(image_size, true);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            drawIterationsDomainAntialiased(image_size, true);
        } else {
            drawIterationsDomain(image_size, true);
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
                progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));
            }

            int temp2 = image_size * image_size;
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Pixels Calculated: " + String.format("%6.2f", ((double) total_calculated.get()) / (temp2) * 100) + "%<br>Threads used: " + ptr.getNumberOfThreads() + "<br>Skipped Iterations: " + Fractal.skippedIterations + "</html>");
        }
    }

    protected abstract void drawIterations(int image_size, boolean polar);

    protected int getFinalColor(double result, boolean escaped) {

        int color;

        if (USE_DIRECT_COLOR) {
            return 0xFF000000 | (0x00FFFFFF & (int) result);
        }
        else if (fractal != null && fractal.hasTrueColor()) {
            fractal.resetTrueColor();
            color = fractal.getTrueColorValue();

            if (pbs.palette_gradient_merge) {
                color = result < 0 ? getPaletteMergedColor(result * pbs.gradient_intensity - 2 * color_cycling_location_outcoloring, color) : getPaletteMergedColor(result * pbs.gradient_intensity + 2 * color_cycling_location_outcoloring, color);
            }
        }
        else if (sts.statistic && sts.statisticGroup == 2 && sts.equicontinuityOverrideColoring &&
                !((!sts.statisticIncludeNotEscaped && (!escaped || Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS))
                || (!sts.statisticIncludeEscaped && escaped && Math.abs(result) != ColorAlgorithm.MAXIMUM_ITERATIONS))) {
            color = getEquicontinuityColor(result, escaped);
        }
        else if (sts.statistic && sts.statisticGroup == 3 && sts.normalMapOverrideColoring &&
                !((!sts.statisticIncludeNotEscaped && (!escaped || Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS))
                        || (!sts.statisticIncludeEscaped && escaped && Math.abs(result) != ColorAlgorithm.MAXIMUM_ITERATIONS))) {
            color = getNormalMapColor(result, escaped);
        }
        else {
            color = getStandardColor(result, escaped);
        }

        if (ots.useTraps && !((!ots.trapIncludeNotEscaped && (!escaped || Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS))
                || (!ots.trapIncludeEscaped && escaped && Math.abs(result) != ColorAlgorithm.MAXIMUM_ITERATIONS))) {
            return getTrapColor(color);
        }

        return color;

    }

    private int getEquicontinuityColor(double result, boolean escaped) {
        GenericStatistic statistic = fractal.getStatisticInstance();

        if(statistic == null) {
            return getStandardColor(result, escaped);
        }

        double L = escaped ? statistic.getValueForColoring() : statistic.getValueNotEscapedForColoring();

        double argValue = 0;
        if(sts.equicontinuityArgValue == 0) {
            argValue = statistic.getZ().arg();
        }
        else if(sts.equicontinuityArgValue == 1) {
            argValue = statistic.getStart().arg();
        }
        else if(sts.equicontinuityArgValue == 2) {
            argValue = statistic.getPixel().arg();
        }
        else if(sts.equicontinuityArgValue == 3) {
            argValue = statistic.getC().arg();
        }

        double arg = (Math.PI + argValue) / (Math.PI * 2);

        int[] rgb;

        if(sts.equicontinuityColorMethod == 0) {
            rgb = ColorSpaceConverter.HSLtoRGB(arg, sts.equicontinuitySatChroma, L);
        }
        else if(sts.equicontinuityColorMethod == 1) {
            rgb = ColorSpaceConverter.HSBtoRGB(arg, sts.equicontinuitySatChroma, L);
        }
        else if(sts.equicontinuityColorMethod == 2) {
            rgb = ColorSpaceConverter.LCHtoRGB(L * 100, sts.equicontinuitySatChroma * 140, arg * 360);
        }
        else {

            int color2 = 0;

            if(sts.equicontinuityColorMethod == 3) {
                int paletteLength = (!escaped && usePaletteForInColoring) ? palette_incoloring.getPaletteLength() : palette_outcoloring.getPaletteLength();

                color2 = getStandardColor(arg * paletteLength, escaped);
            }
            else {
                color2 = getStandardColor(result, escaped);
            }

            int red = (color2 >> 16) & 0xFF;
            int green = (color2 >> 8) & 0xFF;
            int blue = color2 & 0xFF;

            return getModifiedColor(red, green, blue, L, sts.equicontinuityMixingMethod, sts.equicontinuityBlending, false);
        }

        return 0xFF000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    private int getNormalMapColor(double result, boolean escaped) {
        GenericStatistic statistic = fractal.getStatisticInstance();

        if(statistic == null) {
            return getStandardColor(result, escaped);
        }

        int color = getStandardColor(result, escaped);

        if((sts.normalMapColoring == 1 || sts.normalMapColoring == 2 ) && Math.abs(result) != ColorAlgorithm.MAXIMUM_ITERATIONS) {

            if(sts.normalMapColoring == 1) {
                int paletteLength = (!escaped && usePaletteForInColoring) ? palette_incoloring.getPaletteLength() : palette_outcoloring.getPaletteLength();
                color = getStandardColor(statistic.getExtraValue() * paletteLength, escaped);
            }
            else {
                color = getStandardColor(statistic.getExtraValue(), escaped);
            }
        }

        if(!sts.useNormalMap) {
            return color;
        }

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        double L = statistic.getValueForColoring() * 2;

        double coef = L;
        coef = coef > 1 ? 1 : coef;

        double coef2 = L - 1;
        coef2 = coef2 < 0 ? 0 : coef2;
        coef2 = coef2 * sts.normalMapLightFactor;

        if (sts.normalMapColorMode == 0) { //Lab
            double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
            int[] rgb = ColorSpaceConverter.LABtoRGB(res[0] * coef + coef2 * 100, res[1], res[2]);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (sts.normalMapColorMode == 1) { //HSB
            double[] res = ColorSpaceConverter.RGBtoHSB(r, g, b);

            double val = res[2] * coef + coef2;

            if (val > 1) {
                val = 1;
            }
            if (val < 0) {
                val = 0;
            }

            int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (sts.normalMapColorMode == 2) { //HSL
            double[] res = ColorSpaceConverter.RGBtoHSL(r, g, b);

            double val = res[2] * coef + coef2;

            if (val > 1) {
                val = 1;
            }
            if (val < 0) {
                val = 0;
            }

            int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (sts.normalMapColorMode == 3) { //Blending
            if (coef > 1) {
                coef = 1;
            }
            if (coef < 0) {
                coef = 0;
            }

            int index = (int) ((1 - coef) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            int temp_red = (grad_color >> 16) & 0xff;
            int temp_green = (grad_color >> 8) & 0xff;
            int temp_blue = grad_color & 0xff;

            int new_color =  blending.blend(temp_red, temp_green, temp_blue, r, g, b, 1 - sts.normalMapBlending);

            r = (new_color >> 16) & 0xFF;
            g = (new_color >> 8) & 0xFF;
            b = new_color & 0xFF;

            double temp = coef2 * 255;
            r = (int) (r + temp + 0.5);
            g = (int) (g + temp + 0.5);
            b = (int) (b + temp +  0.5);

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }

            return 0xff000000 | (r << 16) | (g << 8) | b;
        } else { //scaling

            double temp = coef2 * 255;
            r = (int) (r * coef + temp + 0.5);
            g = (int) (g * coef + temp + 0.5);
            b = (int) (b * coef + temp +  0.5);

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }

            return 0xff000000 | (r << 16) | (g << 8) | b;
        }
    }

    private int getStandardColor(double result, boolean escaped) {

        if (USE_DIRECT_COLOR) {
            return 0xFF000000 | (0x00FFFFFF & (int) result);
        }

        int colorA = 0;

        if (result == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            colorA = fractal_color;
        } else if (result == -ColorAlgorithm.MAXIMUM_ITERATIONS) {
            colorA = dem_color;
        } else if (!escaped && usePaletteForInColoring) {
            double transfered_result = color_transfer_incoloring.transfer(result);
            colorA = transfered_result < 0 ? palette_incoloring.getPaletteColor(transfered_result - color_cycling_location_incoloring) : palette_incoloring.getPaletteColor(transfered_result + color_cycling_location_incoloring);

            if (pbs.palette_gradient_merge) {
                colorA = result < 0 ? getPaletteMergedColor(result * pbs.gradient_intensity - 2 * color_cycling_location_outcoloring, colorA) : getPaletteMergedColor(result * pbs.gradient_intensity + 2 * color_cycling_location_outcoloring, colorA);
            }
        } else {
            double transfered_result = color_transfer_outcoloring.transfer(result);
            colorA = transfered_result < 0 ? palette_outcoloring.getPaletteColor(transfered_result - color_cycling_location_outcoloring) : palette_outcoloring.getPaletteColor(transfered_result + color_cycling_location_outcoloring);

            if (pbs.palette_gradient_merge) {
                colorA = result < 0 ? getPaletteMergedColor(result * pbs.gradient_intensity - 2 * color_cycling_location_outcoloring, colorA) : getPaletteMergedColor(result * pbs.gradient_intensity + 2 * color_cycling_location_outcoloring, colorA);
            }
        }

        return colorA;

    }

    private int getPaletteMergedColor(double result, int color) {

        double temp = (Math.abs(result) + pbs.gradient_offset) / gradient.length;

        int old_red = (color >> 16) & 0xFF;
        int old_green = (color >> 8) & 0xFF;
        int old_blue = color & 0xFF;

        if (temp > 1) {
            temp = (int) temp % 2 == 1 ? 1 - (temp - (int) temp) : (temp - (int) temp);
        }

        int index = (int) (temp * (gradient.length - 1) + 0.5);

        int grad_color = getGradientColor(index + gradient_offset);

        int temp_red = grad_color & 0xff;
        int temp_green = grad_color & 0xff;
        int temp_blue = grad_color & 0xff;

        if (pbs.merging_type == 0) { //Lab
            double[] grad = ColorSpaceConverter.RGBtoLAB(temp_red, temp_green, temp_blue);
            double[] res = ColorSpaceConverter.RGBtoLAB(old_red, old_green, old_blue);
            int[] rgb = ColorSpaceConverter.LABtoRGB(grad[0], res[1], res[2]);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (pbs.merging_type == 1) { //HSB
            double[] grad = ColorSpaceConverter.RGBtoHSB(temp_red, temp_green, temp_blue);
            double[] res = ColorSpaceConverter.RGBtoHSB(old_red, old_green, old_blue);
            int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], grad[2]);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (pbs.merging_type == 2) { //HSL
            double[] grad = ColorSpaceConverter.RGBtoHSL(temp_red, temp_green, temp_blue);
            double[] res = ColorSpaceConverter.RGBtoHSL(old_red, old_green, old_blue);
            int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], grad[2]);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (pbs.merging_type == 3) {// blend
            return blending.blend(temp_red, temp_green, temp_blue, old_red, old_green, old_blue, 1 - pbs.palette_blending);
        } else { //scale
            double avg = ((temp_red + temp_green + temp_blue) / 3.0) / 255.0;
            old_red = (int) (old_red * avg + 0.5);
            old_green = (int) (old_green * avg + 0.5);
            old_blue = (int) (old_blue * avg + 0.5);
            return 0xff000000 | (old_red << 16) | (old_green << 8) | old_blue;
        }

    }

    private int getTrapColor(int originalColor) {

        OrbitTrap trap = fractal.getOrbitTrapInstance();

        if (trap.hasColor()) {
            int color = trap.getColor();
            if (((color >> 24) & 0xFF) == 0) {
                return originalColor;
            }
            return color;
        }

        double distance = trap.getDistance();

        if ((ots.trapMaxDistance != 0 && distance > ots.trapMaxDistance) || distance == Double.MAX_VALUE) {
            return originalColor;
        }

        double maxDistance = ots.trapMaxDistance != 0 ? ots.trapMaxDistance : trap.getMaxValue();
        distance /= maxDistance;

        int trapColor = getModifiedColor((originalColor >> 16) & 0xFF, (originalColor >> 8) & 0xFF, originalColor & 0xFF, distance, ots.trapColorMethod, 1 - ots.trapBlending, true);

        if (ots.trapColorInterpolation != 0) {

            int red = (trapColor >> 16) & 0xFF;
            int green = (trapColor >> 8) & 0xFF;
            int blue = trapColor & 0xFF;

            int trapRed;
            int trapGreen;
            int trapBlue;

            if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_RANDOM) {
                int color = algorithm_colors[trap.getIteration() % algorithm_colors.length];

                trapRed = (color >> 16) & 0xFF;
                trapGreen = (color >> 8) & 0xFF;
                trapBlue = color & 0xFF;
            } else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_RANDOM_HSB) {
                int color = algorithm_colors2[trap.getIteration() % algorithm_colors2.length];

                trapRed = (color >> 16) & 0xFF;
                trapGreen = (color >> 8) & 0xFF;
                trapBlue = color & 0xFF;
            } else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ARG_PALETTE) {
                double transfered_result = color_transfer_outcoloring.transfer((trap.getTrappedPoint().arg() + Math.PI) / (2 * Math.PI) * palette_outcoloring.getPaletteLength());
                int color = palette_outcoloring.getPaletteColor(transfered_result + color_cycling_location_outcoloring);

                trapRed = (color >> 16) & 0xFF;
                trapGreen = (color >> 8) & 0xFF;
                trapBlue = color & 0xFF;
            } else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_RANDOM_PALETTE) {
                double transfered_result = color_transfer_outcoloring.transfer(trap.getIteration() * 1.61803398875 * Math.PI * Math.E);
                int color = palette_outcoloring.getPaletteColor(transfered_result + color_cycling_location_outcoloring);

                trapRed = (color >> 16) & 0xFF;
                trapGreen = (color >> 8) & 0xFF;
                trapBlue = color & 0xFF;
            } else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ARG_HUE_HSB) {
                int color = Color.HSBtoRGB((float) ((trap.getTrappedPoint().arg() + Math.PI) / (2 * Math.PI)), 1, 1);

                trapRed = (color >> 16) & 0xFF;
                trapGreen = (color >> 8) & 0xFF;
                trapBlue = color & 0xFF;
            } else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ARG_HUE_LCH) {
                int[] rgb = ColorSpaceConverter.LCHtoRGB(50, 100, 360 * ((trap.getTrappedPoint().arg() + Math.PI) / (2 * Math.PI)));

                trapRed = rgb[0];
                trapGreen = rgb[1];
                trapBlue = rgb[2];
            }
            else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ITER_HSB) {
                int index = trap.getIteration() % 20;

                int[] rgb = ColorSpaceConverter.HSBtoRGB(index / 20.0, 1, 1);

                trapRed = rgb[0];
                trapGreen = rgb[1];
                trapBlue = rgb[2];
            }
            else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ITER_LCH) {
                int index = trap.getIteration() % 20;

                int[] rgb = ColorSpaceConverter.LCHtoRGB(50, 100, 360 * (index / 20.0));

                trapRed = rgb[0];
                trapGreen = rgb[1];
                trapBlue = rgb[2];
            }
            else {
                trapRed = ots.trapColor1.getRed();
                trapGreen = ots.trapColor1.getGreen();
                trapBlue = ots.trapColor1.getBlue();

                if (trap.getTrapId() == 1) {
                    trapRed = ots.trapColor2.getRed();
                    trapGreen = ots.trapColor2.getGreen();
                    trapBlue = ots.trapColor2.getBlue();
                } else if (trap.getTrapId() == 2) {
                    trapRed = ots.trapColor3.getRed();
                    trapGreen = ots.trapColor3.getGreen();
                    trapBlue = ots.trapColor3.getBlue();
                }
            }

            trapColor = method.interpolate(red, green, blue, trapRed, trapGreen, trapBlue, ots.trapColorInterpolation);

        }

        if (ots.trapCellularStructure) {

            double limit = 1 - ots.trapCellularSize;
            double coef = 0;

            if (distance >= limit) {
                coef = (distance - limit) / (1 - limit);
            }

            /*double invert_limit = 1 - limit;

            if (distance <= invert_limit) {
                coef = distance / limit;
            }*/
            int trapRed = (trapColor >> 16) & 0xFF;
            int trapGreen = (trapColor >> 8) & 0xFF;
            int trapBlue = trapColor & 0xFF;

            if (ots.trapCellularInverseColor) {
                coef = 1 - coef;
            }

            trapColor = method.interpolate(ots.trapCellularColor.getRed(), ots.trapCellularColor.getGreen(), ots.trapCellularColor.getBlue(), trapRed, trapGreen, trapBlue, 1 - coef);
        }

        return trapColor;
    }

    private void quickDrawIterationsDomain(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, polar, false);

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
                image_iterations[loc2] = color = rgbs[loc2] = domain_color.getDomainColor(iteration_algorithm.calculateDomain(location.getComplex(x, y)));

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

    private void quickDrawIterations(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, polar, PERTURBATION_THEORY && !fractal.isJulia() && fractal.supportsPerturbationTheory());

        if(PERTURBATION_THEORY && !fractal.isJulia() && fractal.supportsPerturbationTheory()) {
            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReference(location);
            }

            try {
                reference_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }
        }

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
                image_iterations[loc2] = iteration_algorithm.calculate(location.getComplex(x, y));
                escaped[loc2] = iteration_algorithm.escaped();
                color = rgbs[loc2] = getFinalColor(image_iterations[loc2], escaped[loc2]);

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

    private void drawIterationsDomain(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, polar, false);

        int pixel_percent = (image_size * image_size) / 100;

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

                Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                image_iterations[loc] = scaleDomainHeight(val.norm());
                rgbs[loc] = domain_color.getDomainColor(val);

                if (domain_image_data != null) {
                    domain_image_data[loc] = val;
                }

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                thread_calculated += drawing_done;
                drawing_done = 0;
            }

        } while (true);

        thread_calculated += drawing_done;

        postProcess(image_size);

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
                    mod = Math.sqrt(norm_0_0 * norm_0_0 + norm_0_1 * norm_0_1 + norm_0_2 * norm_0_2);
                    norm_0_0 /= mod;
                    norm_0_1 /= mod;
                    norm_0_2 /= mod;

                    norm_1_0 = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    norm_1_1 = dx;
                    norm_1_2 = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(norm_1_0 * norm_1_0 + norm_1_1 * norm_1_1 + norm_1_2 * norm_1_2);
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

    private void drawIterations3D(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, detail, circle_period, rotation_center, rotation_vals, fractal, polar, PERTURBATION_THEORY && !fractal.isJulia() && fractal.supportsPerturbationTheory());

        if(PERTURBATION_THEORY && !fractal.isJulia() && fractal.supportsPerturbationTheory()) {
            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReference(location);
            }

            try {
                reference_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }
        }

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail;

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

                temp = iteration_algorithm.calculate3D(location.getComplex(x, y));
                vert[x][y][1] = (float) (temp[0]);
                escaped[loc] = iteration_algorithm.escaped();
                vert[x][y][0] = getFinalColor(temp[1], escaped[loc]);
                image_iterations[loc] = temp[1];

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

    private void drawIterationsDomain3D(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, detail, circle_period, rotation_center, rotation_vals, fractal, polar, false);

        int pixel_percent = detail * detail / 100;

        int w2 = image_size / 2;
        double dx = image_size / (double) detail;

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

                Complex a = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                double norm = a.norm();

                vert[x][y][1] = calaculateDomainColoringHeight(norm);
                vert[x][y][0] = domain_color.getDomainColor(a);
                image_iterations[loc] = scaleDomainHeight(norm);

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

    private void drawIterations3DAntialiased(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, detail, circle_period, rotation_center, rotation_vals, fractal, polar, PERTURBATION_THEORY && !fractal.isJulia() && fractal.supportsPerturbationTheory());
        location.createAntialiasingSteps();

        if(PERTURBATION_THEORY && !fractal.isJulia() && fractal.supportsPerturbationTheory()) {
            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReference(location);
            }

            try {
                reference_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }
        }

        int pixel_percent = detail * detail / 100;

        double[] temp;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail;

        int x, y, loc;

        int condition = detail * detail;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        double height;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                temp = iteration_algorithm.calculate3D(location.getComplex(x, y));
                height = temp[0];
                escaped[loc] = iteration_algorithm.escaped();
                color = getFinalColor(temp[1], escaped[loc]);
                image_iterations[loc] = temp[1];

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int k = 0; k < supersampling_num; k++) {
                    temp = iteration_algorithm.calculate3D(location.getAntialiasingComplex(k));
                    color = getFinalColor(temp[1], iteration_algorithm.escaped());

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

    private void drawIterationsDomain3DAntialiased(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, detail, circle_period, rotation_center, rotation_vals, fractal, polar, false);
        location.createAntialiasingSteps();

        int pixel_percent = detail * detail / 100;

        int w2 = image_size / 2;

        double dx = image_size / (double) detail;

        int x, y, loc;

        int condition = detail * detail;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int red, green, blue, color;

        double height;

        do {

            loc = THREAD_CHUNK_SIZE * normal_drawing_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < THREAD_CHUNK_SIZE && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                Complex a = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                double norm = a.norm();

                height = calaculateDomainColoringHeight(norm);
                color = domain_color.getDomainColor(a);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int k = 0; k < supersampling_num; k++) {
                    a = iteration_algorithm.calculateDomain(location.getAntialiasingComplex(k));

                    color = domain_color.getDomainColor(a);
                    height += calaculateDomainColoringHeight(a.norm());

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                vert[x][y][1] = (float) (height / temp_samples);
                vert[x][y][0] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                image_iterations[loc] = scaleDomainHeight(norm);

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

    private void drawIterationsDomainAntialiased(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, image_size, circle_period, rotation_center, rotation_vals, fractal, polar, false);
        location.createAntialiasingSteps();

        int pixel_percent = (image_size * image_size) / 100;

        //better Brute force with antialiasing
        int x, y, loc;
        int color;

        int red, green, blue;

        int condition = image_size * image_size;

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

                Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                image_iterations[loc] = scaleDomainHeight(val.norm());
                color = domain_color.getDomainColor(val);

                if (domain_image_data != null) {
                    domain_image_data[loc] = val;
                }

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    color = domain_color.getDomainColor(iteration_algorithm.calculateDomain(location.getAntialiasingComplex(i)));

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

        postProcess(image_size);

    }

    protected abstract void drawIterationsAntialiased(int image_size, boolean polar);

    private void fastJuliaDraw() {

        int image_size = image.getHeight();

        if (fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
            drawFastJuliaAntialiased(image_size, false);
        } else {
            drawFastJulia(image_size, false);
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
            drawFastJuliaAntialiased(image_size, true);
        } else {
            drawFastJulia(image_size, true);
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

    private int contourColoring(double[] image_iterations, int i, int j, int image_size, int color, boolean[] escaped) {

        int loc = i * image_size + j;
        if ((!ots.useTraps || (ots.useTraps && !ots.trapIncludeNotEscaped)) && !usesTrueColorIn && Math.abs(image_iterations[loc]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getStandardColor(image_iterations[loc], escaped[loc]);
        }

        double res = ColorAlgorithm.transformResultToHeight(image_iterations[loc], max_iterations);

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        if (cns.contour_algorithm == 0) {
            res = res - (int) res;

            double min_contour = cns.min_contour;
            double max_contour = 1 - min_contour;

            if (res < min_contour || res > max_contour) {
                double coef = 0;
                if (res < min_contour) {
                    coef = (res / min_contour) / 2 + 0.5;
                } else {
                    coef = ((res - max_contour) / min_contour) / 2;
                }

                int color1 = getModifiedColor(r, g, b, max_contour, cns.contourColorMethod, 1 - cns.cn_blending, false);
                int color2 = getModifiedColor(r, g, b, min_contour, cns.contourColorMethod, 1 - cns.cn_blending, false);

                int temp_red1 = (color1 >> 16) & 0xff;
                int temp_green1 = (color1 >> 8) & 0xff;
                int temp_blue1 = color1 & 0xff;

                int temp_red2 = (color2 >> 16) & 0xff;
                int temp_green2 = (color2 >> 8) & 0xff;
                int temp_blue2 = color2 & 0xff;

                return method.interpolate(temp_red1, temp_green1, temp_blue1, temp_red2, temp_green2, temp_blue2, coef);
            }

            return getModifiedColor(r, g, b, res, cns.contourColorMethod, 1 - cns.cn_blending, false);
        } else if (cns.contour_algorithm == 1) {

            res = 2 * (res - (int) res);

            res = res > 1 ? 2.0 - res : res;

            res = Math.abs(res);

            return getModifiedColor(r, g, b, res, cns.contourColorMethod, 1 - cns.cn_blending, false);
        } else {
            res = res - (int) res;

            double min_contour = cns.min_contour;
            double max_contour = 1 - min_contour;

            if (cns.contour_algorithm == 3) {
                color = getStandardColor((int) image_iterations[loc], escaped[loc]);
                r = (color >> 16) & 0xFF;
                g = (color >> 8) & 0xFF;
                b = color & 0xFF;
            }

            if (res < min_contour || res > max_contour) {
                double coef = 0;
                if (res < min_contour) {
                    coef = (res / min_contour) / 2 + 0.5;
                } else {
                    coef = ((res - max_contour) / min_contour) / 2;
                }

                coef = 2 * coef;

                coef = 1 - (coef > 1 ? 2.0 - coef : coef);

                int color1 = getModifiedColor(r, g, b, min_contour, cns.contourColorMethod, 1 - cns.cn_blending, false);

                int temp_red1 = (color1 >> 16) & 0xff;
                int temp_green1 = (color1 >> 8) & 0xff;
                int temp_blue1 = color1 & 0xff;

                return method.interpolate(temp_red1, temp_green1, temp_blue1, r, g, b, coef);
            }

            return color;
        }

    }

    private int offsetColoring(double[] image_iterations, int i, int j, int image_size, int color, boolean[] escaped) {

        int loc = i * image_size + j;
        if ((!ots.useTraps || (ots.useTraps && !ots.trapIncludeNotEscaped)) && !usesTrueColorIn && Math.abs(image_iterations[loc]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getStandardColor(image_iterations[loc], escaped[loc]);
        }

        double coef = 1 - ofs.of_blending;

        double res = ColorAlgorithm.transformResultToHeight(image_iterations[loc], max_iterations);

        int color2 = getStandardColor(res + ofs.post_process_offset, escaped[loc]);

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int fc_red = (color2 >> 16) & 0xFF;
        int fc_green = (color2 >> 8) & 0xFF;
        int fc_blue = color2 & 0xFF;

        return blending.blend(r, g, b, fc_red, fc_green, fc_blue, coef);
    }

    private int greyscaleColoring(double[] image_iterations, int i, int j, int image_size, int color, boolean[] escaped) {

        int loc = i * image_size + j;
        if ((!ots.useTraps || (ots.useTraps && !ots.trapIncludeNotEscaped)) && !usesTrueColorIn && Math.abs(image_iterations[loc]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getStandardColor(image_iterations[loc], escaped[loc]);
        }

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int greyscale = (int) ((r + g + b) / 3.0 + 0.5);

        return 0xff000000 | (greyscale << 16) | (greyscale << 8) | greyscale;

    }

    private int entropyColoring(double[] image_iterations, int i, int j, int image_size, int color, boolean[] escaped) {

        int loc = i * image_size + j;
        if ((!ots.useTraps || (ots.useTraps && !ots.trapIncludeNotEscaped)) && !usesTrueColorIn && Math.abs(image_iterations[loc]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getStandardColor(image_iterations[loc], escaped[loc]);
        }

        int kernel_size = 3;
        int kernel_size2 = kernel_size / 2;

        double min_value = Double.MAX_VALUE;

        double[] values = new double[kernel_size * kernel_size];
        int length = 0;

        for (int k = i - kernel_size2, p = 0; p < kernel_size; k++, p++) {
            for (int l = j - kernel_size2, t = 0; t < kernel_size; l++, t++) {

                if (k >= 0 && k < image_size && l >= 0 && l < image_size) {

                    double temp = ColorAlgorithm.transformResultToHeight(image_iterations[k * image_size + l], max_iterations);

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

        double coef = 1 - ens.en_blending;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int temp_red = 0, temp_green = 0, temp_blue = 0;

        if (ens.entropy_algorithm == 0) {
            double res = ColorAlgorithm.transformResultToHeight(image_iterations[loc], max_iterations);

            int color2 = getStandardColor(ens.entropy_offset + res + ens.entropy_palette_factor * Math.abs(sum2), escaped[loc]);

            temp_red = (color2 >> 16) & 0xFF;
            temp_green = (color2 >> 8) & 0xFF;
            temp_blue = color2 & 0xFF;
        } else {
            double temp = Math.abs(sum2) * ens.entropy_palette_factor;

            if (temp > 1) {
                temp = (int) temp % 2 == 1 ? 1 - (temp - (int) temp) : (temp - (int) temp);
            }

            int index = (int) (temp * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            temp_red = (grad_color >> 16) & 0xff;
            temp_green = (grad_color >> 8) & 0xff;
            temp_blue = grad_color & 0xff;
        }

        return blending.blend(r, g, b, temp_red, temp_green, temp_blue, coef);
    }

    private int paletteRainbow(double[] image_iterations, int i, int j, int image_size, int color, boolean[] escaped) {

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

        if ((!ots.useTraps || (ots.useTraps && !ots.trapIncludeNotEscaped)) && !usesTrueColorIn && Math.abs(image_iterations[k0]) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return getStandardColor(image_iterations[k0], escaped[k0]);
        }

        double n0 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

        if (i < image_size - 1) {
            double nx = ColorAlgorithm.transformResultToHeight(image_iterations[kx], max_iterations);
            zx = sx * (nx - n0);
        }

        if (j < image_size - 1) {
            double ny = ColorAlgorithm.transformResultToHeight(image_iterations[ky], max_iterations);
            zy = sy * (ny - n0);
        }

        if (i > 0) {
            double nx2 = ColorAlgorithm.transformResultToHeight(image_iterations[kx2], max_iterations);
            zx2 = sx2 * (nx2 - n0);
        }

        if (j > 0) {
            double ny2 = ColorAlgorithm.transformResultToHeight(image_iterations[ky2], max_iterations);
            zy2 = sy2 * (ny2 - n0);
        }

        double zz = 1.0;

        double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);
        //zz /= z;
        zx /= z;
        zy /= z;

        double hue = Math.atan2(zy, zx) / Math.PI * 0.5;

        hue = hue < 0 ? hue + 1 : hue;

        double coef = 1 - rps.rp_blending;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int temp_red = 0, temp_green = 0, temp_blue = 0;

        if (rps.rainbow_algorithm == 0) {

            int paletteLength = (!escaped[k0] && usePaletteForInColoring) ? palette_incoloring.getPaletteLength() : palette_outcoloring.getPaletteLength();

            int color2 = getStandardColor(rps.rainbow_offset + hue * paletteLength * rps.rainbow_palette_factor, escaped[k0]);

            temp_red = (color2 >> 16) & 0xFF;
            temp_green = (color2 >> 8) & 0xFF;
            temp_blue = color2 & 0xFF;
        } else {
            hue *= 2 * rps.rainbow_palette_factor;

            hue = hue % 2.0;

            int index = hue < 1 ? (int) (hue * (gradient.length - 1) + 0.5) : (int) ((1 - (hue - 1)) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            temp_red = (grad_color >> 16) & 0xff;
            temp_green = (grad_color >> 8) & 0xff;
            temp_blue = grad_color & 0xff;
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

        double n0 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

        if (i < image_size - 1) {
            double nx = ColorAlgorithm.transformResultToHeight(image_iterations[kx], max_iterations);
            zx = sx * (nx - n0);
        }

        if (j < image_size - 1) {
            double ny = ColorAlgorithm.transformResultToHeight(image_iterations[ky], max_iterations);
            zy = sy * (ny - n0);
        }

        if (i > 0) {
            double nx2 = ColorAlgorithm.transformResultToHeight(image_iterations[kx2], max_iterations);
            zx2 = sx2 * (nx2 - n0);
        }

        if (j > 0) {
            double ny2 = ColorAlgorithm.transformResultToHeight(image_iterations[ky2], max_iterations);
            zy2 = sy2 * (ny2 - n0);
        }

        double zz = 1 / factor;

        double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);

        zz /= z;

        double coef = zz;
        coef = 1 - coef;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int fc_red = (new_color >> 16) & 0xFF;
        int fc_green = (new_color >> 8) & 0xFF;
        int fc_blue = new_color & 0xFF;

        return method.interpolate(fc_red, fc_green, fc_blue, r, g, b, coef);

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

        double n0 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

        if (i < image_size - 1) {
            double nx = ColorAlgorithm.transformResultToHeight(image_iterations[kx], max_iterations);
            zx = sx * (nx - n0);
        }

        if (j < image_size - 1) {
            double ny = ColorAlgorithm.transformResultToHeight(image_iterations[ky], max_iterations);
            zy = sy * (ny - n0);
        }

        if (i > 0) {
            double nx2 = ColorAlgorithm.transformResultToHeight(image_iterations[kx2], max_iterations);
            zx2 = sx2 * (nx2 - n0);
        }

        if (j > 0) {
            double ny2 = ColorAlgorithm.transformResultToHeight(image_iterations[ky2], max_iterations);
            zy2 = sy2 * (ny2 - n0);
        }

        double zz = 1 / fdes.fake_de_factor;

        double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);

        zz /= z;

        double coef = zz;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int fc_red = (dem_color >> 16) & 0xFF;
        int fc_green = (dem_color >> 8) & 0xFF;
        int fc_blue = dem_color & 0xFF;

        if (fdes.inverse_fake_dem) {
            coef = 1 - coef;
        }

        return method.interpolate(fc_red, fc_green, fc_blue, r, g, b, coef);

    }

    protected void applyPostProcessing(int image_size, double[] image_iterations, boolean[] escaped) {

        if (hss.histogramColoring && !domain_coloring) {
            histogramColoringIterations(image_size, image_iterations, escaped);
        }

        double gradCorr, sizeCorr = 0, lightAngleRadians, lightx = 0, lighty = 0;

        if (bms.bump_map) {
            gradCorr = Math.pow(2, (bms.bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = image_size / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bms.bumpMappingDepth) * 0.16);
            lightAngleRadians = Math.toRadians(bms.lightDirectionDegrees);
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

                for (int i = 0; i < post_processing_order.length; i++) {
                    switch (post_processing_order[i]) {

                        case MainWindow.LIGHT:
                            if (ls.lighting) {
                                int original_color = modified;
                                modified = light(image_iterations, original_color, y, x, image_size);
                                modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, ls.l_noise_reducing_factor);
                            }
                            break;
                        case MainWindow.OFFSET_COLORING:
                            if (ofs.offset_coloring && !domain_coloring) {
                                int original_color = modified;
                                modified = offsetColoring(image_iterations, y, x, image_size, original_color, escaped);
                                modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, ofs.of_noise_reducing_factor);
                            }
                            break;
                        case MainWindow.ENTROPY_COLORING:
                            if (ens.entropy_coloring && !domain_coloring) {
                                int original_color = modified;
                                modified = entropyColoring(image_iterations, y, x, image_size, original_color, escaped);
                                modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, ens.en_noise_reducing_factor);
                            }
                            break;
                        case MainWindow.RAINBOW_PALETTE:
                            if (rps.rainbow_palette && !domain_coloring) {
                                int original_color = modified;
                                modified = paletteRainbow(image_iterations, y, x, image_size, original_color, escaped);
                                modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, rps.rp_noise_reducing_factor);
                            }
                            break;
                        case MainWindow.CONTOUR_COLORING:
                            if (cns.contour_coloring && !domain_coloring) {
                                int original_color = modified;
                                modified = contourColoring(image_iterations, y, x, image_size, original_color, escaped);
                                modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, cns.cn_noise_reducing_factor);
                            }
                            break;
                        case MainWindow.GREYSCALE_COLORING:
                            if (gss.greyscale_coloring && !domain_coloring) {
                                int original_color = modified;
                                modified = greyscaleColoring(image_iterations, y, x, image_size, original_color, escaped);
                                modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, gss.gs_noise_reducing_factor);
                            }
                            break;
                        case MainWindow.BUMP_MAPPING:
                            if (bms.bump_map) {
                                gradx = getGradientX(image_iterations, index, image_size);
                                grady = getGradientY(image_iterations, index, image_size);

                                dotp = gradx * lightx + grady * lighty;

                                int original_color = modified;

                                if (bms.bumpProcessing == 3 || bms.bumpProcessing == 4 || bms.bumpProcessing == 5) {
                                    if (dotp != 0) {
                                        gradAbs = Math.sqrt(gradx * gradx + grady * grady);
                                        cosAngle = dotp / gradAbs;
                                        smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;

                                        modified = changeBrightnessOfColorLabHsbHsl(modified, cosAngle * smoothGrad);
                                    }
                                } else if (bms.bumpProcessing == 0) {
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

                                modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, bms.bm_noise_reducing_factor);
                            }
                            break;
                        case MainWindow.FAKE_DISTANCE_ESTIMATION:
                            if (fdes.fake_de && !domain_coloring) {
                                modified = pseudoDistanceEstimation(image_iterations, modified, y, x, image_size);
                            }
                            break;
                    }
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

    protected abstract void drawFastJulia(int image_size, boolean polar);

    protected abstract void drawFastJuliaAntialiased(int image_size, boolean polar);

    private void colorCycling() {

        do {
            color_cycling_toggle_lock.readLock().lock();

            boolean cached_color_cycling = color_cycling;

            try {
                color_cycling_restart_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            color_cycling_toggle_lock.readLock().unlock();

            if (!cached_color_cycling) {
                return;
            }

            ptr.setWholeImageDone(false);

            int image_size = image.getHeight();

            if (cycle_gradient) {
                gradient_offset++;
                gradient_offset = gradient_offset > Integer.MAX_VALUE - 1 ? 0 : gradient_offset;
            }

            if (cycle_colors) {
                color_cycling_location_outcoloring++;

                color_cycling_location_outcoloring = color_cycling_location_outcoloring > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location_outcoloring;

                color_cycling_location_incoloring++;

                color_cycling_location_incoloring = color_cycling_location_incoloring > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location_incoloring;
            }

            if (cycle_lights) {
                if (bms.bump_map) {
                    bms.lightDirectionDegrees++;
                    if (bms.lightDirectionDegrees == 360) {
                        bms.lightDirectionDegrees = 0;
                    }
                }

                if (ls.lighting) {
                    ls.light_direction++;
                    if (ls.light_direction == 360) {
                        ls.light_direction = 0;
                    }

                    double lightAngleRadians = Math.toRadians(ls.light_direction);
                    ls.lightVector[0] = Math.cos(lightAngleRadians) * ls.light_magnitude;
                    ls.lightVector[1] = Math.sin(lightAngleRadians) * ls.light_magnitude;
                }
            }

            for (int y = FROMy; y < TOy; y++) {
                for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                    if (domain_coloring) {
                        domain_color.setColorCyclingLocation(color_cycling_location_outcoloring);
                        domain_color.setGradientOffset(gradient_offset);
                        rgbs[loc] = domain_color.getDomainColor(domain_image_data[loc]);
                    } else {
                        rgbs[loc] = getStandardColor(image_iterations[loc], escaped[loc]);
                    }
                }
            }

            postProcess(image_size);

            try {
                if (color_cycling_filters_sync.await() == 0) {
                    applyFiltersNoProgress();

                    ptr.setWholeImageDone(true);

                    ptr.getMainPanel().repaint();

                    if (cycle_colors) {
                        ptr.updatePalettePreview(color_cycling_location_outcoloring, color_cycling_location_incoloring);
                    }

                    if (cycle_gradient) {
                        ptr.updateGradientPreview(gradient_offset);
                    }
                    //progress.setForeground(new Color(palette.getPaletteColor(color_cycling_location)));
                }
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            try {
                sleep(color_cycling_speed + 35);
            } catch (InterruptedException ex) {
            }

            try {
                color_cycling_restart_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

        } while (true);

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
            progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getNumberOfThreads() + "</html>");
        }

    }

    private void changePalette(int image_size) {

        int pixel_percent = (image_size * image_size) / 100;

        for (int y = FROMy; y < TOy; y++) {
            for (int x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                if (domain_coloring) {
                    rgbs[loc] = domain_color.getDomainColor(domain_image_data[loc]);
                } else {
                    rgbs[loc] = getStandardColor(image_iterations[loc], escaped[loc]);
                }

                drawing_done++;
            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        if ((hss.histogramColoring || ls.lighting || bms.bump_map || fdes.fake_de || rps.rainbow_palette || ens.entropy_coloring || ofs.offset_coloring || gss.greyscale_coloring || cns.contour_coloring) && !USE_DIRECT_COLOR) {
            try {
                post_processing_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size, image_iterations, escaped);
        }

    }

    private void drawJuliaMap() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            juliaMapAntialiased(image_size , false);
        } else {
            juliaMap(image_size, false);
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
            progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getJuliaMapSlices() + "</html>");
        }

    }

    private void drawJuliaMapPolar() {

        int image_size = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            juliaMapAntialiased(image_size, true);
        } else {
            juliaMap(image_size, true);
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
            progress.setValue((image_size * image_size) + ((image_size * image_size) / 100));
            progress.setToolTipText("<html>Elapsed Time: " + (System.currentTimeMillis() - ptr.getCalculationTime()) + " ms<br>Threads used: " + ptr.getJuliaMapSlices() + "</html>");
        }

    }

    private void juliaMap(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, TOx - FROMx, circle_period, rotation_center, rotation_vals, fractal, polar, false);

        int pixel_percent = (image_size * image_size) / 100;

        for (int y = FROMy, y2 = 0; y < TOy; y++, y2++) {
            for (int x = FROMx, x2 = 0, loc = y * image_size + x; x < TOx; x++, loc++, x2++) {

                image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x2, y2));
                escaped[loc] = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(image_iterations[loc], escaped[loc]);

                drawing_done++;

            }

            if (drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        postProcess(image_size);

    }

    private void juliaMapAntialiased(int image_size, boolean polar) {

        Location location = Location.getInstanceForDrawing(xCenter, yCenter, size, height_ratio, TOx - FROMx, circle_period, rotation_center, rotation_vals, fractal, polar, false);
        location.createAntialiasingSteps();

        int pixel_percent = (image_size * image_size) / 100;


        double temp_result;

        int color;

        int red, green, blue;

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for (int y = FROMy, y2 = 0; y < TOy; y++, y2++) {
            for (int x = FROMx, x2 = 0, loc = y * image_size + x; x < TOx; x++, loc++, x2++) {

                image_iterations[loc] = iteration_algorithm.calculate(location.getComplex(x2, y2));
                escaped[loc] = iteration_algorithm.escaped();
                color = getFinalColor(image_iterations[loc], escaped[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i));
                    color = getFinalColor(temp_result, iteration_algorithm.escaped());

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

        double min = -100;
        double range = max_scaling * d3_height_scale;

        for (int x = FROMx; x < TOx; x++) {
            for (int y = FROMy; y < TOy; y++) {
                int r = ((int) vert[x][y][0] >> 16) & 0xff;
                int g = ((int) vert[x][y][0] >> 8) & 0xff;
                int b = (int) vert[x][y][0] & 0xff;

                double coef = 0;

                switch (shade_algorithm) {
                    case 0: //lerp
                        coef = ((vert[x][y][1] - min) / range - 0.5) * 2;
                        break;
                    case 1://cos lerp
                        coef = -Math.cos((vert[x][y][1] - min) / range * Math.PI);
                        break;
                    case 2:
                        double lim = 0.1;
                        if ((vert[x][y][1] - min) / range <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / range * (1 / lim));
                        } else if ((vert[x][y][1] - min) / range >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / range) * (1 / lim);
                        } else {
                            coef = 0;
                        }
                        break;
                    case 3:
                        lim = 0.2;
                        if ((vert[x][y][1] - min) / range <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / range * (1 / lim));
                        } else if ((vert[x][y][1] - min) / range >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / range) * (1 / lim);
                        } else {
                            coef = 0;
                        }
                        break;
                    case 4:
                        lim = 0.3;
                        if ((vert[x][y][1] - min) / range <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / range * (1 / lim));
                        } else if ((vert[x][y][1] - min) / range >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / range) * (1 / lim);
                        } else {
                            coef = 0;
                        }
                        break;
                    case 5:
                        lim = 0.4;
                        if ((vert[x][y][1] - min) / range <= lim) {
                            coef = -(1 - (vert[x][y][1] - min) / range * (1 / lim));
                        } else if ((vert[x][y][1] - min) / range >= (1 - lim)) {
                            coef = 1 - (1 - (vert[x][y][1] - min) / range) * (1 / lim);
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
                    r = (int) (col * Math.abs(coef) + r * (1 - Math.abs(coef)) + 0.5);
                    g = (int) (col * Math.abs(coef) + g * (1 - Math.abs(coef)) + 0.5);
                    b = (int) (col * Math.abs(coef) + b * (1 - Math.abs(coef)) + 0.5);
                } else {
                    r = (int) (col2 * coef + r * (1 - coef) + 0.5);
                    g = (int) (col2 * coef + g * (1 - coef) + 0.5);
                    b = (int) (col2 * coef + b * (1 - coef) + 0.5);
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
        double it = ColorAlgorithm.transformResultToHeight(image_iterations[index], max_iterations);

        if (x == 0) {
            return (ColorAlgorithm.transformResultToHeight(image_iterations[index + 1], max_iterations) - it) * 2;
        } else if (x == size - 1) {
            return (it - ColorAlgorithm.transformResultToHeight(image_iterations[index - 1], max_iterations)) * 2;
        } else {
            double diffL = it - ColorAlgorithm.transformResultToHeight(image_iterations[index - 1], max_iterations);
            double diffR = it - ColorAlgorithm.transformResultToHeight(image_iterations[index + 1], max_iterations);
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }

    }

    private double getGradientY(double[] image_iterations, int index, int size) {

        int y = index / size;
        double it = ColorAlgorithm.transformResultToHeight(image_iterations[index], max_iterations);

        if (y == 0) {
            return (it - ColorAlgorithm.transformResultToHeight(image_iterations[index + size], max_iterations)) * 2;
        } else if (y == size - 1) {
            return (ColorAlgorithm.transformResultToHeight(image_iterations[index - size], max_iterations) - it) * 2;
        } else {
            double diffU = it - ColorAlgorithm.transformResultToHeight(image_iterations[index - size], max_iterations);
            double diffD = it - ColorAlgorithm.transformResultToHeight(image_iterations[index + size], max_iterations);
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }

    }

    private double getBumpCoef(double delta) {
        double mul = 0;

        switch (bms.bump_transfer_function) {
            case 0:
                mul = (1.5 / (Math.abs(delta * bms.bump_transfer_factor) + 1.5));
                break;
            case 1:
                mul = 1 / Math.sqrt(Math.abs(delta * bms.bump_transfer_factor) + 1);
                break;
            case 2:
                mul = 1 / Math.cbrt(Math.abs(delta * bms.bump_transfer_factor) + 1);
                break;
            case 3:
                mul = Math.pow(2, -Math.abs(delta * bms.bump_transfer_factor));
                break;
            //case 4:
            //mul = (Math.atan(-Math.abs(delta * bump_transfer_factor))*0.63662+1);
            //break;
        }

        return mul;
    }

    private int changeBrightnessOfColorLabHsbHsl(int rgb, double delta) {

        double mul = getBumpCoef(delta);

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        if (delta > 0) {
            mul = (2 - mul) / 2;
        } else {
            mul = mul / 2;
        }

        if (bms.bumpProcessing == 3) {
            double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
            double val = contourFactor * mul * res[0];
            val = val > 100 ? 100 : val;
            int[] rgb2 = ColorSpaceConverter.LABtoRGB(val, res[1], res[2]);
            return 0xff000000 | (rgb2[0] << 16) | (rgb2[1] << 8) | rgb2[2];
        } else if (bms.bumpProcessing == 4) {
            double[] res = ColorSpaceConverter.RGBtoHSB(r, g, b);
            double val = contourFactor * mul * res[2];
            val = val > 1 ? 1 : val;
            int[] rgb2 = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb2[0] << 16) | (rgb2[1] << 8) | rgb2[2];
        } else {
            double[] res = ColorSpaceConverter.RGBtoHSL(r, g, b);
            double val = contourFactor * mul * res[2];
            val = val > 1 ? 1 : val;
            int[] rgb2 = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb2[0] << 16) | (rgb2[1] << 8) | rgb2[2];
        }
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
            int index = bms.bumpProcessing == 1 ? (int) ((mul / 2) * (gradient.length - 1) + 0.5) : (int) ((1 - mul) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            temp_red = (grad_color >> 16) & 0xff;
            temp_green = (grad_color >> 8) & 0xff;
            temp_blue = grad_color & 0xff;
        } else {
            int index = bms.bumpProcessing == 1 ? (int) (((2 - mul) / 2) * (gradient.length - 1) + 0.5) : (int) ((1 - mul) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            temp_red = (grad_color >> 16) & 0xff;
            temp_green = (grad_color >> 8) & 0xff;
            temp_blue = grad_color & 0xff;
        }

        int old_red = (rgbIn >> 16) & 0xFF;
        int old_green = (rgbIn >> 8) & 0xFF;
        int old_blue = rgbIn & 0xFF;

        return blending.blend(temp_red, temp_green, temp_blue, old_red, old_green, old_blue, 1 - bms.bump_blending);

    }

    public static void terminateColorCycling() {

        color_cycling_toggle_lock.writeLock().lock();
        color_cycling = false;
        color_cycling_toggle_lock.writeLock().unlock();

    }

    public static void initializeColorCycling() {

        color_cycling_toggle_lock.writeLock().lock();
        color_cycling = true;
        color_cycling_toggle_lock.writeLock().unlock();

    }

    public BumpMapSettings getBumpMapSettings() {
        return bms;
    }

    public LightSettings getLightSettings() {
        return ls;
    }

    public int getColorCyclingLocationOutColoring() {

        return color_cycling_location_outcoloring;

    }

    public int getColorCyclingLocationInColoring() {

        return color_cycling_location_incoloring;

    }

    public int getGradientOffset() {

        return gradient_offset;

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
                return Math.log(x + 1);
            case 1:
                return Math.log(Math.log(x + 1) + 1);
            case 2:
                return 1 / (x + 1);
            case 3:
                return Math.exp(-x + 5);
            case 4:
                return 150 - Math.exp(-x + 5);
            case 5:
                return 150 / (1 + Math.exp(-3 * x + 3));
            case 6:
                return 1 / (Math.log(x + 1) + 1);

        }

        return 0;
    }

    private void findMinMaxHeight() {

        min = Double.MAX_VALUE;
        max = -Double.MIN_VALUE;

        for (int x = 0; x < detail; x++) {
            for (int y = 0; y < detail; y++) {
                if (Double.isNaN(vert[x][y][1]) || Double.isInfinite(vert[x][y][1])) {
                    continue;
                }

                if (vert[x][y][1] < min) {
                    min = vert[x][y][1];
                }

                if (vert[x][y][1] > max) {
                    max = vert[x][y][1];
                }
            }
        }

    }

    private void applyHeightFunction() {

        for (int x = FROMx; x < TOx; x++) {
            for (int y = FROMy; y < TOy; y++) {
                vert[x][y][1] = (float) calculateHeight(vert[x][y][1]);
            }
        }
    }

    private void applyPostHeightScaling() {

        double local_max = max - (max - min) * (1 - max_range / 100.0);

        double local_min = min + (max - min) * (min_range / 100.0);

        local_min = local_min > local_max ? local_max : local_min;

        double new_max = local_max - local_min;

        for (int x = FROMx; x < TOx; x++) {
            for (int y = FROMy; y < TOy; y++) {
                float val = vert[x][y][1];
                if (val <= local_max && val >= local_min) {
                    val -= local_min;
                    vert[x][y][1] = (float) (val * (max_scaling / new_max));
                } else if (val > local_max) {
                    vert[x][y][1] = (float) (max_scaling);
                } else if (!Double.isNaN(val) && !Double.isInfinite(val)) {
                    vert[x][y][1] = 0;
                }

                vert[x][y][1] = (float) (d3_height_scale * vert[x][y][1] - 100);
            }
        }

    }

    private void applyPreHeightScaling() {

        double local_max = max - (max - min) * (1 - max_range / 100.0);

        double local_min = min + (max - min) * (min_range / 100.0);

        local_min = local_min > local_max ? local_max : local_min;

        double new_max = local_max - local_min;

        for (int x = FROMx; x < TOx; x++) {
            for (int y = FROMy; y < TOy; y++) {
                float val = vert[x][y][1];
                if (val <= local_max && val >= local_min) {
                    val -= local_min;
                    vert[x][y][1] = (float) (val * (max_scaling / new_max));
                } else if (val > local_max) {
                    vert[x][y][1] = (float) (max_scaling);
                } else if (!Double.isNaN(val) && !Double.isInfinite(val)) {
                    vert[x][y][1] = 0;
                }
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

        if (height_algorithm > 2) {

            if (height_scaling_sync3.incrementAndGet() == ptr.getNumberOfThreads()) {

                findMinMaxHeight();

            }

            try {
                height_scaling_sync4.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            applyPreHeightScaling();
        }

        try {
            height_function_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        applyHeightFunction();

        if (histogramHeight) {
            histogramHeight();
        }

        if (height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            findMinMaxHeight();

        }

        try {
            height_scaling_sync2.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        applyPostHeightScaling();

        if (shade_height) {
            try {
                shade_color_height_sync.await();
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
                    mod = Math.sqrt(norm_0_0 * norm_0_0 + norm_0_1 * norm_0_1 + norm_0_2 * norm_0_2);
                    norm_0_0 /= mod;
                    norm_0_1 /= mod;
                    norm_0_2 /= mod;

                    norm_1_0 = vert[x][y + 1][1] - vert[x + 1][y + 1][1];
                    norm_1_1 = dx;
                    norm_1_2 = vert[x][y][1] - vert[x][y + 1][1];
                    mod = Math.sqrt(norm_1_0 * norm_1_0 + norm_1_1 * norm_1_1 + norm_1_2 * norm_1_2);
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

        int[] xPol = new int[3];
        int[] yPol = new int[3];

        Graphics2D g = image.createGraphics();

        int ib = 0, ie = detail, sti = 1, jb = 0, je = detail, stj = 1;

        if (m20 < 0) {
            ib = detail - 1;
            ie = -1;
            sti = -1;
        }

        if (m22 < 0) {
            jb = detail - 1;
            je = -1;
            stj = -1;
        }

        int old_max = progress.getMaximum();
        int cur_val = progress.getValue();

        if (updateProgress) {
            progress.setMaximum(detail * detail);
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

                    g.setColor(new Color(getModifiedColor(red, green, blue, Norm1z[i][j][0], d3_color_type, color_3d_blending, false)));

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

                    g.setColor(new Color(getModifiedColor(red, green, blue, Norm1z[i][j][1], d3_color_type, color_3d_blending, false)));

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
        if ((hss.histogramColoring || ls.lighting || bms.bump_map || fdes.fake_de || rps.rainbow_palette || ens.entropy_coloring || ofs.offset_coloring || gss.greyscale_coloring || cns.contour_coloring) && !USE_DIRECT_COLOR) {
            try {
                post_processing_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size, image_iterations_fast_julia, escaped_fast_julia);
        }
    }

    protected void postProcess(int image_size) {
        if ((hss.histogramColoring || ls.lighting || bms.bump_map || fdes.fake_de || rps.rainbow_palette || ens.entropy_coloring || ofs.offset_coloring || gss.greyscale_coloring || cns.contour_coloring) && !USE_DIRECT_COLOR) {
            try {
                post_processing_sync.await();
            } catch (InterruptedException ex) {

            } catch (BrokenBarrierException ex) {

            }

            applyPostProcessing(image_size, image_iterations, escaped);
        }
    }

    private void blendingFactory(int interpolation) {

        switch (color_blending) {
            case MainWindow.NORMAL_BLENDING:
                blending = new NormalBlending(interpolation);
                break;
            case MainWindow.MULTIPLY_BLENDING:
                blending = new MultiplyBlending(interpolation);
                break;
            case MainWindow.DIVIDE_BLENDING:
                blending = new DivideBlending(interpolation);
                break;
            case MainWindow.ADDITION_BLENDING:
                blending = new AdditionBlending(interpolation);
                break;
            case MainWindow.SUBTRACTION_BLENDING:
                blending = new SubtractionBlending(interpolation);
                break;
            case MainWindow.DIFFERENCE_BLENDING:
                blending = new DifferenceBlending(interpolation);
                break;
            case MainWindow.VALUE_BLENDING:
                blending = new ValueBlending(interpolation);
                break;
            case MainWindow.SOFT_LIGHT_BLENDING:
                blending = new SoftLightBlending(interpolation);
                break;
            case MainWindow.SCREEN_BLENDING:
                blending = new ScreenBlending(interpolation);
                break;
            case MainWindow.DODGE_BLENDING:
                blending = new DodgeBlending(interpolation);
                break;
            case MainWindow.BURN_BLENDING:
                blending = new BurnBlending(interpolation);
                break;
            case MainWindow.DARKEN_ONLY_BLENDING:
                blending = new DarkenOnlyBlending(interpolation);
                break;
            case MainWindow.LIGHTEN_ONLY_BLENDING:
                blending = new LightenOnlyBlending(interpolation);
                break;
            case MainWindow.HARD_LIGHT_BLENDING:
                blending = new HardLightBlending(interpolation);
                break;
            case MainWindow.GRAIN_EXTRACT_BLENDING:
                blending = new GrainExtractBlending(interpolation);
                break;
            case MainWindow.GRAIN_MERGE_BLENDING:
                blending = new GrainMergeBlending(interpolation);
                break;
            case MainWindow.SATURATION_BLENDING:
                blending = new SaturationBlending(interpolation);
                break;
            case MainWindow.COLOR_BLENDING:
                blending = new ColorBlending(interpolation);
                break;
            case MainWindow.HUE_BLENDING:
                blending = new HueBlending(interpolation);
                break;
            case MainWindow.EXCLUSION_BLENDING:
                blending = new ExclusionBlending(interpolation);
                break;
            case MainWindow.PIN_LIGHT_BLENDING:
                blending = new PinLightBlending(interpolation);
                break;
            case MainWindow.LINEAR_LIGHT_BLENDING:
                blending = new LinearLightBlending(interpolation);
                break;
            case MainWindow.VIVID_LIGHT_BLENDING:
                blending = new VividLightBlending(interpolation);
                break;
            case MainWindow.OVERLAY_BLENDING:
                blending = new OverlayBlending(interpolation);
                break;
            case MainWindow.LCH_CHROMA_BLENDING:
                blending = new LCHChromaBlending(interpolation);
                break;
            case MainWindow.LCH_COLOR_BLENDING:
                blending = new LCHColorBlending(interpolation);
                break;
            case MainWindow.LCH_HUE_BLENDING:
                blending = new LCHHueBlending(interpolation);
                break;
            case MainWindow.LCH_LIGHTNESS_BLENDING:
                blending = new LCHLightnessBlending(interpolation);
                break;
            case MainWindow.LUMINANCE_BLENDING:
                blending = new LuminanceBlending(interpolation);
                break;
            case MainWindow.LINEAR_BURN_BLENDING:
                blending = new LinearBurnBlending(interpolation);
                break;

        }
    }

    private void domainColoringFactory(DomainColoringSettings ds, int interpolation) {

        this.ds = ds;

        if (ds.customDomainColoring) {
            domain_color = new CustomDomainColoring(ds, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, gradient, interpolation, gradient_offset, contourFactor);
            return;
        }

        switch (ds.domain_coloring_alg) {
            case 0:
                domain_color = new BlackGridWhiteCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 1:
                domain_color = new WhiteGridBlackCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 2:
                domain_color = new BlackGridDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 3:
                domain_color = new WhiteGridDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 4:
                domain_color = new BlackGridBrightContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 5:
                domain_color = new WhiteGridDarkContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 6:
                domain_color = new NormBlackGridWhiteCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 7:
                domain_color = new NormWhiteGridBlackCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 8:
                domain_color = new NormBlackGridDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 9:
                domain_color = new NormWhiteGridDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 10:
                domain_color = new NormBlackGridBrightContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 11:
                domain_color = new NormWhiteGridDarkContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 12:
                domain_color = new WhiteCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 13:
                domain_color = new BlackCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 14:
                domain_color = new BrightContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 15:
                domain_color = new DarkContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 16:
                domain_color = new NormWhiteCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 17:
                domain_color = new NormBlackCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 18:
                domain_color = new NormBrightContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 19:
                domain_color = new NormDarkContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 20:
                domain_color = new BlackGridContoursLog2IsoLinesDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 21:
                domain_color = new NormBlackGridContoursLog2IsoLinesDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 22:
                domain_color = new BlackGridIsoContoursDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 23:
                domain_color = new NormBlackGridIsoContoursDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 24:
                domain_color = new IsoContoursContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 25:
                domain_color = new NormIsoContoursContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 26:
                domain_color = new GridContoursIsoLinesDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
            case 27:
                domain_color = new NormGridContoursIsoLinesDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, blending, interpolation, contourFactor);
                break;
        }

        domain_color.setGradientOffset(gradient_offset);

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

    private double height_transfer(double value) {

        switch (ls.heightTransfer) {
            case 0:
                return value * ls.heightTransferFactor;
            case 1:
                return Math.sqrt(value * ls.heightTransferFactor);
            case 2:
                return value * ls.heightTransferFactor * value * ls.heightTransferFactor;
        }

        return 0;

    }

    private double _angleV = 20;
    private double _angleH = 20;
    private double _intensity = 2;
    private double _diffuse = 32;
    private double _specularIntensity = 1;
    private double _specularSize = 4;
    private double _specularDiffuse = 16;
    private double _heightMultiplier = 16;
    private double _angleMultiplier = 16;

    public int light2(double[] image_iterations, int color, int i, int j, int image_size)
    {
            int x = j;
            int y = i;

            int k0 = image_size * y + x;

            double c = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations) * _heightMultiplier;
            double tp = 0;
            double bp = 0;
            double lp = 0;
            double rp = 0;

            //int count1 = 0;
            //int count2 = 0;
            //int count3 = 0;
            //int count4 = 0;

            for (int yy = -1; yy <= 1; yy++)
            {
                for (int xx = -1; xx <= 1; xx++)
                {
                    if (y + yy >= 0 && y + yy < image_size && x + xx >= 0 && x + xx < image_size)
                    {
                        if (yy != 0 || xx != 0)
                        {
                            int kn = k0 + image_size * yy + xx;//image_size * (y + yy) + x + xx;
                            double pc = ColorAlgorithm.transformResultToHeight(image_iterations[kn], max_iterations) * _heightMultiplier;

                            if (yy < 0) {
                                tp += pc;
                                //count1++;
                            }
                            if (yy > 0) {
                                bp += pc;
                                //count2++;
                            }
                            if (xx < 0) {
                                lp += pc;
                                //count3++;
                            }
                            if (xx > 0) {
                                rp += pc;
                                //count4++;
                            }
                        }
                    }
                }
            }

            //3
            tp /= 3;
            bp /= 3;
            lp /= 3;
            rp /= 3;

        //va = Math.toDegrees(va) * _angleMultiplier;

        //ha = Math.toDegrees(ha) * _angleMultiplier;

        double va = Math.atan2(((c - tp) + (bp - c)) / 2, 1) * _angleMultiplier;
        va = Math.min(90, Math.max(-90, va));
        double ha = Math.atan2(((c - lp) + (rp - c)) / 2, 1) * _angleMultiplier;
        ha = Math.min(90, Math.max(-90, ha));
        double vd = Math.abs(_angleV - va);
        double hd = Math.abs(_angleH - ha);

        double distance = Math.sqrt(vd * vd + hd * hd);
        double light = _intensity - Math.max(0, distance / _diffuse);

        double specDistance = Math.max(0, distance - _specularSize);
        double spec = 255 * Math.max(0, _specularIntensity  - Math.max(0, specDistance / _specularDiffuse));

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        return 0xff000000 | ((int)Math.max(0, Math.min(255, r * light + spec)) << 16) | ((int)Math.max(0, Math.min(255, g * light +  spec)) << 8) | (int)Math.max(0, Math.min(255, b * light + spec));

        /*double coef = light;
        double coef2 = spec;

        if (ls.colorMode == 0) { //Lab
            double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
            int[] rgb = ColorSpaceConverter.LABtoRGB(res[0] * coef + coef2 * 100, res[1], res[2]);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (ls.colorMode == 1) { //HSB
            double[] res = ColorSpaceConverter.RGBtoHSB(r, g, b);

            double val = res[2] * coef + coef2;

            if (val > 1) {
                val = 1;
            }
            if (val < 0) {
                val = 0;
            }

            int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (ls.colorMode == 2) { //HSL
            double[] res = ColorSpaceConverter.RGBtoHSL(r, g, b);

            double val = res[2] * coef + coef2;

            if (val > 1) {
                val = 1;
            }
            if (val < 0) {
                val = 0;
            }

            int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (ls.colorMode == 3) { //Blending

            if (coef > 1) {
                coef = 1;
            }
            if (coef < 0) {
                coef = 0;
            }

            int index = (int) ((1 - coef) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            int temp_red = (grad_color >> 16) & 0xff;
            int temp_green = (grad_color >> 8) & 0xff;
            int temp_blue = grad_color & 0xff;

            int new_color =  blending.blend(temp_red, temp_green, temp_blue, r, g, b, 1 - ls.light_blending);

            r = (new_color >> 16) & 0xFF;
            g = (new_color >> 8) & 0xFF;
            b = new_color & 0xFF;

            double temp = coef2 * 255;
            r = (int) (r + temp + 0.5);
            g = (int) (g + temp + 0.5);
            b = (int) (b + temp +  0.5);

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }

            return 0xff000000 | (r << 16) | (g << 8) | b;
        } else { //scaling

            double temp = coef2 * 255;
            r = (int) (r * coef + temp + 0.5);
            g = (int) (g * coef + temp + 0.5);
            b = (int) (b * coef + temp + 0.5);

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }

            return 0xff000000 | (r << 16) | (g << 8) | b;
        }*/
    }

    private int light(double[] image_iterations, int color, int i, int j, int image_size) {

        int k0 = image_size * i + j;

        int kx = k0 + 1;
        int sx = 1;

        if (j == image_size - 1) {
            kx -= 2;
            sx = -1;
        }

        int ky = k0 + image_size;
        int sy = 1;

        if (i == image_size - 1) {
            ky -= 2 * image_size;
            sy = -1;
        }

        double h00 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);
        double h10 = ColorAlgorithm.transformResultToHeight(image_iterations[kx], max_iterations);
        double h01 = ColorAlgorithm.transformResultToHeight(image_iterations[ky], max_iterations);

        h00 = height_transfer(h00);
        h10 = height_transfer(h10);
        h01 = height_transfer(h01);

        double xz = h10 - h00;
        double yz = h01 - h00;

        double nx = -xz * sy;
        double ny = -sx * yz;
        double nz = sx * sy;

        // normalize nx, ny and nz
        double nlen = Math.sqrt(nx * nx + ny * ny + nz * nz);

        nx = nx / nlen;
        ny = ny / nlen;
        nz = nz / nlen;

        double lz = Math.sqrt(1 - ls.lightVector[0] * ls.lightVector[0] - ls.lightVector[1] * ls.lightVector[1]);

        // Lambert's law.
        double cos_a = ls.lightVector[0] * nx - ls.lightVector[1] * ny + lz * nz;

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        double coef = 0;

        // if lumen is negative it is behind, 
        // but I tweak it a bit for the sake of the looks:
        // cos_a = -1 (which is super-behind) ==> 0
        // cos_a = 0 ==> ambientlight
        // cos_a = 1 ==> lightintensity
        // for a mathematically correct look use the following:
        // if cos_a < 0 then cos_a = 0;
        // color.a = color.a * (ambientlight + lightintensity lumen);
        if (ls.lightMode == 0) {
            double d = ls.lightintensity / 2;
            coef = (((d - ls.ambientlight) * cos_a + d) * cos_a + ls.ambientlight);
        } else if (ls.lightMode == 1) {
            coef = Math.max(0, (ls.ambientlight + ls.lightintensity * cos_a));
        } else if (ls.lightMode == 2) {
            coef = (ls.ambientlight + ls.lightintensity * cos_a);
        }

        // Next, specular reflection. Viewer is always assumed to be in direction (0,0,1)
        // r = 2 n l - l; v = 0:0:1
        double spec_refl = Math.max(0, 2 * cos_a * nz - lz);
        
        double coef2 = ls.specularintensity * Math.pow(spec_refl, ls.shininess);

        if (ls.colorMode == 0) { //Lab         
            double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);      
            int[] rgb = ColorSpaceConverter.LABtoRGB(res[0] * coef + coef2 * 100, res[1], res[2]);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (ls.colorMode == 1) { //HSB
            double[] res = ColorSpaceConverter.RGBtoHSB(r, g, b);

            double val = res[2] * coef + coef2;

            if (val > 1) {
                val = 1;
            }
            if (val < 0) {
                val = 0;
            }

            int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (ls.colorMode == 2) { //HSL
            double[] res = ColorSpaceConverter.RGBtoHSL(r, g, b);

            double val = res[2] * coef + coef2;

            if (val > 1) {
                val = 1;
            }
            if (val < 0) {
                val = 0;
            }

            int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (ls.colorMode == 3) { //Blending
            if (coef > 1) {
                coef = 1;
            }
            if (coef < 0) {
                coef = 0;
            }

            int index = (int) ((1 - coef) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            int temp_red = (grad_color >> 16) & 0xff;
            int temp_green = (grad_color >> 8) & 0xff;
            int temp_blue = grad_color & 0xff;

            int new_color =  blending.blend(temp_red, temp_green, temp_blue, r, g, b, 1 - ls.light_blending);

            r = (new_color >> 16) & 0xFF;
            g = (new_color >> 8) & 0xFF;
            b = new_color & 0xFF;

            double temp = coef2 * 255;
            r = (int) (r + temp + 0.5);
            g = (int) (g + temp + 0.5);
            b = (int) (b + temp +  0.5);

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }

            return 0xff000000 | (r << 16) | (g << 8) | b;
        } else { //scaling

            double temp = coef2 * 255;
            r = (int) (r * coef + temp + 0.5);
            g = (int) (g * coef + temp + 0.5);
            b = (int) (b * coef + temp +  0.5);

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }

            return 0xff000000 | (r << 16) | (g << 8) | b;
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

    private float calaculateDomainColoringHeight(double res) {

        if (Double.isInfinite(res)) {
            res = 1000000;
        }
        return (float) (res > 1000000 ? 1000000 : res);

    }

    private void histogramColoringIterations(int image_size, double[] image_iterations, boolean[] escaped) {

        double histogramDensity = hss.histogramDensity;
        int maxCount = 1000000;
        int HIST_MULT = hss.histogramBinGranularity;

        try {
            if (normalize_find_ranges_sync.await() == 0) {
                maxIterationEscaped = -Double.MAX_VALUE;
                maxIterationNotEscaped = -Double.MAX_VALUE;
                totalEscaped = 0;
                totalNotEscaped = 0;
                minIterationsEscaped = Double.MAX_VALUE;
                minIterationsNotEscaped = Double.MAX_VALUE;
                denominatorEscaped = 1;
                denominatorNotEscaped = 1;

                for (int i = 0; i < image_iterations.length; i++) {

                    double val = image_iterations[i];

                    if (Math.abs(val) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
                        continue;
                    }

                    if (Double.isNaN(val) || Double.isInfinite(val)) {
                        continue;
                    }

                    val = ColorAlgorithm.transformResultToHeight(val, max_iterations);

                    if (escaped[i]) {
                        maxIterationEscaped = val > maxIterationEscaped ? val : maxIterationEscaped;
                        minIterationsEscaped = val < minIterationsEscaped ? val : minIterationsEscaped;
                    } else {
                        maxIterationNotEscaped = val > maxIterationNotEscaped ? val : maxIterationNotEscaped;
                        minIterationsNotEscaped = val < minIterationsNotEscaped ? val : minIterationsNotEscaped;
                    }
                }

                maxIterationEscaped = maxIterationEscaped > maxCount ? maxCount : maxIterationEscaped;
                maxIterationNotEscaped = maxIterationNotEscaped > maxCount ? maxCount : maxIterationNotEscaped;

                if (maxIterationEscaped != -Double.MAX_VALUE && minIterationsEscaped != Double.MAX_VALUE) {
                    escapedCounts = new int[((int) ((maxIterationEscaped - minIterationsEscaped + 1) * HIST_MULT))];
                }

                if (maxIterationNotEscaped != -Double.MAX_VALUE && minIterationsNotEscaped != Double.MAX_VALUE) {
                    notEscapedCounts = new int[((int) ((maxIterationNotEscaped - minIterationsNotEscaped + 1) * HIST_MULT))];
                }

                if(maxIterationEscaped < 1 && minIterationsEscaped < 1) {
                    denominatorEscaped = maxIterationEscaped - minIterationsEscaped + 1e-12;
                }

                if(maxIterationNotEscaped < 1 && minIterationsNotEscaped < 1) {
                    denominatorNotEscaped = maxIterationNotEscaped - minIterationsNotEscaped + 1e-12;
                }

                for (int i = 0; i < image_iterations.length; i++) {
                    double val = image_iterations[i];

                    if (Math.abs(val) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
                        continue;
                    }

                    if (Double.isNaN(val) || Double.isInfinite(val)) {
                        continue;
                    }

                    val = ColorAlgorithm.transformResultToHeight(val, max_iterations);

                    double tempVal = val > maxCount ? maxCount : val;

                    if (escaped[i]) {
                        escapedCounts[(int) ((tempVal - minIterationsEscaped) / denominatorEscaped * HIST_MULT)]++;
                        totalEscaped++;
                    } else {
                        notEscapedCounts[(int) ((tempVal - minIterationsNotEscaped) / denominatorNotEscaped * HIST_MULT)]++;
                        totalNotEscaped++;
                    }
                }

                if (escapedCounts != null) {
                    double sum = 0;
                    for (int i = 0; i < escapedCounts.length; i++) {
                        escapedCounts[i] += sum;
                        sum = escapedCounts[i];
                    }
                }

                if (notEscapedCounts != null) {
                    double sum = 0;
                    for (int i = 0; i < notEscapedCounts.length; i++) {
                        notEscapedCounts[i] += sum;
                        sum = notEscapedCounts[i];
                    }
                }
            }
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException e) {

        }

        try {
            normalize_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int modified = 0;

        for (int y = FROMy; y < TOy; y++) {
            for (int x = FROMx; x < TOx; x++) {
                int index = y * image_size + x;

                double val = image_iterations[index];
                boolean esc = escaped[index];

                if (Double.isNaN(val) || Double.isInfinite(val) || Math.abs(val) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
                    continue;
                }

                double sign = val >= 0 ? 1 : -1;

                double tempVal = ColorAlgorithm.transformResultToHeight(val, max_iterations);

                tempVal = tempVal > maxCount ? maxCount : tempVal;

                int valIndex = esc ? (int) ((tempVal - minIterationsEscaped) / denominatorEscaped * HIST_MULT) : (int) ((tempVal - minIterationsNotEscaped) / denominatorNotEscaped * HIST_MULT);

                int[] array = esc ? escapedCounts : notEscapedCounts;

                double sum = array[valIndex];

                double sumNext = sum;

                //Find the next cdf val that is greater from the old
                for (int i = valIndex + 1; i < array.length; i++) {
                    if (array[i] > sum) {
                        sumNext = array[i];
                        break;
                    }
                }

                double g1, g2;

                if (esc) {
                    double cdfMinEscaped = escapedCounts[0];
                    g1 = 1.0 - Math.pow(1.0 - ((sum - cdfMinEscaped) / (totalEscaped - cdfMinEscaped)), 1.0 / histogramDensity);
                    g2 = 1.0 - Math.pow(1.0 - ((sumNext - cdfMinEscaped) / (totalEscaped - cdfMinEscaped)), 1.0 / histogramDensity);
                } else {
                    double cdfMinNotEscaped = notEscapedCounts[0];
                    g1 = 1.0 - Math.pow(1.0 - ((sum - cdfMinNotEscaped) / (totalNotEscaped - cdfMinNotEscaped)), 1.0 / histogramDensity);
                    g2 = 1.0 - Math.pow(1.0 - ((sumNext - cdfMinNotEscaped) / (totalNotEscaped - cdfMinNotEscaped)), 1.0 / histogramDensity);
                }

                double fractionalPart;

                if (esc) {
                    fractionalPart = (tempVal - minIterationsEscaped) / denominatorEscaped * HIST_MULT - (int) ((tempVal - minIterationsEscaped)  / denominatorEscaped * HIST_MULT);
                } else {
                    fractionalPart = (tempVal - minIterationsNotEscaped) / denominatorNotEscaped * HIST_MULT - (int) ((tempVal - minIterationsNotEscaped) / denominatorNotEscaped * HIST_MULT);
                }

                g1 = method.interpolate(g1, g2, fractionalPart);

                g1 = (hss.histogramScaleMax - hss.histogramScaleMin) * g1 + hss.histogramScaleMin;

                val = sign * g1;

                if (Double.isNaN(val) || Double.isInfinite(val)) {
                    continue;
                }

                int paletteLength = (!esc && usePaletteForInColoring) ? palette_incoloring.getPaletteLength() : palette_outcoloring.getPaletteLength();
                val *= (paletteLength - 1);

                int original_color = 0;
                if (d3) {
                    original_color = (int) vert[x][y][0];
                } else {
                    original_color = rgbs[index];
                }

                int r = (original_color >> 16) & 0xFF;
                int g = (original_color >> 8) & 0xFF;
                int b = original_color & 0xFF;

                modified = getStandardColor(val, esc);

                int fc_red = (modified >> 16) & 0xFF;
                int fc_green = (modified >> 8) & 0xFF;
                int fc_blue = modified & 0xFF;

                double coef = 1 - hss.hs_blending;

                modified = blending.blend(r, g, b, fc_red, fc_green, fc_blue, coef);

                modified = postProcessingSmoothing(modified, image_iterations, original_color, y, x, image_size, hss.hs_noise_reducing_factor);

                if (d3) {
                    vert[x][y][0] = modified;
                } else {
                    rgbs[index] = modified;
                }
            }
        }

        try {
            if (normalize_sync2.await() == 0) {
                escapedCounts = null;
                notEscapedCounts = null;
                System.gc();
            }
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

    }

    private void histogramHeight() {

        double histogramDensity = histogram_density;
        int maxCount = 1000000;
        int HIST_MULT = histogram_granularity;

        histogramDenominator = 1;

        try {
            if (normalize_find_ranges_sync_3d.await() == 0) {
                maxIterations3d = -Double.MAX_VALUE;
                minIterations3d = Double.MAX_VALUE;
                totalCounts = 0;

                for (int x = 0; x < detail; x++) {
                    for (int y = 0; y < detail; y++) {
                        double val = Math.abs(vert[x][y][1]);

                        if (Double.isNaN(val) || Double.isInfinite(val)) {
                            continue;
                        }

                        maxIterations3d = val > maxIterations3d ? val : maxIterations3d;
                        minIterations3d = val < minIterations3d ? val : minIterations3d;
                    }
                }

                maxIterations3d = maxIterations3d > maxCount ? maxCount : maxIterations3d;

                if (maxIterations3d != -Double.MAX_VALUE && minIterations3d != Double.MAX_VALUE) {
                    histogramCounts = new int[((int) ((maxIterations3d - minIterations3d + 1) * HIST_MULT))];
                }


                if(minIterations3d < 1 && maxIterations3d < 1) {
                    histogramDenominator = maxIterations3d - minIterations3d + 1e-12;
                }

                for (int x = 0; x < detail; x++) {
                    for (int y = 0; y < detail; y++) {
                        double val = Math.abs(vert[x][y][1]);

                        if (Double.isNaN(val) || Double.isInfinite(val)) {
                            continue;
                        }

                        double tempVal = val > maxCount ? maxCount : val;

                        histogramCounts[(int) ((tempVal - minIterations3d) / histogramDenominator * HIST_MULT)]++;
                        totalCounts++;
                    }
                }

                if (histogramCounts != null) {
                    double sum = 0;
                    for (int i = 0; i < histogramCounts.length; i++) {
                        histogramCounts[i] += sum;
                        sum = histogramCounts[i];
                    }
                }
            }
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException e) {

        }

        try {
            normalize_sync_3d.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        for (int x = FROMx; x < TOx; x++) {
            for (int y = FROMy; y < TOy; y++) {

                double val = vert[x][y][1];

                if (Double.isNaN(val) || Double.isInfinite(val)) {
                    continue;
                }

                double tempVal = Math.abs(val);
                tempVal = tempVal > maxCount ? maxCount : tempVal;

                int valIndex = (int) ((tempVal - minIterations3d)  / histogramDenominator * HIST_MULT);

                double sum = histogramCounts[valIndex];

                double sumNext = sum;

                //Find the next cdf val that is greater from the old
                for (int i = valIndex + 1; i < histogramCounts.length; i++) {
                    if (histogramCounts[i] > sum) {
                        sumNext = histogramCounts[i];
                        break;
                    }
                }

                double cdfMin = histogramCounts[0];
                double g = 1.0 - Math.pow(1.0 - ((sum - cdfMin) / (totalCounts - cdfMin)), 1.0 / histogramDensity);
                double g2 = 1.0 - Math.pow(1.0 - ((sumNext - cdfMin) / (totalCounts - cdfMin)), 1.0 / histogramDensity);

                double fractionalPart = (tempVal - minIterations3d) / histogramDenominator * HIST_MULT - (int) ((tempVal - minIterations3d) / histogramDenominator * HIST_MULT);

                g = method.interpolate(g, g2, fractionalPart);
                val = g;

                if (Double.isNaN(val) || Double.isInfinite(val)) {
                    continue;
                }

                vert[x][y][1] = (float) val;
            }
        }

        try {
            if (normalize_sync2_3d.await() == 0) {
                histogramCounts = null;
                System.gc();
            }
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

    }

    public static String getDefaultInitialValue() {

        return default_init_val;

    }

    public static double getConvergentBailout() {

        return convergent_bailout;

    }

    public static void setDomainImageData(int image_size, boolean mode) {

        if (mode && (domain_image_data == null || domain_image_data.length != image_size * image_size)) {
            domain_image_data = new Complex[image_size * image_size];
        } else if (!mode) {
            domain_image_data = null;
            System.gc();
        }

    }

    public static void setArrays(int image_size, boolean usesDomainColoring) {

        IMAGE_SIZE = image_size;

        vert = null;
        vert1 = null;
        Norm1z = null;
        image_iterations = null;
        escaped = null;
        domain_image_data = null;

        System.gc();

        image_iterations = new double[image_size * image_size];
        escaped = new boolean[image_size * image_size];
        image_iterations_fast_julia = new double[MainWindow.FAST_JULIA_IMAGE_SIZE * MainWindow.FAST_JULIA_IMAGE_SIZE];
        escaped_fast_julia = new boolean[MainWindow.FAST_JULIA_IMAGE_SIZE * MainWindow.FAST_JULIA_IMAGE_SIZE];

        if (usesDomainColoring) {
            domain_image_data = new Complex[image_size * image_size];
        }
    }

    public static void setArraysExpander(int image_size) {

        IMAGE_SIZE = image_size;

        image_iterations = null;
        escaped = null;
        domain_image_data = null;

        System.gc();

        image_iterations = new double[image_size * image_size];
        escaped = new boolean[image_size * image_size];

    }

    public static void set3DArrays(int detail) {

        IMAGE_SIZE = detail;

        image_iterations = null;
        escaped = null;
        image_iterations_fast_julia = null;
        escaped_fast_julia = null;
        domain_image_data = null;

        System.gc();

        vert = new float[detail][detail][2];
        vert1 = new float[detail][detail][2];
        Norm1z = new float[detail][detail][2];
        image_iterations = new double[detail * detail];
        escaped = new boolean[detail * detail];

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
        height_scaling_sync3 = new AtomicInteger(0);
        height_scaling_sync4 = new CyclicBarrier(num_threads);
        height_function_sync = new CyclicBarrier(num_threads);
        gaussian_scaling_sync = new AtomicInteger(0);
        gaussian_scaling_sync2 = new CyclicBarrier(num_threads);
        normal_drawing_algorithm_pixel = new AtomicInteger(0);
        color_cycling_filters_sync = new CyclicBarrier(num_threads);
        color_cycling_restart_sync = new CyclicBarrier(num_threads);
        shade_color_height_sync = new CyclicBarrier(num_threads);
        initialize_jobs_sync = new CyclicBarrier(num_threads);
        normalize_find_ranges_sync = new CyclicBarrier(num_threads);
        normalize_sync = new CyclicBarrier(num_threads);
        normalize_sync2 = new CyclicBarrier(num_threads);
        normalize_find_ranges_sync_3d = new CyclicBarrier(num_threads);
        normalize_sync_3d = new CyclicBarrier(num_threads);
        normalize_sync2_3d = new CyclicBarrier(num_threads);
        color_cycling_toggle_lock = new ReentrantReadWriteLock();

        reference_calc_sync = new AtomicInteger(0);
        reference_sync = new CyclicBarrier(num_threads);

    }

    public void setThreadId(int threadId) {

        this.threadId = threadId;

    }

    public void colorTransferFactory(int transfer_function_out, int transfer_function_in, double color_intensity_out, double color_intensity_in) {

        switch (transfer_function_out) {

            case MainWindow.LINEAR:
                color_transfer_outcoloring = new LinearTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer_outcoloring = new SqrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer_outcoloring = new CbrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer_outcoloring = new ForthrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out);
                break;
            case MainWindow.LOGARITHM:
                color_transfer_outcoloring = new LogarithmTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out);
                break;
            case MainWindow.LOG_LOG:
                color_transfer_outcoloring = new LogLogTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out);
                break;
            case MainWindow.ATAN:
                color_transfer_outcoloring = new AtanTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out);
                break;
        }

        switch (transfer_function_in) {

            case MainWindow.LINEAR:
                color_transfer_incoloring = new LinearTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer_incoloring = new SqrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer_incoloring = new CbrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer_incoloring = new ForthrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in);
                break;
            case MainWindow.LOGARITHM:
                color_transfer_incoloring = new LogarithmTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in);
                break;
            case MainWindow.LOG_LOG:
                color_transfer_incoloring = new LogLogTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in);
                break;
            case MainWindow.ATAN:
                color_transfer_incoloring = new AtanTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in);
                break;
        }

    }

    private int getModifiedColor(int red, int green, int blue, double coef, int colorMethod, double colorBlending, boolean reverseBlending) {

        if (colorMethod == 0) { //Lab
            double[] res = ColorSpaceConverter.RGBtoLAB(red, green, blue);
            double val = contourFactor * coef * res[0];
            val = val > 100 ? 100 : val;
            int[] rgb = ColorSpaceConverter.LABtoRGB(val, res[1], res[2]);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (colorMethod == 1) { //HSB
            double[] res = ColorSpaceConverter.RGBtoHSB(red, green, blue);
            double val = contourFactor * coef * res[2];
            val = val > 1 ? 1 : val;
            int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (colorMethod == 2) { //HSL
            double[] res = ColorSpaceConverter.RGBtoHSL(red, green, blue);
            double val = contourFactor * coef * res[2];
            val = val > 1 ? 1 : val;
            int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        } else if (colorMethod == 3) {// blend
            int index = (int) (coef * (gradient.length - 1) + 0.5);

            if (reverseBlending) {
                index = gradient.length - 1 - index;
            }

            int grad_color = getGradientColor(index + gradient_offset);

            int temp_red = (grad_color >> 16) & 0xff;
            int temp_green = (grad_color >> 8) & 0xff;
            int temp_blue = grad_color & 0xff;

            return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, colorBlending);
        } else { //scale
            red = (int) (contourFactor * coef * red + 0.5);
            green = (int) (contourFactor * coef * green + 0.5);
            blue = (int) (contourFactor * coef * blue + 0.5);
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            blue = blue > 255 ? 255 : blue;
            return 0xff000000 | (red << 16) | (green << 8) | blue;
        }
    }

    public double scaleDomainHeight(double norm) {

        if (ds.domainProcessingTransfer == 0) {
            return norm * ds.domainProcessingHeightFactor;
        } else {
            return 1 / (norm * ds.domainProcessingHeightFactor);
        }

    }

    private void interpolationFactory(int color_interpolation) {
        switch (color_interpolation) {
            case MainWindow.INTERPOLATION_LINEAR:
                method = new LinearInterpolation();
                break;
            case MainWindow.INTERPOLATION_COSINE:
                method = new CosineInterpolation();
                break;
            case MainWindow.INTERPOLATION_ACCELERATION:
                method = new AccelerationInterpolation();
                break;
            case MainWindow.INTERPOLATION_DECELERATION:
                method = new DecelerationInterpolation();
                break;
            case MainWindow.INTERPOLATION_EXPONENTIAL:
                method = new ExponentialInterpolation();
                break;
            case MainWindow.INTERPOLATION_CATMULLROM:
                method = new CatmullRomInterpolation();
                break;
            case MainWindow.INTERPOLATION_CATMULLROM2:
                method = new CatmullRom2Interpolation();
                break;
            case MainWindow.INTERPOLATION_SIGMOID:
                method = new SigmoidInterpolation();
                break;
            case MainWindow.INTERPOLATION_QUADRATIC:
                method = new QuadraticInterpolation();
                break;
            case MainWindow.INTERPOLATION_CUBIC:
                method = new CubicInterpolation();
                break;
            case MainWindow.INTERPOLATION_SQRT:
                method = new SqrtInterpolation();
                break;
        }
    }

    private Fractal fractalFactory(int function, double xCenter, double yCenter, double size, int max_iterations, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, int plane_type, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double z_exponent, double[] z_exponent_complex, double[] coefficients, double[] coefficients_im, double[] z_exponent_nova, double[] relaxation, int nova_method, int bail_technique, String user_formula, String user_formula2, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, GenericCaZbdZeSettings gcs, String[] lyapunovExpression, OrbitTrapSettings ots, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, int converging_smooth_algorithm, StatisticsSettings sts, boolean useLyapunovExponent, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, double[] kleinianLine, double kleinianK, double kleinianM, double[] laguerre_deg, double[] durand_kernel_init_val, MagneticPendulumSettings mps, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, int root_initialization_method, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean defaultNovaInitialValue) {

        Fractal fractal = null;

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAMBDA2:
                fractal = new Lambda2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAMBDA3:
                fractal = new Lambda3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAMBDA_FN_FN:
                fractal = new LambdaFnFn(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, lfns);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MAGNET13:
                fractal = new Magnet13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MAGNET14:
                fractal = new Magnet14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MAGNET23:
                fractal = new Magnet23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MAGNET24:
                fractal = new Magnet24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON3:
                fractal = new Newton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON4:
                fractal = new Newton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                fractal = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                fractal = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTONSIN:
                fractal = new NewtonSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTONCOS:
                fractal = new NewtonCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTONPOLY:
                fractal = new NewtonPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.NEWTONFORMULA:
                fractal = new NewtonFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SIERPINSKI_GASKET:
                fractal = new SierpinskiGasket(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.KLEINIAN:
                fractal = new Kleinian(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, kleinianLine, kleinianK, kleinianM, sts);
                break;
            case MainWindow.HALLEY3:
                fractal = new Halley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HALLEY4:
                fractal = new Halley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HALLEYGENERALIZED3:
                fractal = new HalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HALLEYGENERALIZED8:
                fractal = new HalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HALLEYSIN:
                fractal = new HalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HALLEYCOS:
                fractal = new HalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HALLEYPOLY:
                fractal = new HalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.HALLEYFORMULA:
                fractal = new HalleyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots, sts);
                break;
            case MainWindow.SCHRODER3:
                fractal = new Schroder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SCHRODER4:
                fractal = new Schroder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SCHRODERGENERALIZED3:
                fractal = new SchroderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SCHRODERGENERALIZED8:
                fractal = new SchroderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SCHRODERSIN:
                fractal = new SchroderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SCHRODERCOS:
                fractal = new SchroderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SCHRODERPOLY:
                fractal = new SchroderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.SCHRODERFORMULA:
                fractal = new SchroderFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER3:
                fractal = new Householder3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER4:
                fractal = new Householder4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED3:
                fractal = new HouseholderGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED8:
                fractal = new HouseholderGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDERSIN:
                fractal = new HouseholderSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDERCOS:
                fractal = new HouseholderCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDERPOLY:
                fractal = new HouseholderPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.HOUSEHOLDERFORMULA:
                fractal = new HouseholderFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots, sts);
                break;
            case MainWindow.SECANT3:
                fractal = new Secant3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SECANT4:
                fractal = new Secant4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SECANTGENERALIZED3:
                fractal = new SecantGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SECANTGENERALIZED8:
                fractal = new SecantGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SECANTCOS:
                fractal = new SecantCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SECANTPOLY:
                fractal = new SecantPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.SECANTFORMULA:
                fractal = new SecantFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots, sts);
                break;
            case MainWindow.STEFFENSEN3:
                fractal = new Steffensen3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STEFFENSEN4:
                fractal = new Steffensen4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STEFFENSENGENERALIZED3:
                fractal = new SteffensenGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STEFFENSENPOLY:
                fractal = new SteffensenPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.STEFFENSENFORMULA:
                fractal = new SteffensenFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots, sts);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, newton_hines_k, defaultNovaInitialValue);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.USER_FORMULA:
                if (bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                } else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                } else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if (bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                } else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, sts);
                } else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, sts);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MULLER3:
                fractal = new Muller3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MULLER4:
                fractal = new Muller4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MULLERGENERALIZED3:
                fractal = new MullerGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MULLERGENERALIZED8:
                fractal = new MullerGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MULLERSIN:
                fractal = new MullerSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MULLERCOS:
                fractal = new MullerCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MULLERPOLY:
                fractal = new MullerPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.MULLERFORMULA:
                fractal = new MullerFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, ots, sts);
                break;
            case MainWindow.PARHALLEY3:
                fractal = new Parhalley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.PARHALLEY4:
                fractal = new Parhalley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.PARHALLEYGENERALIZED3:
                fractal = new ParhalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.PARHALLEYGENERALIZED8:
                fractal = new ParhalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.PARHALLEYSIN:
                fractal = new ParhalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.PARHALLEYCOS:
                fractal = new ParhalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.PARHALLEYPOLY:
                fractal = new ParhalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.PARHALLEYFORMULA:
                fractal = new ParhalleyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots, sts);
                break;
            case MainWindow.LAGUERRE3:
                fractal = new Laguerre3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAGUERRE4:
                fractal = new Laguerre4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAGUERREGENERALIZED3:
                fractal = new LaguerreGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAGUERREGENERALIZED8:
                fractal = new LaguerreGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAGUERRESIN:
                fractal = new LaguerreSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAGUERRECOS:
                fractal = new LaguerreCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAGUERREPOLY:
                fractal = new LaguerrePoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.LAGUERREFORMULA:
                fractal = new LaguerreFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, laguerre_deg, ots, sts);
                break;
            case MainWindow.GENERIC_CaZbdZe:
                fractal = new GenericCaZbdZe(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, gcs);
                break;
            case MainWindow.DURAND_KERNER3:
                fractal = new DurandKerner3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.DURAND_KERNER4:
                fractal = new DurandKerner4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.DURAND_KERNERGENERALIZED3:
                fractal = new DurandKernerGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.DURAND_KERNERGENERALIZED8:
                fractal = new DurandKernerGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.DURAND_KERNERPOLY:
                fractal = new DurandKernerPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, durand_kernel_init_val, coefficients_im, root_initialization_method);
                break;
            case MainWindow.MAGNETIC_PENDULUM:
                fractal = new MagneticPendulum(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, mps);
                break;
            case MainWindow.LYAPUNOV:
                fractal = new Lyapunov(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, lyapunovExpression, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck);
                break;
            case MainWindow.BAIRSTOW3:
                fractal = new Bairstow3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.BAIRSTOW4:
                fractal = new Bairstow4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.BAIRSTOWGENERALIZED3:
                fractal = new BairstowGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.BAIRSTOWGENERALIZED8:
                fractal = new BairstowGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.BAIRSTOWPOLY:
                fractal = new BairstowPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.USER_FORMULA_NOVA:
                fractal = new UserFormulaNova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, newton_hines_k, defaultNovaInitialValue);
                break;
            case MainWindow.GENERIC_CpAZpBC:
                fractal = new GenericCpAZpBC(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, gcps);
                break;
            case MainWindow.INERTIA_GRAVITY:
                fractal = new InertiaGravityFractal(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, igs);
                break;
            case MainWindow.MANDEL_NEWTON:
                fractal = new MandelNewton(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts);
                break;
            case MainWindow.LAMBERT_W_VARIATION:
                fractal = new LambertWVariation(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON_HINES3:
                fractal = new NewtonHines3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON_HINES4:
                fractal = new NewtonHines4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON_HINESGENERALIZED3:
                fractal = new NewtonHinesGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON_HINESGENERALIZED8:
                fractal = new NewtonHinesGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON_HINESSIN:
                fractal = new NewtonHinesSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON_HINESCOS:
                fractal = new NewtonHinesCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.NEWTON_HINESPOLY:
                fractal = new NewtonHinesPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im, newton_hines_k);
                break;
            case MainWindow.NEWTON_HINESFORMULA:
                fractal = new NewtonHinesFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts, newton_hines_k);
                break;
            case MainWindow.WHITTAKER3:
                fractal = new Whittaker3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKER4:
                fractal = new Whittaker4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERGENERALIZED3:
                fractal = new WhittakerGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERGENERALIZED8:
                fractal = new WhittakerGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERSIN:
                fractal = new WhittakerSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERCOS:
                fractal = new WhittakerCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERPOLY:
                fractal = new WhittakerPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.WHITTAKERFORMULA:
                fractal = new WhittakerFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots, sts);
                break;
            case MainWindow.WHITTAKERDOUBLECONVEX3:
                fractal = new WhittakerDoubleConvex3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERDOUBLECONVEX4:
                fractal = new WhittakerDoubleConvex4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERDOUBLECONVEXGENERALIZED3:
                fractal = new WhittakerDoubleConvexGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERDOUBLECONVEXGENERALIZED8:
                fractal = new WhittakerDoubleConvexGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERDOUBLECONVEXSIN:
                fractal = new WhittakerDoubleConvexSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERDOUBLECONVEXCOS:
                fractal = new WhittakerDoubleConvexCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WHITTAKERDOUBLECONVEXPOLY:
                fractal = new WhittakerDoubleConvexPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.WHITTAKERDOUBLECONVEXFORMULA:
                fractal = new WhittakerDoubleConvexFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots, sts);
                break;
            case MainWindow.SUPERHALLEY3:
                fractal = new SuperHalley3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SUPERHALLEY4:
                fractal = new SuperHalley4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SUPERHALLEYGENERALIZED3:
                fractal = new SuperHalleyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SUPERHALLEYGENERALIZED8:
                fractal = new SuperHalleyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SUPERHALLEYSIN:
                fractal = new SuperHalleySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SUPERHALLEYCOS:
                fractal = new SuperHalleyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.SUPERHALLEYPOLY:
                fractal = new SuperHalleyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.SUPERHALLEYFORMULA:
                fractal = new SuperHalleyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, ots, sts);
                break;
            case MainWindow.TRAUB_OSTROWSKI3:
                fractal = new TraubOstrowski3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.TRAUB_OSTROWSKI4:
                fractal = new TraubOstrowski4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.TRAUB_OSTROWSKIGENERALIZED3:
                fractal = new TraubOstrowskiGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.TRAUB_OSTROWSKIGENERALIZED8:
                fractal = new TraubOstrowskiGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.TRAUB_OSTROWSKISIN:
                fractal = new TraubOstrowskiSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.TRAUB_OSTROWSKICOS:
                fractal = new TraubOstrowskiCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.TRAUB_OSTROWSKIPOLY:
                fractal = new TraubOstrowskiPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.TRAUB_OSTROWSKIFORMULA:
                fractal = new TraubOstrowskiFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts);
                break;
            case MainWindow.STIRLING3:
                fractal = new Stirling3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STIRLING4:
                fractal = new Stirling4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STIRLINGGENERALIZED3:
                fractal = new StirlingGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STIRLINGGENERALIZED8:
                fractal = new StirlingGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STIRLINGSIN:
                fractal = new StirlingSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STIRLINGCOS:
                fractal = new StirlingCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.STIRLINGPOLY:
                fractal = new StirlingPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.STIRLINGFORMULA:
                fractal = new StirlingFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts);
                break;
            case MainWindow.MIDPOINT3:
                fractal = new Midpoint3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MIDPOINT4:
                fractal = new Midpoint4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MIDPOINTGENERALIZED3:
                fractal = new MidpointGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MIDPOINTGENERALIZED8:
                fractal = new MidpointGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MIDPOINTSIN:
                fractal = new MidpointSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MIDPOINTCOS:
                fractal = new MidpointCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.MIDPOINTPOLY:
                fractal = new MidpointPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.MIDPOINTFORMULA:
                fractal = new MidpointFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts);
                break;
            case MainWindow.ABERTH_EHRLICH3:
                fractal = new AberthEhrlich3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABERTH_EHRLICH4:
                fractal = new AberthEhrlich4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABERTH_EHRLICHGENERALIZED3:
                fractal = new AberthEhrlichGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABERTH_EHRLICHGENERALIZED8:
                fractal = new AberthEhrlichGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABERTH_EHRLICHPOLY:
                fractal = new AberthEhrlichPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, durand_kernel_init_val, coefficients_im, root_initialization_method);
                break;
            case MainWindow.JARATT3:
                fractal = new Jaratt3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATT4:
                fractal = new Jaratt4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATTGENERALIZED3:
                fractal = new JarattGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATTGENERALIZED8:
                fractal = new JarattGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATTSIN:
                fractal = new JarattSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATTCOS:
                fractal = new JarattCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATTPOLY:
                fractal = new JarattPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.JARATTFORMULA:
                fractal = new JarattFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts);
                break;
            case MainWindow.JARATT23:
                fractal = new Jaratt23(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATT24:
                fractal = new Jaratt24(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATT2GENERALIZED3:
                fractal = new Jaratt2Generalized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATT2GENERALIZED8:
                fractal = new Jaratt2Generalized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATT2SIN:
                fractal = new Jaratt2Sin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATT2COS:
                fractal = new Jaratt2Cos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.JARATT2POLY:
                fractal = new Jaratt2Poly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.JARATT2FORMULA:
                fractal = new Jaratt2Formula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts);
                break;
            case MainWindow.THIRDORDERNEWTON3:
                fractal = new ThirdOrderNewton3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.THIRDORDERNEWTON4:
                fractal = new ThirdOrderNewton4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.THIRDORDERNEWTONGENERALIZED3:
                fractal = new ThirdOrderNewtonGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.THIRDORDERNEWTONGENERALIZED8:
                fractal = new ThirdOrderNewtonGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.THIRDORDERNEWTONSIN:
                fractal = new ThirdOrderNewtonSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.THIRDORDERNEWTONCOS:
                fractal = new ThirdOrderNewtonCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.THIRDORDERNEWTONPOLY:
                fractal = new ThirdOrderNewtonPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.THIRDORDERNEWTONFORMULA:
                fractal = new ThirdOrderNewtonFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts);
                break;
            case MainWindow.WEERAKOON_FERNANDO3:
                fractal = new WeerakoonFernando3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WEERAKOON_FERNANDO4:
                fractal = new WeerakoonFernando4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WEERAKOON_FERNANDOGENERALIZED3:
                fractal = new WeerakoonFernandoGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WEERAKOON_FERNANDOGENERALIZED8:
                fractal = new WeerakoonFernandoGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WEERAKOON_FERNANDOSIN:
                fractal = new WeerakoonFernandoSin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WEERAKOON_FERNANDOCOS:
                fractal = new WeerakoonFernandoCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.WEERAKOON_FERNANDOPOLY:
                fractal = new WeerakoonFernandoPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.WEERAKOON_FERNANDOFORMULA:
                fractal = new WeerakoonFernandoFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER33:
                fractal = new Householder33(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER34:
                fractal = new Householder34(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER3GENERALIZED3:
                fractal = new Householder3Generalized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER3GENERALIZED8:
                fractal = new Householder3Generalized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER3SIN:
                fractal = new Householder3Sin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER3COS:
                fractal = new Householder3Cos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.HOUSEHOLDER3POLY:
                fractal = new Householder3Poly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.HOUSEHOLDER3FORMULA:
                fractal = new Householder3Formula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, ots, sts);
                break;
            case MainWindow.ABBASBANDY3:
                fractal = new Abbasbandy3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABBASBANDY4:
                fractal = new Abbasbandy4(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABBASBANDYGENERALIZED3:
                fractal = new AbbasbandyGeneralized3(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABBASBANDYGENERALIZED8:
                fractal = new AbbasbandyGeneralized8(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABBASBANDYSIN:
                fractal = new AbbasbandySin(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABBASBANDYCOS:
                fractal = new AbbasbandyCos(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts);
                break;
            case MainWindow.ABBASBANDYPOLY:
                fractal = new AbbasbandyPoly(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, coefficients_im);
                break;
            case MainWindow.ABBASBANDYFORMULA:
                fractal = new AbbasbandyFormula(xCenter, yCenter, size, max_iterations, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, ots, sts);
                break;
        }

        fractal.preFilterFactory(preffs);
        fractal.postFilterFactory(postffs);
        fractal.influencePlaneFactory(ips);

        return fractal;

    }

    private Fractal juliaFactory(int function, double xCenter, double yCenter, double size, int max_iterations, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double z_exponent, double[] z_exponent_complex, double[] coefficients, double[] coefficients_im, double[] z_exponent_nova, double[] relaxation, int nova_method, int bail_technique, String user_formula, String user_formula2, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, GenericCaZbdZeSettings gcs, String[] lyapunovExpression, OrbitTrapSettings ots, boolean exterior_de, double exterior_de_factor, boolean inverse_dem, int escaping_smooth_algorithm, int converging_smooth_algorithm, StatisticsSettings sts, boolean useLyapunovExponent, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, double xJuliaCenter, double yJuliaCenter) {

        Fractal fractal = null;

        switch (function) {
            case MainWindow.MANDELBROT:
                fractal = new Mandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                fractal = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                fractal = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                fractal = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                fractal = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                fractal = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                fractal = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                fractal = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                fractal = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                fractal = new MandelbrotNth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                fractal = new MandelbrotWth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                fractal = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, coefficients_im, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                fractal = new Lambda(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA2:
                fractal = new Lambda2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA3:
                fractal = new Lambda3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA_FN_FN:
                fractal = new LambdaFnFn(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, lfns, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                fractal = new Magnet1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET13:
                fractal = new Magnet13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET14:
                fractal = new Magnet14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                fractal = new Magnet2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET23:
                fractal = new Magnet23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET24:
                fractal = new Magnet24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                fractal = new Barnsley1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                fractal = new Barnsley2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                fractal = new Barnsley3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                fractal = new Mandelbar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                fractal = new Spider(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                fractal = new Manowar(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                fractal = new Phoenix(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                fractal = new Nova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, newton_hines_k, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                fractal = new Exp(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                fractal = new Log(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                fractal = new Sin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                fractal = new Cos(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                fractal = new Tan(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                fractal = new Cot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                fractal = new Sinh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                fractal = new Cosh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                fractal = new Tanh(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                fractal = new Coth(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                fractal = new Formula30(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                fractal = new Formula31(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                fractal = new Formula1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                fractal = new Formula2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                fractal = new Formula3(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                fractal = new Formula4(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                fractal = new Formula5(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                fractal = new Formula6(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                fractal = new Formula7(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                fractal = new Formula8(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                fractal = new Formula9(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                fractal = new Formula10(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                fractal = new Formula11(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                fractal = new Formula12(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                fractal = new Formula13(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                fractal = new Formula14(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                fractal = new Formula15(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                fractal = new Formula16(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                fractal = new Formula17(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                fractal = new Formula18(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                fractal = new Formula19(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                fractal = new Formula20(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                fractal = new Formula21(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                fractal = new Formula22(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                fractal = new Formula23(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                fractal = new Formula24(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                fractal = new Formula25(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                fractal = new Formula26(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                fractal = new Formula27(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                fractal = new Formula28(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                fractal = new Formula29(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                fractal = new Formula32(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                fractal = new Formula33(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                fractal = new Formula34(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                fractal = new Formula35(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                fractal = new Formula36(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                fractal = new Formula37(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                fractal = new Formula38(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                fractal = new Formula39(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                fractal = new Formula40(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                fractal = new Formula41(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                fractal = new Formula42(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                fractal = new Formula43(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                fractal = new Formula44(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                fractal = new Formula45(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                fractal = new Formula46(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if (bail_technique == 0) {
                    fractal = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if (bail_technique == 0) {
                    fractal = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if (bail_technique == 0) {
                    fractal = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, sts, xJuliaCenter, yJuliaCenter);
                } else {
                    fractal = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ots, sts, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                fractal = new FrothyBasin(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                fractal = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                fractal = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                fractal = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                fractal = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.GENERIC_CaZbdZe:
                fractal = new GenericCaZbdZe(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, gcs, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LYAPUNOV:
                fractal = new Lyapunov(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, lyapunovExpression, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA_NOVA:
                fractal = new UserFormulaNova(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, newton_hines_k, defaultNovaInitialValue, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.GENERIC_CpAZpBC:
                fractal = new GenericCpAZpBC(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, gcps, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDEL_NEWTON:
                fractal = new MandelNewton(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBERT_W_VARIATION:
                fractal = new LambertWVariation(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, converging_smooth_algorithm, ots, sts, xJuliaCenter, yJuliaCenter);
                break;

        }

        fractal.preFilterFactory(preffs);
        fractal.postFilterFactory(postffs);
        fractal.influencePlaneFactory(ips);

        fractal.setJuliter(juliter);
        fractal.setJuliterIterations(juliterIterations);
        fractal.setJuliterIncludeInitialIterations(juliterIncludeInitialIterations);

        return fractal;

    }

    public static int getGradientColor(int index) {

        return gradient[index % gradient.length];

    }

    public void setTrueColoringOptions(TrueColorSettings tcs) {

        fractal.setTrueColorAlgorithm(tcs);
        usesTrueColorIn = tcs.trueColorIn;
        TrueColorAlgorithm.palette_outcoloring = palette_outcoloring;
        TrueColorAlgorithm.palette_incoloring = palette_incoloring;
        TrueColorAlgorithm.color_transfer_outcoloring = color_transfer_outcoloring;
        TrueColorAlgorithm.color_transfer_incoloring = color_transfer_incoloring;
        TrueColorAlgorithm.usePaletteForInColoring = usePaletteForInColoring;
        TrueColorAlgorithm.color_cycling_location_outcoloring = color_cycling_location_outcoloring;
        TrueColorAlgorithm.color_cycling_location_incoloring = color_cycling_location_incoloring;
        TrueColorAlgorithm.gradient = gradient;
        TrueColorAlgorithm.gradient_offset = gradient_offset;

    }

    public void calculateReference(Location loc) {
        BigComplex rotation = new BigComplex(rotation_vals[0], rotation_vals[1]);
        BigComplex rot_center = new BigComplex(rotation_center[0], rotation_center[1]);
        BigComplex temp = new BigComplex(xCenter, yCenter);
        temp = temp.sub(rot_center);
        temp = temp.times(rotation).plus(rot_center);

        temp = fractal.getPlaneTransformedPixel(temp);

        boolean isDeep = size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0;

        if(Fractal.refPoint != null && Fractal.Reference != null && temp.compare(Fractal.refPoint) == 0 && Fractal.RefType.equals(fractal.getRefType())
                && !(Fractal.ReferenceDeep == null && isDeep)) {

            if(max_iterations > fractal.Reference.length || (CALCULATE_FULL_REFERENCE && !Fractal.FullRef)) {
                fractal.calculateReferencePoint(temp, size,size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0, Fractal.MaxRefIteration + 1, loc);
            }
            else {
                if (SERIES_APPROXIMATION && !fractal.getBurningShip()) {
                    fractal.calculateSeries(size, isDeep, loc);
                }
            }

            return;

        }

        Fractal.clearReferences();
        fractal.calculateReferencePoint(temp, size,size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0, 0, loc);

    }

}
