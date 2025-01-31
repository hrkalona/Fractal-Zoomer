
package fractalzoomer.core;

import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.blending.*;
import fractalzoomer.core.domain_coloring.*;
import fractalzoomer.core.interpolation.*;
import fractalzoomer.core.iteration_algorithm.FractalIterationAlgorithm;
import fractalzoomer.core.iteration_algorithm.IterationAlgorithm;
import fractalzoomer.core.iteration_algorithm.JuliaIterationAlgorithm;
import fractalzoomer.core.approximation.la_zhuoran.LAReference;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.location.normal.CartesianLocationNormalApfloatArbitrary;
import fractalzoomer.core.location.normal.PolarLocationNormalApfloatArbitrary;
import fractalzoomer.core.approximation.mip_la_zhuoran.MipLAStep;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.LibMpir;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.rendering_algorithms.BoundaryTracingRender;
import fractalzoomer.core.rendering_algorithms.QueueBasedRender;
import fractalzoomer.core.rendering_algorithms.SuccessiveRefinementGuessingRender;
import fractalzoomer.fractal_options.iteration_statistics.*;
import fractalzoomer.fractal_options.orbit_traps.OrbitTrap;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.FractalFactory;
import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.*;
import fractalzoomer.true_coloring_algorithms.TrueColorAlgorithm;
import fractalzoomer.utils.*;
import fractalzoomer.utils.queues.ExpandingQueueSquare;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 *
 * @author hrkalona
 */
public abstract class TaskRender implements Runnable {
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
    public static final int NORMAL_MINIMAL_RENDERER = 12;
    public static final int POLAR_MINIMAL_RENDERER = 13;
    public static final int DOMAIN_MINIMAL_RENDERER = 14;
    public static final int DOMAIN_POLAR_MINIMAL_RENDERER = 15;
    public static final int POST_PROCESSING_WITH_AA_AND_FILTER = 16;
    public static final int APPLY_PALETTE_AND_POST_PROCESSING_WITH_AA_AND_FILTER = 17;

    /**
     * ** Thread Related / Synchronization ***
     */
    protected int FROMx;
    protected int TOx;
    protected int FROMy;
    protected int TOy;

    public static double LCHab_CONSTANT_L = 50;
    public static double LCHab_CONSTANT_C = 100;

    public static double LCHuv_CONSTANT_L = 50;
    public static double LCHuv_CONSTANT_C = 130;

    public static float HSB_CONSTANT_S = 1;
    public static float HSB_CONSTANT_B = 1;

    public static double USER_CONVERGENT_BAILOUT;

    public static boolean CHUNK_SIZE_PER_ROW = false;

    protected static final int THREAD_CHUNK_SIZE = 500;
    protected static final int SUCCESSIVE_REFINEMENT_EXPONENT = 7;
    protected static int SUCCESSIVE_REFINEMENT_MAX_SIZE;
    protected static int[] THREAD_CHUNK_SIZE_PER_LEVEL;
    protected static int[] THREAD_CHUNK_SIZE_PER_LEVEL2;
    protected static int[] SUCCESSIVE_REFINEMENT_SPLIT1;
    protected static int[] SUCCESSIVE_REFINEMENT_SPLIT2;
    protected static int[] SUCCESSIVE_REFINEMENT_SPLIT3;
    protected static int[] SUCCESSIVE_REFINEMENT_SPLIT4;

    protected static ExpandingQueueSquare rectangleAreasQueueu;

    public static int SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM = 0;

    public static boolean COMPRESS_REFERENCE_IF_POSSIBLE = false;

    public static boolean TWO_PASS_SUCCESSIVE_REFINEMENT = false;
    public static boolean TWO_PASS_CHECK_CENTER = true;
    public static int SQUARE_RECT_CHUNK_AGGERAGATION = 0;

    protected static int[] SUCCESSIVE_REFINEMENT_CHUNK_X;
    protected static int[] SUCCESSIVE_REFINEMENT_CHUNK_Y;

    public static boolean QUICKRENDER_SUCCESSIVE_REFINEMENT = false;
    public static boolean USE_NON_BLOCKING_RENDERING = true;
    public static boolean ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA = true;
    public static boolean ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA_WITH_PP = true;
    public static boolean USE_RI_ON_BLA2 = false;
    public static boolean DISABLE_RI_ON_BLA2 = false;

    public static boolean USE_FAST_DELTA_LOCATION = true;
    public static int BUILT_IT_BIGNUM_IMPLEMENTATION = 0;

    public static boolean SPLIT_INTO_RECTANGLE_AREAS = false;
    public static int AREA_DIMENSION_Y = 3;
    public static int AREA_DIMENSION_X = 3;
    public static int RECTANGLE_AREA_SPLIT_ALGORITHM = 1;
    private static int RECTANGLE_AREAS_QUEUE_INIT_SIZE = 6000;

    public static int FAST_JULIA_IMAGE_SIZE = 252;
    public static int randomNumber;

    public static boolean USE_DIRECT_COLOR;
    public static int COLOR_SMOOTHING_METHOD;
    public static int COLOR_SPACE;

    public static boolean INCLUDE_AA_DATA_ON_RANK_ORDER = false;

    public static LongAccumulator PostProcessingCalculationTime;

    protected static int[] rendering_done_per_task;
    protected int rendering_done;
    protected int task_calculated;
    protected int task_completed = -1;
    protected int task_post_processed = -1;
    protected int task_calculated_extra;
    protected long pixel_calculation_time_per_task;
    protected long post_processing_calculation_time_per_task;

    public static final int MAX_GROUPING = 64;
    protected long[] task_pixel_grouping = new long[MAX_GROUPING];//hopefully that is enough

    protected boolean usesSquareChunks;

    public static int[] algorithm_colors;
    protected static int[] algorithm_colors2;

    protected int taskId;
    protected String doneByThreadName;
    protected long doneByThreadId;

    private boolean createPreview;
    private boolean zoomToCursor;
    protected boolean createFullImageAfterPreview;
    private static AtomicInteger finalize_sync;
    private static CyclicBarrier normalize_sync2;

    private static CyclicBarrier squares_sync;
    private static CyclicBarrier normalize_find_ranges_sync_3d;

    protected static CyclicBarrier initialize_jobs_sync;
    protected static CyclicBarrier initialize_jobs_sync2;
    protected static CyclicBarrier initialize_jobs_sync3;
    protected static CyclicBarrier initialize_jobs_sync4;
    protected static AtomicInteger mariani_silver_first_rendering;
    private static CyclicBarrier post_processing_sync;
    private static CyclicBarrier calculate_vectors_sync;
    protected static AtomicInteger painting_sync;

    public static AtomicInteger number_of_tasks;
    private static AtomicInteger height_scaling_sync;
    private static CyclicBarrier height_scaling_sync2;
    private static AtomicInteger height_scaling_sync3;
    private static CyclicBarrier height_scaling_sync4;
    private static CyclicBarrier height_function_sync;
    private static AtomicInteger gaussian_scaling_sync;
    private static AtomicInteger remove_outliers_sync;
    private static AtomicInteger remove_outliers_sync3;
    private static CyclicBarrier remove_outliers_sync2;
    private static CyclicBarrier remove_outliers_sync4;
    private static CyclicBarrier gaussian_scaling_sync2;
    private static CyclicBarrier shade_color_height_sync;
    private static LongAdder total_calculated;
    private static LongAdder total_completed;
    private static LongAdder total_post_processed;
    private static LongAdder[] total_pixel_grouping;
    public static LongAccumulator max_pixel_calculation_time;
    private static LongAdder total_calculated_extra;
    protected static AtomicInteger normal_rendering_algorithm_pixel;
    protected static AtomicInteger normal_rendering_algorithm_pixel2;
    protected static AtomicInteger[] quick_render_rendering_algorithm_pixel;
    protected static AtomicInteger[] successive_refinement_rendering_algorithm_pixel;
    protected static AtomicInteger[] successive_refinement_rendering_algorithm2_pixel;
    protected static AtomicInteger render_squares_pixel;
    protected static CyclicBarrier quick_render_rendering_algorithm_barrier;
    protected static CyclicBarrier successive_refinement_rendering_algorithm_barrier;
    protected static AtomicInteger normal_rendering_algorithm_apply_palette;
    protected static AtomicInteger normal_rendering_algorithm_apply_palette2;
    protected static AtomicInteger normal_rendering_algorithm_post_processing;
    protected static AtomicInteger normal_rendering_algorithm_post_processing2;
    protected static AtomicInteger apply_skipped_color_pixel;
    protected static AtomicInteger normal_rendering_algorithm_histogram;
    private static CyclicBarrier color_cycling_filters_sync;
    private static CyclicBarrier color_cycling_restart_sync;
    private static ReadWriteLock color_cycling_toggle_lock;
    protected static ReadWriteLock stop_rendering_lock;
    protected static AtomicInteger reference_calc_sync;
    protected static CyclicBarrier reference_sync;

    protected static volatile boolean STOP_RENDERING;
    public static volatile boolean DONE;
    public static volatile int TOTAL_NUM_TASKS;

    public static void stopRendering() throws StopExecutionException {
        WaitOnCondition.LockWrite(stop_rendering_lock);
        STOP_RENDERING = true;
        WaitOnCondition.UnlockWrite(stop_rendering_lock);
    }

    protected static int getThreadChunkSize(int width, boolean per_row) {
        if (per_row) {
            return width;
        }
        return THREAD_CHUNK_SIZE;
    }

    static {
        SUCCESSIVE_REFINEMENT_MAX_SIZE = 2 << (SUCCESSIVE_REFINEMENT_EXPONENT - 1);

        int upper_bound = 256;
        THREAD_CHUNK_SIZE_PER_LEVEL = new int[SUCCESSIVE_REFINEMENT_EXPONENT + 1];
        int val = 2;
        for(int i = 0; i < THREAD_CHUNK_SIZE_PER_LEVEL.length; i++) {
            THREAD_CHUNK_SIZE_PER_LEVEL[i] = val;
            val <<= 1;
            val = Math.min(val, upper_bound);
        }

        val = 2;
        THREAD_CHUNK_SIZE_PER_LEVEL2 = new int[2 * THREAD_CHUNK_SIZE_PER_LEVEL.length - 1];
        if(THREAD_CHUNK_SIZE_PER_LEVEL2.length >= 1) {
            THREAD_CHUNK_SIZE_PER_LEVEL2[0] = val;
            val <<= 1;
            val = Math.min(val, upper_bound);
        }
        for(int i = 1; i < THREAD_CHUNK_SIZE_PER_LEVEL2.length - 1; i+=2) {
            THREAD_CHUNK_SIZE_PER_LEVEL2[i] = val;
            THREAD_CHUNK_SIZE_PER_LEVEL2[i + 1] = val;
            val <<= 1;
            val = Math.min(val, upper_bound);
        }

        SUCCESSIVE_REFINEMENT_SPLIT1 = new int[THREAD_CHUNK_SIZE_PER_LEVEL2.length];
        SUCCESSIVE_REFINEMENT_SPLIT2 = new int[THREAD_CHUNK_SIZE_PER_LEVEL2.length];
        SUCCESSIVE_REFINEMENT_SPLIT3 = new int[THREAD_CHUNK_SIZE_PER_LEVEL2.length];
        SUCCESSIVE_REFINEMENT_SPLIT4 = new int[THREAD_CHUNK_SIZE_PER_LEVEL2.length];

        for(int i = 0; i < SUCCESSIVE_REFINEMENT_SPLIT1.length; i++) {
            SUCCESSIVE_REFINEMENT_SPLIT1[i] = SUCCESSIVE_REFINEMENT_MAX_SIZE >> ((i + 1) >> 1);
            SUCCESSIVE_REFINEMENT_SPLIT2[i] = SUCCESSIVE_REFINEMENT_MAX_SIZE >> (i >> 1);
        }

        int val1 = 0;
        int val2 = 0;
        int increase1 = 1;
        int increase2 = 2;
        for(int i = 0; i < SUCCESSIVE_REFINEMENT_SPLIT3.length; i++) {
            SUCCESSIVE_REFINEMENT_SPLIT3[i] = SUCCESSIVE_REFINEMENT_MAX_SIZE >> val1;
            SUCCESSIVE_REFINEMENT_SPLIT4[i] = SUCCESSIVE_REFINEMENT_MAX_SIZE >> val2;
            if(increase1 < 2) {
                val1++;
                increase1++;
                increase2 = increase1 == 2 ? 0 : increase2;
            }
            else if(increase2 < 2) {
                val2++;
                increase2++;
                increase1 = increase2 == 2 ? 0 : increase1;
            }
        }

        setSuccessiveRefinementChunks();
    }

    public static void setSuccessiveRefinementChunks() {

        if(SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM == 1) {
            SUCCESSIVE_REFINEMENT_CHUNK_X = SUCCESSIVE_REFINEMENT_SPLIT2;
            SUCCESSIVE_REFINEMENT_CHUNK_Y = SUCCESSIVE_REFINEMENT_SPLIT1;
        }
        else if(SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM == 2) {
            SUCCESSIVE_REFINEMENT_CHUNK_X = SUCCESSIVE_REFINEMENT_SPLIT1;
            SUCCESSIVE_REFINEMENT_CHUNK_Y = SUCCESSIVE_REFINEMENT_SPLIT2;
        }
        else if(SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM == 3) {
            SUCCESSIVE_REFINEMENT_CHUNK_X = SUCCESSIVE_REFINEMENT_SPLIT3;
            SUCCESSIVE_REFINEMENT_CHUNK_Y = SUCCESSIVE_REFINEMENT_SPLIT4;
        }
        else if(SUCCESSIVE_REFINEMENT_SQUARE_RECT_SPLIT_ALGORITHM == 4) {
            SUCCESSIVE_REFINEMENT_CHUNK_X = SUCCESSIVE_REFINEMENT_SPLIT4;
            SUCCESSIVE_REFINEMENT_CHUNK_Y = SUCCESSIVE_REFINEMENT_SPLIT3;
        }
        else {
            SUCCESSIVE_REFINEMENT_CHUNK_X = null;
            SUCCESSIVE_REFINEMENT_CHUNK_Y = null;
        }

    }

    /**
     * ****************************
     */

    /**
     * ** 3D ***
     */
    private static long D3RenderingCalculationTime;
    protected boolean d3;
    protected D3Settings d3s;
    protected int detail;
    private double m20, m21, m22;

    public static double[][] vert;
    public static int[][] vert_color;
    private static float[][][] Norm1z;
    private static double[] gaussian_kernel;
    private static double[][] temp_array;
    private static double max;
    private static double min;

    private static double lowerFence;
    private static double upperFence;


    /**
     * ** Filters ***
     */
    protected FiltersSettings fs;
    private boolean[] filters;
    protected int[] filters_options_vals;
    protected int[][] filters_options_extra_vals;
    protected boolean fast_julia_filters;
    private Color[] filters_colors;
    private Color[][] filters_extra_colors;
    private int[] filters_order;
    private static long FilterCalculationTime;

    /**
     * ** Image Related ***
     */
    protected BufferedImage image;
    protected int[] rgbs;
    public static double[] image_iterations;
    protected static double[] domain_image_data_re;
    protected static double[] domain_image_data_im;
    protected static double[] image_iterations_fast_julia;
    protected static boolean[] escaped_fast_julia;
    protected static boolean[] escaped;
    public static int WIDTH;
    public static int HEIGHT;
    /**
     * ********************
     */

    /**
     * ** Post Processing ***
     */
    protected static final int MAX_BUMP_MAPPING_DEPTH = 100;
    protected static final int DEFAULT_BUMP_MAPPING_STRENGTH = 50;
    private int dem_color;
    private int[] post_processing_order;
    private boolean usePaletteForInColoring;
    private LightSettings ls;
    private NumericalDistanceEstimatorSettings ndes;
    private SlopeSettings ss;
    protected BumpMapSettings bms;
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
    private static volatile boolean color_cycling;
    private int color_cycling_location_outcoloring;
    private int color_cycling_location_incoloring;
    private int gradient_offset;

    private ColorCyclingSettings ccs;

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
    protected boolean domain_coloring;

    private Map<Coordinate, Double> edgeData;
    private Map<Coordinate, PixelExtraData> edgeAAData;
    private DomainColoringSettings ds;
    private boolean usesTrueColorIn;
    protected double height_ratio;
    protected boolean polar_projection;
    protected double circle_period;
    private int fractal_color;
    public static PaletteColor palette_outcoloring;
    public static PaletteColor palette_incoloring;
    private TransferFunction color_transfer_outcoloring;
    private TransferFunction color_transfer_incoloring;
    private Blending blending;
    private Blending ens_blending;
    private Blending rps_blending;

    private Blending ndes_blending;

    private boolean banded;
    private PostProcessing pp;

    private Blending hss_blending;
    private Blending ofs_blending;

    private InterpolationMethod method;

    private GeneratedPaletteSettings gps;
    //private BlendingSettings color_blending;
    protected DomainColoring domain_color;
    protected JitterSettings js;
    private double contourFactor;
    protected MainWindow ptr;
    private MinimalRendererWindow ptrMinimalRenderer;
    protected JProgressBar progress;
    protected int action;
    private boolean quickRender;
    protected int tile;
    protected boolean julia;
    private static String default_init_val;
    private static double convergent_bailout;
    public static int TILE_SIZE = 5;
    public static int QUICK_RENDER_DELAY = 1500; //msec
    public static int SKIPPED_PIXELS_ALG = 0;
    public static int SKIPPED_PIXELS_COLOR = 0xFFFFFFFF;
    public static int[] gradient;
    public static boolean HIGH_PRECISION_CALCULATION = false;
    public static boolean PERTURBATION_THEORY = false;
    public static int APPROXIMATION_ALGORITHM = 4;
    public static int SERIES_APPROXIMATION_TERMS = ApproximationDefaultSettings.SERIES_APPROXIMATION_TERMS;
    public static boolean USE_FULL_FLOATEXP_FOR_ALL_ZOOM = false;
    public static boolean USE_CUSTOM_FLOATEXP_REQUIREMENT = true;
    public static boolean USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = false;
    public static long SERIES_APPROXIMATION_OOM_DIFFERENCE = ApproximationDefaultSettings.SERIES_APPROXIMATION_OOM_DIFFERENCE;
    public static int SERIES_APPROXIMATION_MAX_SKIP_ITER = ApproximationDefaultSettings.SERIES_APPROXIMATION_MAX_SKIP_ITER;
    public static boolean BIGNUM_AUTOMATIC_PRECISION = true;
    public static int BIGNUM_PRECISION = 996;
    public static int BIGNUM_PRECISION_FACTOR = 1;
    public static boolean CALCULATE_PERIOD_EVERY_TIME_FROM_START = false;

    public static boolean USE_THREADS_IN_BIGNUM_LIBS = true;
    public static boolean USE_THREADS_FOR_SA = false;
    public static int BLA_BITS = ApproximationDefaultSettings.BLA_BITS;
    public static boolean USE_THREADS_FOR_BLA = true;
    public static boolean USE_THREADS_FOR_BLA2 = true;
    public static boolean USE_THREADS_FOR_BLA3 = true;
    public static boolean DETECT_PERIOD = true;
    public static int PERIOD_DETECTION_ALGORITHM = 2;
    public static boolean STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD = true;
    public static int PERTUBATION_PIXEL_ALGORITHM = 0;
    public static int BLA_STARTING_LEVEL = ApproximationDefaultSettings.BLA_STARTING_LEVEL;
    public static int BLA3_STARTING_LEVEL = ApproximationDefaultSettings.BLA3_STARTING_LEVEL;
    public static int NANOMB1_N = ApproximationDefaultSettings.NANOMB1_N;
    public static int NANOMB1_M = ApproximationDefaultSettings.NANOMB1_M;
    public static boolean GATHER_PERTURBATION_STATISTICS = false;
    public static boolean GATHER_HIGHPRECISION_STATISTICS = false;
    public static boolean CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE = false;
    public static boolean CHECK_BAILOUT_DURING_MIP_BLA_STEP = false;
    public static boolean GREEDY_ALGORITHM = true;
    public static boolean GREEDY_ALGORITHM_CHECK_ITER_DATA = true;
    public static boolean GATHER_TINY_REF_INDEXES = true;
    public static int GREEDY_ALGORITHM_SELECTION = Constants.PATTERNED_SUCCESSIVE_REFINEMENT;
    public static int BRUTE_FORCE_ALG = 0;
    public static int GUESS_BLOCKS_SELECTION = 3;
    public static boolean USE_SMOOTHING_FOR_PROCESSING_ALGS = true;
    public static boolean RENDER_IMAGE_PREVIEW = false;
    public static boolean LOAD_MPFR = true;
    public static boolean LOAD_MPIR = true;
    public static final String generalArchitecture = "general";
    public static final String generalVcpkgMsvcArchitecture = "general_vcpkg_msvc";
    public static String MPIR_WINDOWS_ARCHITECTURE = "skylake_avx2";
    public static String MPFR_WINDOWS_ARCHITECTURE = "skylake_avx2";
    public static final String[] mpirWinArchitecture = {"skylake_avx2", "haswell_avx2", "sandybridge_ivybridge"};
    public static final String[] mpfrWinArchitecture = {"skylake_avx2", "haswell_avx2", "sandybridge_ivybridge", generalVcpkgMsvcArchitecture, generalArchitecture};
    public static Random generator;
    public static int D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS = 1;
    public static int PATTERN_COMPARE_ALG = 0;
    public static boolean PATTERN_REVERT_ALG = false;
    public static boolean PATTERN_REPEAT_ALG = false;
    public static boolean PATTERN_CENTER = true;
    public static double PATTERN_REPEAT_SPACING = 25;
    public static double PATTERN_N = 2.0;
    public static boolean LOAD_RENDERING_ALGORITHM_FROM_SAVES = false;
    public static int MANTEXPCOMPLEX_FORMAT = 0;
    public static long SEED = 0;

    public static boolean SMOOTH_DATA = false;
    public static ThreadPoolExecutor approximation_thread_executor;
    public static ThreadPoolExecutor reference_thread_executor;
    //public static ThreadPoolExecutor reference_thread_executor2;
    public static ThreadPoolExecutor thread_calculation_executor;
    public static ThreadPoolExecutor julia_map_thread_calculation_executor;
    public static ExecutorService single_thread_executor;
    public static ThreadPoolExecutor action_thread_executor;

    protected int progress_one_percent;

    public static void initThreadPoolExecutor(int numThreads) {
        if(thread_calculation_executor != null) {
            thread_calculation_executor.shutdown();
        }
        thread_calculation_executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
    }

    public static void shutdownThreadPools() {
        if(reference_thread_executor != null) {
            reference_thread_executor.shutdownNow();
        }
//        if(reference_thread_executor2 != null) {
//            reference_thread_executor2.shutdown();
//        }
        if(thread_calculation_executor != null) {
            thread_calculation_executor.shutdownNow();
        }
        if(julia_map_thread_calculation_executor != null) {
            julia_map_thread_calculation_executor.shutdownNow();
        }
        if(single_thread_executor != null) {
            single_thread_executor.shutdownNow();
        }
        if(action_thread_executor != null) {
            action_thread_executor.shutdownNow();
        }
        if(approximation_thread_executor != null) {
            approximation_thread_executor.shutdownNow();
        }
    }

    public static long getActiveThreadCount() {
        long count = 0;

        if(reference_thread_executor != null) {
            count += reference_thread_executor.getActiveCount();
        }
//        if(reference_thread_executor2 != null) {
//            count += reference_thread_executor2.getActiveCount();
//        }
        if(thread_calculation_executor != null) {
            count += thread_calculation_executor.getActiveCount();
        }
        if(julia_map_thread_calculation_executor != null) {
            count += julia_map_thread_calculation_executor.getActiveCount();
        }
        if(action_thread_executor != null) {
            count += action_thread_executor.getActiveCount();
        }
        if(approximation_thread_executor != null) {
            count += approximation_thread_executor.getActiveCount();
        }

        count += ForkJoinPool.commonPool().getActiveThreadCount();

        return count;
    }

    public static void reSeed() {
        if(SEED == 0) {
            generator = new Random(System.currentTimeMillis());
        }
        else {
            generator = new Random(SEED);
        }
        randomNumber = generator.nextInt(100000);
    }
    

    static {

        reSeed();
        default_init_val = "c";
        convergent_bailout = 0;
        List<Color> colors = ColorGenerator.generate(600, 0, 0);
        algorithm_colors = new int[colors.size()];

        for (int i = 0; i < algorithm_colors.length; i++) {
            algorithm_colors[i] = colors.get(i).getRGB();
        }

        algorithm_colors2 = new int[200];
        setAlgorithmColors();

        if (Runtime.getRuntime().availableProcessors() >= 2) {
            reference_thread_executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        }

//        if (Runtime.getRuntime().availableProcessors() >= 3) {
//            reference_thread_executor2 = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
//        }
    }

    public static void setAlgorithmColors() {
        Random random = new Random(5);
        for (int i = 0; i < algorithm_colors2.length; i++) {
            algorithm_colors2[i] = Color.HSBtoRGB(random.nextFloat(), TaskRender.HSB_CONSTANT_S, TaskRender.HSB_CONSTANT_B);
        }
    }

    public TaskRender() {

    }

    private void setPostProcessingData(PostProcessSettings pps) {
        ls = pps.ls;
        ss = pps.ss;
        bms = pps.bms;
        fdes = pps.fdes;
        sts = pps.sts;
        ens = pps.ens;
        rps = pps.rps;
        ots = pps.ots;
        cns = pps.cns;
        hss = pps.hss;
        gss = pps.gss;
        ofs = pps.ofs;
        ndes = pps.ndes;

        setPostProcessingBlending();
    }

    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        this.contourFactor = contourFactor;
        setPostProcessingData(pps);
        SMOOTH_DATA = needsSmoothing(fns, ndes, ls, ss, bms, cns, ens, rps, fdes, sts);
        banded = fns.banded;
        this.gps = gps;
        this.js = js;
        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);
        settingsFractal(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, d3s, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, SMOOTH_DATA, periodicity_checking, fns.plane_type, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.perturbation_user_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.initial_value_user_formula, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_center_hp, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, polar_projection, circle_period, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, fns.laguerre_deg, color_blending, fns.kleinianLine, fns.kleinianK, fns.kleinianM, post_processing_order, pbs, fns.gcs, fns.durand_kerner_init_val, fns.mps, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.gcps, fns.igs, fns.lfns, fns.newton_hines_k, fns.tcs, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.root_initialization_method, fns.preffs, fns.postffs, fns.ips, fns.defaultNovaInitialValue, fns.cbs, fns.useGlobalMethod, fns.globalMethodFactor, fns.period, fns.variable_re, fns.variable_im, fns.inflections_re, fns.inflections_im, fns.inflectionsPower, Settings.toComplex(fns.zenex_re, fns.zenex_im));
    }

    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        this.contourFactor = contourFactor;
        setPostProcessingData(pps);
        SMOOTH_DATA = needsSmoothing(fns, ndes, ls, ss, bms, cns, ens, rps, fdes, sts);
        banded = fns.banded;
        this.gps = gps;
        this.js = js;
        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);
        settingsFractalMinimalRenderer(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, SMOOTH_DATA, periodicity_checking, fns.plane_type, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.perturbation_user_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.initial_value_user_formula, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_center_hp, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, polar_projection, circle_period, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, fns.laguerre_deg, color_blending, fns.kleinianLine, fns.kleinianK, fns.kleinianM, post_processing_order, pbs, fns.gcs, fns.durand_kerner_init_val, fns.mps, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.gcps, fns.igs, fns.lfns, fns.newton_hines_k, fns.tcs, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.root_initialization_method, fns.preffs, fns.postffs, fns.ips, fns.defaultNovaInitialValue, fns.cbs, fns.useGlobalMethod, fns.globalMethodFactor, fns.period, fns.variable_re, fns.variable_im, fns.inflections_re, fns.inflections_im, fns.inflectionsPower, Settings.toComplex(fns.zenex_re, fns.zenex_im));
    }

    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        this.contourFactor = contourFactor;
        setPostProcessingData(pps);
        SMOOTH_DATA = needsSmoothing(fns, ndes, ls, ss, bms, cns, ens, rps, fdes, sts);
        banded = fns.banded;
        this.gps = gps;
        this.js = js;
        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);
        settingsJulia(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, d3s, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, SMOOTH_DATA, periodicity_checking, fns.plane_type, fns.apply_plane_on_julia, fns.apply_plane_on_julia_seed, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_center_hp, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, polar_projection, circle_period, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds, inverse_dem, quickRender, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending, post_processing_order, pbs, fns.gcs, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.laguerre_deg, fns.gcps, fns.lfns, fns.newton_hines_k, fns.tcs, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.preffs, fns.postffs, fns.ips, fns.juliter, fns.juliterIterations, fns.juliterIncludeInitialIterations, fns.defaultNovaInitialValue, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.perturbation_user_formula, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.initial_value_user_formula, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.cbs, fns.useGlobalMethod, fns.globalMethodFactor, fns.variable_re, fns.variable_im, fns.inflections_re, fns.inflections_im, fns.inflectionsPower, Settings.toComplex(fns.zenex_re, fns.zenex_im), xJuliaCenter, yJuliaCenter);
    }

    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        this.contourFactor = contourFactor;
        setPostProcessingData(pps);
        SMOOTH_DATA = needsSmoothing(fns, ndes, ls, ss, bms, cns, ens, rps, fdes, sts);
        banded = fns.banded;
        this.gps = gps;
        this.js = js;
        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);
        settingsJuliaMinimalRenderer(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, SMOOTH_DATA, periodicity_checking, fns.plane_type, fns.apply_plane_on_julia, fns.apply_plane_on_julia_seed, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_center_hp, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, polar_projection, circle_period, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, ds, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending, post_processing_order, pbs, fns.gcs, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.laguerre_deg, fns.gcps, fns.lfns, fns.newton_hines_k, fns.tcs, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.preffs, fns.postffs, fns.ips, fns.juliter, fns.juliterIterations, fns.juliterIncludeInitialIterations, fns.defaultNovaInitialValue, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.perturbation_user_formula, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.initial_value_user_formula, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.cbs, fns.useGlobalMethod, fns.globalMethodFactor, fns.variable_re, fns.variable_im, fns.inflections_re, fns.inflections_im, fns.inflectionsPower, Settings.toComplex(fns.zenex_re, fns.zenex_im), xJuliaCenter, yJuliaCenter);
    }

    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, boolean periodicity_checking, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps) {
        this.contourFactor = contourFactor;
        setPostProcessingData(pps);
        SMOOTH_DATA = needsSmoothing(fns, ndes, ls, ss, bms, cns, ens, rps, fdes, sts);
        banded = fns.banded;
        this.gps = gps;
        this.js = js;
        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);
        settingsJuliaMap(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, ptr, fractal_color, dem_color, image, fs, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, SMOOTH_DATA, periodicity_checking, fns.plane_type, fns.apply_plane_on_julia, fns.apply_plane_on_julia_seed, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_center_hp, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, polar_projection, circle_period, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending, post_processing_order, pbs, fns.gcs, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.laguerre_deg, fns.gcps, fns.lfns, fns.newton_hines_k, fns.tcs, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.preffs, fns.postffs, fns.ips, fns.juliter, fns.juliterIterations, fns.juliterIncludeInitialIterations, fns.defaultNovaInitialValue, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.perturbation_user_formula, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.initial_value_user_formula, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.cbs, fns.useGlobalMethod, fns.globalMethodFactor, fns.variable_re, fns.variable_im, fns.inflections_re, fns.inflections_im, fns.inflectionsPower, Settings.toComplex(fns.zenex_re, fns.zenex_im));
    }

    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, FunctionSettings fns, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, FiltersSettings fs, int color_cycling_location, int color_cycling_location2, boolean exterior_de, double exterior_de_factor, double height_ratio, boolean polar_projection, double circle_period, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, JitterSettings js, PostProcessSettings pps, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {
        this.contourFactor = contourFactor;
        setPostProcessingData(pps);
        SMOOTH_DATA = needsSmoothing(fns, ndes, ls, ss, bms, cns, ens, rps, fdes, sts);
        banded = fns.banded;
        this.gps = gps;
        this.js = js;
        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);
        settingsJuliaPreview(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, fns.bailout_test_algorithm, fns.bailout, fns.bailout_test_user_formula, fns.bailout_test_user_formula2, fns.bailout_test_comparison, fns.n_norm, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, fns.plane_type, fns.apply_plane_on_julia, fns.apply_plane_on_julia_seed, fns.out_coloring_algorithm, fns.user_out_coloring_algorithm, fns.outcoloring_formula, fns.user_outcoloring_conditions, fns.user_outcoloring_condition_formula, fns.in_coloring_algorithm, fns.user_in_coloring_algorithm, fns.incoloring_formula, fns.user_incoloring_conditions, fns.user_incoloring_condition_formula, SMOOTH_DATA, fs, fns.burning_ship, fns.mandel_grass, fns.mandel_grass_vals, fns.function, fns.z_exponent, fns.z_exponent_complex, color_cycling_location, color_cycling_location2, fns.rotation_vals, fns.rotation_center, fns.coefficients, fns.z_exponent_nova, fns.relaxation, fns.nova_method, fns.user_formula, fns.user_formula2, fns.bail_technique, fns.user_plane, fns.user_plane_algorithm, fns.user_plane_conditions, fns.user_plane_condition_formula, fns.user_formula_iteration_based, fns.user_formula_conditions, fns.user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, fns.plane_transform_center, fns.plane_transform_center_hp, fns.plane_transform_angle, fns.plane_transform_radius, fns.plane_transform_scales, fns.plane_transform_wavelength, fns.waveType, fns.plane_transform_angle2, fns.plane_transform_sides, fns.plane_transform_amount, fns.escaping_smooth_algorithm, fns.converging_smooth_algorithm, polar_projection, circle_period, fns.coupling, fns.user_formula_coupled, fns.coupling_method, fns.coupling_amplitude, fns.coupling_frequency, fns.coupling_seed, inverse_dem, color_intensity, transfer_function, color_density, color_intensity2, transfer_function2, color_density2, usePaletteForInColoring, color_blending, post_processing_order, pbs, fns.gcs, fns.coefficients_im, fns.lpns.lyapunovFinalExpression, fns.lpns.useLyapunovExponent, gradient_offset, fns.lpns.lyapunovFunction, fns.lpns.lyapunovExponentFunction, fns.lpns.lyapunovVariableId, fns.user_fz_formula, fns.user_dfz_formula, fns.user_ddfz_formula, fns.user_dddfz_formula, fns.user_relaxation_formula, fns.user_nova_addend_formula, fns.laguerre_deg, fns.gcps, fns.lfns, fns.newton_hines_k, fns.tcs, fns.lpns.lyapunovInitialValue, fns.lpns.lyapunovInitializationIteratons, fns.lpns.lyapunovskipBailoutCheck, fns.preffs, fns.postffs, fns.ips, fns.juliter, fns.juliterIterations, fns.juliterIncludeInitialIterations, fns.defaultNovaInitialValue, fns.perturbation, fns.perturbation_vals, fns.variable_perturbation, fns.user_perturbation_algorithm, fns.perturbation_user_formula, fns.user_perturbation_conditions, fns.user_perturbation_condition_formula, fns.init_val, fns.initial_vals, fns.variable_init_value, fns.user_initial_value_algorithm, fns.initial_value_user_formula, fns.user_initial_value_conditions, fns.user_initial_value_condition_formula, fns.cbs, fns.useGlobalMethod, fns.globalMethodFactor, fns.variable_re, fns.variable_im, fns.inflections_re, fns.inflections_im, fns.inflectionsPower, Settings.toComplex(fns.zenex_re, fns.zenex_im), xJuliaCenter, yJuliaCenter);
    }
    //Fractal
    private void settingsFractal(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm,  boolean polar_projection, double circle_period,   String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    double[] laguerre_deg, BlendingSettings color_blending,   double[] kleinianLine, double kleinianK, double kleinianM, int[] post_processing_order,   PaletteGradientMergingSettings pbs,  GenericCaZbdZeSettings gcs, double[] durand_kernel_init_val, MagneticPendulumSettings mps, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, String lyapunovInitialValue,  int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, int root_initialization_method, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean defaultNovaInitialValue, ConvergentBailoutConditionSettings cbs,  boolean useGlobalMethod, double[] globalMethodFactor, int period, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_center = rotation_center;
        this.rotation_vals = rotation_vals;

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.fs = fs;
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
        this.d3s = d3s;

        this.post_processing_order = post_processing_order;

        this.pbs = pbs;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;
        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        progress = ptr.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

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
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        this.quickRender = quickRender;
        tile = TILE_SIZE;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        FractalFactory ff = new FractalFactory(ptr.getSettings(), method);

        fractal = ff.fractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kernel_init_val, mps, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue, cbs, useGlobalMethod, globalMethodFactor, period, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs);

        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = ff.fractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kernel_init_val, mps, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue, cbs, useGlobalMethod, globalMethodFactor, period, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(false);
            }
        }

        setTrueColoringOptions(tcs);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        iteration_algorithm = new FractalIterationAlgorithm(fractal);

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD, COLOR_SPACE);
        }

        rendering_done = 0;
        task_calculated = 0;
        task_calculated_extra = 0;

    }

    //Fractal-MinimalRenderer
    private void settingsFractalMinimalRenderer(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean polar_projection, double circle_period, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, double[] laguerre_deg, BlendingSettings color_blending, double[] kleinianLine, double kleinianK, double kleinianM, int[] post_processing_order, PaletteGradientMergingSettings pbs, GenericCaZbdZeSettings gcs, double[] durand_kernel_init_val, MagneticPendulumSettings mps, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, int root_initialization_method, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean defaultNovaInitialValue, ConvergentBailoutConditionSettings cbs, boolean useGlobalMethod, double[] globalMethodFactor, int period, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_center = rotation_center;
        this.rotation_vals = rotation_vals;

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptrMinimalRenderer = ptr;
        this.fs = fs;
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

        this.post_processing_order = post_processing_order;

        this.pbs = pbs;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;
        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        progress = ptrMinimalRenderer.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

        if (domain_coloring) {
            if (polar_projection) {
                action = DOMAIN_POLAR_MINIMAL_RENDERER;
            } else {
                action = DOMAIN_MINIMAL_RENDERER;
            }
        } else if (polar_projection) {
            action = POLAR_MINIMAL_RENDERER;
        } else {
            action = NORMAL_MINIMAL_RENDERER;
        }

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        tile = TILE_SIZE;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        FractalFactory ff = new FractalFactory(ptrMinimalRenderer.getSettings(), method);

        fractal = ff.fractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kernel_init_val, mps, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue, cbs, useGlobalMethod, globalMethodFactor, period, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs);

        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = ff.fractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kernel_init_val, mps, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue, cbs, useGlobalMethod, globalMethodFactor, period, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(false);
            }
        }

        setTrueColoringOptions(tcs);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        iteration_algorithm = new FractalIterationAlgorithm(fractal);

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD, COLOR_SPACE);
        }

        rendering_done = 0;
        task_calculated = 0;
        task_calculated_extra = 0;

    }

    //Julia
    private void settingsJulia(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm,  boolean polar_projection, double circle_period,   double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickRender, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs,  String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String perturbation_user_formula, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String initial_value_user_formula, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, ConvergentBailoutConditionSettings cbs,  boolean useGlobalMethod, double[] globalMethodFactor, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.fs = fs;
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
        this.height_ratio = height_ratio;
        this.d3s = d3s;

        this.post_processing_order = post_processing_order;

        this.pbs = pbs;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;
        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        progress = ptr.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

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
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        FractalFactory ff = new FractalFactory(ptr.getSettings(), method);

        fractal = ff.juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, cbs, useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);

        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = ff.juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, cbs, useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(true);
                ((Equicontinuity) fractal.getStatisticInstance()).setJuliIter(juliter);
            }
        }

        setTrueColoringOptions(tcs);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        this.quickRender = quickRender;
        tile = TILE_SIZE;

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD, COLOR_SPACE);
        }

        rendering_done = 0;
        task_calculated = 0;
        task_calculated_extra = 0;
        julia = true;

    }

    //Julia-MinimalRenderer
    private void settingsJuliaMinimalRenderer(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MinimalRendererWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean polar_projection, double circle_period, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs, String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String perturbation_user_formula, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String initial_value_user_formula, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, ConvergentBailoutConditionSettings cbs, boolean useGlobalMethod, double[] globalMethodFactor, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptrMinimalRenderer = ptr;
        this.fs = fs;
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

        this.post_processing_order = post_processing_order;


        this.pbs = pbs;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        this.domain_coloring = ds.domain_coloring;
        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        progress = ptrMinimalRenderer.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

        if (domain_coloring) {
            if (polar_projection) {
                action = DOMAIN_POLAR_MINIMAL_RENDERER;
            } else {
                action = DOMAIN_MINIMAL_RENDERER;
            }
        } else if (polar_projection) {
            action = POLAR_MINIMAL_RENDERER;
        } else {
            action = NORMAL_MINIMAL_RENDERER;
        }

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        FractalFactory ff = new FractalFactory(ptrMinimalRenderer.getSettings(), method);

        fractal = ff.juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, cbs,  useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);

        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = ff.juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, cbs,  useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(true);
                ((Equicontinuity) fractal.getStatisticInstance()).setJuliIter(juliter);
            }
        }

        setTrueColoringOptions(tcs);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        tile = TILE_SIZE;

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD, COLOR_SPACE);
        }

        rendering_done = 0;
        task_calculated = 0;
        task_calculated_extra = 0;
        julia = true;

    }

    //Julia Map
    private void settingsJuliaMap(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm,  boolean polar_projection, double circle_period,   double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs,  String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String perturbation_user_formula, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String initial_value_user_formula, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, ConvergentBailoutConditionSettings cbs,  boolean useGlobalMethod, double[] globalMethodFactor, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.fs = fs;
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

        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        this.post_processing_order = post_processing_order;

        this.pbs = pbs;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        progress = ptr.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        if (polar_projection) {
            action = JULIA_MAP_POLAR;
        } else {
            action = JULIA_MAP;
        }

        Apfloat xJuliaCenter, yJuliaCenter;

        if (polar_projection) {

            PolarLocationNormalApfloatArbitrary location = new PolarLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image.getWidth(), image.getHeight(), circle_period);
            BigPoint p = location.getPoint((int)(FROMx + (TOx - FROMx) * 0.5), (int)(FROMy + (TOy - FROMy) * 0.5));
            p = MathUtils.rotatePointRelativeToPoint(p, rotation_vals, rotation_center);

            xJuliaCenter = p.x;
            yJuliaCenter = p.y;
        } else {
            CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image.getWidth(), image.getHeight());
            BigPoint p = location.getPoint((int)(FROMx + (TOx - FROMx) * 0.5), (int)(FROMy + (TOy - FROMy) * 0.5));

            p = MathUtils.rotatePointRelativeToPoint(p, rotation_vals, rotation_center);

            xJuliaCenter = p.x;
            yJuliaCenter = p.y;
        }

        double mapxCenter = 0;
        double mapyCenter = 0;

        if (function == MainWindow.FORMULA27) {
            mapxCenter = -2;
        }

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        FractalFactory ff = new FractalFactory(ptr.getSettings(), method);

        fractal = ff.juliaFactory(function, mapxCenter, mapyCenter, size, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, cbs,  useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);
        fractal.setJuliaMap(true);

        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = ff.juliaFactory(function, mapxCenter, mapyCenter, size, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, cbs, useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);
            f2.setJuliaMap(true);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(true);
                ((Equicontinuity) fractal.getStatisticInstance()).setJuliIter(juliter);
            }
        }

        setTrueColoringOptions(tcs);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        default_init_val = fractal.getInitialValue();

        convergent_bailout = fractal.getConvergentBailout();

        rendering_done = 0;
        julia = true;

    }

    //Julia Preview
    private void settingsJuliaPreview(int FROMx, int TOx, int FROMy, int TOy, Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, FiltersSettings fs, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, int color_cycling_location2, Apfloat[] rotation_vals, Apfloat[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm,  boolean polar_projection, double circle_period,   double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring,    BlendingSettings color_blending,   int[] post_processing_order,   PaletteGradientMergingSettings pbs,  GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, boolean useLyapunovExponent, int gradient_offset, String lyapunovFunction, String lyapunovExponentFunction, int lyapunovVariableId, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, TrueColorSettings tcs,  String lyapunovInitialValue, int lyapunovInitializationIteratons, boolean lyapunovskipBailoutCheck, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String perturbation_user_formula, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String initial_value_user_formula, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, ConvergentBailoutConditionSettings cbs,  boolean useGlobalMethod, double[] globalMethodFactor, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs, Apfloat xJuliaCenter, Apfloat yJuliaCenter) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.fast_julia_filters = fast_julia_filters;
        this.fs = fs;
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

        this.post_processing_order = post_processing_order;

        this.pbs = pbs;

        this.polar_projection = polar_projection;
        this.circle_period = circle_period;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        progress = ptr.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        if (polar_projection) {
            action = FAST_JULIA_POLAR;
        } else {
            action = FAST_JULIA;
        }

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        FractalFactory ff = new FractalFactory(ptr.getSettings(), method);

        fractal = ff.juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, cbs, useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);

        if(sts.statistic && sts.statisticGroup == 2) {
            Fractal f2 = fractal = ff.juliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size, size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, Settings.fromDDArray(rotation_vals), Settings.fromDDArray(rotation_center), burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, gcs, lyapunovExpression, ots, exterior_de, exterior_de_factor, inverse_dem, escaping_smooth_algorithm, converging_smooth_algorithm, sts, useLyapunovExponent, lyapunovFunction, lyapunovExponentFunction, lyapunovVariableId, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, tcs, lyapunovInitialValue, lyapunovInitializationIteratons, lyapunovskipBailoutCheck, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, cbs, useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);
            if(fractal.getStatisticInstance() != null) {
                ((Equicontinuity) fractal.getStatisticInstance()).setFractal(f2);
                ((Equicontinuity) fractal.getStatisticInstance()).setJulia(true);
                ((Equicontinuity) fractal.getStatisticInstance()).setJuliIter(juliter);
            }
        }

        setTrueColoringOptions(tcs);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        iteration_algorithm = new JuliaIterationAlgorithm(fractal);

        task_calculated = -1;
        julia = true;

    }

    //Color Cycling
    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, DomainColoringSettings ds, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, ColorCyclingSettings ccs, boolean banded) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.image = image;
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.fs = fs;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.contourFactor = contourFactor;
        this.gps = gps;

        action = COLOR_CYCLING;

        this.banded = banded;

        this.ccs = ccs;

        this.post_processing_order = post_processing_order;

        setPostProcessingData(pps);

        this.bms = new BumpMapSettings(bms);
        this.ls = new LightSettings(ls);
        this.ss = new SlopeSettings(ss);
        this.pbs = pbs;

        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);

        domain_coloring = ds.domain_coloring;
        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        progress = ptr.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD, COLOR_SPACE);
        }

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        task_calculated = -1;

    }

    //Apply Filter
    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, DomainColoringSettings ds, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, boolean banded) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.fs = fs;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.contourFactor = contourFactor;
        action = APPLY_PALETTE_AND_FILTER;
        this.gps = gps;

        this.post_processing_order = post_processing_order;

        setPostProcessingData(pps);

        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);

        this.pbs = pbs;

        this.banded = banded;

        domain_coloring = ds.domain_coloring;
        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        progress = ptr.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        if (domain_coloring) {
            domainColoringFactory(ds, COLOR_SMOOTHING_METHOD, COLOR_SPACE);
        }

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        rendering_done = 0;

        task_calculated = -1;

    }

    //Rotate 3d model
    public TaskRender(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean render_action, MainWindow ptr, BufferedImage image, FiltersSettings fs, BlendingSettings color_blending, double contourFactor) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.image = image;
        this.fs = fs;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.d3 = true;
        this.detail = d3s.detail;
        this.d3s = d3s;
        this.contourFactor = contourFactor;

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        progress = ptr.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        if (render_action) {
            action = ROTATE_3D_MODEL;
        } else {
            action = APPLY_PALETTE_AND_FILTER_3D_MODEL;
            rendering_done = 0;
        }

        task_calculated = -1;

    }

    //AA Palette and Post Processing
    public TaskRender(int action , int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, int color_cycling_location2, FiltersSettings fs, double color_intensity, int transfer_function, double color_density, double color_intensity2, int transfer_function2, double color_density2, boolean usePaletteForInColoring, BlendingSettings color_blending, int[] post_processing_order, PaletteGradientMergingSettings pbs, int gradient_offset, double contourFactor, GeneratedPaletteSettings gps, PostProcessSettings pps, DomainColoringSettings ds, boolean banded) {

        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
        this.ptr = ptr;
        this.image = image;
        this.fractal_color = fractal_color.getRGB();
        this.dem_color = dem_color.getRGB();
        this.color_cycling_location_outcoloring = color_cycling_location;
        this.color_cycling_location_incoloring = color_cycling_location2;
        this.gradient_offset = gradient_offset;
        this.fs = fs;
        this.filters = fs.filters;
        this.filters_colors = fs.filters_colors;
        this.filters_extra_colors = fs.filters_extra_colors;
        this.filters_order = fs.filters_order;
        this.filters_options_vals = fs.filters_options_vals;
        this.filters_options_extra_vals = fs.filters_options_extra_vals;
        this.contourFactor = contourFactor;
        this.action = action;
        this.gps = gps;

        this.domain_coloring = ds.domain_coloring;
        ColorAlgorithm.DomainColoringBypass = domain_coloring;

        this.post_processing_order = post_processing_order;

        setPostProcessingData(pps);

        pp = new PostProcessing(this, max_iterations, gradient_offset, pps);

        this.pbs = pbs;

        this.banded = banded;

        progress = ptr.getProgressBar();
        progress_one_percent = progress.getMaximum() / 100;

        this.usePaletteForInColoring = usePaletteForInColoring;
        colorTransferFactory(transfer_function, transfer_function2, color_intensity, color_intensity2, color_density, color_density2);

        blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, color_blending.color_blending, COLOR_SPACE);
        blending.setReverseColors(color_blending.blending_reversed_colors);

        interpolationFactory(COLOR_SMOOTHING_METHOD, COLOR_SPACE);

        rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        rendering_done = 0;

        task_calculated = -1;

    }

    @Override
    public void run() {

        Thread t = Thread.currentThread();
        doneByThreadName = t.getName();
        doneByThreadId = t.getId();

        try {
            switch (action) {

                case NORMAL:
                    if (quickRender) {
                        quickRendering();
                    } else {
                        rendering();
                    }
                    break;
                case FAST_JULIA:
                    fastJuliaRendering();
                    break;
                case COLOR_CYCLING:
                    colorCycling();
                    break;
                case APPLY_PALETTE_AND_FILTER:
                    applyPaletteAndFilter();
                    break;
                case JULIA_MAP:
                    juliaMapRendering();
                    break;
                case ROTATE_3D_MODEL:
                    rotate3DModel();
                    break;
                case APPLY_PALETTE_AND_FILTER_3D_MODEL:
                    applyPaletteAndFilter3DModel();
                    break;
                case POLAR:
                    if (quickRender) {
                        quickPolarRendering();
                    } else {
                        polarRendering();
                    }
                    break;
                case FAST_JULIA_POLAR:
                    fastJuliaRenderingPolar();
                    break;
                case JULIA_MAP_POLAR:
                    juliaMapPolarRendering();
                    break;
                case DOMAIN:
                    if (quickRender) {
                        quickDomainRendering();
                    } else {
                        domainRendering();
                    }
                    break;
                case DOMAIN_POLAR:
                    if (quickRender) {
                        quickDomainPolarRendering();
                    } else {
                        domainPolarRendering();
                    }
                    break;
                case NORMAL_MINIMAL_RENDERER:
                    minimalRendererRendering();
                    break;
                case POLAR_MINIMAL_RENDERER:
                    polarMinimalRendererRendering();
                    break;
                case DOMAIN_MINIMAL_RENDERER:
                    domainMinimalRendererRendering();
                    break;
                case DOMAIN_POLAR_MINIMAL_RENDERER:
                    domainPolarMinimalRendererRendering();
                    break;
                case POST_PROCESSING_WITH_AA_AND_FILTER:
                    applyPostProcessingWithAAandFilter();
                    break;
                case APPLY_PALETTE_AND_POST_PROCESSING_WITH_AA_AND_FILTER:
                    applyPaletteAndFilterWithAA();
                    break;
            }
        }
        catch (StopExecutionException | IllegalMonitorStateException | StopSuccessiveRefinementException ex) {

        } catch (OutOfMemoryError e) {
            if (ptrMinimalRenderer != null) {
                JOptionPane.showMessageDialog(ptrMinimalRenderer, "Maximum Heap size was reached.\nPlease set the maximum Heap size to a higher value.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                ptrMinimalRenderer.savePreferences();
            } else {
                JOptionPane.showMessageDialog(ptr, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                ptr.savePreferences();
            }
            e.printStackTrace();
            NumericLibrary.deleteLibs();
            TaskRender.shutdownThreadPools();
            System.exit(-1);
        } catch (Throwable ex) {
//            if (ptrMinimalRenderer != null) {
//                JOptionPane.showMessageDialog(ptrMinimalRenderer, "An error has occurred.", "Error!", JOptionPane.ERROR_MESSAGE);
//                ptrMinimalRenderer.savePreferences();
//            } else {
//                JOptionPane.showMessageDialog(ptr, "An error has occurred.", "Error!", JOptionPane.ERROR_MESSAGE);
//                ptr.savePreferences();
//            }

            ex.printStackTrace();
            NumericLibrary.deleteLibs();
            TaskRender.shutdownThreadPools();
            System.exit(-1);
        }
        DONE = true;
        int number = number_of_tasks.decrementAndGet();

        if(ptr != null && number == 0) {
            ptr.getMainPanel().stopTimer();
        }
    }


    public TaskStatistic getTaskStatistic() {

        if(action == COLOR_CYCLING) {
            return null;
        }

        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;

        int supersampling_num;
        if(!filters[MainWindow.ANTIALIASING]) {
            supersampling_num = 1;
        }
        else {
            supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod) + 1;
        }

        return new TaskStatistic(taskId, doneByThreadName, pixel_calculation_time_per_task, post_processing_calculation_time_per_task, task_calculated, task_calculated_extra, task_completed, task_post_processed, WIDTH * HEIGHT, supersampling_num, new Color(algorithm_colors[(int)(getRandomThreadId() % algorithm_colors.length)]));
    }

    private long getRandomThreadId() {
        return (doneByThreadId + randomNumber ) * 19;
    }

    private void domainMinimalRendererRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            renderDomainAntialiased(image_width, image_height, false);
        } else {
            renderDomain(image_width, image_height, false);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptrMinimalRenderer.getNumberOfThreads()) {

            image_iterations = null;
            escaped = null;

            applyFilters();

            progress.setValue(progress.getMaximum());

            setFullToolTipMessage(image_width * image_height);

            ptrMinimalRenderer.writeImageToDisk();

            ptrMinimalRenderer.setOptions(true);
        }
    }

    private String _getBigNumLibString(String name) {
        return "<li>BigNum Implementation: <b>" + name + "<b><br>";
    }

    private String _getBigNumLibPrecString(long val) {
        return "<li>BigNum Precision: <b>" + val + " bits</b><br>";
    }

    private String getFloatExpString(boolean isDeep) {

        if(!domain_coloring && !HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && fractal.supportsPerturbationTheory()) {
            if(isDeep) {
                return "<li>Deltas Implementation: <b>Extended Range</b><br>" +
                        "<li>Complex Extended Range Format: " +
                        (MANTEXPCOMPLEX_FORMAT == 0 ? "<b>Combined Exponent</b>" : "<b>Two Exponents</b>") + "<br>";
            }
            else {
                return "<li>Deltas Implementation: <b>Double</b><br>";
            }
        }
        return "";
    }
    private String getBigNumString(int bigNumLib, int arbitraryLib) {

        if(!domain_coloring && HIGH_PRECISION_CALCULATION && fractal.supportsPerturbationTheory()) {
            if(arbitraryLib == Constants.ARBITRARY_BUILT_IN && fractal.supportsBignum()) {
                return _getBigNumLibString(BigNum.getName()) + _getBigNumLibPrecString(BigNum.getPrecision());
            }
            else if(arbitraryLib == Constants.ARBITRARY_BIGINT && fractal.supportsBigIntnum()) {
                return _getBigNumLibString("Fixed Point BigInteger") + _getBigNumLibPrecString(BigIntNum.getPrecision());
            }
            else if(arbitraryLib == Constants.ARBITRARY_MPFR && fractal.supportsMpfrBignum()) {
                return _getBigNumLibString("MPFR " + LibMpfr.mpfr_version) + _getBigNumLibPrecString(MpfrBigNum.precision);
            }
            else if(arbitraryLib == Constants.ARBITRARY_MPIR && fractal.supportsMpirBignum()) {
                return _getBigNumLibString("MPIR " + LibMpir.__mpir_version) + _getBigNumLibPrecString(MpirBigNum.precision);
            }
            else if(arbitraryLib == Constants.ARBITRARY_DOUBLEDOUBLE) {
                return _getBigNumLibString("DoubleDouble") + _getBigNumLibPrecString(106);
            }
            else if(arbitraryLib == Constants.ARBITRARY_APFLOAT) {
                return _getBigNumLibString("Apfloat");
            }
        }
        else if(!domain_coloring && PERTURBATION_THEORY && fractal.supportsPerturbationTheory()) {
            if(bigNumLib == Constants.BIGNUM_BUILT_IN && fractal.supportsBignum()) {
                return _getBigNumLibString(BigNum.getName()) + _getBigNumLibPrecString(BigNum.getPrecision());
            }
            else if(bigNumLib == Constants.BIGNUM_BIGINT && fractal.supportsBigIntnum()) {
                return _getBigNumLibString("Fixed Point BigInteger") + _getBigNumLibPrecString(BigIntNum.getPrecision());
            }
            else if(bigNumLib == Constants.BIGNUM_MPFR && fractal.supportsMpfrBignum()) {
                return _getBigNumLibString("MPFR " + LibMpfr.mpfr_version) + _getBigNumLibPrecString(MpfrBigNum.precision);
            }
            else if(bigNumLib == Constants.BIGNUM_MPIR && fractal.supportsMpirBignum()) {
                return _getBigNumLibString("MPIR " + LibMpir.__mpir_version) + _getBigNumLibPrecString(MpirBigNum.precision);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
                return _getBigNumLibString("Double") + _getBigNumLibPrecString(MpfrBigNum.doublePrec);
            }
            else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
                return _getBigNumLibString("DoubleDouble") + _getBigNumLibPrecString(106);
            }
            else if(bigNumLib == Constants.BIGNUM_APFLOAT) {
                return _getBigNumLibString("Apfloat");
            }
        }
        return "";
    }

    private String getPixelsString(long total, long total_calculated_pixels, long total_calculated_extra_pixels, long total_completed_pixels, long total_pp, int supersampling_num) {
        if(filters[MainWindow.ANTIALIASING] && !quickRender) {

            long totalWithSuperSampling = total * supersampling_num;
            long totalCalculatedWithSuperSampling = total_calculated_pixels * supersampling_num;
            long totalExtraWithSuperSampling = total_calculated_extra_pixels * supersampling_num;
            long totalCompletedWithSuperSampling = total_completed_pixels * supersampling_num;
            long totalPPWithSuperSampling = total_pp * supersampling_num;

            String val = "<li>Anti-Aliasing Samples: <b>" +  supersampling_num + "x</b><br>" +
                    (d3 && image != null ? "<li>Actual Image Size: <b>" + image.getWidth() + "x" +  image.getHeight() + " (" + (image.getWidth() * image.getHeight()) + " Pixels)</b><br>" : "") +
                    IMAGE_SIZE_LABEL + supersampling_num + "x" +  WIDTH + "x" +  HEIGHT + " (" + (supersampling_num * WIDTH * HEIGHT) + " Pixels)</b><br>" +
                    PIXELS_CALCULATED_COUNT_LABEL +  totalCalculatedWithSuperSampling + " / " + totalWithSuperSampling + " (" +String.format("%f", (((double) totalCalculatedWithSuperSampling) / totalWithSuperSampling) * 100) + "%)</b><br>";

            if(totalCompletedWithSuperSampling >= 0) {
                val += PIXELS_COMPLETED_COUNT_LABEL + totalCompletedWithSuperSampling + " / " + (totalWithSuperSampling) + " (" + String.format("%f", (((double) totalCompletedWithSuperSampling) / (totalWithSuperSampling)) * 100) + "%)</b><br>";
            }

            if(TaskRender.GREEDY_ALGORITHM) {// && (!createFullImageAfterPreview || filters[MainWindow.ANTIALIASING])) {
                val += PIXELS_GUESSED_COUNT_LABEL + (totalCompletedWithSuperSampling - totalCalculatedWithSuperSampling) + " / " + (totalCompletedWithSuperSampling) + " (" + String.format("%f", (((double) (totalCompletedWithSuperSampling - totalCalculatedWithSuperSampling)) / (totalWithSuperSampling)) * 100) + "%)</b><br>";
            }

            if(totalPPWithSuperSampling > 0) {
                val += PIXELS_POST_PROCESSED_COUNT_LABEL + totalPPWithSuperSampling + " / " + (totalWithSuperSampling) + " (" + String.format("%f", (((double) totalPPWithSuperSampling) / (totalWithSuperSampling)) * 100) + "%)</b><br>";
            }

            if(totalExtraWithSuperSampling != 0) {
                val += EXTRA_PIXELS_CALCULATED_COUNT_LABEL + totalExtraWithSuperSampling + "</b><br>";
            }

            return val;
        }
        else {
            String val = (d3 && image != null ? "<li>Actual Image Size: <b>" + image.getWidth() + "x" +  image.getHeight() + " (" + (image.getWidth() * image.getHeight()) + " Pixels)</b><br>" : "");
            val += IMAGE_SIZE_LABEL + WIDTH + "x" +  HEIGHT + " (" + (WIDTH * HEIGHT) + " Pixels)</b><br>" +
                    PIXELS_CALCULATED_COUNT_LABEL + total_calculated_pixels + " / " + (total) + " (" + String.format("%f", (((double) total_calculated_pixels) / (total)) * 100) + "%)</b><br>";

            if(total_completed_pixels >= 0) {
                val += PIXELS_COMPLETED_COUNT_LABEL + total_completed_pixels + " / " + (total) + " (" + String.format("%f", (((double) total_completed_pixels) / (total)) * 100) + "%)</b><br>";
            }

            if(TaskRender.GREEDY_ALGORITHM && !quickRender) {// && (!createFullImageAfterPreview || filters[MainWindow.ANTIALIASING])) {
                val += PIXELS_GUESSED_COUNT_LABEL + (total_completed_pixels - total_calculated_pixels) + " / " + (total_completed_pixels) + " (" + String.format("%f", (((double) (total_completed_pixels - total_calculated_pixels)) / (total_completed_pixels)) * 100) + "%)</b><br>";
            }

            if(total_pp > 0 && !quickRender) {
                val += PIXELS_POST_PROCESSED_COUNT_LABEL + total_pp + " / " + (total) + " (" + String.format("%f", (((double) total_pp) / (total)) * 100) + "%)</b><br>";
            }

            if(total_calculated_extra_pixels != 0) {
                val += EXTRA_PIXELS_CALCULATED_COUNT_LABEL + total_calculated_extra_pixels + "</b><br>";
            }
            return val;
        }
    }

    private String getPixelStringSmall(boolean juliaMap, boolean afterAA, long total, long total_pp, int supersampling_num) {

        long total_calculated_pixels = total;
        long total_completed_pixels = total;

        if(filters[MainWindow.ANTIALIASING] && (juliaMap || afterAA)) {

            long totalWithSuperSampling = total * supersampling_num;
            long totalCalculatedWithSuperSampling = total_calculated_pixels * supersampling_num;
            long totalCompletedWithSuperSampling = total_completed_pixels * supersampling_num;
            long totalPPWithSuperSampling = total_pp * supersampling_num;

            String val = "<li>Anti-Aliasing Samples: <b>" +  supersampling_num + "x</b><br>" +
                    IMAGE_SIZE_LABEL + supersampling_num + "x" +  WIDTH + "x" +  HEIGHT + " (" + (supersampling_num * WIDTH * HEIGHT) + " Pixels)</b><br>" +
                    (juliaMap ? PIXELS_CALCULATED_COUNT_LABEL +  totalCalculatedWithSuperSampling + " / " + totalWithSuperSampling + " (" +String.format("%f", (((double) totalCalculatedWithSuperSampling) / totalWithSuperSampling) * 100) + "%)</b><br>" : "") +
                    PIXELS_COMPLETED_COUNT_LABEL + totalCompletedWithSuperSampling + " / " + (totalWithSuperSampling) + " (" + String.format("%f", (((double) totalCompletedWithSuperSampling) / (totalWithSuperSampling)) * 100) + "%)</b><br>";

            if(totalPPWithSuperSampling > 0) {
                val += PIXELS_POST_PROCESSED_COUNT_LABEL + totalPPWithSuperSampling + " / " + (totalWithSuperSampling) + " (" + String.format("%f", (((double) totalPPWithSuperSampling) / (totalWithSuperSampling)) * 100) + "%)</b><br>";
            }


            return val;
        }
        else {
            String val = IMAGE_SIZE_LABEL + WIDTH + "x" +  HEIGHT + " (" + (WIDTH * HEIGHT) + " Pixels)</b><br>" +
                    //PIXELS_CALCULATED_COUNT_LABEL + total_calculated_pixels + " / " + (total) + " (" + String.format("%f", (((double) total_calculated_pixels) / (total)) * 100) + "%)</b><br>" +
                    PIXELS_COMPLETED_COUNT_LABEL + total_completed_pixels + " / " + (total) + " (" + String.format("%f", (((double) total_completed_pixels) / (total)) * 100) + "%)</b><br>";

            if(total_pp > 0) {
                val += PIXELS_POST_PROCESSED_COUNT_LABEL + total_pp + " / " + (total) + " (" + String.format("%f", (((double) total_pp) / (total)) * 100) + "%)</b><br>";
            }

            return val;
        }
    }

    public static final String PIXEL_GROUPING_STRING_LABEL = "Guessed Pixels Group";
    private String getPixelGroupingString(int supersampling_num) {

        String result = "";

        long total = 0;
        for(LongAdder a : total_pixel_grouping) {
            total += a.sum();
        }

        total = total * supersampling_num;

        int i = 1;
        for(LongAdder a : total_pixel_grouping) {
            long v = a.sum() * supersampling_num;
            if(v != 0) {
                result += "<li>" + PIXEL_GROUPING_STRING_LABEL + " " + i + ": <b>" + v + " / " + total + " (" +String.format("%f", (((double) v) / total) * 100) + "%)" + "</b><br>";
            }
            i++;
        }

        return result;
    }

    private String getAlgorithmUsed() {

        if(!domain_coloring && (PERTURBATION_THEORY && !HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory()) {
            return "<li>Using <b>Perturbation Theory</b><br>";
        }
        else if(!domain_coloring && HIGH_PRECISION_CALCULATION && fractal.supportsPerturbationTheory()) {
            return "<li>Using <b>Arbitrary Precision</b><br>";
        }
        else {
            return "<li>Using <b>Double Precision</b><br>";
        }
    }

    private String getCompressionInfo(int refPointIterations, int secondRefPointIterations) {

        int waypoints = 0;
        int waypoints2 = 0;

        String referenceDataWaypoints = "" + waypoints;
        if(Fractal.referenceDeepData != null && Fractal.referenceDeepData.exists()) {
            ArrayList<Integer> waypointsList = Fractal.referenceDeepData.getWaypointsLength();
            waypoints = waypointsList.stream().mapToInt(v -> v).max().orElse(0);
            if(waypointsList.size() == 1) {
                referenceDataWaypoints = "" + waypointsList.get(0);
            }
            else {
                referenceDataWaypoints = "[" + String.join(", ", waypointsList.stream().map(i -> "" + i).collect(Collectors.toList())) + "]";
            }
        }
        else if(Fractal.referenceData != null && Fractal.referenceData.exists()) {
            ArrayList<Integer> waypointsList = Fractal.referenceData.getWaypointsLength();
            waypoints = waypointsList.stream().mapToInt(v -> v).max().orElse(0);
            if(waypointsList.size() == 1) {
                referenceDataWaypoints = "" + waypointsList.get(0);
            }
            else {
                referenceDataWaypoints = "[" + String.join(", ", waypointsList.stream().map(i -> "" + i).collect(Collectors.toList())) + "]";
            }
        }

        String result =  "<li>Reference Compression Waypoints: <b>" + referenceDataWaypoints + "</b><br>";
        result += "<li>Reference Compression Ratio: <b>" + (waypoints > 0 ?  (refPointIterations) + " / " + (waypoints) + " (" + String.format("%.4f", refPointIterations / ((double)waypoints)) + "x)" : "N/A")  + "</b><br>";

        if(fractal.needsSecondReference()) {

            String referenceDataWaypoints2 = "" + waypoints2;
            if(Fractal.secondReferenceDeepData != null && Fractal.secondReferenceDeepData.exists()) {
                ArrayList<Integer> waypointsList = Fractal.secondReferenceDeepData.getWaypointsLength();
                waypoints2 = waypointsList.stream().mapToInt(v -> v).max().orElse(0);
                if(waypointsList.size() == 1) {
                    referenceDataWaypoints2 = "" + waypointsList.get(0);
                }
                else {
                    referenceDataWaypoints2 = "[" + String.join(", ", waypointsList.stream().map(i -> "" + i).collect(Collectors.toList())) + "]";
                }
            }
            else if(Fractal.secondReferenceData != null && Fractal.secondReferenceData.exists()) {
                ArrayList<Integer> waypointsList = Fractal.secondReferenceData.getWaypointsLength();
                waypoints2 = waypointsList.stream().mapToInt(v -> v).max().orElse(0);
                if(waypointsList.size() == 1) {
                    referenceDataWaypoints2 = "" + waypointsList.get(0);
                }
                else {
                    referenceDataWaypoints2 = "[" + String.join(", ", waypointsList.stream().map(i -> "" + i).collect(Collectors.toList())) + "]";
                }
            }

            result += "<li>Julia Extra Reference Compression Waypoints: <b>" + referenceDataWaypoints2 + "</b><br>";
            result += "<li>Julia Extra Reference Compression Ratio: <b>" + (waypoints2 > 0 ?  (secondRefPointIterations) + " / " + (waypoints2) + " (" + String.format("%.4f", secondRefPointIterations / ((double)waypoints2)) + "x)" : "N/A")  + "</b><br>";

        }

        return result;
    }

    private String getApproximationString(boolean supportsPerturbation) {

        if(!(!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation)) {
            return "";
        }

        String approximation = "<li>Approximation: ";
        if(APPROXIMATION_ALGORITHM == 1 && fractal.supportsSeriesApproximation()) {
            approximation += "<b>Series Approximation</b><br>";
        }
        else if(APPROXIMATION_ALGORITHM == 2 && fractal.supportsBilinearApproximation()) {
            approximation += "<b>Bilinear Approximation Mip (claude)</b><br>";
        }
        else if(APPROXIMATION_ALGORITHM == 3 && fractal.supportsNanomb1()) {
            approximation += "<b>Nanomb1</b><br>";
        }
        else if(APPROXIMATION_ALGORITHM == 4 && fractal.supportsBilinearApproximation2()) {
            approximation += "<b>Bilinear Approximation (Zhuoran)</b><br>";
        }
        else if(APPROXIMATION_ALGORITHM == 5 && fractal.supportsBilinearApproximation3()) {
            approximation += "<b>Bilinear Approximation Mip (Zhuoran)</b><br>";
        }
        else {
            approximation += "<b>No Approximation</b><br>";
        }

        return approximation;
    }

    public void setFullToolTipMessage(int total) {

        long time =  ptr != null ? ptr.getCalculationTime() : ptrMinimalRenderer.getCalculationTime();
        int threads = ptr != null ? ptr.getNumberOfThreads() : ptrMinimalRenderer.getNumberOfThreads();

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num;

        if(!filters[MainWindow.ANTIALIASING]) {
            supersampling_num = 1;
        }
        else {
            supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod) + 1;
        }

        if(quickRender) {
            supersampling_num = 1;
        }

        long total_calculated_pixels = total_calculated.sum();
        long total_calculated_extra_pixels = total_calculated_extra.sum();
        long total_completed_pixels = total_completed.sum();

        if(d3) {
            total = detail * detail;
        }

        long total_time = System.currentTimeMillis() - time;

        int bigNumLib = NumericLibrary.getBignumImplementation(size, fractal);

        int arbitraryLib = NumericLibrary.getHighPrecisionImplementation(size, fractal);

        boolean isDeep = useExtendedRange(size, fractal);

        String oldValue = "";
        if(createFullImageAfterPreview) {
            oldValue = progress.getToolTipText();
            oldValue = oldValue.replace("<html>", "");
            oldValue = oldValue.replace("</html>", "");
        }

        int refPointIterations = fractal.getReferenceFinalIterationNumber(false, Fractal.referenceData) + 1;
        int secondRefPointIterations = Fractal.secondReferenceData.MaxRefIteration + 1;

        boolean supportsPerturbation = fractal.supportsPerturbationTheory() && !domain_coloring;

        long total_pp = total_post_processed.sum();

        boolean usesBLA = (APPROXIMATION_ALGORITHM == 2 && fractal.supportsBilinearApproximation()) ||  (APPROXIMATION_ALGORITHM == 4 && fractal.supportsBilinearApproximation2()) || (APPROXIMATION_ALGORITHM == 5 && fractal.supportsBilinearApproximation3());

        progress.setToolTipText("<html>" + TOTAL_ELAPSED_TIME_STRING_LABEL + total_time + " ms</b><br>" +
                getPixelsString(total, total_calculated_pixels, total_calculated_extra_pixels, total_completed_pixels, total_pp, supersampling_num) +
                "<li>Logical Processors: <b>" + Runtime.getRuntime().availableProcessors() + "</b><br>" +
                "<li>Threads Used: <b>" + threads + "</b><br>" +
                getAlgorithmUsed() +
                ((PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && supportsPerturbation ? "<li>Arbitrary Precision: <b>" + MyApfloat.precision + " digits</b><br>" : "") +
                getBigNumString(bigNumLib, arbitraryLib) +
                getFloatExpString(isDeep) +
                PIXEL_CALCULATION_ELAPSED_TIME_STRING_LABEL + max_pixel_calculation_time.get() + " ms</b><br>" +
                (!quickRender && total_pp > 0 && PostProcessingCalculationTime.get() != Long.MIN_VALUE ? POST_PROCESSING_ELAPSED_TIME_STRING_LABEL + PostProcessingCalculationTime.get() + " ms</b><br>" : "") +
                (!quickRender && FilterCalculationTime > 0? IMAGE_FILTERS_TIME_STRING_LABEL + FilterCalculationTime + " ms</b><br>" : "")+
                (d3 && D3RenderingCalculationTime > 0? D3_RENDER_TIME_STRING_LABEL + D3RenderingCalculationTime + " ms</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && Fractal.ReferenceCalculationTime > 0 ? REFERENCE_CALCULATION_ELAPSED_TIME_STRING_LABEL + Fractal.ReferenceCalculationTime + " ms</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation  ? REFERENCE_POINT_ITERATIONS_STRING_LABEL + refPointIterations + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && Fractal.ReferenceCalculationTime > 0 && Fractal.calculatedReferenceIterations > 0 ? "<li>Reference Point Iterations per second: <b>" + String.format("%.4f", Fractal.calculatedReferenceIterations / (Fractal.ReferenceCalculationTime / 1000.0)) + "</b><br>" : "") +

                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && fractal.needsSecondReference() && Fractal.SecondReferenceCalculationTime > 0 ? JULIA_EXTRA_REFERENCE_CALCULATION_ELAPSED_TIME_STRING_LABEL + Fractal.SecondReferenceCalculationTime + " ms</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation  && fractal.needsSecondReference() ? JULIA_EXTRA_REFERENCE_POINT_ITERATIONS_STRING_LABEL + secondRefPointIterations + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation  && fractal.needsSecondReference() && Fractal.SecondReferenceCalculationTime > 0 && Fractal.calculatedSecondReferenceIterations > 0 ? "<li>Julia Extra Reference Point Iterations per second: <b>" + String.format("%.4f", Fractal.calculatedSecondReferenceIterations / (Fractal.SecondReferenceCalculationTime / 1000.0)) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && COMPRESS_REFERENCE_IF_POSSIBLE && fractal.supportsReferenceCompression() ? getCompressionInfo(refPointIterations, secondRefPointIterations) : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && DETECT_PERIOD && fractal.supportsPeriod()  ? "<li>Detected Period: <b>" + (Fractal.DetectedPeriod != 0 ? Fractal.DetectedPeriod : "N/A") + "</b><br>" : "") + //&& Fractal.DetectedPeriod != 0
                //(!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && DETECT_PERIOD && fractal.supportsPeriod() && Fractal.DetectedPeriod != Fractal.DetectedAtomPeriod ? "<li>Detected Atom Period: <b>" + Fractal.DetectedAtomPeriod + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && fractal.supportsPeriod() && fractal.getPeriod()  != 0 && Fractal.SAskippedIterations == 0 ? "<li>Used Period: <b>" + fractal.getPeriod() + "</b><br>" : "") +
                getApproximationString(supportsPerturbation) +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 1 && fractal.supportsSeriesApproximation() && Fractal.SAskippedIterations != 0 && Fractal.SATerms != 0 && Fractal.SACalculationTime > 0? SA_CALCULATION_ELAPSED_TIME_LABEL + Fractal.SACalculationTime + " ms</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 1 && fractal.supportsSeriesApproximation() && Fractal.SAskippedIterations != 0 && Fractal.SATerms != 0 ? "<li>SA Terms Used: <b>" + Fractal.SATerms + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 1 && fractal.supportsSeriesApproximation() && Fractal.SAskippedIterations != 0 && Fractal.SATerms != 0 ? SA_SKIPPED_ITERATIONS_STRING_LABEL + Fractal.SAskippedIterations + "</b><br>": "") +

                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 3 && fractal.supportsNanomb1() && Fractal.Nanomb1CalculationTime > 0 ? NANOMB1_CALCULATION_ELAPSED_TIME_LABEL + Fractal.Nanomb1CalculationTime + " ms</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 3 && fractal.supportsNanomb1() ? "<li>Nanomb1 M: <b>" + NANOMB1_M + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 3 && fractal.supportsNanomb1() ? "<li>Nanomb1 N: <b>" + NANOMB1_N + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 3 && fractal.supportsNanomb1() ? NANOMB1_SKIPPED_ITERATIONS_PER_PIXEL_STRING_LABEL + String.format("%.4f", Fractal.total_nanomb1_skipped_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num))) + "</b><br>": "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && (usesBLA && Fractal.BLACalculationTime > 0) ? BLA_CALCULATION_ELAPSED_TIME_LABEL + Fractal.BLACalculationTime + " ms</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 2 && fractal.supportsBilinearApproximation()  ? "<li>BLA Precision: <b>" + TaskRender.BLA_BITS + " bits</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 2 && fractal.supportsBilinearApproximation()  ? "<li>BLA Starting Level: <b>" + TaskRender.BLA_STARTING_LEVEL + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && APPROXIMATION_ALGORITHM == 5 && fractal.supportsBilinearApproximation3()  ? "<li>BLA Starting Level: <b>" + TaskRender.BLA3_STARTING_LEVEL + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && PERTURBATION_THEORY && supportsPerturbation && usesBLA  ? "<li>BLA Entries: <b>" + fractal.getBLAEntries() + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && usesBLA  ? BLA_ITERATIONS_PER_PIXEL_STRING_LABEL +  String.format("%.4f", Fractal.total_bla_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && usesBLA  ? "<li>BLA Iterations Per BLA Step: <b>" +  (Fractal.total_bla_steps_sum() == 0 ? "N/A" : String.format("%.4f", Fractal.total_bla_iterations_sum() / ((double)Fractal.total_bla_steps_sum()))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && usesBLA  ? PERTURBATION_ITERATIONS_PER_PIXEL_STRING_LABEL +  String.format("%.4f", Fractal.total_perturb_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && usesBLA  ? "<li>BLA Steps Per Pixel: <b>" + String.format("%.4f", Fractal.total_bla_steps_sum() / ((double) total_calculated_pixels * (supersampling_num))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && usesBLA  ? "<li>Total Steps Per Pixel: <b>" + String.format("%.4f", (Fractal.total_bla_steps_sum() + Fractal.total_perturb_iterations_sum()) / ((double) total_calculated_pixels * (supersampling_num))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && !usesBLA && TaskRender.PERTUBATION_PIXEL_ALGORITHM == 1 && fractal.supportsScaledIterations() && isDeep ? EXTENDED_RANGE_ITERATIONS_PER_PIXEL_STRING_LABEL +  (String.format("%.4f", Fractal.total_float_exp_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num)))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && !usesBLA && TaskRender.PERTUBATION_PIXEL_ALGORITHM == 1 && fractal.supportsScaledIterations() && isDeep ? SCALED_DOUBLE_ITERATIONS_PER_PIXEL_STRING_LABEL +  (String.format("%.4f", Fractal.total_scaled_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num)))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && !usesBLA && TaskRender.PERTUBATION_PIXEL_ALGORITHM == 1 && fractal.supportsScaledIterations() && isDeep ? NORMAL_DOUBLE_ITERATIONS_PER_PIXEL_STRING_LABEL +  (String.format("%.4f", Fractal.total_double_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num)))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && !usesBLA && (TaskRender.PERTUBATION_PIXEL_ALGORITHM == 0 || !fractal.supportsScaledIterations()) && isDeep ? EXTENDED_RANGE_ITERATIONS_PER_PIXEL_STRING_LABEL +  (String.format("%.4f", Fractal.total_float_exp_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num)))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && !usesBLA && (TaskRender.PERTUBATION_PIXEL_ALGORITHM == 0 || !fractal.supportsScaledIterations()) && isDeep ? NORMAL_DOUBLE_ITERATIONS_PER_PIXEL_STRING_LABEL +  (String.format("%.4f", Fractal.total_double_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num)))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && !usesBLA && !isDeep ? NORMAL_DOUBLE_ITERATIONS_PER_PIXEL_STRING_LABEL +  (String.format("%.4f", Fractal.total_double_iterations_sum() / ((double) total_calculated_pixels * (supersampling_num)))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation && !usesBLA && TaskRender.PERTUBATION_PIXEL_ALGORITHM == 1 && fractal.supportsScaledIterations() && isDeep ? "<li>Re-Aligns Per Pixel: <b>" +  (String.format("%.4f", Fractal.total_realigns_sum() / ((double) total_calculated_pixels * (supersampling_num)))) + "</b><br>" : "") +
                (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY && supportsPerturbation ? "<li>Rebases Per Pixel: <b>" +  String.format("%.4f", Fractal.total_rebases_sum() / ((double) total_calculated_pixels * (supersampling_num))) + "</b><br>" : "") +
                (((HIGH_PRECISION_CALCULATION && GATHER_HIGHPRECISION_STATISTICS) || (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY)) && supportsPerturbation ? AVERAGE_ITERATIONS_PER_PIXEL_STRING_LABEL +  String.format("%.4f", (Fractal.total_iterations_sum())/ ((double) total_calculated_pixels * (supersampling_num))) + "</b><br>" : "") +
                (((HIGH_PRECISION_CALCULATION && GATHER_HIGHPRECISION_STATISTICS) || (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY)) && supportsPerturbation ? MINIMUM_ITERATIONS_STRING_LABEL +  Fractal.total_min_iterations_get() + "</b><br>" : "") +
                (((HIGH_PRECISION_CALCULATION && GATHER_HIGHPRECISION_STATISTICS) || (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY)) && supportsPerturbation ? MAXIMUM_ITERATIONS_STRING_LABEL +  Fractal.total_max_iterations_get() + "</b><br>" : "") +
                (((HIGH_PRECISION_CALCULATION && GATHER_HIGHPRECISION_STATISTICS) || (!HIGH_PRECISION_CALCULATION && GATHER_PERTURBATION_STATISTICS && PERTURBATION_THEORY)) && supportsPerturbation ? MAXIMUM_ITERATIONS_IGNORE_NOT_ESCAPED_STRING_LABEL +  Fractal.total_max_iterations_ignore_max_iter_get() + "</b><br>" : "") +
                getPixelGroupingString(supersampling_num) +



                "</html>");

        if(createFullImageAfterPreview) {
            String newValue =  progress.getToolTipText();
            newValue = newValue.replace("<html>", "");
            newValue = newValue.replace("</html>", "");
            progress.setToolTipText("<html><center><b><u>" + FIRST_PASS_STRING_LABEL + "</u></b></center><br>" + oldValue + "<br><center><b><u>" + SECOND_PASS_STRING_LABEL + "</u></b></center><br>" + newValue + "</html>");
        }


    }

    public static final String FIRST_PASS_STRING_LABEL = "First Pass";
    public static final String SECOND_PASS_STRING_LABEL = "Second Pass";

    public static final String PERTURBATION_ITERATIONS_PER_PIXEL_STRING_LABEL = "<li>Perturbation Iterations Per Pixel: <b>";
    public static final String BLA_ITERATIONS_PER_PIXEL_STRING_LABEL = "<li>BLA Iterations Per Pixel: <b>";
    public static final String NORMAL_DOUBLE_ITERATIONS_PER_PIXEL_STRING_LABEL = "<li>[Double] Perturbation Iterations Per Pixel: <b>";
    public static final String EXTENDED_RANGE_ITERATIONS_PER_PIXEL_STRING_LABEL = "<li>[Extended Range] Perturbation Iterations Per Pixel: <b>";
    public static final String SCALED_DOUBLE_ITERATIONS_PER_PIXEL_STRING_LABEL = "<li>[Scaled Double] Perturbation Iterations Per Pixel: <b>";
    public static final String SA_SKIPPED_ITERATIONS_STRING_LABEL = "<li>SA Skipped Iterations: <b>";
    public static final String NANOMB1_SKIPPED_ITERATIONS_PER_PIXEL_STRING_LABEL = "<li>Nanomb1 Skipped Iterations Per Pixel: <b>";

    public static final String AVERAGE_ITERATIONS_PER_PIXEL_STRING_LABEL = "<li>Average Iterations Per Pixel: <b>";
    public static final String MINIMUM_ITERATIONS_STRING_LABEL = "<li>Minimum Iterations: <b>";
    public static final String MAXIMUM_ITERATIONS_STRING_LABEL = "<li>Maximum Iterations: <b>";
    public static final String MAXIMUM_ITERATIONS_IGNORE_NOT_ESCAPED_STRING_LABEL = "<li>Maximum Iterations (Escaped Points Only): <b>";
    public static final String REFERENCE_POINT_ITERATIONS_STRING_LABEL = "<li>Reference Point Iterations: <b>";
    public static final String JULIA_EXTRA_REFERENCE_POINT_ITERATIONS_STRING_LABEL = "<li>Julia Extra Reference Point Iterations: <b>";
    public static int getExtraSamples(int aaSamplesIndex, int aaMethod) {
        int supersampling_num;
        if(aaSamplesIndex == 0) {
            supersampling_num = 4;
        }
        else if(aaSamplesIndex < 4) {
            supersampling_num = 8 * aaSamplesIndex;
        }
        else if(aaSamplesIndex == 4) {
            supersampling_num = Location.MAX_AA_SAMPLES_48;
        }
        else if(aaSamplesIndex == 5) {
            supersampling_num = Location.MAX_AA_SAMPLES_80;
        }
        else if(aaSamplesIndex == 6) {
            supersampling_num = Location.MAX_AA_SAMPLES_120;
        }
        else if(aaSamplesIndex == 7) {
            supersampling_num = Location.MAX_AA_SAMPLES_168;
        }
        else if(aaSamplesIndex == 8) {
            supersampling_num = Location.MAX_AA_SAMPLES_224;
        }
        else {
            supersampling_num = Location.MAX_AA_SAMPLES_288;
        }
        return aaMethod == 5 ? Math.min(supersampling_num, Location.MAX_AA_SAMPLES_24) : supersampling_num;
    }

    public void setSmallToolTipMessage(int total, boolean juliaMap, boolean afterAA) {

        long time =  ptr != null ? ptr.getCalculationTime() : ptrMinimalRenderer.getCalculationTime();

        int threads;
        if(action == JULIA_MAP || action == JULIA_MAP_POLAR) {
            threads = ptr != null ? ptr.getJuliaMapSlices() : 0;
        }
        else {
            threads = ptr != null ? ptr.getNumberOfThreads() : ptrMinimalRenderer.getNumberOfThreads();
        }

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num;

        if(!filters[MainWindow.ANTIALIASING]) {
            supersampling_num = 1;
        }
        else {
            supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod) + 1;
        }

        long total_time = System.currentTimeMillis() - time;

        long pixel_time = max_pixel_calculation_time.get();

        long total_pp = total_post_processed.sum();

        progress.setToolTipText("<html>" + TOTAL_ELAPSED_TIME_STRING_LABEL + total_time + " ms</b><br>" +
                getPixelStringSmall(juliaMap, afterAA, total, total_pp, supersampling_num) +
                "<li>Logical Processors: <b>" + Runtime.getRuntime().availableProcessors() + "</b><br>" +
                "<li>Threads Used: <b>" + threads + "</b><br>" +
                (juliaMap ? "<li>Using <b>Double Precision</b><br>" : "" )+
                (pixel_time != Long.MIN_VALUE ? PIXEL_CALCULATION_ELAPSED_TIME_STRING_LABEL + pixel_time  + " ms</b><br>" : "") +
                (total_pp > 0 && PostProcessingCalculationTime.get() != Long.MIN_VALUE ? POST_PROCESSING_ELAPSED_TIME_STRING_LABEL + PostProcessingCalculationTime.get() + " ms</b><br>" : "") +
                (FilterCalculationTime > 0 ? IMAGE_FILTERS_TIME_STRING_LABEL + FilterCalculationTime + " ms</b><br>" : "" )+
                (d3 && D3RenderingCalculationTime > 0 ? D3_RENDER_TIME_STRING_LABEL + D3RenderingCalculationTime + " ms</b><br>" : "") +
                "</html>");


    }

    public static final String IMAGE_SIZE_LABEL = "<li>Image Size: <b>";

    public static final String PIXELS_CALCULATED_COUNT_LABEL = "<li>Pixels Calculated: <b>";
    public static final String EXTRA_PIXELS_CALCULATED_COUNT_LABEL = "<li>Extra Pixels Calculated: <b>";
    public static final String PIXELS_POST_PROCESSED_COUNT_LABEL = "<li>Pixels Post Processed: <b>";
    public static final String PIXELS_COMPLETED_COUNT_LABEL = "<li>Pixels Completed: <b>";
    public static final String PIXELS_GUESSED_COUNT_LABEL = "<li>Pixels Guessed: <b>";

    public static final String D3_RENDER_TIME_STRING_LABEL = "<li>3D Rendering Elapsed Time:  <b>";
    public static final String IMAGE_FILTERS_TIME_STRING_LABEL = "<li>Image Filters Elapsed Time: <b>";
    public static final String PIXEL_CALCULATION_ELAPSED_TIME_STRING_LABEL = "<li>Pixel Calculation Elapsed Time: <b>";
    public static final String POST_PROCESSING_ELAPSED_TIME_STRING_LABEL = "<li>Post Processing Elapsed Time: <b>";
    public static final String REFERENCE_CALCULATION_ELAPSED_TIME_STRING_LABEL = "<li>Reference Calculation Elapsed Time: <b>";
    public static final String JULIA_EXTRA_REFERENCE_CALCULATION_ELAPSED_TIME_STRING_LABEL = "<li>Julia Extra Reference Calculation Elapsed Time: <b>";
    public static final String SA_CALCULATION_ELAPSED_TIME_LABEL = "<li>SA Calculation Elapsed Time: <b>";
    public static final String NANOMB1_CALCULATION_ELAPSED_TIME_LABEL = "<li>Nanomb1 Calculation Elapsed Time: <b>";
    public static final String BLA_CALCULATION_ELAPSED_TIME_LABEL = "<li>BLA Calculation Elapsed Time: <b>";

    public static final String TOTAL_ELAPSED_TIME_STRING_LABEL = "<li>Total Elapsed Time: <b>";

    private void domainPolarMinimalRendererRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            renderDomainAntialiased(image_width, image_height, true);
        } else {
            renderDomain(image_width, image_height, true);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptrMinimalRenderer.getNumberOfThreads()) {
            image_iterations = null;
            escaped = null;

            applyFilters();

            progress.setValue(progress.getMaximum());

            setFullToolTipMessage(image_width * image_height);

            ptrMinimalRenderer.writeImageToDisk();

            ptrMinimalRenderer.setOptions(true);
        }
    }

    private void polarMinimalRendererRendering() throws StopExecutionException, StopSuccessiveRefinementException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            renderAntialiased(image_width, image_height, true);
        } else {
            render(image_width, image_height, true);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        for(int i = 0; i < task_pixel_grouping.length; i++) {
            if(task_pixel_grouping[i] != 0) {
                total_pixel_grouping[i].add(task_pixel_grouping[i]);
            }
        }

        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptrMinimalRenderer.getNumberOfThreads()) {
            image_iterations = null;
            escaped = null;

            applyFilters();

            progress.setValue(progress.getMaximum());

            setFullToolTipMessage(image_width * image_height);

            ptrMinimalRenderer.writeImageToDisk();

            ptrMinimalRenderer.setOptions(true);

        }
    }

    private void minimalRendererRendering() throws StopExecutionException, StopSuccessiveRefinementException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            renderAntialiased(image_width, image_height, false);
        } else {
            render(image_width, image_height, false);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        for(int i = 0; i < task_pixel_grouping.length; i++) {
            if(task_pixel_grouping[i] != 0) {
                total_pixel_grouping[i].add(task_pixel_grouping[i]);
            }
        }

        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptrMinimalRenderer.getNumberOfThreads()) {
            image_iterations = null;
            escaped = null;

            applyFilters();

            progress.setValue(progress.getMaximum());

            setFullToolTipMessage(image_width * image_height);

            ptrMinimalRenderer.writeImageToDisk();

            ptrMinimalRenderer.setOptions(true);
        }
    }

    public static void updateMode(MainWindow ptr, boolean d3, boolean julia, boolean julia_map, boolean domain_coloring) {
        if(domain_coloring) {
            if (d3) {
                ptr.updateValues("3D mode");
            } else {
                ptr.updateValues("Domain C. mode");
            }
        }
        else if(julia_map) {
            ptr.updateValues("Julia Map mode");
        }
        else {
            if (d3) {
                ptr.updateValues("3D mode");
            } else if (julia) {
                ptr.updateValues("Julia mode");
            } else {
                ptr.updateValues("Normal mode");
            }
        }
    }

    private void rendering() throws StopExecutionException, StopSuccessiveRefinementException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                render3DAntialiased(image_width, image_height, false);
            } else {
                render3D(image_width, image_height, false);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            renderAntialiased(image_width, image_height, false);
        } else {
            render(image_width, image_height, false);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        for(int i = 0; i < task_pixel_grouping.length; i++) {
            if(task_pixel_grouping[i] != 0) {
                total_pixel_grouping[i].add(task_pixel_grouping[i]);
            }
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            BoundaryTracingRender.examined = null;
            SuccessiveRefinementGuessingRender.examined = null;
            applyFilters();

            updateMode(ptr, d3, iteration_algorithm.isJulia(), false, false);

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();

            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);

            setFullToolTipMessage(image_width * image_height);
        }
    }

    private void quickRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        quickRender(image_width, image_height,false);

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            updateMode(ptr, false, iteration_algorithm.isJulia(), false, false);

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            if(!createPreview && !QUICKRENDER_SUCCESSIVE_REFINEMENT) {
                ptr.getMainPanel().repaint();
            }

            setFullToolTipMessage(image_width * image_height);

            ptr.setProgressBarVisibility(false);

            ptr.createCompleteImage(createPreview ? 0 : QUICK_RENDER_DELAY, false, createPreview, zoomToCursor);
        }
    }

    private void quickPolarRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        quickRender(image_width, image_height, true);

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            updateMode(ptr, false, iteration_algorithm.isJulia(), false, false);

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();

            if(!createPreview && !QUICKRENDER_SUCCESSIVE_REFINEMENT) {
                ptr.getMainPanel().repaint();
            }

            setFullToolTipMessage(image_width * image_height);

            ptr.setProgressBarVisibility(false);

            ptr.createCompleteImage(createPreview ? 0 : QUICK_RENDER_DELAY, false, createPreview, zoomToCursor);
        }
    }

    private void domainRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                renderDomain3DAntialiased(image_width, image_height, false);
            } else {
                renderDomain3D(image_width, image_height, false);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            renderDomainAntialiased(image_width, image_height, false);
        } else {
            renderDomain(image_width, image_height, false);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);
        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            applyFilters();

            updateMode(ptr, d3, false, false, true);

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);

            setFullToolTipMessage(image_width * image_height);
        }
    }

    private void quickDomainRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        quickRenderDomain(image_width, image_height, false);

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);
        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            updateMode(ptr, false, false, false, true);

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();

            if(!createPreview && !QUICKRENDER_SUCCESSIVE_REFINEMENT) {
                ptr.getMainPanel().repaint();
            }

            setFullToolTipMessage(image_width * image_height);

            ptr.setProgressBarVisibility(false);

            ptr.createCompleteImage(createPreview ? 0 : QUICK_RENDER_DELAY, false, createPreview, zoomToCursor);
        }
    }

    private void quickDomainPolarRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        quickRenderDomain(image_width, image_height, true);

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);
        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            updateMode(ptr, false, false, false, true);

            ptr.setWholeImageDone(true);
            ptr.reloadTitle();

            if(!createPreview && !QUICKRENDER_SUCCESSIVE_REFINEMENT) {
                ptr.getMainPanel().repaint();
            }

            setFullToolTipMessage(image_width * image_height);

            ptr.setProgressBarVisibility(false);

            ptr.createCompleteImage(createPreview ? 0 : QUICK_RENDER_DELAY, false, createPreview, zoomToCursor);
        }
    }

    private void polarRendering() throws StopExecutionException, StopSuccessiveRefinementException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                render3DAntialiased(image_width, image_height, true);
            } else {
                render3D(image_width, image_height, true);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            renderAntialiased(image_width, image_height, true);
        } else {
            render(image_width, image_height, true);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        for(int i = 0; i < task_pixel_grouping.length; i++) {
            if(task_pixel_grouping[i] != 0) {
                total_pixel_grouping[i].add(task_pixel_grouping[i]);
            }
        }

        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            BoundaryTracingRender.examined = null;
            SuccessiveRefinementGuessingRender.examined = null;
            applyFilters();

            updateMode(ptr, d3, iteration_algorithm.isJulia(), false, false);

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);

            setFullToolTipMessage(image_width * image_height);
        }
    }

    private void domainPolarRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (d3) {
            if (filters[MainWindow.ANTIALIASING]) {
                renderDomain3DAntialiased(image_width, image_height, true);
            } else {
                renderDomain3D(image_width, image_height, true);
            }
        } else if (filters[MainWindow.ANTIALIASING]) {
            renderDomainAntialiased(image_width, image_height, true);
        } else {
            renderDomain(image_width, image_height, true);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        total_calculated.add(task_calculated);
        total_calculated_extra.add(task_calculated_extra);
        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);
        if(task_completed >= 0) {
            total_completed.add(task_completed);
        }
        else {
            total_completed.add(task_calculated);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            applyFilters();

            updateMode(ptr, d3, false, false, true);

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);

            setFullToolTipMessage(image_width * image_height);
        }
    }

    protected abstract void render(int image_with, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException;

    protected int getFinalColor(double result, boolean escaped) {

        int color;

        if (USE_DIRECT_COLOR) {
            return 0xFF000000 | (0x00FFFFFF & (int) result);
        }
        else if (fractal != null && fractal.hasTrueColor()) {
            fractal.resetTrueColor();
            color = fractal.getTrueColorValue();

            if (pbs.palette_gradient_merge) {
                color = result < 0 ? getPaletteMergedColor(result * pbs.gradient_intensity - 2.0 * color_cycling_location_outcoloring, color) : getPaletteMergedColor(result * pbs.gradient_intensity + 2.0 * color_cycling_location_outcoloring, color);
            }
        }
        else if(sts.statistic) {
            if (sts.statisticGroup == 2 && sts.equicontinuityOverrideColoring &&
                    !((!sts.statisticIncludeNotEscaped && (!escaped || isMaximumIterations(result)))
                            || (!sts.statisticIncludeEscaped && escaped && !isMaximumIterations(result)))) {
                color = getEquicontinuityColor(result, escaped);
            }
            else if ((sts.statisticGroup == 3 || sts.normalMapCombineWithOtherStatistics) &&
                    !((!sts.statisticIncludeNotEscaped && (!escaped || isMaximumIterations(result)))
                            || (!sts.statisticIncludeEscaped && escaped && !isMaximumIterations(result)))) {
                color = getNormalMapColor(result, escaped);
            }
            else if (sts.statisticGroup == 4 &&
                    !((!sts.statisticIncludeNotEscaped && (!escaped || isMaximumIterations(result)))
                            || (!sts.statisticIncludeEscaped && escaped && !isMaximumIterations(result)))) {
                color = getRootColor(result, escaped);
            }
            else {
                color = getStandardColor(result, escaped);
            }
        }
        else {
            color = getStandardColor(result, escaped);
        }

        if (ots.useTraps && !((!ots.trapIncludeNotEscaped && (!escaped || isMaximumIterations(result)))
                || (!ots.trapIncludeEscaped && escaped && !isMaximumIterations(result)))) {
            return getTrapColor(color);
        }

        return color;

    }

    public boolean isMaximumIterations(double result) {
        result = Math.abs(result);
        return result == ColorAlgorithm.MAXIMUM_ITERATIONS || result == ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
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
            rgb = ColorSpaceConverter.LCH_abtoRGB(L * 100, sts.equicontinuitySatChroma * 140, arg * 360);
        }
        else {

            int color2 = 0;

            if(sts.equicontinuityColorMethod == 3) {
                color2 = getStandardColor(arg * getPaletteLength(escaped), escaped);
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

        if(sts.normalMapOverrideColoring && sts.normalMapColoring ==  1 && !isMaximumIterations(result)) {
            color = getStandardColor(statistic.getExtraValue() * getPaletteLength(escaped), escaped);
        }

        int output_color = 0;
        if(!sts.useNormalMap || !sts.normalMapOverrideColoring) {
            output_color =  color;
        }
        else {

            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;

            double L = statistic.getValueForColoring() * 2;

            double coef = L;
            coef = coef > 1 ? 1 : coef;

            double coef2 = L - 1;
            coef2 = coef2 < 0 ? 0 : coef2;
            coef2 = coef2 * sts.normalMapLightFactor;

            output_color = applyContour(sts.normalMapColorMode, r, g, b, coef, coef2, sts.normalMapBlending);
        }

        if(sts.normalMapUseDE && sts.normalMapDEAAEffect) {

            NormalMap nm;
            if(statistic instanceof  NormalMap) {
                nm = (NormalMap) statistic;
            }
            else {
                nm = ((CombinedStatisticWithNormalMap)statistic).getNm();
            }

            int r1 = (output_color >> 16) & 0xFF;
            int g1 = (output_color >> 8) & 0xFF;
            int b1 = output_color & 0xFF;

            int r2, g2, b2;
            if(sts.normalMapDEUseColorPerDepth) {
                double depth_offset = nm.getSizeOffset();
                int color_per_depth = getStandardColor(depth_offset * sts.normalMapDEOffsetFactor + sts.normalMapDEOffset, escaped);
                r2 = (color_per_depth >> 16) & 0xFF;
                g2 = (color_per_depth >> 8) & 0xFF;
                b2 = color_per_depth & 0xFF;
            }
            else {
                r2 = (dem_color >> 16) & 0xFF;
                g2 = (dem_color >> 8) & 0xFF;
                b2 = dem_color & 0xFF;
            }

            output_color = method.interpolateColors(r1, g1, b1, r2, g2, b2, 1 - nm.getDeCoefficient(), true);
        }

        return output_color;
    }

    private int getRootColor(double result, boolean escaped) {
        GenericStatistic statistic = fractal.getStatisticInstance();

        if(statistic == null) {
            return getStandardColor(result, escaped);
        }

        int color = ((RootColoring)statistic).getRootColor();

        if(color == 0) {
            return fractal_color;
        }

        if(!sts.rootShading) {
            return 0xFF000000 | color;
        }

        double factor = ((RootColoring) statistic).getDepthFactor();
        if(factor > 1) {
            factor = 2 - factor;
        }

        factor = 1 - factor;

        if(sts.rootShadingFunction != 5) {
            switch (sts.rootShadingFunction) {
                case 1:
                    factor = SqrtInterpolation.getCoefficient(factor);
                    break;
                case 2:
                    factor = CbrtInterpolation.getCoefficient(factor);
                    break;
                case 3:
                    factor = CosineInterpolation.getCoefficient(factor);
                    break;
                case 4:
                    factor = Exponential2Interpolation.getCoefficient(factor);
                    break;
                case 6:
                    factor = AccelerationInterpolation.getCoefficient(factor);
                    break;
                case 7:
                    factor = SineInterpolation.getCoefficient(factor);
                    break;
                case 8:
                    factor = DecelerationInterpolation.getCoefficient(factor);
                    break;
                case 9:
                    factor = ThirdPolynomialInterpolation.getCoefficient(factor);
                    break;
                case 10:
                    factor = FifthPolynomialInterpolation.getCoefficient(factor);
                    break;
                case 11:
                    factor = FrthrootInterpolation.getCoefficient(factor);
                    break;
                case 12:
                    factor = SmoothTransitionFunctionInterpolation.getCoefficient(factor);
                    break;
                case 13:
                    factor = QuarterSinInterpolation.getCoefficient(factor);
                    break;
            }

            if (sts.revertRootShading) {
                factor = 1 - factor;
            }

            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = color & 0xFF;

            color = getModifiedColor(r, g, b, factor, sts.rootContourColorMethod, 1 - sts.rootBlending, false);
        }

        if(!sts.highlightRoots) {
            return 0xFF000000 | color;
        }

        double highlightFactor = ((RootColoring)statistic).getHighlightFactor();

        if(highlightFactor != 0) {

            int temp_red2 = (color >> 16) & 0xff;
            int temp_green2 = (color >> 8) & 0xff;
            int temp_blue2 = color & 0xff;

            int temp_red1 = !sts.revertRootShading ? sts.rootShadingColor.getRed() : 255 - sts.rootShadingColor.getRed();
            int temp_green1 = !sts.revertRootShading ? sts.rootShadingColor.getGreen()  : 255 - sts.rootShadingColor.getGreen();
            int temp_blue1 = !sts.revertRootShading ? sts.rootShadingColor.getBlue()  : 255 - sts.rootShadingColor.getBlue();

            return method.interpolateColors(temp_red1, temp_green1, temp_blue1, temp_red2, temp_green2, temp_blue2, highlightFactor, true);

        }

        return 0xFF000000 | color;

    }

    private int getOutPaletteColor(double transfered_result) {

        if(gps.blendNormalPaletteWithGeneratedPaletteOutColoring) {
            int color1 = transfered_result < 0 ? palette_outcoloring.getPaletteColor(transfered_result - color_cycling_location_outcoloring) : palette_outcoloring.getPaletteColor(transfered_result + color_cycling_location_outcoloring);
            int color2 = palette_outcoloring.calculateColor(transfered_result, gps.generatedPaletteOutColoringId, color_cycling_location_outcoloring, gps.GeneratedOutColoringPaletteOffset, gps.restartGeneratedOutColoringPaletteAt, gps.GeneratedOutColoringPaletteFactor, gps.outColoringIQ, true);

            int red1 = (color1 >> 16) & 0xff;
            int green1 = (color1 >> 8) & 0xff;
            int blue1 = color1 & 0xff;

            int red2 = (color2 >> 16) & 0xff;
            int green2 = (color2 >> 8) & 0xff;
            int blue2 = color2 & 0xff;
            return blending.blend(red1, green1, blue1, red2, green2, blue2, 1 - gps.blendingOutColoring);
        }
        else if(!gps.useGeneratedPaletteOutColoring) {
            return transfered_result < 0 ? palette_outcoloring.getPaletteColor(transfered_result - color_cycling_location_outcoloring) : palette_outcoloring.getPaletteColor(transfered_result + color_cycling_location_outcoloring);
        }
        else {
            return palette_outcoloring.calculateColor(transfered_result, gps.generatedPaletteOutColoringId, color_cycling_location_outcoloring, gps.GeneratedOutColoringPaletteOffset, gps.restartGeneratedOutColoringPaletteAt, gps.GeneratedOutColoringPaletteFactor, gps.outColoringIQ, true);
        }
    }

    private int getInPaletteColor(double transfered_result) {

        if(gps.blendNormalPaletteWithGeneratedPaletteInColoring) {
            int color1 = transfered_result < 0 ? palette_incoloring.getPaletteColor(transfered_result - color_cycling_location_incoloring) : palette_incoloring.getPaletteColor(transfered_result + color_cycling_location_incoloring);
            int color2 = palette_incoloring.calculateColor(transfered_result, gps.generatedPaletteInColoringId, color_cycling_location_incoloring, gps.GeneratedInColoringPaletteOffset, gps.restartGeneratedInColoringPaletteAt, gps.GeneratedInColoringPaletteFactor, gps.inColoringIQ, false);

            int red1 = (color1 >> 16) & 0xff;
            int green1 = (color1 >> 8) & 0xff;
            int blue1 = color1 & 0xff;

            int red2 = (color2 >> 16) & 0xff;
            int green2 = (color2 >> 8) & 0xff;
            int blue2 = color2 & 0xff;
            return blending.blend(red1, green1, blue1, red2, green2, blue2, 1 - gps.blendingInColoring);
        }
        else if(!gps.useGeneratedPaletteInColoring) {
            return transfered_result < 0 ? palette_incoloring.getPaletteColor(transfered_result - color_cycling_location_incoloring) : palette_incoloring.getPaletteColor(transfered_result + color_cycling_location_incoloring);
        }
        else {
            return palette_incoloring.calculateColor(transfered_result, gps.generatedPaletteInColoringId, color_cycling_location_incoloring, gps.GeneratedInColoringPaletteOffset, gps.restartGeneratedInColoringPaletteAt, gps.GeneratedInColoringPaletteFactor, gps.inColoringIQ, false);
        }

    }

    protected int getStandardColor(double result, boolean escaped) {

        if (USE_DIRECT_COLOR) {
            return 0xFF000000 | (0x00FFFFFF & (int) result);
        }

        int colorA = 0;

        double temp_res = Math.abs(result);
        if (temp_res == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            colorA = fractal_color;
        } else if (temp_res == ColorAlgorithm.MAXIMUM_ITERATIONS_DE) {
            colorA = dem_color;
        } else if (!escaped && usePaletteForInColoring) {
            double transfered_result = color_transfer_incoloring.transfer(result);
            colorA = getInPaletteColor(transfered_result);
            if (pbs.palette_gradient_merge) {
                colorA = result < 0 ? getPaletteMergedColor(result * pbs.gradient_intensity - 2.0 * color_cycling_location_outcoloring, colorA) : getPaletteMergedColor(result * pbs.gradient_intensity + 2.0 * color_cycling_location_outcoloring, colorA);
            }
        } else {
            double transfered_result = color_transfer_outcoloring.transfer(result);
            colorA = getOutPaletteColor(transfered_result);
            if (pbs.palette_gradient_merge) {
                colorA = result < 0 ? getPaletteMergedColor(result * pbs.gradient_intensity - 2.0 * color_cycling_location_outcoloring, colorA) : getPaletteMergedColor(result * pbs.gradient_intensity + 2.0 * color_cycling_location_outcoloring, colorA);
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
            temp = (int) temp % 2 == 1 ? 1 - MathUtils.fract(temp) : MathUtils.fract(temp);
        }

        int index = (int) (temp * (gradient.length - 1) + 0.5);

        int grad_color = getGradientColor(index + gradient_offset);

        int temp_red = grad_color & 0xff;
        int temp_green = grad_color & 0xff;
        int temp_blue = grad_color & 0xff;

        if (pbs.merging_type == 0) { //Lab
            old_red = ColorCorrection.gammaToLinear(old_red);
            old_green = ColorCorrection.gammaToLinear(old_green);
            old_blue = ColorCorrection.gammaToLinear(old_blue);
            temp_red = ColorCorrection.gammaToLinear(temp_red);
            temp_green = ColorCorrection.gammaToLinear(temp_green);
            temp_blue = ColorCorrection.gammaToLinear(temp_blue);
            double[] grad = ColorSpaceConverter.RGBtoLAB(temp_red, temp_green, temp_blue);
            double[] res = ColorSpaceConverter.RGBtoLAB(old_red, old_green, old_blue);
            int[] rgb = ColorSpaceConverter.LABtoRGB(grad[0], res[1], res[2]);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        } else if (pbs.merging_type == 1) { //HSB
            old_red = ColorCorrection.gammaToLinear(old_red);
            old_green = ColorCorrection.gammaToLinear(old_green);
            old_blue = ColorCorrection.gammaToLinear(old_blue);
            temp_red = ColorCorrection.gammaToLinear(temp_red);
            temp_green = ColorCorrection.gammaToLinear(temp_green);
            temp_blue = ColorCorrection.gammaToLinear(temp_blue);
            double[] grad = ColorSpaceConverter.RGBtoHSB(temp_red, temp_green, temp_blue);
            double[] res = ColorSpaceConverter.RGBtoHSB(old_red, old_green, old_blue);
            int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], grad[2]);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        } else if (pbs.merging_type == 2) { //HSL
            old_red = ColorCorrection.gammaToLinear(old_red);
            old_green = ColorCorrection.gammaToLinear(old_green);
            old_blue = ColorCorrection.gammaToLinear(old_blue);
            temp_red = ColorCorrection.gammaToLinear(temp_red);
            temp_green = ColorCorrection.gammaToLinear(temp_green);
            temp_blue = ColorCorrection.gammaToLinear(temp_blue);
            double[] grad = ColorSpaceConverter.RGBtoHSL(temp_red, temp_green, temp_blue);
            double[] res = ColorSpaceConverter.RGBtoHSL(old_red, old_green, old_blue);
            int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], grad[2]);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        } else if (pbs.merging_type == 3) {// blend
            return blending.blend(temp_red, temp_green, temp_blue, old_red, old_green, old_blue, 1 - pbs.palette_blending);
        } else if (pbs.merging_type == 4) { //scale
            old_red = ColorCorrection.gammaToLinear(old_red);
            old_green = ColorCorrection.gammaToLinear(old_green);
            old_blue = ColorCorrection.gammaToLinear(old_blue);
            temp_red = ColorCorrection.gammaToLinear(temp_red);
            temp_green = ColorCorrection.gammaToLinear(temp_green);
            temp_blue = ColorCorrection.gammaToLinear(temp_blue);
            double avg = ((temp_red + temp_green + temp_blue) / 3.0) / 255.0;
            old_red = (int) (old_red * avg + 0.5);
            old_green = (int) (old_green * avg + 0.5);
            old_blue = (int) (old_blue * avg + 0.5);
            return ColorCorrection.linearToGamma(old_red, old_green, old_blue);
        }
        else {
            old_red = ColorCorrection.gammaToLinear(old_red);
            old_green = ColorCorrection.gammaToLinear(old_green);
            old_blue = ColorCorrection.gammaToLinear(old_blue);
            temp_red = ColorCorrection.gammaToLinear(temp_red);
            temp_green = ColorCorrection.gammaToLinear(temp_green);
            temp_blue = ColorCorrection.gammaToLinear(temp_blue);
            double[] grad = ColorSpaceConverter.RGBtoOKLAB(temp_red, temp_green, temp_blue);
            double[] res = ColorSpaceConverter.RGBtoOKLAB(old_red, old_green, old_blue);
            int[] rgb = ColorSpaceConverter.OKLABtoRGB(grad[0], res[1], res[2]);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        }

    }

    private int getTrapColor(int originalColor) {

        OrbitTrap trap = fractal.getOrbitTrapInstance();

        if (trap.hasColor()) {
            int color = trap.getColor();
            if (((color >> 24) & 0xFF) == 0) {
                if(ots.showOnlyTraps) {
                    originalColor = ots.background.getRGB();
                }
                return originalColor;
            }
            return color;
        }

        if(ots.showOnlyTraps) {
            originalColor = ots.background.getRGB();
        }

        double distance = trap.getDistance();

        if ((ots.trapMaxDistance != 0 && distance > ots.trapMaxDistance) || distance == Double.MAX_VALUE) {
            return originalColor;
        }

        double maxDistance = ots.trapMaxDistance != 0 ? ots.trapMaxDistance : trap.getMaxValue();
        double minDinstance = trap.getMinValue();
        distance = (distance - minDinstance ) / (maxDistance - minDinstance);

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
                int color = getOutPaletteColor(transfered_result);

                trapRed = (color >> 16) & 0xFF;
                trapGreen = (color >> 8) & 0xFF;
                trapBlue = color & 0xFF;
            } else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_RANDOM_PALETTE) {
                double transfered_result = color_transfer_outcoloring.transfer(trap.getIteration() * 1.61803398875 * Math.PI * Math.E);
                int color = getOutPaletteColor(transfered_result);

                trapRed = (color >> 16) & 0xFF;
                trapGreen = (color >> 8) & 0xFF;
                trapBlue = color & 0xFF;
            } else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ARG_HUE_HSB) {
                int color = Color.HSBtoRGB((float) ((trap.getTrappedPoint().arg() + Math.PI) / (2 * Math.PI)), HSB_CONSTANT_S, HSB_CONSTANT_B);

                trapRed = (color >> 16) & 0xFF;
                trapGreen = (color >> 8) & 0xFF;
                trapBlue = color & 0xFF;
            } else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ARG_HUE_LCH_ab) {
                int[] rgb = ColorSpaceConverter.LCH_abtoRGB(TaskRender.LCHab_CONSTANT_L, TaskRender.LCHab_CONSTANT_C, 360 * ((trap.getTrappedPoint().arg() + Math.PI) / (2 * Math.PI)));

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
            else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ITER_LCH_ab) {
                int index = trap.getIteration() % 20;

                int[] rgb = ColorSpaceConverter.LCH_abtoRGB(TaskRender.LCHab_CONSTANT_L, TaskRender.LCHab_CONSTANT_C, 360 * (index / 20.0));

                trapRed = rgb[0];
                trapGreen = rgb[1];
                trapBlue = rgb[2];
            }
            else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ARG_HUE_LCH_uv) {
                int[] rgb = ColorSpaceConverter.LCH_uvtoRGB(TaskRender.LCHuv_CONSTANT_L, TaskRender.LCHuv_CONSTANT_C, 360 * ((trap.getTrappedPoint().arg() + Math.PI) / (2 * Math.PI)));

                trapRed = rgb[0];
                trapGreen = rgb[1];
                trapBlue = rgb[2];
            }
            else if (ots.trapColorFillingMethod == MainWindow.TRAP_COLOR_ITER_LCH_uv) {
                int index = trap.getIteration() % 20;

                int[] rgb = ColorSpaceConverter.LCH_uvtoRGB(TaskRender.LCHuv_CONSTANT_L, TaskRender.LCHuv_CONSTANT_C, 360 * (index / 20.0));

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

            trapColor = method.interpolateColors(red, green, blue, trapRed, trapGreen, trapBlue, ots.trapColorInterpolation, true);

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

            trapColor = method.interpolateColors(ots.trapCellularColor.getRed(), ots.trapCellularColor.getGreen(), ots.trapCellularColor.getBlue(), trapRed, trapGreen, trapBlue, 1 - coef, true);
        }

        return trapColor;
    }

    protected void quickRenderDomain(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int tempx, tempy;

        int color, loc2, loc, x, y;

        int current_chunk_size = tile;
        int min_chuck_size = tile;
        if(QUICKRENDER_SUCCESSIVE_REFINEMENT) {
            current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
            min_chuck_size = prevPowerOf2(tile);
        }

        long nano_time = 0;

        for(int id = 0; current_chunk_size >= min_chuck_size; current_chunk_size >>= 1, id++) {
            long time = System.nanoTime();
            AtomicInteger ai = quick_render_rendering_algorithm_pixel[id];

            int image_width_tile = image_width % current_chunk_size  == 0 ? image_width / current_chunk_size : image_width / current_chunk_size + 1;
            int image_height_tile = image_height % current_chunk_size  == 0 ? image_height / current_chunk_size : image_height / current_chunk_size + 1;
            int condition = (image_width_tile) * (image_height_tile);
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL[id];

            do {

                loc = chunk_size * ai.getAndIncrement();

                if (loc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && loc < condition; count++, loc++) {
                    tempx = loc % image_width_tile;
                    tempy = loc / image_width_tile;

                    x = tempx * current_chunk_size;
                    y = tempy * current_chunk_size;

                    loc2 = y * image_width + x;

                    if(rgbs[loc2] >>> 24 != Constants.QUICKRENDER_CALCULATED_ALPHA) {

                        Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                        image_iterations[loc2] = scaleDomainHeight(getDomainHeight(val));
                        color = domain_color.getDomainColor(val);
                        rgbs[loc2] = (color & 0xFFFFFF) | Constants.QUICKRENDER_CALCULATED_ALPHA_OFFSETED;

                        if (domain_image_data_re != null && domain_image_data_im != null) {
                            domain_image_data_re[loc2] = val.getRe();
                            domain_image_data_im[loc2] = val.getIm();
                        }

                        task_calculated++;

                        tempx = Math.min(image_width, x + current_chunk_size);
                        tempy = Math.min(image_height, y + current_chunk_size);

                        for (int i = y; i < tempy; i++) {
                            for (int j = x, loc3 = i * image_width + j; j < tempx; j++, loc3++) {
                                if (loc3 != loc2) {
                                    rgbs[loc3] = color;
                                }
                            }
                        }
                    }
                }

            } while (true);

            nano_time += System.nanoTime() - time;

            if(QUICKRENDER_SUCCESSIVE_REFINEMENT) {
                WaitOnCondition.WaitOnCyclicBarrier(quick_render_rendering_algorithm_barrier);
                if(taskId == 0) {
                    ptr.setWholeImageDone(true);
                    if(id == 0) {
                        ptr.reloadTitle();
                    }
                    ptr.getMainPanel().repaint();
                }
            }
        }

        pixel_calculation_time_per_task = nano_time / 1_000_000;

    }

    protected int prevPowerOf2 (int x)
    {
        x = x | (x >>> 1);
        x = x | (x >>> 2);
        x = x | (x >>> 4);
        x = x | (x >>> 8);
        x = x | (x >>> 16);
        return x - (x >>> 1);
    }

    protected void initializeFastJulia(Location location) throws StopExecutionException {
        if(PERTURBATION_THEORY && fractal.supportsPerturbationTheory() && !HIGH_PRECISION_CALCULATION) {

            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReferenceFastJulia(location);
            }

            WaitOnCondition.WaitOnCyclicBarrier(reference_sync);
            location.setReference(Fractal.refPoint);
        }
    }

    protected void initialize(Location location) throws StopExecutionException {
        if(PERTURBATION_THEORY && fractal.supportsPerturbationTheory() && !HIGH_PRECISION_CALCULATION) {
            if (reference_calc_sync.getAndIncrement() == 0) {
                calculateReference(location);
            }
            WaitOnCondition.WaitOnCyclicBarrier(reference_sync);
            location.setReference(Fractal.refPoint);
        }
        else if(HIGH_PRECISION_CALCULATION && fractal.supportsPerturbationTheory()) {
            if (reference_calc_sync.getAndIncrement() == 0) {
                initializeHighPrecision();
            }
            WaitOnCondition.WaitOnCyclicBarrier(reference_sync);
        }
    }

    protected void quickRender(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        int color, loc2, loc, x, y;
        int tempx, tempy;

        boolean escaped_val;
        double f_val;


        int current_chunk_size = tile;
        int min_chuck_size = tile;
        if(QUICKRENDER_SUCCESSIVE_REFINEMENT) {
            current_chunk_size = SUCCESSIVE_REFINEMENT_MAX_SIZE;
            min_chuck_size = prevPowerOf2(tile);
        }

        long nano_time = 0;


        for(int id = 0; current_chunk_size >= min_chuck_size; current_chunk_size >>= 1, id++) {
            long time = System.nanoTime();
            AtomicInteger ai = quick_render_rendering_algorithm_pixel[id];

            int image_width_tile = image_width % current_chunk_size  == 0 ? image_width / current_chunk_size : image_width / current_chunk_size + 1;
            int image_height_tile = image_height % current_chunk_size  == 0 ? image_height / current_chunk_size : image_height / current_chunk_size + 1;
            int condition = (image_width_tile) * (image_height_tile);
            int chunk_size = THREAD_CHUNK_SIZE_PER_LEVEL[id];

            do {

                loc = chunk_size * ai.getAndIncrement();

                if (loc >= condition) {
                    break;
                }

                for (int count = 0; count < chunk_size && loc < condition; count++, loc++) {
                    tempx = loc % image_width_tile;
                    tempy = loc / image_width_tile;

                    x = tempx * current_chunk_size;
                    y = tempy * current_chunk_size;

                    loc2 = y * image_width + x;

                    if(rgbs[loc2] >>> 24 != Constants.QUICKRENDER_CALCULATED_ALPHA) {
                        image_iterations[loc2] = f_val = iteration_algorithm.calculate(location.getComplex(x, y));
                        escaped[loc2] = escaped_val = iteration_algorithm.escaped();
                        color = getFinalColor(f_val, escaped_val);

                        rgbs[loc2] = (color & 0xFFFFFF) | Constants.QUICKRENDER_CALCULATED_ALPHA_OFFSETED;

                        task_calculated++;

                        tempx = Math.min(image_width, x + current_chunk_size);
                        tempy = Math.min(image_height, y + current_chunk_size);

                        for (int i = y; i < tempy; i++) {
                            for (int j = x, loc3 = i * image_width + j; j < tempx; j++, loc3++) {
                                if (loc3 != loc2) {
                                    rgbs[loc3] = color;
                                }
                            }
                        }
                    }
                }

            } while (true);

            nano_time += System.nanoTime() - time;

            if(QUICKRENDER_SUCCESSIVE_REFINEMENT) {
                WaitOnCondition.WaitOnCyclicBarrier(quick_render_rendering_algorithm_barrier);

                if(taskId == 0) {
                    ptr.setWholeImageDone(true);
                    if(id == 0) {
                        ptr.reloadTitle();
                    }
                    ptr.getMainPanel().repaint();
                }
            }
        }

        pixel_calculation_time_per_task = nano_time / 1_000_000;

    }

    protected void renderDomain(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int pixel_percent = (image_width * image_height) / 100;

        int x, y, loc;

        long time = System.currentTimeMillis();

        int iteration = 0;

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            for (y = FROMy; y < TOy; y++) {
                location.precalculateY(y);
                for (x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {

                    if (rgbs[loc] >>> 24 != Constants.NORMAL_ALPHA) {
                        Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                        image_iterations[loc] = scaleDomainHeight(getDomainHeight(val));
                        rgbs[loc] = domain_color.getDomainColor(val);

                        if (domain_image_data_re != null && domain_image_data_im != null) {
                            domain_image_data_re[loc] = val.getRe();
                            domain_image_data_im[loc] = val.getIm();
                        }
                        task_calculated++;
                    }

                    rendering_done++;

                }

                if (rendering_done / pixel_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
                }

            }
            iteration++;
        } while (true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, null, location);
        } catch (StopSuccessiveRefinementException ex) {}

    }

    protected void renderDomainAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int pixel_percent = (image_width * image_height) / 100;

        int x, y, loc;
        int color;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        double f_val;

        long time = System.currentTimeMillis();

        int iteration = 0;

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            for (y = FROMy; y < TOy; y++) {
                location.precalculateY(y);
                for (x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {

                    Complex val = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                    image_iterations[loc] = f_val = scaleDomainHeight(getDomainHeight(val));
                    color = domain_color.getDomainColor(val);

                    if (storeExtraData) {
                        pixelData[loc].set(0, color, f_val, true, totalSamples);
                    }

                    if (domain_image_data_re != null && domain_image_data_im != null) {
                        domain_image_data_re[loc] = val.getRe();
                        domain_image_data_im[loc] = val.getIm();
                    }

                    aa.initialize(color);

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        val = iteration_algorithm.calculateDomain(location.getAntialiasingComplex(i, loc));
                        color = domain_color.getDomainColor(val);

                        if (storeExtraData) {
                            f_val = scaleDomainHeight(getDomainHeight(val));
                            pixelData[loc].set(i + 1, color, f_val, true, totalSamples);
                        }

                        if (!aa.addSample(color)) {
                            break;
                        }
                    }

                    rgbs[loc] = aa.getColor();

                    rendering_done++;
                    task_calculated++;
                }

                if (rendering_done / pixel_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
                }

            }
            iteration++;
        } while (true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, aa, location);
        } catch (StopSuccessiveRefinementException ex) {}

    }

    private void calculate3D(int image_width, int image_height, boolean update_progress, int tile_size) {

        long time = System.currentTimeMillis();

        int x, y;

        int n1 = detail - 1;

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double d = image_size / (detail / (double)tile_size);

        double ct = Math.cos(d3s.fiX), cf = Math.cos(d3s.fiY), st = Math.sin(d3s.fiX), sf = Math.sin(d3s.fiY);
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        double mod;
        double norm_0_0, norm_0_1, norm_0_2, norm_1_0, norm_1_1, norm_1_2;

        int pixel_percent = detail * detail / 100;

        for (int x1 = FROMx; x1 < TOx; x1++) {
            x = x1 * tile_size;
            int xp1 = (x1 + 1) * tile_size;
            for (int y1 = FROMy; y1 < TOy; y1++) {
                y = y1 * tile_size;
                if (y < n1 && x < n1) {
                    int yp1 = (y1 + 1) * tile_size;
                    if(xp1 < detail && yp1 < detail) {
                        norm_0_0 = vert[x][y] - vert[xp1][y];
                        norm_0_1 = d;
                        norm_0_2 = vert[xp1][y] - vert[xp1][yp1];
                        mod = Math.sqrt(norm_0_0 * norm_0_0 + norm_0_1 * norm_0_1 + norm_0_2 * norm_0_2);
                        norm_0_0 /= mod;
                        norm_0_1 /= mod;
                        norm_0_2 /= mod;

                        norm_1_0 = vert[x][yp1] - vert[xp1][yp1];
                        norm_1_1 = d;
                        norm_1_2 = vert[x][y] - vert[x][yp1];
                        mod = Math.sqrt(norm_1_0 * norm_1_0 + norm_1_1 * norm_1_1 + norm_1_2 * norm_1_2);
                        norm_1_0 /= mod;
                        norm_1_1 /= mod;
                        norm_1_2 /= mod;

                        Norm1z[x][y][0] = (float) (m20 * norm_0_0 + m21 * norm_0_1 + m22 * norm_0_2);
                        Norm1z[x][y][1] = (float) (m20 * norm_1_0 + m21 * norm_1_1 + m22 * norm_1_2);
                    }
                }

                if (update_progress) {
                    rendering_done++;
                }
            }

            if (update_progress && (rendering_done / pixel_percent >= 1)) {
                update(rendering_done);
                rendering_done = 0;
            }
        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, update_progress, tile_size);

        }

    }

    protected void render3D(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, detail, detail, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());

        initialize(location);

        double[] temp;

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double w2 = image_size * 0.5;

        double d = image_size / (double) detail;

        int x, y, loc;

        int thread_chunk_size = getThreadChunkSize(detail, CHUNK_SIZE_PER_ROW);

        int condition = detail * detail;

        boolean escaped_val;
        double f_val;

        long time = System.currentTimeMillis();

        do {

            loc = thread_chunk_size * normal_rendering_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                temp = iteration_algorithm.calculate3D(location.getComplex(x, y));
                image_iterations[loc] = f_val = temp[1];
                vert[x][y] = fractional_transfer_3d(temp[0]);
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                vert_color[x][y] = getFinalColor(f_val, escaped_val);

                rendering_done_per_task[taskId]++;
                task_calculated++;
            }

            updateProgress();

        } while (true);

        heightProcessing();

        calculate3DVectors(d, w2);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(detail, detail, null, location);
        }
        catch (StopSuccessiveRefinementException ex) {}

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, true, 1);

        }

    }

    protected void renderDomain3D(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, detail, detail, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double w2 = image_size * 0.5;

        double d = image_size / (double) detail;

        int x, y, loc;

        int condition = detail * detail;

        int thread_chunk_size = getThreadChunkSize(detail, CHUNK_SIZE_PER_ROW);

        long time = System.currentTimeMillis();

        do {

            loc = thread_chunk_size * normal_rendering_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                Complex a = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                double height = getDomainHeight(a);

                vert[x][y] = fractional_transfer_3d(calaculateDomainColoringHeight(height));
                vert_color[x][y] = domain_color.getDomainColor(a);
                image_iterations[loc] = scaleDomainHeight(height);

                rendering_done_per_task[taskId]++;
                task_calculated++;
            }

            updateProgress();

        } while (true);

        heightProcessing();

        calculate3DVectors(d, w2);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(detail, detail, null, location);
        }catch (StopSuccessiveRefinementException ex) {}

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, true, 1);

        }

    }

    protected void render3DAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, detail, detail, circle_period, rotation_center, rotation_vals, fractal, js, polar, (PERTURBATION_THEORY || HIGH_PRECISION_CALCULATION) && fractal.supportsPerturbationTheory());
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        initialize(location);

        double[] temp;

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double w2 = image_size * 0.5;

        double d = image_size / (double) detail;

        int x, y, loc;

        int condition = detail * detail;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int temp_samples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(temp_samples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        int color;

        double height;

        boolean escaped_val;
        double f_val;
        boolean storeExtraData = pixelData != null;

        int thread_chunk_size = getThreadChunkSize(detail, CHUNK_SIZE_PER_ROW);

        long time = System.currentTimeMillis();

        do {

            loc = thread_chunk_size * normal_rendering_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                temp = iteration_algorithm.calculate3D(location.getComplex(x, y));
                image_iterations[loc] = f_val = temp[1];
                height = temp[0];
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(storeExtraData) {
                    pixelData[loc].set(0, color, f_val, escaped_val, temp_samples);
                }

                aa.initialize(color);

                //Supersampling
                for (int k = 0; k < supersampling_num; k++) {
                    temp = iteration_algorithm.calculate3D(location.getAntialiasingComplex(k, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp[1], escaped_val);

                    if(storeExtraData) {
                        pixelData[loc].set(k + 1, color, temp[1], escaped_val, temp_samples);
                    }

                    height += temp[0];

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                vert[x][y] = fractional_transfer_3d((height / temp_samples));
                vert_color[x][y] = aa.getColor();

                rendering_done_per_task[taskId]++;
                task_calculated++;
            }

            updateProgress();

        } while (true);

        heightProcessing();

        calculate3DVectors(d, w2);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(detail, detail, aa, location);
        }catch (StopSuccessiveRefinementException ex) {}

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, true, 1);

        }

    }

    protected void renderDomain3DAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, detail, detail, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int image_size = Math.min(image_width, image_height);
        int w2x = (int)(image_width * 0.5);
        int w2y = (int)(image_height * 0.5);
        double w2 = image_size * 0.5;

        double d = image_size / (double) detail;

        int x, y, loc;

        int condition = detail * detail;


        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int temp_samples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(temp_samples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        int color;

        double height;

        double f_val;

        boolean storeExtraData = pixelData != null;

        int thread_chunk_size = getThreadChunkSize(detail, CHUNK_SIZE_PER_ROW);

        long time = System.currentTimeMillis();

        do {

            loc = thread_chunk_size * normal_rendering_algorithm_pixel.getAndIncrement();

            if (loc >= condition) {
                break;
            }

            for (int count = 0; count < thread_chunk_size && loc < condition; count++, loc++) {
                x = loc % detail;
                y = loc / detail;

                Complex a = iteration_algorithm.calculateDomain(location.getComplex(x, y));
                double heightVal = getDomainHeight(a);
                image_iterations[loc] = f_val = scaleDomainHeight(heightVal);

                height = calaculateDomainColoringHeight(heightVal);
                color = domain_color.getDomainColor(a);

                if(storeExtraData) {
                    pixelData[loc].set(0, color, f_val, true, temp_samples);
                }

                aa.initialize(color);

                //Supersampling
                for (int k = 0; k < supersampling_num; k++) {
                    a = iteration_algorithm.calculateDomain(location.getAntialiasingComplex(k, loc));

                    color = domain_color.getDomainColor(a);
                    heightVal = getDomainHeight(a);
                    height += calaculateDomainColoringHeight(heightVal);

                    if(storeExtraData) {
                        f_val = scaleDomainHeight(heightVal);
                        pixelData[loc].set(k + 1, color, f_val, true, temp_samples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                vert[x][y] = fractional_transfer_3d((height / temp_samples));
                vert_color[x][y] = aa.getColor();

                rendering_done_per_task[taskId]++;
                task_calculated++;
            }

            updateProgress();

        } while (true);

        heightProcessing();

        calculate3DVectors(d, w2);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(detail, detail, aa, location);
        }catch (StopSuccessiveRefinementException ex) {}

        if (painting_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            paint3D(image_size, w2x, w2y, true, 1);

        }

    }

    protected abstract void renderAntialiased(int image_width, int image_height, boolean polar) throws StopSuccessiveRefinementException, StopExecutionException;

    private void fastJuliaRendering() throws StopExecutionException {

        int image_size = image.getWidth();

        if (fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
            renderFastJuliaAntialiased(image_size, false);
        } else {
            renderFastJulia(image_size, false);
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

    private void fastJuliaRenderingPolar() throws StopExecutionException {

        int image_size = image.getWidth();

        if (fast_julia_filters && filters[MainWindow.ANTIALIASING]) {
            renderFastJuliaAntialiased(image_size, true);
        } else {
            renderFastJulia(image_size, true);
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

    public static double fade(int fadeAlgorith, double t) {
        switch (fadeAlgorith) {
            case 1:
                return SqrtInterpolation.getCoefficient(t);
            case 2:
                return CbrtInterpolation.getCoefficient(t);
            case 3:
                return FrthrootInterpolation.getCoefficient(t);
            case 4:
                return CosineInterpolation.getCoefficient(t);
            case 5:
                return AccelerationInterpolation.getCoefficient(t);
            case 6:
                return SineInterpolation.getCoefficient(t);
            case 7:
                return DecelerationInterpolation.getCoefficient(t);
            case 8:
                return ThirdPolynomialInterpolation.getCoefficient(t);
            case 9:
                return FifthPolynomialInterpolation.getCoefficient(t);
            case 10:
                return Exponential2Interpolation.getCoefficient(t);
            case 11:
                return SmoothTransitionFunctionInterpolation.getCoefficient(t);
            case 12:
                return QuarterSinInterpolation.getCoefficient(t);
            default:
                return t;
        }
    }

    protected void applyPostProcessingOnPixel(int index, int x, int y, int image_width, int image_height, double[] image_iterations, boolean[] escaped, PixelExtraData[] pixelData, AntialiasingAlgorithm aa, int[] modified, double sizeCorr, double lightx, double lighty, Location location) {

        if(aa != null && pixelData != null) {
            modified = pixelData[index].rgb_values;
        }
        else if (d3) {
            modified[0] = vert_color[x][y];
        } else {
            modified[0] = rgbs[index];
        }

        for (int i = 0; i < post_processing_order.length; i++) {
            switch (post_processing_order[i]) {

                case MainWindow.HISTOGRAM_COLORING:
                    if(hss.histogramColoring && !domain_coloring) {
                        int[] original_color = modified;
                        modified = finalizeHistogramColoring(image_iterations, pixelData, original_color, y, x, image_width, image_height, escaped);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, hss.hs_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.LIGHT:
                    if (ls.lighting) {
                        int[] original_color = modified;
                        modified = pp.light(image_iterations, pixelData, original_color, y, x, image_width, image_height, location, aa);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, ls.l_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.NUMERICAL_DISTANCE_ESTIMATOR:
                    if (ndes.useNumericalDem && !domain_coloring) {
                        int[] original_color = modified;
                        modified = pp.numerical_distance_estimator(image_iterations, pixelData, original_color, y, x, image_width, image_height, location, aa, escaped);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, ndes.n_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.SLOPES:
                    if (ss.slopes) {
                        int[] original_color = modified;
                        modified = pp.slopes(image_iterations, pixelData, original_color, y, x, image_width, image_height, location, aa);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, ss.s_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.OFFSET_COLORING:
                    if (ofs.offset_coloring && !domain_coloring) {
                        int[] original_color = modified;
                        modified = pp.offsetColoring(image_iterations, pixelData, y, x, image_width, image_height, original_color, escaped);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, ofs.of_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.ENTROPY_COLORING:
                    if (ens.entropy_coloring && !domain_coloring) {
                        int[] original_color = modified;
                        modified = pp.entropyColoring(image_iterations, pixelData, y, x, image_width, image_height, original_color, escaped, location, aa);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, ens.en_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.RAINBOW_PALETTE:
                    if (rps.rainbow_palette && !domain_coloring) {
                        int[] original_color = modified;
                        modified = pp.paletteRainbow(image_iterations, pixelData, y, x, image_width, image_height, original_color, escaped, location, aa);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, rps.rp_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.CONTOUR_COLORING:
                    if (cns.contour_coloring && !domain_coloring) {
                        int[] original_color = modified;
                        modified = pp.contourColoring(image_iterations, pixelData, y, x, image_width, image_height, original_color, escaped);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, cns.cn_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.GREYSCALE_COLORING:
                    if (gss.greyscale_coloring && !domain_coloring) {
                        int[] original_color = modified;
                        modified = pp.greyscaleColoring(image_iterations, pixelData, y, x, image_width, image_height, original_color, escaped);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, gss.gs_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.BUMP_MAPPING:
                    if (bms.bump_map) {
                        int[] original_color = modified;
                        modified = pp.bumpMapping(image_iterations, pixelData, y, x, image_width, image_height, modified, lightx, lighty, sizeCorr, location, aa);
                        modified = pp.postProcessingSmoothing(modified, image_iterations, pixelData, original_color, y, x, image_width, image_height, bms.bm_noise_reducing_factor, location, aa);
                    }
                    break;
                case MainWindow.FAKE_DISTANCE_ESTIMATION:
                    if (fdes.fake_de && !domain_coloring) {
                        modified = pp.pseudoDistanceEstimation(image_iterations, pixelData, modified, y, x, image_width, image_height, location, aa);
                    }
                    break;
            }
        }


        if(aa != null && pixelData != null) {

            aa.initialize(modified[0]);

            int length = pixelData[index].getActualLength();

            for(int i = 1; i < length; i++) {
                if(!aa.addSample(modified[i])) {
                    break;
                }
            }

            if (d3) {
                vert_color[x][y] = aa.getColor();
            } else {
                rgbs[index] = aa.getColor();
            }
        }
        else if (d3) {
            vert_color[x][y] = modified[0];
        } else {
            rgbs[index] = modified[0];
        }

        if(task_post_processed >= 0) {
            task_post_processed++;
        }
    }

    protected void applyPostProcessingPointFilter(int image_width, int image_height, double[] image_iterations, boolean[] escaped, PixelExtraData[] pixelData, AntialiasingAlgorithm aa, Location location) throws StopExecutionException {
        double sizeCorr = 0, lightx = 0, lighty = 0;

        if (bms.bump_map) {
            double gradCorr = Math.pow(2, (bms.bumpMappingStrength - DEFAULT_BUMP_MAPPING_STRENGTH) * 0.05);
            sizeCorr = Math.min(image_width, image_height) / Math.pow(2, (MAX_BUMP_MAPPING_DEPTH - bms.bumpMappingDepth) * 0.16);
            double lightAngleRadians = Math.toRadians(bms.lightDirectionDegrees);
            lightx = Math.cos(lightAngleRadians) * gradCorr;
            lighty = Math.sin(lightAngleRadians) * gradCorr;
        }


        int[] modified = new int[1];

        if(aa != null) {
            modified = new int[aa.getTotalSamples()];
            aa.setNeedsAllSamples(false);
        }

        int iteration = 0;

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            for (int y = FROMy; y < TOy; y++) {
                for (int x = FROMx; x < TOx; x++) {
                    int index = y * image_width + x;
                    applyPostProcessingOnPixel(index, x, y, image_width, image_height, image_iterations, escaped, pixelData, aa, modified, sizeCorr, lightx, lighty, location);
                }
            }
            iteration++;
        } while (true);
    }

    protected void applyPostProcessing(int image_width, int image_height, double[] image_iterations, boolean[] escaped, PixelExtraData[] pixelData, AntialiasingAlgorithm aa, Location location) throws StopExecutionException {

        if (hss.histogramColoring && !domain_coloring) {
            if(aa != null && pixelData != null) {
                pp.initializeHistogramColoring(pixelData);
            }else {
                pp.initializeHistogramColoring(image_iterations, escaped);
            }
        }

        applyPostProcessingPointFilter(image_width, image_height, image_iterations, escaped, pixelData, aa, location);

    }

    protected abstract void renderFastJulia(int image_size, boolean polar) throws StopExecutionException;

    protected abstract void renderFastJuliaAntialiased(int image_size, boolean polar) throws StopExecutionException;

    private void colorCycling() throws StopExecutionException {

        int outPaletteLength = CommonFunctions.getOutPaletteLength(domain_coloring, domain_color != null ? domain_color.getColoringMode() : -1);
        int inPaletteLength = CommonFunctions.getInPaletteLength(domain_coloring);

        do {
            WaitOnCondition.LockRead(color_cycling_toggle_lock);

            WaitOnCondition.WaitOnCyclicBarrier(color_cycling_restart_sync);

            boolean cached_color_cycling = color_cycling;

            WaitOnCondition.UnlockRead(color_cycling_toggle_lock);

            if (!cached_color_cycling) {
                return;
            }

            ptr.setWholeImageDone(false);

            int image_width = image.getWidth();
            int image_height = image.getHeight();

            if (ccs.gradient_cycling_adjusting_value != 0) {
                gradient_offset = CommonFunctions.adjustPaletteOffset(gradient_offset, ccs.gradient_cycling_adjusting_value, gradient.length);
            }

            if (ccs.color_cycling_adjusting_value != 0) {
                color_cycling_location_outcoloring = CommonFunctions.adjustPaletteOffset(color_cycling_location_outcoloring, ccs.color_cycling_adjusting_value, outPaletteLength);
                color_cycling_location_incoloring = CommonFunctions.adjustPaletteOffset(color_cycling_location_incoloring, ccs.color_cycling_adjusting_value, inPaletteLength);
            }

            if (ccs.bump_cycling_adjusting_value != 0 && bms.bump_map) {
                CommonFunctions.adjustBumpOffset(bms, ccs.bump_cycling_adjusting_value);
            }

            if (ccs.light_cycling_adjusting_value != 0 && ls.lighting) {
                CommonFunctions.adjustLightOffset(ls, ccs.light_cycling_adjusting_value);
            }

            if(ccs.slope_cycling_adjusting_value != 0 && ss.slopes) {
                CommonFunctions.adjustSlopeOffset(ss, ccs.slope_cycling_adjusting_value);
            }

            int iteration = 0;
            initializeRectangleAreasQueue(image_width, image_height);

            do {
                Square currentSquare = getNextRectangleArea(iteration);

                if (currentSquare == null) {
                    break;
                }
                int FROMx = currentSquare.x1;
                int TOx = currentSquare.x2;
                int FROMy = currentSquare.y1;
                int TOy = currentSquare.y2;
                for (int y = FROMy; y < TOy; y++) {
                    for (int x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {
                        if (domain_coloring) {
                            domain_color.setColorCyclingLocation(color_cycling_location_outcoloring);
                            domain_color.setGradientOffset(gradient_offset);
                            rgbs[loc] = domain_color.getDomainColor(new Complex(domain_image_data_re[loc], domain_image_data_im[loc]));
                        } else {
                            rgbs[loc] = getStandardColor(image_iterations[loc], escaped[loc]);
                        }
                    }
                }
                iteration++;
            } while (true);

            postProcessColorCycling(image_width, image_height);

            if (WaitOnCondition.WaitOnCyclicBarrier(color_cycling_filters_sync) == 0) {
                applyFiltersNoProgress();

                ptr.setWholeImageDone(true);

                ptr.getMainPanel().repaint();

                if (ccs.color_cycling_adjusting_value != 0) {
                    ptr.updatePalettePreview(color_cycling_location_outcoloring, color_cycling_location_incoloring);
                }

                if (ccs.gradient_cycling_adjusting_value != 0) {
                    ptr.updateGradientPreview(gradient_offset);
                }
                //progress.setForeground(new Color(palette.getPaletteColor(color_cycling_location)));
            }

            WaitOnCondition.Sleep(ccs.color_cycling_speed + 35);

        } while (true);

    }

    private void rotate3DModel() {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        int tile_size = detail <= 60 ? 1 : TILE_SIZE;

        calculate3D(image_width, image_height, false, tile_size);

        if (rendering_done != 0) {
            update(rendering_done);
        }

        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            applyFiltersNoProgress();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);
            setSmallToolTipMessage(detail * detail, false, false);

            if(tile_size > 1) {
                ptr.createCompleteImage(QUICK_RENDER_DELAY, true, false, false);
            }
        }

    }

    private void applyPaletteAndFilter3DModel() {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        calculate3D(image_width, image_height, true, 1);

        if (rendering_done != 0) {
            update(rendering_done);
        }

        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);
            setSmallToolTipMessage(detail * detail, false, false);
        }

    }

    private void applyPaletteAndFilter() throws StopSuccessiveRefinementException, StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        changePalette(image_width, image_height);

        if (rendering_done != 0) {
            update(rendering_done);
        }

        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);
            setSmallToolTipMessage(image_width * image_height, false, false);
        }

    }

    private void applyPaletteAndFilterWithAA() throws StopSuccessiveRefinementException, StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        changePaletteWithAA(image_width, image_height);

        if (rendering_done != 0) {
            update(rendering_done);
        }

        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);
            setSmallToolTipMessage(image_width * image_height, false, true);
        }

    }

    private void applyPostProcessingWithAAandFilter() throws StopSuccessiveRefinementException, StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        int totalSamples = supersampling_num + 1;
        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        forcePostProcessing = true;

        postProcess(image_width, image_height, aa, null);

        if (rendering_done != 0) {
            update(rendering_done);
        }

        if (finalize_sync.incrementAndGet() == ptr.getNumberOfThreads()) {
            applyFilters();

            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);
            setSmallToolTipMessage(image_width * image_height, false, true);
        }

    }

    protected void changePalette(int image_width, int image_height) throws StopSuccessiveRefinementException, StopExecutionException {

        int pixel_percent = (image_width * image_height) / 100;

        task_completed = 0;
        int iteration = 0;

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            for (int y = FROMy; y < TOy; y++) {
                for (int x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {
                    if (domain_coloring) {
                        rgbs[loc] = domain_color.getDomainColor(new Complex(domain_image_data_re[loc], domain_image_data_im[loc]));
                    } else {
                        rgbs[loc] = getStandardColor(image_iterations[loc], escaped[loc]);
                    }

                    rendering_done++;
                    task_completed++;
                }

                if (rendering_done / pixel_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
                }
            }
            iteration++;
        } while (true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, null, null);

    }

    protected void changePaletteWithAA(int image_width, int image_height) throws StopSuccessiveRefinementException, StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        int totalSamples = supersampling_num + 1;
        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        int pixel_percent = (image_width * image_height) / 100;
        int color, iteration = 0;
        PixelExtraData data;

        task_completed = 0;

        long time = System.currentTimeMillis();

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            for (int y = FROMy; y < TOy; y++) {
                for (int x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {

                    data = pixelData[loc];
                    data.update_rgb(0, color = getStandardColor(data.values[0], data.escaped[0]));

                    aa.initialize(color);

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        data.update_rgb(i + 1, color = getFinalColor(data.values[i + 1], data.escaped[i + 1]));

                        if (!aa.addSample(color)) {
                            break;
                        }
                    }

                    rgbs[loc] = aa.getColor();

                    rendering_done++;
                    task_completed++;
                }

                if (rendering_done / pixel_percent >= 1) {
                    update(rendering_done);
                    rendering_done = 0;
                }

            }
            iteration++;
        } while (true);

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        postProcess(image_width, image_height, aa, null);

    }

    private void juliaMapRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            juliaMapAntialiased(image_width, image_height, false);
        } else {
            juliaMap(image_width, image_height, false);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if (finalize_sync.incrementAndGet() == ptr.getJuliaMapSlices()) {
            applyFilters();

            updateMode(ptr, false, false, true, false);
            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);
            setSmallToolTipMessage(image_width * image_height, true, false);
        }

    }

    private void juliaMapPolarRendering() throws StopExecutionException {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        if (filters[MainWindow.ANTIALIASING]) {
            juliaMapAntialiased(image_width, image_height, true);
        } else {
            juliaMap(image_width, image_height, true);
        }

        if (rendering_done != 0) {
            update(rendering_done);
        }

        max_pixel_calculation_time.accumulate(pixel_calculation_time_per_task);

        if (finalize_sync.incrementAndGet() == ptr.getJuliaMapSlices()) {
            applyFilters();

            updateMode(ptr, false, false, true, false);
            ptr.setOptions(true);
            ptr.setWholeImageDone(true);
            ptr.reloadTitle();
            ptr.getMainPanel().repaint();
            progress.setValue(progress.getMaximum());
            ptr.setProgressBarVisibility(false);
            setSmallToolTipMessage(image_width * image_height, true, false);
        }

    }

    private void juliaMap(int image_width, int image_height, boolean polar) throws StopExecutionException {

        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, TOx - FROMx, TOy - FROMy, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);

        int pixel_percent = (image_width * image_height) / 100;

        boolean escaped_val;
        double f_val;

        long time = System.currentTimeMillis();

        //No splitting here
        for (int y = FROMy, y2 = 0; y < TOy; y++, y2++) {
            for (int x = FROMx, x2 = 0, loc = y * image_width + x; x < TOx; x++, loc++, x2++) {

                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x2, y2));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                rgbs[loc] = getFinalColor(f_val, escaped_val);

                rendering_done++;
                task_calculated++;

            }

            if (rendering_done / pixel_percent >= 1) {
                update(rendering_done);
                rendering_done = 0;
            }

        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, null, null);
        } catch (StopSuccessiveRefinementException ex) {}

    }

    private void juliaMapAntialiased(int image_width, int image_height, boolean polar) throws StopExecutionException {

        int aaMethod = (filters_options_vals[MainWindow.ANTIALIASING] % 100) / 10;
        boolean useJitter = aaMethod != 6 && ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x4) == 4;
        Location location = Location.getInstanceForRendering(xCenter, yCenter, size, height_ratio, TOx - FROMx, TOy - FROMy, circle_period, rotation_center, rotation_vals, fractal, js, polar, false);
        int aaSamplesIndex = (filters_options_vals[MainWindow.ANTIALIASING] % 100) % 10;
        int supersampling_num = getExtraSamples(aaSamplesIndex, aaMethod);
        location.createAntialiasingSteps(aaMethod == 5, useJitter, supersampling_num);

        int pixel_percent = (image_width * image_height) / 100;


        double temp_result;

        int color;

        boolean aaAvgWithMean = ((filters_options_vals[MainWindow.ANTIALIASING] / 100) & 0x1) == 1;
        int colorSpace = filters_options_extra_vals[0][MainWindow.ANTIALIASING];
        int totalSamples = supersampling_num + 1;

        AntialiasingAlgorithm aa = AntialiasingAlgorithm.getAntialiasingAlgorithm(totalSamples, aaMethod, aaAvgWithMean, colorSpace, fs.aaSigmaR);

        aa.setNeedsAllSamples(needsPostProcessing());

        boolean storeExtraData = pixelData != null;

        boolean escaped_val;
        double f_val;

        long time = System.currentTimeMillis();

        //No splitting here
        for (int y = FROMy, y2 = 0; y < TOy; y++, y2++) {
            for (int x = FROMx, x2 = 0, loc = y * image_width + x; x < TOx; x++, loc++, x2++) {

                image_iterations[loc] = f_val = iteration_algorithm.calculate(location.getComplex(x2, y2));
                escaped[loc] = escaped_val = iteration_algorithm.escaped();
                color = getFinalColor(f_val, escaped_val);

                if(storeExtraData) {
                    pixelData[loc].set(0, color, f_val, escaped_val, totalSamples);
                }

                aa.initialize(color);

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(location.getAntialiasingComplex(i, loc));
                    escaped_val = iteration_algorithm.escaped();
                    color = getFinalColor(temp_result, escaped_val);

                    if(storeExtraData) {
                        pixelData[loc].set(i + 1, color, temp_result, escaped_val, totalSamples);
                    }

                    if(!aa.addSample(color)) {
                        break;
                    }
                }

                rgbs[loc] = aa.getColor();

                rendering_done++;
                task_calculated++;
            }

            if (rendering_done / pixel_percent >= 1) {
                update(rendering_done);
                rendering_done = 0;
            }

        }

        pixel_calculation_time_per_task = System.currentTimeMillis() - time;

        try {
            postProcess(image_width, image_height, aa, null);
        } catch (StopSuccessiveRefinementException ex) {}

    }

    private static final int MIN_3D_SCALED_VALUE = -100;

    private void shadeColorBasedOnHeight() throws StopExecutionException {

        double min = MIN_3D_SCALED_VALUE;
        double range = d3s.max_scaling * d3s.d3_height_scale;

        int iteration = 0;

        initializeRectangleAreasQueue(detail, detail);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            for (int x = FROMx; x < TOx; x++) {
                for (int y = FROMy; y < TOy; y++) {
                    int r = (vert_color[x][y] >> 16) & 0xff;
                    int g = (vert_color[x][y] >> 8) & 0xff;
                    int b = vert_color[x][y] & 0xff;

                    double coef = 0;

                    switch (d3s.shade_algorithm) {
                        case 0: //lerp
                            coef = ((vert[x][y] - min) / range - 0.5) * 2;
                            break;
                        case 1://cos lerp
                            coef = -Math.cos((vert[x][y] - min) / range * Math.PI);
                            break;
                        case 2:
                            double lim = 0.1;
                            if ((vert[x][y] - min) / range <= lim) {
                                coef = -(1 - (vert[x][y] - min) / range * (1 / lim));
                            } else if ((vert[x][y] - min) / range >= (1 - lim)) {
                                coef = 1 - (1 - (vert[x][y] - min) / range) * (1 / lim);
                            } else {
                                coef = 0;
                            }
                            break;
                        case 3:
                            lim = 0.2;
                            if ((vert[x][y] - min) / range <= lim) {
                                coef = -(1 - (vert[x][y] - min) / range * (1 / lim));
                            } else if ((vert[x][y] - min) / range >= (1 - lim)) {
                                coef = 1 - (1 - (vert[x][y] - min) / range) * (1 / lim);
                            } else {
                                coef = 0;
                            }
                            break;
                        case 4:
                            lim = 0.3;
                            if ((vert[x][y] - min) / range <= lim) {
                                coef = -(1 - (vert[x][y] - min) / range * (1 / lim));
                            } else if ((vert[x][y] - min) / range >= (1 - lim)) {
                                coef = 1 - (1 - (vert[x][y] - min) / range) * (1 / lim);
                            } else {
                                coef = 0;
                            }
                            break;
                        case 5:
                            lim = 0.4;
                            if ((vert[x][y] - min) / range <= lim) {
                                coef = -(1 - (vert[x][y] - min) / range * (1 / lim));
                            } else if ((vert[x][y] - min) / range >= (1 - lim)) {
                                coef = 1 - (1 - (vert[x][y] - min) / range) * (1 / lim);
                            } else {
                                coef = 0;
                            }
                            break;
                    }

                    if (d3s.shade_invert) {
                        coef *= -1;
                    }

                    if (d3s.shade_choice == 2) { //-1 to 0 only
                        if (coef > 0) {
                            coef = 0;
                        }
                    } else if (d3s.shade_choice == 1) { //0 to 1 only
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

                    vert_color[x][y] = 0xff000000 | (r << 16) | (g << 8) | b;
                }
            }
            iteration++;
        } while (true);
    }

    //Has race condition
    protected void update(int new_percent) {

        progress.setValue(progress.getValue() + new_percent);

    }

    protected void updateProgress() {
        if(taskId == 0) {
            int total = 0;
            for(int i = 0; i < rendering_done_per_task.length; i++) {
                total += rendering_done_per_task[i];
            }
            if((total - progress.getValue()) > progress_one_percent) {
                progress.setValue(total);
            }
        }
    }

    public static void terminateColorCycling() throws StopExecutionException {
        WaitOnCondition.LockWrite(color_cycling_toggle_lock);
        color_cycling = false;
        WaitOnCondition.UnlockWrite(color_cycling_toggle_lock);
    }

    public static void initializeColorCycling() throws StopExecutionException {
        WaitOnCondition.LockWrite(color_cycling_toggle_lock);
        color_cycling = true;
        WaitOnCondition.UnlockWrite(color_cycling_toggle_lock);
    }

    public BumpMapSettings getBumpMapSettings() {
        return bms;
    }

    public LightSettings getLightSettings() {
        return ls;
    }

    public SlopeSettings getSlopeSettings() {
        return ss;
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

        if (active_filters_count > 0) {
            int old_max = progress.getMaximum();
            int cur_val = progress.getValue();

            progress.setMaximum(active_filters_count);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_filters_color);
            progress.setString("Image Filters: " + 0 + "/" + active_filters_count);

            long time = System.currentTimeMillis();
            ImageFilters.filter(image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order, fs, progress);
            FilterCalculationTime = System.currentTimeMillis() - time;

            progress.setString(null);
            progress.setMaximum(old_max);
            progress.setValue(cur_val);
            progress.setForeground(MainWindow.progress_color);
        }
    }

    private void applyFiltersNoProgress() {
        ImageFilters.filter(image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order, fs, null);
    }

    private double calculateHeight(double x) {

        double sign = x >= 0 ? 1 : -1;
        x = Math.abs(x);
        switch (d3s.height_algorithm) {
            case 0:
                return sign * Math.log(x + 1);
            case 1:
                return sign * Math.log(Math.log(x + 1) + 1);
            case 2:
                return sign * (1 / (x + 1));
            case 3:
                return sign * (1 / (Math.log(x + 1) + 1));
            case 4:
                return sign * x;

        }

        return 0;
    }

    private void findMinMaxHeight() {

        min = Double.MAX_VALUE;
        max = -Double.MAX_VALUE;

        for (int x = 0; x < detail; x++) {
            for (int y = 0; y < detail; y++) {
                if (Double.isNaN(vert[x][y]) || Double.isInfinite(vert[x][y])) {
                    continue;
                }

                if (vert[x][y] < min) {
                    min = vert[x][y];
                }

                if (vert[x][y] > max) {
                    max = vert[x][y];
                }
            }
        }

    }

    private void calculateFences() {

        double mean = 0;
        double variance = 0;
        long samples = 0;

        upperFence = Double.MAX_VALUE;
        lowerFence = -Double.MAX_VALUE;

        ArrayList<Double> data = null;
        if(d3s.outliers_method == 0) {
            data = new ArrayList<>();
        }

        for (int x = 0; x < detail; x++) {
            for (int y = 0; y < detail; y++) {
                double val = vert[x][y];
                if (Double.isNaN(val) || Double.isInfinite(val)) {
                    continue;
                }

                samples++;
                if(d3s.outliers_method == 0) {
                    data.add(val);
                }
                else {
                    double delta = val - mean;
                    mean += delta / samples;
                    double delta2 = val - mean;
                    variance += delta * delta2;
                }

            }
        }


        if(d3s.outliers_method == 0) {
            double[] res = getFences(data);
            lowerFence = res[0];
            upperFence = res[1];

            data.clear();
        }
        else {
            double sigma = Math.sqrt(variance / samples);
            lowerFence = mean - 3 * sigma;
            upperFence = mean + 3 * sigma;
        }

    }

    private double calculateMedian(ArrayList<Double> values, int start, int end) {
        int length = end - start;
        int middle = start + length / 2;

        if (length % 2 == 0) {
            return (values.get(middle) + values.get(middle - 1)) * 0.5;
        }

        return values.get(middle);
    }

    private void applyHeightFunction() throws StopExecutionException {

        int iteration = 0;

        initializeRectangleAreasQueue(detail, detail);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            for (int x = FROMx; x < TOx; x++) {
                for (int y = FROMy; y < TOy; y++) {
                    vert[x][y] = calculateHeight(vert[x][y]);
                }
            }
            iteration++;
        } while (true);
    }

    private void applyPostHeightScaling() throws StopExecutionException {

        double local_max = max - (max - min) * (1 - d3s.max_range / 100.0);

        double local_min = min + (max - min) * (d3s.min_range / 100.0);

        local_min = local_min > local_max ? local_max : local_min;

        double new_max = local_max - local_min;

        int iteration = 0;

        initializeRectangleAreasQueue(detail, detail);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            for (int x = FROMx; x < TOx; x++) {
                for (int y = FROMy; y < TOy; y++) {
                    double val = vert[x][y];
                    if (val <= local_max && val >= local_min) {
                        val -= local_min;
                        vert[x][y] = (val * (d3s.max_scaling / new_max));
                    } else if (val > local_max) {
                        vert[x][y] = d3s.max_scaling;
                    } else if (!Double.isNaN(val) && !Double.isInfinite(val)) {
                        vert[x][y] = 0;
                    }

                    vert[x][y] = (d3s.d3_height_scale * vert[x][y] + MIN_3D_SCALED_VALUE);
                }
            }
            iteration++;
        } while (true);

    }

    private void applyPreHeightScaling() throws StopExecutionException {

        double local_max = max - (max - min) * (1 - d3s.max_range / 100.0);

        double local_min = min + (max - min) * (d3s.min_range / 100.0);

        local_min = local_min > local_max ? local_max : local_min;

        double new_max = local_max - local_min;

        int iteration = 0;

        initializeRectangleAreasQueue(detail, detail);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            for (int x = FROMx; x < TOx; x++) {
                for (int y = FROMy; y < TOy; y++) {
                    double val = vert[x][y];
                    if (val <= local_max && val >= local_min) {
                        val -= local_min;
                        vert[x][y] = (val * (d3s.max_scaling / new_max));
                    } else if (val > local_max) {
                        vert[x][y] = d3s.max_scaling;
                    } else if (!Double.isNaN(val) && !Double.isInfinite(val)) {
                        vert[x][y] = 0;
                    }
                }
            }
            iteration++;
        } while (true);
    }

    private void gaussianHeightScalingInit() {
        temp_array = new double[detail][detail];

        for (int x = 0; x < detail; x++) {
            for (int y = 0; y < detail; y++) {
                if (Double.isNaN(vert[x][y]) || Double.isInfinite(vert[x][y])) {
                    temp_array[x][y] = 0;
                }
                else {
                    temp_array[x][y] = vert[x][y];
                }
            }
        }

        gaussian_kernel = ImageFilters.createGaussianKernel(d3s.gaussian_kernel * 2 + 3, d3s.sigma_r);
    }

    private void gaussianHeightScalingEnd() {
        temp_array = null;
    }


    private double similarity(double val, double centralVal) {
        double distance = Math.abs(val - centralVal);
        double exponent = distance / d3s.sigma_s;
        exponent = exponent * exponent;
        return Math.exp(-0.5 * exponent);
    }

    private void gaussianOrBilateralHeightScaling() throws StopExecutionException {

        int kernel_size = (int) (Math.sqrt(gaussian_kernel.length));
        int kernel_size2 = kernel_size / 2;

        int iteration = 0;

        initializeRectangleAreasQueue(detail, detail);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            for (int x = FROMx; x < TOx; x++) {
                for (int y = FROMy; y < TOy; y++) {
                    double sum = 0;
                    double centerVal = 0;
                    double combined_coef_sum = 0;

                    if (d3s.bilateral_scaling) {
                        centerVal = temp_array[x][y];
                    }

                    for (int k = x - kernel_size2, p = 0; p < kernel_size; k++, p++) {
                        for (int l = y - kernel_size2, t = 0; t < kernel_size; l++, t++) {

                            if (k < 0 || k >= detail || l < 0 || l >= detail) {
                                continue;
                            }

                            if (d3s.bilateral_scaling) {
                                double currentVal = temp_array[k][l];
                                double combined_coef = gaussian_kernel[p * kernel_size + t] * similarity(currentVal, centerVal);
                                sum += currentVal * combined_coef;
                                combined_coef_sum += combined_coef;
                            } else {
                                combined_coef_sum += gaussian_kernel[p * kernel_size + t];
                                sum += temp_array[k][l] * gaussian_kernel[p * kernel_size + t];
                            }
                        }
                    }

                    vert[x][y] = sum / combined_coef_sum;
                }
            }
            iteration++;
        } while (true);
    }


    private void removeOutliers() throws StopExecutionException {

        int iteration = 0;

        initializeRectangleAreasQueue(detail, detail);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            for (int x = FROMx; x < TOx; x++) {
                for (int y = FROMy; y < TOy; y++) {
                    double val = vert[x][y];

                    if (Double.isNaN(val) || Double.isInfinite(val)) {
                        if (val == Double.NEGATIVE_INFINITY) {
                            vert[x][y] = lowerFence;
                        } else if (val == Double.POSITIVE_INFINITY) {
                            vert[x][y] = upperFence;
                        } else {
                            vert[x][y] = lowerFence;
                        }
                    }

                    if (val > upperFence) {
                        vert[x][y] = upperFence;
                    }

                    if (val < lowerFence) {
                        vert[x][y] = lowerFence;
                    }
                }
            }
            iteration++;
        } while(true);

    }

    protected void heightProcessing() throws StopExecutionException {

        if (d3s.remove_outliers_pre) {

            if (remove_outliers_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                calculateFences();

            }

            WaitOnCondition.WaitOnCyclicBarrier(remove_outliers_sync2);

            removeOutliers();

        }

        if (d3s.gaussian_scaling || d3s.bilateral_scaling) {

            if (gaussian_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

                gaussianHeightScalingInit();

            }

            WaitOnCondition.WaitOnCyclicBarrier(gaussian_scaling_sync2);

            gaussianOrBilateralHeightScaling();
        }

        if (d3s.preHeightScaling) {

            if (height_scaling_sync3.incrementAndGet() == ptr.getNumberOfThreads()) {

                findMinMaxHeight();

            }

            WaitOnCondition.WaitOnCyclicBarrier(height_scaling_sync4);

            applyPreHeightScaling();
        }

        WaitOnCondition.WaitOnCyclicBarrier(height_function_sync);

        applyHeightFunction();

        if (d3s.remove_outliers_post) {

            if (remove_outliers_sync3.incrementAndGet() == ptr.getNumberOfThreads()) {

                calculateFences();

            }

            WaitOnCondition.WaitOnCyclicBarrier(remove_outliers_sync4);

            removeOutliers();

        }

        if (height_scaling_sync.incrementAndGet() == ptr.getNumberOfThreads()) {

            findMinMaxHeight();

        }

        WaitOnCondition.WaitOnCyclicBarrier(height_scaling_sync2);

        applyPostHeightScaling();

        if (d3s.shade_height) {
            WaitOnCondition.WaitOnCyclicBarrier(shade_color_height_sync);
            shadeColorBasedOnHeight();
        }

        WaitOnCondition.WaitOnCyclicBarrier(calculate_vectors_sync);

        if (d3s.gaussian_scaling || d3s.bilateral_scaling) {
            gaussianHeightScalingEnd();
        }

    }

    protected void calculate3DVectors(double d, double w2) throws StopExecutionException {

        int n1 = detail - 1;

        double mod;
        double ct = Math.cos(d3s.fiX), cf = Math.cos(d3s.fiY), st = Math.sin(d3s.fiX), sf = Math.sin(d3s.fiY);
        m20 = -ct * sf;
        m21 = st;
        m22 = ct * cf;

        double norm_0_0, norm_0_1, norm_0_2, norm_1_0, norm_1_1, norm_1_2;

        int iteration = 0;

        initializeRectangleAreasQueue(detail, detail);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;

            for (int x = FROMx; x < TOx; x++) {

                for (int y = FROMy; y < TOy; y++) {
                    if (x < n1 && y < n1) {

                        norm_0_0 = vert[x][y] - vert[x + 1][y];
                        norm_0_1 = d;
                        norm_0_2 = vert[x + 1][y] - vert[x + 1][y + 1];
                        mod = Math.sqrt(norm_0_0 * norm_0_0 + norm_0_1 * norm_0_1 + norm_0_2 * norm_0_2);
                        norm_0_0 /= mod;
                        norm_0_1 /= mod;
                        norm_0_2 /= mod;

                        norm_1_0 = vert[x][y + 1] - vert[x + 1][y + 1];
                        norm_1_1 = d;
                        norm_1_2 = vert[x][y] - vert[x][y + 1];
                        mod = Math.sqrt(norm_1_0 * norm_1_0 + norm_1_1 * norm_1_1 + norm_1_2 * norm_1_2);
                        norm_1_0 /= mod;
                        norm_1_1 /= mod;
                        norm_1_2 /= mod;

                        Norm1z[x][y][0] = (float) (m20 * norm_0_0 + m21 * norm_0_1 + m22 * norm_0_2);
                        Norm1z[x][y][1] = (float) (m20 * norm_1_0 + m21 * norm_1_1 + m22 * norm_1_2);
                    }
                }
            }
            iteration++;
        } while(true);
    }

    int min3(int val0, int val1, int val2) {
        return Math.min(Math.min(val0, val1), val2);
    }

    int max3(int val0, int val1, int val2) {
        return Math.max(Math.max(val0, val1), val2);
    }

   protected void paint3D(int image_size, int w2x, int w2y, boolean updateProgress, int tile_size) {

        ptr.setP3Render(true);

        long time = System.currentTimeMillis();

        int[] xPol = new int[3];
        int[] yPol = new int[3];

        Graphics2D g = image.createGraphics();

        int offsetStart = 0;
        int offsetEnd = 0;

        if(tile_size != 1 && detail % tile_size != 0) {
            offsetStart = 1;//detail % tile_size == 1 ? 1 : (detail % tile_size) / 2 + (detail % tile_size) % 2;
            offsetEnd = 1;//detail % tile_size == 1 ? 1 : (detail % tile_size) / 2;
        }

        int ib = offsetStart, ie = detail / tile_size - offsetEnd, sti = 1, jb = offsetStart, je = detail / tile_size - offsetEnd, stj = 1;

        if (m20 < 0) {
            ib = detail / tile_size - 1 - offsetStart;
            ie = -1 + offsetEnd;
            sti = -1;
        }

        if (m22 < 0) {
            jb = detail / tile_size  - 1 - offsetStart;
            je = -1 + offsetEnd;
            stj = -1;
        }

        int old_max = progress.getMaximum();
        int cur_val = progress.getValue();

        if (updateProgress) {
            progress.setMaximum(detail * detail);
            progress.setValue(0);
            progress.setString("3D Render: " + String.format("%3d", 0) + "%");
            progress.setForeground(MainWindow.progress_d3_color);
        }

        int red1, green1, blue1;
        int red2, green2, blue2;
        int red3, green3, blue3;
        int red, green, blue;

        double ct = Math.cos(d3s.fiX), cf = Math.cos(d3s.fiY), st = Math.sin(d3s.fiX), sf = Math.sin(d3s.fiY);
        double m00 = d3s.d3_size_scale * cf, m02 = d3s.d3_size_scale * sf, m10 = d3s.d3_size_scale * st * sf, m11 = d3s.d3_size_scale * ct, m12 = -d3s.d3_size_scale * st * cf;
        double d = image_size / (detail / (double)tile_size);
        double w2 = image_size * 0.5;

        double color_3d_blending = 1 - d3s.color_3d_blending;

        int count = 0;
        for (int i1 = ib; i1 != ie; i1 += sti) {
            for (int j1 = jb; j1 != je; j1 += stj) {

                int i = i1 * tile_size;
                int j = j1 * tile_size;

                int i1p1 = i1 + 1;
                int j1p1 = j1 + 1;

                int ip1 = i1p1 * tile_size;
                int jp1 = j1p1 * tile_size;

                count++;

                double c1 = d * i1 - w2;
                double c2 = d * j1 - w2;
                double c1p1 = d * i1p1 - w2;
                double c2p1 = d * j1p1 - w2;

               if(ip1 < detail && ip1 >= 0 && jp1 < detail && jp1 >= 0) {
                   double v_i_j_0 = m00 * c1 + m02 * c2;
                   double v_ip1_j_0 = m00 * c1p1 + m02 * c2;
                   double v_ip1_jp1_0 = m00 * c1p1 + m02 * c2p1;
                   double v_i_jp1_0 = m00 * c1 + m02 * c2p1;

                   double v_i_j_1 = m10 * c1 + m12 * c2 + vert[i][j] * m11;
                   double v_ip1_j_1 = m10 * c1p1 + m12 * c2 + vert[ip1][j] * m11;
                   double v_ip1_jp1_1 = m10 * c1p1 + m12 * c2p1 + vert[ip1][jp1] * m11;
                   double v_i_jp1_1 = m10 * c1 + m12 * c2p1 + vert[i][jp1] * m11;

                    if (Norm1z[i][j][0] > 0) {
                        xPol[0] = w2x + (int)v_i_j_0;
                        xPol[1] = w2x + (int)v_ip1_j_0;
                        xPol[2] = w2x + (int)v_ip1_jp1_0;
                        yPol[0] = w2y - (int)(v_i_j_1);
                        yPol[1] = w2y - (int)(v_ip1_j_1);
                        yPol[2] = w2y - (int)(v_ip1_jp1_1);

                        red1 = (((vert_color[i][j]) >> 16) & 0xff);
                        green1 = (((vert_color[i][j]) >> 8) & 0xff);
                        blue1 = ((vert_color[i][j]) & 0xff);

                        if(D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1 || D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 2) {
                            red2 = (((vert_color[ip1][j]) >> 16) & 0xff);
                            green2 = (((vert_color[ip1][j]) >> 8) & 0xff);
                            blue2 = ((vert_color[ip1][j]) & 0xff);

                            red3 = (((vert_color[ip1][jp1]) >> 16) & 0xff);
                            green3 = (((vert_color[ip1][jp1]) >> 8) & 0xff);
                            blue3 = ((vert_color[ip1][jp1]) & 0xff);

                            if(D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1) {
                                Color color1 = new Color(getModifiedColor(red1, green1, blue1, Norm1z[i][j][0], d3s.d3_color_type, color_3d_blending, false));
                                Color color2 = new Color(getModifiedColor(red2, green2, blue2, Norm1z[i][j][0], d3s.d3_color_type, color_3d_blending, false));
                                Color color3 = new Color(getModifiedColor(red3, green3, blue3, Norm1z[i][j][0], d3s.d3_color_type, color_3d_blending, false));

                                Point2D p1 = new Point2D.Float(xPol[0], yPol[0]);
                                Point2D p2 = new Point2D.Float(xPol[1], yPol[1]);
                                Point2D p3 = new Point2D.Float(xPol[2], yPol[2]);

                                BarycentricGradientPaint gradient = new BarycentricGradientPaint(p1, p2, p3, color1, color2, color3);
                                g.setPaint(gradient);
                            }
                            else {
                                red = (int)((red1 + red2 + red3) / 3.0 + 0.5);
                                green = (int)((green1 + green2 + green3) / 3.0 + 0.5);
                                blue = (int)((blue1 + blue2 + blue3) / 3.0 + 0.5);
                                g.setColor(new Color(getModifiedColor(red, green, blue, Norm1z[i][j][0], d3s.d3_color_type, color_3d_blending, false)));
                            }
                        }
                        else {
                            red = red1;
                            green = green1;
                            blue = blue1;

                            g.setColor(new Color(getModifiedColor(red, green, blue, Norm1z[i][j][0], d3s.d3_color_type, color_3d_blending, false)));

                        }

                        if (filters[MainWindow.ANTIALIASING] && tile_size == 1) {
                            if(D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1) {
                                int minx = min3(xPol[0], xPol[1], xPol[2]);
                                int miny = min3(yPol[0], yPol[1], yPol[2]);
                                double maxx = max3(xPol[0], xPol[1], xPol[2]);
                                double maxy = max3(yPol[0], yPol[1], yPol[2]);
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                g.fillRect((minx), (miny), (int)Math.ceil(maxx-minx), (int)Math.ceil(maxy-miny));
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g.fillRect((minx), (miny), (int)Math.ceil(maxx-minx), (int)Math.ceil(maxy-miny));
                            }
                            else {
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                g.fillPolygon(xPol, yPol, 3);
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g.fillPolygon(xPol, yPol, 3);
                            }
                        } else {
                            if(D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1) {
                                int minx = min3(xPol[0], xPol[1], xPol[2]);
                                int miny = min3(yPol[0], yPol[1], yPol[2]);
                                double maxx = max3(xPol[0], xPol[1], xPol[2]);
                                double maxy = max3(yPol[0], yPol[1], yPol[2]);
                                g.fillRect((minx), (miny), (int)Math.ceil(maxx-minx), (int)Math.ceil(maxy-miny));
                            }
                            else {
                                g.fillPolygon(xPol, yPol, 3);
                            }
                        }

                    }

                    if (Norm1z[i][j][1] > 0) {
                        xPol[0] = w2x + (int)v_i_j_0;
                        xPol[1] = w2x + (int)v_i_jp1_0;
                        xPol[2] = w2x + (int)v_ip1_jp1_0;
                        yPol[0] = w2y - (int)(v_i_j_1);
                        yPol[1] = w2y - (int)(v_i_jp1_1);
                        yPol[2] = w2y - (int)(v_ip1_jp1_1);

                        red1 = (((vert_color[i][j]) >> 16) & 0xff);
                        green1 = (((vert_color[i][j]) >> 8) & 0xff);
                        blue1 = ((vert_color[i][j]) & 0xff);

                        if(D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1 || D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 2) {
                            red2 = (((vert_color[i][jp1]) >> 16) & 0xff);
                            green2 = (((vert_color[i][jp1]) >> 8) & 0xff);
                            blue2 = ((vert_color[i][jp1]) & 0xff);

                            red3 = (((vert_color[ip1][jp1]) >> 16) & 0xff);
                            green3 = (((vert_color[ip1][jp1]) >> 8) & 0xff);
                            blue3 = ((vert_color[ip1][jp1]) & 0xff);


                            if(D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1) {
                                Color color1 = new Color(getModifiedColor(red1, green1, blue1, Norm1z[i][j][1], d3s.d3_color_type, color_3d_blending, false));
                                Color color2 = new Color(getModifiedColor(red2, green2, blue2, Norm1z[i][j][1], d3s.d3_color_type, color_3d_blending, false));
                                Color color3 = new Color(getModifiedColor(red3, green3, blue3, Norm1z[i][j][1], d3s.d3_color_type, color_3d_blending, false));

                                Point2D p1 = new Point2D.Float(xPol[0], yPol[0]);
                                Point2D p2 = new Point2D.Float(xPol[1], yPol[1]);
                                Point2D p3 = new Point2D.Float(xPol[2], yPol[2]);

                                BarycentricGradientPaint gradient = new BarycentricGradientPaint(p1, p2, p3, color1, color2, color3);
                                g.setPaint(gradient);
                            }
                            else {
                                red = (int)((red1 + red2 + red3) / 3.0 + 0.5);
                                green = (int)((green1 + green2 + green3) / 3.0 + 0.5);
                                blue = (int)((blue1 + blue2 + blue3) / 3.0 + 0.5);
                                g.setColor(new Color(getModifiedColor(red, green, blue, Norm1z[i][j][1], d3s.d3_color_type, color_3d_blending, false)));

                            }
                        }
                        else {
                            red = red1;
                            green = green1;
                            blue = blue1;
                            g.setColor(new Color(getModifiedColor(red, green, blue, Norm1z[i][j][1], d3s.d3_color_type, color_3d_blending, false)));

                        }

                        if (filters[MainWindow.ANTIALIASING] && tile_size == 1) {
                            if(D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1) {
                                int minx = min3(xPol[0], xPol[1], xPol[2]);
                                int miny = min3(yPol[0], yPol[1], yPol[2]);
                                double maxx = max3(xPol[0], xPol[1], xPol[2]);
                                double maxy = max3(yPol[0], yPol[1], yPol[2]);
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                g.fillRect((minx), (miny), (int)Math.ceil(maxx-minx), (int)Math.ceil(maxy-miny));
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g.fillRect((minx), (miny), (int)Math.ceil(maxx-minx), (int)Math.ceil(maxy-miny));
                            }
                            else {
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                g.fillPolygon(xPol, yPol, 3);
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g.fillPolygon(xPol, yPol, 3);
                            }
                        } else {
                            if(D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS == 1) {
                                int minx = min3(xPol[0], xPol[1], xPol[2]);
                                int miny = min3(yPol[0], yPol[1], yPol[2]);
                                double maxx = max3(xPol[0], xPol[1], xPol[2]);
                                double maxy = max3(yPol[0], yPol[1], yPol[2]);
                                g.fillRect((minx), (miny), (int)Math.ceil(maxx-minx), (int)Math.ceil(maxy-miny));
                            }
                            else {
                                g.fillPolygon(xPol, yPol, 3);
                            }
                        }
                    }
                }
            }

            if (updateProgress) {
                progress.setValue(count);
                progress.setString("3D Render: " + String.format("%3d", (int) ((double) count / progress.getMaximum() * 100)) + "%");
            }
        }

        g.dispose();

        if (updateProgress) {
            progress.setString(null);
            progress.setMaximum(old_max);
            progress.setValue(cur_val);
            progress.setForeground(MainWindow.progress_color);
        }
        D3RenderingCalculationTime = System.currentTimeMillis() - time;
    }
    protected void postProcessFastJulia(int image_size, AntialiasingAlgorithm aa, Location location) throws StopExecutionException {
        if (needsPostProcessing()) {
            WaitOnCondition.WaitOnCyclicBarrier(post_processing_sync);

            edgeData = new HashMap<>();
            edgeAAData = new HashMap<>();


            applyPostProcessing(image_size, image_size, image_iterations_fast_julia, escaped_fast_julia, pixelData_fast_julia, aa, location);

            WaitOnCondition.WaitOnCyclicBarrier(normalize_sync2);

            PostProcessing.clear();
        }
    }

    protected void postProcessColorCycling(int image_width, int image_height) throws StopExecutionException {

        if (needsPostProcessing()) {
            WaitOnCondition.WaitOnCyclicBarrier(post_processing_sync);
            applyPostProcessing(image_width, image_height, image_iterations, escaped, null, null, null);
            WaitOnCondition.WaitOnCyclicBarrier(normalize_sync2);
            PostProcessing.clear();
        }
    }

    protected boolean needsPostProcessing() {
        return (
                (!domain_coloring && (hss.histogramColoring || ndes.useNumericalDem || ss.slopes || ls.lighting || bms.bump_map || fdes.fake_de || rps.rainbow_palette || ens.entropy_coloring || ofs.offset_coloring || gss.greyscale_coloring || cns.contour_coloring))
                || (domain_coloring && (ss.slopes || bms.bump_map || ls.lighting))
        ) && !USE_DIRECT_COLOR;
    }

    protected boolean needsSmoothing(FunctionSettings fns, NumericalDistanceEstimatorSettings ndes, LightSettings ls, SlopeSettings ss, BumpMapSettings bms, ContourColoringSettings cns, EntropyColoringSettings ens, RainbowPaletteSettings rps, FakeDistanceEstimationSettings fdes, StatisticsSettings sts) {
        return fns.smoothing
                || ((ndes.useNumericalDem || ss.slopes || ls.lighting || bms.bump_map || cns.contour_coloring || ens.entropy_coloring || rps.rainbow_palette || fdes.fake_de || statisticNeedsSmoothing(sts)) && USE_SMOOTHING_FOR_PROCESSING_ALGS);
    }

    protected boolean statisticNeedsSmoothing(StatisticsSettings sts) {
        return sts.statistic && !(sts.statisticGroup == 3 && !sts.useNormalMap && (!sts.normalMapOverrideColoring || sts.normalMapColoring == 0));
    }

    private boolean forcePostProcessing = false;
    protected void postProcess(int image_width, int image_height, AntialiasingAlgorithm aa, Location location) throws StopSuccessiveRefinementException, StopExecutionException {

        if (forcePostProcessing || needsPostProcessing()) {

            task_post_processed = 0;

            WaitOnCondition.WaitOnCyclicBarrier(post_processing_sync);

            if(progress != null) {
                if(progress.getValue() < progress.getMaximum()) {
                    progress.setValue(progress.getMaximum() - 1);
                }

                try {
                    SwingUtilities.invokeAndWait(() -> {
                        progress.setString("Post Processing");
                        progress.setIndeterminate(true);
                        if (MainWindow.useCustomLaf) {
                            progress.setForeground(Constants.progress_pp_color);
                        }
                    });
                }
                catch (Exception ex) {}

            }

            edgeData = new HashMap<>();
            edgeAAData = new HashMap<>();

            long time = System.currentTimeMillis();

            applyPostProcessing(image_width, image_height, image_iterations, escaped, pixelData, aa, location);

            post_processing_calculation_time_per_task = System.currentTimeMillis() - time;

            PostProcessingCalculationTime.accumulate(post_processing_calculation_time_per_task);

            if(task_post_processed >= 0) {
                total_post_processed.add(task_post_processed);
            }

            if(USE_NON_BLOCKING_RENDERING && supportsNonBlockingRender()) {
                WaitOnCondition.LockRead(stop_rendering_lock);

                WaitOnCondition.WaitOnCyclicBarrier(successive_refinement_rendering_algorithm_barrier);

                if (STOP_RENDERING) {
                    WaitOnCondition.UnlockRead(stop_rendering_lock);

                    finalizePostProcessing();

                    throw new StopSuccessiveRefinementException();
                }
                WaitOnCondition.UnlockRead(stop_rendering_lock);
            }

            WaitOnCondition.WaitOnCyclicBarrier(normalize_sync2);

            finalizePostProcessing();

        }
    }

    private void finalizePostProcessing() {
        PostProcessing.clear();

        if(progress != null) {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    progress.setString(null);
                    progress.setIndeterminate(false);

                    if (MainWindow.useCustomLaf) {
                        progress.setForeground(Constants.progress_color);
                    }
                });
            }
            catch (Exception ex) {}
        }
    }

    private void domainColoringFactory(DomainColoringSettings ds, int interpolation, int color_space) {

        this.ds = ds;

        if (ds.customDomainColoring) {
            domain_color = new CustomDomainColoring(ds, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, gradient, interpolation, gradient_offset, contourFactor, color_space);
            return;
        }

        switch (ds.domain_coloring_alg) {
            case 0:
                domain_color = new BlackGridWhiteCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 1:
                domain_color = new WhiteGridBlackCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 2:
                domain_color = new BlackGridDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 3:
                domain_color = new WhiteGridDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 4:
                domain_color = new BlackGridBrightContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 5:
                domain_color = new WhiteGridDarkContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 6:
                domain_color = new NormBlackGridWhiteCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 7:
                domain_color = new NormWhiteGridBlackCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 8:
                domain_color = new NormBlackGridDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 9:
                domain_color = new NormWhiteGridDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 10:
                domain_color = new NormBlackGridBrightContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 11:
                domain_color = new NormWhiteGridDarkContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 12:
                domain_color = new WhiteCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 13:
                domain_color = new BlackCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 14:
                domain_color = new BrightContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 15:
                domain_color = new DarkContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 16:
                domain_color = new NormWhiteCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 17:
                domain_color = new NormBlackCirclesLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 18:
                domain_color = new NormBrightContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 19:
                domain_color = new NormDarkContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 20:
                domain_color = new BlackGridContoursLog2IsoLinesDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 21:
                domain_color = new NormBlackGridContoursLog2IsoLinesDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 22:
                domain_color = new BlackGridIsoContoursDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 23:
                domain_color = new NormBlackGridIsoContoursDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 24:
                domain_color = new IsoContoursContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 25:
                domain_color = new NormIsoContoursContoursLog2DomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 26:
                domain_color = new GridContoursIsoLinesDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
            case 27:
                domain_color = new NormGridContoursIsoLinesDomainColoring(ds.domain_coloring_mode, palette_outcoloring, color_transfer_outcoloring, color_cycling_location_outcoloring, gps, blending, interpolation, contourFactor, color_space);
                break;
        }

        domain_color.setGradientOffset(gradient_offset);

    }

    protected int getColorForSkippedPixels(int color, int divide_iteration) {

        switch (SKIPPED_PIXELS_ALG) {
            case 0:
                return color;
            case 1:
                return algorithm_colors[(int)(getRandomThreadId() % algorithm_colors.length)];
            case 2:
                return SKIPPED_PIXELS_COLOR;
            case 3:
                return algorithm_colors[(divide_iteration + randomNumber) % algorithm_colors.length];
            case 4:
                return Constants.SKIPPED_PIXELS_SPECIAL_COLOR;
            default:
                return color;
        }

    }

    public static int getGroupPixelColor(int group) {
        return algorithm_colors[(group + randomNumber) % algorithm_colors.length];
    }

    protected double fractional_transfer(double value, int fractional_transfer, int fractional_smoothing, int fractional_transfer_mode, double scale) {

        int sign = 1;
        if(value < 0) {
            sign = -1;
        }

        double fract_part = MathUtils.fract(value);
        fract_part = fade(fractional_smoothing, fract_part);
        double old_fract_part = fract_part;

        switch (fractional_transfer) {
            case 0:
                return (int)value + sign * fract_part;
            case 1:
                double temp = 2*fract_part-1;
                fract_part = 1 - temp * temp;
                break;
            case 2:
                temp = 2*fract_part-1;
                fract_part = temp * temp - 1;
                break;
            case 3:
                temp = 2*fract_part-1;
                temp *= temp;
                fract_part = 1 - temp * temp;
                break;
            case 4:
                temp = 2*fract_part-1;
                temp *= temp;
                fract_part = temp * temp - 1;
                break;
            case 5:
                fract_part = Math.sin(fract_part * Math.PI);
                break;
            case 6:
                fract_part = -Math.sin(fract_part * Math.PI);
                break;
            case 7:
                if(fract_part < 0.5) {
                    fract_part = 2 * fract_part;
                }
                else {
                    fract_part = 2 - 2 *fract_part;
                }
                break;
            case 8:
                if(fract_part < 0.5) {
                    fract_part = -2 * fract_part;
                }
                else {
                    fract_part = -(2 - 2 *fract_part);
                }
                break;
            case 9:
                fract_part = 0.5 - 0.5 * Math.cos(2 * fract_part * Math.PI);
                break;
            case 10:
                fract_part = -0.5 + 0.5 * Math.cos(2 * fract_part * Math.PI);
                break;
            case 11:
                if(fract_part < 0.5) {
                    fract_part = Math.sqrt(2 * fract_part);
                }
                else {
                    fract_part = Math.sqrt(2 - 2 *fract_part);
                }
                break;
            case 12:
                if(fract_part < 0.5) {
                    fract_part = -Math.sqrt(2 * fract_part);
                }
                else {
                    fract_part = -Math.sqrt(2 - 2 *fract_part);
                }
                break;
        }

        value = (int)value + sign * scale * (fract_part + (fractional_transfer_mode == 1 ? old_fract_part : 0));

        return value;
    }

    protected double fractional_transfer_3d(double value) {
        value = d3s.height_invert ? -value : value;
        return fractional_transfer(value, d3s.fractionalTransfer, d3s.fractionalSmoothing, d3s.fractionalTransferMode, d3s.fractionalTransferScale);
    }

    private double calculateData(int y, int x, Location location) {

        Coordinate c = new Coordinate(x, y);
        Double val = edgeData.get(c);
        if(val != null){
            return val;
        }

        task_calculated_extra++;

        double newVal;

        if(domain_coloring) {
            Complex cval = iteration_algorithm.calculateDomain(location.getComplex(x, y));
            newVal = scaleDomainHeight(getDomainHeight(cval));
        }
        else {
            if (d3) {
                newVal = iteration_algorithm.calculate3D(location.getComplex(x, y))[1];
            }
            else {
                newVal = iteration_algorithm.calculate(location.getComplex(x, y));
            }
        }

        edgeData.put(c, newVal);

        return newVal;
    }


    private PixelExtraData calculateData(int y, int x, Location location, AntialiasingAlgorithm aa) {

        Coordinate c = new Coordinate(x, y);
        PixelExtraData val = edgeAAData.get(c);
        if(val != null){
            return val;
        }

        task_calculated_extra++;

        int totalSamples = aa.getTotalSamples();
        int supersampling_num = totalSamples - 1;
        PixelExtraData data = new PixelExtraData();

        if(domain_coloring) {
            Complex cval = iteration_algorithm.calculateDomain(location.getComplex(x, y));
            data.set(0, 0, scaleDomainHeight(getDomainHeight(cval)), true, totalSamples);
        }
        else {
            if(d3) {
                data.set(0, 0, iteration_algorithm.calculate3D(location.getComplex(x, y))[1], iteration_algorithm.escaped(), totalSamples);
            }
            else {
                //We dont care about the color
                data.set(0, 0, iteration_algorithm.calculate(location.getComplex(x, y)), iteration_algorithm.escaped(), totalSamples);
            }
        }

        int hash = c.hashCode();

        for(int i = 0; i < supersampling_num; i++) {
            if(domain_coloring) {
                Complex cval = iteration_algorithm.calculateDomain(location.getAntialiasingComplex(i, hash));
                data.set(i + 1, 0, scaleDomainHeight(getDomainHeight(cval)), true, totalSamples);
            }
            else {
                if(d3) {
                    data.set(i + 1, 0, iteration_algorithm.calculate3D(location.getAntialiasingComplex(i, hash))[1], iteration_algorithm.escaped(), totalSamples);
                }
                else {
                    data.set(i + 1, 0, iteration_algorithm.calculate(location.getAntialiasingComplex(i, hash)), iteration_algorithm.escaped(), totalSamples);
                }
            }

        }

        edgeAAData.put(c, data);

        return data;
    }

    protected double getIterData(int i, int j, int index, double[] image_iterations, int image_width, int image_height, Location location, boolean useIndex) {

        boolean InRange = i >= 0 && j >= 0 && i < image_height && j < image_width;
        if(location == null) {
            if(InRange) {
                return image_iterations[index];
            }
            else if(useIndex) {
                return image_iterations[index];
            }

            return 0;
        }

        if(InRange) {
            return image_iterations[index];
        }

        return calculateData(i, j, location);
    }

    protected double getIterData(int i, int j, int index, double[] image_iterations, int image_width, int image_height, Location location, boolean useIndex, double defaultValue) {

        boolean InRange = i >= 0 && j >= 0 && i < image_height && j < image_width;
        if(location == null) {
            if(InRange) {
                return image_iterations[index];
            }
            else if(useIndex) {
                return image_iterations[index];
            }

            return defaultValue;
        }

        if(InRange) {
            return image_iterations[index];
        }

        return calculateData(i, j, location);
    }

    protected PixelExtraData getIterData(int i, int j, int index, PixelExtraData[] data, int image_width, int image_height, Location location, AntialiasingAlgorithm aa, boolean useIndex) {

        boolean InRange = i >= 0 && j >= 0 && i < image_height && j < image_width;

        if(location == null) {
            if(InRange) {
                return data[index];
            }
            else if(useIndex) {
                return data[index];
            }

            return null;
        }

        if(InRange) {
            return data[index];
        }

        return calculateData(i, j, location, aa);
    }

    protected void renderSquares(int image_width, int image_height) throws StopExecutionException {

        WaitOnCondition.WaitOnCyclicBarrier(squares_sync);

        int white = 0xffffffff;
        int grey = 0xffAAAAAA;

        int colA = white;
        int colB = grey;

        int length = 14;

        int iteration = 0;

        initializeRectangleAreasQueue(image_width, image_height);

        do {
            Square currentSquare = getNextRectangleArea(iteration);

            if (currentSquare == null) {
                break;
            }
            int FROMx = currentSquare.x1;
            int TOx = currentSquare.x2;
            int FROMy = currentSquare.y1;
            int TOy = currentSquare.y2;
            for (int y = FROMy; y < TOy; y++) {

                if (y % length < length / 2) {
                    colA = white;
                    colB = grey;
                } else {
                    colB = white;
                    colA = grey;
                }

                for (int x = FROMx, loc = y * image_width + x; x < TOx; x++, loc++) {
                    if (rgbs[loc] == 0x00ffffff) {
                        if (x % length < length / 2) {
                            rgbs[loc] = colA;
                        } else {
                            rgbs[loc] = colB;
                        }

                    }
                }
            }
            iteration++;
        } while(true);
    }

    double Sigmoid(double x) {
        return (1.0/ (1.0+Math.exp(-x/10.0)))-0.5;
    }
    public double T(double x) {
        return 2.0* Sigmoid(Math.abs(x+1./x));
    }

    protected double calaculateDomainColoringHeight(double res) {

        //res = Math.abs(res + 1 / res);
        //res = 1 / (1 + Math.exp(-res));
        //res = (res / (res + 1));

        if (Double.isInfinite(res)) {
            res = 10000000;
        }
        return (res > 10000000 ? 10000000 : res);

    }

    protected double getDomainHeight(Complex a) {

        double val = 0;
        switch (ds.domain_height_method) {
            case 0:
                val = a.norm();
                break;
            case 1:
                val = a.getRe();
                break;
            case 2:
                val = a.getIm();
                break;
            case 3:
                val = a.getRe() + a.getIm();
                break;
        }

        if(ds.domain_height_normalization_method == 1) {
            double dr = Math.abs(T(val));
            return dr * 4.0 * (Sigmoid(Math.log(Math.abs(val)))); //Taken from fragmentarium for domain lifting
        }
        return val;
    }

    private double[] getFences(ArrayList<Double> data) {

        if(data.isEmpty()) {
            return new double[] {-Double.MAX_VALUE, Double.MAX_VALUE};
        }
        
        Collections.sort(data);

        //double median = calculateMedian(data, 0, data.size());
        double lower_quartile = calculateMedian(data, 0, data.size() / 2);
        double upper_quartile = calculateMedian(data, (data.size() + 1) / 2, data.size());
        double iqr = upper_quartile - lower_quartile;

        if(iqr == 0) {
            double mean = 0;
            double variance = 0;
            int samples = 0;
            for (int i = 0; i < data.size(); i++) {
                samples++;
                double val = data.get(i);
                double delta =  val - mean;
                mean += delta / samples;
                double delta2 = val - mean;
                variance += delta * delta2;
            }
            double sigma = Math.sqrt(variance / samples);
            double temp = 3 * sigma;
            return  new double[] {mean - temp, mean + temp};
        }
        else {
            double temp = 1.5 * iqr;
            return  new double[] {lower_quartile - temp, upper_quartile + temp};
        }
    }

    private int[] finalizeHistogramColoring(double[] image_iterations, PixelExtraData[] data, int[] colors, int i, int j, int image_width, int image_height, boolean[] escaped) {

        double histogramDensity = hss.histogramDensity;
        int mapping = hss.hmapping;
        int histogramGranularity = hss.histogramBinGranularity;

        boolean color_smoothing = true;
        if(banded) {
            color_transfer_outcoloring.setBanded(false);
            color_transfer_incoloring.setBanded(false);
            color_smoothing = palette_outcoloring.getColorSmoothing();
            palette_outcoloring.setColorSmoothing(false);
            palette_incoloring.setColorSmoothing(false);
        }

        int[] res;
        if(mapping == 0) {
            res = pp.applyHistogramToPixel(i * image_width + j, colors, data, histogramGranularity, histogramDensity, image_iterations, escaped);
        }
        else if(mapping == 6) {
            res = pp.applyRankOrderMappingToPixel(i * image_width + j, colors, data, image_iterations, escaped);
        }
        else {
            res = pp.applyScalingToPixel(i * image_width + j, colors, data, mapping, image_iterations, escaped);
        }

        if(banded) {
            color_transfer_outcoloring.setBanded(true);
            color_transfer_incoloring.setBanded(true);
            palette_outcoloring.setColorSmoothing(color_smoothing);
            palette_incoloring.setColorSmoothing(color_smoothing);
        }
        return res;
    }

    public static String getDefaultInitialValue() {

        return default_init_val;

    }

    public static double getConvergentBailout() {

        return convergent_bailout;

    }

    public static void setDomainImageData(int width, int height, boolean mode) {

        if (mode && ((domain_image_data_re == null && domain_image_data_im == null) || (domain_image_data_re.length != width * height && domain_image_data_im.length != width * height))) {
            domain_image_data_re = new double[width * height];
            domain_image_data_im = new double[width * height];
        } else if (!mode) {
            domain_image_data_re = null;
            domain_image_data_im = null;
        }

    }

    protected static PixelExtraData[] pixelData;
    protected static PixelExtraData[] pixelData_fast_julia;
    public static void setArrays(int width, int height, boolean usesDomainColoring, boolean needsExtraData) {

        WIDTH = width;
        HEIGHT = height;

        vert = null;
        vert_color = null;
        Norm1z = null;

        if(image_iterations == null || image_iterations.length != width * height) {
            image_iterations = new double[width * height];
            escaped = new boolean[width * height];
        }

        if(image_iterations_fast_julia == null || image_iterations_fast_julia.length != FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE) {
            image_iterations_fast_julia = new double[FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE];
            escaped_fast_julia = new boolean[FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE];
        }

        if(needsExtraData) {
            if((pixelData == null || pixelData.length != width * height)) {
                pixelData = new PixelExtraData[width * height];
                for (int i = 0; i < pixelData.length; i++) {
                    pixelData[i] = new PixelExtraData();
                }
            }

            if((pixelData_fast_julia == null || pixelData_fast_julia.length != FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE)) {
                pixelData_fast_julia = new PixelExtraData[FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE];
                for (int i = 0; i < pixelData_fast_julia.length; i++) {
                    pixelData_fast_julia[i] = new PixelExtraData();
                }
            }
        }
        else {
            pixelData = null;
            pixelData_fast_julia = null;
        }

        if (usesDomainColoring) {
            if(domain_image_data_re == null || domain_image_data_re.length != width * height) {
                domain_image_data_re = new double[width * height];
                domain_image_data_im = new double[width * height];
            }
        }
        else {
            domain_image_data_re = null;
            domain_image_data_im = null;
        }
    }

    public static void setArraysMinimalRenderer(int width, int height, boolean needsExtraData) {

        WIDTH = width;
        HEIGHT = height;


        if(image_iterations == null || image_iterations.length != width * height) {
            image_iterations = new double[width * height];
            escaped = new boolean[width * height];
        }

        domain_image_data_re = null;
        domain_image_data_im = null;

        if(needsExtraData) {
            if((pixelData == null || pixelData.length != width * height)) {
                pixelData = new PixelExtraData[width * height];
                for (int i = 0; i < pixelData.length; i++) {
                    pixelData[i] = new PixelExtraData();
                }
            }
        }
        else {
            pixelData = null;
        }

    }

    public static void set3DArrays(int detail, boolean needsExtraData) {

        WIDTH = detail;
        HEIGHT = detail;

        image_iterations_fast_julia = null;
        escaped_fast_julia = null;
        domain_image_data_re = null;
        domain_image_data_im = null;

        vert = new double[detail][detail];
        vert_color = new int[detail][detail];
        Norm1z = new float[detail][detail][2];


        if(image_iterations == null || image_iterations.length != detail * detail) {
            image_iterations = new double[detail * detail];
            escaped = new boolean[detail * detail];
        }

        if(needsExtraData) {
            if((pixelData == null || pixelData.length != detail * detail)) {
                pixelData = new PixelExtraData[detail * detail];
                for (int i = 0; i < pixelData.length; i++) {
                    pixelData[i] = new PixelExtraData();
                }
            }
        }
        else {
            pixelData = null;
        }

    }

    public static boolean hasExtraData(int width, int height) {
        return pixelData != null && pixelData.length == width * height && pixelData[0].rgb_values != null
                && pixelData[0].values != null && pixelData[0].escaped != null;
    }

    public static void setExtraDataArrays(boolean needsExtraData, int width, int height) {
        if(needsExtraData) {
            if((pixelData == null || pixelData.length != width * height)) {
                pixelData = new PixelExtraData[width * height];
                for (int i = 0; i < pixelData.length; i++) {
                    pixelData[i] = new PixelExtraData();
                }
            }

            if((pixelData_fast_julia == null || pixelData_fast_julia.length != FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE)) {
                pixelData_fast_julia = new PixelExtraData[FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE];
                for (int i = 0; i < pixelData_fast_julia.length; i++) {
                    pixelData_fast_julia[i] = new PixelExtraData();
                }
            }
        }
        else {
            pixelData = null;
            pixelData_fast_julia = null;
        }
    }

    public static void resetTaskData(int num_tasks, boolean createFullImageAfterPreview, Apfloat size) {

        NumericLibrary.current_size = size;
        TOTAL_NUM_TASKS = num_tasks;
        STOP_RENDERING = false;
        DONE = false;
        number_of_tasks = new AtomicInteger(num_tasks);
        finalize_sync = new AtomicInteger(0);
        total_calculated = new LongAdder();
        total_calculated_extra = new LongAdder();
        total_completed = new LongAdder();
        total_post_processed = new LongAdder();
        total_pixel_grouping = new LongAdder[MAX_GROUPING];
        for(int i = 0; i < total_pixel_grouping.length; i++) {
            total_pixel_grouping[i] = new LongAdder();
        }
        max_pixel_calculation_time = new LongAccumulator(Math::max, Long.MIN_VALUE);
        PostProcessingCalculationTime = new LongAccumulator(Math::max, Long.MIN_VALUE);
        post_processing_sync = new CyclicBarrier(num_tasks);
        calculate_vectors_sync = new CyclicBarrier(num_tasks);
        painting_sync = new AtomicInteger(0);
        height_scaling_sync = new AtomicInteger(0);
        height_scaling_sync2 = new CyclicBarrier(num_tasks);
        height_scaling_sync3 = new AtomicInteger(0);
        height_scaling_sync4 = new CyclicBarrier(num_tasks);
        height_function_sync = new CyclicBarrier(num_tasks);
        gaussian_scaling_sync = new AtomicInteger(0);
        remove_outliers_sync = new AtomicInteger(0);
        remove_outliers_sync3 = new AtomicInteger(0);
        gaussian_scaling_sync2 = new CyclicBarrier(num_tasks);
        remove_outliers_sync2 = new CyclicBarrier(num_tasks);
        remove_outliers_sync4 = new CyclicBarrier(num_tasks);
        normal_rendering_algorithm_pixel = new AtomicInteger(0);
        normal_rendering_algorithm_pixel2 = new AtomicInteger(0);
        apply_skipped_color_pixel = new AtomicInteger(0);
        render_squares_pixel = new AtomicInteger(0);
        quick_render_rendering_algorithm_pixel = new AtomicInteger[SUCCESSIVE_REFINEMENT_EXPONENT];
        for(int i = 0; i < quick_render_rendering_algorithm_pixel.length; i++) {
            quick_render_rendering_algorithm_pixel[i] = new AtomicInteger(0);
        }

        successive_refinement_rendering_algorithm_pixel = new AtomicInteger[2 * (SUCCESSIVE_REFINEMENT_EXPONENT + 1)];
        for(int i = 0; i < successive_refinement_rendering_algorithm_pixel.length; i++) {
            successive_refinement_rendering_algorithm_pixel[i] = new AtomicInteger(0);
        }

        successive_refinement_rendering_algorithm2_pixel = new AtomicInteger[2 * (2 * SUCCESSIVE_REFINEMENT_EXPONENT + 1)];
        for(int i = 0; i < successive_refinement_rendering_algorithm2_pixel.length; i++) {
            successive_refinement_rendering_algorithm2_pixel[i] = new AtomicInteger(0);
        }

        quick_render_rendering_algorithm_barrier = new CyclicBarrier(num_tasks);
        successive_refinement_rendering_algorithm_barrier = new CyclicBarrier(num_tasks);
        normal_rendering_algorithm_post_processing = new AtomicInteger(0);
        normal_rendering_algorithm_post_processing2 = new AtomicInteger(0);
        normal_rendering_algorithm_apply_palette = new AtomicInteger(0);
        normal_rendering_algorithm_apply_palette2 = new AtomicInteger(0);
        normal_rendering_algorithm_histogram = new AtomicInteger(0);
        color_cycling_filters_sync = new CyclicBarrier(num_tasks);
        color_cycling_restart_sync = new CyclicBarrier(num_tasks);
        shade_color_height_sync = new CyclicBarrier(num_tasks);
        initialize_jobs_sync = new CyclicBarrier(num_tasks);
        initialize_jobs_sync2 = new CyclicBarrier(num_tasks);
        initialize_jobs_sync3 = new CyclicBarrier(num_tasks);
        initialize_jobs_sync4 = new CyclicBarrier(num_tasks);
        mariani_silver_first_rendering = new AtomicInteger(0);
        PostProcessing.init(num_tasks);
        normalize_sync2 = new CyclicBarrier(num_tasks);
        normalize_find_ranges_sync_3d = new CyclicBarrier(num_tasks);
        color_cycling_toggle_lock = new ReadWriteLock();
        stop_rendering_lock = new ReadWriteLock();

        reference_calc_sync = new AtomicInteger(0);
        reference_sync = new CyclicBarrier(num_tasks);
        squares_sync = new CyclicBarrier(num_tasks);

        if(!createFullImageAfterPreview) {
            RootColoring.roots.clear();
        }

        Fractal.resetTimes();
        D3RenderingCalculationTime = 0;
        FilterCalculationTime = 0;

        QueueBasedRender.initStatic(num_tasks);

        rendering_done_per_task = new int[num_tasks];
    }

    public void setTaskId(int taskId) {

        this.taskId = taskId;
        if(fractal != null) {
            fractal.setTaskId(taskId);
        }

    }

    public void setCreatePreview(boolean val) {
        createPreview = val;
    }

    public void setZoomToCursor(boolean val) {
        zoomToCursor = val;
    }

    public void setCreateFullImageAfterPreview(boolean val) {
        createFullImageAfterPreview = val;
    }

    public void colorTransferFactory(int transfer_function_out, int transfer_function_in, double color_intensity_out, double color_intensity_in, double color_density_out, double color_density_in) {

        switch (transfer_function_out) {

            case MainWindow.DEFAULT:
                color_transfer_outcoloring = new DefaultTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, banded);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer_outcoloring = new SqrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, color_density_out, banded);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer_outcoloring = new CbrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, color_density_out, banded);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer_outcoloring = new ForthrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, color_density_out, banded);
                break;
            case MainWindow.LOGARITHM:
                color_transfer_outcoloring = new LogarithmTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, color_density_out, banded);
                break;
            case MainWindow.LOG_LOG:
                color_transfer_outcoloring = new LogLogTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, color_density_out, banded);
                break;
            case MainWindow.ATAN:
                color_transfer_outcoloring = new AtanTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, color_density_out, banded);
                break;
            case MainWindow.LINEAR:
                color_transfer_outcoloring = new LinearTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_out, color_density_out, banded);
                break;
            case MainWindow.KF_SQUARE_ROOT:
                color_transfer_outcoloring = new KFSqrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, banded);
                break;
            case MainWindow.KF_CUBE_ROOT:
                color_transfer_outcoloring = new KFCbrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, banded);
                break;
            case MainWindow.KF_FOURTH_ROOT:
                color_transfer_outcoloring = new KFForthrtTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, banded);
                break;
            case MainWindow.KF_LOGARITHM:
                color_transfer_outcoloring = new KFLogarithmTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, banded);
                break;
            case MainWindow.KF_LOG_LOG:
                color_transfer_outcoloring = new KFLogLogTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, banded);
                break;
            case MainWindow.KF_ATAN:
                color_transfer_outcoloring = new KFAtanTransferFunction(palette_outcoloring.getPaletteLength(), color_intensity_out, banded);
                break;
        }


        switch (transfer_function_in) {

            case MainWindow.DEFAULT:
                color_transfer_incoloring = new DefaultTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, banded);
                break;
            case MainWindow.SQUARE_ROOT:
                color_transfer_incoloring = new SqrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, color_density_in, banded);
                break;
            case MainWindow.CUBE_ROOT:
                color_transfer_incoloring = new CbrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, color_density_in, banded);
                break;
            case MainWindow.FOURTH_ROOT:
                color_transfer_incoloring = new ForthrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, color_density_in, banded);
                break;
            case MainWindow.LOGARITHM:
                color_transfer_incoloring = new LogarithmTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, color_density_in, banded);
                break;
            case MainWindow.LOG_LOG:
                color_transfer_incoloring = new LogLogTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, color_density_in, banded);
                break;
            case MainWindow.ATAN:
                color_transfer_incoloring = new AtanTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, color_density_in, banded);
                break;
            case MainWindow.LINEAR:
                color_transfer_incoloring = new LinearTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, color_density_in, banded);
                break;
            case MainWindow.KF_SQUARE_ROOT:
                color_transfer_incoloring = new KFSqrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, banded);
                break;
            case MainWindow.KF_CUBE_ROOT:
                color_transfer_incoloring = new KFCbrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, banded);
                break;
            case MainWindow.KF_FOURTH_ROOT:
                color_transfer_incoloring = new KFForthrtTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, banded);
                break;
            case MainWindow.KF_LOGARITHM:
                color_transfer_incoloring = new KFLogarithmTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, banded);
                break;
            case MainWindow.KF_LOG_LOG:
                color_transfer_incoloring = new KFLogLogTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, banded);
                break;
            case MainWindow.KF_ATAN:
                color_transfer_incoloring = new KFAtanTransferFunction(palette_incoloring.getPaletteLength(), color_intensity_in, banded);
                break;
        }

    }

    protected int getModifiedColor(int red, int green, int blue, double coef, int colorMethod, double colorBlending, boolean reverseBlending) {

        if (colorMethod == 0) { //Lab
            red = ColorCorrection.gammaToLinear(red);
            green = ColorCorrection.gammaToLinear(green);
            blue = ColorCorrection.gammaToLinear(blue);
            double[] res = ColorSpaceConverter.RGBtoLAB(red, green, blue);
            double val = contourFactor * coef * res[0];
            val = val > 100 ? 100 : val;
            int[] rgb = ColorSpaceConverter.LABtoRGB(val, res[1], res[2]);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        } else if (colorMethod == 1) { //HSB
            red = ColorCorrection.gammaToLinear(red);
            green = ColorCorrection.gammaToLinear(green);
            blue = ColorCorrection.gammaToLinear(blue);
            double[] res = ColorSpaceConverter.RGBtoHSB(red, green, blue);
            double val = contourFactor * coef * res[2];
            val = val > 1 ? 1 : val;
            int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        } else if (colorMethod == 2) { //HSL
            red = ColorCorrection.gammaToLinear(red);
            green = ColorCorrection.gammaToLinear(green);
            blue = ColorCorrection.gammaToLinear(blue);
            double[] res = ColorSpaceConverter.RGBtoHSL(red, green, blue);
            double val = contourFactor * coef * res[2];
            val = val > 1 ? 1 : val;
            int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
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
        } else if (colorMethod == 4) { //scale
            red = ColorCorrection.gammaToLinear(red);
            green = ColorCorrection.gammaToLinear(green);
            blue = ColorCorrection.gammaToLinear(blue);
            red = (int) (contourFactor * coef * red + 0.5);
            green = (int) (contourFactor * coef * green + 0.5);
            blue = (int) (contourFactor * coef * blue + 0.5);
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            blue = blue > 255 ? 255 : blue;
            return ColorCorrection.linearToGamma(red, green, blue);
        }
        else {
            red = ColorCorrection.gammaToLinear(red);
            green = ColorCorrection.gammaToLinear(green);
            blue = ColorCorrection.gammaToLinear(blue);
            double[] res = ColorSpaceConverter.RGBtoOKLAB(red, green, blue);
            double val = contourFactor * coef * res[0];
            val = val > 1 ? 1 : val;
            int[] rgb = ColorSpaceConverter.OKLABtoRGB(val, res[1], res[2]);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        }
    }

    public double scaleDomainHeight(double norm) {

        if (ds.domainProcessingTransfer == 0) {
            return norm * ds.domainProcessingHeightFactor;
        } else {
            return 1 / (norm * ds.domainProcessingHeightFactor);
        }

    }

    private void interpolationFactory(int color_interpolation, int color_space) {

        method = InterpolationMethod.create(color_interpolation);
        method.setColorSpace(color_space);

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

    public void calculateReferenceFastJulia(Location loc) {

        Fractal.ReferenceCalculationTime = 0;
        Fractal.SecondReferenceCalculationTime = 0;
        Fractal.SACalculationTime = 0;
        Fractal.BLACalculationTime = 0;
        Fractal.Nanomb1CalculationTime = 0;

        Fractal.total_bla_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_bla_steps = new long[TOTAL_NUM_TASKS];
        Fractal.total_perturb_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_nanomb1_skipped_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_double_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_scaled_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_float_exp_iterations = new long[TOTAL_NUM_TASKS];

        GenericComplex temp = loc.getReferencePoint();

        Fractal.clearReferences(true, false);
        fractal.calculateReferencePoint(temp, size, useExtendedRange(size, fractal), fractal.getStartingIterations(), fractal.getSecondStartingIterations(), loc, null);

    }

    public static boolean useExtendedRange(Apfloat size, Fractal f) {
        return f.needsExtendedRange() || size.compareTo(MyApfloat.MAX_DOUBLE_SIZE) < 0;
    }

    public void initializeHighPrecision() {
        Fractal.total_iterations = new long[TOTAL_NUM_TASKS];

        Fractal.total_min_iterations = new long[TOTAL_NUM_TASKS];
        Arrays.fill(Fractal.total_min_iterations,  Long.MAX_VALUE);

        Fractal.total_max_iterations = new long[TOTAL_NUM_TASKS];
        Arrays.fill(Fractal.total_max_iterations, Long.MIN_VALUE);

        Fractal.total_max_iterations_ignore_max_iter = new long[TOTAL_NUM_TASKS];
        Arrays.fill(Fractal.total_max_iterations_ignore_max_iter,  Long.MIN_VALUE);
    }

    public void calculateReference(Location loc) {

        Fractal.ReferenceCalculationTime = 0;
        Fractal.SecondReferenceCalculationTime = 0;
        Fractal.SACalculationTime = 0;
        Fractal.BLACalculationTime = 0;
        Fractal.Nanomb1CalculationTime = 0;

        Fractal.total_bla_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_bla_steps = new long[TOTAL_NUM_TASKS];
        Fractal.total_perturb_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_nanomb1_skipped_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_double_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_scaled_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_float_exp_iterations = new long[TOTAL_NUM_TASKS];
        Fractal.total_rebases = new long[TOTAL_NUM_TASKS];
        Fractal.total_realigns = new long[TOTAL_NUM_TASKS];
        Fractal.total_iterations = new long[TOTAL_NUM_TASKS];

        Fractal.total_min_iterations = new long[TOTAL_NUM_TASKS];
        Arrays.fill(Fractal.total_min_iterations,  Long.MAX_VALUE);

        Fractal.total_max_iterations = new long[TOTAL_NUM_TASKS];
        Arrays.fill(Fractal.total_max_iterations,  Long.MIN_VALUE);

        Fractal.total_max_iterations_ignore_max_iter = new long[TOTAL_NUM_TASKS];
        Arrays.fill(Fractal.total_max_iterations_ignore_max_iter,  Long.MIN_VALUE);

        int old_max = progress.getMaximum();
        int cur_val = progress.getValue();

        GenericComplex temp = loc.getReferencePoint();

        boolean isDeep = useExtendedRange(size, fractal);

        int max_ref_iterations = fractal.getReferenceMaxIterations();

        boolean referencesArePresent = !(Fractal.referenceDeep == null && isDeep) && !(Fractal.reference == null && !isDeep);
        boolean refTypeIsTheSame = Fractal.refPoint != null && temp.getClass().equals(Fractal.refPoint.getClass()) && Fractal.RefType.equals(fractal.getRefType());
        boolean detectPeriod = TaskRender.DETECT_PERIOD && fractal.supportsPeriod() && fractal.getUserPeriod() == 0;
        boolean hasStoppedReferenceCalculation = detectPeriod && TaskRender.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD && Fractal.DetectedPeriod != 0 &&  fractal.getUserPeriod() == 0 && fractal.canStopOnDetectedPeriod();

        if(refTypeIsTheSame && referencesArePresent && temp.compare(Fractal.refPoint) == 0) {

            if(!hasStoppedReferenceCalculation && max_ref_iterations > fractal.getReferenceLength()) {
                fractal.calculateReferencePoint(temp, size, isDeep, fractal.getNextIterations(), fractal.getSecondNextIterations(), loc, progress);
            }
            else if(detectPeriod && CALCULATE_PERIOD_EVERY_TIME_FROM_START && (size.compareTo(Fractal.LastCalculationSize) != 0 || fractal.getPeriod() == 0)) {
                Fractal.clearReferences(true, false);
                fractal.calculateReferencePoint(temp, size, isDeep, fractal.getStartingIterations(), fractal.getSecondStartingIterations(), loc, progress);
            }
            else if(detectPeriod && !CALCULATE_PERIOD_EVERY_TIME_FROM_START && (size.compareTo(Fractal.LastCalculationSize) < 0 || fractal.getPeriod() == 0) && fractal.shouldRecalculateForPeriodDetection(isDeep, loc)) {
                if(fractal.getPeriodDetectionAlgorithm() == 1
                        && hasStoppedReferenceCalculation
                        && !(APPROXIMATION_ALGORITHM == 3 && fractal.supportsNanomb1())
                        && !(APPROXIMATION_ALGORITHM == 1 && fractal.supportsSeriesApproximation())
                        && ((isDeep && Fractal.referenceData.mdzdc != null) || (!isDeep && Fractal.referenceData.dzdc != null))
                ) {
                    Fractal.DetectedPeriod = 0;
                    fractal.calculateReferencePoint(temp, size, isDeep, fractal.getNextIterations(), fractal.getSecondNextIterations(), loc, progress);
                }
                else {
                    Fractal.clearReferences(true, false);
                    fractal.calculateReferencePoint(temp, size, isDeep, fractal.getStartingIterations(), fractal.getSecondStartingIterations(), loc, progress);
                }
            }
            else {
                if (APPROXIMATION_ALGORITHM == 1 && fractal.supportsSeriesApproximation()
                        && size.compareTo(MyApfloat.SA_START_SIZE) <= 0
                && (Fractal.coefficients == null || Fractal.SAMaxSkip != TaskRender.SERIES_APPROXIMATION_MAX_SKIP_ITER
                        || Fractal.SAOOMDiff != TaskRender.SERIES_APPROXIMATION_OOM_DIFFERENCE
                        || Fractal.SATerms != TaskRender.SERIES_APPROXIMATION_TERMS
                        || Fractal.SASize != loc.getMaxSizeInImage().log2approx()
                        || Fractal.SAskippedIterations == 0)
                ) {
                    fractal.calculateSeriesWrapper(size, isDeep, loc, progress);
                }
                else if(APPROXIMATION_ALGORITHM == 2 && fractal.supportsBilinearApproximation() && (Fractal.B == null || (isDeep && Fractal.B.bdeep == null)
                        || (!isDeep && Fractal.B.b == null)
                        || BLA_BITS != Fractal.BLAbits
                        || fractal.getBLALength() != Fractal.B.M
                        || loc.getMaxSizeInImage().compareToBothPositive(Fractal.BLASize) != 0
                        || BLA_STARTING_LEVEL != Fractal.BLAStartingLevel
                )) {
                    fractal.calculateBLAWrapper(isDeep, loc, progress);
                }
                else if(APPROXIMATION_ALGORITHM == 4 && fractal.supportsBilinearApproximation2()
                 && (loc.getSize().compareToBothPositive(Fractal.BLA2Size) != 0
                     || fractal.BLA2ParamsDiffer()
                    || (isDeep && fractal.useFullFloatExp() != Fractal.BLA2UsedFullFloatExp)
                        || Fractal.laReference == null || !Fractal.laReference.isValid)) {

                    if(Fractal.laReference == null ||
                            !Fractal.laReference.isValid
                            || fractal.BLA2ParamsDiffer()
                            || (isDeep && fractal.useFullFloatExp() != Fractal.BLA2UsedFullFloatExp)
                            //|| (isDeep && !fractal.useFullFloatExp())
                            || (isDeep && fractal.useFullFloatExp() && LAReference.CONVERT_TO_DOUBLE_WHEN_POSSIBLE)
                            || (isDeep != Fractal.laReference.calculatedForDeep)
                    ) {
                        fractal.calculateBLA2Wrapper(isDeep, loc, progress);
                    }
                    else if(Fractal.laReference.isValid && LAReference.CREATE_AT) {
                        fractal.calculateBLA2ATWrapper(loc, progress);
                    }
                }
                else if(APPROXIMATION_ALGORITHM == 5 && fractal.supportsBilinearApproximation3()
                        && (MipLAStep.ValidRadiusScale != Fractal.BLA3UsedScale
                 || BLA3_STARTING_LEVEL != Fractal.BLA3StartingLevel)) {
                    fractal.calculateBLA3Wrapper(isDeep, progress);
                }

                fractal.clearUnusedReferences(isDeep);
                fractal.finalizeReference();
            }


            progress.setString(null);
            progress.setMaximum(old_max);
            progress.setValue(cur_val);
            progress.setForeground(MainWindow.progress_color);
            return;

        }

        if(fractal.isJulia()) {
            Fractal.clearReferences(!(referencesArePresent && refTypeIsTheSame), false);//Dont clear the julia refs if only the ref point changes
        }
        else {
            Fractal.clearReferences(true, false);
        }
        fractal.calculateReferencePoint(temp, size, isDeep, fractal.getStartingIterations(), fractal.getSecondStartingIterations(), loc, progress);

        progress.setString(null);
        progress.setMaximum(old_max);
        progress.setValue(cur_val);
        progress.setForeground(MainWindow.progress_color);

    }

    public boolean isQuickRender() {
        return quickRender;
    }

    public boolean isFastJulia() {
        return action == FAST_JULIA || action == FAST_JULIA_POLAR;
    }

    public boolean isJulia() {
        return (action == NORMAL || action == NORMAL_MINIMAL_RENDERER || action == POLAR || action == POLAR_MINIMAL_RENDERER) && julia;
    }

    public boolean isNonJulia() {
        return (action == NORMAL || action == NORMAL_MINIMAL_RENDERER || action == POLAR || action == POLAR_MINIMAL_RENDERER) && !julia;
    }

    public boolean isJuliaMap() {
        return action == JULIA_MAP || action == JULIA_MAP_POLAR;
    }

    public boolean isDomainColoring() {
        return action == DOMAIN || action == DOMAIN_POLAR || action == DOMAIN_MINIMAL_RENDERER || action == DOMAIN_POLAR_MINIMAL_RENDERER;
    }

    protected int applyContour(int colorMode, int r, int g, int b, double coef, double coef2, double blending_coef) {
        if (colorMode == 0) { //Lab
            r = ColorCorrection.gammaToLinear(r);
            g = ColorCorrection.gammaToLinear(g);
            b = ColorCorrection.gammaToLinear(b);
            double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
            int[] rgb = ColorSpaceConverter.LABtoRGB(res[0] * coef + coef2 * 100, res[1], res[2]);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        } else if (colorMode == 1) { //HSB
            r = ColorCorrection.gammaToLinear(r);
            g = ColorCorrection.gammaToLinear(g);
            b = ColorCorrection.gammaToLinear(b);
            double[] res = ColorSpaceConverter.RGBtoHSB(r, g, b);

            double val = res[2] * coef + coef2;

            if (val > 1) {
                val = 1;
            }
            if (val < 0) {
                val = 0;
            }

            int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        } else if (colorMode == 2) { //HSL
            r = ColorCorrection.gammaToLinear(r);
            g = ColorCorrection.gammaToLinear(g);
            b = ColorCorrection.gammaToLinear(b);
            double[] res = ColorSpaceConverter.RGBtoHSL(r, g, b);

            double val = res[2] * coef + coef2;

            if (val > 1) {
                val = 1;
            }
            if (val < 0) {
                val = 0;
            }

            int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        } else if (colorMode == 3) { //Blending
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

            temp_red = ColorCorrection.gammaToLinear(temp_red);
            temp_green = ColorCorrection.gammaToLinear(temp_green);
            temp_blue = ColorCorrection.gammaToLinear(temp_blue);

            int new_color = blending.blend(temp_red, temp_green, temp_blue, r, g, b, 1 - blending_coef);

            r = (new_color >> 16) & 0xFF;
            g = (new_color >> 8) & 0xFF;
            b = new_color & 0xFF;

            r = ColorCorrection.gammaToLinear(r);
            g = ColorCorrection.gammaToLinear(g);
            b = ColorCorrection.gammaToLinear(b);

            double temp = coef2 * 255;
            r = (int) (r + temp + 0.5);
            g = (int) (g + temp + 0.5);
            b = (int) (b + temp + 0.5);

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

            return ColorCorrection.linearToGamma(r, g, b);
        } else if (colorMode == 4) { //scaling
            r = ColorCorrection.gammaToLinear(r);
            g = ColorCorrection.gammaToLinear(g);
            b = ColorCorrection.gammaToLinear(b);
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

            return ColorCorrection.linearToGamma(r, g, b);
        }
        else {
            r = ColorCorrection.gammaToLinear(r);
            g = ColorCorrection.gammaToLinear(g);
            b = ColorCorrection.gammaToLinear(b);
            double[] res = ColorSpaceConverter.RGBtoOKLAB(r, g, b);
            int[] rgb = ColorSpaceConverter.OKLABtoRGB(res[0] * coef + coef2, res[1], res[2]);
            return ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
        }
    }

    public void setUsesSquareChunks(boolean v) {
        usesSquareChunks = v;
    }

    public boolean usesSuccessiveRefinement() {
        return false;
    }

    public boolean supportsNonBlockingRender() {
        return false;
    }

    public boolean hasPatternedLogic() {
        return false;
    }

    public InterpolationMethod getInterpolationMethod() {
        return method;
    }

    public int getDemColor() {
        return dem_color;
    }

    public double getContourFactor() {
        return contourFactor;
    }

    public boolean getBanded() {
        return banded;
    }

    public Blending getBlending() {
        return blending;
    }

    public Blending getOFSBlending() {
        return ofs_blending;
    }

    public Blending getHSSBlending() {
        return hss_blending;
    }

    public Blending getRPSBlending() {
        return rps_blending;
    }

    public Blending getENSBlending() {
        return ens_blending;
    }

    public Blending geNDESBlending() {
        return ndes_blending;
    }

    protected boolean skipTrapPostProcessing(double val) {
        return (!ots.useTraps || !ots.trapIncludeNotEscaped) && !usesTrueColorIn && isMaximumIterations(val);
    }

    protected int getPaletteLength(boolean escaped) {
        return  (!escaped && usePaletteForInColoring) ? palette_incoloring.getPaletteLength() : palette_outcoloring.getPaletteLength();
    }

    private void setPostProcessingBlending() {

        ens_blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, ens.en_color_blending, COLOR_SPACE);
        ens_blending.setReverseColors(ens.en_reverse_color_blending);

        rps_blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, rps.rp_color_blending, COLOR_SPACE);
        rps_blending.setReverseColors(rps.rp_reverse_color_blending);

        ndes_blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, ndes.nde_color_blending, COLOR_SPACE);
        ndes_blending.setReverseColors(ndes.nde_reverse_color_blending);

        hss_blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, hss.hs_color_blending, COLOR_SPACE);
        hss_blending.setReverseColors(hss.hs_reverse_color_blending);

        ofs_blending = BlendingFactory.blendingFactory(COLOR_SMOOTHING_METHOD, ofs.of_color_blending, COLOR_SPACE);
        ofs_blending.setReverseColors(ofs.of_reverse_color_blending);

    }

    protected Square getNextRectangleArea(int iteration) {
        if(SPLIT_INTO_RECTANGLE_AREAS) {
            synchronized (rectangleAreasQueueu) {
                if (rectangleAreasQueueu.isEmpty()) {
                    return null;
                }
                return rectangleAreasQueueu.dequeue();
            }
        }

        if(iteration == 0) {
            return new Square(FROMx, FROMy, TOx, TOy);
        }
        return null;
    }

    protected void initializeRectangleAreasQueue(int image_width, int image_height) throws StopExecutionException {
        if(!SPLIT_INTO_RECTANGLE_AREAS) {
            return;
        }

        if(taskId == 0) {
            rectangleAreasQueueu = new ExpandingQueueSquare(RECTANGLE_AREAS_QUEUE_INIT_SIZE);
        }

        if(RECTANGLE_AREA_SPLIT_ALGORITHM == 0) {
            if(taskId == 0) {
                //create rectangles of x*y pixels
                int yLength = image_height % AREA_DIMENSION_Y == 0 ? image_height / AREA_DIMENSION_Y : image_height / AREA_DIMENSION_Y + 1;
                int xLength = image_width % AREA_DIMENSION_X == 0 ? image_width / AREA_DIMENSION_X : image_width / AREA_DIMENSION_X + 1;
                for(int y = 0; y < yLength; y++) {
                    for(int x = 0; x < xLength; x++) {
                        int FROMx = x * AREA_DIMENSION_X;
                        int TOx = (x + 1) * AREA_DIMENSION_X;
                        int FROMy = y * AREA_DIMENSION_Y;
                        int TOy = (y + 1) * AREA_DIMENSION_Y;
                        TOx = Math.min(TOx, image_width);
                        TOy = Math.min(TOy, image_height);
                        rectangleAreasQueueu.enqueue(new Square(FROMx, FROMy, TOx, TOy));
                    }
                }
            }
        }
        else if(RECTANGLE_AREA_SPLIT_ALGORITHM == 1) {
            //split the original rectangle into an x*y grid
            WaitOnCondition.WaitOnCyclicBarrier(initialize_jobs_sync4);
            int area_width = TOx - FROMx;
            int area_height = TOy - FROMy;
            synchronized (rectangleAreasQueueu) {
                for(int y = 0; y < AREA_DIMENSION_Y; y++) {
                    for (int x = 0; x < AREA_DIMENSION_X; x++) {
                        rectangleAreasQueueu.enqueue(new Square(FROMx + x * area_width / AREA_DIMENSION_X, FROMy + y * area_height / AREA_DIMENSION_Y, FROMx + (x + 1) * area_width / AREA_DIMENSION_X, FROMy + (y + 1) * area_height / AREA_DIMENSION_Y));
                    }
                }
            }
        }
        else {
            //split the original image into an x*y grid
            if(taskId == 0) {
                for(int y = 0; y < AREA_DIMENSION_Y; y++) {
                    for (int x = 0; x < AREA_DIMENSION_X; x++) {
                        rectangleAreasQueueu.enqueue(new Square(x * image_width / AREA_DIMENSION_X, y * image_height / AREA_DIMENSION_Y, (x + 1) * image_width / AREA_DIMENSION_X, (y + 1) * image_height / AREA_DIMENSION_Y));
                    }
                }
            }
        }

        WaitOnCondition.WaitOnCyclicBarrier(initialize_jobs_sync3);

    }
}
